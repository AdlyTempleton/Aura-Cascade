package pixlepix.auracascade.item;

import com.google.common.collect.Range;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import pixlepix.auracascade.registry.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by pixlepix on 12/21/14.
 */
public class ItemAngelsteelPickaxe extends ItemPickaxe implements ITTinkererItem {
    public ItemAngelsteelPickaxe(Integer i) {
        super(AngelsteelToolHelper.materials[i.intValue()]);
        this.degree = i.intValue();
    }

    public ItemAngelsteelPickaxe() {
        this(0);
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        return AngelsteelToolHelper.getDegreeList(true);
    }

    public int degree = 0;

    public static final String name = "angelsteelPickaxe";

    @Override
    public String getItemName() {
        return name + degree;
    }

    @Override
    public boolean shouldRegister() {
        return true;
    }

    @Override
    public boolean shouldDisplayInTab() {
        return true;
        //return degree == 0 || degree == AngelsteelToolHelper.MAX_DEGREE;
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {

        return new CraftingBenchRecipe(new ItemStack(this, 1, degree), "AAA", " S ", " S ", 'A', new ItemStack(BlockRegistry.getFirstItemFromClass(ItemAngelsteelIngot.class), 1, degree), 'S', new ItemStack(Items.stick));
    }
}
