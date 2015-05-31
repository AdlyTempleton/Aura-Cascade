package pixlepix.auracascade.lexicon;

import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.data.Quest;

/**
 * Created by localmacaccount on 5/31/15.
 * Used for the quest entries, which should be sorted by id*
 */
public class LexiconEntryQuest extends BLexiconEntry {


    private Quest quest;

    /**
     * @param unlocalizedName The unlocalized name of this entry. This will be localized by the client display.
     * @param category
     */
    public LexiconEntryQuest(String unlocalizedName, LexiconCategory category, Quest quest) {
        super(unlocalizedName, category);
        this.quest = quest;
    }

    @Override
    public String getNameForSorting() {
        return getUnlocalizedName();
    }

    @Override
    public String getSuffix() {
        return quest.hasCompleted(AuraCascade.proxy.getPlayer()) ? " §2COMPLETE" : " §4INCOMPLETE";
    }
}
