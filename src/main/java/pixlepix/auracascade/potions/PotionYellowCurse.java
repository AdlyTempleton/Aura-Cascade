package pixlepix.auracascade.potions;

import java.util.Random;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.item.ItemAngelsteelSword;
import pixlepix.auracascade.main.ConstantMod;

/**
 * Created by localmacaccount on 1/19/15.
 */
public class PotionYellowCurse extends Potion {
    public PotionYellowCurse() {
        super(new ResourceLocation(ConstantMod.modId, "yellow_curse"), true, EnumAura.YELLOW_AURA.color.getHex());
        setPotionName("Yellow Curse");

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc) {
        mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        mc.getRenderItem().renderItemIntoGUI(ItemAngelsteelSword.getStackFirstDegree(EnumAura.YELLOW_AURA), x + 8, y + 8);
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
