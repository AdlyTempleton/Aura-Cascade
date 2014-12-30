package pixlepix.auracascade.main;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import pixlepix.auracascade.item.AngelsteelToolHelper;
import pixlepix.auracascade.item.ItemAngelsteelIngot;

/**
 * Created by pixlepix on 12/21/14.
 */
public class AngelsteelRecipe implements IRecipe {
    @Override
    public boolean matches(InventoryCrafting inv, World world) {
        int degree = -1;
        int count = 0;
        for(int i=0; i < inv.getSizeInventory(); i++){
            ItemStack stack = inv.getStackInSlot(i);

            if(stack != null) {
                if (!(stack.getItem() instanceof ItemAngelsteelIngot)) {
                    return false;
                }
                if (degree != -1 && degree != stack.getItemDamage()) {
                    return false;
                }
                degree = stack.getItemDamage();
                count++;
            }
        }
        return count==3 && degree < AngelsteelToolHelper.MAX_DEGREE;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        for(int i=0; i < inv.getSizeInventory(); i++){
            ItemStack stack = inv.getStackInSlot(i);
            if(stack != null){
                int degree = stack.getItemDamage();
                return new ItemStack(stack.getItem(), 1, degree + 1);
            }
        }
        return null;
    }

    @Override
    public int getRecipeSize() {
        return 3;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }
}
