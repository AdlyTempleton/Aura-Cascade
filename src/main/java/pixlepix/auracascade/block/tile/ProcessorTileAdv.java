package pixlepix.auracascade.block.tile;

/**
 * Created by localmacaccount on 5/29/15.
 */
public class ProcessorTileAdv extends ProcessorTile {

    @Override
    public boolean isPrismatic() {
        return true;
    }

    @Override
    public int oreMultFactor() {
        return 3;
    }

    @Override
    public int getMaxProgress() {
        return 9;
    }

    @Override
    public int getPowerPerProgress() {
        return 1000;
    }
}
