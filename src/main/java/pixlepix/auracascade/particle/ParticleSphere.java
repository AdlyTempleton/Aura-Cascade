package pixlepix.auracascade.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.IconFlipped;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import pixlepix.auracascade.registry.IconHelper;

/**
 * Created by pixlepix on 11/30/14.
 */
public class ParticleSphere extends EntityFX {
    public ParticleSphere(World par1World, double par2, double par4, double par6, float par8, float par9, float par10)
    {
        this(par1World, par2, par4, par6, 1.0F, par8, par9, par10);
    }
    public ParticleSphere(World par1World, double x, double y, double z, float scale, float motionX, float motionY, float motionZ)
    {
        super(par1World, x, y, z, 0.0D, 0.0D, 0.0D);
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        if (motionX == 0.0F)
        {
            motionX = 1.0F;
        }
        float var12 = (float)Math.random() * 0.4F + 0.6F;
        this.particleTextureIndexX = 0; //
        this.particleTextureIndexY = 0;
        this.particleRed = 1.0F;//RGB of your particle
        this.particleGreen = .2F;
        this.particleBlue = .2F;
        this.particleScale *= .1F;
        this.particleScale *= scale;

        this.particleMaxAge = 20;
        this.noClip = false;
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
    {

        par1Tessellator.draw();
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("Aura:textures/particles/particleFire.png"));
        par1Tessellator.startDrawingQuads();
        par1Tessellator.setBrightness(200);//make sure you have this!!
        super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
        par1Tessellator.draw();
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/particle/particles.png"));
        par1Tessellator.startDrawingQuads();
    }


}
