package pixlepix.auracascade.main;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class ClientProxy extends CommonProxy {
    public static World getWorld(){
        return Minecraft.getMinecraft().theWorld;
    }
}
