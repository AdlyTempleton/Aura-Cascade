package pixlepix.auracascade.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * Created by localmacaccount on 3/21/15.
 */
public class ParticleBeam extends EntityFX {
    private  double tx;
    private  double ty;
    private  double tz;
    private  float r;
    private  float g;
    private  float b;
    private  boolean pump;

    public ParticleBeam(World world, double x, double y, double z, double tx, double ty, double tz, float r, float g, float b, boolean pump) {
        super(world, x, y, z, 0, 0, 0);
        this.tx = tx;
        this.ty = ty;
        this.tz = tz;
        this.r = r;
        this.g = g;
        this.b = b;
        this.pump = pump;
        setParticleTextureIndex(0);

        this.motionX *= (tx - x) / 10;
        this.motionY *= (tx - x) / 10;
        this.motionZ *= (tx - x) / 10;
        this.particleRed = this.particleGreen = this.particleBlue = (float)(Math.random() * 0.30000001192092896D + 0.6000000238418579D);
        this.particleScale *= 0.75F;
        this.particleAge = 0;
        this.particleMaxAge = 20;
        this.particleMaxAge = (int)(6.0D / (Math.random() * 0.8D + 0.6D));
        this.particleMaxAge = (int)((float)this.particleMaxAge);
        this.noClip = false;
        onUpdate();
    }

    @Override
    public void renderParticle(Tessellator tess, float f1, float f2, float f3, float f4, float f5, float f6) {
        tess.draw();
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("aura:textures/particles/burst.png"));
        tess.startDrawingQuads();
        tess.setBrightness(200);
        super.renderParticle(tess, f1, f2, f3, f4, f5, f6);
        tess.draw();
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/particle/particles.png"));
        tess.startDrawingQuads();
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
    }
}
