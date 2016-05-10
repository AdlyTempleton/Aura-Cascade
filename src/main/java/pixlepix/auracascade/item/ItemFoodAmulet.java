package pixlepix.auracascade.item;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.registry.CraftingBenchRecipe;
import pixlepix.auracascade.registry.ITTinkererItem;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

import java.util.ArrayList;

/**
 * Created by localmacaccount on 2/6/15.
 */
public class ItemFoodAmulet extends Item implements IBauble, ITTinkererItem {

    //Logic implemented in EventHandler

    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return BaubleType.AMULET;
    }

    @Override
    public void onWornTick(ItemStack itemStack, EntityLivingBase entityLivingBase) {

    }

    @Override
    public void onEquipped(ItemStack itemStack, EntityLivingBase entityLivingBase) {

    }

    @Override
    public void onUnequipped(ItemStack itemStack, EntityLivingBase entityLivingBase) {

    }

    @Override
    public boolean canEquip(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        return true;
    }

    @Override
    public boolean canUnequip(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        return true;
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        return null;
    }

    @Override
    public String getItemName() {
        return "amuletFood";
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
    public int getItemStackLimit(ItemStack stack) {
        return 1;
    }


    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return new CraftingBenchRecipe(new ItemStack(this), " R ", "GAB", " Y "
                , 'R', ItemMaterial.getGem(EnumAura.RED_AURA), 'G', ItemMaterial.getGem(EnumAura.GREEN_AURA), 'Y', ItemMaterial.getGem(EnumAura.YELLOW_AURA), 'B', ItemMaterial.getGem(EnumAura.BLUE_AURA), 'A', new ItemStack(Items.apple));
    }

    @Override
    public int getCreativeTabPriority() {
        return -50;
    }
}
