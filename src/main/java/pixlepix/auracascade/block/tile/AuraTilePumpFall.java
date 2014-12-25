package pixlepix.auracascade.block.tile;

import net.minecraftforge.event.entity.living.LivingFallEvent;

/**
 * Created by pixlepix on 12/25/14.
 */
public class AuraTilePumpFall extends AuraTilePumpBase {
    public void onFall(LivingFallEvent event){
        addFuel((int) (5 * event.distance), 350);
    }
}
