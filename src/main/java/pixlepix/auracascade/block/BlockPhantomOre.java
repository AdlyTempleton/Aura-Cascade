package pixlepix.auracascade.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import pixlepix.auracascade.registry.ITTinkererBlock;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by localmacaccount on 6/6/15.
 */
public class BlockPhantomOre extends Block implements ITTinkererBlock {
    public BlockPhantomOre() {
        super(Material.rock);
    }

    public static boolean isPhantomOreBlock(int x, int y, int z) {
        Random random = new Random(x + 3452 * y + 234598 * z);
        return random.nextInt(2500) == 0 && y < 40;
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        return null;
    }

    @Override
    public String getBlockName() {
        return "phantomOre";
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
    public void registerBlockIcons(IIconRegister icon) {
        blockIcon = icon.registerIcon("aura:phantomOre");
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
        return 27;
    }
}
