package pixlepix.auracascade.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import pixlepix.auracascade.block.tile.TileBookshelfCoordinator;

/**
 * Created by localmacaccount on 1/24/15.
 */
public class ContainerCoordinator extends Container {
    protected TileBookshelfCoordinator tileEntity;


    public ContainerCoordinator(InventoryPlayer inventoryPlayer, TileBookshelfCoordinator te) {
        tileEntity = te;
        for (int i = 0; i < te.getSizeInventory(); i++) {
            int x = i % 7;
            int y = i / 7;
            addSlotToContainer(new SlotCoordinator(GuiCoordinator.invBasic, i, 8 + x * 18, 17 + y * 18));
        }
        bindPlayerInventory(inventoryPlayer);
        scrollTo(0);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return tileEntity.isUseableByPlayer(player);
    }


    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
        int ys = 84;
        int xs = 8;
        for (int x = 0; x < 3; ++x)
            for (int y = 0; y < 9; ++y)
                addSlotToContainer(new Slot(inventoryPlayer, y + x * 9 + 9, xs + y * 18, ys + x * 18));
        for (int x = 0; x < 9; ++x)
            addSlotToContainer(new Slot(inventoryPlayer, x, xs + x * 18, ys + 58));

    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
        ItemStack stack = null;
        Slot slotObject = (Slot) inventorySlots.get(slot);

        //null checks and checks if the item can be stacked (maxStackSize > 1)
        if (slotObject != null && slotObject.getHasStack()) {
            ItemStack stackInSlot = slotObject.getStack();
            stack = stackInSlot.copy();

            //merges the item into player inventory since its in the tileEntity
            if (slot < 9) {
                if (!this.mergeItemStack(stackInSlot, 0, 35, true)) {
                    return null;
                }
            }
            //places it into the tileEntity is possible since its in the player inventory
            else if (!this.mergeItemStack(stackInSlot, 0, 9, false)) {
                return null;
            }

            if (stackInSlot.stackSize == 0) {
                slotObject.putStack(null);
            } else {
                slotObject.onSlotChanged();
            }

            if (stackInSlot.stackSize == stack.stackSize) {
                return null;
            }
            slotObject.onPickupFromSlot(player, stackInSlot);
        }
        return stack;
    }

    public void scrollTo(float p_148329_1_) {
        int i = tileEntity.getSizeInventory() / 7 - 3;
        int j = (int) ((double) (p_148329_1_ * (float) i) + 0.5D);

        if (j < 0) {
            j = 0;
        }

        for (int k = 0; k < 2; ++k) {
            for (int l = 0; l < 7; ++l) {
                int i1 = l + (k + j) * 7;

                if (i1 >= 0 && i1 < tileEntity.getSizeInventory()) {
                    GuiCoordinator.invBasic.setInventorySlotContents(l + k * 9, (ItemStack) tileEntity.getStackInSlot(i1));
                } else {
                    GuiCoordinator.invBasic.setInventorySlotContents(l + k * 9, (ItemStack) null);
                }
            }
        }
    }
}
