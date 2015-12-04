package pixlepix.auracascade.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import pixlepix.auracascade.block.entity.EntityFairy;
import pixlepix.auracascade.item.ItemFairyCharm;
import pixlepix.auracascade.registry.BlockRegistry;

/**
 * Created by pixlepix on 12/8/14.
 */
public class RenderEntityFairy extends Render {

    protected RenderEntityFairy(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float p_76986_9_) {

        ItemStack stack = new ItemStack(BlockRegistry.getFirstItemFromClass(ItemFairyCharm.class), 1, 100);

        GlStateManager.pushMatrix();
        GlStateManager.enableLighting();
        GlStateManager.enableBlend();
        //This parameter is never used ._.

        Minecraft.getMinecraft().entityRenderer.disableLightmap();


        //Prevent 'jump' in the bobbing
        //Bobbing is calculated as the age plus the yaw
        renderItem.doRender(entityItem, x, y, z, 0, 0);

        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.popMatrix();


        Minecraft.getMinecraft().entityRenderer.enableLightmap();

            /*
            GL11.glPushMatrix();
            this.entityItem.hoverStart = 0.0F;
            RenderItem.renderInFrame = true;
            GL11.glTranslatef((float) x + 0.5F, (float) y + 2.02F, (float) z + 0.3F);
            //GL11.glRotatef(180, 0, 1, 1);

            RenderManager.instance.renderEntityWithPosYaw(this.entityItem, 0.0D, 0.0D, 0.0D, (float) Math.PI, 0);
            RenderItem.renderInFrame = false;
            GL11.glPopMatrix();
            */
    }


    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return new ResourceLocation("minecraft", "/blocks/cobblestone.png");
    }
}
