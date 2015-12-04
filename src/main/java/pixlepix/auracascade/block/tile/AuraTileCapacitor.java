package pixlepix.auracascade.block.tile;

import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraft.nbt.NBTTagCompound;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.network.PacketBurst;

/**
 * Created by pixlepix on 12/5/14.
 */
public class AuraTileCapacitor extends AuraTile {

    public int[] storageValues = new int[]{100, 1000, 10000, 100000};
    public int storageValueIndex = 1;
    public int ticksDisabled = 0;
    public boolean aboutToBurst = false;

    @Override
    protected void readCustomNBT(NBTTagCompound nbt) {
        super.readCustomNBT(nbt);
        storageValueIndex = nbt.getInteger("storageValueIndex");
        ticksDisabled = nbt.getInteger("ticksDisabled");
        aboutToBurst = nbt.getBoolean("aboutToBurst");
    }

    @Override
    protected void writeCustomNBT(NBTTagCompound nbt) {
        super.writeCustomNBT(nbt);
        nbt.setInteger("storageValueIndex", storageValueIndex);
        nbt.setInteger("ticksDisabled", ticksDisabled);
        nbt.setBoolean("aboutToBurst", aboutToBurst);

    }

    @Override
    public void update() {
        super.update();
        if (!worldObj.isRemote) {
            if (ticksDisabled > 0) {
                ticksDisabled--;
            }

            if (worldObj.getTotalWorldTime() % 19 == 0 && storage.getTotalAura() >= storageValues[storageValueIndex]) {
                aboutToBurst = true;
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 3);
                AuraCascade.proxy.networkWrapper.sendToAllAround(new PacketBurst(2, xCoord + .5, yCoord + .5, zCoord + .5), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 32));
            }

            if (worldObj.getTotalWorldTime() % 5 == 0 && aboutToBurst) {
                aboutToBurst = false;
                ticksDisabled = 110;

                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 3);
                worldObj.notifyBlockOfStateChange(pos, worldObj.getBlockState(pos).getBlock());
            }
        }
    }

    @Override
    public boolean canTransfer(BlockPos tuple, EnumAura aura) {
        return storage.getTotalAura() >= storageValues[storageValueIndex] && super.canTransfer(tuple, aura);
    }

    @Override
    public boolean canReceive(BlockPos source, EnumAura aura) {
        return ticksDisabled == 0 && super.canReceive(source, aura);
    }
}
