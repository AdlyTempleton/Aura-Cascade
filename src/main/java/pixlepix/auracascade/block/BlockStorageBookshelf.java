package pixlepix.auracascade.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import pixlepix.auracascade.block.tile.TileStorageBookshelf;
import pixlepix.auracascade.data.IToolTip;
import pixlepix.auracascade.data.StorageItemStack;
import pixlepix.auracascade.item.ItemStorageBook;
import pixlepix.auracascade.registry.ITTinkererBlock;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by localmacaccount on 1/23/15.
 */
public class BlockStorageBookshelf extends Block implements ITTinkererBlock, ITileEntityProvider, IToolTip {

    public BlockStorageBookshelf() {
        super(Material.wood);
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        return null;
    }

    @Override
    public String getBlockName() {
        return "storageBookshelf";
    }

    @Override
    public boolean shouldRegister() {
        return true;
    }

    @Override
    public boolean shouldDisplayInTab() {
        return false;
    }

    @Override
    public Class<? extends ItemBlock> getItemBlock() {
        return null;
    }

    @Override
    public Class<? extends TileEntity> getTileEntity() {
        return TileStorageBookshelf.class;
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return null;
    }


    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        return new TileStorageBookshelf();
    }

    @Override
    public List<String> getTooltipData(World world, EntityPlayer player, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (!(te instanceof TileStorageBookshelf)) {
            return null;
        }
        TileStorageBookshelf bookshelf = (TileStorageBookshelf) te;
        List<String> result = new ArrayList<String>();
        ArrayList<StorageItemStack> abstractInv = bookshelf.getAbstractInventory();
        for (StorageItemStack stack : abstractInv) {

            if (stack != null) {
                result.add(stack.toItemStack().getDisplayName() + " x" + stack.stackSize);
            }
        }
        if (bookshelf.storedBook != null) {
            result.add(bookshelf.storedBook.getDisplayName());
        }
        return result;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> result = new ArrayList<ItemStack>();
        result.add(new ItemStack(Blocks.bookshelf));
        return result;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int p_149749_6_) {
        TileStorageBookshelf bookshelf = (TileStorageBookshelf) world.getTileEntity(x, y, z);
        if (bookshelf != null && bookshelf.storedBook != null) {
            float f = 0.7F;
            double d0 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            double d1 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            double d2 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            EntityItem entityitem = new EntityItem(world, (double) x + d0, (double) y + d1, (double) z + d2, bookshelf.storedBook);
            entityitem.delayBeforeCanPickup = 10;
            world.spawnEntityInWorld(entityitem);
        }

        super.breakBlock(world, x, y, z, block, p_149749_6_);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        TileStorageBookshelf bookshelf = (TileStorageBookshelf) world.getTileEntity(x, y, z);
        if (player.isSneaking() && !world.isRemote) {
            if (bookshelf.storedBook != null) {
                EntityItem entityItem = new EntityItem(world, player.posX, player.posY, player.posZ, bookshelf.storedBook);
                world.spawnEntityInWorld(entityItem);
                bookshelf.storedBook = null;
                bookshelf.markDirty();
                world.markBlockForUpdate(x, y, z);
                return true;
            }
        }
        if (!world.isRemote && !player.isSneaking() && bookshelf.storedBook == null && player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().getItem() instanceof ItemStorageBook) {
            bookshelf.storedBook = player.inventory.getCurrentItem();
            player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
            bookshelf.markDirty();
            world.markBlockForUpdate(x, y, z);
            return true;
        }
        return false;
    }
}
