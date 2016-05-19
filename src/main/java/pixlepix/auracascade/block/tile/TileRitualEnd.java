package pixlepix.auracascade.block.tile;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.fluids.FluidRegistry;

/**
 * Created by pixlepix on 7/8/15.
 */
public class TileRitualEnd extends TileRitualNether {
    @Override
    public byte getBiomeId() {
        return 9;
    }

    @Override
    public Block getMappedBlock(Block b) {
        if (b == Blocks.stone) {
            return Blocks.END_STONE;
        }
        if (b == Blocks.grass || b == Blocks.DIRT) {
            return Blocks.END_STONE;
        }
        if (b == Blocks.log || b == Blocks.log2 || b == Blocks.LEAVES || b == Blocks.LEAVES2) {
            return Blocks.OBSIDIAN;
        }
        if (b == Blocks.tallgrass) {
            return Blocks.air;
        }
        if (b == Blocks.gravel || b == Blocks.sand) {
            return Blocks.END_STONE;
        }
        if ((b == Blocks.water || b == Blocks.flowing_water)) {
            if (FluidRegistry.isFluidRegistered("ender")) {
                return FluidRegistry.getFluid("ender").getBlock();
            }
        }
        if (b == Blocks.snow || b == Blocks.snow_layer) {
            return Blocks.air;
        }
        return null;
    }
}
