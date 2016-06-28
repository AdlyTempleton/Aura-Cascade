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
        if (b == Blocks.STONE) {
            return Blocks.END_STONE;
        }
        if (b == Blocks.GRASS || b == Blocks.DIRT) {
            return Blocks.END_STONE;
        }
        if (b == Blocks.LOG || b == Blocks.LOG2 || b == Blocks.LEAVES || b == Blocks.LEAVES2) {
            return Blocks.OBSIDIAN;
        }
        if (b == Blocks.TALLGRASS) {
            return Blocks.AIR;
        }
        if (b == Blocks.GRAVEL || b == Blocks.SAND) {
            return Blocks.END_STONE;
        }
        if ((b == Blocks.WATER || b == Blocks.FLOWING_WATER)) {
            if (FluidRegistry.isFluidRegistered("ender")) {
                return FluidRegistry.getFluid("ender").getBlock();
            }
        }
        if (b == Blocks.SNOW || b == Blocks.SNOW_LAYER) {
            return Blocks.AIR;
        }
        return null;
    }
}
