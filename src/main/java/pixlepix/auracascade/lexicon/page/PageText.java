/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Jan 14, 2014, 6:45:33 PM (GMT)]
 */
package pixlepix.auracascade.lexicon.page;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Strings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pixlepix.auracascade.lexicon.FontHelper;
import pixlepix.auracascade.lexicon.IGuiLexiconEntry;
import pixlepix.auracascade.lexicon.LexiconPage;

public class PageText extends LexiconPage {

    public PageText(String unlocalizedName) {
        super(unlocalizedName);
    }

    @SideOnly(Side.CLIENT)
    public static void renderText(int x, int y, int width, String unlocalizedText) {
        FontRenderer renderer = Minecraft.getMinecraft().fontRendererObj;
        boolean unicode = renderer.getUnicodeFlag();
        renderer.setUnicodeFlag(true);
        String text = StatCollector.translateToLocal(unlocalizedText).replaceAll("&", "\u00a7");
        String[] textEntries = text.split("<br>");

        String lastFormat = "";
        String pendingFormat = "";
        for (String s : textEntries) {
            List<String> wrappedLines = new ArrayList<>();
            String workingOn = "";

            int i = 0;
            String[] tokens = s.split(" ");
            for (String s1 : tokens) {
                boolean skipPending = false;
                String format = FontHelper.getFormatFromString(s1);

                if (!format.isEmpty() && s1.length() > 0 && s1.charAt(0) != '\u00a7') {
                    skipPending = true;
                    pendingFormat = format;
                    format = "";
                }

                if (!pendingFormat.isEmpty() && !skipPending) {
                    format = pendingFormat;
                    pendingFormat = "";
                }

                if (Strings.isNullOrEmpty(format))
                    format = lastFormat;

                if (renderer.getStringWidth(workingOn + " " + s1) >= width) {
                    wrappedLines.add(workingOn);
                    workingOn = "";
                }
                workingOn = workingOn + format + " " + s1;

                if (i == tokens.length - 1)
                    wrappedLines.add(workingOn);

                ++i;
                lastFormat = format;
            }

            for (String s1 : wrappedLines) {
                y += 10;
                renderer.drawString(s1, x, y, 0);
            }

            y += 10;
        }

        renderer.setUnicodeFlag(unicode);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderScreen(IGuiLexiconEntry gui, int mx, int my) {
        int width = gui.getWidth() - 34;
        int x = gui.getLeft() + 16;
        int y = gui.getTop() + 2;

        renderText(x, y, width, getUnlocalizedName());
    }

}
