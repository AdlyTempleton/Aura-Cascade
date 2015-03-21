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
    public double tx;
    public double ty;
    public double tz;
    public float r;
    public float g;
    public float b;
    public boolean pump;

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

        this.motionX = (tx - x) / 10;
        this.motionY = (ty - y) / 10;
        this.motionZ = (tz - z) / 10;
        this.particleRed = this.particleGreen = this.particleBlue = (float)(Math.random() * 0.30000001192092896D + 0.6000000238418579D);
        this.particleScale *= 0.75F;
        this.particleAge = 0;
        this.particleMaxAge = 10;
        this.noClip = true;
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
