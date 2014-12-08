package pixlepix.auracascade.particle;

import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFireworkSparkFX;
import net.minecraft.world.World;

/**
 * Created by pixlepix on 12/7/14.
 */
public class EntityItemPoof extends EntityFireworkSparkFX {
    public EntityItemPoof(World p_i1207_1_, double p_i1207_2_, double p_i1207_4_, double p_i1207_6_, double p_i1207_8_, double p_i1207_10_, double p_i1207_12_, EffectRenderer p_i1207_14_) {
        super(p_i1207_1_, p_i1207_2_, p_i1207_4_, p_i1207_6_, p_i1207_8_, p_i1207_10_, p_i1207_12_, p_i1207_14_);
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }

        if (this.particleAge > this.particleMaxAge / 2) {
            this.setAlphaF(1.0F - ((float) this.particleAge - (float) (this.particleMaxAge / 2)) / (float) this.particleMaxAge);
        }

        this.setParticleTextureIndex(160 + (7 - this.particleAge * 8 / this.particleMaxAge));
        this.motionY -= 0.004D;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);

        if (this.onGround) {
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
        }


    }
}
