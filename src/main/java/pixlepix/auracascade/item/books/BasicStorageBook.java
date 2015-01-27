package pixlepix.auracascade.item.books;

import net.minecraft.item.ItemStack;
import pixlepix.auracascade.item.ItemStorageBook;

/**
 * Created by localmacaccount on 1/27/15.
 */
public class BasicStorageBook extends ItemStorageBook {
    @Override
    public int getMaxStackSize() {
        return 64;
    }

    @Override
    public int getHeldStacks() {
        return 27;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return true;
    }

    @Override
    public String getItemName() {
        return "basicBook";
    }
}
