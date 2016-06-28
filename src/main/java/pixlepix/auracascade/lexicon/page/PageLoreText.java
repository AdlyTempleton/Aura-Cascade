/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Nov 7, 2014, 11:46:21 PM (GMT)]
 */
package pixlepix.auracascade.lexicon.page;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import pixlepix.auracascade.lexicon.IGuiLexiconEntry;
import pixlepix.auracascade.lexicon.LibResources;

public class PageLoreText extends PageText {

    private static final ResourceLocation paperOverlay = new ResourceLocation(LibResources.GUI_PAPER);

    public PageLoreText(String unlocalizedName) {
        super(unlocalizedName);
    }

    @Override
    public void renderScreen(IGuiLexiconEntry gui, int mx, int my) {
        Minecraft.getMinecraft().renderEngine.bindTexture(paperOverlay);

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1F, 1F, 1F, 1F);
        ((GuiScreen) gui).drawTexturedModalRect(gui.getLeft(), gui.getTop(), 0, 0, gui.getWidth(), gui.getHeight());
        GlStateManager.disableBlend();
        super.renderScreen(gui, mx, my);
    }

}
