package pixlepix.auracascade.block.tile;

import pixlepix.auracascade.data.AuraQuantity;
import pixlepix.auracascade.data.AuraQuantityList;
import pixlepix.auracascade.data.EnumAura;

/**
 * Created by pixlepix on 12/2/14.
 */
public class AuraTileBlack extends AuraTile {

    @Override
    public void updateEntity() {
        super.updateEntity();
        if(worldObj.getTotalWorldTime() % 20 == 2){
            this.storage = new AuraQuantityList();
            if(worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
                storage.add(new AuraQuantity(EnumAura.BLACK_AURA, 100000));
            }
        }
    }
}
