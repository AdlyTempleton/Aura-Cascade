package pixlepix.auracascade.item.books;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import pixlepix.auracascade.block.tile.TileStorageBookshelf;
import pixlepix.auracascade.item.ItemStorageBook;
import pixlepix.auracascade.registry.BlockRegistry;
import pixlepix.auracascade.registry.CraftingBenchRecipe;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

/**
 * Created by localmacaccount on 1/27/15.
 */
public class MobStorageBook extends ItemStorageBook {
    public Block[] blocks = new Block[]{};
    public Item[] items = new Item[]{Items.BONE, Items.ARROW, Items.BLAZE_ROD, Items.BLAZE_POWDER, Items.ENDER_EYE, Items.ENDER_PEARL, Items.FERMENTED_SPIDER_EYE, Items.SPIDER_EYE, Items.SPAWN_EGG, Items.GHAST_TEAR, Items.GUNPOWDER, Items.ROTTEN_FLESH, Items.STRING, Items.SLIME_BALL, Items.NETHER_STAR, Items.BOW};
    public String[] ores = new String[]{};

    @Override
    public int getMaxStackSize() {
        return 100000;
    }

    @Override
    public int getHeldStacks() {
        return 32;
    }

    @Override
    public boolean isItemValid(ItemStack stack, TileStorageBookshelf tileStorageBookshelf) {
        return isValid(stack, blocks, items, ores);
    }

    @Override
    public String getItemName() {
        return "mobDropBook";
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return new CraftingBenchRecipe(new ItemStack(this), "SSS", "SBS", "SSS", 'B',
                new ItemStack(BlockRegistry.getFirstItemFromClass(BasicStorageBook.class)), 'S', new ItemStack(Items.ROTTEN_FLESH));
    }
}
