package pixlepix.auracascade.block.tile;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import pixlepix.auracascade.data.AuraQuantity;
import pixlepix.auracascade.data.CoordTuple;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.data.recipe.PylonRecipe;

/**
 * Created by pixlepix on 12/6/14.
 */
public class AuraTilePedestal extends AuraTile implements IInventory{

    public ItemStack itemStack;
    //Direction to center of crafting
    public ForgeDirection direction = ForgeDirection.UNKNOWN;
    public int powerReceived = 0;
    public EnumAura typeReceiving;

    //Not stored or synchronized
    //Not placed in world
    //Used for rendering
    public EntityItem entityItem;
    public long frames;


    @Override
    public void verifyConnections(){
        super.verifyConnections();
        if(direction != ForgeDirection.UNKNOWN){
            TileEntity tileEntity = worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY,zCoord + direction.offsetZ);
            if(!(tileEntity instanceof CraftingCenterTile)){
                direction = ForgeDirection.UNKNOWN;
            }
        }

        if(direction == ForgeDirection.UNKNOWN){
            for(ForgeDirection searchDir:CraftingCenterTile.pedestalRelativeLocations){
                TileEntity tileEntity = worldObj.getTileEntity(xCoord + searchDir.offsetX, yCoord + searchDir.offsetY,zCoord + searchDir.offsetZ);
                if(tileEntity instanceof CraftingCenterTile){
                    direction = searchDir;
                    break;
                }
            }
        }
    }

    public CraftingCenterTile getCenter(){
        if(direction == ForgeDirection.UNKNOWN){
            return null;
        }
        TileEntity te= new CoordTuple(this).add(direction).getTile(worldObj);
        return te instanceof CraftingCenterTile ? (CraftingCenterTile)te :null;
    }

    @Override
    public void receivePower(int power, EnumAura type) {
        verifyConnections();
        CraftingCenterTile center = getCenter();
        if(center != null){
            PylonRecipe recipe = center.getRecipe();
            if(recipe != null){
                AuraQuantity quantity = recipe.getAuraFromItem(itemStack);
                if(quantity!= null && quantity.getType() != typeReceiving){
                    typeReceiving = quantity.getType();
                    powerReceived = 0;
                }
                if(quantity != null && (quantity.getType() == EnumAura.WHITE_AURA ||quantity.getType() == type)){
                    powerReceived += power;
                    powerReceived = Math.min(powerReceived, quantity.getNum());
                    if(powerReceived >= quantity.getNum()){
                        center.checkRecipeComplete();
                    }
                }
            }
        }
    }

    @Override
    protected void readCustomNBT(NBTTagCompound nbt) {
        super.readCustomNBT(nbt);
        itemStack = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("itemStack"));
        direction = ForgeDirection.getOrientation(nbt.getInteger("direction"));
        powerReceived = nbt.getInteger("powerReceived");
        typeReceiving = nbt.getInteger("typeReceiving") == -1 ? null : EnumAura.values()[nbt.getInteger("typeReceiving")];
    }

    @Override
    protected void writeCustomNBT(NBTTagCompound nbt) {
        super.writeCustomNBT(nbt);
        if(itemStack != null) {
            NBTTagCompound compound = new NBTTagCompound();
            itemStack.writeToNBT(compound);
            nbt.setTag("itemStack", compound);
        }
        nbt.setInteger("direction", direction.ordinal());
        nbt.setInteger("powerReceived", powerReceived);
        nbt.setInteger("typeReceiving", typeReceiving == null ? -1 :typeReceiving.ordinal());
    }



    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int p_70301_1_) {
        return itemStack;
    }

    @Override
    public ItemStack decrStackSize(int slot, int amt) {
        if (itemStack != null) {
            if (itemStack.stackSize <= amt) {
                setInventorySlotContents(slot, null);
            } else {
                itemStack = itemStack.splitStack(amt);
                if (itemStack.stackSize == 0) {
                    itemStack = null;
                }
            }
        }
        return itemStack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
        ItemStack stack = itemStack;
        itemStack = null;
        return  stack;
    }

    @Override
    public void setInventorySlotContents(int p_70299_1_, ItemStack stack) {
        itemStack = stack;
    }

    @Override
    public String getInventoryName() {
        return "craftingPedestal";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return true;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        return true;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
        return true;
    }


}
