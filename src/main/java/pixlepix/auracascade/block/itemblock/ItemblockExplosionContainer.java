package pixlepix.auracascade.block.itemblock;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemBlock;
import pixlepix.auracascade.block.BlockExplosionContainer;
import pixlepix.auracascade.registry.ITTinkererItem;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

import java.util.ArrayList;

/**
 * Created by localmacaccount on 6/9/15.
 */
public class ItemblockExplosionContainer extends ItemBlock implements ITTinkererItem {
    public ItemblockExplosionContainer(Block block) {
        super(block);
    }


    @Override
    public void registerIcons(IIconRegister register) {
        itemIcon = register.registerIcon("aura:fortified" + ((BlockExplosionContainer) field_150939_a).type);
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        return null;
    }

    @Override
    public String getItemName() {
        return "fortified" + ((BlockExplosionContainer) field_150939_a).type;
    }

    @Override
    public boolean shouldRegister() {
        return true;
    }

    @Override
    public boolean shouldDisplayInTab() {
        return true;
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return null;
    }

    @Override
    public int getCreativeTabPriority() {
        return 23;
    }
}
