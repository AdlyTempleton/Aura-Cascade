package pixlepix.auracascade.main;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.TextureManager;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.particle.EntityItemPoof;
import pixlepix.auracascade.particle.ParticleSphere;
import pixlepix.auracascade.render.ParticleBeam;

/**
 * Created by pixlepix on 11/30/14.
 * Based on code by CaveJohnson212
 */
public class ParticleEffects {
    public static TextureManager textureManager = Minecraft.getMinecraft().renderEngine;
    public static Minecraft minecraft = Minecraft.getMinecraft();


    public static void spawnParticle(String particleName, double posX, double posY, double posZ, double motX, double motY, double motZ) {
        spawnParticle(particleName, posX, posY, posZ, motX, motY, motZ, 0, 0, 0);
    }
    
    public static void spawnParticle(String particleName, double posX, double posY, double posZ, double motX, double motY, double motZ, double r, double g, double b) {

        if (minecraft != null && minecraft.getRenderViewEntity() != null && minecraft.effectRenderer != null) {
            double var15 = minecraft.getRenderViewEntity().posX - posX;
            double var17 = minecraft.getRenderViewEntity().posY - posY;
            double var19 = minecraft.getRenderViewEntity().posZ - posZ;
            Particle entityfx = null;
            double var22 = 16.0D;
            if (!(var15 * var15 + var17 * var17 + var19 * var19 > var22 * var22)) {
                if (particleName.equals("fire")) {
                    entityfx = new ParticleSphere(minecraft.theWorld, posX, posY, posZ, (float) motX, (float) motY, (float) motZ);
                }
                // todo 1.8.8 recheck all of these
                //Many particles are rendered here to make use of UnlimitedEffectRendererif (p_72726_1_.equals("spell"))
                if (particleName.equals("spell")) {
                    entityfx = new EntitySpellParticleFX.Factory().getEntityFX(-1, minecraft.theWorld, posX, posY, posZ, (float) motX, (float) motY, (float) motZ);
                }
                if (particleName.equals("happyVillager")) {
                    entityfx = new EntityAuraFX.HappyVillagerFactory().getEntityFX(-1, minecraft.theWorld, posX, posY, posZ, (float) motX, (float) motY, (float) motZ);
                    entityfx.setParticleTextureIndex(82);
                    entityfx.setRBGColorF(1.0F, 1.0F, 1.0F);
                }
                if (particleName.equals("magicCrit")) {
                    entityfx = new EntityCrit2FX.MagicFactory().getEntityFX(-1, Minecraft.getMinecraft().theWorld, posX, posY, posZ, (float) motX, (float) motY, (float) motZ);
                    entityfx.setRBGColorF(entityfx.getRedColorF() * 0.3F, entityfx.getGreenColorF() * 0.8F, entityfx.getBlueColorF());
                    entityfx.nextTextureIndexX();
                }
                if (particleName.equals("enchantmenttable")) {
                    entityfx = new EntityEnchantmentTableParticleFX.EnchantmentTable().getEntityFX(-1, Minecraft.getMinecraft().theWorld, posX, posY, posZ, (float) motX, (float) motY, (float) motZ);
                    entityfx.nextTextureIndexX();
                }
                if (particleName.equals("crit")) {
                    entityfx = new EntityCritFX.Factory().getEntityFX(-1, Minecraft.getMinecraft().theWorld, posX, posY, posZ, motX, motY, motZ);
                    if (r != 0 || g != 0 || b != 0) {
                        entityfx.setRBGColorF((float) r, (float) g, (float) b);
                    }
                }
                if (particleName.equals("square")) {
                    entityfx = new ParticleBeam(Minecraft.getMinecraft().theWorld, posX, posY, posZ, (float) r, (float) g, (float) b);
                }
                if (particleName.equals("squareLong")) {
                    entityfx = new ParticleBeam(Minecraft.getMinecraft().theWorld, posX, posY, posZ, (float) r, (float) g, (float) b, true);
                }
                if (particleName.equals("witchMagic")) {
                    entityfx = new EntitySpellParticleFX.WitchFactory().getEntityFX(-1, Minecraft.getMinecraft().theWorld, posX, posY, posZ, motX, motY, motZ);
                    ((EntitySpellParticleFX) entityfx).setBaseSpellTextureIndex(144);
                    float f = Minecraft.getMinecraft().theWorld.rand.nextFloat() * 0.5F + 0.35F;
                    entityfx.setRBGColorF((float) r, (float) g, (float) b);
                    entityfx.ySpeed = -.07;
                }
                if (particleName.equals("fireworksSpark")) {
                    entityfx = new EntityItemPoof(Minecraft.getMinecraft().theWorld, posX, posY, posZ, motX, motY, motZ, minecraft.effectRenderer);

                    if (r != 0 || g != 0 || b != 0) {
                        entityfx.setRBGColorF((float) r, (float) g, (float) b);
                    }

                }

                AuraCascade.proxy.addEffectBypassingLimit(entityfx);
            }
        }

    }

}
