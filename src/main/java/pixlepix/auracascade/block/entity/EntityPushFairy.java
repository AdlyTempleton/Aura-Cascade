package pixlepix.auracascade.block.entity;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.network.PacketBurst;

import java.util.List;

/**
 * Created by pixlepix on 12/14/14.
 */
public class EntityPushFairy extends EntityFairy{
    public EntityPushFairy(World p_i1582_1_) {
        super(p_i1582_1_);
    }

    public PotionEffect[] potionEffects;

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if(!worldObj.isRemote && worldObj.getTotalWorldTime() % 3 == 0){
            List<EntityMob> nearbyEntities = worldObj.getEntitiesWithinAABB(EntityMob.class, AxisAlignedBB.getBoundingBox(posX - 2, posY - 2, posZ - 2, posX + 2, posY + 2, posZ + 2));
            for(EntityMob entity:nearbyEntities){
                entity.knockBack(this, 0, player.posX - entity.posX, player.posZ - entity.posZ);
                break;
            }
        }
    }
}
