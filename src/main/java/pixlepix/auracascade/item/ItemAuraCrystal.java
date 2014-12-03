package pixlepix.auracascade.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import pixlepix.auracascade.block.tile.AuraTile;
import pixlepix.auracascade.data.AuraQuantity;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.registry.ITTinkererItem;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pixlepix on 11/29/14.
 */
public class ItemAuraCrystal extends Item implements ITTinkererItem {

    public static final String name = "AuraCrystal";

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int meta, float hitX, float hitY, float hitZ) {
        TileEntity te = world.getTileEntity(x, y, z);
        if(te instanceof AuraTile){
            stack.stackSize--;
            ((AuraTile) te).storage.add(new AuraQuantity(EnumAura.values()[stack.getItemDamage()], 100));
            return true;
        }
        return false;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return super.getItemStackDisplayName(stack) + EnumAura.values()[stack.getItemDamage()].name;
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
        for(int i=0; i<EnumAura.values().length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public boolean getHasSubtypes() {
        return true;
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return null;
    }
}
