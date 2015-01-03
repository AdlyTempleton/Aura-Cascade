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
import pixlepix.auracascade.registry.CraftingBenchRecipe;
import pixlepix.auracascade.registry.ITTinkererItem;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;
import pixlepix.auracascade.registry.ThaumicTinkererRecipeMulti;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pixlepix on 11/29/14.
 */
public class ItemAuraCrystal extends Item implements ITTinkererItem {

    public static final String name = "AuraCrystal";
    private IIcon[] icons;

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int meta, float hitX, float hitY, float hitZ) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof AuraTile) {
            stack.stackSize--;
            ((AuraTile) te).storage.add(new AuraQuantity(EnumAura.values()[stack.getItemDamage()], 100));
            return true;
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
                new CraftingBenchRecipe(new ItemStack(this, 64, 0), "GGG", "GDG", "GGG", 'D', new ItemStack(Items.diamond), 'G', new ItemStack(Blocks.glass)),
                //Green
                new CraftingBenchRecipe(new ItemStack(this, 8, 1), "CCC", "CXC", "CCC", 'X', new ItemStack(Items.dye, 1, 2), 'C', new ItemStack(this, 1, 0)),
                //Black
                new CraftingBenchRecipe(new ItemStack(this, 8, 2), "CCC", "CXC", "CCC", 'X', new ItemStack(Items.dye, 1, 0), 'C', new ItemStack(this, 1, 0)),
                //Red
                new CraftingBenchRecipe(new ItemStack(this, 8, 3), "CCC", "CXC", "CCC", 'X', new ItemStack(Items.dye, 1, 1), 'C', new ItemStack(this, 1, 0)),
                //Orange
                new CraftingBenchRecipe(new ItemStack(this, 8, 4), "CCC", "CXC", "CCC", 'X', new ItemStack(Items.dye, 1, 14), 'C', new ItemStack(this, 1, 0)),
                //Yellow
                new CraftingBenchRecipe(new ItemStack(this, 8, 5), "CCC", "CXC", "CCC", 'X', new ItemStack(Items.dye, 1, 11), 'C', new ItemStack(this, 1, 0)),
                //Blue
                new CraftingBenchRecipe(new ItemStack(this, 8, 6), "CCC", "CXC", "CCC", 'X', new ItemStack(Items.dye, 1, 4), 'C', new ItemStack(this, 1, 0)),
                //Violet
                new CraftingBenchRecipe(new ItemStack(this, 8, 7), "CCC", "CXC", "CCC", 'X', new ItemStack(Items.dye, 1, 5), 'C', new ItemStack(this, 1, 0))

        );
    }
}
