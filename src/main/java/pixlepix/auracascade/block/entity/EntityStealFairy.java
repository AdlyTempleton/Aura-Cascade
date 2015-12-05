package pixlepix.auracascade.block.entity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import pixlepix.auracascade.main.AuraUtil;

import java.util.List;

/**
 * Created by pixlepix on 12/14/14.
 */
public class EntityStealFairy extends EntityFairy {

    public EntityStealFairy(World world) {
        super(world);
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if (!worldObj.isRemote && worldObj.getTotalWorldTime() % 200 == 0) {
            List<EntityPlayer> nearbyEntities = worldObj.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(posX - 2, posY - 2, posZ - 2, posX + 2, posY + 2, posZ + 2));
            for (EntityPlayer entity : nearbyEntities) {
                ItemStack stack = entity.getCurrentEquippedItem();

                if (stack != null && entity != player) {
                    EntityItem item = new EntityItem(worldObj, player.posX, player.posY, player.posZ, stack);
                    item.motionX = 0;
                    item.motionY = 0;
                    item.motionZ = 0;
                    AuraUtil.setItemDelay(item, 0);
                    worldObj.spawnEntityInWorld(item);

                    entity.inventory.setInventorySlotContents(entity.inventory.currentItem, null);
                }
            }
        }
    }
}
