package pixlepix.auracascade.block.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by pixlepix on 12/16/14.
 */
public class EntityBaitFairy extends EntityFairy {
    public EntityBaitFairy(World p_i1582_1_) {
        super(p_i1582_1_);
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if(!worldObj.isRemote) {
            if (new Random().nextInt(3600) == 0) {
                Random random = new Random();
                int targetX = (int) (random.nextInt(20) + posX - 10);
                int targetZ = (int) (random.nextInt(20) + posZ - 10);
                int targetY = 200;
                while(worldObj.isAirBlock(targetX, targetY, targetZ)){
                    targetY --;
                }
                targetY ++;
                Entity entity = null;
                switch(random.nextInt(4)){
                    case 0:
                        entity = new EntityCow(worldObj);
                        break;

                    case 1:
                        entity = new EntityChicken(worldObj);
                        break;

                    case 2:
                        entity = new EntityPig(worldObj);
                        break;

                    case 3:
                        entity = new EntitySheep(worldObj);
                        break;
                }

                entity.setPosition(targetX, targetY, targetZ);
                worldObj.spawnEntityInWorld(entity);


            }
        }
    }
}


