package pixlepix.auracascade.data.recipe;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import pixlepix.auracascade.data.AuraQuantity;
import pixlepix.auracascade.data.EnumAura;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pixlepix on 12/7/14.
 */
public class PylonRecipeRegistry {

    public static List<PylonRecipe> recipes = new ArrayList<PylonRecipe>();

    public static void init(){
    }

    public static void registerRecipe(PylonRecipe recipe){
        recipes.add(recipe);
    }


}
