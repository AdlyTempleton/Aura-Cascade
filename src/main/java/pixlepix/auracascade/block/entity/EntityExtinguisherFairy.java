package pixlepix.auracascade.block.entity;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;

/**
 * Created by pixlepix on 12/17/14.
 */
public class EntityExtinguisherFairy extends EntityFairy {
    public EntityExtinguisherFairy(World p_i1582_1_) {
        super(p_i1582_1_);
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if(!worldObj.isRemote){
            if(player.isBurning()){
                player.extinguish();
            }
            if(worldObj.getBlock((int)Math.floor(posX), (int)Math.floor(posY), (int)Math.floor(posZ)).getMaterial() == Material.lava){
                worldObj.setBlockToAir((int)Math.floor(posX), (int)Math.floor(posY), (int)Math.floor(posZ));
            }
        }
    }
}
