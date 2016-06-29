package pixlepix.auracascade.block.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import pixlepix.auracascade.main.AuraUtil;

/**
 * Created by pixlepix on 12/24/14.
 */
public class AuraTilePumpBase extends AuraTile {
    public int pumpPower;
    public int pumpSpeed;

    @Override
    protected void readCustomNBT(NBTTagCompound nbt) {
        super.readCustomNBT(nbt);
        pumpPower = nbt.getInteger("pumpPower");
        pumpSpeed = nbt.getInteger("pumpSpeed");
    }

    @Override
    protected void writeCustomNBT(NBTTagCompound nbt) {
        super.writeCustomNBT(nbt);
        nbt.setInteger("pumpPower", pumpPower);
        nbt.setInteger("pumpSpeed", pumpSpeed);
    }

    public boolean isAlternator() {
        return false;
    }

    @Override
    public boolean canTransfer(BlockPos tuple) {
        return false;
    }

    @Override
    public boolean canReceive(BlockPos source) {
        return source.getY() <= getPos().getY() && super.canReceive(source);
    }

    public void addFuel(int time, int speed) {
        if (time * speed > pumpSpeed * pumpPower) {
            pumpSpeed = speed;
            pumpPower = time;
            if (isAlternator()) {
                pumpSpeed *= 3;

            }
        }
        AuraUtil.updateMonitor(worldObj, getPos());
    }



    @Override
    public void update() {
        super.update();
        if (!worldObj.isRemote && worldObj.getTotalWorldTime() % 20 == 2 && worldObj.isBlockIndirectlyGettingPowered(getPos()) == 0) {
            if (pumpPower > 0) {
                AuraTile upNode = null;
                for (int i = 1; i < 16; i++) {
                    TileEntity te = worldObj.getTileEntity(getPos().up(i));
                    if (te instanceof AuraTile && isOpenPath(getPos().up(i))) {
                        upNode = (AuraTile) te;
                        break;
                    }
                }
                if (upNode != null) {

                    pumpPower--;
                    if (pumpPower == 0) {
                        AuraUtil.updateMonitor(worldObj, getPos());

                    }
                    int dist = upNode.getPos().getY() - getPos().getY();
                    int quantity = pumpSpeed / dist;
                    if (isAlternator()) {
                        float f = getAlternatingFactor();
                        quantity *= f;
                    }

                    quantity = Math.min(quantity, storage);
                    burst(upNode.getPos(), "magicCrit");
                    storage -= quantity;
                    upNode.storage += quantity;

                }
            }
        }
    }

    public float getAlternatingFactor() {
        return (float) (1 + Math.sin(Math.PI * worldObj.getTotalWorldTime() / 10000)) / 2;

    }
}
