package pixlepix.auracascade.block.tile;

import com.sun.jmx.remote.internal.ArrayQueue;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import pixlepix.auracascade.data.CoordTuple;
import pixlepix.auracascade.data.ItemStackMapEntry;
import pixlepix.auracascade.data.StorageItemStack;
import pixlepix.auracascade.item.ItemStorageBook;
import scala.collection.mutable.ArrayStack;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

/**
 * Created by localmacaccount on 1/24/15.
 */
public class TileBookshelfCoordinator extends TileEntity implements IInventory {

    public ArrayList<TileStorageBookshelf> bookshelfLocations = new ArrayList<TileStorageBookshelf>();


    public boolean hasCheckedShelves;
    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        writeCustomNBT(nbt);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        readCustomNBT(nbt);
    }

    public void readCustomNBT(NBTTagCompound nbt) {
    }

    public void writeCustomNBT(NBTTagCompound nbt) {
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (worldObj.getTotalWorldTime() % 20 == 0 || !hasCheckedShelves) {
            bookshelfLocations = new ArrayList<TileStorageBookshelf>();
            ArrayStack<CoordTuple> toSearch = new ArrayStack<CoordTuple>();
            toSearch.push(new CoordTuple(this));
            while (toSearch.size() > 0) {
                CoordTuple nextTuple = toSearch.pop();
                for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
                    CoordTuple newTuple = nextTuple.add(direction);
                    TileEntity storageBookshelf = newTuple.getTile(worldObj);
                    if (storageBookshelf instanceof TileStorageBookshelf && !bookshelfLocations.contains(newTuple.getTile(worldObj))) {
                        toSearch.push(newTuple);
                        bookshelfLocations.add((TileStorageBookshelf) newTuple.getTile(worldObj));
                    }
                }
            }
            hasCheckedShelves = true;
        }
    }

    public ArrayList<TileStorageBookshelf> getBookshelves() {
        return bookshelfLocations;
    }

    @Override
    public int getSizeInventory() {
        int result = 0;
        for (TileStorageBookshelf bookshelf : getBookshelves()) {
            result += bookshelf.getSizeInventory();
        }
        return result;
    }

    public ArrayList<ItemStack> getInv() {
        ArrayList<ItemStack> result = new ArrayList<ItemStack>(getSizeInventory() + 2);
        for (TileStorageBookshelf bookshelf : getBookshelves()) {
            result.addAll(bookshelf.inv);
        }
        return result;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return getInv().get(i);
    }

    //Get the bookshelf responsible for the ith slot in the total inventory array
    //Needed to mark the bookshelf as changed.
    public TileStorageBookshelf getBookshelfAtIndex(int i) {
        for (TileStorageBookshelf bookshelf : getBookshelves()) {
            i -= bookshelf.getSizeInventory();
            if (i < 0) {
                return bookshelf;
            }
        }
        return null;
    }

    public int getIndexWithinBookshelf(int i) {
        for (TileStorageBookshelf bookshelf : getBookshelves()) {
            i -= bookshelf.getSizeInventory();
            if (i < 0) {
                return i + bookshelf.getSizeInventory();
            }
        }
        return 0;
    }

    @Override
    public ItemStack decrStackSize(int slot, int amt) {
        ItemStack stack = getStackInSlot(slot);
        if (stack != null) {
            if (stack.stackSize <= amt) {
                setInventorySlotContents(slot, null);
            } else {
                stack = stack.splitStack(amt);
                if (stack.stackSize == 0) {
                    setInventorySlotContents(slot, null);
                }
            }
        }

        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        return stack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack stack) {
        TileStorageBookshelf bookshelf = getBookshelfAtIndex(i);
        bookshelf.setInventorySlotContents(getIndexWithinBookshelf(i), stack);
        bookshelf.markDirty();
    }

    @Override
    public void markDirty() {
        //This is inefficient, but it's the only possible way I can find
        for (TileStorageBookshelf bookshelf : getBookshelves()) {
            bookshelf.markDirty();
        }
    }

    @Override
    public String getInventoryName() {
        return "Bookshelf Coordinator";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        return true;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    public ArrayList<StorageItemStack> getAbstractInventory() {
        ArrayList<ItemStack> startInv = getInv();
        ArrayList<StorageItemStack> inv = new ArrayList<StorageItemStack>();
        for (ItemStack itemStack : startInv) {
            if (itemStack != null) {
                StorageItemStack remainingStorage = new StorageItemStack(itemStack);
                for (StorageItemStack storageItemStack : inv) {
                    remainingStorage = storageItemStack.merge(remainingStorage, Integer.MAX_VALUE);
                }
                while (remainingStorage != null && remainingStorage.stackSize > 0) {
                    int delta = remainingStorage.stackSize;
                    StorageItemStack storageItemStack = new StorageItemStack(remainingStorage.item, delta, remainingStorage.damage, remainingStorage.compound);
                    inv.add(storageItemStack);
                    remainingStorage.stackSize -= delta;
                }
            }
        }
        return inv;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack) {

        TileStorageBookshelf bookshelf = getBookshelfAtIndex(i);
        return bookshelf.isItemValidForSlot(getIndexWithinBookshelf(i), stack);
    }

    //Used by SlotCoordinator.
    public boolean isItemValidForSlotSensitive(int i, ItemStack stack) {

        TileStorageBookshelf bookshelf = getBookshelfAtIndex(i);
        return bookshelf.isItemValidForSlotSensitive(getIndexWithinBookshelf(i), stack);
    }
}