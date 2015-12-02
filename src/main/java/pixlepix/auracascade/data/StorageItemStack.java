package pixlepix.auracascade.data;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

/**
 * Created by localmacaccount on 1/23/15.
 */
public class StorageItemStack {
    public Item item;
    public int stackSize;
    public int damage;
    public NBTTagCompound compound;

    public StorageItemStack(Item item, int stackSize, int damage, NBTTagCompound compound) {
        this.item = item;
        this.stackSize = stackSize;
        this.damage = damage;
        this.compound = compound;
    }

    public StorageItemStack(ItemStack stack) {
        this(stack.getItem(), stack.stackSize, stack.getItemDamage(), stack.getTagCompound());
    }

    public static StorageItemStack readFromNBT(NBTTagCompound compound) {
        Item item = Item.itemRegistry.getObject(new ResourceLocation(compound.getString("item")));
        int stackSize = compound.getInteger("stackSize");
        int damage = compound.getInteger("damage");
        compound = compound.getCompoundTag("compound");
        return new StorageItemStack(item, stackSize, damage, compound);
    }

    public NBTTagCompound writeToNBT() {
        NBTTagCompound result = new NBTTagCompound();
        result.setString("item", Item.itemRegistry.getNameForObject(item).toString());
        result.setInteger("stackSize", stackSize);
        result.setInteger("damage", damage);
        result.setTag("compound", compound);
        return result;
    }

    public boolean equalsType(Object obj) {
        //Doesn't check stacksize
        if (obj instanceof StorageItemStack) {
            StorageItemStack storageItemStack = (StorageItemStack) obj;
            if (storageItemStack.damage == damage) {
                if (storageItemStack.item == item) {
                    if (storageItemStack.compound == null) {
                        return compound == null;
                    }
                    if (storageItemStack.compound.equals(compound)) {
                        return true;

                    }

                }

            }

        }
        return false;
    }

    public StorageItemStack copy() {
        return new StorageItemStack(item, stackSize, damage, compound != null ? (NBTTagCompound) compound.copy() : null);
    }

    public StorageItemStack merge(StorageItemStack other, int max) {
        if (!equalsType(other)) {
            return other;
        }
        StorageItemStack otherStack = other.copy();
        int delta = Math.min(max - stackSize, otherStack.stackSize);
        otherStack.stackSize -= delta;
        if (otherStack.stackSize <= 0) {
            otherStack = null;
        }
        stackSize += delta;
        return otherStack;
    }

    public ArrayList<ItemStack> getItemStacks(int maxSize) {
        ArrayList<ItemStack> result = new ArrayList<ItemStack>();
        StorageItemStack copy = copy();
        for (int i = 0; i < maxSize / 64; i++) {
            int delta = Math.min(64, copy.stackSize);
            copy.stackSize -= delta;
            ItemStack resultStack = null;
            if (delta > 0) {
                resultStack = new ItemStack(item, delta, damage);
                resultStack.setTagCompound((NBTTagCompound) compound.copy());
            }
            result.add(resultStack);
        }
        return result;
    }

    public ItemStack toItemStack() {
        if (stackSize == 0) {
            return null;
        }
        ItemStack result = new ItemStack(item, stackSize, damage);
        result.setTagCompound(compound);
        return result;
    }
}
