package pixlepix.auracascade.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.item.ItemMaterial;
import pixlepix.auracascade.registry.CraftingBenchRecipe;
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
        setHardness(2F);
        setLightLevel(1F);
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
    public void onEntityCollidedWithBlock(World world, BlockPos pos, Entity entity) {
        Vec3 dir = new Vec3(entity.motionX, entity.motionY, entity.motionZ);
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
        return new CraftingBenchRecipe(new ItemStack(this, 32), "BBB", "BIB", "BBB", 'I', ItemMaterial.getIngot(EnumAura.BLACK_AURA), 'B', new ItemStack(Blocks.stonebrick));
    }

    @Override
    public int getCreativeTabPriority() {
        return -50;
    }
}
