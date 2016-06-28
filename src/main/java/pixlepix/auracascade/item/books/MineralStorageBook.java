package pixlepix.auracascade.item.books;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import pixlepix.auracascade.block.tile.TileStorageBookshelf;
import pixlepix.auracascade.item.ItemStorageBook;
import pixlepix.auracascade.registry.BlockRegistry;
import pixlepix.auracascade.registry.CraftingBenchRecipe;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

import java.util.Arrays;

/**
 * Created by localmacaccount on 1/27/15.
 */
public class MineralStorageBook extends ItemStorageBook {
    public Block[] blocks = new Block[]{Blocks.EMERALD_BLOCK, Blocks.LAPIS_BLOCK, Blocks.COAL_BLOCK, Blocks.DIAMOND_BLOCK, Blocks.GOLD_BLOCK, Blocks.IRON_BLOCK, Blocks.REDSTONE_BLOCK, Blocks.COAL_ORE, Blocks.DIAMOND_ORE, Blocks.EMERALD_ORE, Blocks.GOLD_ORE, Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.QUARTZ_ORE, Blocks.QUARTZ_BLOCK, Blocks.REDSTONE_ORE};
    public Item[] items = new Item[]{Items.QUARTZ, Items.DYE, Items.DIAMOND, Items.GOLD_INGOT, Items.IRON_INGOT, Items.COAL, Items.REDSTONE, Items.EMERALD};
    public String[] ores = new String[]{"ore", "ingot", "dust"};

    @Override
    public int getMaxStackSize() {
        return 100000;
    }

    @Override
    public int getHeldStacks() {
        return 8;
    }

    @Override
    public boolean isItemValid(ItemStack stack, TileStorageBookshelf tileStorageBookshelf) {
        if (isValid(stack, blocks, items, ores)) {
            return true;

        }
        int[] ids = OreDictionary.getOreIDs(stack);
        for (int i : ids) {
            String s = OreDictionary.getOreName(i);
            if (Arrays.asList(ores).contains(s.toLowerCase())) {
                return true;
            }
            if (s.startsWith("block")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return new CraftingBenchRecipe(new ItemStack(this), "SSS", "SBS", "SSS", 'B',
                new ItemStack(BlockRegistry.getFirstItemFromClass(BasicStorageBook.class)), 'S', new ItemStack(Items.IRON_INGOT));
    }

    @Override
    public String getItemName() {
        return "mineralBook";
    }
}

