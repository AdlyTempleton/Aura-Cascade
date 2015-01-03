package pixlepix.auracascade.block.entity;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by pixlepix on 12/14/14.
 */
public class EntityBuffFairy extends EntityFairy {
    public PotionEffect[] potionEffects;

    public EntityBuffFairy(World p_i1582_1_) {
        super(p_i1582_1_);
        potionEffects = new PotionEffect[]{
                new PotionEffect(Potion.regeneration.getId(), 5000),
                new PotionEffect(Potion.resistance.getId(), 5000),
                new PotionEffect(Potion.damageBoost.getId(), 5000),
                //absorbtion
                new PotionEffect(Potion.field_76444_x.getId(), 5000),
                new PotionEffect(Potion.jump.getId(), 5000),
                new PotionEffect(Potion.moveSpeed.getId(), 5000)};
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if (!worldObj.isRemote && worldObj.getTotalWorldTime() % 5000 == 0) {
            Random random = new Random();
            player.addPotionEffect(potionEffects[random.nextInt(potionEffects.length)]);
        }
    }
}
