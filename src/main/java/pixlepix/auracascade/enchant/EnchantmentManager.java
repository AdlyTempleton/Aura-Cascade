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
        red = new KaleidoscopeEnchantment(Config.enchantRed, EnumAura.RED_AURA);
        orange = new KaleidoscopeEnchantment(Config.enchantOrange, EnumAura.ORANGE_AURA);
        yellow = new KaleidoscopeEnchantment(Config.enchantYellow, EnumAura.YELLOW_AURA);
        green = new KaleidoscopeEnchantment(Config.enchantGreen, EnumAura.GREEN_AURA);
        blue = new KaleidoscopeEnchantment(Config.enchantBlue, EnumAura.BLUE_AURA);
        purple = new KaleidoscopeEnchantment(Config.enchantViolet, EnumAura.VIOLET_AURA);
    }
}
