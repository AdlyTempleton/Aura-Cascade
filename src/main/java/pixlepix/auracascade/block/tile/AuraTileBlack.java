package pixlepix.auracascade.block.tile;

import net.minecraft.util.BlockPos;
import pixlepix.auracascade.data.AuraQuantity;
import pixlepix.auracascade.data.AuraQuantityList;
import pixlepix.auracascade.data.EnumAura;

/**
 * Created by pixlepix on 12/2/14.
 */
public class AuraTileBlack extends AuraTile {

    public EnumAura getAuraType() {
        return EnumAura.BLACK_AURA;
    }

    @Override
    public void update() {
        super.update();
        if (worldObj.getTotalWorldTime() % 20 == 2) {
            this.storage = new AuraQuantityList();
            if (worldObj.isBlockIndirectlyGettingPowered(pos) > 0) {
                storage.add(new AuraQuantity(getAuraType(), 100000));
            }
        }
    }

    @Override
    public boolean canReceive(BlockPos source, EnumAura aura) {
        return aura == getAuraType() && super.canReceive(source, aura);
    }
}
