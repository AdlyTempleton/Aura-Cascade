package pixlepix.auracascade.block.tile;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.compat.IMCManager;
import pixlepix.auracascade.main.AuraUtil;

/**
 * Created by pixlepix on 12/21/14.
 */
public class LootTile extends ConsumerTile {
    public static int MAX_PROGRESS = 100;
    public static int POWER_PER_PROGRESS = 5000;

    @Override
    public int getMaxProgress() {
        return MAX_PROGRESS;
    }

    @Override
    public int getPowerPerProgress() {
        return POWER_PER_PROGRESS;
    }

    @Override
    public void readCustomNBT(NBTTagCompound nbt) {
        super.readCustomNBT(nbt);
        progress = nbt.getInteger("progress");
    }

    @Override
    public boolean validItemsNearby() {
        return true;
    }

    @Override
    public void writeCustomNBT(NBTTagCompound nbt) {
        super.writeCustomNBT(nbt);
        progress = nbt.getInteger("progress");
    }

    @Override
    public void onUsePower() {
        AuraCascade.analytics.eventDesign("cascaderLoot", AuraUtil.formatLocation(this));
        ItemStack lootStack;
        Random rand = this.getWorld().rand;
        do {
        	//TODO TEST THAT THIS WORKS. USES BOTANIA LOONIUM CODE.
        	lootStack = this.getWorld().getLootTableManager().getLootTableFromLocation(new ResourceLocation("minecraft", "chests/simple_dungeon")).generateLootForPools(rand, new LootContext.Builder(((WorldServer) this.getWorld())).build()).get(0);
            //OLD CODE: lootStack = ChestGenHooks.getOneItem(ChestGenHooks.DUNGEON_CHEST, new Random());
        } while (IMCManager.isStackBlacklistedFromLoot(lootStack));
        EntityItem entityItem = new EntityItem(worldObj, pos.getX() + .5, pos.getY() + 1.5, pos.getZ() + .5, lootStack);
        entityItem.motionX = 0;
        entityItem.motionY = 0;
        entityItem.motionZ = 0;
        worldObj.spawnEntityInWorld(entityItem);

    }
}
