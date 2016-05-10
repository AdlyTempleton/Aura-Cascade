package pixlepix.auracascade.block.entity;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by pixlepix on 12/12/14.
 */
public class EntityCombatFairy extends EntityFairy {
    public EntityCombatFairy(World p_i1582_1_) {
        super(p_i1582_1_);
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if (!worldObj.isRemote && worldObj.getTotalWorldTime() % 3 == 0) {
            List<EntityMob> nearbyEntities = worldObj.getEntitiesWithinAABB(EntityMob.class, new AxisAlignedBB(posX - 2, posY - 2, posZ - 2, posX + 2, posY + 2, posZ + 2));
            if (nearbyEntities.size() > 0) {
                nearbyEntities.get(0).attackEntityFrom(DamageSource.causePlayerDamage(player), 1.5F);
            }
        }
    }
}
