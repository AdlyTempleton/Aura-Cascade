package pixlepix.auracascade.block.entity;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by pixlepix on 12/20/14.
 */
public class EntityXPFairy extends EntityFairy{
    public EntityXPFairy(World p_i1582_1_) {
        super(p_i1582_1_);
    }

    @Override
    public void onEntityUpdate() {
        if(!worldObj.isRemote && new Random().nextInt(60 * 20) == 0){
            EntityXPOrb xpOrb = new EntityXPOrb(worldObj, posX, posY, posZ, 5);
            worldObj.spawnEntityInWorld(xpOrb);
        }
    }
}
