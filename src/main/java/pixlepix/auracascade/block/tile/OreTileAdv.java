package pixlepix.auracascade.block.tile;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;

import java.util.List;

/**
 * Created by localmacaccount on 5/29/15.
 */
public class OreTileAdv extends OreTile {

    @Override
    public boolean validItemsNearby() {
        boolean result = super.validItemsNearby();
        int range = 3;
        List<EntityItem> nearbyItems = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord - range, yCoord - range, zCoord - range, xCoord + range, yCoord + range, zCoord + range));
        for (EntityItem entityItem : nearbyItems) {
            ItemStack stack = entityItem.getEntityItem();
            if (stack != null && stack.getItem() == Item.getItemFromBlock(Blocks.wool)) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public int oreMultFactor() {
        return 3;
    }

    @Override
    public void onUsePower() {
        if (super.validItemsNearby()) {
            super.onUsePower();
        } else {
            int range = 3;
            List<EntityItem> nearbyItems = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord - range, yCoord - range, zCoord - range, xCoord + range, yCoord + range, zCoord + range));
            for (EntityItem entityItem : nearbyItems) {
                ItemStack stack = entityItem.getEntityItem();
                if (stack != null && stack.getItem() == Item.getItemFromBlock(Blocks.wool)) {
                    if (stack.getItemDamage() != 0) {
                        stack.stackSize--;
                        ItemStack resultStack = new ItemStack(Items.dye, 1, 15 - stack.getItemDamage());
                        spawnInWorld(resultStack, entityItem);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public int getMaxProgress() {
        return 9;
    }

    @Override
    public int getPowerPerProgress() {
        return 1000;
    }
}
