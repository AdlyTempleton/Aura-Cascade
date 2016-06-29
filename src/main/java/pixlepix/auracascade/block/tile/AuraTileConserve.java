package pixlepix.auracascade.block.tile;

import net.minecraft.util.math.BlockPos;

/**
 * Created by pixlepix on 12/4/14.
 */
public class AuraTileConserve extends AuraTile {
    @Override
    public boolean canTransfer(BlockPos tuple) {
        return super.canTransfer(tuple) && tuple.getY() == pos.getY();
    }
}
