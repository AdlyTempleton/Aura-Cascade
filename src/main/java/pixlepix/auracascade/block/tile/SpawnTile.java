package pixlepix.auracascade.block.tile;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;

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
        //AuraCascade.analytics.eventDesign("consumerSpawner", AuraUtil.formatLocation(this));
        Biome.SpawnListEntry spawnListEntry = ((WorldServer) worldObj).getSpawnListEntryForTypeAt(EnumCreatureType.MONSTER, getPos());
        try {
            EntityLiving entity = (EntityLiving) spawnListEntry.entityClass.getConstructor(new Class[]{World.class}).newInstance(worldObj);
            entity.setPosition(pos.getX() + .5, pos.getY() + 2, pos.getZ() + .5);
            worldObj.spawnEntityInWorld(entity);
            //TODO 1.8.8 entity.onSpawnWithEgg(null);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException  | InvocationTargetException e) {
            e.printStackTrace();
        }


    }
}
