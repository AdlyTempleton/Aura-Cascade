package pixlepix.auracascade.block.entity;

import net.minecraft.world.World;
import pixlepix.auracascade.main.CommonProxy;

/**
 * Created by pixlepix on 12/16/14.
 */
public class EntityScareFairy extends EntityFairy {
    public EntityScareFairy(World p_i1582_1_) {
        super(p_i1582_1_);if(!worldObj.isRemote){
            CommonProxy.eventHandler.scareFairies.add(this);
        }
    }

    @Override
    public void setDead() {
        super.setDead();

        if(!worldObj.isRemote){
            CommonProxy.eventHandler.scareFairies.remove(this);
        }
    }
}
