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

    public boolean matches(List<ItemStack> outsideStacks) {
        List<ItemStack> recipeStacks = new ArrayList<ItemStack>();
        for (ItemStack component : componentList) {
            recipeStacks.add(component);
        }

        search:
        for (ItemStack curOutsideStack : outsideStacks) {
            Iterator recipeStacksIter = recipeStacks.iterator();

            while (recipeStacksIter.hasNext()) {
                ItemStack curRecipeStack = (ItemStack) recipeStacksIter.next();
                if (curOutsideStack != null && curRecipeStack.getItem() == curOutsideStack.getItem() && curOutsideStack.getItemDamage() == curRecipeStack.getItemDamage()
                        && curOutsideStack.stackSize <= curRecipeStack.stackSize) {
                    recipeStacksIter.remove();
                    continue search;
                }
            }
        }

        return recipeStacks.size() == 0;
    }
}