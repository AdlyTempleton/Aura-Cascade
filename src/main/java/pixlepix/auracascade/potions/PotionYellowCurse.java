package pixlepix.auracascade.potions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.potion.Potion;
import pixlepix.auracascade.data.EnumAura;

import java.util.Random;

/**
 * Created by localmacaccount on 1/19/15.
 */
public class PotionYellowCurse extends Potion {
    public PotionYellowCurse(int id) {
        super(id, true, EnumAura.YELLOW_AURA.color.getHex());
    }

    @Override
    public boolean isReady(int p_76397_1_, int p_76397_2_) {
        return new Random().nextInt(250) == 0;
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        EntityLightningBolt entityLightningBolt = new EntityLightningBolt(entity.worldObj, entity.posX, entity.posY, entity.posZ);
        entity.worldObj.addWeatherEffect(entityLightningBolt);
    }
}
