package pixlepix.auracascade.item.books;

import net.minecraft.item.ItemStack;
import pixlepix.auracascade.block.tile.TileStorageBookshelf;
import pixlepix.auracascade.item.ItemStorageBook;

/**
 * Created by localmacaccount on 1/27/15.
 */
public class LightStorageBook extends ItemStorageBook {
    @Override
    public int getMaxStackSize() {
        return 32;
    }

    @Override
    public int getHeldStacks() {
        return 100;
    }

    @Override
    public boolean isItemValid(ItemStack stack, TileStorageBookshelf tileStorageBookshelf) {
        return true;
    }

    @Override
    public String getItemName() {
        return "lightBook";
    }
}
