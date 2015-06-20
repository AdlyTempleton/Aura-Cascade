package pixlepix.auracascade.data.recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by localmacaccount on 6/20/15.
 */
public class ProcessorRecipeRegistry {

    public static List<ProcessorRecipe> recipes = new ArrayList<ProcessorRecipe>();


    public static void init() {

    }

    public static void registerRecipe(ProcessorRecipe recipe) {
        recipes.add(recipe);
    }

}
