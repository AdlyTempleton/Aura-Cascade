package pixlepix.auracascade.render;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.data.IToolTip;
import pixlepix.auracascade.lexicon.VazkiiRenderHelper;

/**
 * Created by pixlepix on 12/29/14.
 */
public class OverlayRender {
    @SubscribeEvent
    public void onScreenRenderEvent(RenderGameOverlayEvent event) {
        if (event.type == RenderGameOverlayEvent.ElementType.TEXT) {
            World world = AuraCascade.proxy.getWorld();
            EntityPlayer player = AuraCascade.proxy.getPlayer();
            Vec3 vec3 = player.getPosition(1.0F);
            Vec3 vec3a = player.getLook(1.0F);
            Vec3 vec3b = vec3.addVector(vec3a.xCoord * 3, vec3a.yCoord * 3, vec3a.zCoord * 3);
            MovingObjectPosition movingobjectposition = world.rayTraceBlocks(vec3, vec3b);
            if (movingobjectposition != null && movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                int x = movingobjectposition.blockX;
                int y = movingobjectposition.blockY;
                int z = movingobjectposition.blockZ;

                Block block = world.getBlock(x, y, z);

                ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
                int centerX = (resolution.getScaledWidth() - Minecraft.getMinecraft().fontRenderer.getStringWidth("hi")) / 2;

                int centerY = (resolution.getScaledHeight() - Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT) / 2;
                if (block instanceof IToolTip) {
                    IToolTip toolTip = (IToolTip) block;
                    VazkiiRenderHelper.renderTooltip(centerX, centerY, toolTip.getTooltipData(world, player, x, y, z), 0x5577ff, 0x505000ff);
                }

            }
        }
    }
}
