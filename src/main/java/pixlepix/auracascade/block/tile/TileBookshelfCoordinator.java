package pixlepix.auracascade.block.tile;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.data.StorageItemStack;
import pixlepix.auracascade.main.AuraUtil;
import pixlepix.auracascade.network.PacketBurst;

/**
 * Created by localmacaccount on 1/24/15.
 */
public class TileBookshelfCoordinator extends TileEntity implements IInventory, ITickable {

    public ArrayList<TileStorageBookshelf> bookshelfLocations = new ArrayList<TileStorageBookshelf>();


    public boolean hasCheckedShelves;
    public int neededPower = 0;
    public int lastPower = 0;

    public static ArrayList<StorageItemStack> getAbstractInventoryFromInv(ArrayList<ItemStack> startInv) {
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
        return new S35PacketUpdateTileEntity(getPos(), -999, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        readCustomNBT(pkt.getNbtCompound());
    }

    public boolean hasClearLineOfSight(BlockPos pos) {
        int x = getPos().getX();
        int y = getPos().getY();
        int z = getPos().getZ();

        Vec3 originalVector = new Vec3(pos.subtract(getPos()));
        Vec3 vec3 = originalVector.normalize();
        double f = 0;
        while (true) {
            f += .1;
            x = (int) (getPos().getX() + f * vec3.xCoord);
            y = (int) (getPos().getY() + f * vec3.yCoord);
            z = (int) (getPos().getZ() + f * vec3.zCoord);

            BlockPos pos_ = new BlockPos(x, y, z);

            if (pos_.equals(pos)) {
                return true;
            }
            if (pos_.equals(getPos())) {
                continue;
            }
            if (!worldObj.isAirBlock(pos_) && pos_.distanceSq(getPos()) >= 2.25 && pos_.distanceSq(pos) >= 2.25) {
                return false;
            }
            if (f > originalVector.lengthVector()) {
                return true;

            }
        }

    }

    @Override
    public void update() {
        if (!worldObj.isRemote && worldObj.getTotalWorldTime() % 200 == 5) {
            int numShelves = getBookshelves().size();

            AuraCascade.analytics.eventDesign("booshelfUsage", AuraUtil.formatLocation(this), numShelves);
            neededPower = (int) (5 * numShelves * Math.pow(1.05, numShelves));
            //Drain power from aura nodes
            lastPower = 0;
            for (EnumFacing direction : EnumFacing.VALUES) {
                TileEntity tileEntity = worldObj.getTileEntity(getPos().offset(direction));
                if (tileEntity instanceof AuraTile) {
                    AuraTile auraTile = (AuraTile) tileEntity;
                    if (auraTile.energy > 0) {
                        auraTile.burst(getPos(), "magicCrit", EnumAura.WHITE_AURA, 1);
                        lastPower += auraTile.energy;
                        auraTile.energy = 0;
                    }
                }
            }
            worldObj.markBlocksDirtyVertical(pos.getX(), pos.getZ(), pos.getX(), pos.getZ());
        }
        if (worldObj.getTotalWorldTime() % 200 == 0 || !hasCheckedShelves) {
/* todo 1.8.8 this causes an infinite loop wtf
            bookshelfLocations = new ArrayList<TileStorageBookshelf>();
            ArrayList<BlockPos> checkedLocations = new ArrayList<BlockPos>();
            ArrayList<BlockPos> toSearch = new ArrayList<BlockPos>();
            toSearch.add(getPos());
            while (toSearch.size() > 0) {
                BlockPos nextPos = toSearch.remove(0);
                for (EnumFacing direction : EnumFacing.VALUES) {
                    BlockPos newPos = nextPos.offset(direction);
                    TileEntity storageBookshelf = worldObj.getTileEntity(newPos);
                    if (storageBookshelf instanceof TileStorageBookshelf && !checkedLocations.contains(newPos)) {
                        toSearch.add(newPos);
                        if (hasClearLineOfSight(newPos)) {
                            bookshelfLocations.add((TileStorageBookshelf) worldObj.getTileEntity(newPos));
                            if (!worldObj.isRemote) {
                                burst(newPos, "enchantmenttable", EnumAura.WHITE_AURA, 1);
                            }
                        }
                        checkedLocations.add(newPos);
                    }
                    if ((worldObj.getBlockState(newPos).getBlock() instanceof BlockBookshelf || (worldObj.getBlockState(newPos).getBlock() != null && worldObj.getBlockState(newPos).getBlock() == AuraCascade.proxy.chiselBookshelf))
                            && !checkedLocations.contains(newPos)) {
                        toSearch.add(newPos);
                        checkedLocations.add(newPos);
                    }
                }
            }
            hasCheckedShelves = true;
*/
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

        return AuraUtil.decrStackSize(this, slot, amt);
    }

    @Override
    public ItemStack removeStackFromSlot(int p_70304_1_) {
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
    public String getName() {
        return "Bookshelf Coordinator";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public IChatComponent getDisplayName() {
        return new ChatComponentText(getName());
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
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    public ArrayList<StorageItemStack> getAbstractInventory() {
        ArrayList<ItemStack> startInv = getInv();
        return getAbstractInventoryFromInv(startInv);
    }

    public void burst(BlockPos target, String particle, EnumAura aura, double composition) {
        AuraCascade.proxy.networkWrapper.sendToAllAround(new PacketBurst(getPos(), target, particle, aura.r, aura.g, aura.b, composition), new NetworkRegistry.TargetPoint(worldObj.provider.func_177502_q(), getPos().getX(), getPos().getY(), getPos().getZ(), 32));
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack) {
        if (lastPower < neededPower) {
            return false;
        }
        TileStorageBookshelf bookshelf = getBookshelfAtIndex(i);
        return bookshelf.isItemValidForSlot(getIndexWithinBookshelf(i), stack);
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {

    }

    //Used by SlotCoordinator.
    public boolean isItemValidForSlotSensitive(int i, ItemStack stack) {

        TileStorageBookshelf bookshelf = getBookshelfAtIndex(i);
        return bookshelf != null && bookshelf.isItemValidForSlotSensitive(getIndexWithinBookshelf(i), stack);
    }
}