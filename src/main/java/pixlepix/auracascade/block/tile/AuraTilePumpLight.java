package pixlepix.auracascade.block.tile;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.data.BlockPos;
import pixlepix.auracascade.main.Config;

/**
 * Created by pixlepix on 12/25/14.
 */
public class AuraTilePumpLight extends AuraTilePumpBase {
    boolean hasSearched = false;

    @Override
    protected void readCustomNBT(NBTTagCompound nbt) {
        super.readCustomNBT(nbt);
        hasSearched = nbt.getBoolean("hasSearched");
    }

    @Override
    protected void writeCustomNBT(NBTTagCompound nbt) {
        super.writeCustomNBT(nbt);
        nbt.setBoolean("hasSearched", hasSearched);
    }

    @Override
    public void update() {
        super.update();
        if (pumpPower > 0) {
            hasSearched = false;
        }

        if (pumpPower == 0 && (!hasSearched || worldObj.getTotalWorldTime() % 1200 == 0)) {
            for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
                BlockPos tuple = new BlockPos(this).add(direction);
                if (consumeLightSource(tuple, Blocks.glowstone)) {

                    addFuel(Config.pumpGlowstoneDuration, Config.pumpGlowstoneSpeed);
                    break;
                }
                if (consumeLightSource(tuple, Blocks.torch)) {
                    addFuel(Config.pumpTorchDuration, Config.pumpTorchSpeed);
                    break;
                }
            }

        }
    }

    public boolean consumeLightSource(BlockPos tuple, Block block) {
        if (tuple.getBlock(worldObj) == block) {
            if (!worldObj.isRemote) {
                for (int j = 0; j < 5; j++) {
                    AuraCascade.proxy.addBlockDestroyEffects(tuple);
                }
            }

            tuple.setBlockToAir(worldObj);
            return true;
        }
        return false;
    }
}
