package pixlepix.auracascade.block.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import pixlepix.auracascade.data.StorageItemStack;
import pixlepix.auracascade.item.ItemStorageBook;

import java.util.ArrayList;

/**
 * Created by localmacaccount on 1/23/15.
 */
public class TileStorageBookshelf extends TileEntity implements IInventory {
    public ItemStack storedBook;

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
    }

    public void readCustomNBT(NBTTagCompound nbt) {
        storedBook = ItemStack.loadItemStackFromNBT(nbt);
    }

    public void writeCustomNBT(NBTTagCompound nbt) {
        if (storedBook != null) {
            storedBook.writeToNBT(nbt);
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        writeCustomNBT(nbt);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, -999, nbt);
    }


    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        readCustomNBT(pkt.func_148857_g());
    }


    @Override
    public int getSizeInventory() {
        return storedBook != null ? ((ItemStorageBook) storedBook.getItem()).getActualCount() : 0;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        if (storedBook == null) {
            return null;
        }

        ItemStorageBook itemStorageBook = (ItemStorageBook) storedBook.getItem();
        return itemStorageBook.getInventory(storedBook).get(i);
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
        markDirty();
        return stack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        return getStackInSlot(i);
    }


    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        if (storedBook == null) {
            return;
        }

        ItemStorageBook itemStorageBook = (ItemStorageBook) storedBook.getItem();
        ArrayList<ItemStack> inv = itemStorageBook.getInventory(storedBook);
        inv.set(slot, stack);
        ItemStorageBook.setInventory(storedBook, inv);
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        markDirty();
    }

    @Override
    public String getInventoryName() {
        return "Storage Bookshelf";
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

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        if (storedBook == null) {
            return false;
        }
        ItemStorageBook itemStorageBook = (ItemStorageBook) storedBook.getItem();
        ArrayList<ItemStack> testInv = itemStorageBook.getInventory(storedBook);
        testInv.add(item);

        ArrayList<StorageItemStack> inv = new ArrayList<StorageItemStack>();
        for (ItemStack itemStack : testInv) {
            if (itemStack != null) {
                StorageItemStack remainingStorage = new StorageItemStack(itemStack);
                for (StorageItemStack storageItemStack : inv) {
                    remainingStorage = storageItemStack.merge(remainingStorage, itemStorageBook.getMaxStackSize());
                }
                while (inv.size() < itemStorageBook.getHeldStacks() && remainingStorage.stackSize > 0) {
                    int delta = Math.min(itemStorageBook.getMaxStackSize(), remainingStorage.stackSize);
                    StorageItemStack storageItemStack = new StorageItemStack(remainingStorage.item, delta, remainingStorage.damage, remainingStorage.compound);
                    inv.add(storageItemStack);
                    remainingStorage.stackSize -= delta;
                }
                if (remainingStorage != null && remainingStorage.stackSize > 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
