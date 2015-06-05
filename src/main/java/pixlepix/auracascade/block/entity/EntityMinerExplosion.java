package pixlepix.auracascade.block.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by localmacaccount on 6/4/15.
 */
public class EntityMinerExplosion extends EntityLivingBase {
    public int charge;
    public long lastCharged;

    public EntityMinerExplosion(World world) {
        super(world);
        setSize(.1F, .1F);
    }


    public void bounce() {
        Random r = new Random();
        motionX = (r.nextDouble() / 10) - .05;
        motionY = (r.nextDouble() / 10) - .05;
        motionZ = (r.nextDouble() / 10) - .05;
    }


    /**
     * Gets called every tick from main Entity class
     */
    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if (!worldObj.isRemote && lastCharged + 100 < worldObj.getTotalWorldTime()) {
            setDead();

        }
        if (!worldObj.isRemote) {
            //.05^2
            if (isCollided) {
                bounce();
            }
        }
        if (worldObj.isRemote && worldObj.getTotalWorldTime() % 2 == 0) {
            this.worldObj.spawnParticle("largeexplode", posX, posY, posZ, 1.0D, 0.0D, 0.0D);
        }
    }


    /**
     * Deals damage to the entity. If its a EntityPlayer then will take damage from the armor first and then health
     * second with the reduced value. Args: damageAmount
     *
     * @param p_70665_1_
     * @param p_70665_2_
     */
    @Override
    protected void damageEntity(DamageSource p_70665_1_, float p_70665_2_) {
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        charge = nbt.getInteger("charge");
        lastCharged = nbt.getLong("lastCharged");

    }

    /**
     * Returns the item that this EntityLiving is holding, if any.
     */
    @Override
    public ItemStack getHeldItem() {
        return null;
    }

    /**
     * 0: Tool in Hand; 1-4: Armor
     *
     * @param p_71124_1_
     */
    @Override
    public ItemStack getEquipmentInSlot(int p_71124_1_) {
        return null;
    }

    /**
     * Sets the held item, or an armor slot. Slot 0 is held item. Slot 1-4 is armor. Params: Item, slot
     *
     * @param p_70062_1_
     * @param p_70062_2_
     */
    @Override
    public void setCurrentItemOrArmor(int p_70062_1_, ItemStack p_70062_2_) {

    }

    @Override
    public ItemStack[] getLastActiveItems() {
        return new ItemStack[0];
    }

    @Override
    public boolean shouldRenderInPass(int pass) {
        return false;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        nbt.setInteger("charge", charge);
        nbt.setLong("lastCharged", lastCharged);
    }
}
