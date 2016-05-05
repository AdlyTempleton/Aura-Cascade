package pixlepix.auracascade.block.tile;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import pixlepix.auracascade.main.Config;

/**
 * Created by pixlepix on 12/24/14.
 */
public class AuraTilePumpProjectile extends AuraTilePumpBase {

    public void onEntityCollidedWithBlock(Entity entity) {
        if (entity instanceof EntityArrow) {
            addFuel(Config.pumpArrowDuration, Config.pumpArrowSpeed);
        }

        if (entity instanceof EntityEgg) {
            addFuel(Config.pumpEggDuration, Config.pumpEggSpeed);
        }

        if (entity instanceof EntitySnowball) {
            addFuel(Config.pumpSnowballDuration, Config.pumpSnowballSpeed);
        }
    }

    @Override
    public void update() {
        super.update();
        List<Entity> entityList = worldObj.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos.getX() - .5, pos.getY() - .5, pos.getZ() - .5, pos.getX() + 1.5, pos.getY() + 1.5, pos.getZ() + 1.5));
        for (Entity entity : entityList) {
            if (entity instanceof EntitySnowball || entity instanceof EntityEgg) {
                //Fun fact: Eggs and snowballs use the same particle code
                for (int i = 0; i < 8; ++i) {
                    this.worldObj.spawnParticle(EnumParticleTypes.SNOWBALL, entity.posX, entity.posY, entity.posZ, 0.0D, 0.0D, 0.0D);
                }
                entity.setDead();
                if (!worldObj.isRemote) {
                    onEntityCollidedWithBlock(entity);
                }
            } else if (entity instanceof EntityArrow) {
                entity.setDead();
                if (!worldObj.isRemote) {
                    onEntityCollidedWithBlock(entity);
                }
            }
        }
    }
}
