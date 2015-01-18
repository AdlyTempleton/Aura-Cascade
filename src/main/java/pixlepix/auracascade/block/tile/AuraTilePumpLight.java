package pixlepix.auracascade.block.tile;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGlowstone;
import net.minecraft.block.BlockTorch;
import net.minecraft.nbt.NBTTagCompound;
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
            for (CoordTuple tuple : new CoordTuple(this).inRange(5)) {
                Block block = tuple.getBlock(worldObj);
                if (block instanceof BlockGlowstone) {
                    addFuel(Config.pumpGlowstoneDuration, Config.pumpGlowstoneSpeed);
                    if (!worldObj.isRemote) {
                        for (int j = 0; j < 5; j++) {
                            AuraCascade.proxy.addBlockDestroyEffects(tuple);
                        }
                    }

                    tuple.setBlockToAir(worldObj);
                    break;
                }
                if (block instanceof BlockTorch) {
                    addFuel(Config.pumpTorchDuration, Config.pumpTorchSpeed);
                    if (!worldObj.isRemote) {
                        for (int j = 0; j < 5; j++) {
                            AuraCascade.proxy.getEffectRenderer().addBlockDestroyEffects(tuple.getX(), tuple.getY(), tuple.getZ(), tuple.getBlock(worldObj), tuple.getMeta(worldObj));
                        }
                    }

                    tuple.setBlockToAir(worldObj);
                    break;
                }
            }

        }
    }
}
