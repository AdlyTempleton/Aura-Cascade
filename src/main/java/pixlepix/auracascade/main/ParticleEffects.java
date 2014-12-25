package pixlepix.auracascade.main;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityCritFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.Vec3;
import pixlepix.auracascade.particle.EntityItemPoof;
import pixlepix.auracascade.particle.ParticleSphere;

/**
 * Created by pixlepix on 11/30/14.
 * Based on code by CaveJohnson212
 */
public class ParticleEffects {
    public static TextureManager textureManager = Minecraft.getMinecraft().renderEngine;
    public static Minecraft minecraft = Minecraft.getMinecraft();

    public static EntityFX spawnParticle(String particleName, double posX, double posY, double posZ, double motX, double motY, double motZ, double r, double g, double b){

        if (minecraft != null && minecraft.renderViewEntity != null && minecraft.effectRenderer != null)
        {
            int var14 = minecraft.gameSettings.particleSetting;
            if (var14 == 1 && minecraft.theWorld.rand.nextInt(3) == 0)
            {
                var14 = 2;
            }
            double var15 = minecraft.renderViewEntity.posX - posX;
            double var17 = minecraft.renderViewEntity.posY - posY;
            double var19 = minecraft.renderViewEntity.posZ - posZ;
            EntityFX var21 = null;
            double var22 = 16.0D;
            if (var15 * var15 + var17 * var17 + var19 * var19 > var22 * var22)
            {
                return null;
            }
            else
            {
                if (particleName.equals("fire"))
                {
                    var21 = new ParticleSphere(minecraft.theWorld, posX, posY, posZ, (float)motX, (float)motY, (float)motZ);
                }
                if (particleName.equals("crit"))
                {
                    var21 = new EntityCritFX(Minecraft.getMinecraft().theWorld, posX, posY, posZ, motX, motY,motZ);
                    if(r!=0 || g!=0 || b!=0) {
                        ((EntityFX) var21).setRBGColorF((float) r, (float) g, (float) b);
                    }


                }
                if (particleName.equals("fireworksSpark")){
                    var21 = new EntityItemPoof(Minecraft.getMinecraft().theWorld, posX, posY, posZ, motX, motY,motZ, minecraft.effectRenderer);

                    if(r!=0 || g!=0 || b!=0) {
                        ((EntityFX) var21).setRBGColorF((float) r, (float) g, (float) b);
                    }

                }

                minecraft.effectRenderer.addEffect(var21);
                return var21;
            }
        }
        return null;
    }
    
}
