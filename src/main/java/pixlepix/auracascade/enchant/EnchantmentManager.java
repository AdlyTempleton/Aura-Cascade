package pixlepix.auracascade.enchant;

import net.minecraft.enchantment.Enchantment;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.main.Config;

/**
 * Created by localmacaccount on 2/14/15.
 */
public class EnchantmentManager {

    public static Enchantment red;
    public static Enchantment orange;
    public static Enchantment yellow;
    public static Enchantment green;
    public static Enchantment blue;
    public static Enchantment purple;

    public static void init() {
        int id = Config.enchantStartId;
        red = new KaleidoscopeEnchantment(id++, EnumAura.RED_AURA);
        orange = new KaleidoscopeEnchantment(id++, EnumAura.ORANGE_AURA);
        yellow = new KaleidoscopeEnchantment(id++, EnumAura.YELLOW_AURA);
        green = new KaleidoscopeEnchantment(id++, EnumAura.GREEN_AURA);
        blue = new KaleidoscopeEnchantment(id++, EnumAura.BLUE_AURA);
        purple = new KaleidoscopeEnchantment(id++, EnumAura.VIOLET_AURA);
    }
}
