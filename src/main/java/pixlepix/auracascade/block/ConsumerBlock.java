package pixlepix.auracascade.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import pixlepix.auracascade.block.tile.*;
import pixlepix.auracascade.data.AuraQuantity;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.data.IToolTip;
import pixlepix.auracascade.data.recipe.PylonRecipe;
import pixlepix.auracascade.data.recipe.PylonRecipeComponent;
import pixlepix.auracascade.item.ItemAuraCrystal;
import pixlepix.auracascade.item.ItemMaterial;
import pixlepix.auracascade.item.ItemRedHole;
import pixlepix.auracascade.main.AuraUtil;
import pixlepix.auracascade.registry.BlockRegistry;
import pixlepix.auracascade.registry.CraftingBenchRecipe;
import pixlepix.auracascade.registry.ITTinkererBlock;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pixlepix on 11/29/14.
 */
public class ConsumerBlock extends Block implements IToolTip, ITTinkererBlock, ITileEntityProvider {

    public static final PropertyEnum<EnumFacing> FACING = PropertyEnum.create("facing", EnumFacing.class, EnumFacing.Plane.HORIZONTAL);

    public String name;

    public ConsumerBlock() {
        super(Material.IRON);
        this.name = "furnace";
        setHardness(2F);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.SOUTH));
    }

    public ConsumerBlock(String name) {
        super(Material.IRON);
        this.name = name;
        setHardness(2F);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.SOUTH));
    }

    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        if (meta < 2 || meta > 5) {
            meta = 2;
        }
        return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
    }

    public static ConsumerBlock getBlockFromName(String name) {
        List<Block> blockList = BlockRegistry.getBlockFromClass(ConsumerBlock.class);
        for (Block b : blockList) {
            if (((ConsumerBlock) b).name != null && ((ConsumerBlock) b).name.equals(name)) {
                return (ConsumerBlock) b;
            }
        }
        return null;
    }

    @Override
    public void onBlockPlacedBy(World w, BlockPos pos, IBlockState state, EntityLivingBase livingBase, ItemStack stack) {
        w.setBlockState(pos, state.withProperty(FACING, livingBase.getHorizontalFacing().getOpposite()));
        AuraUtil.updateMonitor(w, pos);
    }
    @Override
    public void breakBlock(World w, BlockPos pos, IBlockState state) {
        super.breakBlock(w, pos, state);
        AuraUtil.updateMonitor(w, pos);
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        if (name != null) {
            if (name.equals("plant")) {
                return new PylonRecipe(new ItemStack(this), new PylonRecipeComponent(new AuraQuantity(EnumAura.GREEN_AURA, 100000), ItemMaterial.getGem(EnumAura.GREEN_AURA)));
            }
            if (name.equals("ore")) {
                return new CraftingBenchRecipe(new ItemStack(this), "IFI", "FIF", "IFI", 'F', new ItemStack(Blocks.FURNACE), 'I', ItemAuraCrystal.getCrystalFromAura(EnumAura.WHITE_AURA));
            }
            if (name.equals("loot")) {
                return new PylonRecipe(new ItemStack(this), new PylonRecipeComponent(new AuraQuantity(EnumAura.YELLOW_AURA, 100000), ItemMaterial.getGem(EnumAura.YELLOW_AURA)));
            }
            if (name.equals("mob")) {
                return new PylonRecipe(new ItemStack(this), new PylonRecipeComponent(new AuraQuantity(EnumAura.VIOLET_AURA, 100000), ItemMaterial.getGem(EnumAura.VIOLET_AURA)));
            }
            if (name.equals("angel")) {
                return new PylonRecipe(new ItemStack(this), new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 300000), ItemMaterial.getPrism()),
                        new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 200000), new ItemStack(Items.IRON_INGOT)),
                        new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 200000), new ItemStack(Items.IRON_INGOT)),
                        new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 200000), new ItemStack(Items.IRON_INGOT)));
            }
            if (name.equals("nether")) {
                return new PylonRecipe(new ItemStack(this), new PylonRecipeComponent(new AuraQuantity(EnumAura.RED_AURA, 100000), ItemMaterial.getGem(EnumAura.RED_AURA)));
            }
            if (name.equals("potion")) {
                return new PylonRecipe(new ItemStack(this), new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 100000), ItemMaterial.getGem(EnumAura.ORANGE_AURA)));
            }
            if (name.equals("enchant")) {
                return new PylonRecipe(new ItemStack(this), new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 250000), ItemMaterial.getGem(EnumAura.BLACK_AURA)));
            }
            if (name.equals("oreAdv")) {
                return new CraftingBenchRecipe(new ItemStack(this), "GPG", "GCG", "GGG", 'P', ItemMaterial.getPrism(), 'G', new ItemStack(Blocks.GLASS), 'C', new ItemStack(getBlockFromName("ore")));
            }
            if (name.equals("dye")) {
                return new CraftingBenchRecipe(new ItemStack(this), "CCC", "CFC", "CCC", 'F', new ItemStack(Items.SHEARS), 'C', Blocks.WOOL);
            }
            if (name.equals("miner")) {
                return new CraftingBenchRecipe(new ItemStack(this), "PAP", "IRI", "IRI", 'P', ItemMaterial.getPrism(), 'A', new ItemStack(Items.DIAMOND_PICKAXE), 'I', new ItemStack(Items.IRON_INGOT), 'R', BlockRegistry.getFirstItemFromClass(ItemRedHole.class));
            }
            if (name.equals("end")) {
                return new CraftingBenchRecipe(new ItemStack(this), "EPE", "ENE", "EEE", 'P', ItemMaterial.getPrism(), 'E', new ItemStack(Blocks.END_STONE), 'N', new ItemStack(getBlockFromName("nether")));
            }
            if (name.equals("fish")) {
                return new CraftingBenchRecipe(new ItemStack(this), "RRR", "III", 'R', new ItemStack(Items.FISHING_ROD), 'I', ItemMaterial.getIngot(EnumAura.BLUE_AURA));

            }
        }
        return new CraftingBenchRecipe(new ItemStack(this), "FFF", "FIF", "FFF", 'F', new ItemStack(Blocks.FURNACE), 'I', ItemAuraCrystal.getCrystalFromAura(EnumAura.WHITE_AURA));
    }

    @Override
    public int getCreativeTabPriority() {
        return 0;
    }

    @SuppressWarnings({ "unchecked", "rawtypes"})
	@Override
    public ArrayList<Object> getSpecialParameters() {
        ArrayList result = new ArrayList<Object>();
        result.add("plant");
        result.add("ore");
        result.add("loot");
        result.add("mob");
        result.add("angel");
        result.add("nether");
        result.add("enchant");
        result.add("potion");
        result.add("dye");
        result.add("oreAdv");
        result.add("miner");
        result.add("fish");
        result.add("end");
        return result;
    }

    @Override
    public String getBlockName() {
        return "consumerBlock" + name;
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
        if (name != null) {
            if (name.equals("plant")) {
                return PlanterTile.class;
            }
            if (name.equals("ore")) {
                return ProcessorTile.class;
            }
            if (name.equals("oreAdv")) {
                return ProcessorTileAdv.class;
            }
            if (name.equals("loot")) {
                return LootTile.class;
            }

            if (name.equals("mob")) {
                return SpawnTile.class;
            }
            if (name.equals("angel")) {
                return AngelSteelTile.class;
            }
            if (name.equals("nether")) {
                return TileRitualNether.class;
            }
            if (name.equals("potion")) {
                return PotionTile.class;
            }
            if (name.equals("enchant")) {
                return EnchanterTile.class;
            }
            if (name.equals("dye")) {
                return DyeTile.class;
            }
            if (name.equals("miner")) {
                return MinerTile.class;
            }
            if (name.equals("end")) {
                return TileRitualEnd.class;
            }
            if (name.equals("fish")) {
                return FisherTile.class;
                
            }
        }
        return FurnaceTile.class;
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos ) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof ConsumerTile) {
            return (int) (15D * (((double) ((ConsumerTile) tileEntity).progress) / ((double) ((ConsumerTile) tileEntity).getMaxProgress())));
        } else {
            return super.getComparatorInputOverride(state, world, pos);
        }
    }


    @Override
    public TileEntity createNewTileEntity(World world, int meta) {

        try {
            return getTileEntity().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<String> getTooltipData(World world, EntityPlayer player, BlockPos pos) {
        List<String> result = new ArrayList<String>();
        if (world.getTileEntity(pos) instanceof ConsumerTile) {
            ConsumerTile consumerTile = (ConsumerTile) world.getTileEntity(pos);
            result.add("Progress: " + consumerTile.progress + " / " + consumerTile.getMaxProgress());
            result.add("Power per progress: " + consumerTile.getPowerPerProgress());
            result.add("Last Power: " + consumerTile.lastPower);

        }
        return result;
    }
}
