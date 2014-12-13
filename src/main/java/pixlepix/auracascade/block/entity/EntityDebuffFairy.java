package pixlepix.auracascade.block.entity;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.network.PacketBurst;

import java.util.List;
import java.util.Random;

/**
 * Created by pixlepix on 12/13/14.
 */
public class EntityDebuffFairy extends EntityFairy {
    public EntityDebuffFairy(World p_i1582_1_) {
        super(p_i1582_1_);
        potionEffects = new PotionEffect[]{
                new PotionEffect(Potion.poison.getId(), 20),
                new PotionEffect(Potion.confusion.getId(), 20),
                new PotionEffect(Potion.weakness.getId(), 20),
                new PotionEffect(Potion.wither.getId(), 20),
                new PotionEffect(Potion.moveSlowdown.getId(), 20),
                new PotionEffect(Potion.hunger.getId(), 20)};
    }

    public PotionEffect[] potionEffects;

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if(!worldObj.isRemote && worldObj.getTotalWorldTime() % 3 == 0){
            List<EntityMob> nearbyEntities = worldObj.getEntitiesWithinAABB(EntityMob.class, AxisAlignedBB.getBoundingBox(posX - 2, posY - 2, posZ - 2, posX + 2, posY + 2, posZ + 2));
            for(EntityMob entity:nearbyEntities){
                for(PotionEffect potionEffect: potionEffects) {
                    entity.addPotionEffect(potionEffect);
                }
                AuraCascade.netHandler.sendToAllAround(new PacketBurst(4, entity.posX, entity.posY, entity.posZ), new NetworkRegistry.TargetPoint(entity.worldObj.provider.dimensionId, entity.posX, entity.posY, entity.posZ, 10));
                break;
            }
        }
    }
}
