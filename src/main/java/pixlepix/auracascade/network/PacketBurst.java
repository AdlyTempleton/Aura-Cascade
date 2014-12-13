package pixlepix.auracascade.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import org.lwjgl.opengl.GL11;
import pixlepix.auracascade.AuraCascade;
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
                if(msg.comp != 0D) {
                    Vec3 velocity = CoordTuple.vec(msg.to.subtract(msg.from));
                    velocity = velocity.normalize();
                    double dist = msg.to.dist(msg.from);

                    int density = (int) (10D * msg.comp);
                    for (int count = 0; count < dist * density; count++) {
                        double i = ((double) count) / density;
                        double xp = msg.from.getX() + (velocity.xCoord * i) + .5;
                        double yp = msg.from.getY() + (velocity.yCoord * i) + .5;
                        double zp = msg.from.getZ() + (velocity.zCoord * i) + .5;
                        if (msg.particle.equals("crit")) {
                            ParticleEffects.spawnParticle("crit", xp, yp, zp, velocity.xCoord * .1, .15, velocity.zCoord * .1, msg.r, msg.g, msg.b);
                        }else {
                            msg.world.spawnParticle(msg.particle, xp, yp, zp, velocity.xCoord * .1, .15, velocity.zCoord * .1);
                        }
                    }
                }
            }
            if(msg.type == 1){
                for(int i=0; i<50; i++){
                    Random rand = new Random();
                    msg.world.spawnParticle("flame", msg.x, msg.y, msg.z, (rand.nextDouble() - .5D)/16, rand.nextDouble()/16, (rand.nextDouble()-.5)/16);
                }
            }
            if(msg.type == 2){
                for(int i=0; i<50; i++){
                    Random rand = new Random();
                    msg.world.spawnParticle("explode", msg.x, msg.y, msg.z, (rand.nextDouble() - .5D)/4, rand.nextDouble()/4, (rand.nextDouble()-.5)/4);
                }
            }
            if(msg.type == 3){
                for(int i=0; i<200; i++){
                    Random rand = new Random();
                    double posX = msg.x + rand.nextDouble() * 8 - 4D;
                    double posY = msg.y + rand.nextDouble() * 8 - 4D;
                    double posZ = msg.z + rand.nextDouble() * 8 - 4D;
                    msg.world.spawnParticle("fireworksSpark", posX, posY, posZ, .1D * (msg.x - posX), .1D * (msg.y - posY), .1D * (msg.z - posZ));
                }
            }
            if(msg.type == 4){
                for(int i=0; i<200; i++){
                    Random rand = new Random();
                    double posX = msg.x + rand.nextDouble() * 8 - 4D;
                    double posY = msg.y + rand.nextDouble() * 8 - 4D;
                    double posZ = msg.z + rand.nextDouble() * 8 - 4D;
                    msg.world.spawnParticle("witchMagic", posX, posY, posZ, .1D * (msg.x - posX), .1D * (msg.y - posY), .1D * (msg.z - posZ));
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

    public double r;
    public double g;
    public double b;
    public double comp;
    //Type = 0: Straight light between from and to
    //Type = 1: Fire sphere
    int type = 0;

    public static final String[] particles = {"spell", "magicCrit", "crit", "happyVillager", "fireworksSpark"};

    public PacketBurst(World world, CoordTuple from, CoordTuple to, String particle, double r, double g, double b, double comp){
        this.from = from;
        this.to = to;
        this.particle = particle;
        this.type = 0;
        this.r = r;
        this.g = g;
        this.b = b;
        this.comp = comp;
    }
    public PacketBurst(int i, double x, double y, double z){
        this.type = i;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public PacketBurst(int i, double x, double y, double z, CoordTuple from){
        this.type = i;
        this.x = x;
        this.y = y;
        this.z = z;
        this.from = from;
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
            r = buf.readDouble();
            g = buf.readDouble();
            b = buf.readDouble();
            comp = buf.readDouble();
        }else{
            x = buf.readDouble();
            y = buf.readDouble();
            z = buf.readDouble();
        }

        world = AuraCascade.proxy.getWorld();
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
            buf.writeDouble(r);
            buf.writeDouble(g);
            buf.writeDouble(b);
            buf.writeDouble(comp);
        }else{
            buf.writeDouble(x);
            buf.writeDouble(y);
            buf.writeDouble(z);
        }
    }
}
