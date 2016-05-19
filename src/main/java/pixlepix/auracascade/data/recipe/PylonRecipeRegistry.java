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
                new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 10000), new ItemStack(Items.ender_pearl))));
        recipes.add(new PylonRecipe(new ItemStack(Items.ARROW, 8),
                new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 10000), new ItemStack(Items.feather))));
        recipes.add(new PylonRecipe(new ItemStack(Blocks.rail, 32),
                new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 10000), new ItemStack(Items.IRON_INGOT))));
        recipes.add(new PylonRecipe(new ItemStack(Blocks.lapis_block, 1),
                new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 10000), new ItemStack(Blocks.REDSTONE_BLOCK))));
        recipes.add(new PylonRecipe(new ItemStack(Items.repeater, 1),
                new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 10000), new ItemStack(Items.REDSTONE))));
        recipes.add(new PylonRecipe(new ItemStack(Items.comparator, 1),
                new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 10000), new ItemStack(Items.repeater))));
        recipes.add(new PylonRecipe(new ItemStack(Blocks.soul_sand, 1),
                new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 10000), new ItemStack(Blocks.sand))));
        recipes.add(new PylonRecipe(new ItemStack(Blocks.DIAMOND_block, 1),
                new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 10000), new ItemStack(Blocks.GOLD_BLOCK))));
        recipes.add(new PylonRecipe(new ItemStack(Blocks.GOLD_BLOCK, 1),
                new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 10000), new ItemStack(Blocks.IRON_BLOCK))));


    }

    public static void registerRecipe(PylonRecipe recipe) {
        recipes.add(recipe);
    }


}
