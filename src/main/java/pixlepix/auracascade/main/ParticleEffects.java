package pixlepix.auracascade.main;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.texture.TextureManager;
import pixlepix.auracascade.particle.ParticleSphere;

/**
 * Created by pixlepix on 11/30/14.
 * Based on code by CaveJohnson212
 */
public class ParticleEffects {
    public static TextureManager textureManager = Minecraft.getMinecraft().renderEngine;
    public static Minecraft minecraft = Minecraft.getMinecraft();

    public static EntityFX spawnParticle(String particleName, double par2, double par4, double par6, double par8, double par10, double par12){
        if (minecraft != null && minecraft.renderViewEntity != null && minecraft.effectRenderer != null)
        {
            int var14 = minecraft.gameSettings.particleSetting;
            if (var14 == 1 && minecraft.theWorld.rand.nextInt(3) == 0)
            {
                var14 = 2;
            }
            double var15 = minecraft.renderViewEntity.posX - par2;
            double var17 = minecraft.renderViewEntity.posY - par4;
            double var19 = minecraft.renderViewEntity.posZ - par6;
            EntityFX var21 = null;
            double var22 = 16.0D;
            if (var15 * var15 + var17 * var17 + var19 * var19 > var22 * var22)
            {
                return null;
            }
            else if (var14 > 1)
            {
                return null;
            }
            else
            {
                if (particleName.equals("fire"))
                {
                    var21 = new ParticleSphere(minecraft.theWorld, par2, par4, par6, (float)par8, (float)par10, (float)par12);
                }
                minecraft.effectRenderer.addEffect(var21);
                return var21;
            }
        }
        return null;
    }
    
}
