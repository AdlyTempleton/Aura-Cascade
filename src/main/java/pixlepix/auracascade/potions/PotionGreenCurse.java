package pixlepix.auracascade.potions;

import java.util.List;
import java.util.Random;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
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
public class PotionGreenCurse extends Potion {
    public PotionGreenCurse() {
        super(new ResourceLocation(ConstantMod.modId, "green_curse"), true, EnumAura.GREEN_AURA.color.getHex());
        setPotionName("Green Curse");

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc) {

        mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        mc.getRenderItem().renderItemIntoGUI(ItemAngelsteelSword.getStackFirstDegree(EnumAura.GREEN_AURA), x + 8, y + 8);
    }

    @Override
    public boolean isReady(int p_76397_1_, int p_76397_2_) {
        return new Random().nextInt(40) == 0;
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {

        entity.attackEntityFrom(DamageSource.magic, 2.0F);


        List<EntityLivingBase> entities = entity.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(entity.posX - 5, entity.posY - 5, entity.posZ - 5, entity.posX + 5, entity.posY + 5, entity.posZ + 5));
        if (entities.size() > 0) {
            EntityLivingBase entityLiving = entities.get(new Random().nextInt(entities.size()));
            if (entityLiving != entity) {
                if (!entityLiving.isPotionActive(this)) {
                    entityLiving.addPotionEffect(new PotionEffect(this.field_76415_H, entity.getActivePotionEffect(this).getDuration()));
                }
            }
        }
    }
}
