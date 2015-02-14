package pixlepix.auracascade.enchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;
import pixlepix.auracascade.data.EnumAura;

/**
 * Created by localmacaccount on 2/14/15.
 */
public class KaleidoscopeEnchantment extends Enchantment {
    public KaleidoscopeEnchantment(int id, EnumAura aura) {
        super(id, 0, EnumEnchantmentType.digger);
        setName(aura.name);
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return EnumEnchantmentType.digger.canEnchantItem(stack.getItem()) || EnumEnchantmentType.weapon.canEnchantItem(stack.getItem());
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return false;
    }
}
