package pixlepix.auracascade.block.tile;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraftforge.common.util.ForgeDirection;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.data.BlockPos;
import pixlepix.auracascade.main.Config;

/**
 * Created by pixlepix on 12/25/14.
 */
public class AuraTilePumpRedstone extends AuraTilePumpBase {


    @Override
    public void update() {
        super.update();
        if (pumpPower == 0) {
            for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
                for (int i = 1; i < 16; i++) {
                    BlockPos tuple = new BlockPos(this).add(direction, i);
                    Block block = tuple.getBlock(worldObj);
                    if (block instanceof BlockRedstoneWire && tuple.getMeta(worldObj) > 0) {
                        addFuel((int) (Config.pumpRedstoneDuration * Math.pow(1.4, i)), Config.pumpRedstoneSpeed);
                        if (!worldObj.isRemote) {
                            for (int j = 0; j < 5; j++) {
                                AuraCascade.proxy.addBlockDestroyEffects(tuple);
                            }
                        }

                        tuple.setBlockToAir(worldObj);
                    } else {
                        break;
                    }
                }
            }

        }
    }
}
