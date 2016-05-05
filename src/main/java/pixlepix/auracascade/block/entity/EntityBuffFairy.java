package pixlepix.auracascade.block.entity;

import java.util.Random;

import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import pixlepix.auracascade.main.AuraUtil;

/**
 * Created by pixlepix on 12/14/14.
 */
public class EntityBuffFairy extends EntityFairy {
    public PotionEffect[] potionEffects;

    public EntityBuffFairy(World p_i1582_1_) {
        super(p_i1582_1_);
        potionEffects = new PotionEffect[]{
        		//TODO TEST THIS
        		new PotionEffect(MobEffects.absorption, 2400),
                new PotionEffect(MobEffects.regeneration, 2400),
                new PotionEffect(MobEffects.resistance, 2400),
                new PotionEffect(MobEffects.damageBoost, 2400),
                new PotionEffect(MobEffects.absorption, 2400),
                new PotionEffect(MobEffects.jump, 2400),
                new PotionEffect(MobEffects.moveSpeed, 2400)
                
                };
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if (!worldObj.isRemote && worldObj.getTotalWorldTime() % 2400 == 0) {
            Random random = new Random();
            player.addPotionEffect(potionEffects[random.nextInt(potionEffects.length)]);
            AuraUtil.diamondBurst(player, "happyVillager");
        }
    }
}
