/* todo 1.8.8 await NEI update
package pixlepix.auracascade.main;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import pixlepix.auracascade.block.tile.CraftingCenterTile;
import pixlepix.auracascade.data.AuraQuantity;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.data.recipe.PylonRecipe;
import pixlepix.auracascade.data.recipe.PylonRecipeComponent;
import pixlepix.auracascade.data.recipe.PylonRecipeRegistry;

import java.util.ArrayList;
import java.util.List;

*/
/**
 * Created by pixlepix on 12/26/14.
 *//*

public class PylonRecipeHandler extends TemplateRecipeHandler {
    @Override
    public void drawExtras(int recipe) {
        super.drawExtras(recipe);
        for (int i = 0; i < 4; i++) {
            EnumFacing d = CraftingCenterTile.pedestalRelativeLocations.get(i);
            PylonRecipe recipeObj = ((NEIPylonRecipe) arecipes.get(recipe)).recipe;
            AuraQuantity quantity = recipeObj.componentList.get(i).auraQuantity;
            Minecraft.getMinecraft().fontRendererObj.drawString("" + quantity.getNum() + "(" + (quantity.getType() == EnumAura.WHITE_AURA ? "Any" : quantity.getType().name) + ")", 50 + d.getFrontOffsetX() * 50, 85 + d.getFrontOffsetZ() * 35, quantity.getType().color.getHex());
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        for (PylonRecipe recipe : PylonRecipeRegistry.recipes) {
            if (recipe.result.isItemEqual(result)) {
                arecipes.add(new NEIPylonRecipe(recipe));
            }
        }
    }

    @Override
    public void loadUsageRecipes(String inputId, Object... ingredients) {
        if (ingredients.length == 0) {
            return;
        }
        if ("item".equals(inputId)) {
            for (PylonRecipe r : PylonRecipeRegistry.recipes) {
                for (PylonRecipeComponent component : r.componentList) {
                    for (Object obj : ingredients) {
                        if (obj instanceof ItemStack && ((ItemStack) obj).isItemEqual(component.itemStack)) {
                            arecipes.add(new NEIPylonRecipe(r));
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public String getOverlayIdentifier() {
        return "aurapylonrecipies";
    }

    @Override
    public String getGuiTexture() {
        return "aura:textures/blankgui.png";
    }

    @Override
    public int recipiesPerPage() {
        return 1;
    }

    @Override
    public String getRecipeName() {
        return StatCollector.translateToLocal("vortex.nei.name");
    }

    public class NEIPylonRecipe extends CachedRecipe {


        public PylonRecipe recipe;

        public NEIPylonRecipe(PylonRecipe recipe) {
            this.recipe = recipe;
        }

        @Override
        public PositionedStack getResult() {
            return new PositionedStack(recipe.result, 80, 70);
        }

        @Override
        public List<PositionedStack> getIngredients() {
            ArrayList<PositionedStack> stacks = new ArrayList<PositionedStack>();
            for (int i = 0; i < 4; i++) {
                EnumFacing d = CraftingCenterTile.pedestalRelativeLocations.get(i);
                stacks.add(new PositionedStack(recipe.componentList.get(i).itemStack, 80 + d.getFrontOffsetX() * 35, 70 + d.getFrontOffsetZ() * 35));
            }
            return stacks;
        }
    }


}*/
