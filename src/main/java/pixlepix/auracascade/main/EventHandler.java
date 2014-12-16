package pixlepix.auracascade.main;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import pixlepix.auracascade.block.entity.EntityBaitFairy;
import pixlepix.auracascade.block.entity.EntityScareFairy;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by pixlepix on 12/16/14.
 */
public class EventHandler {
    public ArrayList<EntityScareFairy> scareFairies = new ArrayList<EntityScareFairy>();

    @SubscribeEvent
    public void onLivingSpawn(LivingSpawnEvent.CheckSpawn event){
        int scareCount = 0;
        for(Entity entity: scareFairies){
            if(entity.worldObj == event.world && entity.getDistance(event.x, event.y, event.z) < 50){
                scareCount += 1;
            }
        }
        Random random = new Random();
        if(scareCount > 0){
            if(random.nextInt(25) <= scareCount){
                event.setResult(Event.Result.DENY);
            }
        }
    }



}
