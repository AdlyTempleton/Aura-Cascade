package pixlepix.auracascade.data.recipe;

import net.minecraft.item.ItemStack;
import pixlepix.auracascade.data.AuraQuantity;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by pixlepix on 12/7/14.
 */
public class PylonRecipe extends ThaumicTinkererRecipe {
    public List<PylonRecipeComponent> componentList = new ArrayList<PylonRecipeComponent>();
    public ItemStack result;

    public PylonRecipe(ItemStack result, PylonRecipeComponent component) {
        this(result, component, component, component, component);
    }

    public PylonRecipe(ItemStack result, PylonRecipeComponent component1, PylonRecipeComponent component2, PylonRecipeComponent component3, PylonRecipeComponent component4) {
        this.result = result;
        componentList.add(component1);
        componentList.add(component2);
        componentList.add(component3);
        componentList.add(component4);
    }

    @Override
    public void registerRecipe() {
        PylonRecipeRegistry.registerRecipe(this);
    }

    public AuraQuantity getAuraFromItem(ItemStack stack) {
        for (PylonRecipeComponent component : componentList) {
            if (ItemStack.areItemStacksEqual(stack, component.itemStack)) {
                return component.auraQuantity;
            }
        }
        return null;
    }

    public boolean matches(List<ItemStack> stacks) {
        List<ItemStack> recipeStacks = new ArrayList<ItemStack>();
        for (PylonRecipeComponent component : componentList) {
            recipeStacks.add(component.itemStack);
        }

        //ItemStack.areItemStacksEqual returns a false positive if itemstacks are null
        for (ItemStack stack : stacks) {
            if (stack == null) {
                return false;
            }
        }
        search:
        for (ItemStack curStack : stacks) {
            Iterator<ItemStack> recipeStacksIter = recipeStacks.iterator();

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
