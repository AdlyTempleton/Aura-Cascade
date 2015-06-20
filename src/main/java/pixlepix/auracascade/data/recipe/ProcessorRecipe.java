package pixlepix.auracascade.data.recipe;

import net.minecraft.item.ItemStack;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by localmacaccount on 6/20/15.
 */
public class ProcessorRecipe extends ThaumicTinkererRecipe {
    public List<ItemStack> componentList = new ArrayList<ItemStack>();
    public ItemStack result;

    public ProcessorRecipe(ItemStack result, ItemStack... components) {
        this.result = result;
        this.componentList = Arrays.asList(components);
    }


    @Override
    public void registerRecipe() {
        ProcessorRecipeRegistry.registerRecipe(this);
    }

    public boolean matches(List<ItemStack> stacks) {
        List<ItemStack> recipeStacks = new ArrayList<ItemStack>();
        for (ItemStack component : componentList) {
            recipeStacks.add(component);
        }

        //ItemStack.areItemStacksEqual returns a false positive if itemstacks are null
        for (ItemStack stack : stacks) {
            if (stack == null) {
                return false;
            }
        }
        search:
        for (ItemStack curStack : stacks) {
            Iterator recipeStacksIter = recipeStacks.iterator();

            while (recipeStacksIter.hasNext()) {
                ItemStack curRecipeStack = (ItemStack) recipeStacksIter.next();
                if (ItemStack.areItemStacksEqual(curRecipeStack, curStack)) {
                    recipeStacksIter.remove();
                    continue search;
                }
            }
            return false;
        }

        return true;
    }
}