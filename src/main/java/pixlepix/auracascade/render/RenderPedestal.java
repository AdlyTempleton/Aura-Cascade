package pixlepix.auracascade.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import pixlepix.auracascade.block.tile.AuraTilePedestal;
import pixlepix.auracascade.main.AuraUtil;

/**
 * Created by pixlepix on 12/6/14.
 */
public class RenderPedestal extends TileEntitySpecialRenderer<AuraTilePedestal> {

    @Override
    public void renderTileEntityAt(AuraTilePedestal pedestal, double x, double y, double z, float f, int digProgress) {
        if (pedestal.itemStack != null) {
            if (pedestal.entityItem == null || !ItemStack.areItemStacksEqual(pedestal.entityItem.getEntityItem(), pedestal.itemStack)) {
                pedestal.entityItem = new EntityItem(pedestal.getWorld(), x, y, z, pedestal.itemStack);
            }
            EntityItem entityItem = pedestal.entityItem;
            x = x + .5;
            y = y + 1.16;
            z = z + .5;

            GlStateManager.pushMatrix();
            GlStateManager.enableLighting();

            pedestal.frames++;
            //This parameter is never used ._.
            Minecraft.getMinecraft().entityRenderer.disableLightmap();

            entityItem.setRotationYawHead(pedestal.frames);

            //Prevent 'jump' in the bobbing
            //Bobbing is calculated as the age plus the yaw
            AuraUtil.setItemAge(entityItem, (int) (400F - pedestal.frames));
            //TODO Fix pedestals
            //Minecraft.getMinecraft().getRenderManager().renderEntityWithPosYaw(entityItem, x, y, z, 0, entityItem.rotationYaw);
            GlStateManager.disableLighting();
            GlStateManager.popMatrix();


            Minecraft.getMinecraft().entityRenderer.enableLightmap();

            /*
            GlStateManager.pushMatrix();
            this.entityItem.hoverStart = 0.0F;
            RenderItem.renderInFrame = true;
            GlStateManager.translate((float) x + 0.5F, (float) y + 2.02F, (float) z + 0.3F);
            //GL11.glRotatef(180, 0, 1, 1);

            RenderManager.instance.renderEntityWithPosYaw(this.entityItem, 0.0D, 0.0D, 0.0D, (float) Math.PI, 0);
            RenderItem.renderInFrame = false;
            GlStateManager.popMatrix();
            */
        }
    }
}
