package pixlepix.auracascade.block.tile;

import cofh.api.energy.IEnergyReceiver;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.data.CoordTuple;
import pixlepix.auracascade.data.EnumAura;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by localmacaccount on 2/24/15.
 */
public class AuraTileRF extends AuraTile {

    public ArrayList<CoordTuple> foundTiles = new ArrayList<CoordTuple>();

    public int lastPower = 0;

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (worldObj.getTotalWorldTime() % 100 == 0) {
            foundTiles.clear();
            LinkedList<CoordTuple> nextTiles = new LinkedList<CoordTuple>();
            nextTiles.add(new CoordTuple(this));
            while (nextTiles.size() > 0) {
                CoordTuple target = nextTiles.removeFirst();
                for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
                    CoordTuple adjacent = target.add(direction);
                    TileEntity entity = adjacent.getTile(worldObj);
                    if (entity instanceof IEnergyReceiver) {
                        if (!nextTiles.contains(adjacent) && !foundTiles.contains(adjacent)) {
                            nextTiles.add(adjacent);
                            foundTiles.add(adjacent);
                        }
                    }
                }
            }
        }

        if (foundTiles.size() <= 10) {
            for (CoordTuple tuple : foundTiles) {
                TileEntity entity = tuple.getTile(worldObj);
                if (entity instanceof IEnergyReceiver) {
                    ((IEnergyReceiver) entity).receiveEnergy(ForgeDirection.UNKNOWN, (int) (lastPower * .25 / foundTiles.size()), false);
                }

            }
        }

        //Just before aura moves
        if (worldObj.getTotalWorldTime() % 20 == 0) {
            lastPower = 0;
        }


    }

    @Override
    public void receivePower(int power, EnumAura type) {
        super.receivePower(power, type);
        lastPower += power;
    }
}
