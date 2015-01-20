package pixlepix.auracascade.potions;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
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
public class PotionGreenCurse extends Potion {
    public PotionGreenCurse(int id) {
        super(id, true, EnumAura.GREEN_AURA.color.getHex());
        setPotionName("Green Curse");

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc) {

        mc.getMinecraft().renderEngine.bindTexture(mc.getMinecraft().renderEngine.getResourceLocation(1));
        mc.currentScreen.drawTexturedModelRectFromIcon(x + 8, y + 8, ItemAngelsteelSword.getStackFirstDegree(EnumAura.GREEN_AURA).getIconIndex(), 16, 16);
    }

    @Override
    public boolean isReady(int p_76397_1_, int p_76397_2_) {
        return new Random().nextInt(40) == 0;
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {

        entity.attackEntityFrom(DamageSource.magic, 2.0F);


        List<EntityLivingBase> entities = entity.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(entity.posX - 5, entity.posY - 5, entity.posZ - 5, entity.posX + 5, entity.posY + 5, entity.posZ + 5));
        EntityLivingBase entityLiving = entities.get(new Random().nextInt(entities.size()));
        if (entityLiving != entity) {
            if (!entityLiving.isPotionActive(this)) {
                entityLiving.addPotionEffect(new PotionEffect(this.getId(), entity.getActivePotionEffect(this).getDuration()));
            }
        }
    }
}
