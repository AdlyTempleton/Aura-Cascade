package pixlepix.auracascade.block.tile;

import net.minecraft.block.Block;

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
        Block block = worldObj.getBlock(xCoord, yCoord + 2, zCoord);
        block.updateTick(worldObj, xCoord, yCoord + 2, zCoord, new Random());
    }
}
