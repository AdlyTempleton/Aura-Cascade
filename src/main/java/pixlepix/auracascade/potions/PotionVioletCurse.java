package pixlepix.auracascade.potions;

import java.util.List;
import java.util.Random;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.item.ItemAngelsteelSword;
import pixlepix.auracascade.main.ConstantMod;

/**
 * Created by localmacaccount on 1/19/15.
 */
public class PotionVioletCurse extends Potion {

    public PotionVioletCurse() {
        super(true, EnumAura.VIOLET_AURA.color.getHex());
        setPotionName("Violet Curse");
    }

    @Override
    public boolean isReady(int p_76397_1_, int p_76397_2_) {
        return new Random().nextInt(60) == 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc) {
        mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        mc.getRenderItem().renderItemIntoGUI(ItemAngelsteelSword.getStackFirstDegree(EnumAura.VIOLET_AURA), x + 8, y + 8);
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        List<EntityLivingBase> entities = entity.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(entity.posX - 15, entity.posY - 15, entity.posZ - 15, entity.posX + 15, entity.posY + 15, entity.posZ + 15));
        EntityLivingBase entityLiving = entities.get(new Random().nextInt(entities.size()));
        if (entityLiving != entity) {
            //XOR, XOR, blah blah blah
            double tempX = entity.posX;
            double tempY = entity.posY;
            double tempZ = entity.posZ;

            entity.setPositionAndUpdate(entityLiving.posX, entityLiving.posY, entityLiving.posZ);
            entityLiving.setPositionAndUpdate(tempX, tempY, tempZ);
        }
    }
}