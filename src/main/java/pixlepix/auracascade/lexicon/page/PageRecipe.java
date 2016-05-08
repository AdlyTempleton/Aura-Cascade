/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Feb 8, 2014, 2:46:36 PM (GMT)]
 */
package pixlepix.auracascade.lexicon.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pixlepix.auracascade.lexicon.GuiLexiconEntry;
import pixlepix.auracascade.lexicon.IGuiLexiconEntry;
import pixlepix.auracascade.lexicon.LexiconPage;
import pixlepix.auracascade.lexicon.LexiconRecipeMappings;
import pixlepix.auracascade.lexicon.VazkiiRenderHelper;

public class PageRecipe extends LexiconPage {

    static boolean mouseDownLastTick = false;
    int relativeMouseX, relativeMouseY;
    ItemStack tooltipStack, tooltipContainerStack;
    boolean tooltipEntry;

    public PageRecipe(String unlocalizedName) {
        super(unlocalizedName);
    }

    protected int getTextOffset(IGuiLexiconEntry gui) {
        return gui.getHeight() - 40;

    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void renderScreen(IGuiLexiconEntry gui, int mx, int my) {
        relativeMouseX = mx;
        relativeMouseY = my;

        renderRecipe(gui, mx, my);

        int width = gui.getWidth() - 30;
        int x = gui.getLeft() + 16;
        int y = gui.getTop() + getTextOffset(gui);
        if (!I18n.translateToLocal(getUnlocalizedName()).equals(getUnlocalizedName())) {
            PageText.renderText(x, y, width, getUnlocalizedName());
        }

        if (tooltipStack != null) {
            List<String> tooltipData = tooltipStack.getTooltip(Minecraft.getMinecraft().thePlayer, false);
            List<String> parsedTooltip = new ArrayList<>();
            boolean first = true;

            for (String s : tooltipData) {
                String s_ = s;
                if (!first)
                    s_ = TextFormatting.GRAY + s;
                parsedTooltip.add(s_);
                first = false;
            }

            VazkiiRenderHelper.renderTooltip(mx, my, parsedTooltip);

            int tooltipY = 8 + tooltipData.size() * 11;

            if (tooltipEntry) {
                VazkiiRenderHelper.renderTooltipOrange(mx, my + tooltipY, Arrays.asList(TextFormatting.GRAY + I18n.translateToLocal("auramisc.clickToRecipe")));
                tooltipY += 18;
            }

            if (tooltipContainerStack != null)
                VazkiiRenderHelper.renderTooltipGreen(mx, my + tooltipY, Arrays.asList(TextFormatting.AQUA + I18n.translateToLocal("auramisc.craftingContainer"), tooltipContainerStack.getDisplayName()));
        }

        tooltipStack = tooltipContainerStack = null;
        tooltipEntry = false;
        GlStateManager.disableBlend();
        mouseDownLastTick = Mouse.isButtonDown(0);
    }

    @SideOnly(Side.CLIENT)
    public void renderRecipe(IGuiLexiconEntry gui, int mx, int my) {
        // NO-OP
    }

    @SideOnly(Side.CLIENT)
    public void renderItemAtAngle(IGuiLexiconEntry gui, int angle, ItemStack stack) {
        if (stack == null || stack.getItem() == null)
            return;

        ItemStack workStack = stack.copy();

        if (workStack.getItemDamage() == Short.MAX_VALUE || workStack.getItemDamage() == -1)
            workStack.setItemDamage(0);

        angle -= 90;
        int radius = 32;
        double xPos = gui.getLeft() + Math.cos(angle * Math.PI / 180D) * radius + gui.getWidth() / 2 - 8;
        double yPos = gui.getTop() + Math.sin(angle * Math.PI / 180D) * radius + 53;

        renderItem(gui, (int) xPos, (int) yPos, workStack, false);
    }

    @SideOnly(Side.CLIENT)
    public void renderItemAtGridPos(IGuiLexiconEntry gui, int x, int y, ItemStack stack, boolean accountForContainer) {
        if (stack == null || stack.getItem() == null)
            return;
        stack = stack.copy();

        if (stack.getItemDamage() == Short.MAX_VALUE)
            stack.setItemDamage(0);

        int xPos = gui.getLeft() + x * 29 + 7 + (y == 0 && x == 3 ? 10 : 0);
        int yPos = gui.getTop() + y * 29 + 24 - (y == 0 ? 7 : 0);
        ItemStack stack1 = stack.copy();
        if (stack1.getItemDamage() == -1)
            stack1.setItemDamage(0);

        renderItem(gui, xPos, yPos, stack1, accountForContainer);
    }

    @SideOnly(Side.CLIENT)
    public void renderItem(IGuiLexiconEntry gui, int xPos, int yPos, ItemStack stack, boolean accountForContainer) {
        RenderItem render = Minecraft.getMinecraft().getRenderItem();
        boolean mouseDown = Mouse.isButtonDown(0);

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableDepth();
        render.renderItemAndEffectIntoGUI(stack, xPos, yPos);
        render.renderItemOverlayIntoGUI(Minecraft.getMinecraft().fontRendererObj, stack, xPos, yPos, "");
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popMatrix();

        if (relativeMouseX >= xPos && relativeMouseY >= yPos && relativeMouseX <= xPos + 16 && relativeMouseY <= yPos + 16) {
            tooltipStack = stack;

            LexiconRecipeMappings.EntryData data = LexiconRecipeMappings.getDataForStack(tooltipStack);
            if (data != null && (data.entry != gui.getEntry() || data.page != gui.getPageOn())) {
                tooltipEntry = true;

                if (!mouseDownLastTick && mouseDown && GuiScreen.isShiftKeyDown()) {
                    GuiLexiconEntry newGui = new GuiLexiconEntry(data.entry, (GuiScreen) gui);
                    newGui.page = data.page;
                    Minecraft.getMinecraft().displayGuiScreen(newGui);
                }
            } else tooltipEntry = false;

            if (accountForContainer) {
                ItemStack containerStack = stack.getItem().getContainerItem(stack);
                if (containerStack != null && containerStack.getItem() != null)
                    tooltipContainerStack = containerStack;
            }
        }

        GlStateManager.disableLighting();
    }

}
