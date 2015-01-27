package pixlepix.auracascade.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
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
public abstract class ItemStorageBook extends Item implements ITTinkererItem {
    public ItemStorageBook() {
        super();
        setMaxStackSize(1);
    }

    public static void setInventory(ItemStack stack, ArrayList<ItemStack> newInventory) {
        if (stack.stackTagCompound == null) {
            stack.stackTagCompound = new NBTTagCompound();
        }
        NBTTagList list = new NBTTagList();
        for (ItemStack itemStack : newInventory) {
            NBTTagCompound nbt = new NBTTagCompound();
            if (itemStack != null) {
                itemStack.writeToNBT(nbt);
            }
            list.appendTag(nbt);
        }
        stack.stackTagCompound.setTag("items", list);
    }

    public ArrayList<ItemStack> getInventory(ItemStack stack) {
        ArrayList<ItemStack> result = new ArrayList<ItemStack>();

        NBTTagCompound compound = stack.stackTagCompound;
        if (compound != null) {
            NBTTagList itemsList = compound.getTagList("items", Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < itemsList.tagCount(); i++) {
                NBTTagCompound itemCompound = itemsList.getCompoundTagAt(i);
                //Key "Count" is a key used in itemstack nbt methods
                result.add(itemCompound.hasKey("Count") ? ItemStack.loadItemStackFromNBT(itemCompound) : null);
            }
        }
        while (result.size() < getActualCount()) {
            result.add(null);
        }
        return result;
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        return null;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote && world.getBlock(x, y, z) == Blocks.bookshelf) {
            world.setBlock(x, y, z, BlockRegistry.getFirstBlockFromClass(BlockStorageBookshelf.class));
            TileStorageBookshelf te = (TileStorageBookshelf) world.getTileEntity(x, y, z);
            te.storedBook = stack.copy();
            te.markDirty();
            world.markBlockForUpdate(x, y, z);
            player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
            return true;
        }
        return false;
    }

    public abstract int getMaxStackSize();

    public abstract int getHeldStacks();

    public abstract boolean isItemValid(ItemStack stack);
    public int getActualCount() {
        return (int) (Math.ceil(getMaxStackSize() / 64F) * getHeldStacks());
        
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
