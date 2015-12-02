package pixlepix.auracascade.block.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import pixlepix.auracascade.block.BlockExplosionContainer;

import java.util.Random;

/**
 * Created by localmacaccount on 6/4/15.
 */
public class EntityMinerExplosion extends Entity {
    public int charge;
    public long lastCharged;
    public long lastExplosion;

    public EntityMinerExplosion(World world) {
        super(world);
        setSize(1F, 1F);
    }

    @Override
    protected void entityInit() {

    }


    public void bounce() {
        Random r = new Random();
        double speed = .25 * (1 + Math.log10(charge));
        motionX = (r.nextDouble() * speed) - (speed / 2);
        motionY = (r.nextDouble() * speed) - (speed / 2);
        motionZ = (r.nextDouble() * speed) - (speed / 2);
        velocityChanged = true;
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

        if (isCollided) {
            if (!worldObj.isRemote) {
                explode();
                bounce();
            } else {
                //this.worldObj.spawnParticle("hugeexplosion", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
            }
        }
        moveEntity(motionX, motionY, motionZ);
        if (worldObj.isRemote && worldObj.getTotalWorldTime() % 2 == 0) {
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
        }
    }

    public void explode() {
        int delay = (int) Math.max(0, (100 - 20 * Math.log10(charge)));
        if (lastExplosion + delay < worldObj.getTotalWorldTime()) {
            lastExplosion = worldObj.getTotalWorldTime();
            int xCoord = (int) posX;
            int yCoord = (int) posY;
            int zCoord = (int) posZ;

            boolean contained = false;

            for (int i = -2; i < 3; i++) {
                for (int j = -2; j < 3; j++) {
                    for (int k = -2; k < 3; k++) {
                        Block block = worldObj.getBlock(xCoord + i, yCoord + j, zCoord + k);
                        if (block instanceof BlockExplosionContainer) {
                            contained = true;
                            Random r = new Random();
                            int meta = worldObj.getBlockMetadata(xCoord + i, yCoord + j, zCoord + k) + 1;
                            if (r.nextDouble() > ((BlockExplosionContainer) block).getChanceToResist()) {

                                if (meta > 15) {

                                    worldObj.setBlockToAir(xCoord + i, yCoord + j, zCoord + k);
                                } else {

                                    worldObj.setBlockMetadataWithNotify(xCoord + i, yCoord + j, zCoord + k, meta, 3);
                                }
                            }
                        }
                    }
                }
            }
            if (!contained) {
                worldObj.createExplosion(this, posX, posY, posZ, 50F, true);
                setDead();
            }
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        charge = nbt.getInteger("charge");
        lastCharged = nbt.getLong("lastCharged");
        lastExplosion = nbt.getLong("lastExplosion");

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
        nbt.setLong("lastExplosion", lastExplosion);
    }
}
