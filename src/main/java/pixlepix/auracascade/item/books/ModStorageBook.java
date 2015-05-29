package pixlepix.auracascade.item.books;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import pixlepix.auracascade.block.tile.TileStorageBookshelf;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.item.ItemAuraCrystal;
import pixlepix.auracascade.item.ItemStorageBook;
import pixlepix.auracascade.registry.BlockRegistry;
import pixlepix.auracascade.registry.CraftingBenchRecipe;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

import java.util.ArrayList;

/**
 * Created by localmacaccount on 1/27/15.
 */
public class ModStorageBook extends ItemStorageBook {
    @Override
    public int getMaxStackSize() {
        return 100;
    }

    @Override
    public int getHeldStacks() {
        return 64;
    }

    @Override
    public boolean isItemValid(ItemStack stack, TileStorageBookshelf tileStorageBookshelf) {
        ArrayList<ItemStack> inv = tileStorageBookshelf.inv;
        GameRegistry.UniqueIdentifier uid = GameRegistry.findUniqueIdentifierFor(stack.getItem());
        if (uid == null || uid.modId == null) {
            return false;
        }
        for (ItemStack stackInInv : inv) {
            if (stackInInv != null && !uid.modId.equals(GameRegistry.findUniqueIdentifierFor(stackInInv.getItem()).modId)) {
                return false;
            }
        }
        return !uid.modId.equals("minecraft");
    }

    @Override
    public String getItemName() {
        return "modBook";
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return new CraftingBenchRecipe(new ItemStack(this), "SSS", "SBS", "SSS", 'B',
                new ItemStack(BlockRegistry.getFirstItemFromClass(BasicStorageBook.class)), 'S', ItemAuraCrystal.getCrystalFromAura(EnumAura.WHITE_AURA));
    }
}
