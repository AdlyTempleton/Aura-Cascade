package pixlepix.auracascade.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import java.util.List;
public class CraftingBenchRecipe extends ThaumicTinkererRecipe {
    private final ItemStack output;
    private final Object[] stuff;
    public CraftingBenchRecipe(ItemStack output, Object... stuff) {
        this.output = output;
        this.stuff = stuff;
    }
    @Override
    public void registerRecipe() {
        GameRegistry.addRecipe(output, stuff);
    }
}