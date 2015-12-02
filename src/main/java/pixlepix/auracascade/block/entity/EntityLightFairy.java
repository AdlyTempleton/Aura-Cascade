package pixlepix.auracascade.block.entity;

import net.minecraft.util.BlockPos;
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
        BlockPos pos = new BlockPos(this);
        int lightValue = worldObj.getLight(pos);
        if (lightValue < 10 && !worldObj.isRemote) {
            if (worldObj.isAirBlock(pos)) {
                worldObj.setBlockState(pos, BlockRegistry.getFirstBlockFromClass(FairyTorch.class).getDefaultState());
            }
        }
    }
}
