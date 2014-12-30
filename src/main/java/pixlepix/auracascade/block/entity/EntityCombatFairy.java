package pixlepix.auracascade.block.entity;

import com.sun.org.apache.xerces.internal.impl.xpath.XPath;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
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
        if(!worldObj.isRemote && worldObj.getTotalWorldTime() % 3 == 0){
            List<Entity> nearbyEntities = worldObj.getEntitiesWithinAABB(EntityMob.class, AxisAlignedBB.getBoundingBox(posX -2, posY -2, posZ -2, posX +2, posY + 2, posZ + 2));
            for(Entity entity:nearbyEntities){
                entity.attackEntityFrom(DamageSource.causePlayerDamage(player), 1.5F);
                break;
            }
        }
    }
}
