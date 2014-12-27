package pixlepix.auracascade.block.tile;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.data.AuraQuantity;
import pixlepix.auracascade.data.AuraQuantityList;
import pixlepix.auracascade.data.CoordTuple;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.network.PacketBurst;

import java.util.HashMap;
import java.util.LinkedList;

public class AuraTile extends TileEntity {

    public AuraQuantityList storage = new AuraQuantityList();
    public HashMap<CoordTuple, AuraQuantityList> burstMap = null;
    public LinkedList<CoordTuple> connected = new LinkedList<CoordTuple>();
    public boolean hasConnected = false;
    public int energy = 0;

    public AuraTile() {
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {

        super.readFromNBT(nbt);
        readCustomNBT(nbt);
    }

    protected void readCustomNBT(NBTTagCompound nbt) {
        NBTTagList storageNBT = nbt.getTagList("storage", 10);
        storage.readFromNBT(storageNBT);

        NBTTagList connectionsNBT = nbt.getTagList("connected", 10);
        connected = new LinkedList<CoordTuple>();
        for(int i=0; i<connectionsNBT.tagCount(); i++){
            NBTTagCompound coordNBT = connectionsNBT.getCompoundTagAt(i);
            int x = coordNBT.getInteger("x");
            int y = coordNBT.getInteger("y");
            int z = coordNBT.getInteger("z");
            CoordTuple coord = new CoordTuple(x, y, z);
            connected.add(coord);
        }

        hasConnected = nbt.getBoolean("hasConnected");
        energy = nbt.getInteger("energy");

    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        writeCustomNBT(nbt);
    }

    protected void writeCustomNBT(NBTTagCompound nbt){
        NBTTagList storageNBT = new NBTTagList();
        storage.writeToNBT(storageNBT);
        nbt.setTag("storage", storageNBT);

        NBTTagList connectionsNBT = new NBTTagList();
        for(CoordTuple tuple: connected){
            NBTTagCompound coordNBT = new NBTTagCompound();
            coordNBT.setInteger("x", tuple.getX());
            coordNBT.setInteger("y", tuple.getY());
            coordNBT.setInteger("z", tuple.getZ());
            connectionsNBT.appendTag(coordNBT);
        }
        nbt.setTag("connected", connectionsNBT);

        nbt.setBoolean("hasConnected", hasConnected);
        nbt.setInteger("energy", energy);
    }

    public void verifyConnections() {
        LinkedList<CoordTuple> result = new LinkedList<CoordTuple>();
        for(CoordTuple next:connected){
            TileEntity tile = next.getTile(worldObj);
            if (tile instanceof AuraTile) {
                if (!((AuraTile) tile).connected.contains(new CoordTuple(this))) {
                    ((AuraTile) tile).connected.add(new CoordTuple(this));
                }
                result.add(next);
            }
        }
        connected = result;
    }

    public void connect(int x2, int y2, int z2) {
        if(!worldObj.isRemote){
            if (worldObj.getTileEntity(x2, y2, z2) instanceof AuraTile && worldObj.getTileEntity(x2, y2, z2) != this) {
                AuraTile otherNode = (AuraTile) worldObj.getTileEntity(x2, y2, z2);
                otherNode.connected.add(new CoordTuple(this));
                this.connected.add(new CoordTuple(otherNode));
                burst(new CoordTuple(x2, y2, z2), "spell", EnumAura.WHITE_AURA, 1D);

            }
        }
    }

    public void burst(CoordTuple target, String particle, EnumAura aura, double composition) {

        AuraCascade.proxy.networkWrapper.sendToAllAround(new PacketBurst(worldObj, new CoordTuple(this), target, particle, aura.r, aura.g, aura.b, composition), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 32));

    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (!hasConnected) {
            for (int i = -15; i < 15; i++) {

                connect(xCoord + i, yCoord, zCoord);

                connect(xCoord, yCoord, zCoord + i);

                connect(xCoord, yCoord + i, zCoord);
            }
            hasConnected = true;
        }

        if (!worldObj.isRemote) {

            if (worldObj.getTotalWorldTime() % 20 == 0) {
                verifyConnections();
                energy = 0;
                burstMap = new HashMap<CoordTuple, AuraQuantityList>();
                double totalWeight = 0;
                for (CoordTuple tuple : connected) {
                    if (canTransfer(tuple, EnumAura.WHITE_AURA)) {
                        totalWeight += getWeight(tuple);
                    }
                }
                //Add a balance so the node doesn't completley discharge
                //Based off 'sending aura to yourself'
                totalWeight += this instanceof AuraTileCapacitor ? 0 : 20 * 20;

                for (CoordTuple tuple : connected) {
                    double factor = getWeight(tuple) / totalWeight;
                    AuraTile other = (AuraTile) tuple.getTile(worldObj);
                    AuraQuantityList quantityList = new AuraQuantityList();
                    for (EnumAura enumAura : EnumAura.values()) {

                        if (canTransfer(tuple, enumAura)) {
                            int auraHere = storage.get(enumAura);
                            int auraThere = other.storage.get(enumAura);
                            int diff = Math.abs(auraHere - auraThere);
                            if (diff > 25) {
                                quantityList.add(new AuraQuantity(enumAura, (int) (auraHere * (float) factor)));
                            }


                        }
                    }
                    if(!quantityList.empty()) {
                        burstMap.put(tuple, quantityList);
                    }
                }
            }


        }
        if (worldObj.getTotalWorldTime() % 20 == 1) {
            verifyConnections();
            if (burstMap != null) {
                for (CoordTuple tuple : connected) {
                    if (burstMap.containsKey(tuple)) {

                        transferAura(tuple, burstMap.get(tuple), true);
                    }
                }
                burstMap = null;
            }
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }

        for(AuraQuantity quantity:storage.quantityList){
            if(quantity.getNum() > 0){
                quantity.getType().updateTick(worldObj, new CoordTuple(this), quantity);
            }
        }
    }

    public void transferAura(CoordTuple tuple, AuraQuantityList list, boolean triggerOrange) {
        if(storage.greaterThan(list)){
            ((AuraTile) tuple.getTile(worldObj)).storage.add(list);
            storage.subtract(list);
            for(EnumAura aura:EnumAura.values()) {
                burst(tuple, "crit", aura, list.getComposition(aura));
                int power = (int)((yCoord - tuple.getY()) * list.get(aura) * aura.getRelativeMass(worldObj, new CoordTuple(this)));
                if(power > 0) {
                    ((AuraTile) tuple.getTile(worldObj)).receivePower(power, aura);
                }
                if(!(!triggerOrange && aura == EnumAura.ORANGE_AURA) && list.get(aura) != 0){
                    aura.onTransfer(worldObj, new CoordTuple(this), new AuraQuantity(aura, list.get(aura)), new CoordTuple(this).getDirectionTo(tuple));
                }
            }
        }
    }

    public void receivePower(int power, EnumAura type) {
        energy += power;
    }

    public boolean canTransfer(CoordTuple tuple, EnumAura aura) {
        boolean isLower = tuple.getY() < yCoord;

        boolean isSame = tuple.getY() == yCoord;
        if(!(tuple.getTile(worldObj) instanceof AuraTile)){
            return false;
        }
        if(!(isSame || isLower )){
            return false;
        }
        if(worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord) && !(this instanceof AuraTileBlack)){
            return false;
        }
        if(aura == EnumAura.BLACK_AURA && (xCoord != tuple.getX() || yCoord == tuple.getY())){
            return false;
        }
        return ((AuraTile)tuple.getTile(worldObj)).canReceive(new CoordTuple(this), aura);
    }

    public boolean canReceive(CoordTuple source, EnumAura aura){
        return true;
    }

    public double getWeight(CoordTuple tuple) {
        return Math.pow(20 - tuple.dist(this), 2);
    }

    @Override
    public void validate() {
        super.validate();
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        writeCustomNBT(nbt);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, -999, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        readCustomNBT(pkt.func_148857_g());
    }
}
