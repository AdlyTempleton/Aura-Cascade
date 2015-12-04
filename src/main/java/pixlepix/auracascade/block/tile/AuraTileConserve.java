package pixlepix.auracascade.block.tile;

import net.minecraft.util.BlockPos;
import pixlepix.auracascade.data.EnumAura;

/**
 * Created by pixlepix on 12/4/14.
 */
public class AuraTileConserve extends AuraTile {
    @Override
    public boolean canTransfer(BlockPos tuple, EnumAura aura) {
        return super.canTransfer(tuple, aura) && tuple.getY() == pos.getY();
    }
}
