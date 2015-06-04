package pixlepix.auracascade.block.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * Created by localmacaccount on 6/4/15.
 */
public class EntityMinerExplosion extends Entity {
    public int charge;

    public EntityMinerExplosion(World world) {
        super(world);
    }

    @Override
    protected void entityInit() {

    }

    /**
     * Gets called every tick from main Entity class
     */
    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if (worldObj.isRemote && worldObj.getTotalWorldTime() % 10 == 0) {
            this.worldObj.spawnParticle("hugeexplosion", posX, posY, posZ, 1.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbt) {
        charge = nbt.getInteger("charge");

    }

    @Override
    public boolean shouldRenderInPass(int pass) {
        return false;
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbt) {
        nbt.setInteger("charge", charge);
    }
}
