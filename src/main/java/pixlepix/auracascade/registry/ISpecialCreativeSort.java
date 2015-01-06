package pixlepix.auracascade.registry;

import net.minecraft.item.ItemStack;

/**
 * Created by localmacaccount on 1/6/15.
 */
public interface ISpecialCreativeSort {
    //stack is guaranteed to be a instance of the Item or Block
    //otherStack is NOT
    //If you don't want any special comparison in certain circumstance, you must re-implement compare
    //return stack.getDisplayName().compareToIgnoreCase(otherStack.getDisplayName());
    public int compare(ItemStack stack, ItemStack otherStack);
}
