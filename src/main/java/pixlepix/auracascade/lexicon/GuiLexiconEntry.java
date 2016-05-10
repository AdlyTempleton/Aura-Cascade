/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Jan 14, 2014, 6:47:06 PM (GMT)]
 */
package pixlepix.auracascade.lexicon;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import org.lwjgl.input.Mouse;
import pixlepix.auracascade.lexicon.button.GuiButtonBackWithShift;
import pixlepix.auracascade.lexicon.button.GuiButtonPage;

import java.io.IOException;
import java.util.List;


public class GuiLexiconEntry extends GuiLexicon implements IGuiLexiconEntry, IParented {

    public int page = 0;
    LexiconEntry entry;
    GuiScreen parent;
    String title;
    String subtitle;

    GuiButton leftButton, rightButton, backButton, shareButton;
    int fx = 0;
    boolean swiped = false;

    public GuiLexiconEntry(LexiconEntry entry, GuiScreen parent) {
        this.entry = entry;
        this.parent = parent;

        title = I18n.translateToLocal(entry.getUnlocalizedName()) + entry.getSuffix();
    }

    public void renderToolTip(ItemStack p_146285_1_, int p_146285_2_, int p_146285_3_) {
        List<String> list = p_146285_1_.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);

        for (int k = 0; k < list.size(); ++k) {
            if (k == 0) {
                list.set(k, p_146285_1_.getRarity().rarityColor + (String) list.get(k));
            } else {
                list.set(k, TextFormatting.GRAY + (String) list.get(k));
            }
        }

        FontRenderer font = p_146285_1_.getItem().getFontRenderer(p_146285_1_);
        drawHoveringText(list, p_146285_2_, p_146285_3_, (font == null ? fontRendererObj : font));
    }

    @Override
    public void initGui() {
        super.initGui();

        buttonList.add(backButton = new GuiButtonBackWithShift(0, left + guiWidth / 2 - 8, top + guiHeight + 2));
        buttonList.add(leftButton = new GuiButtonPage(1, left, top + guiHeight - 10, false));
        buttonList.add(rightButton = new GuiButtonPage(2, left + guiWidth - 18, top + guiHeight - 10, true));

        LexiconPage page = entry.pages.get(this.page);
        page.onOpened(this);
        updatePageButtons();
        positionTutorialArrow();
    }

    @Override
    public LexiconEntry getEntry() {
        return entry;
    }

    @Override
    public int getPageOn() {
        return page;
    }

    @Override
    void drawHeader() {
        // NO-OP
    }

    @Override
    String getTitle() {
        return String.format("%s " + TextFormatting.ITALIC + "(%s/%s)", title, page + 1, entry.pages.size());
    }

    @Override
    String getSubtitle() {
        return subtitle;
    }

    @Override
    boolean isCategoryIndex() {
        return false;
    }

    @Override
    protected void actionPerformed(GuiButton par1GuiButton) {
        LexiconPage currentPage = entry.pages.get(page);
        LexiconPage newPage;

        if (par1GuiButton.id >= BOOKMARK_START)
            handleBookmark(par1GuiButton);
        else
            switch (par1GuiButton.id) {
                case 0:
                    currentPage.onClosed(this);
                    mc.displayGuiScreen(GuiScreen.isShiftKeyDown() ? new GuiLexicon() : parent);
                    ClientTickHandler.notifyPageChange();
                    break;
                case 1:
                    currentPage.onClosed(this);
                    page--;
                    newPage = entry.pages.get(page);
                    newPage.onOpened(this);

                    ClientTickHandler.notifyPageChange();
                    break;
                case 2:
                    currentPage.onClosed(this);
                    page++;
                    newPage = entry.pages.get(page);
                    newPage.onOpened(this);

                    ClientTickHandler.notifyPageChange();
                    break;
                case 3:
                    Minecraft mc = Minecraft.getMinecraft();
                    String cmd = "/botania-share " + entry.unlocalizedName;

                    mc.ingameGUI.getChatGUI().addToSentMessages(cmd);
                    mc.thePlayer.sendChatMessage(cmd);
                    break;
            }

        updatePageButtons();
        currentPage.onActionPerformed(par1GuiButton);
    }

    public void updatePageButtons() {
        leftButton.enabled = page != 0;
        rightButton.enabled = page + 1 < entry.pages.size();
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        super.drawScreen(par1, par2, par3);

        LexiconPage page = entry.pages.get(this.page);
        page.renderScreen(this, par1, par2);
    }

    @Override
    public void updateScreen() {
        LexiconPage page = entry.pages.get(this.page);
        page.updateScreen();

        if (this.page == entry.pages.size() - 1) {
            LexiconEntry entry = tutorial.peek();
            if (entry == this.entry) {
                tutorial.poll();
                positionTutorialArrow();
                if (tutorial.isEmpty()) {
                    mc.thePlayer.addChatMessage(new TextComponentString("aura.tutorialEnded"));
                    hasTutorialArrow = false;
                }
            }
        }
    }

    @Override
    public void positionTutorialArrow() {
        LexiconEntry entry = tutorial.peek();
        if (backButton != null && entry != this.entry) {
            orientTutorialArrowWithButton(backButton);
            return;
        }

        if (rightButton != null && rightButton.enabled && rightButton.visible) {
            orientTutorialArrowWithButton(rightButton);
        }
    }

    @Override
    public int getLeft() {
        return left;
    }

    @Override
    public int getTop() {
        return top;
    }

    @Override
    public int getWidth() {
        return guiWidth;
    }

    @Override
    public int getHeight() {
        return guiHeight;
    }

    @Override
    public float getZLevel() {
        return zLevel;
    }

    @Override
    public void setParent(GuiLexicon gui) {
        parent = gui;
    }

    @Override
    protected void mouseClickMove(int x, int y, int button, long time) {
        if (button == 0 && Math.abs(x - fx) > 100 && mc.gameSettings.touchscreen && !swiped) {
            double swipe = (x - fx) / Math.max(1, (double) time);
            if (swipe < 0.5) {
                nextPage();
                swiped = true;
            } else if (swipe > 0.5) {
                prevPage();
                swiped = true;
            }
        }
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) throws IOException {
        super.mouseClicked(par1, par2, par3);

        fx = par1;
        if (par3 == 1)
            back();
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();

        if (Mouse.getEventButton() == 0)
            swiped = false;

        int w = Mouse.getEventDWheel();
        if (w < 0)
            nextPage();
        else if (w > 0)
            prevPage();
    }

    @Override
    protected void keyTyped(char par1, int par2) throws IOException {
        LexiconPage page = entry.pages.get(this.page);
        page.onKeyPressed(par1, par2);

        if (par2 == 203 || par2 == 200 || par2 == 201) // Left, Up, Page Up
            prevPage();
        else if (par2 == 205 || par2 == 208 || par2 == 209) // Right, Down Page Down
            nextPage();
        else if (par2 == 14) // Backspace
            back();
        else if (par2 == 199) { // Home
            mc.displayGuiScreen(new GuiLexicon());
            ClientTickHandler.notifyPageChange();
        }

        super.keyTyped(par1, par2);
    }

    void back() {
        if (backButton.enabled) {
            actionPerformed(backButton);
            backButton.playPressSound(mc.getSoundHandler());
        }
    }

    void nextPage() {
        if (rightButton.enabled) {
            actionPerformed(rightButton);
            rightButton.playPressSound(mc.getSoundHandler());
        }
    }

    void prevPage() {
        if (leftButton.enabled) {
            actionPerformed(leftButton);
            leftButton.playPressSound(mc.getSoundHandler());
        }
    }

    @Override
    public List<GuiButton> getButtonList() {
        return buttonList;
    }

    @Override
    public float getElapsedTicks() {
        return lastTime;
    }

    @Override
    public float getPartialTicks() {
        return partialTicks;
    }

    @Override
    public float getTickDelta() {
        return timeDelta;
    }
}
