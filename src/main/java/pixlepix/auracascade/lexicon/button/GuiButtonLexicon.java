/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Aug 7, 2014, 5:53:16 PM (GMT)]
 */
package pixlepix.auracascade.lexicon.button;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class GuiButtonLexicon extends GuiButton {

    public GuiButtonLexicon(int p_i1021_1_, int p_i1021_2_, int p_i1021_3_, int p_i1021_4_, int p_i1021_5_, String p_i1021_6_) {
        super(p_i1021_1_, p_i1021_2_, p_i1021_3_, p_i1021_4_, p_i1021_5_, p_i1021_6_);
    }

    @Override
    public void playPressSound(SoundHandler p_146113_1_) {
    	//TODO Reimplement
        //p_146113_1_.playSound(PositionedSoundRecord.create(new ResourceLocation("aura:lexiconPage"), 1.0F));
    }

}
