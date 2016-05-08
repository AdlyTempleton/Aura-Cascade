package pixlepix.auracascade.item;

import java.util.ArrayList;
import java.util.List;

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
import pixlepix.auracascade.data.AuraQuantity;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.registry.BlockRegistry;
import pixlepix.auracascade.registry.ITTinkererItem;
import pixlepix.auracascade.registry.OreCraftingBenchRecipe;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;
import pixlepix.auracascade.registry.ThaumicTinkererRecipeMulti;

/**
 * Created by pixlepix on 11/29/14.
 */
public class ItemAuraCrystal extends Item implements ITTinkererItem {

    public static final String name = "AuraCrystal";

    public static ItemStack getCrystalFromAura(EnumAura aura) {
        return new ItemStack(BlockRegistry.getFirstItemFromClass(ItemAuraCrystal.class), 1, aura.ordinal());
    }

    public static ItemStack getCrystalFromAuraMax(EnumAura aura) {
        return new ItemStack(BlockRegistry.getFirstItemFromClass(ItemAuraCrystal.class), 64, aura.ordinal());
    }

    @Override
    public EnumActionResult onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand){
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof AuraTile) {
            stack.stackSize--;
            ((AuraTile) te).storage.add(new AuraQuantity(EnumAura.values()[stack.getItemDamage()], 1000));
        }
        //Changing anything to fail literally breaks all of it.
        return EnumActionResult.PASS;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return super.getItemStackDisplayName(stack) + " " + EnumAura.values()[stack.getItemDamage()].name;
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
    public void getSubItems(Item item, CreativeTabs p_150895_2_, List<ItemStack> list) {
        for (int i = 0; i < EnumAura.values().length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public boolean getHasSubtypes() {
        return true;
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {

        return new ThaumicTinkererRecipeMulti(
                new OreCraftingBenchRecipe(new ItemStack(this, 2, 0), "GGG", "GDG", "GGG", 'D', new ItemStack(Items.iron_ingot), 'G', new ItemStack(Items.gold_nugget)),
                //Green
                new OreCraftingBenchRecipe(new ItemStack(this, 8, 1), "CCC", "CXC", "CCC", 'X', "dyeGreen", 'C', new ItemStack(this, 1, 0)),
                //Black
                new OreCraftingBenchRecipe(new ItemStack(this, 8, 2), "CCC", "CXC", "CCC", 'X', "dyeBlack", 'C', new ItemStack(this, 1, 0)),
                //Red
                new OreCraftingBenchRecipe(new ItemStack(this, 8, 3), "CCC", "CXC", "CCC", 'X', "dyeRed", 'C', new ItemStack(this, 1, 0)),
                //Orange
                new OreCraftingBenchRecipe(new ItemStack(this, 8, 4), "CCC", "CXC", "CCC", 'X', "dyeOrange", 'C', new ItemStack(this, 1, 0)),
                //Yellow
                new OreCraftingBenchRecipe(new ItemStack(this, 8, 5), "CCC", "CXC", "CCC", 'X', "dyeYellow", 'C', new ItemStack(this, 1, 0)),
                //Blue
                new OreCraftingBenchRecipe(new ItemStack(this, 8, 6), "CCC", "CXC", "CCC", 'X', "dyeBlue", 'C', new ItemStack(this, 1, 0)),
                //Violet
                new OreCraftingBenchRecipe(new ItemStack(this, 8, 7), "CCC", "CXC", "CCC", 'X', "dyePurple", 'C', new ItemStack(this, 1, 0)),
                //Green
                new OreCraftingBenchRecipe(new ItemStack(this, 8, 1), "CCC", "CXC", "CCC", 'X', new ItemStack(Blocks.wool, 1, 13), 'C', new ItemStack(this, 1, 0)),
                //Black
                new OreCraftingBenchRecipe(new ItemStack(this, 8, 2), "CCC", "CXC", "CCC", 'X', new ItemStack(Blocks.wool, 1, 15), 'C', new ItemStack(this, 1, 0)),
                //Red
                new OreCraftingBenchRecipe(new ItemStack(this, 8, 3), "CCC", "CXC", "CCC", 'X', new ItemStack(Blocks.wool, 1, 14), 'C', new ItemStack(this, 1, 0)),
                //Orange
                new OreCraftingBenchRecipe(new ItemStack(this, 8, 4), "CCC", "CXC", "CCC", 'X', new ItemStack(Blocks.wool, 1, 1), 'C', new ItemStack(this, 1, 0)),
                //Yellow
                new OreCraftingBenchRecipe(new ItemStack(this, 8, 5), "CCC", "CXC", "CCC", 'X', new ItemStack(Blocks.wool, 1, 4), 'C', new ItemStack(this, 1, 0)),
                //Blue
                new OreCraftingBenchRecipe(new ItemStack(this, 8, 6), "CCC", "CXC", "CCC", 'X', new ItemStack(Blocks.wool, 1, 11), 'C', new ItemStack(this, 1, 0)),
                //Violet
                new OreCraftingBenchRecipe(new ItemStack(this, 8, 7), "CCC", "CXC", "CCC", 'X', new ItemStack(Blocks.wool, 1, 10), 'C', new ItemStack(this, 1, 0))

        );
    }

    @Override
    public int getCreativeTabPriority() {
        return 74;
    }
}
