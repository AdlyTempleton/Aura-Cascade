package pixlepix.auracascade.block.entity;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
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
        if (!worldObj.isRemote) {
            if (player.isBurning()) {
                player.extinguish();
            }
            BlockPos pos = new BlockPos(this);
            Block block = worldObj.getBlockState(pos).getBlock();
            if (block.getMaterial(block.getDefaultState()) == Material.lava) {
                worldObj.setBlockToAir(pos);
            }
        }
    }
}
