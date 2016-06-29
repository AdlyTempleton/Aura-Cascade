package pixlepix.auracascade.item.books;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import pixlepix.auracascade.block.tile.TileStorageBookshelf;
import pixlepix.auracascade.data.EnumRainbowColor;
import pixlepix.auracascade.item.ItemMaterial;
import pixlepix.auracascade.item.ItemStorageBook;
import pixlepix.auracascade.registry.CraftingBenchRecipe;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

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
    public boolean isItemValid(ItemStack stack, TileStorageBookshelf tileStorageBookshelf) {
        return true;
    }

    @Override
    public String getItemName() {
        return "basicBook";
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return new CraftingBenchRecipe(new ItemStack(this), "BIB", 'B', new ItemStack(Items.BOOK), 'I', ItemMaterial.getIngot(EnumRainbowColor.BLACK));
    }
}
