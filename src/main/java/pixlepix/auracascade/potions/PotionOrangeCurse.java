package pixlepix.auracascade.potions;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.item.ItemAngelsteelSword;
import pixlepix.auracascade.main.ConstantMod;

import java.util.Random;


public class PotionOrangeCurse extends Potion {
    public PotionOrangeCurse(int id) {
        super(id, new ResourceLocation(ConstantMod.modId, "orange_curse"), true, EnumAura.ORANGE_AURA.color.getHex());
        setPotionName("Orange Curse");

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc) {
        mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        mc.getRenderItem().renderItemIntoGUI(ItemAngelsteelSword.getStackFirstDegree(EnumAura.ORANGE_AURA), x + 8, y + 8);
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
