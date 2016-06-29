package pixlepix.auracascade.block.tile;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import pixlepix.auracascade.block.BlockMonitor;
import pixlepix.auracascade.data.EnumRainbowColor;
import pixlepix.auracascade.main.AuraUtil;

/**
 * Created by pixlepix on 12/21/14.
 */
public abstract class ConsumerTile extends TileEntity implements ITickable {

    public int storedPower;
    public int lastPower;
    public int progress;
    private boolean lastValidState;

    @Override
    public void readFromNBT(NBTTagCompound nbt) {

        super.readFromNBT(nbt);
        readCustomNBT(nbt);
    }

    public abstract int getMaxProgress();

    public abstract int getPowerPerProgress();

    public void readCustomNBT(NBTTagCompound nbt) {
        progress = nbt.getInteger("progress");
        storedPower = nbt.getInteger("storedPower");
        lastPower = nbt.getInteger("lastPower");
    }

    public abstract boolean validItemsNearby();

    public void writeCustomNBT(NBTTagCompound nbt) {
        nbt.setInteger("progress", progress);
        nbt.setInteger("storedPower", storedPower);
        nbt.setInteger("lastPower", lastPower);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        writeCustomNBT(nbt);
        return nbt;
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

    public void updateMonitor() {
        for (EnumFacing d1 : EnumFacing.VALUES) {
            Block b = worldObj.getBlockState(getPos().offset(d1)).getBlock();
            if (b instanceof BlockMonitor) {

                for (EnumFacing d2 : EnumFacing.VALUES) {
                    BlockPos pos = getPos().offset(d2).offset(d1);
                    Block b2 = worldObj.getBlockState(pos).getBlock();
                    b2.onNeighborChange(worldObj, pos, getPos());
                }
            }
        }
    }

    @Override
    public void update() {
        if (!worldObj.isRemote) {
            if (worldObj.getTotalWorldTime() % 20 == 18) {
                storedPower *= .25;
            }

            if (worldObj.getTotalWorldTime() % 20 == 0) {
                if (lastValidState != validItemsNearby()) {
                    lastValidState = !lastValidState;
                    updateMonitor();
                }
            }

            boolean changeLastPower = false;
            //Drain energy from color Nodes
            for (EnumFacing direction : EnumFacing.VALUES) {
                TileEntity tileEntity = worldObj.getTileEntity(getPos().offset(direction));
                if (tileEntity instanceof AuraTile) {
                    AuraTile auraTile = (AuraTile) tileEntity;
                    if (auraTile.energy > 0) {
                        auraTile.burst(getPos(), "magicCrit");
                        storedPower += auraTile.energy;
                        auraTile.energy = 0;
                        changeLastPower = true;
                    }
                }
            }
            if (worldObj.getTotalWorldTime() % 20 == 0) {
                lastPower = 0;
            }
            if (changeLastPower) {
                lastPower = storedPower;
                markDirty();
            } else if (worldObj.getTotalWorldTime() % 20 == 2) {
            	markDirty();
            }

            if (worldObj.getTotalWorldTime() % 500 == 0) {
                AuraUtil.keepAlive(this, 3);
            }

            if (worldObj.getTotalWorldTime() % 20 == 1 || worldObj.getTotalWorldTime() % 20 == 2) {

                int nextBoostCost = getPowerPerProgress();
                while (true) {
                    if (progress > getMaxProgress()) {
                        progress = 0;
                        onUsePower();
                        markDirty();
                    }
                    if (storedPower < nextBoostCost) {
                        break;
                    }
                    progress += 1;
                    storedPower -= nextBoostCost;
                    nextBoostCost *= 2;
                    markDirty();
                   // worldObj.notifyBlockOfStateChange(getPos(), worldObj.getBlockState(pos).getBlock());
                    worldObj.markAndNotifyBlock(this.pos, this.worldObj.getChunkFromBlockCoords(this.pos),this.blockType.getDefaultState(), this.blockType.getDefaultState(), 2);
                }
            }
        }
    }

    public abstract void onUsePower();
}
