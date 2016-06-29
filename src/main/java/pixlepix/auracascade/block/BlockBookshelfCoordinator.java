package pixlepix.auracascade.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.block.tile.TileBookshelfCoordinator;
import pixlepix.auracascade.data.EnumRainbowColor;
import pixlepix.auracascade.data.IToolTip;
import pixlepix.auracascade.item.ItemMaterial;
import pixlepix.auracascade.main.EnumColor;
import pixlepix.auracascade.registry.CraftingBenchRecipe;
import pixlepix.auracascade.registry.ITTinkererBlock;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by localmacaccount on 1/24/15.
 */
public class BlockBookshelfCoordinator extends Block implements ITTinkererBlock, ITileEntityProvider, IToolTip {

    public BlockBookshelfCoordinator() {
        super(Material.WOOD);
        setHardness(2F);
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        return null;
    }

    @Override
    public String getBlockName() {
        return "storageBookshelfCoordinator";
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
        return TileBookshelfCoordinator.class;
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return new CraftingBenchRecipe(new ItemStack(this), "IPI", "IBI", "III", 'P', ItemMaterial.getPrism(), 'I', ItemMaterial.getIngot(EnumRainbowColor.BLUE), 'B', new ItemStack(Blocks.BOOKSHELF));
    }

    @Override
    public int getCreativeTabPriority() {
        return -18;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        return new TileBookshelfCoordinator();
    }

    @Override
    public List<String> getTooltipData(World world, EntityPlayer player, BlockPos pos) {
        ArrayList<String> result = new ArrayList<String>();
        TileBookshelfCoordinator coordinator = (TileBookshelfCoordinator) world.getTileEntity(pos);
        result.add("Power used last second: " + coordinator.lastPower);
        result.add("Power needed: " + coordinator.neededPower);
        return result;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileBookshelfCoordinator coordinator = (TileBookshelfCoordinator) world.getTileEntity(pos);
        if (coordinator.lastPower >= coordinator.neededPower) {
            player.openGui(AuraCascade.instance, 1, world, pos.getX(), pos.getY(), pos.getZ());
        } else if (!world.isRemote) {
            player.addChatComponentMessage(new TextComponentString(EnumColor.DARK_RED + "Not enough power to activate the Coordinator"));
        }
        return true;
    }


}
