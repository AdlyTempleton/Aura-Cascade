package pixlepix.auracascade.main.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.KeyBindings;
import pixlepix.auracascade.main.ClientProxy;
import pixlepix.auracascade.network.PacketAngelJump;

/**
 * Created by localmacaccount on 5/30/15.
 */
public class ClientEventHandler {
    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        if (KeyBindings.jumpKeyBind.isPressed()) {
            AuraCascade.proxy.networkWrapper.sendToServer(new PacketAngelJump(AuraCascade.proxy.getPlayer(), true));
        }
        if (KeyBindings.jumpDownKeyBind.isPressed()) {
            AuraCascade.proxy.networkWrapper.sendToServer(new PacketAngelJump(AuraCascade.proxy.getPlayer(), false));
        }
    }

    @SubscribeEvent
    public void registerEvent(TextureStitchEvent.Pre event) {
        if (event.map.getTextureType() == 0) {
            ClientProxy clientProxy = (ClientProxy) AuraCascade.proxy;
            for (int i = 0; i < 10; i++) {
                clientProxy.breakingIcons[i] = event.map.registerIcon("destroy_stage_" + i);
            }
            clientProxy.blankIcon = event.map.registerIcon("aura:blank");
        }
    }
}
