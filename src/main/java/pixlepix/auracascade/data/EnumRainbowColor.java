package pixlepix.auracascade.data;

import net.minecraft.world.World;
import pixlepix.auracascade.main.EnumColor;

/**
 * Created by ATempleton on 6/29/2016.
 */
public enum EnumRainbowColor {
    WHITE("White", 1, 1, 1, EnumColor.BLACK, new int[]{0}),
    GREEN("Green", .1, 1, .1, EnumColor.DARK_GREEN, new int[]{5, 13}),
    BLACK("Black", .1, .1, .1, EnumColor.BLACK, new int[]{12, 15, 7, 8}),
    RED("Red", 1, .1, .1, EnumColor.RED, new int[]{14}),
    ORANGE("Orange", 1, .5, 0, EnumColor.ORANGE, new int[]{1}),
    YELLOW("Yellow", 1, 1, .1, EnumColor.YELLOW, new int[]{4}),
    BLUE("Blue", .1, .1, 1, EnumColor.DARK_BLUE, new int[]{3, 9, 11}),
    VIOLET("Violet", 1, .1, 1, EnumColor.PURPLE, new int[]{2, 6, 10}), ;

    public String name;
    public double r;
    public double g;
    public double b;
    public EnumColor color;

    public int[] dyes;

    EnumRainbowColor(String name, double r, double g, double b, EnumColor color, int[] dyes) {
        this.name = name;
        this.r = r;
        this.g = g;
        this.b = b;
        this.color = color;
        this.dyes = dyes;
    }

    public static EnumRainbowColor getColorFromDyeMeta(int i) {
        for (EnumRainbowColor aura : EnumRainbowColor.values()) {
            for (int dyeMeta : aura.dyes) {
                if (dyeMeta == i) {
                    return aura;
                }
            }
        }
        return null;
    }
}