package pixlepix.auracascade.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 * Created by localmacaccount on 1/17/15.
 */
public class OreCraftingBenchRecipe extends ThaumicTinkererRecipe {

    private final ItemStack output;
    private final Object[] stuff;
    public IRecipe iRecipe;

    public OreCraftingBenchRecipe(ItemStack output, Object... stuff) {
        this.output = output;
        this.stuff = stuff;
    }

    @Override
    public void registerRecipe() {
        iRecipe = new ShapedOreRecipe(output, stuff);
        GameRegistry.addRecipe(iRecipe);
    }
}