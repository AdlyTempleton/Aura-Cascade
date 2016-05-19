package pixlepix.auracascade.data.recipe;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by localmacaccount on 6/20/15.
 */
public class ProcessorRecipeRegistry {

    public static List<ProcessorRecipe> recipes = new ArrayList<ProcessorRecipe>();


    public static ProcessorRecipe getRecipeFromEntity(List<EntityItem> entityItems, boolean isPrismatic) {
        ArrayList<ItemStack> stackList = new ArrayList<ItemStack>();
        for (EntityItem entityItem : entityItems) {
            stackList.add(entityItem.getEntityItem());
        }
        return getRecipe(stackList, isPrismatic);
    }

    public static ProcessorRecipe getRecipe(List<ItemStack> stacks, boolean isPrismatic) {
        for (ProcessorRecipe recipe : recipes) {
            if (recipe.matches(stacks) && !(!isPrismatic && recipe.prismaticOnly)) {
                return recipe;
            }
        }
        return null;
    }

    public static void init() {
        for (int i = 0; i < 16; i++) {
            registerRecipe(new ProcessorRecipe(new ItemStack(Items.DYE, 1, i), true, new ItemStack(Blocks.WOOL, 1, 15 - i)));
        }
    }

    public static void registerRecipe(ProcessorRecipe recipe) {
        recipes.add(recipe);
    }

}
