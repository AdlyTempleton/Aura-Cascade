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

/**
 * Created by pixlepix on 11/29/14.
 */
public class PacketBurst implements IMessage, IMessageHandler<PacketBurst, IMessage> {
    @Override
    public IMessage onMessage(PacketBurst msg, MessageContext ctx) {
        //Particle
        if(msg.world.isRemote){
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
        return null;
    }


    private World world;
    private CoordTuple from;
    private CoordTuple to;
    private String particle;

    public static final String[] particles = {"spell", "magicCrit"};

    public PacketBurst(World world, CoordTuple from, CoordTuple to, String particle){
        this.from = from;
        this.to = to;
        this.particle = particle;
    }
    public PacketBurst(){
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        from = new CoordTuple(buf.readInt(), buf.readInt(), buf.readInt());
        to = new CoordTuple(buf.readInt(), buf.readInt(), buf.readInt());
        particle = particles[buf.readByte()];
        world = ClientProxy.getWorld();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(from.getX());
        buf.writeInt(from.getY());
        buf.writeInt(from.getZ());
        buf.writeInt(to.getX());
        buf.writeInt(to.getY());
        buf.writeInt(to.getZ());
        for(int i=0; i<particles.length; i++){
            if(particles[i].equals(particle)){
                buf.writeByte(i);
                break;
            }
        }
    }


}
