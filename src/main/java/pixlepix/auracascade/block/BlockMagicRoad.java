package pixlepix.auracascade.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import pixlepix.auracascade.data.AuraQuantity;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.data.recipe.PylonRecipe;
import pixlepix.auracascade.data.recipe.PylonRecipeComponent;
import pixlepix.auracascade.registry.ITTinkererBlock;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

import java.util.ArrayList;

/**
 * Created by localmacaccount on 2/4/15.
 */
public class BlockMagicRoad extends Block implements ITTinkererBlock {
    public BlockMagicRoad() {
        super(Material.rock);
        setBlockBounds(0F, 0F, 0F, 1F, .8F, 1F);
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        return null;
    }

    @Override
    public String getBlockName() {
        return "magicRoad";
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        Vec3 dir = Vec3.createVectorHelper(entity.motionX, entity.motionY, entity.motionZ);
        if (dir.lengthVector() > 0.25) {
            dir = dir.normalize();
            entity.addVelocity(dir.xCoord * 5, dir.yCoord * 5, dir.zCoord * 5);
        }
    }

    @Override
    public boolean shouldRegister() {
        return true;
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        blockIcon = register.registerIcon("aura:bricks");
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
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
        return null;
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return new PylonRecipe(new ItemStack(this), new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 100000),
                new ItemStack(Blocks.stonebrick)));
    }
}
