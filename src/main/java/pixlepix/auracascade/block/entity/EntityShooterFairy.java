package pixlepix.auracascade.block.entity;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.network.PacketBurst;

import java.util.List;

/**
 * Created by pixlepix on 12/14/14.
 */
public class EntityShooterFairy extends EntityFairy{

    public EntityShooterFairy(World p_i1582_1_) {
        super(p_i1582_1_);
    }

    public EntityArrow arrow;

    @Override
    public double getEffectiveRho() {
        return arrow!= null ? .01D: super.getEffectiveRho();
    }

    @Override
    public Entity getOrbitingEntity() {
        return arrow != null ? arrow : player;
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if(arrow != null){
            if((arrow.motionX == 0 && arrow.motionY == 0) || arrow.isDead ){
                arrow = null;
            }
        }
        if(!worldObj.isRemote && worldObj.getTotalWorldTime() % 3 == 0){
            List<EntityArrow> nearbyEntities = worldObj.getEntitiesWithinAABB(EntityArrow.class, AxisAlignedBB.getBoundingBox(posX - 2, posY - 2, posZ - 2, posX + 2, posY + 2, posZ + 2));
            for(EntityArrow entity:nearbyEntities){
                if(entity.shootingEntity == player&& arrow == null){
                    arrow = entity;
                    if(arrow.getDamage() < 10) {
                        arrow.setDamage(arrow.getDamage() + 10);
                        arrow.setIsCritical(true);
                    }
                }
                break;
            }
        }
    }
}
