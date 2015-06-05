package pixlepix.auracascade.block.entity;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by localmacaccount on 6/4/15.
 */
public class EntityMinerExplosion extends Entity {
    public int charge;
    public long lastCharged;

    public EntityMinerExplosion(World world) {
        super(world);
        setSize(1F, 1F);
    }

    @Override
    protected void entityInit() {
        
    }


    public void bounce() {
        Random r = new Random();
        motionX = (r.nextDouble() / 5) - .1;
        motionY = (r.nextDouble() / 5) - .1;
        motionZ = (r.nextDouble() / 5) - .1;
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
        moveEntity(motionX, motionY, motionZ);
        if (worldObj.isRemote && worldObj.getTotalWorldTime() % 2 == 0) {
            this.worldObj.spawnParticle("largeexplode", posX, posY, posZ, 1.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        charge = nbt.getInteger("charge");
        lastCharged = nbt.getLong("lastCharged");

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
