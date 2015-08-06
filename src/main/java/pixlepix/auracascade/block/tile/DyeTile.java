package pixlepix.auracascade.block.tile;

import net.minecraft.entity.passive.EntitySheep;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.data.CoordTuple;
import pixlepix.auracascade.main.AuraUtil;

import java.util.List;
import java.util.Random;

/**
 * Created by localmacaccount on 4/5/15.
 */
public class DyeTile extends ConsumerTile {
    @Override
    public int getMaxProgress() {
        return 12;
    }

    @Override
    public int getPowerPerProgress() {
        return 50;
    }

    @Override
    public boolean validItemsNearby() {
        List<EntitySheep> nearbySheep = worldObj.getEntitiesWithinAABB(EntitySheep.class, new CoordTuple(this).getBoundingBox(2));
        return nearbySheep.size() > 0;
    }

    @Override
    public void onUsePower() {

        AuraCascade.analytics.eventDesign("consumerDye", AuraUtil.formatLocation(this));
        List<EntitySheep> nearbySheep = worldObj.getEntitiesWithinAABB(EntitySheep.class, new CoordTuple(this).getBoundingBox(2));
        if (nearbySheep.size() > 0) {
            EntitySheep sheep = nearbySheep.get(new Random().nextInt(nearbySheep.size()));
            sheep.setSheared(false);
            sheep.setFleeceColor(new Random().nextInt(16));
        }
    }
}
