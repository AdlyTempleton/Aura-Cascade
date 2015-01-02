package pixlepix.auracascade.block.tile;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import pixlepix.auracascade.AuraCascade;

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
    public void onUsePower(){
        BiomeGenBase.SpawnListEntry spawnListEntry = ((WorldServer)worldObj).spawnRandomCreature(EnumCreatureType.monster, xCoord, yCoord, zCoord);
        EntityLiving entity;
        try {
            entity = (EntityLiving)spawnListEntry.entityClass.getConstructor(World.class).newInstance(worldObj);
        } catch (Exception e) {
            AuraCascade.log.error("Failed to spawn entity: {}", spawnListEntry.entityClass.getClass());
            AuraCascade.log.error("Exception thrown:", e);
            return;
        }
        entity.setPosition(xCoord + .5, yCoord + 2, zCoord + .5);
        worldObj.spawnEntityInWorld(entity);
        entity.onSpawnWithEgg(null);
    }
}
