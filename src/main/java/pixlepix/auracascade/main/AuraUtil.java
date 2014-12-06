package pixlepix.auracascade.main;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import java.util.List;

/**
 * Created by pixlepix on 11/29/14.
 */
public class AuraUtil {
    
    public static void keepAlive(TileEntity te, int range){
        List<EntityItem> nearbyItems = te.getWorldObj().getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(te.xCoord - range, te.yCoord - range, te.zCoord - range, te.xCoord + range, te.yCoord + range, te.zCoord + range));
        for (EntityItem entityItem : nearbyItems) {
            entityItem.lifespan = Integer.MAX_VALUE;
        }
    }
    
}
