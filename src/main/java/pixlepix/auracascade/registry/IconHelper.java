package pixlepix.auracascade.registry;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import pixlepix.auracascade.main.ConstantMod;

public class IconHelper {
    private static IIcon emptyTexture;

    public static IIcon forName(IIconRegister ir, String name) {
        return ir.registerIcon(ConstantMod.prefixMod + name);
    }

    public static IIcon forBlock(IIconRegister ir, Block block) {
        return forName(ir, block.getUnlocalizedName().replaceAll("tile.", ""));
    }

    public static IIcon forBlock(IIconRegister ir, Block block, int i) {
        return forBlock(ir, block, Integer.toString(i));
    }

    public static IIcon forBlock(IIconRegister ir, Block block, String s) {
        return forName(ir, block.getUnlocalizedName().replaceAll("tile.", "") + s);
    }

    public static IIcon forItem(IIconRegister ir, Item item) {
        return forName(ir, item.getUnlocalizedName().replaceAll("item.", ""));
    }

    public static IIcon forItem(IIconRegister ir, Item item, int i) {
        return forItem(ir, item, Integer.toString(i));
    }

    public static IIcon forItem(IIconRegister ir, Item item, String s) {
        return forName(ir, item.getUnlocalizedName().replaceAll("item.", "") + s);
    }
}