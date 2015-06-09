package pixlepix.auracascade.block.tile;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import pixlepix.auracascade.block.entity.EntityMinerExplosion;
import pixlepix.auracascade.data.OreDropManager;

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

        if (!worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
            if (explosion != null && !explosion.isDead) {
                explosion.charge++;
                explosion.lastCharged = worldObj.getTotalWorldTime();
            } else {
                explosion = new EntityMinerExplosion(worldObj);
                explosion.setPosition(xCoord + .5, yCoord - 1.5, zCoord + .5);
                explosion.charge = 1;
                explosion.lastCharged = worldObj.getTotalWorldTime();
                explosion.bounce();
                worldObj.spawnEntityInWorld(explosion);

            }

        } else {
            if (explosion != null && !explosion.isDead) {
                explosion.setDead();

                if (worldObj.isRemote) {
                    this.worldObj.spawnParticle("hugeexplosion", explosion.posX, explosion.posY, explosion.posZ, 0.0D, 0.0D, 0.0D);
                } else if (explosion.charge > 30) {
                    int oresSpawned = (explosion.charge * explosion.charge) / 100;
                    for (int i = 0; i < oresSpawned; i++) {
                        ItemStack stack = OreDropManager.getOreToPut();
                        EntityItem item = new EntityItem(worldObj, xCoord + .5, yCoord + 1.5, zCoord + .5, stack);
                        worldObj.spawnEntityInWorld(item);
                    }
                }
            }
        }
    }
}
