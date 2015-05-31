package pixlepix.auracascade.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.item.ItemAngelJump;
import pixlepix.auracascade.main.event.EventHandler;

/**
 * Created by localmacaccount on 5/30/15.
 */
public class PacketAngelJump implements IMessage {

    public EntityPlayer entityPlayer;
    public boolean up;

    public PacketAngelJump(EntityPlayer player, boolean up) {
        this.entityPlayer = player;
        this.up = up;
    }

    public PacketAngelJump() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        World world = DimensionManager.getWorld(buf.readInt());
        if (world != null) {
            entityPlayer = (EntityPlayer) world.getEntityByID(buf.readInt());
        }
        up = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(entityPlayer.worldObj.provider.dimensionId);
        buf.writeInt(entityPlayer.getEntityId());
        buf.writeBoolean(up);
    }

    public static class PacketAngelJumpHandler implements IMessageHandler<PacketAngelJump, IMessage> {

        @Override
        public IMessage onMessage(PacketAngelJump msg, MessageContext ctx) {
            if (msg.entityPlayer != null) {
                EntityPlayer player = msg.entityPlayer;
                if (EventHandler.getBaubleFromInv(ItemAngelJump.class, player) != null) {
                    for (int y = (int) (player.posY + (msg.up ? 2 : -2)); y < 255 && y > -1; y += msg.up ? 1 : -1) {
                        if (!player.worldObj.isAirBlock((int) Math.floor(player.posX), y, (int) Math.floor(player.posZ)) &&
                                player.worldObj.isAirBlock((int) Math.floor(player.posX), y + 1, (int) Math.floor(player.posZ)) &&
                                player.worldObj.isAirBlock((int) Math.floor(player.posX), y + 2, (int) Math.floor(player.posZ))) {
                            player.setPositionAndUpdate(player.posX, y + 2, player.posZ);
                            AuraCascade.proxy.networkWrapper.sendToAllAround(new PacketBurst(8, player.posX, player.posY - 0.5, player.posZ), new NetworkRegistry.TargetPoint(player.worldObj.provider.dimensionId, player.posX, player.posY, player.posZ, 32));

                        }
                    }
                }
            }
            return null;
        }
    }
}