package pixlepix.auracascade.main;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

/**
 * Created by localmacaccount on 1/16/15.
 */
public class BookPlayerTracker implements IExtendedEntityProperties {

    public boolean hasReceived;

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        compound.setBoolean("HAS_REVEICED_AURA_BOOK", hasReceived);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        hasReceived = compound.getBoolean("HAS_REVEICED_AURA_BOOK");
    }

    @Override
    public void init(Entity entity, World world) {

    }
}
