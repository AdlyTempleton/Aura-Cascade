package pixlepix.auracascade.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import pixlepix.auracascade.block.tile.AuraTile;
import pixlepix.auracascade.block.tile.FurnaceTile;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.registry.ITTinkererBlock;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

import java.util.ArrayList;

/**
 * Created by pixlepix on 11/29/14.
 */
public class FurnaceBlock extends Block implements ITTinkererBlock, ITileEntityProvider {

    public FurnaceBlock() {
        super(Material.glass);
    }


    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        // TODO Auto-generated method stub
        return null;
    }

    public static String name = "auraFurnace";

    @Override
    public String getBlockName() {
        // TODO Auto-generated method stub
        return name;
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
        // TODO Auto-generated method stub
        return AuraTile.class;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new FurnaceTile();
    }





}
