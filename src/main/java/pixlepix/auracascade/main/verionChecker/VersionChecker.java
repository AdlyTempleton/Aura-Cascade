package pixlepix.auracascade.main.verionChecker;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import pixlepix.auracascade.main.ConstantMod;

import java.io.IOException;

/**
 * Created by Bailey on 5/11/2016.
 */
public class VersionChecker {
    public static String version = "";
    public static boolean doneChecking = false;

    public void init() {
        new ThreadVersionChecker();
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) throws IOException {
        if(!doneChecking && Minecraft.getMinecraft().thePlayer != null){
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            if(!VersionChecker.version.equals(ConstantMod.version)){
                System.out.println("VC: " + VersionChecker.version + " " + "AC V: " + ConstantMod.version);
                player.addChatComponentMessage(new TextComponentString("There is a new version of Aura Cascade Available!"));
            }
            VersionChecker.doneChecking = true;
        }
    }
}
