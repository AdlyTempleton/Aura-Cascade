package pixlepix.auracascade.item;

import net.minecraft.item.Item;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.registry.BlockRegistry;
import pixlepix.auracascade.registry.ITTinkererItem;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by localmacaccount on 4/6/15.
 */
public class ItemMaterial extends Item implements ITTinkererItem {

    public static String[] names = new String[]{"ingot", "gem", "prism"};
    EnumAura aura;
    //0 = Ingot
    //1 = Gem
    //2 = Prism (Only 1 color)
    //Note that the prism only comes in "White" color
    int materialIndex;

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

    public static ItemMaterial getItemFromName(MaterialPair pair) {
        List<Item> blockList = BlockRegistry.getItemFromClass(ItemMaterial.class);
        for (Item b : blockList) {
            ItemMaterial itemMaterial = (ItemMaterial) b;
            if (pair.aura == itemMaterial.aura && pair.materialIndex == itemMaterial.materialIndex) {
                return itemMaterial;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        ArrayList<Object> result = new ArrayList<Object>();
        for (int i = 0; i < 2; i++) {
            for (EnumAura auraCons : EnumAura.values()) {
                if (!(i == 0 && auraCons == EnumAura.WHITE_AURA)) {
                    result.add(new MaterialPair(auraCons, i));
                }
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
        return null;
    }

    //Private class for constructor
    private class MaterialPair {
        private final EnumAura aura;
        private final int materialIndex;

        public MaterialPair(EnumAura aura, int materialIndex) {
            this.aura = aura;
            this.materialIndex = materialIndex;
        }
    }
}
