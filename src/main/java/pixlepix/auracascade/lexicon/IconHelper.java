/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Jan 14, 2014, 5:28:21 PM (GMT)]
 */
package pixlepix.auracascade.lexicon;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import pixlepix.auracascade.main.ConstantMod;

public final class IconHelper {


    public static TextureAtlasSprite forName(TextureMap ir, String name) {
        return ir.registerSprite(new ResourceLocation(ConstantMod.modId, name));
    }

    public static TextureAtlasSprite forBlock(TextureMap ir, Block block) {
        return forName(ir, block.getUnlocalizedName().replaceAll("tile\\.", ""));
    }

    public static TextureAtlasSprite forBlock(TextureMap ir, Block block, int i) {
        return forBlock(ir, block, Integer.toString(i));
    }

    public static TextureAtlasSprite forBlock(TextureMap ir, Block block, int i, String dir) {
        return forBlock(ir, block, Integer.toString(i), dir);
    }

    public static TextureAtlasSprite forBlock(TextureMap ir, Block block, String s) {
        return forName(ir, block.getUnlocalizedName().replaceAll("tile\\.", "") + s);
    }

    public static TextureAtlasSprite forBlock(TextureMap ir, Block block, String s, String dir) {
        return forName(ir, dir + "/" + block.getUnlocalizedName().replaceAll("tile\\.", "") + s);
    }

    public static TextureAtlasSprite forItem(TextureMap ir, Item item) {
        return forName(ir, item.getUnlocalizedName().replaceAll("item\\.", ""));
    }

    public static TextureAtlasSprite forItem(TextureMap ir, Item item, int i) {
        return forItem(ir, item, Integer.toString(i));
    }

    public static TextureAtlasSprite forItem(TextureMap ir, Item item, String s) {
        return forName(ir, item.getUnlocalizedName().replaceAll("item\\.", "") + s);
    }

}