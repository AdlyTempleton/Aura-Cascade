package pixlepix.auracascade.potions;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pixlepix.auracascade.data.EnumRainbowColor;
import pixlepix.auracascade.item.ItemAngelsteelSword;

import java.util.Random;

/**
 * Created by localmacaccount on 1/19/15.
 */
public class PotionRedCurse extends Potion {
    public PotionRedCurse() {
        super(true, EnumRainbowColor.RED.color.getHex());
        setPotionName("Red Curse");

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc) {
        mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        mc.getRenderItem().renderItemIntoGUI(ItemAngelsteelSword.getStackFirstDegree(EnumRainbowColor.RED), x + 8, y + 8);
    }

    @Override
    public boolean isReady(int p_76397_1_, int p_76397_2_) {
        return new Random().nextInt(100) == 0;
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        BlockPos pos = new BlockPos(entity);

        for (BlockPos pos_ : BlockPos.getAllInBox(pos.add(-5, -2, -5), pos.add(6, 3, 6))) {
            if (entity.worldObj.isAirBlock(pos_) && Blocks.FIRE.canPlaceBlockAt(entity.worldObj, pos_)) {
                entity.worldObj.setBlockState(pos_, Blocks.FIRE.getDefaultState());
            }
        }
    }
}
