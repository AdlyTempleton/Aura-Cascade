/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Jan 14, 2014, 8:34:01 PM (GMT)]
 */
package pixlepix.auracascade.lexicon.button;

import net.minecraft.client.Minecraft;

public class GuiButtonInvisible extends GuiButtonLexicon {

    public GuiButtonInvisible(int par1, int par2, int par3, int par4, int par5, String par6Str) {
        super(par1, par2, par3, par4, par5, par6Str);
    }

    @Override
    public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
        field_146123_n = par2 >= xPosition && par3 >= yPosition && par2 < xPosition + width && par3 < yPosition + height;
        int k = getHoverState(field_146123_n);

        boolean unicode = par1Minecraft.fontRenderer.getUnicodeFlag();
        par1Minecraft.fontRenderer.setUnicodeFlag(true);
        par1Minecraft.fontRenderer.drawString(displayString, xPosition + (k == 2 ? 5 : 0), yPosition + (height - 8) / 2, 0);
        par1Minecraft.fontRenderer.setUnicodeFlag(unicode);
    }

}
