package pixlepix.auracascade.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import pixlepix.auracascade.block.tile.TileStorageBookshelf;
import pixlepix.auracascade.registry.ITTinkererBlock;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

import java.util.ArrayList;

/**
 * Created by localmacaccount on 1/23/15.
 */
public class BlockStorageBookshelf extends Block implements ITTinkererBlock, ITileEntityProvider {

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
}
