package pixlepix.auracascade.block.tile;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.main.AuraUtil;
import pixlepix.auracascade.network.PacketBurst;

import java.util.List;

/**
 * Created by pixlepix on 11/29/14.
 */
public class FurnaceTile extends ConsumerTile {

    public static final int COST_PER_SMELT = 1000;
    int heat = 0;

    public void readCustomNBT(NBTTagCompound nbt) {
        super.readCustomNBT(nbt);
        heat = nbt.getInteger("heat");
    }
    public void writeCustomNBT(NBTTagCompound nbt){
        super.writeCustomNBT(nbt);
        nbt.setInteger("heat", heat);
    }



    @Override
    public void updateEntity() {
        super.updateEntity();
        if(!worldObj.isRemote){

            if(worldObj.getTotalWorldTime() % 2400 == 0){
                AuraUtil.keepAlive(this, 3);
            }

            heat += (storedPower);
            storedPower = 0;
            if(heat >= COST_PER_SMELT) {
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
