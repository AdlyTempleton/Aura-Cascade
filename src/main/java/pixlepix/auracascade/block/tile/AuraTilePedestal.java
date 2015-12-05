package pixlepix.auracascade.block.tile;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import pixlepix.auracascade.data.AuraQuantity;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.data.recipe.PylonRecipe;

/**
 * Created by pixlepix on 12/6/14.
 */
public class AuraTilePedestal extends AuraTile implements IInventory {

    public ItemStack itemStack;
    //Direction to center of crafting
    public EnumFacing direction = null;
    public int powerReceived = 0;
    public EnumAura typeReceiving;

    //Not stored or synchronized
    //Not placed in world
    //Used for rendering
    public EntityItem entityItem;
    public long frames;


    @Override
    public void verifyConnections() {
        super.verifyConnections();
        if (direction != null) {
            TileEntity tileEntity = worldObj.getTileEntity(getPos().offset(direction));
            if (!(tileEntity instanceof CraftingCenterTile)) {
                direction = null;
            }
        }

        if (direction == null) {
            for (EnumFacing searchDir : CraftingCenterTile.pedestalRelativeLocations) {
                TileEntity tileEntity = worldObj.getTileEntity(getPos().offset(searchDir));
                if (tileEntity instanceof CraftingCenterTile) {
                    direction = searchDir;
                    break;
                }
            }
        }
    }

    public CraftingCenterTile getCenter() {
        if (direction == null) {
            return null;
        }
        TileEntity te = worldObj.getTileEntity(getPos().offset(direction));
        return te instanceof CraftingCenterTile ? (CraftingCenterTile) te : null;
    }

    @Override
    public void receivePower(int power, EnumAura type) {
        verifyConnections();
        CraftingCenterTile center = getCenter();
        if (center != null) {
            PylonRecipe recipe = center.getRecipe();
            if (recipe != null) {
                AuraQuantity quantity = recipe.getAuraFromItem(itemStack);
                if (quantity != null && quantity.getType() != typeReceiving) {
                    typeReceiving = quantity.getType();
                    powerReceived = 0;
                }
                if (quantity != null && (quantity.getType() == EnumAura.WHITE_AURA || quantity.getType() == type)) {
                    powerReceived += power;
                    powerReceived = Math.min(powerReceived, quantity.getNum());
                    if (powerReceived >= quantity.getNum()) {
                        center.checkRecipeComplete();
                    }
                }
            }
        }
        worldObj.markBlockForUpdate(getPos());
    }

    @Override
    protected void readCustomNBT(NBTTagCompound nbt) {
        super.readCustomNBT(nbt);
        itemStack = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("itemStack"));
        direction = EnumFacing.getFront(nbt.getInteger("direction"));
        powerReceived = nbt.getInteger("powerReceived");
        typeReceiving = nbt.getInteger("typeReceiving") == -1 ? null : EnumAura.values()[nbt.getInteger("typeReceiving")];
    }

    @Override
    protected void writeCustomNBT(NBTTagCompound nbt) {
        super.writeCustomNBT(nbt);
        if (itemStack != null) {
            NBTTagCompound compound = new NBTTagCompound();
            itemStack.writeToNBT(compound);
            nbt.setTag("itemStack", compound);
        }
        nbt.setInteger("direction", direction.getIndex());
        nbt.setInteger("powerReceived", powerReceived);
        nbt.setInteger("typeReceiving", typeReceiving == null ? -1 : typeReceiving.ordinal());
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
    public ItemStack removeStackFromSlot(int p_70304_1_) {
        ItemStack stack = itemStack;
        itemStack = null;
        return stack;
    }

    @Override
    public void setInventorySlotContents(int p_70299_1_, ItemStack stack) {
        itemStack = stack;
    }

    @Override
    public String getName() {
        return "craftingPedestal";
    }

    @Override
    public boolean hasCustomName() {
        return true;
    }

    @Override
    public IChatComponent getDisplayName() {
        return new ChatComponentText(getName());
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
        return true;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        itemStack = null;
    }


}
