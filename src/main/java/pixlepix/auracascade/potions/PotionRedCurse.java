package pixlepix.auracascade.potions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import pixlepix.auracascade.data.CoordTuple;
import pixlepix.auracascade.data.EnumAura;

import java.util.Random;

/**
 * Created by localmacaccount on 1/19/15.
 */
public class PotionRedCurse extends Potion {
    public PotionRedCurse(int id) {
        super(id, true, EnumAura.RED_AURA.color.getHex());
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
