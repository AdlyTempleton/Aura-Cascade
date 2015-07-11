package pixlepix.auracascade.block.tile;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.data.CoordTuple;
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
    public void updateEntity() {
        super.updateEntity();
        if (pumpPower > 0) {
            hasSearched = false;
        }

        if (pumpPower == 0 && (!hasSearched || worldObj.getTotalWorldTime() % 1200 == 0)) {
            for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
                CoordTuple tuple = new CoordTuple(this).add(direction);
                if (consumeLightSource(tuple, Blocks.glowstone)) {
                    break;
                }
                if (consumeLightSource(tuple, Blocks.torch)) {
                    addFuel(Config.pumpTorchDuration, Config.pumpTorchSpeed);

                    tuple.setBlockToAir(worldObj);
                    break;
                }
            }

        }
    }

    public boolean consumeLightSource(CoordTuple tuple, Block block) {
        if (tuple.getBlock(worldObj) == block) {
            addFuel(Config.pumpGlowstoneDuration, Config.pumpGlowstoneSpeed);
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
