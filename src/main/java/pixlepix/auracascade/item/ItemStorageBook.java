package pixlepix.auracascade.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import pixlepix.auracascade.block.BlockStorageBookshelf;
import pixlepix.auracascade.block.tile.TileStorageBookshelf;
import pixlepix.auracascade.data.StorageItemStack;
import pixlepix.auracascade.registry.BlockRegistry;
import pixlepix.auracascade.registry.ITTinkererItem;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

import java.util.ArrayList;

/**
 * Created by localmacaccount on 1/23/15.
 */
public class ItemStorageBook extends Item implements ITTinkererItem {
    public ItemStorageBook() {
        super();
        setMaxStackSize(1);
    }

    public static ArrayList<StorageItemStack> getInventory(ItemStack stack) {
        if (stack.stackTagCompound == null) {
            return new ArrayList<StorageItemStack>();
        }
        ArrayList<StorageItemStack> result = new ArrayList<StorageItemStack>();

        NBTTagCompound compound = stack.stackTagCompound;
        NBTTagList itemsList = compound.getTagList("items", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < itemsList.tagCount(); i++) {
            NBTTagCompound itemCompound = itemsList.getCompoundTagAt(i);
            result.add(StorageItemStack.readFromNBT(itemCompound));
        }
        return result;
    }

    public static void setInventory(ItemStack stack, ArrayList<StorageItemStack> newInventory) {
        if (stack.stackTagCompound == null) {
            stack.stackTagCompound = new NBTTagCompound();
        }
        NBTTagList list = new NBTTagList();
        for (StorageItemStack storageItemStack : newInventory) {
            list.appendTag(storageItemStack.writeToNBT());
        }
        stack.stackTagCompound.setTag("items", list);
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        return null;
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (world.getBlock(x, y, z) == Blocks.bookshelf) {
            world.setBlock(x, y, z, BlockRegistry.getFirstBlockFromClass(BlockStorageBookshelf.class));
            TileStorageBookshelf te = (TileStorageBookshelf) world.getTileEntity(x, y, z);
            te.storedBook = stack.copy();
            player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
            return true;
        }
        return false;
    }

    public int getMaxStackSize() {
        return 64;

    }

    public int getHeldStacks() {
        return 5;
    }

    public ItemStack[] getStacks(ItemStack bookStack) {
        ArrayList<ItemStack> result = new ArrayList<ItemStack>();
        ArrayList<StorageItemStack> inv = getInventory(bookStack);
        for (StorageItemStack storageItemStack : inv) {
            result.addAll(storageItemStack.getItemStacks(getMaxStackSize()));
        }
        return (ItemStack[]) result.toArray();
    }

    public boolean isItemValid(ItemStack stack) {
        return true;

    }

    public ItemStack addItemStack(ItemStack bookStack, ItemStack contentStack) {
        ItemStorageBook book = (ItemStorageBook) bookStack.getItem();
        ArrayList<StorageItemStack> inv = getInventory(bookStack);
        StorageItemStack remainingStorage = new StorageItemStack(contentStack);
        for (StorageItemStack storageItemStack : inv) {
            remainingStorage = storageItemStack.merge(remainingStorage, getMaxStackSize());
        }
        while (inv.size() < getHeldStacks() && remainingStorage.stackSize > 0) {
            int delta = Math.min(book.getMaxStackSize(), remainingStorage.stackSize);
            StorageItemStack storageItemStack = new StorageItemStack(remainingStorage.item, delta, remainingStorage.damage, remainingStorage.compound);
            inv.add(storageItemStack);
        }
        setInventory(bookStack, inv);
        return remainingStorage.toItemStack();
    }

    @Override
    public String getItemName() {
        return "storageBook";
    }

    @Override
    public boolean shouldRegister() {
        return true;
    }

    @Override
    public boolean shouldDisplayInTab() {
        return true;
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return null;
    }
}
