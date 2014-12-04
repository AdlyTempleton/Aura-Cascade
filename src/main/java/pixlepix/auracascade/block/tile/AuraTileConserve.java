package pixlepix.auracascade.block.tile;

import pixlepix.auracascade.data.CoordTuple;
import pixlepix.auracascade.data.EnumAura;

/**
 * Created by pixlepix on 12/4/14.
 */
public class AuraTileConserve extends AuraTile {
    @Override
    public boolean canTransfer(CoordTuple tuple, EnumAura aura) {
        return super.canTransfer(tuple, aura) && tuple.getY() == yCoord;
    }
}
