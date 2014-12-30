package pixlepix.auracascade.block.tile;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ChestGenHooks;

import java.util.Random;

/**
 * Created by pixlepix on 12/21/14.
 */
public class LootTile extends ConsumerTile {
    @Override
    public int getMaxProgress() {
        return MAX_PROGRESS;
    }

    @Override
    public int getPowerPerProgress() {
        return POWER_PER_PROGRESS;
    }

    public static int MAX_PROGRESS = 100;
    public static int POWER_PER_PROGRESS = 1000;

    @Override
    public void readCustomNBT(NBTTagCompound nbt) {
        super.readCustomNBT(nbt);
        progress = nbt.getInteger("progress");
    }

    @Override
    public void writeCustomNBT(NBTTagCompound nbt) {
        super.writeCustomNBT(nbt);
        progress = nbt.getInteger("progress");
    }

    @Override
    public void onUsePower() {
        ItemStack lootStack = ChestGenHooks.getOneItem(ChestGenHooks.DUNGEON_CHEST, new Random());
        EntityItem entityItem = new EntityItem(worldObj, xCoord + .5, yCoord + 1.5, zCoord + .5, lootStack);
        entityItem.setVelocity(0, 0, 0);
        worldObj.spawnEntityInWorld(entityItem);

    }
}
