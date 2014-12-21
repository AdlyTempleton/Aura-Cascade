package pixlepix.auracascade.block.tile;

import net.minecraft.block.Block;

import java.util.Random;

/**
 * Created by pixlepix on 12/21/14.
 */
public class PlanterTile extends ConsumerTile{

    public static final int COST_PER_BOOST = 150;

    @Override
    public void updateEntity() {
        super.updateEntity();
        if(!worldObj.isRemote){
            int nextBoostCost = COST_PER_BOOST;
            while (true){
                if(storedPower < nextBoostCost){
                    break;
                }
                Block block = worldObj.getBlock(xCoord, yCoord + 2, zCoord);
                block.updateTick(worldObj, xCoord, yCoord + 2, zCoord, new Random());
                storedPower -= nextBoostCost;
                nextBoostCost *= 1.05;
            }
        }
    }
}
