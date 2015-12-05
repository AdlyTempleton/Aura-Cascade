package pixlepix.auracascade.block.tile;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import pixlepix.auracascade.block.BlockMonitor;
import pixlepix.auracascade.data.EnumAura;
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
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        writeCustomNBT(nbt);
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

    public void updateMonitor() {
        for (EnumFacing d1 : EnumFacing.VALUES) {
            Block b = worldObj.getBlockState(getPos().offset(d1)).getBlock();
            if (b instanceof BlockMonitor) {

                for (EnumFacing d2 : EnumFacing.VALUES) {
                    BlockPos pos = getPos().offset(d2).offset(d1);
                    Block b2 = worldObj.getBlockState(pos).getBlock();
                    b2.onNeighborBlockChange(worldObj, pos, worldObj.getBlockState(pos), b);
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
            //Drain energy from aura Nodes
            for (EnumFacing direction : EnumFacing.VALUES) {
                TileEntity tileEntity = worldObj.getTileEntity(getPos().offset(direction));
                if (tileEntity instanceof AuraTile) {
                    AuraTile auraTile = (AuraTile) tileEntity;
                    if (auraTile.energy > 0) {
                        auraTile.burst(getPos(), "magicCrit", EnumAura.WHITE_AURA, 1);
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

                worldObj.markBlockForUpdate(getPos());

            } else if (worldObj.getTotalWorldTime() % 20 == 2) {

                worldObj.markBlockForUpdate(getPos());

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
                        worldObj.markBlockForUpdate(getPos());
                    }
                    if (storedPower < nextBoostCost) {
                        break;
                    }
                    progress += 1;
                    storedPower -= nextBoostCost;
                    nextBoostCost *= 2;
                    worldObj.markBlockForUpdate(getPos());
                    worldObj.notifyBlockOfStateChange(getPos(), worldObj.getBlockState(pos).getBlock());
                }
            }
        }
    }

    public abstract void onUsePower();
}
