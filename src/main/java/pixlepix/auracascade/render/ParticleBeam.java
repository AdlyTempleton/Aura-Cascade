package pixlepix.auracascade.render;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

/**
 * Created by pixlepix on 11/30/14.
 */
public class ParticleBeam extends EntityFX {

    public ParticleBeam(World par1World, double x, double y, double z, float red, float blue, float green, boolean longLived) {
        this(par1World, x, y, z, red, blue, green);
        this.particleMaxAge *= 5;
    }
    
    public ParticleBeam(World par1World, double x, double y, double z, float red, float blue, float green) {
        super(par1World, x, y, z, 0.0D, 0.0D, 0.0D);
        this.xSpeed *= 0.10000000149011612D;
        this.ySpeed *= 0.10000000149011612D;
        this.zSpeed *= 0.10000000149011612D;
        this.xSpeed += xSpeed * 0.4D;
        this.ySpeed += ySpeed * 0.4D;
        this.zSpeed += zSpeed * 0.4D;
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
