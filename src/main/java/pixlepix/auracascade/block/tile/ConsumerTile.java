package pixlepix.auracascade.block.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import pixlepix.auracascade.data.CoordTuple;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.main.AuraUtil;

/**
 * Created by pixlepix on 12/21/14.
 */
public abstract class ConsumerTile extends TileEntity {

    public int storedPower;

    @Override
    public void readFromNBT(NBTTagCompound nbt) {

        super.readFromNBT(nbt);
        readCustomNBT(nbt);
    }

    public abstract int getMaxProgress();
    public abstract int getPowerPerProgress();

    public int progress;

    public void readCustomNBT(NBTTagCompound nbt) {
        progress = nbt.getInteger("progress");
        storedPower = nbt.getInteger("storedPower");
    }

    public void writeCustomNBT(NBTTagCompound nbt) {
        nbt.setInteger("progress", progress);
        nbt.setInteger("storedPower", storedPower);
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


    @Override
    public void updateEntity() {
        super.updateEntity();
        if(!worldObj.isRemote){
            if(worldObj.getTotalWorldTime() % 20 == 18){
                storedPower *= .5;
            }
            //Drain energy from aura Nodes
            for(ForgeDirection direction:ForgeDirection.VALID_DIRECTIONS){
                TileEntity tileEntity = worldObj.getTileEntity(xCoord+direction.offsetX, yCoord+direction.offsetY, zCoord+direction.offsetZ);
                if(tileEntity instanceof AuraTile){
                    AuraTile auraTile = (AuraTile) tileEntity;
                    if(auraTile.energy > 0) {
                        auraTile.burst(new CoordTuple(this), "magicCrit", EnumAura.WHITE_AURA, 1);
                        storedPower += auraTile.energy;
                        auraTile.energy = 0;
                    }
                }
            }

            if(worldObj.getTotalWorldTime() % 2400 == 0){
                AuraUtil.keepAlive(this, 3);
            }

            int nextBoostCost = getPowerPerProgress();
            while (true){
                if(progress > getMaxProgress()) {
                    progress = 0;
                    onUsePower();
                    worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                }
                if(storedPower < nextBoostCost){
                    break;
                }
                progress += 1;
                storedPower -= nextBoostCost;
                nextBoostCost *= 2;
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                worldObj.notifyBlockChange(xCoord, yCoord, zCoord, blockType);
            }
        }
    }

    public abstract void onUsePower();
}
