package pixlepix.auracascade.main.event;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
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
        ClientProxy clientProxy = (ClientProxy) AuraCascade.proxy;
        for (int i = 0; i < 10; i++) {
            clientProxy.breakingIcons[i] = event.map.registerSprite(new ResourceLocation("destroy_stage_" + i));
        }
        clientProxy.blankIcon = event.map.registerSprite(new ResourceLocation("aura:blank")); // todo 1.8 recheck resourcelocations
    }
}
