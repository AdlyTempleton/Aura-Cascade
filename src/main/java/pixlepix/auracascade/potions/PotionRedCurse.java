package pixlepix.auracascade.potions;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.item.ItemAngelsteelSword;

import java.util.Random;

/**
 * Created by localmacaccount on 1/19/15.
 */
public class PotionRedCurse extends Potion {
    public PotionRedCurse(int id) {
        super(id, true, EnumAura.RED_AURA.color.getHex());
        setPotionName("Red Curse");

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc) {
        Minecraft.getMinecraft().renderEngine.bindTexture(Minecraft.getMinecraft().renderEngine.getResourceLocation(1));
        mc.currentScreen.drawTexturedModelRectFromIcon(x + 8, y + 8, ItemAngelsteelSword.getStackFirstDegree(EnumAura.RED_AURA).getIconIndex(), 16, 16);
    }

    @Override
    public boolean isReady(int p_76397_1_, int p_76397_2_) {
        return new Random().nextInt(100) == 0;
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        int x = (int) entity.posX;
        int y = (int) entity.posY;
        int z = (int) entity.posZ;

        for (int i = x - 5; i < x + 6; i++) {
            for (int j = y - 2; j < y + 3; j++) {
                for (int k = z - 5; k < z + 6; k++) {
                    if (entity.worldObj.isAirBlock(i, j, k) && Blocks.fire.canPlaceBlockAt(entity.worldObj, i, j, k)) {
                        entity.worldObj.setBlock(i, j, k, Blocks.fire);
                    }
                }
            }
        }
    }
}
