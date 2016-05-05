package pixlepix.auracascade.block.tile;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.main.AuraUtil;
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
            if (worldObj.getBlockState(pos.down()).getBlock() != Blocks.WATER && worldObj.getBlockState(pos.down()).getBlock() != Blocks.FLOWING_WATER) {
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
        	//TODO FIX
           // EntityItem entityItem = new EntityItem(worldObj, pos.getX() + .5, pos.getY() + 1.5, pos.getZ() + .5, worldObj.getLootTableManager().getLootTableFromLocation(new ResourceLocation("minecraft",))
            		//OLD CODE:(new Random(), new Random().nextFloat()));
         //   entityItem.motionX = 0;
          //  entityItem.motionY = 0;
        //    entityItem.motionZ = 0;
           // worldObj.spawnEntityInWorld(entityItem);

        }
    }
}
