package pixlepix.auracascade.potions;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.item.ItemAngelsteelSword;

import java.util.List;
import java.util.Random;

/**
 * Created by localmacaccount on 1/19/15.
 */
public class PotionBlueCurse extends Potion {
    public PotionBlueCurse(int id) {
        super(id, true, EnumAura.BLUE_AURA.color.getHex());
        setPotionName("Blue Curse");

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc) {
        mc.getMinecraft().renderEngine.bindTexture(mc.getMinecraft().renderEngine.getResourceLocation(1));
        mc.currentScreen.drawTexturedModelRectFromIcon(x, y, ItemAngelsteelSword.getStackFirstDegree(EnumAura.BLUE_AURA).getIconIndex(), 16, 16);
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
