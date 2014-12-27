/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * 
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [Jan 16, 2014, 4:52:06 PM (GMT)]
 */
package pixlepix.auracascade.lexicon.button;

import net.minecraft.client.Minecraft;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import pixlepix.auracascade.lexicon.GuiLexicon;
import pixlepix.auracascade.lexicon.VazkiiRenderHelper;

import java.util.Arrays;

public class GuiButtonPage extends GuiButtonLexicon {

	boolean right;

	public GuiButtonPage(int par1, int par2, int par3, boolean right) {
		super(par1, par2, par3, 18, 10, "");
		this.right = right;
	}

	@Override
	public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
		if(enabled) {
			field_146123_n = par2 >= xPosition && par3 >= yPosition && par2 < xPosition + width && par3 < yPosition + height;
			int k = getHoverState(field_146123_n);

			par1Minecraft.renderEngine.bindTexture(GuiLexicon.texture);
			GL11.glColor4f(1F, 1F, 1F, 1F);
			drawTexturedModalRect(xPosition, yPosition, k == 2 ? 18 : 0, right ? 180 : 190, 18, 10);

			if(k == 2)
				VazkiiRenderHelper.renderTooltip(par2, par3, Arrays.asList(StatCollector.translateToLocal(right ? "botaniamisc.nextPage" : "botaniamisc.prevPage")));
		}
	}

}
