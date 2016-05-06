/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Sep 3, 2014, 9:41:47 PM (GMT)]
 */
package pixlepix.auracascade.lexicon.page;

import java.awt.Desktop;
import java.net.URI;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.text.translation.I18n;
import pixlepix.auracascade.lexicon.IGuiLexiconEntry;

public class PageGuide extends PageText {

    private final String desc;
    private final String url;
    GuiButton button;

    public PageGuide(String unlocalizedName, String desc, String url) {
        super(unlocalizedName);
        this.desc = desc;
        this.url = url;
    }

    @Override
    public void onOpened(IGuiLexiconEntry gui) {
        button = new GuiButton(101, gui.getLeft() + 30, gui.getTop() + gui.getHeight() - 50, gui.getWidth() - 60, 20, I18n.translateToLocal(desc));
        gui.getButtonList().add(button);
    }

    @Override
    public void onClosed(IGuiLexiconEntry gui) {
        gui.getButtonList().remove(button);
    }

    @Override
    public void onActionPerformed(GuiButton button) {
        if (button == this.button && Desktop.isDesktopSupported())
            try {
                Desktop.getDesktop().browse(new URI(url));
                //if (Math.random() < 0.01)
                //    Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
            } catch (Exception e) {
                e.printStackTrace();
            }
    }


}
