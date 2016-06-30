package pixlepix.auracascade.lexicon.page;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pixlepix.auracascade.lexicon.GuiLexicon;
import pixlepix.auracascade.lexicon.IGuiLexiconEntry;

import static net.minecraft.client.Minecraft.getMinecraft;

/**
 * Created by localmacaccount on 6/2/15.
 */
public class PageTutorial extends PageText {

    GuiButton button;

    public PageTutorial(String unlocalizedName) {
        super(unlocalizedName);
    }

    @Override
    public void onOpened(IGuiLexiconEntry gui) {
        button = new GuiButton(101, gui.getLeft() + 30, gui.getTop() + gui.getHeight() - 50, gui.getWidth() - 60, 20, I18n.translateToLocal("startTutorial"));
        gui.getButtonList().add(button);
    }

    @Override
    public void onClosed(IGuiLexiconEntry gui) {
        gui.getButtonList().remove(button);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onActionPerformed(GuiButton button) {
        if (button == this.button) {
            GuiLexicon.startTutorial();
            getMinecraft().displayGuiScreen(new GuiLexicon());
            getMinecraft().thePlayer.addChatMessage(new TextComponentString("aura.tutorialStarted"));
        }
    }

}