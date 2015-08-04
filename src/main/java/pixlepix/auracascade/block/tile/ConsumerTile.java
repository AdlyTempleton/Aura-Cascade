package pixlepix.auracascade.block.tile;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import pixlepix.auracascade.block.BlockMonitor;
import pixlepix.auracascade.data.CoordTuple;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.main.AuraUtil;

/**
 * Created by pixlepix on 12/21/14.
 */
public abstract class ConsumerTile extends TileEntity {

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
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, -999, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        readCustomNBT(pkt.func_148857_g());
    }

    public void updateMonitor() {
        for (ForgeDirection d1 : ForgeDirection.VALID_DIRECTIONS) {
            Block b = new CoordTuple(this).add(d1).getBlock(worldObj);
            if (b instanceof BlockMonitor) {

                for (ForgeDirection d2 : ForgeDirection.VALID_DIRECTIONS) {
                    CoordTuple tuple = new CoordTuple(this).add(d2).add(d1);
                    Block b2 = tuple.getBlock(worldObj);
                    b2.onNeighborBlockChange(worldObj, tuple.getX(), tuple.getY(), tuple.getZ(), b);
                }
            }
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
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
            for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
                TileEntity tileEntity = worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
                if (tileEntity instanceof AuraTile) {
                    AuraTile auraTile = (AuraTile) tileEntity;
                    if (auraTile.energy > 0) {
                        auraTile.burst(new CoordTuple(this), "magicCrit", EnumAura.WHITE_AURA, 1);
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

                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);

            } else if (worldObj.getTotalWorldTime() % 20 == 2) {

                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);

            }

            if (worldObj.getTotalWorldTime() % 500 == 0) {
                AuraUtil.keepAlive(this, 3);
            }

            int nextBoostCost = getPowerPerProgress();
            while (true) {
                if (progress > getMaxProgress()) {
                    progress = 0;
                    onUsePower();
                    worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                }
                if (storedPower < nextBoostCost) {
                    break;
                }
                progress += 1;
                storedPower -= nextBoostCost;
                nextBoostCost *= 2;
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                worldObj.notifyBlockChange(xCoord, yCoord, zCoord, worldObj.getBlock(xCoord, yCoord, zCoord));
            }
        }
    }

    public abstract void onUsePower();
}
