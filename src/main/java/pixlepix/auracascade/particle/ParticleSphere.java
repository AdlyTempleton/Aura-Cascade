package pixlepix.auracascade.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

/**
 * Created by pixlepix on 11/30/14.
 */
public class ParticleSphere extends Particle {
    public ParticleSphere(World par1World, double x, double y, double z, float motionX, float motionY, float motionZ) {
        super(par1World, x, y, z, 0.0D, 0.0D, 0.0D);
        this.motionX *= 0.10000000149011612D;
        this.motionY *= 0.10000000149011612D;
        this.motionZ *= 0.10000000149011612D;
        this.motionX += motionX * 0.4D;
        this.motionY += motionY * 0.4D;
        this.motionZ += motionZ * 0.4D;
        this.particleRed = this.particleGreen = this.particleBlue = (float) (Math.random() * 0.30000001192092896D + 0.6000000238418579D);
        this.particleScale *= 0.75F;
        this.particleMaxAge = (int) (6.0D / (Math.random() * 0.8D + 0.6D));
        this.isCollided = false;

        this.particleMaxAge = 20;
        this.isCollided = false;
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void renderParticle(VertexBuffer wr, Entity e, float par2, float par3, float par4, float par5, float par6, float par7) {
        // todo 1.8.8 verify
        Tessellator.getInstance().draw();
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("color:textures/particles/particleFire.png"));
        wr.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
        wr.putBrightness4(200, 200, 200, 200);
        super.renderParticle(wr, e, par2, par3, par4, par5, par6, par7);
        Tessellator.getInstance().draw();
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/particle/particles.png"));
        wr.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
    }


}
