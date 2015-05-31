package pixlepix.auracascade;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

/**
 * Created by localmacaccount on 5/30/15.
 */
public class KeyBindings {

    public static KeyBinding jumpKeyBind;

    public static void init() {
        jumpKeyBind = new KeyBinding("aura.angelJump", Keyboard.KEY_UP, "aura.keyBindCategory");
        ClientRegistry.registerKeyBinding(jumpKeyBind);
    }
}
