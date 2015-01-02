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
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import pixlepix.auracascade.block.tile.*;
import pixlepix.auracascade.data.AuraQuantity;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.data.IToolTip;
import pixlepix.auracascade.data.recipe.PylonRecipe;
import pixlepix.auracascade.data.recipe.PylonRecipeComponent;
import pixlepix.auracascade.item.ItemAuraCrystal;
import pixlepix.auracascade.registry.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pixlepix on 11/29/14.
 */
public class ConsumerBlock extends Block implements IToolTip, ITTinkererBlock, ITileEntityProvider {

    public ConsumerBlock() {
        super(Material.iron);
        this.name = "furnace";
        setHardness(2F);
    }

    public static ConsumerBlock getBlockFromName(String name){
        List<Block> blockList = BlockRegistry.getBlockFromClass(ConsumerBlock.class);
        for(Block b:blockList){
            if(((ConsumerBlock)b).name!= null && ((ConsumerBlock)b).name.equals(name)){
                return (ConsumerBlock) b;
            }
        }
        return null;
    }

    public ConsumerBlock(String name) {
        super(Material.iron);
        this.name = name;
        setHardness(2F);
    }

    public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
    {
        int l = MathHelper.floor_double((double) (p_149689_5_.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (l == 0)
        {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 2, 2);
        }

        if (l == 1)
        {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 5, 2);
        }

        if (l == 2)
        {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 3, 2);
        }

        if (l == 3)
        {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 4, 2);
        }

        if (p_149689_6_.hasDisplayName())
        {
            ((TileEntityFurnace)p_149689_1_.getTileEntity(p_149689_2_, p_149689_3_, p_149689_4_)).func_145951_a(p_149689_6_.getDisplayName());
        }
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        if(name.equals("furnace")){
            top = iconRegister.registerIcon("aura:auraFurnace_top");
            side = iconRegister.registerIcon("aura:auraFurnace_side");
            front = iconRegister.registerIcon("aura:auraFurnace_front");
        }
    }

    @Override
    public IIcon getIcon(int side, int meta){
        if(side == 1 || side == 0){
            return top;
        }
        if(side == meta){
            return front;
        }
        return this.side;
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        if(name != null) {
            if (name.equals("plant")) {
                return new PylonRecipe(new ItemStack(this), new PylonRecipeComponent(new AuraQuantity(EnumAura.GREEN_AURA, 200000), new ItemStack(Items.golden_hoe)));
            }
            if (name.equals("ore")){
                return new PylonRecipe(new ItemStack(this), new PylonRecipeComponent(new AuraQuantity(EnumAura.RED_AURA, 200000), new ItemStack(Blocks.furnace)));
            }
            if(name.equals("loot")){
                return new PylonRecipe(new ItemStack(this), new PylonRecipeComponent(new AuraQuantity(EnumAura.YELLOW_AURA, 200000), new ItemStack(Blocks.mossy_cobblestone)));
            }
            if(name.equals("mob")){
                return new PylonRecipe(new ItemStack(this), new PylonRecipeComponent(new AuraQuantity(EnumAura.VIOLET_AURA, 200000), new ItemStack(Items.egg)));
            }
            if(name.equals("angel")){
                return new PylonRecipe(new ItemStack(this), new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 200000), new ItemStack(Items.blaze_rod)));
            }
        }
        return new CraftingBenchRecipe(new ItemStack(this), "CCC", "CFC", "CCC", 'F', new ItemStack(Blocks.furnace), 'C', new ItemStack(BlockRegistry.getFirstItemFromClass(ItemAuraCrystal.class)));
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        ArrayList result = new ArrayList<Object>();
        result.add("plant");
        result.add("ore");
        result.add("loot");
        result.add("mob");
        result.add("angel");
        return result;
    }

    public String name;

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
        if(name != null) {
            if (name.equals("plant")) {
                return PlanterTile.class;
            }
            if (name.equals("ore")){
                return OreTile.class;
            }
            if(name.equals("loot")){
                return LootTile.class;
            }

            if(name.equals("mob")){
                return SpawnTile.class;
            }
            if(name.equals("angel")){
                return AngelSteelTile.class;
            }
        }
        return FurnaceTile.class;
    }

    public IIcon front;
    public IIcon side;
    public IIcon top;



    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World world, int x, int y, int z, int meta) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if(tileEntity instanceof ConsumerTile) {
            return (int) (15D * (((double)((ConsumerTile)tileEntity).progress) / ((double)((ConsumerTile)tileEntity).getMaxProgress())));
        }else{
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
        if(world.getTileEntity(x, y, z) instanceof ConsumerTile){
            ConsumerTile consumerTile = (ConsumerTile) world.getTileEntity(x, y, z);
            result.add(""+consumerTile.progress + " / " + consumerTile.getMaxProgress());
            result.add("Power per progress: "+consumerTile.getPowerPerProgress());
        }
        return result;
    }
}
