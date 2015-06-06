package pixlepix.auracascade.render;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import pixlepix.auracascade.AuraCascade;

/**
 * Created by localmacaccount on 6/5/15.
 */
public class RenderFortified implements ISimpleBlockRenderingHandler {
    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        renderer.renderBlockAsItem(block, modelId, 0);
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        //Basic icon
        IIcon icon = block.getIcon(0, 0);
        float u = icon.getMinU();
        float v = icon.getMinV();
        float U = icon.getMaxU();
        float V = icon.getMaxV();

        Tessellator tessellator = Tessellator.instance;
        tessellator.addTranslation(x, y, z);
        tessellator.addVertexWithUV(0, 1, 1, u, v);
        tessellator.addVertexWithUV(1, 1, 1, u, V);
        tessellator.addVertexWithUV(1, 1, 0, U, V);
        tessellator.addVertexWithUV(0, 1, 0, U, v);
        tessellator.addTranslation(-x, -y, -z);
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return AuraCascade.proxy.renderIdFortified;
    }
}
