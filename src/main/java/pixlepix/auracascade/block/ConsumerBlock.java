package pixlepix.auracascade.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import pixlepix.auracascade.block.tile.*;
import pixlepix.auracascade.data.AuraQuantity;
import pixlepix.auracascade.data.CoordTuple;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.data.IToolTip;
import pixlepix.auracascade.data.recipe.PylonRecipe;
import pixlepix.auracascade.data.recipe.PylonRecipeComponent;
import pixlepix.auracascade.item.ItemAuraCrystal;
import pixlepix.auracascade.item.ItemMaterial;
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

    public String name;
    public IIcon front;
    public IIcon side;
    public IIcon top;

    public ConsumerBlock() {
        super(Material.iron);
        this.name = "furnace";
        setHardness(2F);
    }

    public ConsumerBlock(String name) {
        super(Material.iron);
        this.name = name;
        setHardness(2F);
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

    public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase livingBase, ItemStack stack) {
        int l = MathHelper.floor_double((double) (livingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (l == 0) {
            w.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }

        if (l == 1) {
            w.setBlockMetadataWithNotify(x, y, z, 5, 2);
        }

        if (l == 2) {
            w.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }

        if (l == 3) {
            w.setBlockMetadataWithNotify(x, y, z, 4, 2);
        }

        updateMonitor(w, x, y, z);
    }

    public void updateMonitor(World w, int x, int y, int z) {
        for (ForgeDirection d1 : ForgeDirection.VALID_DIRECTIONS) {
            Block b = new CoordTuple(x, y, z).add(d1).getBlock(w);
            if (b instanceof BlockMonitor) {

                for (ForgeDirection d2 : ForgeDirection.VALID_DIRECTIONS) {
                    CoordTuple tuple = new CoordTuple(x, y, z).add(d2).add(d1);
                    Block b2 = tuple.getBlock(w);
                    b2.onNeighborBlockChange(w, tuple.getX(), tuple.getY(), tuple.getZ(), b);
                }
            }
        }
    }

    @Override
    public void breakBlock(World w, int x, int y, int z, Block b, int p_149749_6_) {
        super.breakBlock(w, x, y, z, b, p_149749_6_);
        updateMonitor(w, x, y, z);
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        if (name.equals("furnace")) {
            top = iconRegister.registerIcon("aura:auraFurnace_top");
            side = iconRegister.registerIcon("aura:auraFurnace_side");
            front = iconRegister.registerIcon("aura:auraFurnace_front");
        }

        if (name.equals("ore")) {
            blockIcon = iconRegister.registerIcon("aura:auraOre");
        }
        if (name.equals("mob")) {
            blockIcon = iconRegister.registerIcon("aura:auraMob");
        }

        if (name.equals("plant")) {
            blockIcon = iconRegister.registerIcon("aura:auraGrow");
            top = iconRegister.registerIcon("aura:auraGrowTop");
        }
        if (name.equals("angel")) {
            blockIcon = iconRegister.registerIcon("aura:auraAngel");
            top = iconRegister.registerIcon("aura:auraAngelTop");
        }
        if (name.equals("loot")) {
            blockIcon = iconRegister.registerIcon("aura:auraLoot");
            top = iconRegister.registerIcon("aura:auraLootTop");
        }
        if (name.equals("nether")) {
            blockIcon = iconRegister.registerIcon("aura:ritualNether");

        }
        if (name.equals("potion")) {
            blockIcon = iconRegister.registerIcon("aura:brewer");

        }
        if (name.equals("enchant")) {
            blockIcon = iconRegister.registerIcon("aura:enchanter");

        }
        if (name.equals("oreAdv")) {
            blockIcon = iconRegister.registerIcon("aura:auraOreAdv");

        }

        if (name.equals("dye")) {
            blockIcon = iconRegister.registerIcon("aura:dye");
        }
        if (name.equals("miner")) {
            blockIcon = iconRegister.registerIcon("aura:miner");
        }
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (top != null && this.side != null && front != null) {
            if (side == 1 || side == 0) {
                return top;
            }
            if (side == meta) {
                return front;
            }
            return this.side;
        }
        if (top != null) {
            if (side == 1 || side == 0) {
                return top;
            }
            return blockIcon;
        }
        return blockIcon;
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        if (name != null) {
            if (name.equals("plant")) {
                return new PylonRecipe(new ItemStack(this), new PylonRecipeComponent(new AuraQuantity(EnumAura.GREEN_AURA, 100000), ItemMaterial.getGem(EnumAura.GREEN_AURA)));
            }
            if (name.equals("ore")) {
                return new CraftingBenchRecipe(new ItemStack(this), "IFI", "FIF", "IFI", 'F', new ItemStack(Blocks.furnace), 'I', ItemAuraCrystal.getCrystalFromAura(EnumAura.WHITE_AURA));
            }
            if (name.equals("loot")) {
                return new PylonRecipe(new ItemStack(this), new PylonRecipeComponent(new AuraQuantity(EnumAura.YELLOW_AURA, 100000), ItemMaterial.getGem(EnumAura.YELLOW_AURA)));
            }
            if (name.equals("mob")) {
                return new PylonRecipe(new ItemStack(this), new PylonRecipeComponent(new AuraQuantity(EnumAura.VIOLET_AURA, 100000), ItemMaterial.getGem(EnumAura.VIOLET_AURA)));
            }
            if (name.equals("angel")) {
                return new PylonRecipe(new ItemStack(this), new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 300000), ItemMaterial.getPrism()),
                        new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 200000), new ItemStack(Items.iron_ingot)),
                        new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 200000), new ItemStack(Items.iron_ingot)),
                        new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 200000), new ItemStack(Items.iron_ingot)));
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
                return new CraftingBenchRecipe(new ItemStack(this), "GPG", "GCG", "GGG", 'P', ItemMaterial.getPrism(), 'G', new ItemStack(Blocks.glass), 'C', new ItemStack(getBlockFromName("ore")));
            }
            if (name.equals("dye")) {
                return new CraftingBenchRecipe(new ItemStack(this), "CCC", "CFC", "CCC", 'F', new ItemStack(Items.shears), 'C', new ItemStack(Blocks.wool));
            }
            if (name.equals("miner")) {
                return new CraftingBenchRecipe(new ItemStack(this), "PAP", "IRI", "IRI", 'P', ItemMaterial.getPrism(), 'A', new ItemStack(Items.diamond_pickaxe), 'I', new ItemStack(Items.iron_ingot), 'R', new ItemStack(Items.redstone));
            }
        }
        return new CraftingBenchRecipe(new ItemStack(this), "FFF", "FIF", "FFF", 'F', new ItemStack(Blocks.furnace), 'I', ItemMaterial.getIngot(EnumAura.WHITE_AURA));
    }

    @Override
    public int getCreativeTabPriority() {
        return 0;
    }

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
        return result;
    }

    @Override
    public String getBlockName() {
        // TODO Auto-generated method stub
        return "consumerBlock" + name;
    }

    @Override
    public boolean shouldRegister() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean shouldDisplayInTab() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public Class<? extends ItemBlock> getItemBlock() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Class<? extends TileEntity> getTileEntity() {
        if (name != null) {
            if (name.equals("plant")) {
                return PlanterTile.class;
            }
            if (name.equals("ore")) {
                return OreTile.class;
            }
            if (name.equals("oreAdv")) {
                return OreTileAdv.class;
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
                return ExploderTile.class;
            }
        }
        return FurnaceTile.class;
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World world, int x, int y, int z, int meta) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity instanceof ConsumerTile) {
            return (int) (15D * (((double) ((ConsumerTile) tileEntity).progress) / ((double) ((ConsumerTile) tileEntity).getMaxProgress())));
        } else {
            return super.getComparatorInputOverride(world, x, y, z, meta);
        }
    }


    @Override
    public TileEntity createNewTileEntity(World world, int meta) {

        try {
            return getTileEntity().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<String> getTooltipData(World world, EntityPlayer player, int x, int y, int z) {
        List<String> result = new ArrayList<String>();
        if (world.getTileEntity(x, y, z) instanceof ConsumerTile) {
            ConsumerTile consumerTile = (ConsumerTile) world.getTileEntity(x, y, z);
            result.add("Progress: " + consumerTile.progress + " / " + consumerTile.getMaxProgress());
            result.add("Power per progress: " + consumerTile.getPowerPerProgress());
            result.add("Last Power: " + consumerTile.lastPower);

        }
        return result;
    }
}
