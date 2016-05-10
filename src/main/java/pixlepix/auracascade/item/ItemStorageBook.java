package pixlepix.auracascade.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.oredict.OreDictionary;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.block.BlockStorageBookshelf;
import pixlepix.auracascade.block.tile.TileStorageBookshelf;
import pixlepix.auracascade.registry.BlockRegistry;
import pixlepix.auracascade.registry.ITTinkererItem;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by localmacaccount on 1/23/15.
 */
public abstract class ItemStorageBook extends Item implements ITTinkererItem {
    public ItemStorageBook() {
        super();
        setMaxStackSize(1);
    }

    public static void setInventory(ItemStack stack, ArrayList<ItemStack> newInventory) {
        if (stack.getTagCompound() == null) {
            stack.setTagCompound(new NBTTagCompound());
        }
        NBTTagList list = new NBTTagList();
        for (ItemStack itemStack : newInventory) {
            NBTTagCompound nbt = new NBTTagCompound();
            if (itemStack != null) {
                itemStack.writeToNBT(nbt);
            }
            list.appendTag(nbt);
        }
        stack.getTagCompound().setTag("items", list);
    }

    public boolean isValid(ItemStack stack, Block[] blocks, Item[] items, String[] ores) {
        Item item = stack.getItem();
        if (blocks != null && Block.getBlockFromItem(item) != null) {
            Block block = Block.getBlockFromItem(item);
            if (Arrays.asList(blocks).contains(block)) {
                return true;
            }
        }
        if (items != null && Arrays.asList(items).contains(item)) {
            return true;
        }
        int[] ids = OreDictionary.getOreIDs(stack);
        if (ores != null) {
            for (int i : ids) {
                String s = OreDictionary.getOreName(i);
                if (Arrays.asList(ores).contains(s.toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<ItemStack> getInventory(ItemStack stack) {
        ArrayList<ItemStack> result = new ArrayList<ItemStack>();

        NBTTagCompound compound = stack.getTagCompound();
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
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote && ((world.getBlockState(pos).getBlock() != null && world.getBlockState(pos).getBlock() == AuraCascade.proxy.chiselBookshelf) || world.getBlockState(pos).getBlock() == Blocks.bookshelf)) {
            world.setBlockState(pos, BlockRegistry.getFirstBlockFromClass(BlockStorageBookshelf.class).getDefaultState());
            TileStorageBookshelf te = (TileStorageBookshelf) world.getTileEntity(pos);
            te.storedBook = stack.copy();
            te.markDirty();
            world.markBlocksDirtyVertical(pos.getX(), pos.getZ(), pos.getX(), pos.getZ());
            player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
            return EnumActionResult.PASS;
        }
        return EnumActionResult.FAIL;
    }

    public abstract int getMaxStackSize();

    public abstract int getHeldStacks();

    public abstract boolean isItemValid(ItemStack stack, TileStorageBookshelf tileStorageBookshelf);

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

    @Override
    public int getCreativeTabPriority() {
        return -18;
    }
}
