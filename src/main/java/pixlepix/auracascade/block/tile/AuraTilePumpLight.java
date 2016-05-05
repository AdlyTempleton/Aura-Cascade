package pixlepix.auracascade.block.tile;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import pixlepix.auracascade.AuraCascade;
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
            for (EnumFacing direction : EnumFacing.VALUES) {
                BlockPos pos = getPos().offset(direction);
                if (consumeLightSource(pos, Blocks.GLOWSTONE)) {

                    addFuel(Config.pumpGlowstoneDuration, Config.pumpGlowstoneSpeed);
                    break;
                }
                if (consumeLightSource(pos, Blocks.TORCH)) {
                    addFuel(Config.pumpTorchDuration, Config.pumpTorchSpeed);
                    break;
                }
            }

        }
    }

    public boolean consumeLightSource(BlockPos pos, Block block) {
        if (worldObj.getBlockState(pos).getBlock() == block) {
            if (!worldObj.isRemote) {
                for (int j = 0; j < 5; j++) {
                    AuraCascade.proxy.addBlockDestroyEffects(pos);
                }
            }

            worldObj.setBlockToAir(pos);
            return true;
        }
        return false;
    }
}
