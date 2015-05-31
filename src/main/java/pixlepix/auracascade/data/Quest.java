package pixlepix.auracascade.data;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by localmacaccount on 5/31/15.
 */
public class Quest {
    public static int nextId;
    public final ItemStack target;
    public final ItemStack result;
    public final int id;

    public Quest(ItemStack target, ItemStack result) {
        this.target = target;
        this.result = result;
        this.id = nextId;
        nextId++;
    }

    public boolean hasCompleted(EntityPlayer player) {
        QuestData questData = (QuestData) player.getExtendedProperties(QuestData.EXT_PROP_NAME);
        return questData.completedQuests.contains(this);
    }

    public void complete(EntityPlayer player) {
        QuestData questData = (QuestData) player.getExtendedProperties(QuestData.EXT_PROP_NAME);
        questData.completedQuests.add(this);
    }
}
