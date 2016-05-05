package pixlepix.auracascade.item;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.registry.CraftingBenchRecipe;
import pixlepix.auracascade.registry.ITTinkererItem;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

/**
 * Created by localmacaccount on 2/5/15.
 */
public class ItemThiefSword extends ItemSword implements ITTinkererItem {

    //Logic done in event handler

    public ItemThiefSword() {
        super(AngelsteelToolHelper.materials[2]);
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        return null;
    }

    @Override
    public String getItemName() {
        return "swordThief";
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
        return new CraftingBenchRecipe(new ItemStack(this), " A ", " A ", " E ", 'A', ItemMaterial.getIngot(EnumAura.BLUE_AURA), 'E', ItemMaterial.getGem(EnumAura.BLACK_AURA));
    }

    @Override
    public int getCreativeTabPriority() {
        return -50;
    }
}
