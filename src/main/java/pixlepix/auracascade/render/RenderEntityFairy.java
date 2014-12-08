package pixlepix.auracascade.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import pixlepix.auracascade.block.tile.AuraTilePedestal;

/**
 * Created by pixlepix on 12/8/14.
 */
public class RenderEntityFairy extends Render {
    @Override
    public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float p_76986_9_) {




        EntityItem entityItem = new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(Items.wheat));


        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_LIGHTING);

        //This parameter is never used ._.
        Minecraft.getMinecraft().entityRenderer.disableLightmap(0D);

        float angle = (float) ((System.currentTimeMillis() % 360000D) / 10D);

        entityItem.setRotationYawHead(angle);

        //Prevent 'jump' in the bobbing
        //Bobbing is calculated as the age plus the yaw
        entityItem.age = (int) (400F - entityItem.rotationYaw);

        RenderManager.instance.renderEntityWithPosYaw(entityItem, x, y, z, 0, angle);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();


        Minecraft.getMinecraft().entityRenderer.enableLightmap(0D);

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
