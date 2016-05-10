package pixlepix.auracascade.block.tile;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.main.AuraUtil;

import java.util.Random;

/**
 * Created by pixlepix on 12/21/14.
 */
public class PlanterTile extends ConsumerTile {

    public static final int COST_PER_BOOST = 150;

    @Override
    public int getMaxProgress() {
        return 2;
    }

    @Override
    public int getPowerPerProgress() {
        return 50;
    }

    @Override
    public boolean validItemsNearby() {
        return true;
    }

    @Override
    public void onUsePower() {
        AuraCascade.analytics.eventDesign("consumerPlant", AuraUtil.formatLocation(this));
        BlockPos updatePos = pos.up(2);
        Block block = worldObj.getBlockState(updatePos).getBlock();
        for (int i = 0; i < 50; i++) {
            block.updateTick(worldObj, updatePos, worldObj.getBlockState(updatePos), new Random());
        }
    }
}
