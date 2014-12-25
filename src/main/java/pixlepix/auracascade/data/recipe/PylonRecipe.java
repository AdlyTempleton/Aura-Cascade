package pixlepix.auracascade.data.recipe;

import net.minecraft.item.Item;
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
    @Override
    public void registerRecipe() {
        PylonRecipeRegistry.registerRecipe(this);
    }

    public List<PylonRecipeComponent> componentList = new ArrayList<PylonRecipeComponent>();
    public ItemStack result;

    public PylonRecipe(ItemStack result,  PylonRecipeComponent component){
        this(result, component, component, component, component);
    }

    public PylonRecipe(ItemStack result, PylonRecipeComponent component1, PylonRecipeComponent component2, PylonRecipeComponent component3, PylonRecipeComponent component4){
        this.result = result;
        componentList.add(component1);
        componentList.add(component2);
        componentList.add(component3);
        componentList.add(component4);
    }

    public AuraQuantity getAuraFromItem(ItemStack stack){
        for(PylonRecipeComponent component:componentList){
            if(ItemStack.areItemStacksEqual(stack, component.itemStack)){
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
        for(ItemStack stack: stacks){
            if(stack == null){
                return false;
            }
        }
        Iterator stacksIter = stacks.iterator();
        while (stacksIter.hasNext()) {
            ItemStack curStack = (ItemStack) stacksIter.next();
            Iterator recipeStacksIter = new ArrayList<ItemStack>(recipeStacks).iterator();

            boolean found = false;
            while (recipeStacksIter.hasNext()) {
                ItemStack curRecipeStack = (ItemStack) recipeStacksIter.next();
                if (ItemStack.areItemStacksEqual(curRecipeStack, curStack)) {
                    found = true;
                    recipeStacksIter.remove();
                }
            }
            if (!found) {
                return false;
            }
        }

        return true;
    }
}
