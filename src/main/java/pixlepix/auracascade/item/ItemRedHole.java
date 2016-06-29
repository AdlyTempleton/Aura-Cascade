package pixlepix.auracascade.item;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import pixlepix.auracascade.data.EnumRainbowColor;
import pixlepix.auracascade.registry.CraftingBenchRecipe;
import pixlepix.auracascade.registry.ITTinkererItem;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

import java.util.ArrayList;

/**
 * Created by localmacaccount on 2/3/15.
 */
public class ItemRedHole extends Item implements ITTinkererItem {
    @Override
    public ArrayList<Object> getSpecialParameters() {
        return null;
    }

    @Override
    public String getItemName() {
        return "redHole";
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
    public boolean onEntityItemUpdate(EntityItem entityItem) {
        World world = entityItem.worldObj;
        if (!world.isRemote && world.getTotalWorldTime() % 100 == 0) {
            world.createExplosion(entityItem, entityItem.posX, entityItem.posY, entityItem.posZ, 12F, true);
            return true;
        }
        return false;
    }

    @Override
    public int getEntityLifespan(ItemStack itemStack, World world) {
        return 30000;
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return new CraftingBenchRecipe(new ItemStack(this), "IGI", " I ", "IGI", 'G', ItemMaterial.getGem(EnumRainbowColor.RED), 'I', ItemMaterial.getIngot(EnumRainbowColor.RED));
    }

    @Override
    public int getCreativeTabPriority() {
        return -50;
    }
}
