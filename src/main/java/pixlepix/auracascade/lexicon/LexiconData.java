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


import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import pixlepix.auracascade.block.AuraBlock;
import pixlepix.auracascade.block.BlockBookshelfCoordinator;
import pixlepix.auracascade.block.ConsumerBlock;
import pixlepix.auracascade.block.entity.*;
import pixlepix.auracascade.data.recipe.PylonRecipe;
import pixlepix.auracascade.data.recipe.PylonRecipeRegistry;
import pixlepix.auracascade.item.*;
import pixlepix.auracascade.item.books.*;
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
        LexiconCategory categoryBasics = CategoryManager.categoryBasics = new LexiconCategory("Basics").setIcon(new ItemStack(AuraBlock.getBlockFromName("")));
        CategoryManager.addCategory(categoryBasics);
        LexiconCategory categoryAuraColors = CategoryManager.categoryAuraColors = new LexiconCategory("Aura Colors").setIcon(new ItemStack(BlockRegistry.getFirstItemFromClass(ItemAuraCrystal.class), 1, 2));
        ;
        CategoryManager.addCategory(categoryAuraColors);
        LexiconCategory categoryAuraNodes = CategoryManager.categoryAuraNodes = new LexiconCategory("Aura Nodes").setIcon(new ItemStack(AuraBlock.getBlockFromName("capacitor")));
        CategoryManager.addCategory(categoryAuraNodes);
        LexiconCategory categoryConsumers = CategoryManager.categoryConsumers = new LexiconCategory("Consumers").setIcon(new ItemStack(ConsumerBlock.getBlockFromName("mob")));
        CategoryManager.addCategory(categoryConsumers);
        LexiconCategory categoryFairies = CategoryManager.categoryFairies = new LexiconCategory("Fairies").setIcon(new ItemStack(BlockRegistry.getFirstItemFromClass(ItemFairyCharm.class), 1, 100));
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
        new BLexiconEntry("protection", categoryConsumers).setLexiconPages(new PageText("0"), new PageText("1"),
                new PagePylon("2", ItemRedAmulet.class),
                new PagePylon("3", ItemOrangeAmulet.class),
                new PagePylon("4", ItemYellowAmulet.class),
                new PagePylon("5", ItemGreenAmulet.class),
                new PagePylon("6", ItemBlueAmulet.class),
                new PagePylon("7", ItemPurpleAmulet.class));
        new BLexiconEntry("books", categoryConsumers).setPriority().setLexiconPages(new PageText("0"), new PageText("1"), new PageText("2"), new PagePylon("3", (PylonRecipe) BlockRegistry.getFirstRecipeFromBlock(BlockBookshelfCoordinator.class)),
                new PagePylon("4", ((PylonRecipe) BlockRegistry.getFirstRecipeFromItem(BasicStorageBook.class))),
                new PageText("5"),
                new PageCraftingRecipe("6", ((CraftingBenchRecipe) BlockRegistry.getFirstRecipeFromItem(DenseStorageBook.class)).iRecipe),
                new PageCraftingRecipe("7", ((CraftingBenchRecipe) BlockRegistry.getFirstRecipeFromItem(VeryDenseStorageBook.class)).iRecipe),
                new PageCraftingRecipe("8", ((CraftingBenchRecipe) BlockRegistry.getFirstRecipeFromItem(SuperDenseStorageBook.class)).iRecipe),
                new PageCraftingRecipe("9", ((CraftingBenchRecipe) BlockRegistry.getFirstRecipeFromItem(ExtremelyDenseStorageBook.class)).iRecipe),
                new PageText("10"),
                new PageCraftingRecipe("11", ((CraftingBenchRecipe) BlockRegistry.getFirstRecipeFromItem(LightStorageBook.class)).iRecipe),
                new PageCraftingRecipe("12", ((CraftingBenchRecipe) BlockRegistry.getFirstRecipeFromItem(VeryLightStorageBook.class)).iRecipe),
                new PageCraftingRecipe("13", ((CraftingBenchRecipe) BlockRegistry.getFirstRecipeFromItem(SuperLightStorageBook.class)).iRecipe),
                new PageCraftingRecipe("14", ((CraftingBenchRecipe) BlockRegistry.getFirstRecipeFromItem(ExtremelyLightStorageBook.class)).iRecipe),
                new PageText("15"), new PageText("16"),
                new PageCraftingRecipe("17", ((CraftingBenchRecipe) BlockRegistry.getFirstRecipeFromItem(MineralStorageBook.class)).iRecipe),
                new PageCraftingRecipe("18", ((CraftingBenchRecipe) BlockRegistry.getFirstRecipeFromItem(MobStorageBook.class)).iRecipe),
                new PageCraftingRecipe("19", ((CraftingBenchRecipe) BlockRegistry.getFirstRecipeFromItem(FarmingStorageBook.class)).iRecipe),
                new PageCraftingRecipe("20", ((CraftingBenchRecipe) BlockRegistry.getFirstRecipeFromItem(ModStorageBook.class)).iRecipe));
        new BLexiconEntry("crafting", categoryConsumers).setPriority().setLexiconPages(new PageText("0"), new PageText("1"),
                new PageCraftingRecipe("2", (CraftingBenchRecipe) BlockRegistry.getRecipe(AuraBlock.getBlockFromName("craftingCenter"))),
                new PageCraftingRecipe("3", (CraftingBenchRecipe) BlockRegistry.getRecipe(AuraBlock.getBlockFromName("craftingPedestal"))),
                new PagePylon("4", PylonRecipeRegistry.getRecipe(new ItemStack(Items.leather))),
                new PagePylon("5", PylonRecipeRegistry.getRecipe(new ItemStack(Items.blaze_powder, 20))),
                new PagePylon("6", PylonRecipeRegistry.getRecipe(new ItemStack(Items.saddle, 1))),
                new PagePylon("7", PylonRecipeRegistry.getRecipe(new ItemStack(Items.ender_eye, 2))),
                new PagePylon("8", PylonRecipeRegistry.getRecipe(new ItemStack(Items.arrow, 8))),
                new PagePylon("9", PylonRecipeRegistry.getRecipe(new ItemStack(Blocks.rail, 32))),
                new PagePylon("10", PylonRecipeRegistry.getRecipe(new ItemStack(Blocks.lapis_block, 1))),
                new PagePylon("11", PylonRecipeRegistry.getRecipe(new ItemStack(Items.repeater))),
                new PagePylon("12", PylonRecipeRegistry.getRecipe(new ItemStack(Items.comparator))),
                new PagePylon("13", PylonRecipeRegistry.getRecipe(new ItemStack(Blocks.soul_sand))),
                new PagePylon("14", PylonRecipeRegistry.getRecipe(new ItemStack(Blocks.gold_block))),
                new PagePylon("15", PylonRecipeRegistry.getRecipe(new ItemStack(Blocks.diamond_block))));
        new BLexiconEntry("ore", categoryConsumers).setLexiconPages(new PageText("0"), new PagePylon("1", (PylonRecipe) BlockRegistry.getRecipe(ConsumerBlock.getBlockFromName("ore"))));
        new BLexiconEntry("loot", categoryConsumers).setLexiconPages(new PageText("0"), new PagePylon("1", (PylonRecipe) BlockRegistry.getRecipe(ConsumerBlock.getBlockFromName("loot"))));
        new BLexiconEntry("plant", categoryConsumers).setLexiconPages(new PageText("0"), new PagePylon("1", (PylonRecipe) BlockRegistry.getRecipe(ConsumerBlock.getBlockFromName("plant"))));
        new BLexiconEntry("mob", categoryConsumers).setLexiconPages(new PageText("0"), new PagePylon("1", (PylonRecipe) BlockRegistry.getRecipe(ConsumerBlock.getBlockFromName("mob"))));
        new BLexiconEntry("angel", categoryConsumers).setLexiconPages(new PageText("0"), new PagePylon("1", (PylonRecipe) BlockRegistry.getRecipe(ConsumerBlock.getBlockFromName("angel"))));
        new BLexiconEntry("furnace", categoryConsumers).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", (CraftingBenchRecipe) BlockRegistry.getRecipe(ConsumerBlock.getBlockFromName("furnace"))));
        ThaumicTinkererRecipeMulti angelSwordRecipe = (ThaumicTinkererRecipeMulti) BlockRegistry.getFirstRecipeFromItem(ItemAngelsteelSword.class);
        new BLexiconEntry("angelsteel", categoryConsumers).setLexiconPages(new PageText("0"), new PageText("1"), new PageText("2"),
                new PageCraftingRecipe("4", (CraftingBenchRecipe) BlockRegistry.getRecipe((ITTinkererRegisterable) BlockRegistry.getFirstItemFromClass(ItemAngelsteelAxe.class))),
                new PageCraftingRecipe("5", (CraftingBenchRecipe) BlockRegistry.getRecipe((ITTinkererRegisterable) BlockRegistry.getFirstItemFromClass(ItemAngelsteelPickaxe.class))),
                new PageCraftingRecipe("6", (CraftingBenchRecipe) BlockRegistry.getRecipe((ITTinkererRegisterable) BlockRegistry.getFirstItemFromClass(ItemAngelsteelShovel.class))));
        new BLexiconEntry("angelsteelSword", categoryConsumers).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", angelSwordRecipe.getIRecipies(0, 6))
                , new PageText("2"), new PageText("3"), new PageText("4"), new PageText("5"), new PageText("6"), new PageText("7"));
        

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

