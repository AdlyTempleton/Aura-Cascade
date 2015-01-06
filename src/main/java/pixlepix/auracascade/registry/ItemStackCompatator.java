package pixlepix.auracascade.registry;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import java.util.Comparator;

/**
 * Created by pixlepix on 8/3/14.
 * Used to sort ItemStacks alphabetically for the creative tab
 */
public class ItemStackCompatator implements Comparator<ItemStack> {
    @Override
    public int compare(ItemStack o1, ItemStack o2) {
        if (o1.getItem() instanceof ISpecialCreativeSort) {
            return ((ISpecialCreativeSort) o1.getItem()).compare(o1, o2);
        }
        if (Block.getBlockFromItem(o1.getItem()) instanceof ISpecialCreativeSort) {
            return ((ISpecialCreativeSort) Block.getBlockFromItem(o1.getItem())).compare(o1, o2);
        }
        if (o2.getItem() instanceof ISpecialCreativeSort) {
            return -1 * ((ISpecialCreativeSort) o2.getItem()).compare(o2, o1);
        }
        if (Block.getBlockFromItem(o2.getItem()) instanceof ISpecialCreativeSort) {
            return -1 * ((ISpecialCreativeSort) Block.getBlockFromItem(o2.getItem())).compare(o2, o1);
        }
        return o1.getDisplayName().compareToIgnoreCase(o2.getDisplayName());
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
