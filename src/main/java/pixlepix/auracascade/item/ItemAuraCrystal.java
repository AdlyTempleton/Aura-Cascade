package pixlepix.auracascade.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import pixlepix.auracascade.block.tile.AuraTile;
import pixlepix.auracascade.data.AuraQuantity;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.registry.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pixlepix on 11/29/14.
 */
public class ItemAuraCrystal extends Item implements ITTinkererItem {

    public static final String name = "AuraCrystal";
    private IIcon[] icons;

    public static ItemStack getCrystalFromAura(EnumAura aura) {
        return new ItemStack(BlockRegistry.getFirstItemFromClass(ItemAuraCrystal.class), 1, aura.ordinal());
    }

    public static ItemStack getCrystalFromAuraMax(EnumAura aura) {
        return new ItemStack(BlockRegistry.getFirstItemFromClass(ItemAuraCrystal.class), 64, aura.ordinal());
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int meta, float hitX, float hitY, float hitZ) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof AuraTile) {
            stack.stackSize--;
            ((AuraTile) te).storage.add(new AuraQuantity(EnumAura.values()[stack.getItemDamage()], 1000));
        }
        return false;
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        icons = new IIcon[EnumAura.values().length];
        for (int i = 0; i < EnumAura.values().length; i++) {
            icons[i] = iconRegister.registerIcon("aura:" + EnumAura.values()[i].name.toLowerCase() + "Crystal");
        }
    }

    @Override
    public IIcon getIconFromDamage(int i) {
        return icons[i];
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
    public void getSubItems(Item item, CreativeTabs p_150895_2_, List list) {
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
                new OreCraftingBenchRecipe(new ItemStack(this, 16, 0), "GGG", "GDG", "GGG", 'D', new ItemStack(Items.iron_ingot), 'G', new ItemStack(Items.gold_nugget)),
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
