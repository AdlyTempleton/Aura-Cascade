package pixlepix.auracascade.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
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


    public BlockExplosionContainer() {
        super(Material.rock);
        //Same as obby
        setResistance(2000F);
        type = "Dirt";
        setTickRandomly(true);
        setHardness(2F);
    }

    public BlockExplosionContainer(String s) {
        this();
        type = s;
        if (type.equals("Glass")) {
            setLightLevel(15);
            setLightOpacity(0);
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
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
        super.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, meta);
        world.scheduleBlockUpdate(x, y, z, this, tickRate(world));
        return meta;
    }

    @Override
    public int tickRate(World world) {
        return 100;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        super.updateTick(world, x, y, z, rand);
        if (rand.nextDouble() < getChanceToRepair()) {
            int meta = world.getBlockMetadata(x, y, z);
            if (meta > 0) {
                world.setBlockMetadataWithNotify(x, y, z, meta - 1, 3);

            }
        }
        world.scheduleBlockUpdate(x, y, z, this, tickRate(world) + rand.nextInt(5));
    }

    /**
     * Determines if this block should render in this pass.
     *
     * @param pass The pass in question
     * @return True to render
     */
    @Override
    public boolean canRenderInPass(int pass) {
        AuraCascade.proxy.renderPass = pass;
        return true;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    @Override
    public boolean renderAsNormalBlock() {
        return true;
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
    public void registerBlockIcons(IIconRegister register) {
        blockIcon = register.registerIcon("aura:fortified" + type);
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
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        if (!type.equals("Glass")) {
            return true;
        }
        Block block = world.getBlock(x, y, z);
        return block != this;
    }

    /**
     * Returns which pass should this block be rendered on. 0 for solids and 1 for alpha
     */
    @Override
    public int getRenderBlockPass() {
        return 1;
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

    /**
     * Gets the block's texture. Args: side, meta
     *
     * @param side
     * @param meta
     */
    @Override
    public IIcon getIcon(int side, int meta) {

        return blockIcon;
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        int meta = world.getBlockMetadata(x, y, z);
        if (AuraCascade.proxy.renderPass == 1) {
            if (meta != 0) {
                return AuraCascade.proxy.breakingIcons[getCrackedStage(meta)];
            } else {
                return AuraCascade.proxy.blankIcon;
            }
        }
        return blockIcon;
    }

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
