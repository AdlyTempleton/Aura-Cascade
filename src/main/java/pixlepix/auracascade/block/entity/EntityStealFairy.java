package pixlepix.auracascade.block.entity;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.network.PacketBurst;

import java.util.List;
import java.util.Random;

/**
 * Created by pixlepix on 12/14/14.
 */
public class EntityStealFairy extends EntityFairy {

    public EntityStealFairy(World world){
        super(world);
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if(!worldObj.isRemote && worldObj.getTotalWorldTime() % 200 == 0){
            List<EntityPlayer> nearbyEntities = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(posX - 2, posY - 2, posZ - 2, posX + 2, posY + 2, posZ + 2));
            for(EntityPlayer entity:nearbyEntities){
                ItemStack stack = entity.getCurrentEquippedItem();

                if(stack != null && entity != player) {
                    EntityItem item = new EntityItem(worldObj, player.posX, player.posY, player.posZ, stack);
                    item.setVelocity(0, 0, 0);
                    item.delayBeforeCanPickup = 0;
                    worldObj.spawnEntityInWorld(item);

                    entity.inventory.setInventorySlotContents(entity.inventory.currentItem, null);
                }
            }
        }
    }
}
