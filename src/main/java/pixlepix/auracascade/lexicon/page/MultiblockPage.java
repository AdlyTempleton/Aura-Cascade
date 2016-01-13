package pixlepix.auracascade.lexicon.page;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import pixlepix.auracascade.lexicon.GuiButtonManualNavigation;
import pixlepix.auracascade.lexicon.GuiLexiconEntry;
import pixlepix.auracascade.lexicon.IGuiLexiconEntry;

/**
 * Created by Blusunrize, used with permission on 6/25/15.
 */
public class MultiblockPage extends PageText {
    ItemStack[][][] multiblock;
    boolean canTick = true;
    int tick = 0;
    int showLayer = -1;
    int blockCount = 0;
    int[] countPerLevel;
    int structureHeight = 0;
    int structureLength = 0;
    int structureWidth = 0;

    public MultiblockPage(String text, ItemStack[][][] multiblock) {
        super(text);
        this.multiblock = multiblock;
    }

    @Override
    public void onOpened(IGuiLexiconEntry gui) {
        int yOff = 0;
        if (multiblock != null) {
            ItemStack[][][] structure = multiblock;
            structureHeight = structure.length;
            structureWidth = 0;
            structureLength = 0;
            countPerLevel = new int[structureHeight];
            blockCount = 0;
            for (int h = 0; h < structure.length; h++) {
                if (structure[h].length - 1 > structureLength)
                    structureLength = structure[h].length - 1;
                int perLvl = 0;
                for (int l = 0; l < structure[h].length; l++) {
                    if (structure[h][l].length - 1 > structureWidth)
                        structureWidth = structure[h][l].length - 1;
                    for (ItemStack ss : structure[h][l])
                        if (ss != null)
                            perLvl++;
                }
                countPerLevel[h] = perLvl;
                blockCount += perLvl;
            }
            tick = (showLayer == -1 ? blockCount : countPerLevel[showLayer]) * 40;
            yOff = (structureHeight - 1) * 12 + structureWidth * 5 + structureLength * 5 + 16;
            yOff = Math.max(48, yOff);
            int x = gui.getLeft();
            int y = gui.getTop();
            gui.getButtonList().add(new GuiButtonManualNavigation(gui, this, 100, x + 20, y + 10 + yOff / 2 - 5, 10, 10, 4));
            if (structureHeight > 1) {
                gui.getButtonList().add(new GuiButtonManualNavigation(gui, this, 101, x + 20, y + 10 + yOff / 2 - 8 - 16, 10, 16, 3));
                gui.getButtonList().add(new GuiButtonManualNavigation(gui, this, 102, x + 20, y + 10 + yOff / 2 + 8, 10, 16, 2));
            }
        }
        super.onOpened(gui);
    }

    @Override
    public void renderScreen(IGuiLexiconEntry gui, int mx, int my) {
        int x = gui.getLeft();
        int y = gui.getTop();
        int yOffPartial = 0;
        if (multiblock != null) {
            if (canTick)
                tick++;

            ItemStack[][][] structure = multiblock;
            int prevLayers = 0;
            if (showLayer != -1)
                for (int ll = 0; ll < showLayer; ll++)
                    prevLayers += countPerLevel[ll];
            int limiter = prevLayers + (tick / 40) % ((showLayer == -1 ? blockCount : countPerLevel[showLayer]) + 4);

            int xHalf = (structureWidth * 5 - structureLength * 5);
            yOffPartial = (structureHeight - 1) * 12 + structureWidth * 5 + structureLength * 5 + 16;
            int yOffTotal = Math.max(48, yOffPartial + 16);

            GlStateManager.disableDepth();
            GlStateManager.enableRescaleNormal();
            RenderHelper.enableGUIStandardItemLighting();
            int i = 0;
            ItemStack highlighted = null;
            for (int h = 0; h < structure.length; h++)
                if (showLayer == -1 || h <= showLayer) {
                    ItemStack[][] level = structure[h];
                    for (int l = level.length - 1; l >= 0; l--) {
                        ItemStack[] row = level[l];
                        for (int w = row.length - 1; w >= 0; w--) {
                            int xx = 60 + xHalf - 10 * w + 10 * l - 7;
                            int yy = yOffPartial - 5 * w - 5 * l - 12 * h;
                            GlStateManager.translate(0, 0, 1);
                            if (row[w] != null && i <= limiter) {
                                i++;
                                Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(row[w], x + xx, y + yy);
                                if (mx >= x + xx && mx < x + xx + 16 && my >= y + yy && my < y + yy + 16)
                                    highlighted = row[w];
                            }
                        }
                    }
                }

            GlStateManager.translate(0, 0, -i);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
            GlStateManager.enableBlend();
            GlStateManager.enableDepth();

            Minecraft.getMinecraft().fontRendererObj.setUnicodeFlag(false);
            if (highlighted != null) {
                gui.renderToolTip(highlighted, mx, my);
            }
            RenderHelper.disableStandardItemLighting();
            /*
            Minecraft.getMinecraft().fontRenderer.setUnicodeFlag(true);
            if(localizedText!=null&&!localizedText.isEmpty())
                Minecraft.getMinecraft().fontRenderer.drawSplitString(localizedText, x,y+yOffTotal, 120, 0);
                */
        }

        int width = gui.getWidth() - 34;
        x = gui.getLeft() + 16;
        y = gui.getTop() + 10 + yOffPartial;

        renderText(x, y, width, getUnlocalizedName());
    }

    @Override
    public void onActionPerformed(GuiButton button) {
        if (button.id == 100)
            canTick = !canTick;
        if (button.id == 101) {
            showLayer = Math.min(showLayer + 1, structureHeight - 1);
            tick = (countPerLevel[showLayer]) * 40;
        }
        if (button.id == 102) {
            showLayer = Math.max(showLayer - 1, -1);
            tick = (showLayer == -1 ? blockCount : countPerLevel[showLayer]) * 40;
        }
        super.onActionPerformed(button);
    }
}
