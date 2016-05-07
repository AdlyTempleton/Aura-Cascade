package pixlepix.auracascade.block;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import pixlepix.auracascade.block.tile.AuraTilePumpBase;
import pixlepix.auracascade.block.tile.ConsumerTile;
import pixlepix.auracascade.registry.CraftingBenchRecipe;
import pixlepix.auracascade.registry.ITTinkererBlock;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

/**
 * Created by localmacaccount on 5/27/15.
 */
public class BlockMonitor extends Block implements ITTinkererBlock {
    public BlockMonitor() {
        super(Material.redstoneLight);
        setHardness(3);
        setHarvestLevel("pickaxe", 2);
    }

    @Override
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return true;
    }


    @Override
    public int getWeakPower(IBlockState blockState, IBlockAccess world, BlockPos pos, EnumFacing side) {
        EnumFacing powerDirection = side.getOpposite();
        Block b = world.getBlockState(pos.offset(powerDirection)).getBlock();


        for (EnumFacing direction : EnumFacing.VALUES) {
            if (direction != powerDirection) {
                TileEntity auraTile = world.getTileEntity(pos.offset(direction));

                if (auraTile instanceof AuraTilePumpBase) {
                    return ((AuraTilePumpBase) auraTile).pumpPower > 0 ? 0 : 15;
                }
                if (auraTile instanceof ConsumerTile) {
                    return ((ConsumerTile) auraTile).validItemsNearby() ? 0 : 15;
                }
            }
        }
        return 0;
    }

    /**
     * Can this block provide power. Only wire currently seems to have this change based on its state.
     */


    @Override
    public boolean canProvidePower(IBlockState state) {
        return true;
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        return null;
    }

    @Override
    public String getBlockName() {
        return "monitor";
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
        return null;
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return new CraftingBenchRecipe(new ItemStack(this), "RRR", "RAR", "RRR", 'R', new ItemStack(Items.redstone), 'A', AuraBlock.getAuraNodeItemstack());
    }

    @Override
    public int getCreativeTabPriority() {
        return 3;
    }
}
