package pixlepix.auracascade.block.entity;

import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import pixlepix.auracascade.main.AuraUtil;

import java.util.Random;

/**
 * Created by pixlepix on 12/14/14.
 */
public class EntityBuffFairy extends EntityFairy {
    public PotionEffect[] potionEffects;

    public EntityBuffFairy(World p_i1582_1_) {
        super(p_i1582_1_);
        potionEffects = new PotionEffect[]{
                new PotionEffect(MobEffects.REGENERATION, 2400),
                new PotionEffect(MobEffects.RESISTANCE, 2400),
                new PotionEffect(MobEffects.STRENGTH, 2400),
                new PotionEffect(MobEffects.ABSORPTION, 2400),
                new PotionEffect(MobEffects.JUMP_BOOST, 2400),
                new PotionEffect(MobEffects.SPEED, 2400),
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
