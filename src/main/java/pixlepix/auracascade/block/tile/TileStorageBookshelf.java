package pixlepix.auracascade.block.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextComponentString;
import pixlepix.auracascade.data.ItemStackMapEntry;
import pixlepix.auracascade.data.StorageItemStack;
import pixlepix.auracascade.item.ItemStorageBook;
import pixlepix.auracascade.main.AuraUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by localmacaccount on 1/23/15.
 */
public class TileStorageBookshelf extends TileEntity implements IInventory {
    public ItemStack storedBook;

    public ArrayList<ItemStack> inv = new ArrayList<ItemStack>();
    //Minimalist cache to improve performance
    private HashMap<ItemStackMapEntry, Boolean> validCache = new HashMap<ItemStackMapEntry, Boolean>();

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        writeCustomNBT(nbt);
        return nbt;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        readCustomNBT(nbt);
    }

    public void readCustomNBT(NBTTagCompound nbt) {
        if (nbt.getTag("book") != null) {
            storedBook = ItemStack.loadItemStackFromNBT((NBTTagCompound) nbt.getTag("book"));
        }

        if (storedBook != null) {
            ItemStorageBook itemStorageBook = (ItemStorageBook) storedBook.getItem();
            inv = itemStorageBook.getInventory(storedBook);
        } else {
            inv = new ArrayList<ItemStack>();
        }
        validCache = new HashMap<ItemStackMapEntry, Boolean>();
    }

    public void writeCustomNBT(NBTTagCompound nbt) {
        NBTTagCompound compound = new NBTTagCompound();
        if (storedBook != null) {
            compound = storedBook.writeToNBT(compound);
        }
        nbt.setTag("book", compound);
    }

	@Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        writeCustomNBT(nbt);
        return new SPacketUpdateTileEntity(getPos(), -999, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readCustomNBT(pkt.getNbtCompound());
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

        if (inv == null) {
            ItemStorageBook itemStorageBook = (ItemStorageBook) storedBook.getItem();
            inv = itemStorageBook.getInventory(storedBook);
        }

        return inv.get(i);
    }

    @Override
    public ItemStack decrStackSize(int slot, int amt) {

        markDirty();
        return AuraUtil.decrStackSize(this, slot, amt);
    }

    @Override
    public ItemStack removeStackFromSlot(int i) {
        return getStackInSlot(i);
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        if (storedBook == null) {
            return;
        }
        if (inv == null) {
            ItemStorageBook itemStorageBook = (ItemStorageBook) storedBook.getItem();
            inv = itemStorageBook.getInventory(storedBook);
        }
        inv.set(slot, stack);
        markDirty();
    }

    public void onStoredBookChange() {
        if (storedBook != null) {
            ItemStorageBook itemStorageBook = (ItemStorageBook) storedBook.getItem();
            inv = itemStorageBook.getInventory(storedBook);
        } else {
            inv = new ArrayList<ItemStack>();
        }
        validCache = new HashMap<ItemStackMapEntry, Boolean>();
        markDirty();
    }   @Override
    public void markDirty() {
        super.markDirty();
        if (storedBook != null) {
            ItemStorageBook itemStorageBook = (ItemStorageBook) storedBook.getItem();
            ItemStorageBook.setInventory(storedBook, inv);
            inv = itemStorageBook.getInventory(storedBook);
        } else {
            inv = new ArrayList<ItemStack>();

        }
        validCache = new HashMap<ItemStackMapEntry, Boolean>();
        //TODO BS storage, verify an extra mark dirty is needed?
    }

    @Override
    public String getName() {
        return "Storage Bookshelf";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public TextComponentString getDisplayName() {
        return new TextComponentString(getName());
    }

    @Override
    public int getInventoryStackLimit() {
        return storedBook != null ? ((ItemStorageBook) storedBook.getItem()).getMaxStackSize() : 0;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        if (storedBook == null) {
            return false;
        }
        if (validCache.containsKey(new ItemStackMapEntry(item))) {
            return validCache.get(new ItemStackMapEntry(item));
        }
        ItemStorageBook itemStorageBook = (ItemStorageBook) storedBook.getItem();
        if (!((ItemStorageBook) storedBook.getItem()).isItemValid(item, this)) {
            return false;
        }
        ArrayList<ItemStack> testInv = itemStorageBook.getInventory(storedBook);
        testInv.add(item);

        ArrayList<StorageItemStack> inv = new ArrayList<StorageItemStack>();
        for (ItemStack itemStack : testInv) {
            if (itemStack != null) {
                StorageItemStack remainingStorage = mergeStack(itemStorageBook, inv, itemStack);
                if (remainingStorage != null && remainingStorage.stackSize > 0) {
                    validCache.put(new ItemStackMapEntry(item), false);
                    return false;
                }
            }
        }
        validCache.put(new ItemStackMapEntry(item), true);
        return true;

    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {}

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        inv.clear();
    }

    public boolean isItemValidForSlotSensitive(int slot, ItemStack item) {
        //Used by SlotCoordinator
        //Version of isItemValidForSlot that replaces the item in the certain slot before checking validity
        //NOT cached, so this is much more performance intensive
        if (storedBook == null) {
            return false;
        }
        ItemStorageBook itemStorageBook = (ItemStorageBook) storedBook.getItem();
        if (!((ItemStorageBook) storedBook.getItem()).isItemValid(item, this)) {
            return false;
        }
        ArrayList<ItemStack> testInv = itemStorageBook.getInventory(storedBook);
        //Only difference from isItemValidForSlot
        testInv.set(slot, item);

        ArrayList<StorageItemStack> inv = new ArrayList<StorageItemStack>();
        for (ItemStack itemStack : testInv) {
            if (itemStack != null) {
                StorageItemStack remainingStorage = mergeStack(itemStorageBook, inv, itemStack);
                if (remainingStorage != null && remainingStorage.stackSize > 0) {
                    return false;
                }
            }
        }
        return true;

    }

    public StorageItemStack mergeStack(ItemStorageBook itemStorageBook, ArrayList<StorageItemStack> inv, ItemStack itemStack) {
        StorageItemStack remainingStorage = new StorageItemStack(itemStack);
        for (StorageItemStack storageItemStack : inv) {
            remainingStorage = storageItemStack.merge(remainingStorage, itemStorageBook.getMaxStackSize());
        }
        while (inv.size() < itemStorageBook.getHeldStacks() && remainingStorage != null && remainingStorage.stackSize > 0) {
            int delta = Math.min(itemStorageBook.getMaxStackSize(), remainingStorage.stackSize);
            StorageItemStack storageItemStack = new StorageItemStack(remainingStorage.item, delta, remainingStorage.damage, remainingStorage.compound);
            inv.add(storageItemStack);
            remainingStorage.stackSize -= delta;
        }
        return remainingStorage;
    }

    public ArrayList<StorageItemStack> getAbstractInventory() {
        ArrayList<ItemStack> startInv = inv;
        return TileBookshelfCoordinator.getAbstractInventoryFromInv(startInv);
    }
}
