package pixlepix.auracascade.block.tile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.AxisAlignedBB;

import java.util.List;

/**
 * Created by pixlepix on 12/24/14.
 */
public class AuraTilePumpProjectile extends AuraTilePumpBase {

    public void onEntityCollidedWithBlock(Entity entity) {
        if(entity instanceof EntityArrow){
            addFuel(20, 1000);
            entity.setDead();
        }

        if(entity instanceof EntityEgg){
            addFuel(90, 100);
        }

        if(entity instanceof EntitySnowball){
            addFuel(10, 400);
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        List<Entity> entityList = worldObj.getEntitiesWithinAABB(EntityThrowable.class, AxisAlignedBB.getBoundingBox(xCoord - .5, yCoord - .5, zCoord - .5, xCoord + 1.5, yCoord + 1.5, zCoord + 1.5));
        for(Entity entity:entityList){
            if(entity instanceof EntitySnowball || entity instanceof EntityEgg){
                //Fun fact: Eggs and snowballs use the same particle code
                for (int i = 0; i < 8; ++i)
                {
                    this.worldObj.spawnParticle("snowballpoof", entity.posX, entity.posY, entity.posZ, 0.0D, 0.0D, 0.0D);
                }
                entity.setDead();
                if(!worldObj.isRemote) {
                    onEntityCollidedWithBlock(entity);
                }
            }
        }
    }
}
