package pixlepix.auracascade.item;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import pixlepix.auracascade.data.EnumRainbowColor;
import pixlepix.auracascade.data.recipe.ProcessorRecipe;
import pixlepix.auracascade.data.recipe.PylonRecipe;
import pixlepix.auracascade.data.recipe.PylonRecipeComponent;
import pixlepix.auracascade.registry.BlockRegistry;
import pixlepix.auracascade.registry.ITTinkererItem;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;
import pixlepix.auracascade.registry.ThaumicTinkererRecipeMulti;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by localmacaccount on 4/6/15.
 */
public class ItemMaterial extends Item implements ITTinkererItem {

    public static String[] names = new String[]{"ingot", "gem", "prism"};
    public EnumRainbowColor color;
    //0 = Ingot
    //1 = Gem
    //2 = Prism (Only 1 color)
    //Note that the prism only comes in "White" color
    //Left like this as multicolored prisms are planned in the future
    public int materialIndex;

    public ItemMaterial(EnumRainbowColor color, int materialIndex) {
        super();

        this.color = color;
        this.materialIndex = materialIndex;
    }

    public ItemMaterial(MaterialPair materialPair) {
        this(materialPair.color, materialPair.materialIndex);
    }

    public ItemMaterial() {
        this(EnumRainbowColor.WHITE, 2);
    }

    public static ItemMaterial getItemFromSpecs(MaterialPair pair) {
        List<Item> blockList = BlockRegistry.getItemFromClass(ItemMaterial.class);
        for (Item b : blockList) {
            ItemMaterial itemMaterial = (ItemMaterial) b;
            if (pair.color == itemMaterial.color && pair.materialIndex == itemMaterial.materialIndex) {
                return itemMaterial;
            }
        }
        return null;
    }


    public static ItemStack getPrism() {
        return getPrism(1);
        
    }

    public static ItemStack getPrism(int size) {
        return new ItemStack(getItemFromSpecs(new MaterialPair(EnumRainbowColor.WHITE, 2)), size);
    }

    public static ItemStack getGem(EnumRainbowColor color, int size) {
        return new ItemStack(getItemFromSpecs(new MaterialPair(color, 1)), 1);
    }

    public static ItemStack getGem(EnumRainbowColor color) {
        return getGem(color, 1);
    }

    public static ItemStack getIngot(EnumRainbowColor color) {
        return getIngot(color, 1);
    }

    public static ItemStack getIngot(EnumRainbowColor color, int size) {
        return new ItemStack(getItemFromSpecs(new MaterialPair(color, 0)), size);
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        ArrayList<Object> result = new ArrayList<Object>();
        for (int i = 0; i < 2; i++) {
            for (EnumRainbowColor auraCons : EnumRainbowColor.values()) {
                result.add(new MaterialPair(auraCons, i));
            }
        }
        return result;
    }

    @Override
    public String getItemName() {
        return names[materialIndex] + color.name;
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
        if (materialIndex == 0) {
            ThaumicTinkererRecipeMulti multi = new ThaumicTinkererRecipeMulti();
            for (int i : color.dyes) {
                multi.addRecipe(new ProcessorRecipe(new ItemStack(this), false, new ItemStack(Items.IRON_INGOT), new ItemStack(Blocks.WOOL, 1, i)));
            }
            return multi;
        }
        if (materialIndex == 1) {
            return new PylonRecipe(
                    new ItemStack(this),
                    new PylonRecipeComponent(60000, new ItemStack(Items.DIAMOND)),
                    new PylonRecipeComponent(20000, new ItemStack(getItemFromSpecs(new MaterialPair(color, 0)))),
                    new PylonRecipeComponent(20000, new ItemStack(getItemFromSpecs(new MaterialPair(color, 0)))),
                    new PylonRecipeComponent(20000, new ItemStack(getItemFromSpecs(new MaterialPair(color, 0)))));
        }
        if (materialIndex == 2) {
            return new ProcessorRecipe(new ItemStack(this), false,
                    new ItemStack(getItemFromSpecs(new MaterialPair(EnumRainbowColor.RED, 1))),
                    new ItemStack(getItemFromSpecs(new MaterialPair(EnumRainbowColor.ORANGE, 1))),
                    new ItemStack(getItemFromSpecs(new MaterialPair(EnumRainbowColor.YELLOW, 1))),
                    new ItemStack(getItemFromSpecs(new MaterialPair(EnumRainbowColor.BLUE, 1))),
                    new ItemStack(getItemFromSpecs(new MaterialPair(EnumRainbowColor.GREEN, 1))),
                    new ItemStack(getItemFromSpecs(new MaterialPair(EnumRainbowColor.VIOLET, 1))),
                    new ItemStack(getItemFromSpecs(new MaterialPair(EnumRainbowColor.BLACK, 1))),
                    new ItemStack(getItemFromSpecs(new MaterialPair(EnumRainbowColor.WHITE, 1))));
        }
        return null;
    }

    @Override
    public int getCreativeTabPriority() {
        return 25;
    }

    //Private class for constructor
    public static class MaterialPair {
        private final EnumRainbowColor color;
        private final int materialIndex;

        public MaterialPair(EnumRainbowColor color, int materialIndex) {
            this.color = color;
            this.materialIndex = materialIndex;
        }
    }
}
