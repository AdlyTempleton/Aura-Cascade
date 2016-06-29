package pixlepix.auracascade.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import pixlepix.auracascade.block.tile.AuraTile;
import pixlepix.auracascade.registry.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pixlepix on 11/29/14.
 */
public class ItemAuraCrystal extends Item implements ITTinkererItem {

    public static final String name = "whiteCrystal";

    @Override
    public EnumActionResult onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand){
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof AuraTile) {
            stack.stackSize--;
            ((AuraTile) te).storage += 1000;
        }
        //Changing anything to fail literally breaks all of it.
        return EnumActionResult.PASS;
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        return null;
    }

    @Override
    public String getItemName() {
        return name;
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
    public boolean getHasSubtypes() {
        return true;
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {

        return new OreCraftingBenchRecipe(new ItemStack(this, 2, 0), "GGG", "GDG", "GGG", 'D', new ItemStack(Items.IRON_INGOT), 'G', new ItemStack(Items.GOLD_NUGGET));
    }

    @Override
    public int getCreativeTabPriority() {
        return 74;
    }
}
