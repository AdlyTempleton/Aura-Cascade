package pixlepix.auracascade.potions;

import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.item.ItemAngelsteelSword;
import pixlepix.auracascade.main.ConstantMod;

import java.util.Random;

/**
 * Created by localmacaccount on 1/19/15.
 */
public class PotionRedCurse extends Potion {
    public PotionRedCurse(int id) {
        super(id, new ResourceLocation(ConstantMod.modId, "red_curse"), true, EnumAura.RED_AURA.color.getHex());
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
        BlockPos pos = new BlockPos(entity);

        for (BlockPos pos_ : BlockPos.getAllInBox(pos.add(-5, -2, -5), pos.add(6, 3, 6))) {
            if (entity.worldObj.isAirBlock(pos_) && Blocks.fire.canPlaceBlockAt(entity.worldObj, pos_)) {
                entity.worldObj.setBlockState(pos_, Blocks.fire.getDefaultState());
            }
        }
    }
}
