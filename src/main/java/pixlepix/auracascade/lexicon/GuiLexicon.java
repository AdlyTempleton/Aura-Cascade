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
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import pixlepix.auracascade.lexicon.button.GuiButtonBookmark;
import pixlepix.auracascade.lexicon.button.GuiButtonCategory;
import pixlepix.auracascade.lexicon.button.GuiButtonInvisible;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class GuiLexicon extends GuiScreen {

	public static GuiLexicon currentOpenLexicon = new GuiLexicon();
	public static ItemStack stackUsed;

	public static final int BOOKMARK_START = 1337;
	public static List<GuiLexicon> bookmarks = new ArrayList();
	boolean bookmarksNeedPopulation = false;

	public static final ResourceLocation texture = new ResourceLocation(LibResources.GUI_LEXICON);
	public static final ResourceLocation textureToff = new ResourceLocation(LibResources.GUI_TOFF);

	public float lastTime = 0F;
	public float partialTicks = 0F;
	public float timeDelta = 0F;
	
	List<LexiconCategory> allCategories;

	String title;
	int guiWidth = 146;
	int guiHeight = 180;
	int left, top;

	@Override
	public void initGui() {
		super.initGui();
		
		allCategories = new ArrayList(CategoryManager.getAllCategories());
		Collections.sort(allCategories);
		
		lastTime = ClientTickHandler.ticksInGame;

		title = Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().getDisplayName();
		currentOpenLexicon = this;

		left = width / 2 - guiWidth / 2;
		top = height / 2 - guiHeight / 2;

		buttonList.clear();
		if(isIndex()) {
			int x = 18;
			for(int i = 0; i < 12; i++) {
				int y = 16 + i * 12;
				buttonList.add(new GuiButtonInvisible(i, left + x, top + y, 110, 10, ""));
			}
			populateIndex();
		} else if(isCategoryIndex()) {
			int categories = allCategories.size();
			for(int i = 0; i < categories + 1; i++) {
				LexiconCategory category = null;
				category = i >= categories ? null : allCategories.get(i);
				int x = i % 4;
				int y = i / 4;

				int size = 27;
				GuiButtonCategory button = new GuiButtonCategory(i, left + 18 + x * size, top + 35 + y * size, this, category);
				buttonList.add(button);
			}
		}
		populateBookmarks();
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		float time = ClientTickHandler.ticksInGame + par3;
		timeDelta = time - lastTime;
		lastTime = time;
		partialTicks = par3;

		GL11.glColor4f(1F, 1F, 1F, 1F);
		mc.renderEngine.bindTexture(texture);
		drawTexturedModalRect(left, top, 0, 0, guiWidth, guiHeight);

		drawBookmark(left + guiWidth / 2, top - getTitleHeight(), getTitle(), true);
		String subtitle = getSubtitle();
		if(subtitle != null) {
			GL11.glScalef(0.5F, 0.5F, 1F);
			drawCenteredString(fontRendererObj, subtitle, left * 2 + guiWidth, (top - getTitleHeight() + 11) * 2, 0x00FF00);
			GL11.glScalef(2F, 2F, 1F);
		}

		drawHeader();

		if(bookmarksNeedPopulation) {
			populateBookmarks();
			bookmarksNeedPopulation = false;
		}
		
		if(mc.thePlayer.getCommandSenderName().equals("haighyorkie")) {
			GL11.glColor4f(1F, 1F, 1F, 1F);
			mc.renderEngine.bindTexture(texture);
			drawTexturedModalRect(left - 19, top + 12, 67, 180, 19, 26);
			if(par1 >= left - 19 && par1 < left && par2 >= top + 12 && par2 < top + 38) {
				mc.renderEngine.bindTexture(textureToff);
				GL11.glPushMatrix();
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				GL11.glTranslatef(0F, 0F, 2F);
				
				int w = 256;
				int h = 152;
				int x = (int) ((ClientTickHandler.ticksInGame + par3) * 6) % (width + w) - w;
				int y = (int) (top + guiHeight / 2 - h / 4 + Math.sin((ClientTickHandler.ticksInGame + par3) / 6.0) * 40);
				
				drawTexturedModalRect(x * 2, y * 2, 0, 0, w, h);
				GL11.glDisable(GL11.GL_BLEND);				
				GL11.glPopMatrix();
				
				VazkiiRenderHelper.renderTooltip(par1, par2, Arrays.asList(EnumChatFormatting.GOLD + "#goldfishchris", EnumChatFormatting.RED + "INTENSIFY HIM"));
			}
		}

		super.drawScreen(par1, par2, par3);
	}

	public void drawBookmark(int x, int y, String s, boolean drawLeft) {
		// This function is called from the buttons so I can't use fontRendererObj
		FontRenderer font = Minecraft.getMinecraft().fontRenderer;
		boolean unicode = font.getUnicodeFlag();
		font.setUnicodeFlag(true);
		int l = font.getStringWidth(s);
		int fontOff = 0;

		if(!drawLeft) {
			x += l / 2;
			fontOff = 2;
		}

		Minecraft.getMinecraft().renderEngine.bindTexture(texture);

		GL11.glColor4f(1F, 1F, 1F, 1F);
		drawTexturedModalRect(x + l / 2 + 3, y - 1, 54, 180, 6, 11);
		if(drawLeft)
			drawTexturedModalRect(x - l / 2 - 9, y - 1, 61, 180, 6, 11);
		for(int i = 0; i < l + 6; i++)
			drawTexturedModalRect(x - l / 2 - 3 + i, y - 1, 60, 180, 1, 11);

		font.drawString(s, x - l / 2 + fontOff, y, 0x111111, false);
		font.setUnicodeFlag(unicode);
	}

	void drawHeader() {
		boolean unicode = fontRendererObj.getUnicodeFlag();
		fontRendererObj.setUnicodeFlag(true);
		fontRendererObj.drawSplitString(String.format(StatCollector.translateToLocal("botania.gui.lexicon.header")), left + 18, top + 12, 110, 0);
		fontRendererObj.setUnicodeFlag(unicode);
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		if(par1GuiButton.id >= BOOKMARK_START)
			handleBookmark(par1GuiButton);
		else if(par1GuiButton instanceof GuiButtonCategory) {
			LexiconCategory category = ((GuiButtonCategory) par1GuiButton).getCategory();

			mc.displayGuiScreen(new GuiLexiconIndex(category));
			ClientTickHandler.notifyPageChange();
		}
	}

	public void handleBookmark(GuiButton par1GuiButton) {
		int i = par1GuiButton.id - BOOKMARK_START;
		if(i == bookmarks.size()) {
			if(!bookmarks.contains(this))
				bookmarks.add(this);
		} else {
			if(isShiftKeyDown())
				bookmarks.remove(i);
			else {
				GuiLexicon bookmark = bookmarks.get(i);
				Minecraft.getMinecraft().displayGuiScreen(bookmark);
				if(!bookmark.getTitle().equals(getTitle())) {
					if(bookmark instanceof IParented)
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
		for(int i = shift; i < 12; i++) {
			int i_ = i - shift;
			GuiButtonInvisible button = (GuiButtonInvisible) buttonList.get(i);
			LexiconCategory category = i_ >= categoryList.size() ? null : categoryList.get(i_);
			if(category != null)
				button.displayString = StatCollector.translateToLocal(category.getUnlocalizedName());
			else {
				button.displayString = StatCollector.translateToLocal("botaniamisc.lexiconIndex");
				break;
			}
		}
	}

	void populateBookmarks() {
		List remove = new ArrayList();
		List<GuiButton> buttons = buttonList;
		for(GuiButton button : buttons)
			if(button.id >= BOOKMARK_START)
				remove.add(button);
		buttonList.removeAll(remove);

		int len = bookmarks.size();
		boolean thisExists = false;
		for(GuiLexicon lex : bookmarks)
			if(lex.getTitle().equals(getTitle()))
				thisExists = true;

		boolean addEnabled = len < 8 && this instanceof IParented && !thisExists;
		for(int i = 0; i < len + (addEnabled ? 1 : 0); i++) {
			boolean isAdd = i == bookmarks.size();
			GuiLexicon gui = isAdd ? null : bookmarks.get(i);
			buttonList.add(new GuiButtonBookmark(BOOKMARK_START + i, left + 138, top + 18 + 14 * i, gui == null ? this : gui, gui == null ? "+" : gui.getTitle()));
		}
	}

	boolean closeScreenOnInvKey() {
		return true;
	}

	@Override
	protected void keyTyped(char par1, int par2) {
		if(closeScreenOnInvKey() && mc.gameSettings.keyBindInventory.getKeyCode() == par2) {
			mc.displayGuiScreen(null);
			mc.setIngameFocus();
		}

		super.keyTyped(par1, par2);
	}

}
