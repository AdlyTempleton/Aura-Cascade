package pixlepix.auracascade.data.recipe;

import net.minecraft.item.ItemStack;

/**
 * Created by pixlepix on 12/7/14.
 */
public class PylonRecipeComponent {
    public int auraQuantity;
    public ItemStack itemStack;

    public PylonRecipeComponent(int auraQuantity, ItemStack itemStack) {
        this.auraQuantity = auraQuantity;
        this.itemStack = itemStack;
    }
}
