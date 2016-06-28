package pixlepix.auracascade.render;

import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;

/**
 * Created by pixlepix on 11/30/14.
 */
public class ParticleBeam extends Particle {

    public ParticleBeam(World par1World, double x, double y, double z, float red, float blue, float green, boolean longLived) {
        this(par1World, x, y, z, red, blue, green);
        this.particleMaxAge *= 5;
    }
    
    public ParticleBeam(World par1World, double x, double y, double z, float red, float blue, float green) {
        super(par1World, x, y, z, 0.0D, 0.0D, 0.0D);
        this.motionX *= 0.10000000149011612D;
        this.motionY *= 0.10000000149011612D;
        this.motionZ *= 0.10000000149011612D;
        this.motionX += motionX * 0.4D;
        this.motionY += motionY * 0.4D;
        this.motionZ += motionZ * 0.4D;
        float offset = (float) (Math.random() * 0.30000001192092896D);

        red -= offset;
        blue -= offset;
        green -= offset;


        setRBGColorF(red, blue, green);

        this.particleScale *= 2F;
        this.particleMaxAge = (int) (6.0D / (Math.random() * 0.8D + 0.6D));
        this.isCollided = false;
        this.isExpired = false;
    }
}
