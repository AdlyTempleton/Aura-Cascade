package pixlepix.auracascade.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.registry.CraftingBenchRecipe;
import pixlepix.auracascade.registry.ITTinkererItem;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by localmacaccount on 4/3/15.
 */
public class ItemTransmutingSword extends Item implements ITTinkererItem {

    public HashMap<Class<? extends Entity>, Class<? extends Entity>> entityMap = new HashMap<Class<? extends Entity>, Class<? extends Entity>>();

    {
        entityMap.put(EntityCow.class, EntityMooshroom.class);
        entityMap.put(EntitySheep.class, EntityPig.class);
        entityMap.put(EntityGhast.class, EntityBlaze.class);
        entityMap.put(EntityMagmaCube.class, EntitySlime.class);
        entityMap.put(EntityOcelot.class, EntityWolf.class);
        entityMap.put(EntityCreeper.class, EntityEnderman.class);

        //Backwards

        entityMap.put(EntityMooshroom.class, EntityCow.class);
        entityMap.put(EntityPig.class, EntitySheep.class);
        entityMap.put(EntityBlaze.class, EntityGhast.class);
        entityMap.put(EntitySlime.class, EntityMagmaCube.class);
        entityMap.put(EntityWolf.class, EntityOcelot.class);
        entityMap.put(EntityEnderman.class, EntityCreeper.class);
    }

    public ItemTransmutingSword() {
        super();
        setMaxStackSize(1);
    }

    @Override
    public int getCreativeTabPriority() {
        return -50;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        if (!target.worldObj.isRemote) {
            if (entityMap.get(target.getClass()) != null && target.getHealth() > 0) {
                target.setDead();
                Class clazz = entityMap.get(target.getClass());
                Entity newEntity = null;
                try {
                    newEntity = (Entity) clazz.getConstructor(World.class).newInstance(target.worldObj);
                    newEntity.setPosition(target.posX, target.posY, target.posZ);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }

                target.worldObj.spawnEntityInWorld(newEntity);
                if (newEntity instanceof EntitySlime && target instanceof EntitySlime) {
                    ((EntitySlime) newEntity).setSlimeSize(((EntitySlime) target).getSlimeSize());
                }
                if (newEntity instanceof EntityLivingBase) {
                    ((EntityLivingBase) newEntity).setHealth(Math.min(((EntityLivingBase) newEntity).getMaxHealth(), target.getHealth()));

                }
            }

        }
        return super.hitEntity(stack, attacker, target);
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        return null;
    }

    @Override
    public String getItemName() {
        return "transmutingSword";
    }

    @Override
    public boolean shouldRegister() {
        return true;
    }

    @Override
    public boolean shouldDisplayInTab() {
        return true;
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return new CraftingBenchRecipe(new ItemStack(this), " I ", " I ", " G ", 'I', ItemMaterial.getIngot(EnumAura.VIOLET_AURA), 'G', ItemMaterial.getGem(EnumAura.VIOLET_AURA));
    }
}
