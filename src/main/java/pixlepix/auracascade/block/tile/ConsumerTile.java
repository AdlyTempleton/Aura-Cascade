package pixlepix.auracascade.block.tile;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.main.AuraUtil;
import pixlepix.auracascade.network.PacketBurst;

import java.util.List;

/**
 * Created by pixlepix on 12/21/14.
 */
public class ConsumerTile extends TileEntity {

    public int storedPower;

    @Override
    public void readFromNBT(NBTTagCompound nbt) {

        super.readFromNBT(nbt);
        readCustomNBT(nbt);
    }

    public void readCustomNBT(NBTTagCompound nbt) {
        storedPower = nbt.getInteger("storedPower");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        writeCustomNBT(nbt);
    }

    public void writeCustomNBT(NBTTagCompound nbt) {
        nbt.setInteger("storedPower", storedPower);
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
                    storedPower += auraTile.energy;
                    auraTile.energy = 0;
                }
            }
        }



    }
}
