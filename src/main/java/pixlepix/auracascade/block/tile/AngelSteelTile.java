package pixlepix.auracascade.block.tile;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ChestGenHooks;
import pixlepix.auracascade.item.ItemAngelsteelIngot;
import pixlepix.auracascade.registry.BlockRegistry;

import java.util.Random;

/**
 * Created by pixlepix on 12/21/14.
 */
public class AngelSteelTile extends ConsumerTile {
    public int progress;
    public static int MAX_PROGRESS = 1000;
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
    public void updateEntity() {
        super.updateEntity();
        if(!worldObj.isRemote){
            int nextBoostCost = POWER_PER_PROGRESS;
            while (true){
                if(progress > MAX_PROGRESS){
                    progress = 0;
                    ItemStack lootStack = new ItemStack(BlockRegistry.getFirstItemFromClass(ItemAngelsteelIngot.class));
                    EntityItem entityItem = new EntityItem(worldObj, xCoord + .5, yCoord + 1.5, zCoord + .5, lootStack);
                    entityItem.setVelocity(0, 0, 0);
                    worldObj.spawnEntityInWorld(entityItem);
                }
                if(storedPower < nextBoostCost){
                    break;
                }
                progress += 1;
                storedPower -= nextBoostCost;
                nextBoostCost *= 1.05;
            }
        }
    }
}
