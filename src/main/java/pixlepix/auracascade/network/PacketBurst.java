package pixlepix.auracascade.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import pixlepix.auracascade.data.CoordTuple;
import pixlepix.auracascade.main.ClientProxy;
import pixlepix.auracascade.main.ParticleEffects;

import java.util.Random;

/**
 * Created by pixlepix on 11/29/14.
 */
public class PacketBurst implements IMessage, IMessageHandler<PacketBurst, IMessage> {


    @Override
    public IMessage onMessage(PacketBurst msg, MessageContext ctx) {
        //Particle
        if(msg.world.isRemote){
            if(msg.type == 0) {
                Vec3 velocity = CoordTuple.vec(msg.to.subtract(msg.from));
                velocity = velocity.normalize();
                double dist = msg.to.dist(msg.from);
                for (int count = 0; count < dist * 10; count++) {
                    double i = ((double) count) / 10D;
                    double xp = msg.from.getX() + (velocity.xCoord * i) + .5;
                    double yp = msg.from.getY() + (velocity.yCoord * i) + .5;
                    double zp = msg.from.getZ() + (velocity.zCoord * i) + .5;
                    msg.world.spawnParticle(msg.particle, xp, yp, zp, velocity.xCoord * .1, .15, velocity.zCoord * .1);

                }
            }
            if(msg.type == 1){
                for(int i=0; i<50; i++){
                    Random rand = new Random();
                    msg.world.spawnParticle("flame", msg.x, msg.y, msg.z, (rand.nextDouble() - .5D)/16, rand.nextDouble()/16, (rand.nextDouble()-.5)/16);
                }
            }
        }
        return null;
    }


    private World world;
    private CoordTuple from;
    private CoordTuple to;
    private String particle;

    public double x;
    public double y;
    public double z;
    //Type = 0: Straight light between from and to
    //Type = 1: Fire sphere
    int type = 0;

    public static final String[] particles = {"spell", "magicCrit", "crit"};

    public PacketBurst(World world, CoordTuple from, CoordTuple to, String particle){
        this.from = from;
        this.to = to;
        this.particle = particle;
        this.type = 0;
    }
    public PacketBurst(int i, double x, double y, double z){
        this.type = 1;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public PacketBurst(){
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        type = buf.readInt();
        if(type == 0) {
            from = new CoordTuple(buf.readInt(), buf.readInt(), buf.readInt());
            to = new CoordTuple(buf.readInt(), buf.readInt(), buf.readInt());
            particle = particles[buf.readByte()];
        }
        if(type == 1){

            x = buf.readDouble();
            y = buf.readDouble();
            z = buf.readDouble();
        }

        world = ClientProxy.getWorld();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(type);

        if(type == 0) {
            buf.writeInt(from.getX());
            buf.writeInt(from.getY());
            buf.writeInt(from.getZ());
            buf.writeInt(to.getX());
            buf.writeInt(to.getY());
            buf.writeInt(to.getZ());

            for (int i = 0; i < particles.length; i++) {
                if (particles[i].equals(particle)) {
                    buf.writeByte(i);
                    break;
                }
            }
        }

        if(type == 1){
            buf.writeDouble(x);
            buf.writeDouble(y);
            buf.writeDouble(z);
        }
    }


}
