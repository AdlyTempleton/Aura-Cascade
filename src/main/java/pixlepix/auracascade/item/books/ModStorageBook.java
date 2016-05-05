package pixlepix.auracascade.item.books;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameData;
import pixlepix.auracascade.block.tile.TileStorageBookshelf;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.item.ItemAuraCrystal;
import pixlepix.auracascade.item.ItemStorageBook;
import pixlepix.auracascade.registry.BlockRegistry;
import pixlepix.auracascade.registry.CraftingBenchRecipe;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

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
        ResourceLocation uid = GameData.getItemRegistry().getNameForObject(stack.getItem());
        if (uid == null || uid.getResourceDomain() == null) {
            return false;
        }
        for (ItemStack stackInInv : inv) {
            if (stackInInv != null && !uid.getResourceDomain().equals(GameData.getItemRegistry().getNameForObject(stackInInv.getItem()).getResourceDomain())) {
                return false;
            }
        }
        return !uid.getResourceDomain().equals("minecraft");
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
