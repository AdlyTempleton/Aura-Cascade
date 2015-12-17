package pixlepix.auracascade.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import pixlepix.auracascade.registry.ITTinkererBlock;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by pixlepix on 12/20/14.
 */
public class FairyTorch extends Block implements ITTinkererBlock {

    public final String name = "fairyTorch";

    public FairyTorch() {
        super(Material.glass);
        setTickRandomly(true);

        setLightLevel(1F);
        setBlockBounds(0, 0, 0, 0, 0, 0);
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isReplaceable(World world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean isAir(IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    public int quantityDropped(Random p_149745_1_) {
        return 0;
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        return new ArrayList<ItemStack>();
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(world, pos, state, rand);
        if (!world.isRemote) {
            world.setBlockToAir(pos);
        }
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        return null;
    }

    @Override
    public String getBlockName() {
        return name;
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
        return null;
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return null;
    }

    @Override
    public int getCreativeTabPriority() {
        return 0;
    }
}
