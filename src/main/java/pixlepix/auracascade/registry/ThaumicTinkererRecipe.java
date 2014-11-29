package pixlepix.auracascade.registry;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public abstract class ThaumicTinkererRecipe {

	public abstract void registerRecipe();

	public static Object oreDictOrStack(ItemStack stack, String oreDict) {
		return OreDictionary.getOres(oreDict).isEmpty();
	}

}
