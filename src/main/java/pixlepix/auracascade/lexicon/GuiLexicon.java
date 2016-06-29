/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Jan 14, 2014, 6:48:05 PM (GMT)]
 */
package pixlepix.auracascade.lexicon;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import org.lwjgl.opengl.GL11;
import pixlepix.auracascade.lexicon.button.GuiButtonBookmark;
import pixlepix.auracascade.lexicon.button.GuiButtonCategory;
import pixlepix.auracascade.lexicon.button.GuiButtonInvisible;

import java.io.IOException;
import java.util.*;


public class GuiLexicon extends GuiScreen {

    public static final int BOOKMARK_START = 1337;
    public static final ResourceLocation texture = new ResourceLocation(LibResources.GUI_LEXICON);
    private static final int TUTORIAL_ARROW_WIDTH = 10;
    private static final int TUTORIAL_ARROW_HEIGHT = 12;
    public static GuiLexicon currentOpenLexicon = new GuiLexicon();
    public static ItemStack stackUsed;
    public static List<GuiLexicon> bookmarks = new ArrayList<GuiLexicon>();
    public static Queue<LexiconEntry> tutorial = new ArrayDeque<LexiconEntry>();
    public static ArrayList<LexiconEntry> tutorialMaster = new ArrayList<LexiconEntry>();
    public float lastTime = 0F;
    public float partialTicks = 0F;
    public float timeDelta = 0F;
    boolean bookmarksNeedPopulation = false;
    List<LexiconCategory> allCategories;
    boolean hasTutorialArrow;
    int tutorialArrowX, tutorialArrowY;


    String title;
    int guiWidth = 146;
    int guiHeight = 180;
    int left, top;

    public static void startTutorial() {
        tutorial.clear();
        for (LexiconEntry entry : tutorialMaster) {
            tutorial.add(entry);
        }
    }

    @Override
    public void initGui() {
        super.initGui();

        allCategories = new ArrayList<LexiconCategory>(CategoryManager.getAllCategories());
        Collections.sort(allCategories);

        lastTime = ClientTickHandler.ticksInGame;

        currentOpenLexicon = this;
        title = stackUsed.getDisplayName();

        left = width / 2 - guiWidth / 2;
        top = height / 2 - guiHeight / 2;

        buttonList.clear();
        if (isIndex()) {
            int x = 18;
            for (int i = 0; i < 12; i++) {
                int y = 16 + i * 12;
                buttonList.add(new GuiButtonInvisible(i, left + x, top + y, 110, 10, ""));
            }
            populateIndex();
        } else if (isCategoryIndex()) {
            int categories = allCategories.size();
            for (int i = 0; i < categories + 1; i++) {
                LexiconCategory category;
                category = i >= categories ? null : allCategories.get(i);
                int x = i % 4;
                int y = i / 4;

                int size = 27;
                GuiButtonCategory button = new GuiButtonCategory(i, left + 18 + x * size, top + 35 + y * size, this, category);
                buttonList.add(button);
            }
        }
        populateBookmarks();


        putTutorialArrow();
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        float time = ClientTickHandler.ticksInGame + par3;
        timeDelta = time - lastTime;
        lastTime = time;
        partialTicks = par3;

        GlStateManager.color(1F, 1F, 1F, 1F);
        mc.renderEngine.bindTexture(texture);
        drawTexturedModalRect(left, top, 0, 0, guiWidth, guiHeight);

        drawBookmark(left + guiWidth / 2, top - getTitleHeight(), getTitle(), true);
        String subtitle = getSubtitle();
        if (subtitle != null) {
            GlStateManager.scale(0.5F, 0.5F, 1F);
            drawCenteredString(fontRendererObj, subtitle, left * 2 + guiWidth, (top - getTitleHeight() + 11) * 2, 0x00FF00);
            GlStateManager.scale(2F, 2F, 1F);
        }

        drawHeader();

        if (bookmarksNeedPopulation) {
            populateBookmarks();
            bookmarksNeedPopulation = false;
        }

        super.drawScreen(par1, par2, par3);

        if (hasTutorialArrow) {
            mc.renderEngine.bindTexture(texture);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GlStateManager.color(1F, 1F, 1F, 0.7F + (float) (Math.sin((ClientTickHandler.ticksInGame + par3) * 0.3F) + 1) * 0.15F);
            drawTexturedModalRect(tutorialArrowX, tutorialArrowY, 20, 200, TUTORIAL_ARROW_WIDTH, TUTORIAL_ARROW_HEIGHT);
            GlStateManager.disableBlend();
        }
    }

    public void drawBookmark(int x, int y, String s, boolean drawLeft) {
        // This function is called from the buttons so I can't use fontRendererObj
        FontRenderer font = Minecraft.getMinecraft().fontRendererObj;
        boolean unicode = font.getUnicodeFlag();
        font.setUnicodeFlag(true);
        int l = font.getStringWidth(s);
        int fontOff = 0;

        if (!drawLeft) {
            x += l / 2;
            fontOff = 2;
        }

        Minecraft.getMinecraft().renderEngine.bindTexture(texture);

        GlStateManager.color(1F, 1F, 1F, 1F);
        drawTexturedModalRect(x + l / 2 + 3, y - 1, 54, 180, 6, 11);
        if (drawLeft)
            drawTexturedModalRect(x - l / 2 - 9, y - 1, 61, 180, 6, 11);
        for (int i = 0; i < l + 6; i++)
            drawTexturedModalRect(x - l / 2 - 3 + i, y - 1, 60, 180, 1, 11);

        font.drawString(s, x - l / 2 + fontOff, y, 0x111111, false);
        font.setUnicodeFlag(unicode);
    }

    void drawHeader() {
        boolean unicode = fontRendererObj.getUnicodeFlag();
        fontRendererObj.setUnicodeFlag(true);
        fontRendererObj.drawSplitString(I18n.translateToLocal("color.gui.lexicon.header"), left + 18, top + 12, 110, 0);
        fontRendererObj.setUnicodeFlag(unicode);
    }

    @Override
    protected void actionPerformed(GuiButton par1GuiButton) {
        if (par1GuiButton.id >= BOOKMARK_START)
            handleBookmark(par1GuiButton);
        else if (par1GuiButton instanceof GuiButtonCategory) {
            LexiconCategory category = ((GuiButtonCategory) par1GuiButton).getCategory();

            mc.displayGuiScreen(new GuiLexiconIndex(category));
            ClientTickHandler.notifyPageChange();
        }
    }

    public void handleBookmark(GuiButton par1GuiButton) {
        int i = par1GuiButton.id - BOOKMARK_START;
        if (i == bookmarks.size()) {
            if (!bookmarks.contains(this))
                bookmarks.add(this);
        } else {
            if (isShiftKeyDown())
                bookmarks.remove(i);
            else {
                GuiLexicon bookmark = bookmarks.get(i);
                Minecraft.getMinecraft().displayGuiScreen(bookmark);
                if (!bookmark.getTitle().equals(getTitle())) {
                    if (bookmark instanceof IParented)
                        ((IParented) bookmark).setParent(this);
                    ClientTickHandler.notifyPageChange();
                }
            }
        }

        bookmarksNeedPopulation = true;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public int bookmarkWidth(String b) {
        boolean unicode = fontRendererObj.getUnicodeFlag();
        fontRendererObj.setUnicodeFlag(true);
        int width = fontRendererObj.getStringWidth(b) + 15;
        fontRendererObj.setUnicodeFlag(unicode);
        return width;
    }

    String getTitle() {
        return title;
    }

    String getSubtitle() {
        return null;
    }

    int getTitleHeight() {
        return getSubtitle() == null ? 12 : 16;
    }

    boolean isIndex() {
        return false;
    }

    boolean isCategoryIndex() {
        return true;
    }

    void populateIndex() {
        List<LexiconCategory> categoryList = CategoryManager.getAllCategories();
        int shift = 2;
        for (int i = shift; i < 12; i++) {
            int i_ = i - shift;
            GuiButtonInvisible button = (GuiButtonInvisible) buttonList.get(i);
            LexiconCategory category = i_ >= categoryList.size() ? null : categoryList.get(i_);
            if (category != null)
                button.displayString = I18n.translateToLocal(category.getUnlocalizedName());
            else {
                button.displayString = I18n.translateToLocal("auramisc.lexiconIndex");
                break;
            }
        }
    }

    void populateBookmarks() {
        List<GuiButton> remove = new ArrayList<GuiButton>();
        List<GuiButton> buttons = buttonList;
        for (GuiButton button : buttons)
            if (button.id >= BOOKMARK_START)
                remove.add(button);
        buttonList.removeAll(remove);

        int len = bookmarks.size();
        boolean thisExists = false;
        for (GuiLexicon lex : bookmarks)
            if (lex.getTitle().equals(getTitle()))
                thisExists = true;

        boolean addEnabled = len < 8 && this instanceof IParented && !thisExists;
        for (int i = 0; i < len + (addEnabled ? 1 : 0); i++) {
            boolean isAdd = i == bookmarks.size();
            GuiLexicon gui = isAdd ? null : bookmarks.get(i);
            buttonList.add(new GuiButtonBookmark(BOOKMARK_START + i, left + 138, top + 18 + 14 * i, gui == null ? this : gui, gui == null ? "+" : gui.getTitle()));
        }
    }

    boolean closeScreenOnInvKey() {
        return true;
    }

    @Override
    protected void keyTyped(char par1, int par2) throws IOException {
        if (closeScreenOnInvKey() && mc.gameSettings.keyBindInventory.getKeyCode() == par2) {
            mc.displayGuiScreen(null);
            mc.setIngameFocus();
        }

        super.keyTyped(par1, par2);
    }

    public final void putTutorialArrow() {
        hasTutorialArrow = !tutorial.isEmpty();
        if (hasTutorialArrow)
            positionTutorialArrow();
    }

    public void positionTutorialArrow() {
        LexiconEntry entry = tutorial.peek();
        LexiconCategory category = entry.category;

        List<GuiButton> buttons = buttonList;
        for (GuiButton button : buttons)
            if (button instanceof GuiButtonCategory) {
                GuiButtonCategory catButton = (GuiButtonCategory) button;
                if (catButton.getCategory() == category) {
                    orientTutorialArrowWithButton(button);
                    break;
                }
            }
    }

    public void orientTutorialArrowWithButton(GuiButton button) {
        if (button != null) {
            tutorialArrowX = button.xPosition - TUTORIAL_ARROW_WIDTH;
            tutorialArrowY = button.yPosition - TUTORIAL_ARROW_HEIGHT;
        }
    }
}
