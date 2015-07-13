package pixlepix.auracascade.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import pixlepix.auracascade.block.tile.TileBookshelfCoordinator;
import pixlepix.auracascade.block.tile.TileStorageBookshelf;
import pixlepix.auracascade.data.StorageItemStack;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by localmacaccount on 1/24/15.
 */
public class ContainerCoordinator extends Container {
    public float lastScroll;
    protected TileBookshelfCoordinator tileEntity;
    private String lastFilter = "";

    public ContainerCoordinator(InventoryPlayer inventoryPlayer, TileBookshelfCoordinator te) {
        tileEntity = te;

        for (int i = 0; i < 21; i++) {
            int x = i % 7;
            int y = i / 7;
            addSlotToContainer(new SlotCoordinator(i, 8 + x * 20, 17 + y * 18, te, null));
        }
        bindPlayerInventory(inventoryPlayer);
        scrollTo(0);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return tileEntity.neededPower <= tileEntity.lastPower;
    }

    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
        int ys = 84;
        int xs = 8;

        for (int x = 0; x < 3; ++x) {
            for (int y = 0; y < 9; ++y) {
                addSlotToContainer(new Slot(inventoryPlayer, y + x * 9 + 9, xs + y * 18, ys + x * 18));
            }
        }
        for (int x = 0; x < 9; ++x) {
            addSlotToContainer(new Slot(inventoryPlayer, x, xs + x * 18, ys + 58));
        }

    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
        ItemStack stack = null;
        Slot slotObject = (Slot) inventorySlots.get(slot);

        //null checks and checks if the item can be stacked (maxStackSize > 1)
        if (slotObject != null && slotObject.getHasStack()) {
            stack = slotObject.getStack();
            ItemStack stackLeftover;

            //merges the item into player inventory since its in the tileEntity
            if (slot < 21) {
                StorageItemStack storageItemStack = ((SlotCoordinator) inventorySlots.get(slot)).storage;
                storageItemStack.stackSize = storageItemStack.item.getItemStackLimit(storageItemStack.toItemStack());
                stackLeftover = takeFromInventory(storageItemStack);
                if (stackLeftover != null) {
                    this.mergeItemStack(stackLeftover, 21, 21 + 36, true);
                }
                if (stackLeftover != null) {
                    putIntoInventory(new StorageItemStack(stackLeftover));
                }
                update();
                slotObject.onSlotChange(stack, slotObject.getStack());
                return null;

            }
            //places it into the tileEntity is possible since its in the player inventory
            else {
                ItemStack stackToTransfer = slotObject.getStack();
                stackLeftover = putIntoInventory(new StorageItemStack(stackToTransfer));
                if (stackLeftover != null && stackLeftover.stackSize == stackToTransfer.stackSize) {
                    return null;
                }
                slotObject.putStack(stackLeftover);
                slotObject.onSlotChange(stack, stackLeftover);
            }
        }
        update();
        return stack;
    }


    public void update() {
        scrollTo(lastScroll);
    }

    public void scrollTo(float scroll) {
        scrollTo(scroll, lastFilter);

    }

    public void scrollTo(float scroll, String filter) {
        if (!tileEntity.getWorldObj().isRemote) {
            tileEntity.getWorldObj().markBlockForUpdate(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
        }
        lastFilter = filter;

        ArrayList<StorageItemStack> stacks = (ArrayList<StorageItemStack>) tileEntity.getAbstractInventory().clone();
        Iterator iter = stacks.iterator();
        while (iter.hasNext()) {
            StorageItemStack storageItemStack = (StorageItemStack) iter.next();
            String name = StatCollector.translateToFallback(storageItemStack.toItemStack().getUnlocalizedName() + ".name").toUpperCase();

            if (!name.contains(filter)) {
                iter.remove();
            }
        }
        this.lastScroll = scroll;
        int i = stacks.size() / 7 - 2;
        int j = (int) ((double) (scroll * (float) i) + 0.5D);

        if (j < 0) {
            j = 0;
        }


        for (int k = 0; k < 3; ++k) {
            for (int l = 0; l < 7; ++l) {
                int i1 = l + (k + j) * 7;

                if (i1 >= 0 && i1 < stacks.size()) {
                    ((SlotCoordinator) this.getSlot(l + k * 7)).storage = stacks.get(i1);
                } else {

                    ((SlotCoordinator) this.getSlot(l + k * 7)).storage = null;
                }
            }
        }
    }

    public ItemStack takeFromInventory(StorageItemStack lost) {
        int amount = 0;
        outer:
        for (TileStorageBookshelf bookshelf : tileEntity.getBookshelves()) {
            for (int i = 0; i < bookshelf.getSizeInventory(); i++) {
                ItemStack stackInShelf = bookshelf.getStackInSlot(i);
                if (stackInShelf != null && new StorageItemStack(stackInShelf).equalsType(lost)) {
                    int delta = Math.min(lost.stackSize - amount, stackInShelf.stackSize);
                    amount += delta;
                    stackInShelf.stackSize -= delta;
                    if (stackInShelf.stackSize == 0) {
                        bookshelf.setInventorySlotContents(i, null);
                    }
                    bookshelf.markDirty();
                    if (lost.stackSize <= 0) {
                        break outer;

                    }
                }
            }
        }
        lost.stackSize = amount;
        return lost.stackSize != 0 ? lost.toItemStack() : null;
    }

    public ItemStack putIntoInventory(StorageItemStack gained) {

        int amount = gained.stackSize;
        if (amount != 0) {
            outer:
            for (TileStorageBookshelf bookshelf : tileEntity.getBookshelves()) {
                for (int i = 0; i < bookshelf.getSizeInventory(); i++) {
                    ItemStack stackInShelf = bookshelf.getStackInSlot(i);
                    ItemStack testStack = null;
                    if (stackInShelf != null && new StorageItemStack(stackInShelf).equalsType(gained)) {
                        testStack = stackInShelf.copy();
                    }
                    if (stackInShelf == null) {
                        testStack = gained.toItemStack();
                        testStack.stackSize = 0;
                    }
                    if (testStack != null) {
                        ItemStack nextTestStack = testStack.copy();
                        nextTestStack.stackSize++;
                        while (amount > 0 && bookshelf.isItemValidForSlotSensitive(i, nextTestStack) && nextTestStack.stackSize <= testStack.getMaxStackSize()) {
                            testStack = nextTestStack.copy();
                            nextTestStack.stackSize++;
                            amount--;
                        }

                        if (testStack.stackSize > 0) {
                            bookshelf.setInventorySlotContents(i, testStack);
                        }
                        if (gained.stackSize <= 0) {
                            break outer;
                        }
                    }
                    if (bookshelf.getStackInSlot(i) == null) {
                        break;

                    }
                }
            }
        }

        StorageItemStack result = gained.copy();
        result.stackSize = amount;
        return result.toItemStack();

    }

    @Override
    public ItemStack slotClick(int slot, int clickedButton, int mode, EntityPlayer player) {
        ItemStack itemstack = null;

        if (player.inventory.getItemStack() != null && slot == -999 && (clickedButton == 0 || clickedButton == 1) && (mode == 0 || mode == 1)) {
            if (clickedButton == 0) {
                player.dropPlayerItemWithRandomChoice(player.inventory.getItemStack(), true);
                player.inventory.setItemStack(null);
            }

            if (clickedButton == 1) {
                player.dropPlayerItemWithRandomChoice(player.inventory.getItemStack().splitStack(1), true);

                if (player.inventory.getItemStack().stackSize == 0) {
                    player.inventory.setItemStack(null);
                }
            }
        } else if (slot >= 0 && inventorySlots.get(slot) instanceof SlotCoordinator && mode != 1) {
            if ((clickedButton == 0 || clickedButton == 1) && (mode == 0 || mode == 5)) {
                StorageItemStack target = ((SlotCoordinator) inventorySlots.get(slot)).storage;
                if (target != null && player.inventory.getItemStack() == null) {
                    target = target.copy();
                    int maxStackSize = target.item.getItemStackLimit(target.toItemStack());
                    target.stackSize = clickedButton == 0 ? maxStackSize : maxStackSize / 2;
                    ItemStack result = takeFromInventory(target);
                    player.inventory.setItemStack(result);
                    ((SlotCoordinator) inventorySlots.get(slot)).onPickupFromSlot(player, result);
                    update();
                } else if (player.inventory.getItemStack() != null) {
                    ItemStack placedStack = player.inventory.getItemStack().copy();
                    int reservedItems = 0;
                    if (clickedButton == 1) {
                        reservedItems = placedStack.stackSize - 1;
                        placedStack.stackSize = 1;

                    }
                    ItemStack leftovers = putIntoInventory(new StorageItemStack(placedStack));
                    if (leftovers != null) {
                        leftovers.stackSize += reservedItems;
                    } else if (reservedItems > 0) {
                        leftovers = placedStack.copy();
                        leftovers.stackSize = reservedItems;
                    }
                    player.inventory.setItemStack(leftovers);
                    update();
                }
            }

        } else if (mode == 1) {
            if (slot < 0) {
                return null;
            }

            Slot slot2 = (Slot) this.inventorySlots.get(slot);

            if (slot2 != null && slot2.canTakeStack(player)) {
                ItemStack itemstack3 = this.transferStackInSlot(player, slot);

                if (itemstack3 != null) {
                    Item item = itemstack3.getItem();
                    itemstack = itemstack3.copy();

                    if (slot2.getStack() != null && slot2.getStack().getItem() == item) {
                        this.retrySlotClick(slot, clickedButton, true, player);
                    }
                }
            }
        } else {

            if (slot < 0) {
                return null;
            }

            Slot slot2 = (Slot) this.inventorySlots.get(slot);

            if (slot2 != null) {
                ItemStack stackInSlot = slot2.getStack();
                ItemStack stackInPlayer = player.inventory.getItemStack();
                ItemStack stackPickedUp;
                int l1;

                if (stackInSlot != null) {
                    itemstack = stackInSlot.copy();
                }

                if (stackInSlot == null) {
                    if (stackInPlayer != null && slot2.isItemValid(stackInPlayer)) {
                        l1 = clickedButton == 0 ? stackInPlayer.stackSize : 1;

                        if (l1 > slot2.getSlotStackLimit()) {
                            l1 = slot2.getSlotStackLimit();
                        }

                        if (stackInPlayer.stackSize >= l1) {
                            slot2.putStack(stackInPlayer.splitStack(l1));
                        }

                        if (stackInPlayer.stackSize == 0) {
                            player.inventory.setItemStack(null);
                        }
                    }
                } else if (slot2.canTakeStack(player)) {
                    if (stackInPlayer == null) {
                        l1 = clickedButton == 0 ? stackInSlot.stackSize : (stackInSlot.stackSize + 1) / 2;
                        stackPickedUp = slot2.decrStackSize(l1);
                        player.inventory.setItemStack(stackPickedUp);

                        if (stackInSlot.stackSize == 0) {
                            slot2.putStack(null);
                        }

                        slot2.onPickupFromSlot(player, player.inventory.getItemStack());
                    } else if (slot2.isItemValid(stackInPlayer)) {
                        if (stackInSlot.getItem() == stackInPlayer.getItem() && stackInSlot.getItemDamage() == stackInPlayer.getItemDamage() && ItemStack.areItemStackTagsEqual(stackInPlayer, stackInSlot)) {
                            l1 = clickedButton == 0 ? stackInPlayer.stackSize : 1;

                            if (l1 > slot2.getSlotStackLimit() - stackInSlot.stackSize) {
                                l1 = slot2.getSlotStackLimit() - stackInSlot.stackSize;
                            }

                            if (l1 > stackInPlayer.getMaxStackSize() - stackInSlot.stackSize) {
                                l1 = stackInPlayer.getMaxStackSize() - stackInSlot.stackSize;
                            }

                            stackInPlayer.splitStack(l1);

                            if (stackInPlayer.stackSize == 0) {
                                player.inventory.setItemStack(null);
                            }

                            stackInSlot.stackSize += l1;
                        } else if (stackInPlayer.stackSize <= slot2.getSlotStackLimit()) {
                            slot2.putStack(stackInPlayer);
                            player.inventory.setItemStack(stackInSlot);
                        }
                    } else if (stackInSlot.getItem() == stackInPlayer.getItem() && stackInPlayer.getMaxStackSize() > 1 && (!stackInSlot.getHasSubtypes() || stackInSlot.getItemDamage() == stackInPlayer.getItemDamage()) && ItemStack.areItemStackTagsEqual(stackInSlot, stackInPlayer)) {
                        l1 = stackInSlot.stackSize;

                        if (l1 > 0 && l1 + stackInPlayer.stackSize <= stackInPlayer.getMaxStackSize()) {
                            stackInPlayer.stackSize += l1;
                            stackInSlot = slot2.decrStackSize(l1);

                            if (stackInSlot.stackSize == 0) {
                                slot2.putStack(null);
                            }

                            slot2.onPickupFromSlot(player, player.inventory.getItemStack());
                        }
                    }
                }

                slot2.onSlotChanged();
            }
        }

        return itemstack;
    }


}
