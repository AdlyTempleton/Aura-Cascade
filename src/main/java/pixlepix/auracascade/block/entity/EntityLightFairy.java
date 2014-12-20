package pixlepix.auracascade.block.entity;

import net.minecraft.block.BlockTorch;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import pixlepix.auracascade.block.FairyTorch;
import pixlepix.auracascade.registry.BlockRegistry;

/**
 * Created by pixlepix on 12/20/14.
 */
public class EntityLightFairy extends EntityFairy {
    public EntityLightFairy(World p_i1582_1_) {
        super(p_i1582_1_);
    }

    @Override
    public void onEntityUpdate() {
        int lightValue = worldObj.getBlockLightValue((int)posX, (int)posY, (int) posZ);
        if(lightValue < 10 && !worldObj.isRemote){
            if(worldObj.isAirBlock((int)posX, (int)posY, (int) posZ) ){
                worldObj.setBlock((int)posX, (int)posY, (int) posZ, BlockRegistry.getFirstBlockFromClass(FairyTorch.class));
            }
        }
    }
}
