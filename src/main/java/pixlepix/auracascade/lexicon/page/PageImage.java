/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Jan 16, 2014, 6:13:08 PM (GMT)]
 */
package pixlepix.auracascade.lexicon.page;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import pixlepix.auracascade.lexicon.IGuiLexiconEntry;
import pixlepix.auracascade.lexicon.LexiconPage;

public class PageImage extends LexiconPage {

    ResourceLocation resource;

    public PageImage(String unlocalizedName, String resource) {
        super(unlocalizedName);
        this.resource = new ResourceLocation(resource);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderScreen(IGuiLexiconEntry gui, int mx, int my) {
        TextureManager render = Minecraft.getMinecraft().renderEngine;
        render.bindTexture(resource);

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1F, 1F, 1F, 1F);
        GlStateManager.translate(gui.getLeft() + gui.getWidth() / 8, gui.getTop() + gui.getHeight() / 8, 0);
        GlStateManager.scale(.5, .5, .5);
        ((GuiScreen) gui).drawTexturedModalRect(0, 0, 0, 0, (int) (gui.getWidth() * 1.5) + 5, (int) (gui.getHeight() * 1.5) - 15);
        GlStateManager.disableBlend();

        int width = gui.getWidth() - 30;
        int height = gui.getHeight();
        int x = gui.getLeft() + 16;
        int y = gui.getTop() + height - 40;
        //TODO reimplement renderText
        //PageText.renderText(x, y, width, height, getUnlocalizedName());
    }
}
