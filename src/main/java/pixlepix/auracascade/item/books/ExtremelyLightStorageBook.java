package pixlepix.auracascade.item.books;

import net.minecraft.item.ItemStack;
import pixlepix.auracascade.item.ItemStorageBook;

/**
 * Created by localmacaccount on 1/27/15.
 */
public class ExtremelyLightStorageBook extends ItemStorageBook {
    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public int getHeldStacks() {
        return 10000;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return true;
    }

    @Override
    public String getItemName() {
        return "extremelyLightBook";
    }
}
