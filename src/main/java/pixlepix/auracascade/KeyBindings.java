package pixlepix.auracascade;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

/**
 * Created by localmacaccount on 5/30/15.
 */
public class KeyBindings {

    public static KeyBinding jumpKeyBind;
    public static KeyBinding jumpDownKeyBind;

    public static void init() {
        jumpKeyBind = new KeyBinding("aura.angelJump", Keyboard.KEY_UP, "aura.keyBindCategory");
        jumpDownKeyBind = new KeyBinding("aura.angelJumpDown", Keyboard.KEY_DOWN, "aura.keyBindCategory");
        ClientRegistry.registerKeyBinding(jumpKeyBind);
        ClientRegistry.registerKeyBinding(jumpDownKeyBind);
    }
}
