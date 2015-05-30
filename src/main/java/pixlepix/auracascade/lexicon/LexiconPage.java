/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Jan 14, 2014, 6:17:24 PM (GMT)]
 */
package pixlepix.auracascade.lexicon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;

public abstract class LexiconPage {

    public String unlocalizedName;
    public boolean skipRegistry;

    public LexiconPage(String unlocalizedName) {
        this.unlocalizedName = unlocalizedName;
    }

    /**
     * Does the rendering for this page.
     *
     * @param gui The active GuiScreen
     * @param mx  The mouse's relative X position.
     * @param my  The mouse's relative Y position.
     */
    @SideOnly(Side.CLIENT)
    public abstract void renderScreen(IGuiLexiconEntry gui, int mx, int my);

    /**
     * Called when this page is opened, be it via initGui() or when the player changes page.
     * You can add buttons and whatever you'd do on initGui() here.
     */
    @SideOnly(Side.CLIENT)
    public void onOpened(IGuiLexiconEntry gui) {
        // NO-OP
    }

    /**
     * Called when this page is opened, be it via closing the gui or when the player changes page.
     * Make sure to dispose of anything you don't use any more, such as buttons in the gui's buttonList.
     */
    @SideOnly(Side.CLIENT)
    public void onClosed(IGuiLexiconEntry gui) {
        // NO-OP
    }

    /**
     * Called when a button is pressed, equivalent to GuiScreen.actionPerformed.
     */
    @SideOnly(Side.CLIENT)
    public void onActionPerformed(GuiButton button) {
        // NO-OP
    }

    /**
     * Called when a key is pressed.
     */
    @SuppressWarnings("EmptyMethod")
    @SideOnly(Side.CLIENT)
    public void onKeyPressed(char c, int key) {
        // NO-OP
    }

    /**
     * Called when {@link LexiconEntry#setLexiconPages(LexiconPage...)} is called.
     */
    public void onPageAdded(LexiconEntry entry, int index) {
        // NO-OP
    }

    public String getUnlocalizedName() {
        return unlocalizedName;
    }

    public LexiconPage setSkipRegistry() {
        skipRegistry = true;
        return this;
    }
}
