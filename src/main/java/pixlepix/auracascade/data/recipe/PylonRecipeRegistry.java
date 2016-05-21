package pixlepix.auracascade.data.recipe;

import net.minecraft.init.Blocks;
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

    //Used in LexiconData
    public static PylonRecipe getRecipe(ItemStack stack) {
        for (PylonRecipe recipe : recipes) {
            if (ItemStack.areItemStacksEqual(recipe.result, stack)) {
                return recipe;
            }
        }
        return null;
    }

    public static void init() {
        recipes.add(new PylonRecipe(new ItemStack(Items.LEATHER),
                new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 10000), new ItemStack(Items.ROTTEN_FLESH))));
        recipes.add(new PylonRecipe(new ItemStack(Items.BLAZE_POWDER, 20),
                new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 10000), new ItemStack(Items.BLAZE_ROD))));
        recipes.add(new PylonRecipe(new ItemStack(Items.SADDLE, 1),
                new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 10000), new ItemStack(Items.LEATHER))));
        recipes.add(new PylonRecipe(new ItemStack(Items.ENDER_EYE, 2),
                new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 10000), new ItemStack(Items.ENDER_PEARL))));
        recipes.add(new PylonRecipe(new ItemStack(Items.ARROW, 8),
                new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 10000), new ItemStack(Items.FEATHER))));
        recipes.add(new PylonRecipe(new ItemStack(Blocks.RAIL, 32),
                new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 10000), new ItemStack(Items.IRON_INGOT))));
        recipes.add(new PylonRecipe(new ItemStack(Blocks.LAPIS_BLOCK, 1),
                new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 10000), new ItemStack(Blocks.REDSTONE_BLOCK))));
        recipes.add(new PylonRecipe(new ItemStack(Items.REPEATER, 1),
                new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 10000), new ItemStack(Items.REDSTONE))));
        recipes.add(new PylonRecipe(new ItemStack(Items.COMPARATOR, 1),
                new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 10000), new ItemStack(Items.REPEATER))));
        recipes.add(new PylonRecipe(new ItemStack(Blocks.SOUL_SAND, 1),
                new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 10000), new ItemStack(Blocks.SAND))));
        recipes.add(new PylonRecipe(new ItemStack(Blocks.DIAMOND_BLOCK, 1),
                new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 10000), new ItemStack(Blocks.GOLD_BLOCK))));
        recipes.add(new PylonRecipe(new ItemStack(Blocks.GOLD_BLOCK, 1),
                new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 10000), new ItemStack(Blocks.IRON_BLOCK))));


    }

    public static void registerRecipe(PylonRecipe recipe) {
        recipes.add(recipe);
    }


}
