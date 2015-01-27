package pixlepix.auracascade.item.books;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
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
public class TerrainStorageBook extends ItemStorageBook {
    public Block[] blocks = new Block[]{Blocks.stone, Blocks.sand, Blocks.dirt, Blocks.netherrack, Blocks.soul_sand};

    @Override
    public int getMaxStackSize() {
        return 10000000;
    }

    @Override
    public int getHeldStacks() {
        return 1;
    }

    @Override
    public boolean isItemValid(ItemStack stack, TileStorageBookshelf tileStorageBookshelf) {
        Item item = stack.getItem();
        if (Block.getBlockFromItem(item) != null) {
            Block block = Block.getBlockFromItem(item);
            if (Arrays.asList(blocks).contains(block)) {
                return true;
            }
        }
        int[] ids = OreDictionary.getOreIDs(stack);
        for (int i : ids) {
            String s = OreDictionary.getOreName(i);
            if (s.toLowerCase().contains("stone") || s.toLowerCase().contains("dirt")) {
                return true;
            }
        }


        return false;
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return new CraftingBenchRecipe(new ItemStack(this), "SSS", "SBS", "SSS", 'B',
                new ItemStack(BlockRegistry.getFirstItemFromClass(BasicStorageBook.class)), 'S', new ItemStack(Blocks.cobblestone));
    }

    @Override
    public String getItemName() {
        return "terrainBook";
    }
}
