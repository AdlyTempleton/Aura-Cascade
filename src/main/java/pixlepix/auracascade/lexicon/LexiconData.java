/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Jan 14, 2014, 9:12:15 PM (GMT)]
 */
package pixlepix.auracascade.lexicon;


import pixlepix.auracascade.block.AuraBlock;
import pixlepix.auracascade.block.ConsumerBlock;
import pixlepix.auracascade.block.entity.*;
import pixlepix.auracascade.data.recipe.PylonRecipe;
import pixlepix.auracascade.item.*;
import pixlepix.auracascade.lexicon.page.PageCraftingRecipe;
import pixlepix.auracascade.lexicon.page.PagePylon;
import pixlepix.auracascade.lexicon.page.PageText;
import pixlepix.auracascade.registry.BlockRegistry;
import pixlepix.auracascade.registry.CraftingBenchRecipe;
import pixlepix.auracascade.registry.ITTinkererRegisterable;
import pixlepix.auracascade.registry.ThaumicTinkererRecipeMulti;

public final class LexiconData {

    public static CraftingBenchRecipe getRecipeFromFairy(Class<? extends EntityFairy> clazz) {
        int num = 0;
        for (int i = 0; i < ItemFairyCharm.fairyClasses.length; i++) {
            Class clazzCompare = ItemFairyCharm.fairyClasses[i];
            if (clazzCompare.equals(clazz)) {
                num = i;
                break;
            }
        }
        return (CraftingBenchRecipe) ((ThaumicTinkererRecipeMulti) BlockRegistry.getFirstRecipeFromItem(ItemFairyCharm.class)).recipes.get(num);

    }

    public static void init() {
        //Add categories
        LexiconCategory categoryBasics = CategoryManager.categoryBasics = new LexiconCategory("Basics");
        CategoryManager.addCategory(categoryBasics);
        LexiconCategory categoryAuraColors = CategoryManager.categoryAuraColors = new LexiconCategory("Aura Colors");
        CategoryManager.addCategory(categoryAuraColors);
        LexiconCategory categoryAuraNodes = CategoryManager.categoryAuraNodes = new LexiconCategory("Aura Nodes");
        CategoryManager.addCategory(categoryAuraNodes);
        LexiconCategory categoryConsumers = CategoryManager.categoryConsumers = new LexiconCategory("Consumers");
        CategoryManager.addCategory(categoryConsumers);
        LexiconCategory categoryFairies = CategoryManager.categoryFairies = new LexiconCategory("Fairies");
        CategoryManager.addCategory(categoryFairies);

        // BASICS ENTRIES
        new BLexiconEntry("basics", categoryBasics).setPriority().setLexiconPages(new PageText("0"), new PageText("1"));
        ItemAuraCrystal itemAuraCrystal = (ItemAuraCrystal) BlockRegistry.getFirstItemFromClass(ItemAuraCrystal.class);
        new BLexiconEntry("auraFlow", categoryBasics).setLexiconPages(new PageText("0"), new PageText("1"),
                new PageCraftingRecipe("2", ((ThaumicTinkererRecipeMulti) BlockRegistry.getRecipe(itemAuraCrystal)).getIRecipies(0, 1)),
                new PageCraftingRecipe("3", (CraftingBenchRecipe) BlockRegistry.getRecipe(AuraBlock.getBlockFromName(""))));
        new BLexiconEntry("interactions", categoryBasics).setLexiconPages(new PageText("0"), new PageText("1"));
        new BLexiconEntry("power", categoryBasics).setLexiconPages(new PageText("0"), new PageText("1"));
        new BLexiconEntry("pumps", categoryBasics).setLexiconPages(new PageText("0"), new PageText("1"), new PageText("2"),
                new PageCraftingRecipe("3", (CraftingBenchRecipe) BlockRegistry.getRecipe(AuraBlock.getBlockFromName("pump"))));
        new BLexiconEntry("comparator", categoryBasics).setLexiconPages(new PageText("0"));

        //Aura Colors
        new BLexiconEntry("red", categoryAuraColors).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ((ThaumicTinkererRecipeMulti) BlockRegistry.getRecipe(itemAuraCrystal)).getIRecipies(3, 4)));
        new BLexiconEntry("orange", categoryAuraColors).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ((ThaumicTinkererRecipeMulti) BlockRegistry.getRecipe(itemAuraCrystal)).getIRecipies(4, 5)));
        new BLexiconEntry("yellow", categoryAuraColors).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ((ThaumicTinkererRecipeMulti) BlockRegistry.getRecipe(itemAuraCrystal)).getIRecipies(5, 6)));
        new BLexiconEntry("blue", categoryAuraColors).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ((ThaumicTinkererRecipeMulti) BlockRegistry.getRecipe(itemAuraCrystal)).getIRecipies(6, 7)));
        new BLexiconEntry("green", categoryAuraColors).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ((ThaumicTinkererRecipeMulti) BlockRegistry.getRecipe(itemAuraCrystal)).getIRecipies(1, 2)));
        new BLexiconEntry("violet", categoryAuraColors).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ((ThaumicTinkererRecipeMulti) BlockRegistry.getRecipe(itemAuraCrystal)).getIRecipies(7, 8)));
        new BLexiconEntry("black", categoryAuraColors).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ((ThaumicTinkererRecipeMulti) BlockRegistry.getRecipe(itemAuraCrystal)).getIRecipies(2, 3)));

        //Aura Nodes
        new BLexiconEntry("manipulator", categoryAuraNodes).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ((CraftingBenchRecipe) BlockRegistry.getRecipe(AuraBlock.getBlockFromName("black"))).iRecipe), new PageCraftingRecipe("2", ((CraftingBenchRecipe) BlockRegistry.getRecipe(AuraBlock.getBlockFromName("orange"))).iRecipe));
        new BLexiconEntry("conserve", categoryAuraNodes).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ((CraftingBenchRecipe) BlockRegistry.getRecipe(AuraBlock.getBlockFromName("conserve"))).iRecipe));
        new BLexiconEntry("capacitor", categoryAuraNodes).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ((CraftingBenchRecipe) BlockRegistry.getRecipe(AuraBlock.getBlockFromName("capacitor"))).iRecipe));
        new BLexiconEntry("pumpProjectile", categoryAuraNodes).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ((CraftingBenchRecipe) BlockRegistry.getRecipe(AuraBlock.getBlockFromName("pumpProjectile"))).iRecipe));
        new BLexiconEntry("pumpFall", categoryAuraNodes).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ((CraftingBenchRecipe) BlockRegistry.getRecipe(AuraBlock.getBlockFromName("pumpFall"))).iRecipe));
        new BLexiconEntry("pumpLight", categoryAuraNodes).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ((CraftingBenchRecipe) BlockRegistry.getRecipe(AuraBlock.getBlockFromName("pumpLight"))).iRecipe));
        new BLexiconEntry("pumpRedstone", categoryAuraNodes).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ((CraftingBenchRecipe) BlockRegistry.getRecipe(AuraBlock.getBlockFromName("pumpRedstone"))).iRecipe));

        //Consumers
        new BLexiconEntry("consumers", categoryConsumers).setPriority().setLexiconPages(new PageText("0"));
        new BLexiconEntry("crafting", categoryConsumers).setLexiconPages(new PageText("0"), new PageText("1"),
                new PageCraftingRecipe("2", (CraftingBenchRecipe) BlockRegistry.getRecipe(AuraBlock.getBlockFromName("craftingCenter"))),
                new PageCraftingRecipe("3", (CraftingBenchRecipe) BlockRegistry.getRecipe(AuraBlock.getBlockFromName("craftingPedestal"))));
        new BLexiconEntry("ore", categoryConsumers).setLexiconPages(new PageText("0"), new PagePylon("1", (PylonRecipe) BlockRegistry.getRecipe(ConsumerBlock.getBlockFromName("ore"))));
        new BLexiconEntry("loot", categoryConsumers).setLexiconPages(new PageText("0"), new PagePylon("1", (PylonRecipe) BlockRegistry.getRecipe(ConsumerBlock.getBlockFromName("loot"))));
        new BLexiconEntry("plant", categoryConsumers).setLexiconPages(new PageText("0"), new PagePylon("1", (PylonRecipe) BlockRegistry.getRecipe(ConsumerBlock.getBlockFromName("plant"))));
        new BLexiconEntry("mob", categoryConsumers).setLexiconPages(new PageText("0"), new PagePylon("1", (PylonRecipe) BlockRegistry.getRecipe(ConsumerBlock.getBlockFromName("mob"))));
        new BLexiconEntry("angel", categoryConsumers).setLexiconPages(new PageText("0"), new PagePylon("1", (PylonRecipe) BlockRegistry.getRecipe(ConsumerBlock.getBlockFromName("angel"))));
        new BLexiconEntry("furnace", categoryConsumers).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", (CraftingBenchRecipe) BlockRegistry.getRecipe(ConsumerBlock.getBlockFromName("furnace"))));
        new BLexiconEntry("angelsteel", categoryConsumers).setLexiconPages(new PageText("0"), new PageText("1"), new PageText("2"),
                new PageCraftingRecipe("4", (CraftingBenchRecipe) BlockRegistry.getRecipe((ITTinkererRegisterable) BlockRegistry.getFirstItemFromClass(ItemAngelsteelAxe.class))),
                new PageCraftingRecipe("5", (CraftingBenchRecipe) BlockRegistry.getRecipe((ITTinkererRegisterable) BlockRegistry.getFirstItemFromClass(ItemAngelsteelPickaxe.class))),
                new PageCraftingRecipe("6", (CraftingBenchRecipe) BlockRegistry.getRecipe((ITTinkererRegisterable) BlockRegistry.getFirstItemFromClass(ItemAngelsteelShovel.class))));

        //Fairies
        new BLexiconEntry("fairies", categoryFairies).setPriority().setLexiconPages(new PageText("0"), new PageText("1"),
                new PagePylon("2", (PylonRecipe) BlockRegistry.getFirstRecipeFromItem(ItemFairyRing.class)),
                new PagePylon("3", (PylonRecipe) ((ThaumicTinkererRecipeMulti) BlockRegistry.getFirstRecipeFromItem(ItemFairyCharm.class)).recipes.get(0)));
        new BLexiconEntry("fairyCombat", categoryFairies).setLexiconPages(new PageText("0"),
                new PageCraftingRecipe("1", getRecipeFromFairy(EntityCombatFairy.class)));
        new BLexiconEntry("fairyDebuff", categoryFairies).setLexiconPages(new PageText("0"),
                new PageCraftingRecipe("1", getRecipeFromFairy(EntityDebuffFairy.class)));
        new BLexiconEntry("fairyBuff", categoryFairies).setLexiconPages(new PageText("0"),
                new PageCraftingRecipe("1", getRecipeFromFairy(EntityBuffFairy.class)));
        new BLexiconEntry("fairySteal", categoryFairies).setLexiconPages(new PageText("0"),
                new PageCraftingRecipe("1", getRecipeFromFairy(EntityStealFairy.class)));
        new BLexiconEntry("fairyPush", categoryFairies).setLexiconPages(new PageText("0"),
                new PageCraftingRecipe("1", getRecipeFromFairy(EntityPushFairy.class)));
        new BLexiconEntry("fairyShoot", categoryFairies).setLexiconPages(new PageText("0"),
                new PageCraftingRecipe("1", getRecipeFromFairy(EntityShooterFairy.class)));
        new BLexiconEntry("fairySavior", categoryFairies).setLexiconPages(new PageText("0"),
                new PageCraftingRecipe("1", getRecipeFromFairy(EntitySaviorFairy.class)));
        new BLexiconEntry("fairyFetch", categoryFairies).setLexiconPages(new PageText("0"),
                new PageCraftingRecipe("1", getRecipeFromFairy(EntityFetchFairy.class)));
        new BLexiconEntry("fairyBait", categoryFairies).setLexiconPages(new PageText("0"),
                new PageCraftingRecipe("1", getRecipeFromFairy(EntityBaitFairy.class)));
        new BLexiconEntry("fairyBreed", categoryFairies).setLexiconPages(new PageText("0"),
                new PageCraftingRecipe("1", getRecipeFromFairy(EntityBreederFairy.class)));
        new BLexiconEntry("fairyScare", categoryFairies).setLexiconPages(new PageText("0"),
                new PageCraftingRecipe("1", getRecipeFromFairy(EntityScareFairy.class)));
        new BLexiconEntry("fairyExtinguisher", categoryFairies).setLexiconPages(new PageText("0"),
                new PageCraftingRecipe("1", getRecipeFromFairy(EntityExtinguisherFairy.class)));
        new BLexiconEntry("fairyDigger", categoryFairies).setLexiconPages(new PageText("0"),
                new PageCraftingRecipe("1", getRecipeFromFairy(EntityDigFairy.class)));
        new BLexiconEntry("fairyFall", categoryFairies).setLexiconPages(new PageText("0"),
                new PageCraftingRecipe("1", getRecipeFromFairy(EntityFallFairy.class)));
        new BLexiconEntry("fairyLight", categoryFairies).setLexiconPages(new PageText("0"),
                new PageCraftingRecipe("1", getRecipeFromFairy(EntityLightFairy.class)));
        new BLexiconEntry("fairyTrain", categoryFairies).setLexiconPages(new PageText("0"),
                new PageCraftingRecipe("1", getRecipeFromFairy(EntityXPFairy.class)));
    }

    public static void postInit() {
    }
}

