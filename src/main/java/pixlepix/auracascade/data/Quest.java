package pixlepix.auracascade.data;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by localmacaccount on 5/31/15.
 */
public class Quest {
	//TODO QUEST
    public static int nextId;
    public final ItemStack target;
    public final ItemStack result;
    public final int id;
    public String string;

    public Quest(String string, ItemStack target, ItemStack result) {
        this.target = target;
        this.result = result;
        this.string = string;
        this.id = nextId;
        nextId++;

    }
    public boolean hasCompleted(EntityPlayer player) {
      //  QuestData questData = (QuestData) player.getExtendedProperties(QuestData.EXT_PROP_NAME);
       // return questData.completedQuests.contains(this);
        return false;
    }

    public void complete(EntityPlayer player) {
        //QuestData questData = (QuestData) player.getExtendedProperties(QuestData.EXT_PROP_NAME);
       // questData.completedQuests.add(this);

       // AuraCascade.analytics.eventDesign("questComplete", id);
    }
}
