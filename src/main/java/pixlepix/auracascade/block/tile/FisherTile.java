package pixlepix.auracascade.block.tile;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.FishingHooks;

import java.util.Random;

/**
 * Created by pixlepix on 7/7/15.
 */
public class FisherTile extends ConsumerTile {
    @Override
    public int getMaxProgress() {
        return 200;
    }

    @Override
    public int getPowerPerProgress() {
        return 200;
    }

    public boolean hasWater() {
        for (int x = xCoord - 1; x < xCoord + 2; x++) {

            for (int z = zCoord - 1; z < zCoord + 2; z++) {

                if (worldObj.getBlock(x, yCoord - 1, z) != Blocks.water && worldObj.getBlock(x, yCoord - 1, z) != Blocks.flowing_water) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean validItemsNearby() {
        return hasWater();
    }

    @Override
    public void onUsePower() {
        if (hasWater()) {

            EntityItem entityItem = new EntityItem(worldObj, xCoord + .5, yCoord + 1.5, zCoord + .5, FishingHooks.getRandomFishable(new Random(), new Random().nextFloat()));
            entityItem.motionX = 0;
            entityItem.motionY = 0;
            entityItem.motionZ = 0;
            worldObj.spawnEntityInWorld(entityItem);

        }
    }
}
