package pixlepix.auracascade.block.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by pixlepix on 12/14/14.
 */
public class EntitySaviorFairy extends EntityFairy {
    public EntitySaviorFairy(World p_i1582_1_) {
        super(p_i1582_1_);
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if (!worldObj.isRemote && worldObj.getTotalWorldTime() % 3 == 0 && player.getHealth() < 5) {
            List<Entity> nearbyEntities = worldObj.getEntitiesWithinAABB(EntityMob.class, AxisAlignedBB.getBoundingBox(posX - 2, posY - 2, posZ - 2, posX + 2, posY + 2, posZ + 2));
            if (nearbyEntities.size() > 0) {
                Entity entity = nearbyEntities.get(0);
                entity.attackEntityFrom(DamageSource.causePlayerDamage(player), 10F);
            }
        }
    }
}
