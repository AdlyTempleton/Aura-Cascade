package pixlepix.auracascade.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import pixlepix.auracascade.registry.CraftingBenchRecipe;
import pixlepix.auracascade.registry.ITTinkererItem;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by localmacaccount on 5/15/15.
 */
public class ItemComboSword extends ItemSword implements ITTinkererItem {
    public static final String NBT_TAG_LAST_TIME = "lastComboTime";
    public static final String NBT_TAG_COMBO_COUNT = "comboCount";

    public ItemComboSword() {
        super(ToolMaterial.EMERALD);
    }

    public static double getComboMultiplier(int i) {
        return Math.pow(1.05, i);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        super.addInformation(stack, player, list, bool);
        if (stack.stackTagCompound != null) {
            double timeLeft = (double) (stack.stackTagCompound.getLong(NBT_TAG_LAST_TIME) + 200 - player.worldObj.getTotalWorldTime());
            timeLeft /= 20;
            if (timeLeft > 0) {
                list.add(String.format("Combo: %.3f", getComboMultiplier(stack.stackTagCompound.getInteger(NBT_TAG_COMBO_COUNT))));

                list.add("Time left in combo: " + timeLeft);
            }
        }
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        return null;
    }

    @Override
    public String getItemName() {
        return "comboSword";
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon("aura:comboSword");
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
    public ThaumicTinkererRecipe getRecipeItem() {
        return new CraftingBenchRecipe(new ItemStack(this), " d ", " d ", " p ", 'd', new ItemStack(Items.diamond), 'p', ItemMaterial.getPrism());
    }

    @Override
    public int getCreativeTabPriority() {
        return -50;
    }
}
