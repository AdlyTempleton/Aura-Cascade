package pixlepix.auracascade.item.books;

import net.minecraft.block.Block;
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
public class MobStorageBook extends ItemStorageBook {
    public Block[] blocks = new Block[]{};
    public Item[] items = new Item[]{Items.bone, Items.arrow, Items.blaze_rod, Items.blaze_powder, Items.ender_eye, Items.ender_pearl, Items.fermented_spider_eye, Items.spider_eye, Items.spawn_egg, Items.ghast_tear, Items.gunpowder, Items.rotten_flesh, Items.string, Items.slime_ball, Items.nether_star, Items.bow};
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
        Item item = stack.getItem();
        if (Block.getBlockFromItem(item) != null) {
            Block block = Block.getBlockFromItem(item);
            if (Arrays.asList(blocks).contains(block)) {
                return true;
            }
        }
        if (Arrays.asList(items).contains(item)) {
            return true;
        }
        int[] ids = OreDictionary.getOreIDs(stack);
        for (int i : ids) {
            String s = OreDictionary.getOreName(i);
            if (Arrays.asList(ores).contains(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getItemName() {
        return "mobDropBook";
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return new CraftingBenchRecipe(new ItemStack(this), "SSS", "SBS", "SSS", 'B',
                new ItemStack(BlockRegistry.getFirstItemFromClass(BasicStorageBook.class)), 'S', new ItemStack(Items.rotten_flesh));
    }
}
