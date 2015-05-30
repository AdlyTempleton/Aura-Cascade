package pixlepix.auracascade.data;

import net.minecraft.item.ItemStack;

/**
 * Created by pixlepix on 1/24/15.
 * Wraps an itemstack and provides a sane hash code function
 */
public class ItemStackMapEntry {

    public ItemStack stack;

    public ItemStackMapEntry(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public int hashCode() {
        if (stack == null || stack.getItem() == null) {
            return -43532987;

        }
        return stack.getItem().getUnlocalizedName().hashCode() * -2134 + stack.stackSize * 3245879 + stack.getItemDamage() * -234569 + (stack.stackTagCompound != null ? stack.stackTagCompound.hashCode() * 2345798 : 0);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean equals(Object obj) {
        return obj instanceof ItemStackMapEntry && ((stack == null && ((ItemStackMapEntry) obj).stack == null) || ((ItemStackMapEntry) obj).stack.isItemEqual(stack));
    }
}
