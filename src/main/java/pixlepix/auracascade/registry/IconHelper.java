package pixlepix.auracascade.registry;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import pixlepix.auracascade.main.ConstantMod;

public class IconHelper {
    public static TextureAtlasSprite forName(TextureMap ir, String name) {
        return ir.registerSprite(new ResourceLocation(ConstantMod.prefixMod, name));
    }

    public static TextureAtlasSprite forBlock(TextureMap ir, Block block) {
        return forName(ir, block.getUnlocalizedName().replaceAll("tile.", ""));
    }

    public static TextureAtlasSprite forBlock(TextureMap ir, Block block, int i) {
        return forBlock(ir, block, Integer.toString(i));
    }

    public static TextureAtlasSprite forBlock(TextureMap ir, Block block, String s) {
        return forName(ir, block.getUnlocalizedName().replaceAll("tile.", "") + s);
    }

    public static TextureAtlasSprite forItem(TextureMap ir, Item item) {
        return forName(ir, item.getUnlocalizedName().replaceAll("item.", ""));
    }

    public static TextureAtlasSprite forItem(TextureMap ir, Item item, int i) {
        return forItem(ir, item, Integer.toString(i));
    }

    public static TextureAtlasSprite forItem(TextureMap ir, Item item, String s) {
        return forName(ir, item.getUnlocalizedName().replaceAll("item.", "") + s);
    }
}