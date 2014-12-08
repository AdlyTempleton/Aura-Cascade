package pixlepix.auracascade.block.entity;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import pixlepix.auracascade.AuraCascade;
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


    public EntityFairy(World p_i1582_1_) {
        super(p_i1582_1_);
    }

    @Override
    protected void entityInit() {
        Random random = new Random();
        rho = random.nextDouble() + 4;
        phi = random.nextDouble() * 180;
        theta = random.nextDouble() * 360;

        //Period of 5-10 s
        dTheta = 1 / (random.nextDouble() * 5 + 5);
        maxPhi = 45 + random.nextDouble() * 45;
        //Period of 3-5 s
        dPhi = .3 / (3 + random.nextDouble());

        reverseTheta = random.nextBoolean();
        reversePhi = random.nextBoolean();

    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if(worldObj.getTotalWorldTime() % 400 == 0){
            AuraCascade.netHandler.sendToAllAround(new PacketFairyUpdate(this), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, posX, posY, posZ, 30));
        }
        setPosition(player.posX + rho * Math.sin(phi) * Math.cos(theta), player.posY + rho * Math.sin(phi) * Math.sin(theta), player.posZ + rho * Math.cos(phi));
        phi += dPhi * (reversePhi ? -1 : 1);
        theta += dTheta * (reverseTheta ? -1 : 1);
        if(Math.abs(phi) > maxPhi){
            reversePhi = !reversePhi;
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        return null;
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
        nbt.setDouble("dPhi",dPhi);
        nbt.setDouble("dTheta",dTheta);
        nbt.setDouble("theta",theta);
        nbt.setDouble("maxPhi",maxPhi);
        nbt.setDouble("rho",rho);
        nbt.setBoolean("reverseTheta", reverseTheta);
        nbt.setBoolean("reversePhi", reversePhi);
    }




}
