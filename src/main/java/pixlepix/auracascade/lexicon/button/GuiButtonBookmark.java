package pixlepix.auracascade.lexicon.button;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import pixlepix.auracascade.lexicon.GuiLexicon;
import pixlepix.auracascade.lexicon.VazkiiRenderHelper;

public class GuiButtonBookmark extends GuiButtonLexicon {

    GuiLexicon gui;

    public GuiButtonBookmark(int par1, int par2, int par3, GuiLexicon gui, String str) {
        super(par1, par2, par3, gui.bookmarkWidth(str) + 5, 11, str);
        this.gui = gui;
    }

    @Override
    public void drawButton(Minecraft mc, int par2, int par3) {
        gui.drawBookmark(xPosition, yPosition, displayString, false);
        hovered = par2 >= xPosition && par3 >= yPosition && par2 < xPosition + width && par3 < yPosition + height;
        int k = getHoverState(hovered);

        List<String> tooltip = new ArrayList();
        if (displayString.equals("+"))
            tooltip.add(I18n.translateToLocal("auramisc.clickToAdd"));
        else {
            tooltip.add(String.format(I18n.translateToLocal("auramisc.bookmark"), id - GuiLexicon.BOOKMARK_START + 1));
            tooltip.add(TextFormatting.GRAY + I18n.translateToLocal("auramisc.clickToSee"));
            tooltip.add(TextFormatting.GRAY + I18n.translateToLocal("auramisc.shiftToRemove"));
        }

        int tooltipY = (tooltip.size() + 1) * 5;
        if (k == 2)
            VazkiiRenderHelper.renderTooltip(par2, par3 + tooltipY, tooltip);
    }

}
