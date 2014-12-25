package pixlepix.auracascade.block.tile;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.StringUtils;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.main.AuraUtil;
import pixlepix.auracascade.network.PacketBurst;

import java.util.List;

/**
 * Created by pixlepix on 12/21/14.
 */
public class OreTile extends ConsumerTile {


    public static final int COST_PER_SMELT = 2000;
    int heat = 0;

    public void readCustomNBT(NBTTagCompound nbt) {
        super.readCustomNBT(nbt);
        heat = nbt.getInteger("heat");
    }

    public void writeCustomNBT(NBTTagCompound nbt) {
        super.writeCustomNBT(nbt);
        nbt.setInteger("heat", heat);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!worldObj.isRemote) {

            if (worldObj.getTotalWorldTime() % 2400 == 0) {
                AuraUtil.keepAlive(this, 3);
            }

            heat += (storedPower);
            storedPower = 0;
            if (heat >= COST_PER_SMELT) {
                heat = 0;
                int range = 3;
                List<EntityItem> nearbyItems = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord - range, yCoord - range, zCoord - range, xCoord + range, yCoord + range, zCoord + range));
                for (EntityItem entityItem : nearbyItems) {
                    ItemStack stack = entityItem.getEntityItem();
                    if (getTripleResult(stack) != null) {
                        ItemStack dustStack = getTripleResult(stack);
                        dustStack.stackSize = 3;
                        //Kill the stack
                        if (stack.stackSize == 0) {
                            entityItem.setDead();
                        } else {
                            stack.stackSize--;
                        }

                        EntityItem newEntity = new EntityItem(worldObj, entityItem.posX, entityItem.posY, entityItem.posZ, dustStack);

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

    public static ItemStack getTripleResult(ItemStack stack){
        int[] oreIds = OreDictionary.getOreIDs(stack);
        for(int id:oreIds){
            String oreName = OreDictionary.getOreName(id);
            if(StringUtils.startsWith(oreName, "ore")){
                String dustName = StringUtils.replace(oreName, "ore", "dust");
                return OreDictionary.getOres(dustName).get(0);
            }
        }
        return null;
    }
}