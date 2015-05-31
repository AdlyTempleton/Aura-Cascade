package pixlepix.auracascade.data;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import pixlepix.auracascade.QuestManager;

import java.util.ArrayList;

/**
 * Created by localmacaccount on 5/31/15.
 */
public class QuestData implements IExtendedEntityProperties {


    public final static String EXT_PROP_NAME = "ACQuest";
    public ArrayList<Quest> completedQuests = new ArrayList<Quest>();

    public QuestData(ArrayList<Quest> completedQuests) {
        this.completedQuests = completedQuests;
    }

    public QuestData() {
        this(new ArrayList<Quest>());
    }

    public static final void register(EntityPlayer player) {
        player.registerExtendedProperties(QuestData.EXT_PROP_NAME, new QuestData());
    }

    /**
     * Called when the entity that this class is attached to is saved.
     * Any custom entity data  that needs saving should be saved here.
     *
     * @param compound The compound to save to.
     */
    @Override
    public void saveNBTData(NBTTagCompound compound) {
        NBTTagCompound properties = new NBTTagCompound();
        int[] questIds = new int[completedQuests.size()];
        for (int i = 0; i < completedQuests.size(); i++) {
            questIds[i] = completedQuests.get(i).id;
        }
        properties.setIntArray("questArray", questIds);
        compound.setTag(EXT_PROP_NAME, properties);
    }

    /**
     * Called when the entity that this class is attached to is loaded.
     * In order to hook into this, you will need to subscribe to the EntityConstructing event.
     * Otherwise, you will need to initialize manually.
     *
     * @param compound The compound to load from.
     */
    @Override
    public void loadNBTData(NBTTagCompound compound) {
        NBTTagCompound properties = compound.getCompoundTag(EXT_PROP_NAME);
        int[] questIds = properties.getIntArray("questArray");
        for (int i : questIds) {
            completedQuests.add(QuestManager.quests.get(i));
        }
    }

    /**
     * Used to initialize the extended properties with the entity that this is attached to, as well
     * as the world object.
     * Called automatically if you register with the EntityConstructing event.
     * May be called multiple times if the extended properties is moved over to a new entity.
     * Such as when a player switches dimension {Minecraft re-creates the player entity}
     *
     * @param entity The entity that this extended properties is attached to
     * @param world  The world in which the entity exists
     */
    @Override
    public void init(Entity entity, World world) {

    }
}
