/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Oct 18, 2014, 4:00:30 PM (GMT)]
 */
package pixlepix.auracascade.lexicon.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import org.lwjgl.opengl.GL11;
import pixlepix.auracascade.lexicon.GuiLexicon;
import pixlepix.auracascade.lexicon.LexiconCategory;
import pixlepix.auracascade.lexicon.LibResources;
import pixlepix.auracascade.lexicon.VazkiiRenderHelper;

import java.util.Arrays;

public class GuiButtonCategory extends GuiButtonLexicon {

    private static final ResourceLocation fallbackResource = new ResourceLocation(LibResources.CATEGORY_INDEX);

    GuiLexicon gui;
    LexiconCategory category;
    float ticksHovered = 0F;

    public GuiButtonCategory(int id, int x, int y, GuiLexicon gui, LexiconCategory category) {
        super(id, x, y, 24, 24, "");
        this.gui = gui;
        this.category = category;
    }

    @Override
    public void drawButton(Minecraft mc, int mx, int my) {
        boolean inside = mx >= xPosition && my >= yPosition && mx < xPosition + width && my < yPosition + height;
        float time = 5F;
        if (inside)
            ticksHovered = Math.min(time, ticksHovered + gui.timeDelta);
        else ticksHovered = Math.max(0F, ticksHovered - gui.timeDelta);

        /*
        Old code related to rendering categories from a texture instead of an ItemStack
        ResourceLocation resource;
        if (category == null)
            resource = fallbackResource;
        else resource = category.getIcon();
        if (resource == null)
            resource = fallbackResource;
            

        mc.renderEngine.bindTexture(resource);
        */
        //float s = 1F / 48F;
        float defAlpha = 0.3F;
        float alpha = ticksHovered / time * (1F - defAlpha) + defAlpha;

        GlStateManager.pushMatrix();
        GlStateManager.color(2F, 2F, 2F, alpha);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        //GL11.glScalef(.5F, 3F, 3F);

        ItemStack itemStack;
        if (category == null) {
            itemStack = new ItemStack(Items.book);
        } else {
            itemStack = category.getIcon();
        }
        GlStateManager.disableLighting();
        Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(itemStack, xPosition, yPosition);

        GlStateManager.enableLighting();
        GlStateManager.popMatrix();

        if (inside)
            VazkiiRenderHelper.renderTooltipGreen(mx, my, Arrays.asList(I18n.translateToLocal(getTooltipText())));
    }

    String getTooltipText() {
        if (category == null)
            return "auramisc.lexiconIndex";
        return category.getUnlocalizedName();
    }

    public LexiconCategory getCategory() {
        return category;
    }

	public static ResourceLocation getFallbackresource() {
		return fallbackResource;
	}

}
