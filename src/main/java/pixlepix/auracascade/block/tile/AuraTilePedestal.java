package pixlepix.auracascade.block.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by pixlepix on 12/6/14.
 */
public class AuraTilePedestal extends AuraTile implements IInventory{

    public ItemStack itemStack;


    @Override
    protected void readCustomNBT(NBTTagCompound nbt) {
        super.readCustomNBT(nbt);
        itemStack = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("itemStack"));
    }

    @Override
    protected void writeCustomNBT(NBTTagCompound nbt) {
        super.writeCustomNBT(nbt);
        if(itemStack != null) {
            NBTTagCompound compound = new NBTTagCompound();
            itemStack.writeToNBT(compound);
            nbt.setTag("itemStack", compound);
        }
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
