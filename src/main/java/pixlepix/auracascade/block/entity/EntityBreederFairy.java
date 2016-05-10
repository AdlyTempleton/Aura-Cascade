package pixlepix.auracascade.block.entity;

import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.network.PacketBurst;

import java.util.List;

/**
 * Created by pixlepix on 12/16/14.
 */
public class EntityBreederFairy extends EntityFairy {
    public EntityBreederFairy(World p_i1582_1_) {
        super(p_i1582_1_);
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if (!worldObj.isRemote && worldObj.getTotalWorldTime() % 3 == 0) {
            List<EntityAnimal> nearbyEntities = worldObj.getEntitiesWithinAABB(EntityAnimal.class, new AxisAlignedBB(posX - 1, posY - 1, posZ - 1, posX + 1, posY + 1, posZ + 1));
            for (EntityAnimal entity : nearbyEntities) {
                if (!entity.isInLove() && entity.getGrowingAge() == 0) {
                    entity.setInLove(player);
                    AuraCascade.proxy.networkWrapper.sendToAllAround(new PacketBurst(5, entity.posX, entity.posY, entity.posZ), new NetworkRegistry.TargetPoint(entity.worldObj.provider.getDimension(), entity.posX, entity.posY, entity.posZ, 32));
                    break;

                }
            }
        }
    }
}
