package pixlepix.auracascade.lexicon;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

/**
 * Created by pixlepix on 12/27/14.
 */
public class ClientTickHandler {

    public static int ticksWithLexicaOpen = 0;
    public static int pageFlipTicks = 0;
    public static int ticksInGame = 0;

    public static void notifyPageChange() {
        if (pageFlipTicks == 0)
            pageFlipTicks = 5;
    }

    @SubscribeEvent
    public void onTick(TickEvent event) {
        if (event.type == TickEvent.Type.CLIENT) {
            GuiScreen gui = Minecraft.getMinecraft().currentScreen;
            if (gui == null || !gui.doesGuiPauseGame()) {
                ticksInGame++;
            }
            int ticksToOpen = 10;
            if (gui instanceof GuiLexicon) {
                if (ticksWithLexicaOpen < 0)
                    ticksWithLexicaOpen = 0;
                if (ticksWithLexicaOpen < ticksToOpen)
                    ticksWithLexicaOpen++;
                if (pageFlipTicks > 0)
                    pageFlipTicks--;
            } else {
                pageFlipTicks = 0;
                if (ticksWithLexicaOpen > 0) {
                    if (ticksWithLexicaOpen > ticksToOpen)
                        ticksWithLexicaOpen = ticksToOpen;
                    ticksWithLexicaOpen--;
                }
            }
        }
    }

}
