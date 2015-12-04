package pixlepix.auracascade.block.tile;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraftforge.common.FishingHooks;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.main.AuraUtil;

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
        for (BlockPos pos : BlockPos.getAllInBox(getPos().add(-1, 0, -1), getPos().add(2, 0, 2))) {
            if (worldObj.getBlockState(pos.down()).getBlock() != Blocks.water && worldObj.getBlockState(pos.down()).getBlock() != Blocks.flowing_water) {
                return false;
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
        AuraCascade.analytics.eventDesign("consumerLoot", AuraUtil.formatLocation(this));
        if (hasWater()) {

            EntityItem entityItem = new EntityItem(worldObj, pos.getX() + .5, pos.getY() + 1.5, pos.getZ() + .5, FishingHooks.getRandomFishable(new Random(), new Random().nextFloat()));
            entityItem.motionX = 0;
            entityItem.motionY = 0;
            entityItem.motionZ = 0;
            worldObj.spawnEntityInWorld(entityItem);

        }
    }
}
