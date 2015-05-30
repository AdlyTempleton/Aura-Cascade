package pixlepix.auracascade.block.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by pixlepix on 12/14/14.
 */
public class EntityShooterFairy extends EntityFairy {

    public EntityArrow arrow;

    public EntityShooterFairy(World p_i1582_1_) {
        super(p_i1582_1_);
    }

    @Override
    public double getEffectiveRho() {
        return arrow != null ? .01D : super.getEffectiveRho();
    }

    @Override
    public Entity getOrbitingEntity() {
        return arrow != null ? arrow : player;
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if (arrow != null) {
            if ((arrow.motionX == 0 && arrow.motionY == 0) || arrow.isDead) {
                arrow = null;
            }
        }
        if (!worldObj.isRemote && worldObj.getTotalWorldTime() % 3 == 0) {
            List<EntityArrow> nearbyEntities = worldObj.getEntitiesWithinAABB(EntityArrow.class, AxisAlignedBB.getBoundingBox(posX - 2, posY - 2, posZ - 2, posX + 2, posY + 2, posZ + 2));
            EntityArrow entity = nearbyEntities.get(0);
            if (entity.shootingEntity == player && arrow == null) {
                arrow = entity;
                if (arrow.getDamage() < 10) {
                    arrow.setDamage(arrow.getDamage() + 10);
                    arrow.setIsCritical(true);
                }

            }
        }
    }
}
