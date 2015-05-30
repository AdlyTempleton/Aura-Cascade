package pixlepix.auracascade.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

/**
 * Created by localmacaccount on 2/2/15.
 */
public class PacketCoordinatorScroll implements IMessage {

    public EntityPlayer player;
    public String filter;
    public float scroll;

    public PacketCoordinatorScroll(EntityPlayer player, String filter, float scroll) {
        this.player = player;
        this.filter = filter;
        this.scroll = scroll;
    }

    public PacketCoordinatorScroll() {


    }

    @Override
    public void fromBytes(ByteBuf buf) {
        World world = DimensionManager.getWorld(buf.readInt());
        player = (EntityPlayer) world.getEntityByID(buf.readInt());
        scroll = buf.readFloat();
        filter = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(player.worldObj.provider.dimensionId);
        buf.writeInt(player.getEntityId());
        buf.writeFloat(scroll);
        ByteBufUtils.writeUTF8String(buf, filter);
    }

}
