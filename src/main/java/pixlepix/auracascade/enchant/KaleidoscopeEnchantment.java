package pixlepix.auracascade.enchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import pixlepix.auracascade.data.EnumAura;

/**
 * Created by localmacaccount on 2/14/15.
 */
public class KaleidoscopeEnchantment extends Enchantment {
	private static EntityEquipmentSlot[] slots = new EntityEquipmentSlot[]{
			EntityEquipmentSlot.CHEST
	};
    public KaleidoscopeEnchantment(int id, EnumAura aura) {
        //super(id, new ResourceLocation(ConstantMod.prefixMod, "kaleidoscope"), 0, EnumEnchantmentType.ALL);
    	
    	super(Enchantment.Rarity.RARE, EnumEnchantmentType.ALL, slots);
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
