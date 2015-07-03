package pixlepix.auracascade.item.books;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
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
public class FarmingStorageBook extends ItemStorageBook {
    public Block[] blocks = new Block[]{Blocks.lit_pumpkin, Blocks.cactus, Blocks.leaves, Blocks.leaves2, Blocks.log, Blocks.log2, Blocks.melon_block, Blocks.pumpkin, Blocks.cake, Blocks.red_flower, Blocks.yellow_flower, Blocks.sapling};
    public Item[] items = new Item[]{Items.apple, Items.baked_potato, Items.beef, Items.bread, Items.cake, Items.carrot, Items.chicken, Items.cooked_beef, Items.cooked_chicken, Items.cooked_fished, Items.cooked_porkchop, Items.cookie, Items.egg, Items.feather, Items.fish, Items.leather, Items.melon_seeds, Items.melon, Items.milk_bucket, Items.nether_wart, Items.porkchop, Items.potato, Items.pumpkin_seeds, Items.reeds, Items.speckled_melon, Items.sugar, Items.wheat, Items.wheat_seeds};
    public String[] ores = new String[]{"seed"};

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
        return isValid(stack, blocks, items, ores) || GameRegistry.findUniqueIdentifierFor(item).modId != null && GameRegistry.findUniqueIdentifierFor(item).modId.equals("HarvestCraft");
    }

    @Override
    public String getItemName() {
        return "farmingBook";
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return new CraftingBenchRecipe(new ItemStack(this), "SSS", "SBS", "SSS", 'B',
                new ItemStack(BlockRegistry.getFirstItemFromClass(BasicStorageBook.class)), 'S', new ItemStack(Items.wheat_seeds));
    }
}
