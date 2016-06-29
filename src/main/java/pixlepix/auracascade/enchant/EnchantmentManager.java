package pixlepix.auracascade.enchant;

import net.minecraft.enchantment.Enchantment;
import pixlepix.auracascade.data.EnumRainbowColor;
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
        red = new KaleidoscopeEnchantment(EnumRainbowColor.RED);
        orange = new KaleidoscopeEnchantment(EnumRainbowColor.ORANGE);
        yellow = new KaleidoscopeEnchantment(EnumRainbowColor.YELLOW);
        green = new KaleidoscopeEnchantment(EnumRainbowColor.GREEN);
        blue = new KaleidoscopeEnchantment(EnumRainbowColor.BLUE);
        purple = new KaleidoscopeEnchantment(EnumRainbowColor.VIOLET);
    }
}
