package pixlepix.auracascade.item.books;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import pixlepix.auracascade.block.tile.TileStorageBookshelf;
import pixlepix.auracascade.item.ItemStorageBook;

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
}
