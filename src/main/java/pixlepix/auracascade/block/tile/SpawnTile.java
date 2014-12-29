package pixlepix.auracascade.block.tile;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by pixlepix on 12/21/14.
 */
public class SpawnTile extends ConsumerTile {
    public int progress;
    public static int MAX_PROGRESS = 15;
    public static int POWER_PER_PROGRESS = 190;

    @Override
    public void readCustomNBT(NBTTagCompound nbt) {
        super.readCustomNBT(nbt);
        progress = nbt.getInteger("progress");
    }

    @Override
    public void writeCustomNBT(NBTTagCompound nbt) {
        super.writeCustomNBT(nbt);
        progress = nbt.getInteger("progress");
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if(!worldObj.isRemote){
            int nextBoostCost = POWER_PER_PROGRESS;
            while (true){
                if(progress > MAX_PROGRESS){
                    progress = 0;

                    BiomeGenBase.SpawnListEntry spawnListEntry = ((WorldServer)worldObj).spawnRandomCreature(EnumCreatureType.monster, xCoord, yCoord, zCoord);
                    try {
                        EntityLiving entity = (EntityLiving)spawnListEntry.entityClass.getConstructor(new Class[] {World.class}).newInstance(new Object[] {worldObj});
                        entity.setPosition(xCoord + .5, yCoord + 2, zCoord + .5);
                        worldObj.spawnEntityInWorld(entity);
                        entity.onSpawnWithEgg(null);
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }

                }
                if(storedPower < nextBoostCost){
                    break;
                }
                progress += 1;
                storedPower -= nextBoostCost;
                nextBoostCost *= 2;
            }
        }
    }
}
