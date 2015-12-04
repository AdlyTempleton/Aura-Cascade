package pixlepix.auracascade.network;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import pixlepix.auracascade.QuestManager;
import pixlepix.auracascade.data.Quest;
import pixlepix.auracascade.data.QuestData;

import java.util.ArrayList;

/**
 * Created by localmacaccount on 5/31/15.
 */
public class PacketSyncQuestData implements IMessage {
    public ArrayList<Quest> completed;
    public EntityPlayer entityPlayer;

    public PacketSyncQuestData(EntityPlayer entityPlayer) {
        this.entityPlayer = entityPlayer;
        this.completed = ((QuestData) entityPlayer.getExtendedProperties(QuestData.EXT_PROP_NAME)).completedQuests;
    }

    public PacketSyncQuestData() {
    }

    /**
     * Convert from the supplied buffer into your specific message type
     *
     * @param data
     */
    @Override
    public void fromBytes(ByteBuf data) {
        World world = DimensionManager.getWorld(data.readInt());
        if (world != null) {
            entityPlayer = (EntityPlayer) world.getEntityByID(data.readInt());
            int desiredSize = data.readByte();
            completed = new ArrayList<Quest>();
            for (int i = 0; i < desiredSize; i++) {
                completed.add(QuestManager.quests.get(data.readByte()));
            }
        }
    }

    /**
     * Deconstruct your message into the supplied byte buffer
     *
     * @param data
     */
    @Override
    public void toBytes(ByteBuf data) {
        data.writeInt(entityPlayer.worldObj.provider.getDimensionId());
        data.writeInt(entityPlayer.getEntityId());

        data.writeByte(completed.size());
        for (Quest Quest : completed) {
            data.writeByte(Quest.id);
        }
    }

    public static class PacketSyncQuestDataHandler implements IMessageHandler<PacketSyncQuestData, IMessage> {

        public PacketSyncQuestDataHandler() {
        }

        /**
         * Called when a message is received of the appropriate type. You can optionally return a reply message, or null if no reply
         * is needed.
         *
         * @param message The message
         * @param ctx
         * @return an optional return message
         */
        @Override
        public IMessage onMessage(final PacketSyncQuestData message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    if (message.entityPlayer != null) {
                        QuestData data = (QuestData) message.entityPlayer.getExtendedProperties(QuestData.EXT_PROP_NAME);
                        data.completedQuests = message.completed;
                    }
                }
            });
            return null;
        }
    }
}
