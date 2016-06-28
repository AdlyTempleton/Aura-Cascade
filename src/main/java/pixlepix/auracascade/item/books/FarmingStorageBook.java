package pixlepix.auracascade.item.books;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import pixlepix.auracascade.block.tile.TileStorageBookshelf;
import pixlepix.auracascade.item.ItemStorageBook;
import pixlepix.auracascade.registry.BlockRegistry;
import pixlepix.auracascade.registry.CraftingBenchRecipe;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

/**
 * Created by localmacaccount on 1/27/15.
 */
public class FarmingStorageBook extends ItemStorageBook {
    public Block[] blocks = new Block[]{Blocks.LIT_PUMPKIN , Blocks.CACTUS, Blocks.LEAVES, Blocks.LEAVES2, Blocks.LOG, Blocks.LOG2, Blocks.MELON_BLOCK, Blocks.PUMPKIN, Blocks.CAKE, Blocks.RED_FLOWER, Blocks.YELLOW_FLOWER, Blocks.SAPLING};
    public Item[] items = new Item[]{Items.APPLE, Items.BAKED_POTATO, Items.BEEF, Items.BREAD, Items.CAKE, Items.CARROT, Items.CHICKEN, Items.COOKED_BEEF, Items.COOKED_CHICKEN, Items.COOKED_FISH, Items.COOKED_PORKCHOP, Items.COOKIE, Items.EGG, Items.FEATHER, Items.FISH, Items.LEATHER, Items.MELON_SEEDS, Items.MELON, Items.MILK_BUCKET, Items.NETHER_WART, Items.PORKCHOP, Items.POTATO, Items.PUMPKIN_SEEDS, Items.REEDS, Items.SPECKLED_MELON, Items.SUGAR, Items.WHEAT, Items.WHEAT_SEEDS};
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
        return isValid(stack, blocks, items, ores) || ForgeRegistries.ITEMS.getKey(item).getResourceDomain() != null && ForgeRegistries.ITEMS.getKey(item).getResourceDomain().equals("HarvestCraft");
    }

    @Override
    public String getItemName() {
        return "farmingBook";
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return new CraftingBenchRecipe(new ItemStack(this), "SSS", "SBS", "SSS", 'B',
                new ItemStack(BlockRegistry.getFirstItemFromClass(BasicStorageBook.class)), 'S', new ItemStack(Items.WHEAT_SEEDS));
    }
}
