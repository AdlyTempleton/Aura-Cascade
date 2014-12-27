package pixlepix.auracascade.block.tile;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.AxisAlignedBB;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.data.AuraQuantity;
import pixlepix.auracascade.data.CoordTuple;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.main.AuraUtil;
import pixlepix.auracascade.main.CommonProxy;
import pixlepix.auracascade.network.PacketBurst;

import java.util.List;

/**
 * Created by pixlepix on 11/29/14.
 */
public class AuraTilePump extends AuraTilePumpBase {


    @Override
    public void updateEntity() {
        super.updateEntity();
        if(worldObj.getTotalWorldTime() % 2400 == 0){
            AuraUtil.keepAlive(this, 3);
        }
        if(!worldObj.isRemote && worldObj.getTotalWorldTime() % 20 ==2 && !worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)){
            if(pumpPower <= 0){
                int range = 3;
                List<EntityItem> nearbyItems = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord - range, yCoord - range, zCoord - range, xCoord + range, yCoord + range, zCoord + range));
                for (EntityItem entityItem : nearbyItems) {
                    ItemStack stack = entityItem.getEntityItem();
                    if(TileEntityFurnace.getItemBurnTime(stack) != 0){
                        //Worth noting that the burn time should be 2* longer than a furnace
                        pumpPower = TileEntityFurnace.getItemBurnTime(stack) / 20;
                        pumpSpeed = 200;

                        //Kill the stack
                        if (stack.stackSize == 0) {
                            entityItem.setDead();
                        } else {
                            stack.stackSize--;
                        }


                        AuraCascade.proxy.networkWrapper.sendToAllAround(new PacketBurst(1, entityItem.posX, entityItem.posY, entityItem.posZ), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 32));


                        break;
                    }
                }
            }
        }
    }


}
