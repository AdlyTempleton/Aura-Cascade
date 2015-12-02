package pixlepix.auracascade.block.entity;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by pixlepix on 12/14/14.
 */
public class EntityPushFairy extends EntityFairy {
    public PotionEffect[] potionEffects;

    public EntityPushFairy(World p_i1582_1_) {
        super(p_i1582_1_);
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if (!worldObj.isRemote && worldObj.getTotalWorldTime() % 3 == 0) {
            List<EntityMob> nearbyEntities = worldObj.getEntitiesWithinAABB(EntityMob.class, new AxisAlignedBB(posX - 2, posY - 2, posZ - 2, posX + 2, posY + 2, posZ + 2));
            if (nearbyEntities.size() > 0) {
                EntityMob entity = nearbyEntities.get(0);
                entity.knockBack(this, 0, player.posX - entity.posX, player.posZ - entity.posZ);
            }
        }
    }
}
