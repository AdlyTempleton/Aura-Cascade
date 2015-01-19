package pixlepix.auracascade.potions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import pixlepix.auracascade.data.EnumAura;

import java.util.Random;


public class PotionOrangeCurse extends Potion {
    public PotionOrangeCurse(int id) {
        super(id, true, EnumAura.ORANGE_AURA.color.getHex());
    }

    @Override
    public boolean isReady(int p_76397_1_, int p_76397_2_) {
        return new Random().nextInt(100) == 0;
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        entity.motionY += 1;
    }
}
