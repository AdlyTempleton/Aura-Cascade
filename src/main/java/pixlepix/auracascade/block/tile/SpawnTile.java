package pixlepix.auracascade.block.tile;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by pixlepix on 12/21/14.
 */
public class SpawnTile extends ConsumerTile {
    public static int MAX_PROGRESS = 15;
    public static int POWER_PER_PROGRESS = 190;

    @Override
    public int getMaxProgress() {
        return MAX_PROGRESS;
    }

    @Override
    public int getPowerPerProgress() {
        return POWER_PER_PROGRESS;
    }

    @Override
    public boolean validItemsNearby() {
        return true;
    }

    @Override
    public void onUsePower() {
        BiomeGenBase.SpawnListEntry spawnListEntry = ((WorldServer) worldObj).spawnRandomCreature(EnumCreatureType.monster, xCoord, yCoord, zCoord);
        try {
            EntityLiving entity = (EntityLiving) spawnListEntry.entityClass.getConstructor(new Class[]{World.class}).newInstance(worldObj);
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
}
