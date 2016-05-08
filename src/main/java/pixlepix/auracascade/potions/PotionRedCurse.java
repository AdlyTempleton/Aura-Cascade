package pixlepix.auracascade.potions;

import java.util.Random;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.item.ItemAngelsteelSword;

/**
 * Created by localmacaccount on 1/19/15.
 */
public class PotionRedCurse extends Potion {
    public PotionRedCurse() {
        super(true, EnumAura.RED_AURA.color.getHex());
        setPotionName("Red Curse");

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc) {
        mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        mc.getRenderItem().renderItemIntoGUI(ItemAngelsteelSword.getStackFirstDegree(EnumAura.RED_AURA), x + 8, y + 8);
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
