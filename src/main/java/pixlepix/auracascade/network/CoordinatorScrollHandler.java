package pixlepix.auracascade.network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.inventory.Container;
import pixlepix.auracascade.gui.ContainerCoordinator;

/**
 * Created by localmacaccount on 2/2/15.
 */
public class CoordinatorScrollHandler implements IMessageHandler<PacketCoordinatorScroll, IMessage> {
    public CoordinatorScrollHandler() {

    }

    @Override
    public IMessage onMessage(PacketCoordinatorScroll message, MessageContext ctx) {
        Container container = message.player.openContainer;
        if (container instanceof ContainerCoordinator) {
            ContainerCoordinator coordinator = (ContainerCoordinator) container;
            coordinator.scrollTo(message.scroll, message.filter);
        }
        return null;
    }
}
