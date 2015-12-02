package pixlepix.auracascade.lexicon.page;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import org.lwjgl.opengl.GL11;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.data.Quest;
import pixlepix.auracascade.lexicon.IGuiLexiconEntry;

/**
 * Created by localmacaccount on 5/31/15.
 */
public class PageQuest extends PageRecipe {

    public Quest quest;

    public PageQuest(Quest quest) {
        super("" + quest.id);
        this.quest = quest;
    }

    @Override
    protected int getTextOffset(IGuiLexiconEntry gui) {
        return 115;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderRecipe(IGuiLexiconEntry gui, int mx, int my) {

        TextureManager render = Minecraft.getMinecraft().renderEngine;
        renderItemAtGridPos(gui, 2, 1, quest.target, true);
        renderItemAtGridPos(gui, 2, 3, quest.result, true);


        int xPos = gui.getLeft() + 60;
        int yPosTop = gui.getTop() + 39;
        int yPosBot = gui.getTop() + 97;

        FontRenderer renderer = Minecraft.getMinecraft().fontRenderer;
        renderer.drawString("Goal: ", xPos, yPosTop, 0);
        renderer.drawString("Reward: ", xPos, yPosBot, 0);

        boolean unicode = renderer.getUnicodeFlag();
        renderer.setUnicodeFlag(true);

        if (quest.hasCompleted(AuraCascade.proxy.getPlayer())) {
            GL11.glPushMatrix();
            double scale = 2;
            GL11.glScaled(scale, scale, scale);
            renderer.drawString("§2COMPLETED", (int) (xPos / scale) - 10, (int) ((yPosTop - 25) / scale), 0);
            GL11.glPopMatrix();
        }


        renderer.setUnicodeFlag(unicode);
    }
}
