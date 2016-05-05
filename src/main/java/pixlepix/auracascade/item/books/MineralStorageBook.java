package pixlepix.auracascade.item.books;

import java.util.Arrays;

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

/**
 * Created by localmacaccount on 1/27/15.
 */
public class MineralStorageBook extends ItemStorageBook {
    public Block[] blocks = new Block[]{Blocks.emerald_block, Blocks.lapis_block, Blocks.coal_block, Blocks.diamond_block, Blocks.gold_block, Blocks.iron_block, Blocks.redstone_block, Blocks.coal_ore, Blocks.diamond_ore, Blocks.emerald_ore, Blocks.gold_ore, Blocks.iron_ore, Blocks.lapis_ore, Blocks.quartz_ore, Blocks.quartz_block, Blocks.redstone_ore};
    public Item[] items = new Item[]{Items.quartz, Items.dye, Items.diamond, Items.gold_ingot, Items.iron_ingot, Items.coal, Items.redstone, Items.emerald};
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
                new ItemStack(BlockRegistry.getFirstItemFromClass(BasicStorageBook.class)), 'S', new ItemStack(Items.iron_ingot));
    }

    @Override
    public String getItemName() {
        return "mineralBook";
    }
}

