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
        registerRecipe(new PylonRecipe(new ItemStack(Items.diamond),
                new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 100), new ItemStack(Items.coal))));
    }

    public static void registerRecipe(PylonRecipe recipe){
        recipes.add(recipe);
    }

}
