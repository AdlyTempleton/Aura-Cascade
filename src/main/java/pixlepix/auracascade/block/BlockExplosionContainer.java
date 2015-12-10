package pixlepix.auracascade.block;

import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.data.recipe.ProcessorRecipe;
import pixlepix.auracascade.lexicon.page.PageCraftingRecipe;
import pixlepix.auracascade.registry.BlockRegistry;
import pixlepix.auracascade.registry.ITTinkererBlock;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;
import pixlepix.auracascade.registry.ThaumicTinkererRecipeMulti;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by localmacaccount on 6/5/15.
 */
public class BlockExplosionContainer extends Block implements ITTinkererBlock {

    public String type;
    public static final PropertyInteger DAMAGE = PropertyInteger.create("damage", 0, 15);

    public BlockExplosionContainer() {
        super(Material.rock);
        //Same as obby
        setResistance(2000F);
        type = "Dirt";
        setTickRandomly(true);
        setHardness(2F);
        setDefaultState(blockState.getBaseState().withProperty(DAMAGE, 0));
    }

    @Override
    public BlockState createBlockState() {
        return new BlockState(this, DAMAGE);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(DAMAGE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(DAMAGE, meta);
    }

    public BlockExplosionContainer(String s) {
        this();
        type = s;
        if (type.equals("Glass")) {
            //setLightLevel(15);
            setLightOpacity(1);
        }
    }

    public static BlockExplosionContainer getBlockFromName(String name) {
        List<Block> blockList = BlockRegistry.getBlockFromClass(BlockExplosionContainer.class);
        for (Block b : blockList) {
            if (((BlockExplosionContainer) b).type != null && ((BlockExplosionContainer) b).type.equals(name)) {
                return (BlockExplosionContainer) b;
            }
        }
        return null;
    }

    public static PageCraftingRecipe getRecipe(String unloc, String name) {
        return new PageCraftingRecipe(unloc, BlockRegistry.getRecipe(getBlockFromName(name)));
    }

    @Override
    public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        super.onBlockPlaced(world, pos, facing, hitX, hitY, hitZ, meta, placer);
        world.scheduleUpdate(pos, this, tickRate(world));
        return getStateFromMeta(meta);
    }

    @Override
    public int tickRate(World world) {
        return 100;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(world, pos, state, rand);
        if (rand.nextDouble() < getChanceToRepair()) {
            int damage = state.getValue(DAMAGE);
            if (damage > 0) {
                world.setBlockState(pos, state.withProperty(DAMAGE, damage - 1), 3);

            }
        }
        world.scheduleUpdate(pos, this, tickRate(world) + rand.nextInt(5));
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    @Override
    public boolean isFullCube() {
        return isOpaqueCube();
    }

    public double getChanceToResist() {
        return 1 - (1 / (getVirtualHealth() / 16D));
    }

    public double getChanceToRepair() {
        return 1 / (getRepairSeconds() / 5D);
    }

    public int getRepairSeconds() {
        if (type.equals("Dirt")) {
            return 120;
        }
        if (type.equals("Wood")) {
            return 5;
        }
        if (type.equals("Glass")) {
            return 120;
        }
        if (type.equals("Cobblestone")) {
            return 30;
        }
        if (type.equals("Stone")) {
            return 60;
        }
        //10m
        if (type.equals("Obsidian")) {
            return 6000;
        }
        return 0;
    }

    public int getVirtualHealth() {
        if (type.equals("Dirt")) {
            return 50;
        }
        if (type.equals("Wood")) {
            return 30;
        }
        if (type.equals("Glass")) {
            return 16;
        }
        if (type.equals("Cobblestone")) {
            return 75;
        }
        if (type.equals("Stone")) {
            return 100;
        }
        if (type.equals("Obsidian")) {
            return 1600;
        }
        return 0;

    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        // TODO Auto-generated method stub
        ArrayList result = new ArrayList<Object>();
        result.add("Wood");
        result.add("Glass");
        result.add("Cobblestone");
        result.add("Stone");
        result.add("Obsidian");
        return result;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, BlockPos pos, EnumFacing side) {
        if (!type.equals("Glass")) {
            return true;
        }
        Block block = world.getBlockState(pos).getBlock();
        return block != this;
    }

    /**
     * Returns which pass should this block be rendered on. 0 for solids and 1 for alpha
     */
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT_MIPPED; // todo 1.8.8
    }

    @Override
    public String getBlockName() {
        return "fortified" + type;
    }

    @Override
    public boolean shouldRegister() {
        return true;
    }

    @Override
    public boolean shouldDisplayInTab() {
        return true;
    }

//    @Override todo 1.8.8
//    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
//        int meta = world.getBlockMetadata(x, y, z);
//        if (AuraCascade.proxy.renderPass == 1) {
//            if (meta != 0) {
//                return AuraCascade.proxy.breakingIcons[getCrackedStage(meta)];
//            } else {
//                return AuraCascade.proxy.blankIcon;
//            }
//        }
//        return blockIcon;
//    }

    public int getCrackedStage(int meta) {
        if (meta <= 5) {
            return meta - 1;
        } else {
            return (int) (4 + Math.ceil((meta - 5) / 2));
        }

    }

    @Override
    public Class<? extends ItemBlock> getItemBlock() {
        return null;
    }

    @Override
    public Class<? extends TileEntity> getTileEntity() {
        return null;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    @Override
    public boolean isOpaqueCube() {
        return type == null || !type.equals("Glass");
    }


    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        if (type.equals("Dirt")) {
            return new ProcessorRecipe(new ItemStack(this), false, new ItemStack(Blocks.end_stone), new ItemStack(Blocks.dirt));
        }
        if (type.equals("Wood")) {
            return new ThaumicTinkererRecipeMulti(new ProcessorRecipe(new ItemStack(this), false, new ItemStack(Blocks.end_stone), new ItemStack(Blocks.planks)),
                    new ProcessorRecipe(new ItemStack(this), false, new ItemStack(Blocks.end_stone), new ItemStack(Blocks.planks, 1, 1)),
                    new ProcessorRecipe(new ItemStack(this), false, new ItemStack(Blocks.end_stone), new ItemStack(Blocks.planks, 1, 2)),
                    new ProcessorRecipe(new ItemStack(this), false, new ItemStack(Blocks.end_stone), new ItemStack(Blocks.planks, 1, 3)),
                    new ProcessorRecipe(new ItemStack(this), false, new ItemStack(Blocks.end_stone), new ItemStack(Blocks.planks, 1, 4)),
                    new ProcessorRecipe(new ItemStack(this), false, new ItemStack(Blocks.end_stone), new ItemStack(Blocks.planks, 1, 5)));
        }
        if (type.equals("Glass")) {
            return new ProcessorRecipe(new ItemStack(this), false, new ItemStack(Blocks.end_stone), new ItemStack(Blocks.glass));
        }
        if (type.equals("Cobblestone")) {
            return new ProcessorRecipe(new ItemStack(this), false, new ItemStack(Blocks.end_stone), new ItemStack(Blocks.cobblestone));
        }
        if (type.equals("Stone")) {
            return new ProcessorRecipe(new ItemStack(this), false, new ItemStack(Blocks.end_stone), new ItemStack(Blocks.stone));
        }
        if (type.equals("Obsidian")) {
            return new ProcessorRecipe(new ItemStack(this), false, new ItemStack(Blocks.end_stone), new ItemStack(Blocks.obsidian));
        }
        return null;
    }

    @Override
    public int getCreativeTabPriority() {
        return 23;
    }
}
