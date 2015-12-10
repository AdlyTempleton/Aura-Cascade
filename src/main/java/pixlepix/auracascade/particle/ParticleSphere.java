package pixlepix.auracascade.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * Created by pixlepix on 11/30/14.
 */
public class ParticleSphere extends EntityFX {
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
        this.noClip = false;

        this.particleMaxAge = 20;
        this.noClip = false;
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void renderParticle(WorldRenderer wr, Entity e, float par2, float par3, float par4, float par5, float par6, float par7) {

//        par1Tessellator.draw(); todo 1.8.8
//        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("aura:textures/particles/particleFire.png"));
//        par1Tessellator.startDrawingQuads();
//        par1Tessellator.setBrightness(200);//make sure you have this!!
        super.renderParticle(wr, e, par2, par3, par4, par5, par6, par7);
//        par1Tessellator.draw();
//        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/particle/particles.png"));
//        par1Tessellator.startDrawingQuads();
    }


}
