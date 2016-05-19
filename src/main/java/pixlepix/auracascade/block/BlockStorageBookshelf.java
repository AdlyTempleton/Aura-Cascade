package pixlepix.auracascade.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import pixlepix.auracascade.block.tile.TileStorageBookshelf;
import pixlepix.auracascade.data.IToolTip;
import pixlepix.auracascade.data.StorageItemStack;
import pixlepix.auracascade.item.ItemStorageBook;
import pixlepix.auracascade.main.AuraUtil;
import pixlepix.auracascade.registry.ITTinkererBlock;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by localmacaccount on 1/23/15.
 */
public class BlockStorageBookshelf extends Block implements ITTinkererBlock, ITileEntityProvider, IToolTip {

    public BlockStorageBookshelf() {
        super(Material.WOOD);
        setHardness(2F);
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
    public float getEnchantPowerBonus(World world, BlockPos pos) {
        return 1;
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
    public int getCreativeTabPriority() {
        return -18;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        return new TileStorageBookshelf();
    }

    @Override
    public List<String> getTooltipData(World world, EntityPlayer player, BlockPos pos) {
        TileEntity te = world.getTileEntity(pos);
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
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        ArrayList<ItemStack> result = new ArrayList<ItemStack>();
        result.add(new ItemStack(Blocks.BOOKSHELF));
        return result;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileStorageBookshelf bookshelf = (TileStorageBookshelf) world.getTileEntity(pos);
        if (bookshelf != null && bookshelf.storedBook != null) {
            double d0 = AuraUtil.getDropOffset(world);
            double d1 = AuraUtil.getDropOffset(world);
            double d2 = AuraUtil.getDropOffset(world);
            EntityItem entityitem = new EntityItem(world, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, bookshelf.storedBook);
            AuraUtil.setItemDelay(entityitem, 10);
            world.spawnEntityInWorld(entityitem);
        }

        super.breakBlock(world, pos, state);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileStorageBookshelf bookshelf = (TileStorageBookshelf) world.getTileEntity(pos);
        if (player.isSneaking() && !world.isRemote) {
            if (bookshelf.storedBook != null) {
                EntityItem entityItem = new EntityItem(world, player.posX, player.posY, player.posZ, bookshelf.storedBook);
                world.spawnEntityInWorld(entityItem);
                bookshelf.storedBook = null;
                bookshelf.onStoredBookChange();
                return true;
            }
        }
        if (!world.isRemote && !player.isSneaking() && bookshelf.storedBook == null && player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().getItem() instanceof ItemStorageBook) {
            bookshelf.storedBook = player.inventory.getCurrentItem();
            player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
            bookshelf.onStoredBookChange();
            return true;
        }
        return false;
    }
}
