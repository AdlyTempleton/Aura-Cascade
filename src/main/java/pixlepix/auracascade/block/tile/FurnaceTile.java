package pixlepix.auracascade.block.tile;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.data.AuraQuantity;
import pixlepix.auracascade.data.CoordTuple;
import pixlepix.auracascade.main.AuraUtil;
import pixlepix.auracascade.main.CommonProxy;
import pixlepix.auracascade.network.PacketBurst;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by pixlepix on 11/29/14.
 */
public class FurnaceTile extends TileEntity {

    public static final int COST_PER_TICK = 200;
    //Smelting occurs when this number reaches 20
    //Incremented once per tick
    int heat = 0;

    @Override
    public void readFromNBT(NBTTagCompound nbt) {

        super.readFromNBT(nbt);
        readCustomNBT(nbt);
    }

    private void readCustomNBT(NBTTagCompound nbt) {
        heat = nbt.getInteger("heat");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        writeCustomNBT(nbt);
    }

    private void writeCustomNBT(NBTTagCompound nbt){
        nbt.setInteger("heat", heat);
    }



    @Override
    public void updateEntity() {
        super.updateEntity();
        if(!worldObj.isRemote){

            if(worldObj.getTotalWorldTime() % 2400 == 0){
                AuraUtil.keepAlive(this, 3);
            }
            int energyLeft = COST_PER_TICK;

            //Drain energy from Aura Nodes
            for(ForgeDirection direction:ForgeDirection.VALID_DIRECTIONS){
                TileEntity tileEntity = worldObj.getTileEntity(xCoord+direction.offsetX, yCoord+direction.offsetY, zCoord+direction.offsetZ);
                if(tileEntity instanceof AuraTile){
                    AuraTile auraTile = (AuraTile) tileEntity;
                    int drain = Math.min(energyLeft, auraTile.energy);
                    energyLeft -= drain;
                    auraTile.energy -= drain;
                    if(energyLeft == 0){
                        break;
                    }
                }
            }
            heat += (energyLeft-COST_PER_TICK);

            if(heat >= COST_PER_TICK) {
                heat = 0;
                int range = 3;
                List<EntityItem> nearbyItems = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord - range, yCoord - range, zCoord - range, xCoord + range, yCoord + range, zCoord + range));
                for (EntityItem entityItem : nearbyItems) {
                    ItemStack stack = entityItem.getEntityItem();
                    if (FurnaceRecipes.smelting().getSmeltingResult(stack) != null) {

                        //Kill the stack
                        if (stack.stackSize == 0) {
                            entityItem.setDead();
                        } else {
                            stack.stackSize--;
                        }

                        EntityItem newEntity = new EntityItem(worldObj, entityItem.posX, entityItem.posY, entityItem.posZ, FurnaceRecipes.smelting().getSmeltingResult(stack).copy());

                        newEntity.delayBeforeCanPickup = entityItem.delayBeforeCanPickup;
                        newEntity.motionX = entityItem.motionX;
                        newEntity.motionY = entityItem.motionY;
                        newEntity.motionZ = entityItem.motionZ;

                        worldObj.spawnEntityInWorld(newEntity);

                        AuraCascade.proxy.networkWrapper.sendToAllAround(new PacketBurst(1, newEntity.posX, newEntity.posY, newEntity.posZ), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 32));

                        break;
                    }
                }
            }
        }



    }
}
