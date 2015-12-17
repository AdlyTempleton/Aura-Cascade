package pixlepix.auracascade.block.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.network.PacketFairyRequestUpdate;
import pixlepix.auracascade.network.PacketFairyUpdate;

import java.util.Random;

/**
 * Created by pixlepix on 12/8/14.
 */
public class EntityFairy extends Entity {

    public EntityPlayer player;

    public double theta;
    public double dTheta;

    public double phi;
    public double dPhi;
    public double maxPhi;

    public double rho;

    public boolean reverseTheta;
    public boolean reversePhi;

    public EntityItem entityItemRender;


    public EntityFairy(World p_i1582_1_) {
        super(p_i1582_1_);
        entityItemRender = new EntityItem(worldObj);
    }

    @Override
    public boolean canRenderOnFire() {
        return false;
    }

    @Override
    protected void entityInit() {
        Random random = new Random(this.getPersistentID().hashCode());
        rho = random.nextDouble() + 5;
        phi = random.nextDouble() * 180;
        theta = random.nextDouble() * 360;

        //Period of 5-10 s
        dTheta = 1 / (random.nextDouble() * 5 + 5) + .1;
        //Period of 3-5 s
        dPhi = .3 / (3 + random.nextDouble());


    }

    public double getEffectiveRho() {
        return rho;
    }

    public Entity getOrbitingEntity() {
        return player;
    }


    @Override
    public void onUpdate() {
        super.onUpdate();
        if (player != null) {
            if (player.isDead) {
                setDead();
            }

            extinguish();
            phi += (dPhi / 2);
            theta += (dTheta / 2);

            phi %= 360;
            theta %= 360;
            if (!worldObj.isRemote && worldObj.getTotalWorldTime() % 1000 == 0) {
                ((WorldServer) worldObj).getEntityTracker().sendToAllTrackingEntity(this, AuraCascade.proxy.networkWrapper.getPacketFrom(new PacketFairyUpdate(this)));
            }

            double oldX = posX;
            double oldY = posY;
            double oldZ = posZ;
            Entity entity = getOrbitingEntity();
            setPosition(entity.posX + getEffectiveRho() * Math.sin(phi) * Math.cos(theta), entity.posY + getEffectiveRho() * Math.sin(phi) * Math.sin(theta), player.posZ + getEffectiveRho() * Math.cos(phi));

            if (entityItemRender == null) {
                entityItemRender = new EntityItem(worldObj);
            }
            entityItemRender.setPosition(posX, posY, posZ);
            entityItemRender.motionX = oldX;
            entityItemRender.motionY = oldY;
            entityItemRender.motionZ = oldZ;

            motionX = 0;
            motionY = 0;
            motionZ = 0;

        } else if (worldObj.isRemote) {
            AuraCascade.proxy.networkWrapper.sendToServer(new PacketFairyRequestUpdate(this));
        } else {
            setDead();
        }
    }

    @Override
    public AxisAlignedBB getEntityBoundingBox() {
        return new AxisAlignedBB(0, 0, 0, 0, 0, 0);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbt) {
        phi = nbt.getDouble("phi");
        dPhi = nbt.getDouble("dPhi");
        maxPhi = nbt.getDouble("maxPhi");
        dTheta = nbt.getDouble("dTheta");
        theta = nbt.getDouble("theta");
        rho = nbt.getDouble("rho");
        reversePhi = nbt.getBoolean("reversePhi");
        reverseTheta = nbt.getBoolean("reverseTheta");
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbt) {
        nbt.setDouble("phi", phi);
        nbt.setDouble("dPhi", dPhi);
        nbt.setDouble("dTheta", dTheta);
        nbt.setDouble("theta", theta);
        nbt.setDouble("maxPhi", maxPhi);
        nbt.setDouble("rho", rho);
        nbt.setBoolean("reverseTheta", reverseTheta);
        nbt.setBoolean("reversePhi", reversePhi);
    }


}
