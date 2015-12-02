package pixlepix.auracascade.block.tile;

/**
 * Created by localmacaccount on 4/28/15.
 */
public class AuraTilePumpCreative extends AuraTilePumpBase {

    @Override
    public void update() {
        super.update();
        addFuel(2, 10000000);
    }
}
