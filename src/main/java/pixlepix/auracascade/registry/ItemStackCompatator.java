package pixlepix.auracascade.registry;

import java.util.Comparator;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by pixlepix on 8/3/14.
 * Used to sort ItemStacks alphabetically for the creative tab
 */
public class ItemStackCompatator implements Comparator<ItemStack> {
    @Override
    public int compare(ItemStack o1, ItemStack o2) {

        Object itemObj1 = o1.getItem();
        Object itemObj2 = o2.getItem();

        //Convert to block if it is a block
        itemObj1 = Block.getBlockFromItem((Item) itemObj1) != Blocks.air ? Block.getBlockFromItem((Item) itemObj1) : itemObj1;
        itemObj2 = Block.getBlockFromItem((Item) itemObj2) != Blocks.air ? Block.getBlockFromItem((Item) itemObj2) : itemObj2;

        if (itemObj1 instanceof ITTinkererRegisterable && itemObj2 instanceof ITTinkererRegisterable) {
            int p1 = ((ITTinkererRegisterable) itemObj1).getCreativeTabPriority();
            int p2 = ((ITTinkererRegisterable) itemObj2).getCreativeTabPriority();
            int comp = p2 - p1;
            if (comp != 0) {
                return comp;
            }
        }

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
