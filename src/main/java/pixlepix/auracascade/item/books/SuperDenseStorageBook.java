package pixlepix.auracascade.item.books;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import pixlepix.auracascade.block.tile.TileStorageBookshelf;
import pixlepix.auracascade.item.ItemStorageBook;
import pixlepix.auracascade.registry.BlockRegistry;
import pixlepix.auracascade.registry.CraftingBenchRecipe;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

/**
 * Created by localmacaccount on 1/27/15.
 */
public class SuperDenseStorageBook extends ItemStorageBook {
    @Override
    public int getMaxStackSize() {
        return 25000;
    }

    @Override
    public int getHeldStacks() {
        return 2;
    }

    @Override
    public boolean isItemValid(ItemStack stack, TileStorageBookshelf tileStorageBookshelf) {
        return true;
    }

    @Override
    public String getItemName() {
        return "superDenseBook";
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return new CraftingBenchRecipe(new ItemStack(this), "SSS", "SBS", "SSS", 'B',
                new ItemStack(BlockRegistry.getFirstItemFromClass(DenseStorageBook.class)), 'S', new ItemStack(Blocks.STONE));
    }
}
