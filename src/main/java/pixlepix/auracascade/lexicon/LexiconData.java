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


import cpw.mods.fml.common.ModAPIManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import pixlepix.auracascade.QuestManager;
import pixlepix.auracascade.block.*;
import pixlepix.auracascade.block.entity.*;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.data.Quest;
import pixlepix.auracascade.data.recipe.PylonRecipe;
import pixlepix.auracascade.data.recipe.PylonRecipeRegistry;
import pixlepix.auracascade.item.*;
import pixlepix.auracascade.item.books.*;
import pixlepix.auracascade.lexicon.page.*;
import pixlepix.auracascade.main.Config;
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
        LexiconCategory categoryQuest = null;
        if (Config.questline) {
            categoryQuest = CategoryManager.categoryQuest = new LexiconCategory("Quests").setIcon(new ItemStack(Items.diamond_sword));
            CategoryManager.addCategory(categoryQuest);
        }

        LexiconCategory categoryWalkthrough = CategoryManager.categoryWalkthrough = new LexiconCategory("Walkthrough").setIcon(new ItemStack(Items.wooden_pickaxe));
        CategoryManager.addCategory(categoryWalkthrough);
        
        LexiconCategory categoryBasics = CategoryManager.categoryBasics = new LexiconCategory("Basics").setIcon(new ItemStack(AuraBlock.getBlockFromName("")));
        CategoryManager.addCategory(categoryBasics);
        LexiconCategory categoryAuraColors = CategoryManager.categoryAuraColors = new LexiconCategory("Special Aura Colors").setIcon(new ItemStack(BlockRegistry.getFirstItemFromClass(ItemAuraCrystal.class), 1, 2));
        CategoryManager.addCategory(categoryAuraColors);
        LexiconCategory categoryAuraNodes = CategoryManager.categoryAuraNodes = new LexiconCategory("Special Aura Nodes").setIcon(new ItemStack(AuraBlock.getBlockFromName("capacitor")));
        CategoryManager.addCategory(categoryAuraNodes);
        LexiconCategory categoryConsumers = CategoryManager.categoryConsumers = new LexiconCategory("Power Consumers").setIcon(new ItemStack(ConsumerBlock.getBlockFromName("mob")));
        CategoryManager.addCategory(categoryConsumers);
        LexiconCategory categoryFairies = CategoryManager.categoryFairies = new LexiconCategory("Fairies").setIcon(new ItemStack(BlockRegistry.getFirstItemFromClass(ItemFairyCharm.class), 1, 100));
        CategoryManager.addCategory(categoryFairies);
        LexiconCategory catagoryAccessories = CategoryManager.categoryAccessories = new LexiconCategory("Accessories").setIcon(new ItemStack(BlockRegistry.getFirstItemFromClass(ItemGreenAmulet.class)));
        CategoryManager.addCategory(catagoryAccessories);


        LexiconCategory categoryEnchants = CategoryManager.categoryEnchants = new LexiconCategory("Enchantments").setIcon(new ItemStack(ConsumerBlock.getBlockFromName("enchant")));
        CategoryManager.addCategory(categoryEnchants);

        //Procedurally generate quest entries
        if (Config.questline) {
            for (Quest quest : QuestManager.quests) {
                int id = quest.id;
                new LexiconEntryQuest((id > 9 ? "" : "0") + id + "quest", categoryQuest, quest).setLexiconPages(new PageText("Desc"), new PageQuest(quest));
            }
        }

        ItemAuraCrystal itemAuraCrystal = (ItemAuraCrystal) BlockRegistry.getFirstItemFromClass(ItemAuraCrystal.class);


        //All tutorial entries in order
        new BLexiconEntry("tutorial", categoryWalkthrough).setPriority().setLexiconPages(new PageTutorial("0"));
        new BLexiconEntry("basics", categoryBasics).setPriority().setLexiconPages(new PageText("0"), new PageText("1")).tutorial();
        new BLexiconEntry("auraFlow", categoryBasics).setLexiconPages(new PageText("0"), new PageText("1"),
                new PageCraftingRecipe("2", ((ThaumicTinkererRecipeMulti) BlockRegistry.getRecipe(itemAuraCrystal)).getIRecipies(0, 1)),
                new PageCraftingRecipe("3", (CraftingBenchRecipe) BlockRegistry.getRecipe(AuraBlock.getBlockFromName("")))).tutorial();
        new BLexiconEntry("power", categoryBasics).setLexiconPages(new PageText("0"), new PageText("1")).setPriority().tutorial();
        new BLexiconEntry("pumps", categoryBasics).setLexiconPages(new PageText("0"), new PageText("1"), new PageText("2"),
                new PageCraftingRecipe("3", (CraftingBenchRecipe) BlockRegistry.getRecipe(AuraBlock.getBlockFromName("pump")))).tutorial();


        // BASICS ENTRIES
        new BLexiconEntry("interactions", categoryBasics).setLexiconPages(new PageText("0"), new PageText("1"));
        new BLexiconEntry("comparator", categoryBasics).setLexiconPages(new PageText("0"));
        new BLexiconEntry("monitor", categoryBasics).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ((CraftingBenchRecipe) BlockRegistry.getFirstRecipeFromBlock(BlockMonitor.class)).iRecipe));

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
        new BLexiconEntry("alternating", categoryAuraNodes).setLexiconPages(new PageText("0"),
                new PageCraftingRecipe("2", ((CraftingBenchRecipe) BlockRegistry.getRecipe(AuraBlock.getBlockFromName("pumpAlt"))).iRecipe),
                new PageCraftingRecipe("3", ((CraftingBenchRecipe) BlockRegistry.getRecipe(AuraBlock.getBlockFromName("pumpProjectileAlt"))).iRecipe),
                new PageCraftingRecipe("4", ((CraftingBenchRecipe) BlockRegistry.getRecipe(AuraBlock.getBlockFromName("pumpFallAlt"))).iRecipe),
                new PageCraftingRecipe("5", ((CraftingBenchRecipe) BlockRegistry.getRecipe(AuraBlock.getBlockFromName("pumpLightAlt"))).iRecipe),
                new PageCraftingRecipe("6", ((CraftingBenchRecipe) BlockRegistry.getRecipe(AuraBlock.getBlockFromName("pumpRedstoneAlt"))).iRecipe));
        //Consumers
        new BLexiconEntry("explosionRing", catagoryAccessories).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ItemExplosionRing.class));

        new BLexiconEntry("swordThief", catagoryAccessories).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ItemThiefSword.class));
        new BLexiconEntry("swordCombo", catagoryAccessories).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ItemComboSword.class));
        new BLexiconEntry("swordTransmute", catagoryAccessories).setLexiconPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", ItemTransmutingSword.class));
        new BLexiconEntry("redHole", catagoryAccessories).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ItemRedHole.class));
        new BLexiconEntry("blackHole", catagoryAccessories).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ItemBlackHole.class));
        new BLexiconEntry("prismaticWand", catagoryAccessories).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ItemPrismaticWand.class));
        new BLexiconEntry("mirror", catagoryAccessories).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ItemMirror.class));
        new BLexiconEntry("amuletAngel", catagoryAccessories).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ItemAngelJump.class));
        new BLexiconEntry("beltAngel", catagoryAccessories).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ItemAngelStep.class));
        new BLexiconEntry("magicRoad", catagoryAccessories).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", BlockMagicRoad.class));
        new BLexiconEntry("trampoline", catagoryAccessories).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", BlockTrampoline.class));
        new BLexiconEntry("amuletFood", catagoryAccessories).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ItemFoodAmulet.class));
        new BLexiconEntry("consumers", categoryConsumers).setPriority().setLexiconPages(new PageText("0"));
        new BLexiconEntry("protection", catagoryAccessories).setLexiconPages(new PageText("0"), new PageText("1"),
                new PageCraftingRecipe("2", ItemRedAmulet.class),
                new PageCraftingRecipe("3", ItemOrangeAmulet.class),
                new PageCraftingRecipe("4", ItemYellowAmulet.class),
                new PageCraftingRecipe("5", ItemGreenAmulet.class),
                new PageCraftingRecipe("6", ItemBlueAmulet.class),
                new PageCraftingRecipe("7", ItemPurpleAmulet.class));
        new BLexiconEntry("books", categoryConsumers).setPriority().setLexiconPages(new PageText("0"), new PageText("1"), new PageText("2"), new PageCraftingRecipe("3", BlockRegistry.getFirstRecipeFromBlock(BlockBookshelfCoordinator.class)),
                new PageCraftingRecipe("4", (BlockRegistry.getFirstRecipeFromItem(BasicStorageBook.class))),
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
        new BLexiconEntry("materials", categoryBasics).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", BlockRegistry.getRecipe(ConsumerBlock.getBlockFromName("ore"))), new PageText("2"), new PageText("3"), new PagePylon("4", PylonRecipeRegistry.getRecipe(ItemMaterial.getGem(EnumAura.WHITE_AURA))), new PageText("5")
                , new PageText("6"), new PageText("7"), new PageCraftingRecipe("8", BlockRegistry.getRecipe(ConsumerBlock.getBlockFromName("oreAdv"))));

        if (ModAPIManager.INSTANCE.hasAPI("CoFHAPI|energy")) {
            new BLexiconEntry("flux", categoryConsumers).setPriority().setLexiconPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", ((CraftingBenchRecipe) BlockRegistry.getRecipe(AuraBlock.getBlockFromName("flux"))).iRecipe));
        }
        new BLexiconEntry("nether", categoryConsumers).setLexiconPages(new PageText("0"), new PagePylon("1", (PylonRecipe) BlockRegistry.getRecipe(ConsumerBlock.getBlockFromName("nether"))));
        new BLexiconEntry("loot", categoryConsumers).setLexiconPages(new PageText("0"), new PagePylon("1", (PylonRecipe) BlockRegistry.getRecipe(ConsumerBlock.getBlockFromName("loot"))));
        new BLexiconEntry("plant", categoryConsumers).setLexiconPages(new PageText("0"), new PagePylon("1", (PylonRecipe) BlockRegistry.getRecipe(ConsumerBlock.getBlockFromName("plant"))));
        new BLexiconEntry("mob", categoryConsumers).setLexiconPages(new PageText("0"), new PagePylon("1", (PylonRecipe) BlockRegistry.getRecipe(ConsumerBlock.getBlockFromName("mob"))));
        new BLexiconEntry("dye", categoryConsumers).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", (CraftingBenchRecipe) BlockRegistry.getRecipe(ConsumerBlock.getBlockFromName("dye"))));
        new BLexiconEntry("brewer", categoryConsumers).setLexiconPages(new PageText("0"), new PagePylon("1", (PylonRecipe) BlockRegistry.getRecipe(ConsumerBlock.getBlockFromName("potion"))));
        new BLexiconEntry("angel", categoryConsumers).setLexiconPages(new PageText("0"), new PagePylon("1", (PylonRecipe) BlockRegistry.getRecipe(ConsumerBlock.getBlockFromName("angel"))));
        new BLexiconEntry("furnace", categoryConsumers).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", (CraftingBenchRecipe) BlockRegistry.getRecipe(ConsumerBlock.getBlockFromName("furnace"))));
        ThaumicTinkererRecipeMulti angelSwordRecipe = (ThaumicTinkererRecipeMulti) BlockRegistry.getFirstRecipeFromItem(ItemAngelsteelSword.class);
        new BLexiconEntry("angelsteel", categoryConsumers).setLexiconPages(new PageText("0"), new PageText("1"), new PageText("2"),
                new PageCraftingRecipe("4", (CraftingBenchRecipe) BlockRegistry.getRecipe((ITTinkererRegisterable) BlockRegistry.getFirstItemFromClass(ItemAngelsteelAxe.class))),
                new PageCraftingRecipe("5", (CraftingBenchRecipe) BlockRegistry.getRecipe((ITTinkererRegisterable) BlockRegistry.getFirstItemFromClass(ItemAngelsteelPickaxe.class))),
                new PageCraftingRecipe("6", (CraftingBenchRecipe) BlockRegistry.getRecipe((ITTinkererRegisterable) BlockRegistry.getFirstItemFromClass(ItemAngelsteelShovel.class))));
        new BLexiconEntry("angelsteelSword", catagoryAccessories).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", angelSwordRecipe.getIRecipies(0, 6))
                , new PageText("2"), new PageText("3"), new PageText("4"), new PageText("5"), new PageText("6"), new PageText("7"));


        new BLexiconEntry("enchanter", categoryEnchants).setPriority().setLexiconPages(new PageText("0"), new PagePylon("1", (PylonRecipe) BlockRegistry.getRecipe(ConsumerBlock.getBlockFromName("enchant"))), new PageText("2"), new PageText("3"));

        new BLexiconEntry("basicEffects", categoryEnchants).setLexiconPages(new PageText("0"), new PageText("1"), new PageText("2"), new PageText("3"), new PageText("4"), new PageText("5"));
        new BLexiconEntry("speedBoosts", categoryEnchants).setLexiconPages(new PageText("0"), new PageText("1"), new PageText("2"), new PageText("3"), new PageText("4"));
        new BLexiconEntry("looting", categoryEnchants).setLexiconPages(new PageText("0"), new PageText("1"));
        new BLexiconEntry("areaOfEffect", categoryEnchants).setLexiconPages(new PageText("0"), new PageText("1"));
        new BLexiconEntry("negative", categoryEnchants).setLexiconPages(new PageText("0"), new PageText("1"), new PageText("2"));
        new BLexiconEntry("combat", categoryEnchants).setLexiconPages(new PageText("0"), new PageText("1"), new PageText("2"));


        //Fairies
        new BLexiconEntry("fairies", categoryFairies).setPriority().setLexiconPages(new PageText("0"), new PageText("1"),
                new PagePylon("2", (PylonRecipe) BlockRegistry.getFirstRecipeFromItem(ItemFairyRing.class)),
                new PageCraftingRecipe("3", ((ThaumicTinkererRecipeMulti) BlockRegistry.getFirstRecipeFromItem(ItemFairyCharm.class)).recipes.get(0)));
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
        //Walkthrough
        new BLexiconEntry("video", categoryWalkthrough).setPriority().setLexiconPages(new PageGuide("0"));
        new BLexiconEntry("introduction", categoryWalkthrough).setPriority().setLexiconPages(new PageText("0"));
        new BLexiconEntry("basicSetup", categoryWalkthrough).setLexiconPages(new PageText("0"), new PageImage("1", "aura:textures/gui/walkthrough/0.png"), new PageText("2"));
        new BLexiconEntry("vortexInfusion", categoryWalkthrough).setLexiconPages(new PageText("0"), new PageImage("1", "aura:textures/gui/walkthrough/1.png"));

    }

}

