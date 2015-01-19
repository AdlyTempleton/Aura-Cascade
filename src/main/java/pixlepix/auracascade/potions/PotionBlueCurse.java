package pixlepix.auracascade.potions;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import pixlepix.auracascade.data.EnumAura;

import java.util.List;
import java.util.Random;

/**
 * Created by localmacaccount on 1/19/15.
 */
public class PotionBlueCurse extends Potion {
    public PotionBlueCurse(int id) {
        super(id, true, EnumAura.BLUE_AURA.color.getHex());
    }

    @Override
    public boolean isReady(int p_76397_1_, int p_76397_2_) {
        return new Random().nextInt(50) == 0;
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {

        if (entity.getHealth() * 2 < entity.getMaxHealth()) {
            entity.attackEntityFrom(DamageSource.magic, 4.0F);
        }
    }
}
