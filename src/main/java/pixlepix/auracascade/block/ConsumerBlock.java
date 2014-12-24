package pixlepix.auracascade.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import pixlepix.auracascade.block.tile.*;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.registry.ITTinkererBlock;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

import java.util.ArrayList;

/**
 * Created by pixlepix on 11/29/14.
 */
public class ConsumerBlock extends Block implements ITTinkererBlock, ITileEntityProvider {

    public ConsumerBlock() {
        super(Material.iron);
        this.name = "furnace";
    }


    public ConsumerBlock(String name) {
        super(Material.iron);
        this.name = name;
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_) {
        super.registerBlockIcons(p_149651_1_);
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        // TODO Auto-generated method stub
        return null;
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
        }
        return FurnaceTile.class;
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





}
