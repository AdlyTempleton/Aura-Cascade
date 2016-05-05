package pixlepix.auracascade.block.entity;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.network.PacketBurst;

import java.util.List;

/**
 * Created by pixlepix on 12/13/14.
 */
public class EntityDebuffFairy extends EntityFairy {
    public PotionEffect[] potionEffects;

    public EntityDebuffFairy(World p_i1582_1_) {
        super(p_i1582_1_);
        //TODO
      //  potionEffects = new PotionEffect[]{
        		/*
                new PotionEffect(Potion.poison.getId(), 200),
                new PotionEffect(Potion.confusion.getId(), 200),
                new PotionEffect(Potion.weakness.getId(), 200),
                new PotionEffect(Potion.wither.getId(), 200),
                new PotionEffect(Potion.moveSlowdown.getId(), 200),
                new PotionEffect(Potion.hunger.getId(), 200)};
                */
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if (!worldObj.isRemote && worldObj.getTotalWorldTime() % 3 == 0) {
            List<EntityMob> nearbyEntities = worldObj.getEntitiesWithinAABB(EntityMob.class, new AxisAlignedBB(posX - 2, posY - 2, posZ - 2, posX + 2, posY + 2, posZ + 2));
            if (nearbyEntities.size() > 0) {
                EntityMob entity = nearbyEntities.get(0);
                for (PotionEffect potionEffect : potionEffects) {
                    entity.addPotionEffect(potionEffect);
                }
               // AuraCascade.proxy.networkWrapper.sendToAllAround(new PacketBurst(4, entity.posX, entity.posY, entity.posZ), new NetworkRegistry.TargetPoint(entity.worldObj.provider.getDimensionId(), entity.posX, entity.posY, entity.posZ, 32));
            }
        }
    }
}
