package pixlepix.auracascade.data.recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pixlepix on 12/7/14.
 */
public class PylonRecipeRegistry {

    public static List<PylonRecipe> recipes = new ArrayList<PylonRecipe>();

    public static void init() {
    }

    public static void registerRecipe(PylonRecipe recipe) {
        recipes.add(recipe);
    }


}
