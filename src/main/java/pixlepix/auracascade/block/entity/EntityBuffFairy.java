package pixlepix.auracascade.block.entity;

import net.minecraft.potion.Potion;
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
                new PotionEffect(Potion.regeneration.getId(), 2400),
                new PotionEffect(Potion.resistance.getId(), 2400),
                new PotionEffect(Potion.damageBoost.getId(), 2400),
                new PotionEffect(Potion.absorption.getId(), 2400),
                new PotionEffect(Potion.jump.getId(), 2400),
                new PotionEffect(Potion.moveSpeed.getId(), 2400)};
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
