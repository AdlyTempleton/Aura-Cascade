package pixlepix.auracascade.lexicon.page;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import pixlepix.auracascade.block.tile.CraftingCenterTile;
import pixlepix.auracascade.data.AuraQuantity;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.data.recipe.PylonRecipe;
import pixlepix.auracascade.lexicon.IGuiLexiconEntry;
import pixlepix.auracascade.lexicon.LexiconEntry;
import pixlepix.auracascade.lexicon.LexiconRecipeMappings;
import pixlepix.auracascade.lexicon.VazkiiRenderHelper;
import pixlepix.auracascade.registry.BlockRegistry;

import java.util.Arrays;

/**
 * Created by pixlepix on 12/28/14.
 */
public class PagePylon extends PageRecipe {
    private static final ResourceLocation craftingOverlay = new ResourceLocation("aura:textures/gui/pylonOverlay.png");

    PylonRecipe recipe;

    public PagePylon(String unlocalizedName, PylonRecipe recipe) {
        super(unlocalizedName);
        this.recipe = recipe;
    }

    public PagePylon(String unlocalizedName, Class item) {
        super(unlocalizedName);
        if (Item.class.isAssignableFrom(item)) {
            this.recipe = (PylonRecipe) BlockRegistry.getFirstRecipeFromItem(item);
        }
        if (Block.class.isAssignableFrom(item)) {
            this.recipe = (PylonRecipe) BlockRegistry.getFirstRecipeFromBlock(item);
        }
    }

    @Override
    public void onPageAdded(LexiconEntry entry, int index) {
        LexiconRecipeMappings.map(recipe.result, entry, index);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderRecipe(IGuiLexiconEntry gui, int mx, int my) {
        renderPylonRecipe(gui, recipe, mx, my);


        TextureManager render = Minecraft.getMinecraft().renderEngine;
        render.bindTexture(craftingOverlay);

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1F, 1F, 1F, 1F);
        ((GuiScreen) gui).drawTexturedModalRect(gui.getLeft(), gui.getTop(), 0, 0, gui.getWidth(), gui.getHeight());

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        render.bindTexture(craftingOverlay);
        GlStateManager.enableBlend();
        GlStateManager.disableBlend();
    }


    @SideOnly(Side.CLIENT)
    public void renderPylonRecipe(IGuiLexiconEntry gui, PylonRecipe recipe, int mx, int my) {
        for (int i = 0; i < 4; i++) {
            EnumFacing direction = CraftingCenterTile.pedestalRelativeLocations.get(i);

            int x = 2 + direction.getFrontOffsetX();
            int y = 2 + direction.getFrontOffsetZ();

            renderItemAtGridPos(gui, x, y, recipe.componentList.get(i).itemStack, true);

            int xPos = gui.getLeft() + x * 29 + 7 + (y == 0 && x == 3 ? 10 : 0);
            int yPos = gui.getTop() + y * 29 + 24 - (y == 0 ? 7 : 0);
            AuraQuantity quantity = recipe.componentList.get(i).auraQuantity;
            //If the 'Shift click to see recipe is present
            //We need to offset the aura display

            LexiconRecipeMappings.EntryData data = LexiconRecipeMappings.getDataForStack(recipe.componentList.get(i).itemStack);
            int yOffset = (data != null && (data.entry != gui.getEntry() || data.page != gui.getPageOn())) ? 32 : 16;
            if (mx >= xPos && my >= yPos && mx < xPos + 16 && my < yPos + 16) {
                VazkiiRenderHelper.renderTooltip(mx, my + yOffset, Arrays.asList("" + quantity.getNum() + "(" + (quantity.getType() == EnumAura.WHITE_AURA ? "Any" : quantity.getType().name) + ")"));
            }
        }
        renderItemAtGridPos(gui, 2, 0, recipe.result, false);
    }
}
