package pixlepix.auracascade.block.tile;

import net.minecraftforge.event.entity.living.LivingFallEvent;
import pixlepix.auracascade.main.Config;

/**
 * Created by pixlepix on 12/25/14.
 */
public class AuraTilePumpFall extends AuraTilePumpBase {
    public void onFall(LivingFallEvent event) {
        addFuel((int) (Config.pumpFallDuration * event.getDistance()), Config.pumpFallSpeed);
    }
}
