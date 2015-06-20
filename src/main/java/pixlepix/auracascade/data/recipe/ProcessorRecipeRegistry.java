package pixlepix.auracascade.data.recipe;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by localmacaccount on 6/20/15.
 */
public class ProcessorRecipeRegistry {

    public static List<ProcessorRecipe> recipes = new ArrayList<ProcessorRecipe>();


    public static ProcessorRecipe getResult(List<EntityItem> entityItems) {
        ArrayList<ItemStack> stackList = new ArrayList<ItemStack>();
        for (EntityItem entityItem : entityItems) {
            stackList.add(entityItem.getEntityItem());
        }
        return getRecipe(stackList);
    }

    public static ProcessorRecipe getRecipe(List<ItemStack> stacks) {
        for (ProcessorRecipe recipe : recipes) {
            if (recipe.matches(stacks)) {
                return recipe;
            }
        }
        return null;
    }

    public static void init() {

    }

    public static void registerRecipe(ProcessorRecipe recipe) {
        recipes.add(recipe);
    }

}
