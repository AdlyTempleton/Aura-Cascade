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
import net.minecraftforge.fml.common.ModAPIManager;
import pixlepix.auracascade.QuestManager;
import pixlepix.auracascade.block.*;
import pixlepix.auracascade.block.entity.*;
import pixlepix.auracascade.data.EnumRainbowColor;
import pixlepix.auracascade.data.Quest;
import pixlepix.auracascade.data.recipe.PylonRecipe;
import pixlepix.auracascade.data.recipe.PylonRecipeRegistry;
import pixlepix.auracascade.item.*;
import pixlepix.auracascade.lexicon.page.*;
import pixlepix.auracascade.registry.*;

public final class LexiconData {

    public static CraftingBenchRecipe getRecipeFromFairy(Class<? extends EntityFairy> clazz) {
        int num = 0;
        for (int i = 0; i < ItemFairyCharm.fairyClasses.length; i++) {
            Class<?> clazzCompare = ItemFairyCharm.fairyClasses[i];
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
        //TODO bring this back
        //if (Config.questline)
        if(false){
            categoryQuest = CategoryManager.categoryQuest = new LexiconCategory("Quests").setIcon(new ItemStack(Items.DIAMOND_SWORD));
            CategoryManager.addCategory(categoryQuest);
        }

        LexiconCategory categoryWalkthrough = CategoryManager.categoryWalkthrough = new LexiconCategory("Walkthrough").setIcon(new ItemStack(Items.WOODEN_PICKAXE));
        CategoryManager.addCategory(categoryWalkthrough);

        LexiconCategory categoryBasics = CategoryManager.categoryBasics = new LexiconCategory("Basics").setIcon(new ItemStack(AuraBlock.getBlockFromName("")));
        CategoryManager.addCategory(categoryBasics);
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
        //TODO questline bring back
        if (false) {
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
                new PageCraftingRecipe("2", (OreCraftingBenchRecipe)BlockRegistry.getRecipe(itemAuraCrystal)),
                new PageCraftingRecipe("3", (CraftingBenchRecipe) BlockRegistry.getRecipe(AuraBlock.getBlockFromName("")))).tutorial();
        new BLexiconEntry("power", categoryBasics).setLexiconPages(new PageText("0"), new PageText("1")).setPriority().tutorial();
        new BLexiconEntry("pumps", categoryBasics).setLexiconPages(new PageText("0"), new PageText("1"), new PageText("2"),
                new PageCraftingRecipe("3", (CraftingBenchRecipe) BlockRegistry.getRecipe(AuraBlock.getBlockFromName("pump")))).tutorial();
        new BLexiconEntry("consumers", categoryConsumers).setPriority().setLexiconPages(new PageText("0")).tutorial();
        new BLexiconEntry("fish", categoryConsumers).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", BlockRegistry.getRecipe(ConsumerBlock.getBlockFromName("fish"))));
        new BLexiconEntry("furnace", categoryConsumers).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", (CraftingBenchRecipe) BlockRegistry.getRecipe(ConsumerBlock.getBlockFromName("furnace")))).tutorial();
        new BLexiconEntry("dye", categoryConsumers).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", (CraftingBenchRecipe) BlockRegistry.getRecipe(ConsumerBlock.getBlockFromName("dye")))).tutorial();

        new BLexiconEntry("materials", categoryBasics).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", BlockRegistry.getRecipe(ConsumerBlock.getBlockFromName("ore"))), new PageText("2"), new PageText("3"), new PagePylon("4", PylonRecipeRegistry.getRecipe(ItemMaterial.getGem(EnumRainbowColor.WHITE))), new PageText("5")
                , new PageText("6"), new PageText("7"), new PageCraftingRecipe("8", BlockRegistry.getRecipe(ConsumerBlock.getBlockFromName("oreAdv")))).tutorial();

        new BLexiconEntry("crafting", categoryConsumers).tutorial().setPriority().setLexiconPages(new PageText("0"), new PageText("1"),
                new PageCraftingRecipe("2", (CraftingBenchRecipe) BlockRegistry.getRecipe(AuraBlock.getBlockFromName("craftingCenter"))),
                new PageCraftingRecipe("3", (CraftingBenchRecipe) BlockRegistry.getRecipe(AuraBlock.getBlockFromName("craftingPedestal"))),
                new PagePylon("4", PylonRecipeRegistry.getRecipe(new ItemStack(Items.LEATHER))),
                new PagePylon("5", PylonRecipeRegistry.getRecipe(new ItemStack(Items.BLAZE_POWDER, 20))),
                new PagePylon("6", PylonRecipeRegistry.getRecipe(new ItemStack(Items.SADDLE, 1))),
                new PagePylon("7", PylonRecipeRegistry.getRecipe(new ItemStack(Items.ENDER_EYE, 2))),
                new PagePylon("8", PylonRecipeRegistry.getRecipe(new ItemStack(Items.ARROW, 8))),
                new PagePylon("9", PylonRecipeRegistry.getRecipe(new ItemStack(Blocks.RAIL, 32))),
                new PagePylon("10", PylonRecipeRegistry.getRecipe(new ItemStack(Blocks.LAPIS_BLOCK, 1))),
                new PagePylon("11", PylonRecipeRegistry.getRecipe(new ItemStack(Items.REPEATER))),
                new PagePylon("12", PylonRecipeRegistry.getRecipe(new ItemStack(Items.COMPARATOR))),
                new PagePylon("13", PylonRecipeRegistry.getRecipe(new ItemStack(Blocks.SOUL_SAND))),
                new PagePylon("14", PylonRecipeRegistry.getRecipe(new ItemStack(Blocks.GOLD_BLOCK))),
                new PagePylon("15", PylonRecipeRegistry.getRecipe(new ItemStack(Blocks.DIAMOND_BLOCK))));


        // BASICS ENTRIES
        new BLexiconEntry("interactions", categoryBasics).setLexiconPages(new PageText("0"), new PageText("1"));
        new BLexiconEntry("comparator", categoryBasics).setLexiconPages(new PageText("0"));
        new BLexiconEntry("monitor", categoryBasics).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ((CraftingBenchRecipe) BlockRegistry.getFirstRecipeFromBlock(BlockMonitor.class)).iRecipe));

        //Aura Nodes
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
        new BLexiconEntry("miner", categoryConsumers).setLexiconPages(new PageText("0"), new PageText("1"), new PageText("2"), new PageText("3"), new PageText("4"), new PageCraftingRecipe("6", BlockRegistry.getRecipe(ConsumerBlock.getBlockFromName("miner"))));
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
        new BLexiconEntry("protection", catagoryAccessories).setLexiconPages(new PageText("0"), new PageText("1"),
                new PageCraftingRecipe("2", ItemRedAmulet.class),
                new PageCraftingRecipe("3", ItemOrangeAmulet.class),
                new PageCraftingRecipe("4", ItemYellowAmulet.class),
                new PageCraftingRecipe("5", ItemGreenAmulet.class),
                new PageCraftingRecipe("6", ItemBlueAmulet.class),
                new PageCraftingRecipe("7", ItemPurpleAmulet.class));


        if (ModAPIManager.INSTANCE.hasAPI("CoFHAPI|energy")) {
            new BLexiconEntry("flux", categoryConsumers).setPriority().setLexiconPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", ((CraftingBenchRecipe) BlockRegistry.getRecipe(AuraBlock.getBlockFromName("flux"))).iRecipe));
        }
        new BLexiconEntry("nether", categoryConsumers).setLexiconPages(new PageText("0"), new PagePylon("1", (PylonRecipe) BlockRegistry.getRecipe(ConsumerBlock.getBlockFromName("nether"))));
        new BLexiconEntry("end", categoryConsumers).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", BlockRegistry.getRecipe(ConsumerBlock.getBlockFromName("end"))));

        new BLexiconEntry("loot", categoryConsumers).setLexiconPages(new PageText("0"), new PagePylon("1", (PylonRecipe) BlockRegistry.getRecipe(ConsumerBlock.getBlockFromName("loot"))));
        new BLexiconEntry("plant", categoryConsumers).setLexiconPages(new PageText("0"), new PagePylon("1", (PylonRecipe) BlockRegistry.getRecipe(ConsumerBlock.getBlockFromName("plant"))));
        new BLexiconEntry("mob", categoryConsumers).setLexiconPages(new PageText("0"), new PagePylon("1", (PylonRecipe) BlockRegistry.getRecipe(ConsumerBlock.getBlockFromName("mob"))));
        new BLexiconEntry("brewer", categoryConsumers).setLexiconPages(new PageText("0"), new PagePylon("1", (PylonRecipe) BlockRegistry.getRecipe(ConsumerBlock.getBlockFromName("potion"))));
        new BLexiconEntry("angel", categoryConsumers).setLexiconPages(new PageText("0"), new PagePylon("1", (PylonRecipe) BlockRegistry.getRecipe(ConsumerBlock.getBlockFromName("angel"))));
        ThaumicTinkererRecipeMulti angelSwordRecipe = (ThaumicTinkererRecipeMulti) BlockRegistry.getFirstRecipeFromItem(ItemAngelsteelSword.class);
        new BLexiconEntry("angelsteel", categoryConsumers).setLexiconPages(new PageText("0"), new PageText("1"), new PageText("2"),
                new PageCraftingRecipe("4", (CraftingBenchRecipe) BlockRegistry.getRecipe((ITTinkererRegisterable) BlockRegistry.getFirstItemFromClass(ItemAngelsteelAxe.class))),
                new PageCraftingRecipe("5", (CraftingBenchRecipe) BlockRegistry.getRecipe((ITTinkererRegisterable) BlockRegistry.getFirstItemFromClass(ItemAngelsteelPickaxe.class))),
                new PageCraftingRecipe("6", (CraftingBenchRecipe) BlockRegistry.getRecipe((ITTinkererRegisterable) BlockRegistry.getFirstItemFromClass(ItemAngelsteelShovel.class))));
        new BLexiconEntry("angelsteelSword", catagoryAccessories).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", angelSwordRecipe.getIRecipies(0, 6))
                , new PageText("2"), new PageText("3"), new PageText("4"), new PageText("5"), new PageText("6"), new PageText("7"));


        new BLexiconEntry("enchanter", categoryEnchants).setPriority().setLexiconPages(new PageText("0"), new PagePylon("1", (PylonRecipe) BlockRegistry.getRecipe(ConsumerBlock.getBlockFromName("enchant"))), new PageText("2"), new PageText("3"), new PageText(("4")));

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
        new BLexiconEntry("video", categoryWalkthrough).setPriority().setLexiconPages(new PageGuide("0", "auramisc.playVideo", "https://www.youtube.com/watch?v=dQw4w9WgXcQ"), new PageGuide("1", "auramisc.playVideo", "https://www.youtube.com/watch?v=bbtr85S5m4Q"));
        new BLexiconEntry("introduction", categoryWalkthrough).setPriority().setLexiconPages(new PageText("0"));
        ItemStack node = AuraBlock.getAuraNodeItemstack();
        ItemStack pumpnode = AuraBlock.getAuraNodePumpItemstack();
        ItemStack furnacenode = new ItemStack(ConsumerBlock.getBlockFromName("furnace"));
        ItemStack pedestal = new ItemStack(AuraBlock.getBlockFromName("craftingPedestal"));
        ItemStack vortex = new ItemStack(AuraBlock.getBlockFromName("craftingCenter"));
        ItemStack monitor = new ItemStack(BlockRegistry.getFirstBlockFromClass(BlockMonitor.class));
        ItemStack cobble = new ItemStack(Blocks.COBBLESTONE);
        new BLexiconEntry("patreon", categoryWalkthrough).setPriority().setLexiconPages(new PageGuide("0", "auramisc.support", "https://www.patreon.com/pixlepix"));
        new BLexiconEntry("basicSetup", categoryWalkthrough).setLexiconPages(new PageText("0"), new MultiblockPage("1", new ItemStack[][][]{
                {{pumpnode, node, furnacenode}},
                {}, {},
                {{node, node}}

        }
        ));
        new BLexiconEntry("autoOff", categoryWalkthrough).setLexiconPages(new MultiblockPage("0", new ItemStack[][][]{
                {{pumpnode, node}, {monitor, furnacenode}},
                {}, {},
                {{node, node}}
        }));
        new BLexiconEntry("accumulate", categoryWalkthrough).setLexiconPages(new MultiblockPage("0", new ItemStack[][][]{

                {{null, null, pumpnode, node, furnacenode}},
                {{null, null, cobble}},
                {{null, null, node, node}}
        }));
        new BLexiconEntry("multiplePumps", categoryWalkthrough).setLexiconPages(new MultiblockPage("0", new ItemStack[][][]{
                {{null, pumpnode}, {pumpnode, node, furnacenode}, {null, pumpnode}},
                {}, {},
                {{null, node}, {node, node}, {null, node}}
        }));

        new BLexiconEntry("vortexInfusion", categoryWalkthrough).setLexiconPages(new MultiblockPage("0", new ItemStack[][][]{
                {{null, null, pumpnode, null, null}, {null, null, pedestal, null, null}, {pumpnode, pedestal, vortex, pedestal, pumpnode}, {null, null, pedestal, null, null}, {null, null, pumpnode, null, null}},
                {},
                {{null, null, node, null, null}, {null, null, node, null, null}, {node, node, cobble, node, node}, {null, null, node, null, null}, {null, null, node, null, null}}

        }));

    }

}

