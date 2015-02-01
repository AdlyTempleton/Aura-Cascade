package pixlepix.auracascade.block.tile;

import com.sun.jmx.remote.internal.ArrayQueue;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBookshelf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.data.CoordTuple;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.data.ItemStackMapEntry;
import pixlepix.auracascade.data.StorageItemStack;
import pixlepix.auracascade.item.ItemStorageBook;
import pixlepix.auracascade.main.CommonProxy;
import pixlepix.auracascade.network.PacketBurst;
import scala.collection.mutable.ArrayStack;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import java.util.Vector;

/**
 * Created by localmacaccount on 1/24/15.
 */
public class TileBookshelfCoordinator extends TileEntity implements IInventory {

    public ArrayList<TileStorageBookshelf> bookshelfLocations = new ArrayList<TileStorageBookshelf>();


    public boolean hasCheckedShelves;
    public int neededPower = 0;
    public int lastPower = 0;

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
        lastPower = nbt.getInteger("lastPower");
        neededPower = nbt.getInteger("neededPower");
    }

    public void writeCustomNBT(NBTTagCompound nbt) {

        nbt.setInteger("lastPower", lastPower);
        nbt.setInteger("neededPower", neededPower);
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

    public boolean hasClearLineOfSight(CoordTuple tuple) {
        int x = (int) (xCoord);
        int y = (int) (yCoord);
        int z = (int) (zCoord);

        Vec3 originalVector = Vec3.createVectorHelper(tuple.getX() - x, tuple.getY() - y, tuple.getZ() - z);
        Vec3 vec3 = originalVector.normalize();
        double f = 0;
        while (true) {
            f += .1;
            x = (int) (xCoord + f * vec3.xCoord);
            y = (int) (yCoord + f * vec3.yCoord);
            z = (int) (zCoord + f * vec3.zCoord);

            if (new CoordTuple(x, y, z).equals(tuple)) {
                return true;
            }
            if (new CoordTuple(x, y, z).equals(new CoordTuple(xCoord, yCoord, zCoord))) {
                continue;
            }
            if (!worldObj.isAirBlock(x, y, z) && new CoordTuple(x, y, z).dist(this) >= 1.5 && new CoordTuple(x, y, z).dist(tuple) >= 1.5) {
                return false;
            }
            if (f > originalVector.lengthVector()) {
                return true;

            }
        }

    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote && worldObj.getTotalWorldTime() % 20 == 5) {
            int numShelves = getBookshelves().size();
            neededPower = (int) (5 * numShelves * Math.pow(1.05, numShelves));
            //Drain power from aura nodes
            lastPower = 0;
            for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
                TileEntity tileEntity = worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
                if (tileEntity instanceof AuraTile) {
                    AuraTile auraTile = (AuraTile) tileEntity;
                    if (auraTile.energy > 0) {
                        auraTile.burst(new CoordTuple(this), "magicCrit", EnumAura.WHITE_AURA, 1);
                        lastPower += auraTile.energy;
                        auraTile.energy = 0;
                    }
                }
            }
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
        if (worldObj.getTotalWorldTime() % 20 == 0 || !hasCheckedShelves) {
            bookshelfLocations = new ArrayList<TileStorageBookshelf>();
            ArrayList<CoordTuple> checkedLocations = new ArrayList<CoordTuple>();
            ArrayStack<CoordTuple> toSearch = new ArrayStack<CoordTuple>();
            toSearch.push(new CoordTuple(this));
            while (toSearch.size() > 0) {
                CoordTuple nextTuple = toSearch.pop();
                for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
                    CoordTuple newTuple = nextTuple.add(direction);
                    TileEntity storageBookshelf = newTuple.getTile(worldObj);
                    if (storageBookshelf instanceof TileStorageBookshelf && !checkedLocations.contains(newTuple)) {
                        toSearch.push(newTuple);
                        if (hasClearLineOfSight(newTuple)) {
                            bookshelfLocations.add((TileStorageBookshelf) newTuple.getTile(worldObj));
                            burst(newTuple, "enchantmenttable", EnumAura.WHITE_AURA, 1);
                        }
                        checkedLocations.add(newTuple);
                    }
                    if ((newTuple.getBlock(worldObj) instanceof BlockBookshelf || (newTuple.getBlock(worldObj) != null && newTuple.getBlock(worldObj) == AuraCascade.proxy.chiselBookshelf))
                            && !checkedLocations.contains(newTuple)) {
                        toSearch.push(newTuple);
                        checkedLocations.add(newTuple);
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
        if (lastPower < neededPower) {
            return 0;
        }
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

    public void burst(CoordTuple target, String particle, EnumAura aura, double composition) {
        AuraCascade.proxy.networkWrapper.sendToAllAround(new PacketBurst(new CoordTuple(this), target, particle, aura.r, aura.g, aura.b, composition), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 32));
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack) {
        if (lastPower < neededPower) {
            return false;
        }
        TileStorageBookshelf bookshelf = getBookshelfAtIndex(i);
        return bookshelf.isItemValidForSlot(getIndexWithinBookshelf(i), stack);
    }

    //Used by SlotCoordinator.
    public boolean isItemValidForSlotSensitive(int i, ItemStack stack) {

        TileStorageBookshelf bookshelf = getBookshelfAtIndex(i);
        return bookshelf != null ? bookshelf.isItemValidForSlotSensitive(getIndexWithinBookshelf(i), stack) : false;
    }
}