package pixlepix.auracascade.block.tile;

import java.util.ArrayList;
import java.util.HashSet;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import pixlepix.auracascade.data.EnumAura;

/**
 * Created by localmacaccount on 2/24/15.
 */
public class AuraTileRF extends AuraTile {

    public ArrayList<BlockPos> foundTiles = new ArrayList<BlockPos>();

    public HashSet<BlockPos> particleTiles = new HashSet<BlockPos>();

    public int lastPower = 0;
    public boolean disabled = false;
    public String[] blacklist = new String[]{"InductionPort", "EnergyCube", "ChargePad", "EnergyStorage", "TileEntityMagnetic", "TileTransceiver", "TileEntityRift", "TileTransvectorInterface", "TileRemoteInterface", "TileEntityEnergyDistributor", "TileEntityEnderEnergyDistributor", "TileCharger", "TileCell", "TileEntityTransferNodeEnergy", "TileEnergyInfuser"};
    public String[] whitelist = new String[]{"tileentityenderthermiclavapump", "tileentityenderquarry"};
    public String[] blacklistModId = new String[]{"quantumflux"};
    public String[] whitelistModId = new String[]{"buildcraft", "GalacticraftCore", "progressiveautomation", "Mekanism"};

    @Override
    protected void readCustomNBT(NBTTagCompound nbt) {
        super.readCustomNBT(nbt);
        lastPower = nbt.getInteger("lastPower");
    }

    @Override
    protected void writeCustomNBT(NBTTagCompound nbt) {
        super.writeCustomNBT(nbt);
        nbt.setInteger("lastPower", lastPower);
    }

    @Override
    public void update() {
        super.update();
        /*
        if (worldObj.getTotalWorldTime() % 40 == 0) {
            foundTiles.clear();
            LinkedList<BlockPos> nextTiles = new LinkedList<BlockPos>();
            nextTiles.add(getPos());
            while (nextTiles.size() > 0) {
                BlockPos target = nextTiles.removeFirst();
                for (EnumFacing direction : EnumFacing.VALUES) {
                    BlockPos adjacent = target.offset(direction);
                    TileEntity entity = worldObj.getTileEntity(adjacent);
                    if (entity instanceof IEnergyReceiver) {
                        if (!nextTiles.contains(adjacent) && !foundTiles.contains(adjacent)) {
                            nextTiles.add(adjacent);
                            foundTiles.add(adjacent);
                        }
                    }
                }
            }
            particleTiles.clear();
            //First, find all things near tiles
            for (BlockPos pos : foundTiles) {
                for (EnumFacing direction : EnumFacing.VALUES) {
                    particleTiles.add(pos.offset(direction));
                }
            }

            //Remove things that are 'inside' the bubble
            Iterator iterator = particleTiles.iterator();
            while (iterator.hasNext()) {
                BlockPos pos = (BlockPos) iterator.next();
                if (foundTiles.contains(pos)) {
                    iterator.remove();
                }

            }

            disabled = foundTiles.size() > 4;

            for (BlockPos pos : foundTiles) {

                String modid = GameData.getBlockRegistry().getNameForObject(worldObj.getBlockState(pos).getBlock()).getResourceDomain();
                TileEntity te = worldObj.getTileEntity(pos);
                // todo 1.8.8 await duct update
                // if (te instanceof IEnderEnergyHandler) {
                //    disabled = true;
                // }
                for (String clazz : blacklist) {
                    if (te.getClass().getName().toLowerCase().contains(clazz.toLowerCase())) {
                        boolean whitelistedByMod = false;
                        for (String whitelistMod : whitelistModId) {
                            if (modid.toLowerCase().contains(whitelistMod)) {
                                whitelistedByMod = true;
                            }
                        }
                        if (!whitelistedByMod) {
                            disabled = true;
                        }
                    }
                }
                for (String blacklistMod : blacklistModId) {
                    if (modid.equals(blacklistMod)) {
                        disabled = true;
                    }
                }
                if (te instanceof IEnergyProvider && !((IEnergyConnection) (te)).canConnectEnergy(null)) {
                    AuraCascade.analytics.eventError(GAErrorEvent.Severity.info, "Blacklisted IEnergyProvider authmatically: " + te.getClass().getName());
                    boolean isWhitelisted = false;
                    for (String clazz : whitelist) {
                        if (te.getClass().getName().toLowerCase().contains(clazz.toLowerCase())) {
                            isWhitelisted = true;
                        }
                    }
                    for (String whitelistMod : whitelistModId) {
                        if (modid.toLowerCase().contains(whitelistMod.toLowerCase())) {
                            isWhitelisted = true;
                        }
                    }
                    if (!isWhitelisted) {
                        disabled = true;
                    }
                }
            }
        }

        if (worldObj.isRemote && worldObj.getTotalWorldTime() % 3 == 0) {
            for (BlockPos tuple : particleTiles) {
                Random random = new Random();
                double x = tuple.getX() + random.nextDouble();
                double y = tuple.getY() + random.nextDouble();
                double z = tuple.getZ() + random.nextDouble();
                ParticleEffects.spawnParticle("witchMagic", x, y, z, 0, 0, 0, 255, 0, !disabled ? 50 : 0);
            }

        }

        if (!disabled) {
            int divisions = foundTiles.size();
            for (BlockPos pos : foundTiles) {

                TileEntity entity = worldObj.getTileEntity(pos);
                if (!(entity instanceof IEnergyReceiver) || ((IEnergyReceiver) entity).receiveEnergy(null, 1, true) <= 0) {
                    divisions--;
                }
            }
            if (divisions > 0) {
                for (BlockPos pos : foundTiles) {
                    TileEntity entity = worldObj.getTileEntity(pos);
                    if (entity instanceof IEnergyReceiver) {
                        ((IEnergyReceiver) entity).receiveEnergy(null, (int) (lastPower * Config.powerFactor / divisions), false);
                    }
                }
            }
        }

        //Just before aura moves
        if (worldObj.getTotalWorldTime() % 20 == 0 && !worldObj.isRemote) {
            lastPower = 0;
        }
        if (worldObj.getTotalWorldTime() % 20 == 1) {
            worldObj.markBlocksDirtyVertical(pos.getX(), pos.getZ(), pos.getX(), pos.getZ());
        }
	*/

    }

    @Override
    public void receivePower(int power, EnumAura type) {
        lastPower += power;
    }
}
