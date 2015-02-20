/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Jan 14, 2014, 6:15:28 PM (GMT)]
 */
package pixlepix.auracascade.lexicon;

import java.util.ArrayList;
import java.util.List;

//Derived from the BopaniaAPI class, but the name didn't make sense in context
public final class CategoryManager {

    // All of these categories are initialized during botania's PreInit stage.
    public static LexiconCategory categoryBasics;
    public static LexiconCategory categoryAuraColors;
    public static LexiconCategory categoryAuraNodes;
    public static LexiconCategory categoryEnchants;
    public static LexiconCategory categoryConsumers;
    public static LexiconCategory categoryFairies;
    public static LexiconCategory categoryAccessories;
    public static LexiconCategory categoryWalkthrough;
    public static LexiconCategory categoryMisc;
    private static List<LexiconCategory> categories = new ArrayList<LexiconCategory>();
    private static List<LexiconEntry> allEntries = new ArrayList<LexiconEntry>();

    /**
     * Adds a category to the list of registered categories to appear in the Lexicon.
     */
    public static void addCategory(LexiconCategory category) {
        categories.add(category);
    }

    /**
     * Gets all registered categories.
     */
    public static List<LexiconCategory> getAllCategories() {
        return categories;
    }

    /**
     * Gets all registered entries.
     */
    public static List<LexiconEntry> getAllEntries() {
        return allEntries;
    }

    /**
     * Registers a Lexicon Entry and adds it to the category passed in.
     */
    public static void addEntry(LexiconEntry entry, LexiconCategory category) {
        allEntries.add(entry);
        category.entries.add(entry);
    }

}
