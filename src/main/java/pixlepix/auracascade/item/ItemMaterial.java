package pixlepix.auracascade.item;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import pixlepix.auracascade.data.AuraQuantity;
import pixlepix.auracascade.data.EnumAura;
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
    public EnumAura aura;
    //0 = Ingot
    //1 = Gem
    //2 = Prism (Only 1 color)
    //Note that the prism only comes in "White" color
    //Left like this as multicolored prisms are planned in the future
    public int materialIndex;

    public ItemMaterial(EnumAura aura, int materialIndex) {
        super();

        this.aura = aura;
        this.materialIndex = materialIndex;
    }

    public ItemMaterial(MaterialPair materialPair) {
        this(materialPair.aura, materialPair.materialIndex);
    }

    public ItemMaterial() {
        this(EnumAura.WHITE_AURA, 2);
    }

    public static ItemMaterial getItemFromSpecs(MaterialPair pair) {
        List<Item> blockList = BlockRegistry.getItemFromClass(ItemMaterial.class);
        for (Item b : blockList) {
            ItemMaterial itemMaterial = (ItemMaterial) b;
            if (pair.aura == itemMaterial.aura && pair.materialIndex == itemMaterial.materialIndex) {
                return itemMaterial;
            }
        }
        return null;
    }


    public static ItemStack getPrism() {
        return getPrism(1);
        
    }

    public static ItemStack getPrism(int size) {
        return new ItemStack(getItemFromSpecs(new MaterialPair(EnumAura.WHITE_AURA, 2)), size);
    }

    public static ItemStack getGem(EnumAura color, int size) {
        return new ItemStack(getItemFromSpecs(new MaterialPair(color, 1)), 1);
    }

    public static ItemStack getGem(EnumAura color) {
        return getGem(color, 1);
    }

    public static ItemStack getIngot(EnumAura color) {
        return getIngot(color, 1);
    }

    public static ItemStack getIngot(EnumAura color, int size) {
        return new ItemStack(getItemFromSpecs(new MaterialPair(color, 0)), size);
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        ArrayList<Object> result = new ArrayList<Object>();
        for (int i = 0; i < 2; i++) {
            for (EnumAura auraCons : EnumAura.values()) {
                result.add(new MaterialPair(auraCons, i));
            }
        }
        return result;
    }

    @Override
    public String getItemName() {
        return names[materialIndex] + aura.name;
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
            for (int i : aura.dyes) {
                multi.addRecipe(new ProcessorRecipe(new ItemStack(this), false, new ItemStack(Items.IRON_INGOT), new ItemStack(Blocks.WOOL, 1, i)));
            }
            return multi;
        }
        if (materialIndex == 1) {
            return new PylonRecipe(
                    new ItemStack(this),
                    new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 60000), new ItemStack(Items.diamond)),
                    new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 20000), new ItemStack(getItemFromSpecs(new MaterialPair(aura, 0)))),
                    new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 20000), new ItemStack(getItemFromSpecs(new MaterialPair(aura, 0)))),
                    new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 20000), new ItemStack(getItemFromSpecs(new MaterialPair(aura, 0)))));
        }
        if (materialIndex == 2) {
            return new ProcessorRecipe(new ItemStack(this), false,
                    new ItemStack(getItemFromSpecs(new MaterialPair(EnumAura.RED_AURA, 1))),
                    new ItemStack(getItemFromSpecs(new MaterialPair(EnumAura.ORANGE_AURA, 1))),
                    new ItemStack(getItemFromSpecs(new MaterialPair(EnumAura.YELLOW_AURA, 1))),
                    new ItemStack(getItemFromSpecs(new MaterialPair(EnumAura.BLUE_AURA, 1))),
                    new ItemStack(getItemFromSpecs(new MaterialPair(EnumAura.GREEN_AURA, 1))),
                    new ItemStack(getItemFromSpecs(new MaterialPair(EnumAura.VIOLET_AURA, 1))),
                    new ItemStack(getItemFromSpecs(new MaterialPair(EnumAura.BLACK_AURA, 1))),
                    new ItemStack(getItemFromSpecs(new MaterialPair(EnumAura.WHITE_AURA, 1))));
        }
        return null;
    }

    @Override
    public int getCreativeTabPriority() {
        return 25;
    }

    //Private class for constructor
    public static class MaterialPair {
        private final EnumAura aura;
        private final int materialIndex;

        public MaterialPair(EnumAura aura, int materialIndex) {
            this.aura = aura;
            this.materialIndex = materialIndex;
        }
    }
}
