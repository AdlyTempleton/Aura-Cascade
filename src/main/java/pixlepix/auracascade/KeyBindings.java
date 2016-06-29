package pixlepix.auracascade;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

/**
 * Created by localmacaccount on 5/30/15.
 */
public class KeyBindings {

    public static KeyBinding jumpKeyBind;
    public static KeyBinding jumpDownKeyBind;

    public static void init() {
        jumpKeyBind = new KeyBinding("color.angelJump", Keyboard.KEY_UP, "color.keyBindCategory");
        jumpDownKeyBind = new KeyBinding("color.angelJumpDown", Keyboard.KEY_DOWN, "color.keyBindCategory");
        ClientRegistry.registerKeyBinding(jumpKeyBind);
        ClientRegistry.registerKeyBinding(jumpDownKeyBind);
    }
}
