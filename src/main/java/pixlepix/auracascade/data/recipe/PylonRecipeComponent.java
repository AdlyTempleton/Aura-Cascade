package pixlepix.auracascade.data.recipe;

import net.minecraft.item.ItemStack;
import pixlepix.auracascade.data.AuraQuantity;

/**
 * Created by pixlepix on 12/7/14.
 */
public class PylonRecipeComponent {
    public AuraQuantity auraQuantity;
    public ItemStack itemStack;

    public PylonRecipeComponent(AuraQuantity auraQuantity, ItemStack itemStack) {
        this.auraQuantity = auraQuantity;
        this.itemStack = itemStack;
    }
}
