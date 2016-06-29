package pixlepix.auracascade.block.tile;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.block.AuraBlock;
import pixlepix.auracascade.data.PosUtil;
import pixlepix.auracascade.network.PacketBurst;

import java.util.HashMap;
import java.util.LinkedList;

public class AuraTile extends TileEntity implements ITickable {

    public int storage;
    public HashMap<BlockPos, Integer> burstMap = null;
    public LinkedList<BlockPos> connected = new LinkedList<BlockPos>();
    public boolean hasConnected = false;
    public int energy = 0;
    public int initialYValue = -1;

    public AuraTile() {
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {

        super.readFromNBT(nbt);
        readCustomNBT(nbt);
    }

    protected void readCustomNBT(NBTTagCompound nbt) {
        storage = nbt.getInteger("storage");

        NBTTagList connectionsNBT = nbt.getTagList("connected", 10);
        connected = new LinkedList<BlockPos>();
        for (int i = 0; i < connectionsNBT.tagCount(); i++) {
            NBTTagCompound coordNBT = connectionsNBT.getCompoundTagAt(i);
            int x = coordNBT.getInteger("x");
            int y = coordNBT.getInteger("y");
            int z = coordNBT.getInteger("z");
            BlockPos coord = new BlockPos(x, y, z);
            connected.add(coord);
        }

        hasConnected = nbt.getBoolean("hasConnected");
        energy = nbt.getInteger("energy");
        initialYValue = nbt.getInteger("initialYValue");

    }

    public boolean connectionBlockedByBlock(BlockPos pos) {
        Block block = worldObj.getBlockState(pos).getBlock();
        return !block.isAir(block.getDefaultState(), worldObj, pos) && (block instanceof AuraBlock || block.isOpaqueCube(block.getDefaultState()));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        writeCustomNBT(nbt);
        return nbt;
    }

    protected void writeCustomNBT(NBTTagCompound nbt) {
        nbt.setInteger("storage", storage);

        NBTTagList connectionsNBT = new NBTTagList();
        for (BlockPos tuple : connected) {
            NBTTagCompound coordNBT = new NBTTagCompound();
            coordNBT.setInteger("x", tuple.getX());
            coordNBT.setInteger("y", tuple.getY());
            coordNBT.setInteger("z", tuple.getZ());
            connectionsNBT.appendTag(coordNBT);
        }
        nbt.setTag("connected", connectionsNBT);

        nbt.setBoolean("hasConnected", hasConnected);
        nbt.setInteger("energy", energy);
        nbt.setInteger("initialYValue", initialYValue);
    }

    public boolean isOpenPath(BlockPos target) {
        int dist = (int) Math.sqrt(getPos().distanceSq(target));

        EnumFacing direction = PosUtil.directionTo(getPos(), target);
        if (direction == null) {
            return false;
        }
        for (int i = 1; i < dist; i++) {
            BlockPos between = getPos().offset(direction, i);
            if (connectionBlockedByBlock(between)) {
                return false;
            }
        }
        return true;
    }

    public void verifyConnections() {
        LinkedList<BlockPos> result = new LinkedList<BlockPos>();
        for (BlockPos next : connected) {
            TileEntity tile = worldObj.getTileEntity(next);
            if (tile instanceof AuraTile && isOpenPath(next)) {
                if (!((AuraTile) tile).connected.contains(getPos())) {
                    ((AuraTile) tile).connected.add(getPos());
                }
                if (!result.contains(next)) {
                    result.add(next);
                }
            }
        }
        connected = result;
    }

    public void connect(BlockPos pos) {
        if (worldObj.getTileEntity(pos) instanceof AuraTile && worldObj.getTileEntity(pos) != this && isOpenPath(pos)) {
            AuraTile otherNode = (AuraTile) worldObj.getTileEntity(pos);
            otherNode.connected.add(getPos());
            this.connected.add(otherNode.getPos());
            //This should only happen on initial placement
            //Not on 'follow ups'
            if (!hasConnected) {
                burst(pos, "spell");
            }

        }
    }

    public void burst(BlockPos target, String particle) {
        AuraCascade.proxy.networkWrapper.sendToAllAround(new PacketBurst(getPos(), target, particle), new NetworkRegistry.TargetPoint(worldObj.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 32));

    }

    @Override
    public void update() {

        if ((!hasConnected || worldObj.getTotalWorldTime() % 200 == 0) && !worldObj.isRemote) {
            for (int i = 1; i < 16; i++) {
                for (EnumFacing dir : EnumFacing.VALUES) {
                    connect(getPos().offset(dir, i));
                }
            }
            //This initial check does *not* see if connections are blocked
            verifyConnections();
            hasConnected = true;
        }

        if (initialYValue != pos.getY()) {
            if (initialYValue == -1 || initialYValue == 0) {
                initialYValue = pos.getY();
            } else {
                Explosion explosion = new Explosion(worldObj, null, pos.getX(), pos.getY(), pos.getZ(), 2F, true, true); // todo 1.8 isSmoking/isFlaming
                explosion.doExplosionA();
                explosion.doExplosionB(false);
                worldObj.setBlockToAir(pos);
            }
        }

        if (!worldObj.isRemote) {

            if (worldObj.getTotalWorldTime() % 20 == 0) {

                verifyConnections();
                energy = 0;
                burstMap = new HashMap<BlockPos, Integer>();
                double totalWeight = 0;
                for (BlockPos tuple : connected) {
                    if (canTransfer(tuple)) {
                        totalWeight += getWeight(tuple);
                    }
                }
                //Add a balance so the node doesn't completley discharge
                //Based off 'sending color to yourself'
                totalWeight += this instanceof AuraTileCapacitor ? 0 : 20 * 20;

                for (BlockPos pos : connected) {
                    double factor = getWeight(pos) / totalWeight;
                    AuraTile other = (AuraTile) worldObj.getTileEntity(pos);

                    if (canTransfer(pos)) {
                        int diff = Math.abs(storage - other.storage);
                        if (diff > 25) {

                            burstMap.put(pos, (int) (storage * factor));
                        }


                    }
                }
            }
        }



        if (worldObj.getTotalWorldTime() % 20 == 1) {
            verifyConnections();
            if (burstMap != null) {
                for (BlockPos tuple : connected) {
                    if (burstMap.containsKey(tuple)) {
                        transferAura(tuple, burstMap.get(tuple));

                    }
                }
                burstMap = null;
            }
        }
        markDirty();
        worldObj.markBlockRangeForRenderUpdate(pos.getX(), pos.getY(), pos.getX(), pos.getX(), pos.getY(), pos.getZ());
        worldObj.notifyBlockOfStateChange(pos, worldObj.getBlockState(pos).getBlock());
        worldObj.markAndNotifyBlock(this.pos, this.worldObj.getChunkFromBlockCoords(this.pos),this.blockType.getDefaultState(), this.blockType.getDefaultState(), 2);
    }

    public void transferAura(BlockPos pos, int auraToTransfer) {
        if (storage >= auraToTransfer) {
            ((AuraTile) worldObj.getTileEntity(pos)).storage += auraToTransfer;
            storage -= auraToTransfer;

            burst(pos, "square");
            int power = (int) ((this.pos.getY() - pos.getY()) * auraToTransfer);
            if (power > 0) {
                ((AuraTile) worldObj.getTileEntity(pos)).receivePower(power);
            }
        }
    }

    public void receivePower(int power) {
        energy += power;
    }

    public boolean canTransfer(BlockPos pos) {
        boolean isLower = pos.getY() < this.pos.getY();

        boolean isSame = pos.getY() == this.pos.getY();
        return worldObj.getTileEntity(pos) instanceof AuraTile && (isSame || isLower) && !(worldObj.isBlockIndirectlyGettingPowered(this.pos) > 0) && ((AuraTile) worldObj.getTileEntity(pos)).canReceive(getPos());

    }

    public boolean canReceive(BlockPos source) {
        return true;
    }

    public double getWeight(BlockPos pos) {
        return Math.pow(20 - Math.sqrt(pos.distanceSq(getPos())), 2);
    }



    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        writeCustomNBT(nbt);
        return new SPacketUpdateTileEntity(getPos(), -999, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readCustomNBT(pkt.getNbtCompound());
    }
}
