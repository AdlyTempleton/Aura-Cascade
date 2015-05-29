package pixlepix.auracascade.potions;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.item.ItemAngelsteelSword;

import java.util.Random;


public class PotionOrangeCurse extends Potion {
    public PotionOrangeCurse(int id) {
        super(id, true, EnumAura.ORANGE_AURA.color.getHex());
        setPotionName("Orange Curse");

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc) {
        mc.getMinecraft().renderEngine.bindTexture(mc.getMinecraft().renderEngine.getResourceLocation(1));
        mc.currentScreen.drawTexturedModelRectFromIcon(x + 8, y + 8, ItemAngelsteelSword.getStackFirstDegree(EnumAura.ORANGE_AURA).getIconIndex(), 16, 16);
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
