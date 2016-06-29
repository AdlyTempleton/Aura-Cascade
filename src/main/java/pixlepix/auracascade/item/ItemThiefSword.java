package pixlepix.auracascade.item;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import pixlepix.auracascade.data.EnumRainbowColor;
import pixlepix.auracascade.registry.CraftingBenchRecipe;
import pixlepix.auracascade.registry.ITTinkererItem;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

import java.util.ArrayList;

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
        return new CraftingBenchRecipe(new ItemStack(this), " A ", " A ", " E ", 'A', ItemMaterial.getIngot(EnumRainbowColor.BLUE), 'E', ItemMaterial.getGem(EnumRainbowColor.BLACK));
    }

    @Override
    public int getCreativeTabPriority() {
        return -50;
    }
}
