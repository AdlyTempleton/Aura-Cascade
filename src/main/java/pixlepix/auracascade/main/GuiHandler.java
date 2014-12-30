package pixlepix.auracascade.main;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import pixlepix.auracascade.item.ItemLexicon;
import pixlepix.auracascade.lexicon.GuiLexicon;

/**
 * Created by pixlepix on 12/27/14.
 */
public class GuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {

        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if(id == 0) {
            GuiLexicon lex = GuiLexicon.currentOpenLexicon;
            GuiLexicon.stackUsed = player.getCurrentEquippedItem();
            if (GuiLexicon.stackUsed == null || !(GuiLexicon.stackUsed.getItem() instanceof ItemLexicon))
                return null;
            return lex;
        }
        return null;

    }
}