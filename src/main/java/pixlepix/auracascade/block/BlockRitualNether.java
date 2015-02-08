package pixlepix.auracascade.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import pixlepix.auracascade.block.tile.TileRitualNether;
import pixlepix.auracascade.registry.ITTinkererBlock;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

import java.util.ArrayList;

/**
 * Created by localmacaccount on 2/8/15.
 */
public class BlockRitualNether extends Block implements ITTinkererBlock, ITileEntityProvider {
    public BlockRitualNether() {
        super(Material.cloth);
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        return null;
    }

    @Override
    public String getBlockName() {
        return "netherRitual";
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
    public Class<? extends ItemBlock> getItemBlock() {
        return null;
    }

    @Override
    public Class<? extends TileEntity> getTileEntity() {
        return TileRitualNether.class;
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return null;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileRitualNether();
    }
}
