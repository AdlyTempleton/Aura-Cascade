package pixlepix.auracascade.block.tile;

import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import cofh.api.transport.IEnderEnergyHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import pixlepix.auracascade.data.CoordTuple;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.main.Config;
import pixlepix.auracascade.main.ParticleEffects;

import java.util.*;

/**
 * Created by localmacaccount on 2/24/15.
 */
public class AuraTileRF extends AuraTile {

    public ArrayList<CoordTuple> foundTiles = new ArrayList<CoordTuple>();

    public HashSet<CoordTuple> particleTiles = new HashSet<CoordTuple>();

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
    public void updateEntity() {
        super.updateEntity();
        if (worldObj.getTotalWorldTime() % 40 == 0) {
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
            particleTiles.clear();
            //First, find all things near tiles
            for (CoordTuple tuple : foundTiles) {
                for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
                    particleTiles.add(tuple.add(direction));
                }
            }

            //Remove things that are 'inside' the bubble
            Iterator iterator = particleTiles.iterator();
            while (iterator.hasNext()) {
                CoordTuple tuple = (CoordTuple) iterator.next();
                if (foundTiles.contains(tuple)) {
                    iterator.remove();
                }

            }

            disabled = foundTiles.size() > 4;

            for (CoordTuple tuple : foundTiles) {

                String modid = GameRegistry.findUniqueIdentifierFor(tuple.getBlock(worldObj)).modId;
                TileEntity te = tuple.getTile(worldObj);
                if (te instanceof IEnderEnergyHandler) {
                    disabled = true;
                }
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
                if (te instanceof IEnergyProvider && !((IEnergyConnection) (te)).canConnectEnergy(ForgeDirection.UNKNOWN)) {
                    boolean isWhitelisted = false;
                    for (String clazz : whitelist) {
                        if (te.getClass().getName().toLowerCase().contains(clazz.toLowerCase())) {
                            isWhitelisted = true;
                        }
                    }
                    for (String whitelistMod : whitelistModId) {
                        if (modid.toLowerCase().contains(whitelistMod)) {
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
            for (CoordTuple tuple : particleTiles) {
                Random random = new Random();
                double x = tuple.getX() + random.nextDouble();
                double y = tuple.getY() + random.nextDouble();
                double z = tuple.getZ() + random.nextDouble();
                ParticleEffects.spawnParticle("witchMagic", x, y, z, 0, 0, 0, 255, 0, !disabled ? 50 : 0);
            }

        }

        if (!disabled) {
            int divisions = foundTiles.size();
            for (CoordTuple tuple : foundTiles) {

                TileEntity entity = tuple.getTile(worldObj);
                if (!(entity instanceof IEnergyReceiver) || ((IEnergyReceiver) entity).receiveEnergy(ForgeDirection.UNKNOWN, 1, true) <= 0) {
                    divisions--;
                }
            }
            if (divisions > 0) {
                for (CoordTuple tuple : foundTiles) {
                    TileEntity entity = tuple.getTile(worldObj);
                    if (entity instanceof IEnergyReceiver) {
                        ((IEnergyReceiver) entity).receiveEnergy(ForgeDirection.UNKNOWN, (int) (lastPower * Config.powerFactor / divisions), false);
                    }
                }
            }
        }

        //Just before aura moves
        if (worldObj.getTotalWorldTime() % 20 == 0 && !worldObj.isRemote) {
            lastPower = 0;
        }
        if (worldObj.getTotalWorldTime() % 20 == 1) {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }


    }

    @Override
    public void receivePower(int power, EnumAura type) {
        lastPower += power;
    }
}
