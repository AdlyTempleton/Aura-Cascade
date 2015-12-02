package pixlepix.auracascade.enchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.main.ConstantMod;

/**
 * Created by localmacaccount on 2/14/15.
 */
public class KaleidoscopeEnchantment extends Enchantment {
    public KaleidoscopeEnchantment(int id, EnumAura aura) {
        super(id, new ResourceLocation(ConstantMod.prefixMod, "kaleidoscope"), 0, EnumEnchantmentType.DIGGER);
        setName(aura.name);
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return EnumEnchantmentType.DIGGER.canEnchantItem(stack.getItem()) || EnumEnchantmentType.WEAPON.canEnchantItem(stack.getItem());
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return false;
    }
}
