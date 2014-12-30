/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * 
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [Jan 20, 2014, 7:05:44 PM (GMT)]
 */
package pixlepix.auracascade.lexicon;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Any block that implements this can be right clicked with
 * a Lexica Botania to open a entry page.
 */
public interface ILexiconable {

	/**
	 * Gets the lexicon entry to open at this location. null works too.
	 */
	public LexiconEntry getEntry(World world, int x, int y, int z, EntityPlayer player, ItemStack lexicon);

}
