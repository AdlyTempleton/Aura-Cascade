package pixlepix.auracascade.network;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.server.FMLServerHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import pixlepix.auracascade.block.entity.EntityFairy;
import pixlepix.auracascade.main.ClientProxy;

/**
 * Created by pixlepix on 12/8/14.
 */
public class PacketFairyUpdate implements IMessage, IMessageHandler<PacketFairyUpdate, IMessage> {

    public EntityFairy fairy;

    public PacketFairyUpdate(EntityFairy fairy){
        this.fairy = fairy;
    }

    public PacketFairyUpdate(){

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        Entity entity = ClientProxy.getWorld().getEntityByID(buf.readInt());
        if(entity instanceof EntityFairy){
            this.fairy = (EntityFairy) entity;
            fairy.theta = buf.readDouble();
            fairy.rho = buf.readDouble();
            fairy.phi = buf.readDouble();
            fairy.dPhi = buf.readDouble();
            fairy.dTheta = buf.readDouble();
            fairy.maxPhi = buf.readDouble();
            fairy.reverseTheta = buf.readBoolean();
            fairy.reversePhi = buf.readBoolean();
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(fairy.getEntityId());
        buf.writeDouble(fairy.theta);
        buf.writeDouble(fairy.rho);
        buf.writeDouble(fairy.phi);
        buf.writeDouble(fairy.dPhi);
        buf.writeDouble(fairy.dTheta);
        buf.writeDouble(fairy.maxPhi);
        buf.writeBoolean(fairy.reverseTheta);
        buf.writeBoolean(fairy.reversePhi);
    }

    @Override
    public IMessage onMessage(PacketFairyUpdate message, MessageContext ctx) {
        return null;
    }
}
