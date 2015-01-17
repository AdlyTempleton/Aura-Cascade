package pixlepix.auracascade.registry;

import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThaumicTinkererRecipeMulti extends ThaumicTinkererRecipe {

    public List<ThaumicTinkererRecipe> recipes;

    public ThaumicTinkererRecipeMulti(ThaumicTinkererRecipe... recipes) {
        this.recipes = Arrays.asList(recipes);
    }

    public ThaumicTinkererRecipeMulti() {
        this.recipes = new ArrayList<ThaumicTinkererRecipe>();
    }

    public void addRecipe(ThaumicTinkererRecipe recipe) {
        recipes.add(recipe);
    }

    public List<IRecipe> getIRecipies(int start, int end) {
        List<ThaumicTinkererRecipe> subsetRecipies = recipes.subList(start, end);
        List<IRecipe> result = new ArrayList<IRecipe>();
        for (ThaumicTinkererRecipe recipe : subsetRecipies) {
            if (recipe instanceof CraftingBenchRecipe) {
                result.add(((CraftingBenchRecipe) recipe).iRecipe);
            }
            if (recipe instanceof OreCraftingBenchRecipe) {
                result.add(((OreCraftingBenchRecipe) recipe).iRecipe);
            }
        }
        return result;
    }

    public List<IRecipe> getIRecipies() {
        return getIRecipies(0, recipes.size());
    }

    @Override
    public void registerRecipe() {
        for (ThaumicTinkererRecipe recipe : recipes) {
            recipe.registerRecipe();
        }
    }
}
