package pixlepix.auracascade.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import pixlepix.auracascade.block.entity.EntityFairy;

/**
 * Created by pixlepix on 12/10/14.
 */
public class PacketFairyRequestUpdate implements IMessage, IMessageHandler<PacketFairyRequestUpdate, PacketFairyUpdate> {

    public EntityFairy entityFairy;

    public PacketFairyRequestUpdate(EntityFairy fairy) {
        this.entityFairy = fairy;
    }

    public PacketFairyRequestUpdate() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        World world = DimensionManager.getWorld(buf.readInt());
        if (world != null) {
            entityFairy = (EntityFairy) world.getEntityByID(buf.readInt());
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(entityFairy.worldObj.provider.func_177502_q());
        buf.writeInt(entityFairy.getEntityId());
    }

    @Override
    public PacketFairyUpdate onMessage(final PacketFairyRequestUpdate message, MessageContext ctx) {
        if (message.entityFairy != null) {
            return new PacketFairyUpdate(message.entityFairy);
        }
        return null;
    }
}
