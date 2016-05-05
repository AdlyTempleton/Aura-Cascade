package pixlepix.auracascade.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import pixlepix.auracascade.block.entity.EntityFairy;
import pixlepix.auracascade.item.ItemFairyCharm;
import pixlepix.auracascade.registry.BlockRegistry;

/**
 * Created by pixlepix on 12/8/14.
 */
public class RenderEntityFairy extends Render<EntityFairy> {

    private final ItemStack stack = new ItemStack(BlockRegistry.getFirstItemFromClass(ItemFairyCharm.class), 1, 100);

    public RenderEntityFairy(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(EntityFairy entity, double x, double y, double z, float p_76986_8_, float p_76986_9_) {

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(0.5F, 0.5F, 0.5F);
        // Billboard towards the player
        GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.GROUND);
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, p_76986_8_, p_76986_9_);

    }


    @Override
    protected ResourceLocation getEntityTexture(EntityFairy p_110775_1_) {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }
}
