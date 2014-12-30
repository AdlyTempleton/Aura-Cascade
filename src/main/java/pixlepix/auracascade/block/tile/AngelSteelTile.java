package pixlepix.auracascade.block.tile;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import pixlepix.auracascade.item.ItemAngelsteelIngot;
import pixlepix.auracascade.registry.BlockRegistry;

/**
 * Created by pixlepix on 12/21/14.
 */
public class AngelSteelTile extends ConsumerTile {

    @Override
    public int getMaxProgress() {
        return MAX_PROGRESS;
    }

    @Override
    public int getPowerPerProgress() {
        return POWER_PER_PROGRESS;
    }

    public static int MAX_PROGRESS = 1000;
    public static int POWER_PER_PROGRESS = 1000;

    @Override
    public void onUsePower() {
        ItemStack lootStack = new ItemStack(BlockRegistry.getFirstItemFromClass(ItemAngelsteelIngot.class));
        EntityItem entityItem = new EntityItem(worldObj, xCoord + .5, yCoord + 1.5, zCoord + .5, lootStack);
        entityItem.setVelocity(0, 0, 0);
        worldObj.spawnEntityInWorld(entityItem);

    }
}
