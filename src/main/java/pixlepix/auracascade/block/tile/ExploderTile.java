package pixlepix.auracascade.block.tile;

import net.minecraft.nbt.NBTTagCompound;
import pixlepix.auracascade.block.entity.EntityMinerExplosion;

/**
 * Created by localmacaccount on 6/4/15.
 */
public class ExploderTile extends ConsumerTile {
    public EntityMinerExplosion explosion;

    @Override
    public void readCustomNBT(NBTTagCompound nbt) {
        super.readCustomNBT(nbt);
        if (nbt.hasKey("explosion")) {
            explosion = (EntityMinerExplosion) worldObj.getEntityByID(nbt.getInteger("explosion"));
        }
    }

    @Override
    public void writeCustomNBT(NBTTagCompound nbt) {
        super.writeCustomNBT(nbt);
        if (explosion != null) {
            nbt.setInteger("explosion", explosion.getEntityId());
        }
    }

    @Override
    public int getMaxProgress() {
        return 1;
    }

    @Override
    public int getPowerPerProgress() {
        return 2500;
    }

    @Override
    public boolean validItemsNearby() {
        return true;
    }

    @Override
    public void onUsePower() {
        if (explosion != null && !explosion.isDead) {
            explosion.charge++;
        } else {
            explosion = new EntityMinerExplosion(worldObj);
            explosion.setPosition(xCoord + .5, yCoord - 5, zCoord + .5);
            explosion.charge = 1;
            worldObj.spawnEntityInWorld(explosion);
        }
    }
}
