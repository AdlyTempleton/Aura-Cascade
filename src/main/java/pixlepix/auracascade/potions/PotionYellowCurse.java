package pixlepix.auracascade.potions;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pixlepix.auracascade.data.EnumRainbowColor;
import pixlepix.auracascade.item.ItemAngelsteelSword;

import java.util.Random;

/**
 * Created by localmacaccount on 1/19/15.
 */
public class PotionYellowCurse extends Potion {
    public PotionYellowCurse() {
        super(true, EnumRainbowColor.YELLOW.color.getHex());
        setPotionName("Yellow Curse");

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc) {
        mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        mc.getRenderItem().renderItemIntoGUI(ItemAngelsteelSword.getStackFirstDegree(EnumRainbowColor.YELLOW), x + 8, y + 8);
    }

    @Override
    public boolean isReady(int p_76397_1_, int p_76397_2_) {
        return new Random().nextInt(250) == 0;
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        entity.worldObj.addWeatherEffect(new EntityLightningBolt(entity.worldObj, entity.posX, entity.posY, entity.posZ, false));
    }
}
