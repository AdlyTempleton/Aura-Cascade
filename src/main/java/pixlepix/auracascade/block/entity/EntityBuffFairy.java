package pixlepix.auracascade.block.entity;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.network.PacketBurst;

import java.util.List;
import java.util.Random;

/**
 * Created by pixlepix on 12/14/14.
 */
public class EntityBuffFairy extends EntityFairy {
    public EntityBuffFairy(World p_i1582_1_) {
        super(p_i1582_1_);
        potionEffects = new PotionEffect[]{
                new PotionEffect(Potion.regeneration.getId(), 5000),
                new PotionEffect(Potion.resistance.getId(), 5000),
                new PotionEffect(Potion.damageBoost.getId(), 5000),
                //absorbtion
                new PotionEffect(Potion.field_76444_x.getId(), 5000),
                new PotionEffect(Potion.jump.getId(), 5000),
                new PotionEffect(Potion.moveSpeed.getId(), 5000)};
    }

    public PotionEffect[] potionEffects;

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if(!worldObj.isRemote && worldObj.getTotalWorldTime() % 5000 == 0){
            Random random = new Random();
            player.addPotionEffect(potionEffects[random.nextInt(potionEffects.length)]);
        }
    }
}
