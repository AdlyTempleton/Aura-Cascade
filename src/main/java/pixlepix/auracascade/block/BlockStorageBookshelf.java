package pixlepix.auracascade.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import pixlepix.auracascade.block.tile.TileStorageBookshelf;
import pixlepix.auracascade.data.IToolTip;
import pixlepix.auracascade.data.StorageItemStack;
import pixlepix.auracascade.registry.ITTinkererBlock;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

import java.util.ArrayList;
import java.util.List;

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
        return result;
        
    }
}
