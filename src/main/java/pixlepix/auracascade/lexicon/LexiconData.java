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

	public static CraftingBenchRecipe getRecipeFromFairy(Class<? extends EntityFairy> clazz){
		int num = 0;
		for(int i=0; i < ItemFairyCharm.fairyClasses.length; i++){
			Class clazzCompare = ItemFairyCharm.fairyClasses[i];
			if(clazzCompare.equals(clazz)){
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
		LexiconCategory categoryConsumers = CategoryManager.categoryConsumers= new LexiconCategory("Consumers");
		CategoryManager.addCategory(categoryConsumers);
		LexiconCategory categoryFairies = CategoryManager.categoryFairies= new LexiconCategory("Fairies");
		CategoryManager.addCategory(categoryFairies);

		// BASICS ENTRIES
		new BLexiconEntry("basics", categoryBasics).setPriority().setLexiconPages(new PageText("0"), new PageText("1"));
		ItemAuraCrystal itemAuraCrystal = (ItemAuraCrystal) BlockRegistry.getFirstItemFromClass(ItemAuraCrystal.class);
		new BLexiconEntry("auraFlow", categoryBasics).setLexiconPages(new PageText("0"), new PageText("1"),
				new PageCraftingRecipe("2", ((ThaumicTinkererRecipeMulti)BlockRegistry.getRecipe(itemAuraCrystal)).getIRecipies(0, 1)),
				new PageCraftingRecipe("3", (CraftingBenchRecipe)BlockRegistry.getRecipe(AuraBlock.getBlockFromName(""))));
		new BLexiconEntry("interactions", categoryBasics).setLexiconPages(new PageText("0"), new PageText("1"));
		new BLexiconEntry("power", categoryBasics).setLexiconPages(new PageText("0"), new PageText("1"));
		new BLexiconEntry("pumps", categoryBasics).setLexiconPages(new PageText("0"), new PageText("1"), new PageText("2"),
				new PageCraftingRecipe("3", (CraftingBenchRecipe)BlockRegistry.getRecipe(AuraBlock.getBlockFromName("pump"))));

		//Aura Colors
		new BLexiconEntry("red", categoryAuraColors).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1",((ThaumicTinkererRecipeMulti)BlockRegistry.getRecipe(itemAuraCrystal)).getIRecipies(3, 4)));
		new BLexiconEntry("orange", categoryAuraColors).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1",((ThaumicTinkererRecipeMulti)BlockRegistry.getRecipe(itemAuraCrystal)).getIRecipies(4, 5)));
		new BLexiconEntry("yellow", categoryAuraColors).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1",((ThaumicTinkererRecipeMulti)BlockRegistry.getRecipe(itemAuraCrystal)).getIRecipies(5, 6)));
		new BLexiconEntry("blue", categoryAuraColors).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1",((ThaumicTinkererRecipeMulti)BlockRegistry.getRecipe(itemAuraCrystal)).getIRecipies(1, 2)));
		new BLexiconEntry("green", categoryAuraColors).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1",((ThaumicTinkererRecipeMulti)BlockRegistry.getRecipe(itemAuraCrystal)).getIRecipies(6, 7)));
		new BLexiconEntry("violet", categoryAuraColors).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1",((ThaumicTinkererRecipeMulti)BlockRegistry.getRecipe(itemAuraCrystal)).getIRecipies(7, 8)));
		new BLexiconEntry("black", categoryAuraColors).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1",((ThaumicTinkererRecipeMulti)BlockRegistry.getRecipe(itemAuraCrystal)).getIRecipies(2, 3)));

		//Aura Nodes
		new BLexiconEntry("manipulator", categoryAuraNodes).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ((CraftingBenchRecipe)BlockRegistry.getRecipe(AuraBlock.getBlockFromName("black"))).iRecipe), new PageCraftingRecipe("2", ((CraftingBenchRecipe)BlockRegistry.getRecipe(AuraBlock.getBlockFromName("orange"))).iRecipe));
		new BLexiconEntry("conserve", categoryAuraNodes).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ((CraftingBenchRecipe)BlockRegistry.getRecipe(AuraBlock.getBlockFromName("conserve"))).iRecipe));
		new BLexiconEntry("capacitor", categoryAuraNodes).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ((CraftingBenchRecipe)BlockRegistry.getRecipe(AuraBlock.getBlockFromName("capacitor"))).iRecipe));
		new BLexiconEntry("pumpProjectile", categoryAuraNodes).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ((CraftingBenchRecipe)BlockRegistry.getRecipe(AuraBlock.getBlockFromName("pumpProjectile"))).iRecipe));
		new BLexiconEntry("pumpFall", categoryAuraNodes).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ((CraftingBenchRecipe)BlockRegistry.getRecipe(AuraBlock.getBlockFromName("pumpFall"))).iRecipe));
		new BLexiconEntry("pumpLight", categoryAuraNodes).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ((CraftingBenchRecipe)BlockRegistry.getRecipe(AuraBlock.getBlockFromName("pumpLight"))).iRecipe));
		new BLexiconEntry("pumpRedstone", categoryAuraNodes).setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ((CraftingBenchRecipe)BlockRegistry.getRecipe(AuraBlock.getBlockFromName("pumpRedstone"))).iRecipe));

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

		/*
		flowers = new BLexiconEntry(LibLexicon.BASICS_FLOWERS, categoryBasics);
		flowers.setPriority().setLexiconPages(new PageText("0"), new PageImage("1", LibResources.ENTRY_FLOWERS), new PageText("2"), new PageCraftingRecipe("3", ModCraftingRecipes.recipesPetals), new PageCraftingRecipe("4", ModCraftingRecipes.recipePestleAndMortar), new PageCraftingRecipe("5", ModCraftingRecipes.recipesDyes),
				new PageText("6"), new PageCraftingRecipe("7", ModCraftingRecipes.recipeFertilizerPowder), new PageCraftingRecipe("8", ModCraftingRecipes.recipeFerilizerDye));

		apothecary = new BLexiconEntry(LibLexicon.BASICS_APOTHECARY, categoryBasics);
		apothecary.setPriority().setLexiconPages(new PageText("0"), new PageImage("1", LibResources.ENTRY_APOTHECARY), new PageText("2"), new PageText("3"), new PageText("4"), new PageCraftingRecipe("5", ModCraftingRecipes.recipesApothecary));

		lexicon = new BLexiconEntry(LibLexicon.BASICS_LEXICON, categoryBasics);
		lexicon.setPriority().setLexiconPages(new PageText("0"), new PageText("3"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeLexicon), new PageText("2"));

		wand = new BLexiconEntry(LibLexicon.BASICS_WAND, categoryBasics);
		wand.setPriority().setLexiconPages(new PageText("0"), new PageText("1"), new PageText("2"), new PageCraftingRecipe("3", ModCraftingRecipes.recipesTwigWand));

		pureDaisy = new BLexiconEntry(LibLexicon.BASICS_PURE_DAISY, categoryBasics);
		pureDaisy.setPriority().setLexiconPages(new PageText("0"), new PageImage("1", LibResources.ENTRY_PURE_DAISY), new PageCraftingRecipe("2", ModCraftingRecipes.recipeLivingwoodTwig), new PagePetalRecipe("3", ModPetalRecipes.pureDaisyRecipe));
		LexiconRecipeMappings.map(new ItemStack(ModBlocks.livingwood), pureDaisy, 1);
		LexiconRecipeMappings.map(new ItemStack(ModBlocks.livingrock), pureDaisy, 1);

		runicAltar = new BLexiconEntry(LibLexicon.BASICS_RUNE_ALTAR, categoryBasics);
		runicAltar.setPriority().setLexiconPages(new PageText("21"), new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", ModCraftingRecipes.recipesRuneAltar), new PageText("3"), new PageText("20"), new PageRuneRecipe("4", ModRuneRecipes.recipeWaterRune), new PageRuneRecipe("5", ModRuneRecipes.recipesEarthRune), new PageRuneRecipe("6", ModRuneRecipes.recipesAirRune), new PageRuneRecipe("7", ModRuneRecipes.recipeFireRune),
				new PageRuneRecipe("8", ModRuneRecipes.recipeSpringRune), new PageRuneRecipe("9", ModRuneRecipes.recipeSummerRune), new PageRuneRecipe("10", ModRuneRecipes.recipeAutumnRune), new PageRuneRecipe("11", ModRuneRecipes.recipesWinterRune),  new PageRuneRecipe("12", ModRuneRecipes.recipeManaRune),
				new PageRuneRecipe("13", ModRuneRecipes.recipeLustRune), new PageRuneRecipe("14", ModRuneRecipes.recipeGluttonyRune), new PageRuneRecipe("15", ModRuneRecipes.recipeGreedRune), new PageRuneRecipe("16", ModRuneRecipes.recipeSlothRune), new PageRuneRecipe("17", ModRuneRecipes.recipeWrathRune), new PageRuneRecipe("18", ModRuneRecipes.recipeEnvyRune), new PageRuneRecipe("19", ModRuneRecipes.recipePrideRune));

		terrasteel = new BLexiconEntry(LibLexicon.BASICS_TERRASTEEL, categoryBasics);
		terrasteel.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeTerraPlate), new PageText("2"), new PageTerrasteel("3"));

		if(Botania.thaumcraftLoaded)
			new BLexiconEntry("wrap", categoryBasics).setLexiconPages(new PageText("0")); // lel

		// MANA ENTRIES
		manaIntro = new BLexiconEntry(LibLexicon.MANA_INTRO, categoryMana);
		manaIntro.setPriority().setLexiconPages(new PageText("0"), new PageText("1"), new PageText("2"));

		spreader = new BLexiconEntry(LibLexicon.MANA_SPREADER, categoryMana);
		spreader.setPriority().setLexiconPages(new PageText("0"), new PageImage("1", LibResources.ENTRY_SPREADER), new PageText("2"), new PageText("3"), new PageText("4"), new PageText("11"), new PageCraftingRecipe("5", ModCraftingRecipes.recipesSpreader));

		pool = new BLexiconEntry(LibLexicon.MANA_POOL, categoryMana);
		pool.setPriority().setLexiconPages(new PageText("0"), new PageText("9"), new PageCraftingRecipe("1", ModCraftingRecipes.recipePool), new PageManaInfusionRecipe("10", ModManaInfusionRecipes.poolRecipe), new PageText("2"), new PageText("8"), new PageManaInfusionRecipe("3", ModManaInfusionRecipes.manasteelRecipes), new PageManaInfusionRecipe("4", ModManaInfusionRecipes.manaPearlRecipe), new PageManaInfusionRecipe("5", ModManaInfusionRecipes.manaDiamondRecipe), new PageManaInfusionRecipe("6", ModManaInfusionRecipes.manaPetalRecipes), new PageManaInfusionRecipe("11", ModManaInfusionRecipes.managlassRecipe), new PageManaInfusionRecipe("7", ModManaInfusionRecipes.manaCookieRecipe));

		sparks = new BLexiconEntry(LibLexicon.MANA_SPARKS, categoryMana);
		sparks.setPriority().setLexiconPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", ModCraftingRecipes.recipesSpark));

		sparkUpgrades = new ALexiconEntry(LibLexicon.MANA_SPARK_UPGRADES, categoryMana);
		sparkUpgrades.setLexiconPages(new PageText("0"), new PageText("1"), new PageText("2"), new PageText("3"), new PageText("4"), new PageCraftingRecipe("5", ModCraftingRecipes.recipesSparkUpgrades));

		rfGenerator = new BLexiconEntry(LibLexicon.MANA_RF_GENERATOR, categoryMana);
		rfGenerator.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeRFGenerator));

		lenses = new BLexiconEntry(LibLexicon.MANA_LENSES, categoryMana);
		lenses.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeManaLens), new PageCraftingRecipe("2", ModCraftingRecipes.recipesLensDying), new PageCraftingRecipe("3", ModCraftingRecipes.recipeRainbowLens), new PageText("4"), new PageText("5"),
				new PageText("6"), new PageCraftingRecipe("7", ModCraftingRecipes.recipeLensVelocity),
				new PageText("8"), new PageCraftingRecipe("9", ModCraftingRecipes.recipeLensPotency),
				new PageText("10"), new PageCraftingRecipe("11", ModCraftingRecipes.recipeLensResistance),
				new PageText("12"), new PageCraftingRecipe("13", ModCraftingRecipes.recipeLensEfficiency),
				new PageText("14"), new PageCraftingRecipe("15", ModCraftingRecipes.recipeLensBounce),
				new PageText("16"), new PageCraftingRecipe("17", ModCraftingRecipes.recipeLensGravity),
				new PageText("18"), new PageCraftingRecipe("19", ModCraftingRecipes.recipeLensBore),
				new PageText("20"), new PageCraftingRecipe("21", ModCraftingRecipes.recipeLensDamaging),
				new PageText("22"), new PageCraftingRecipe("23", ModCraftingRecipes.recipeLensPhantom),
				new PageText("24"), new PageCraftingRecipe("25", ModCraftingRecipes.recipeLensMagnet),
				new PageText("26"), new PageCraftingRecipe("27", ModCraftingRecipes.recipeLensExplosive),
				new PageText("28"), new PageCraftingRecipe("29", ModCraftingRecipes.recipeLensInfluence),
				new PageText("30"), new PageCraftingRecipe("31", ModCraftingRecipes.recipeLensWeight),
				new PageText("32"), new PageCraftingRecipe("33", ModCraftingRecipes.recipeLensFire),
				new PageText("34"), new PageCraftingRecipe("35", ModCraftingRecipes.recipeLensPiston),
				new PageText("36"), new PageCraftingRecipe("37", ModCraftingRecipes.recipesLensFlash));

		distributor = new BLexiconEntry(LibLexicon.MANA_DISTRIBUTOR, categoryMana);
		distributor.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeDistributor));

		manaVoid = new BLexiconEntry(LibLexicon.MANA_VOID, categoryMana);
		manaVoid.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeManaVoid));

		manaTransport = new BLexiconEntry(LibLexicon.MANA_TRANSPORT, categoryMana);
		manaTransport.setLexiconPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", ModCraftingRecipes.recipesManaTablet),
				new PageText("3"), new PageCraftingRecipe("4", ModCraftingRecipes.recipeManaMirror));

		manaDetector = new BLexiconEntry(LibLexicon.MANA_DETECTOR, categoryMana);
		manaDetector.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeManaDetector));

		redstoneSpreader = new BLexiconEntry(LibLexicon.MANA_REDSTONE_SPREADER, categoryMana);
		redstoneSpreader.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeRedstoneSpreader));

		manastar = new BLexiconEntry(LibLexicon.MANA_MANASTAR, categoryMana);
		manastar.setLexiconPages(new PageText("0"), new PagePetalRecipe("1", ModPetalRecipes.manastarRecipe));

		dreamwoodSpreader = new ALexiconEntry(LibLexicon.MANA_DREAMWOOD_SPREADER, categoryMana);
		dreamwoodSpreader.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipesDreamwoodSpreader), new PageText("2"), new PageCraftingRecipe("3", ModCraftingRecipes.recipeUltraSpreader));

		paintLens = new ALexiconEntry(LibLexicon.MANA_PAINT_LENS, categoryMana);
		paintLens.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeLensPaint));

		// FUNCTIONAL FLOWERS ENTRIES
		functionalIntro = new BLexiconEntry(LibLexicon.FFLOWER_INTRO, categoryFunctionalFlowers);
		functionalIntro.setPriority().setLexiconPages(new PageText("0"), new PageText("1"), new PageText("2"), new PageText("3"), new PageCraftingRecipe("4", ModCraftingRecipes.recipeRedstoneRoot));

		jadedAmaranthus = new BLexiconEntry(LibLexicon.FFLOWER_JADED_AMARANTHUS, categoryFunctionalFlowers);
		jadedAmaranthus.setLexiconPages(new PageText("0"), new PagePetalRecipe("1", ModPetalRecipes.jadedAmaranthusRecipe), new PageText("2"));

		bellethorne = new BLexiconEntry(LibLexicon.FFLOWER_BELLETHORNE, categoryFunctionalFlowers);
		bellethorne.setLexiconPages(new PageText("0"), new PagePetalRecipe("1", ModPetalRecipes.bellethorneRecipe));

		dreadthorne = new BLexiconEntry(LibLexicon.FFLOWER_DREADTHORNE, categoryFunctionalFlowers);
		dreadthorne.setLexiconPages(new PageText("0"), new PagePetalRecipe("1", ModPetalRecipes.dreadthorneRecipe));

		heiseiDream = new BLexiconEntry(LibLexicon.FFLOWER_HEISEI_DREAM, categoryFunctionalFlowers);
		heiseiDream.setLexiconPages(new PageText("0"), new PagePetalRecipe("1", ModPetalRecipes.heiseiDreamRecipe), new PageText("2"));

		tigerseye = new BLexiconEntry(LibLexicon.FFLOWER_TIGERSEYE, categoryFunctionalFlowers);
		tigerseye.setLexiconPages(new PageText("0"), new PagePetalRecipe("1", ModPetalRecipes.tigerseyeRecipe));

		orechid = new BLexiconEntry(LibLexicon.FFLOWER_ORECHID, categoryFunctionalFlowers);
		orechid.setLexiconPages(new PageText("0"), new PagePetalRecipe("1", ModPetalRecipes.orechidRecipe));

		if(ConfigHandler.fallenKanadeEnabled) {
			fallenKanade = new BLexiconEntry(LibLexicon.FFLOWER_FALLEN_KANADE, categoryFunctionalFlowers);
			fallenKanade.setLexiconPages(new PageText("0"), new PagePetalRecipe("1", ModPetalRecipes.fallenKanadeRecipe), new PageText("2"));
		}

		exoflame = new BLexiconEntry(LibLexicon.FFLOWER_EXOFLAME, categoryFunctionalFlowers);
		exoflame.setLexiconPages(new PageText("0"), new PagePetalRecipe("1", ModPetalRecipes.exoflameRecipe));

		agricarnation = new BLexiconEntry(LibLexicon.FFLOWER_AGRICARNATION, categoryFunctionalFlowers);
		agricarnation.setLexiconPages(new PageText("0"), new PagePetalRecipe("1", ModPetalRecipes.agricarnationRecipe));

		hopperhock = new BLexiconEntry(LibLexicon.FFLOWER_HOPPERHOCK, categoryFunctionalFlowers);
		hopperhock.setLexiconPages(new PageText("0"), new PageText("1"), new PagePetalRecipe("2", ModPetalRecipes.hopperhockRecipe));

		tangleberrie = new BLexiconEntry(LibLexicon.FFLOWER_TANGLEBERRIE, categoryFunctionalFlowers);
		tangleberrie.setLexiconPages(new PageText("0"), new PagePetalRecipe("1", ModPetalRecipes.tangleberrieRecipe));

		jiyuulia = new BLexiconEntry(LibLexicon.FFLOWER_JIYUULIA, categoryFunctionalFlowers);
		jiyuulia.setLexiconPages(new PageText("0"), new PagePetalRecipe("1", ModPetalRecipes.jiyuuliaRecipe));

		rannuncarpus = new BLexiconEntry(LibLexicon.FFLOWER_RANNUNCARPUS, categoryFunctionalFlowers);
		rannuncarpus.setLexiconPages(new PageText("0"), new PageText("1"), new PagePetalRecipe("2", ModPetalRecipes.rannuncarpusRecipe));

		hyacidus = new BLexiconEntry(LibLexicon.FFLOWER_HYACIDUS, categoryFunctionalFlowers);
		hyacidus.setLexiconPages(new PageText("0"), new PagePetalRecipe("1", ModPetalRecipes.hyacidusRecipe));

		pollidisiac = new BLexiconEntry(LibLexicon.FFLOWER_POLLIDISIAC, categoryFunctionalFlowers);
		pollidisiac.setLexiconPages(new PageText("0"), new PagePetalRecipe("1", ModPetalRecipes.pollidisiacRecipe));

		clayconia = new BLexiconEntry(LibLexicon.FFLOWER_CLAYCONIA, categoryFunctionalFlowers);
		clayconia.setLexiconPages(new PageText("0"), new PagePetalRecipe("1", ModPetalRecipes.clayconiaRecipe));

		loonium = new BLexiconEntry(LibLexicon.FFLOWER_LOONIUM, categoryFunctionalFlowers);
		loonium.setLexiconPages(new PageText("0"), new PagePetalRecipe("1", ModPetalRecipes.looniumRecipe));

		daffomill = new BLexiconEntry(LibLexicon.FFLOWER_DAFFOMILL, categoryFunctionalFlowers);
		daffomill.setLexiconPages(new PageText("0"), new PagePetalRecipe("1", ModPetalRecipes.daffomillRecipe));

		vinculotus = new BLexiconEntry(LibLexicon.FFLOWER_VINCULOTUS, categoryFunctionalFlowers);
		vinculotus.setLexiconPages(new PageText("0"), new PagePetalRecipe("1", ModPetalRecipes.vinculotusRecipe));

		// GENERATING FLOWERS ENTRIES
		generatingIntro = new BLexiconEntry(LibLexicon.GFLOWER_INTRO, categoryGenerationFlowers);
		generatingIntro.setPriority().setLexiconPages(new PageText("0"), new PageText("1"), new PageText("2"));

		daybloom = new BLexiconEntry(LibLexicon.GFLOWER_DAYBLOOM, categoryGenerationFlowers);
		daybloom.setPriority().setLexiconPages(new PageText("0"), new PageText(ConfigHandler.hardcorePassiveGeneration > 0 ? "1a" : "1"), new PageImage("3", LibResources.ENTRY_DIMINISHING_RETURNS), new PagePetalRecipe("2", ModPetalRecipes.daybloomRecipe), new PageText("4"), new PageText("5"));

		nightshade = new BLexiconEntry(LibLexicon.GFLOWER_NIGHTSHADE, categoryGenerationFlowers);
		nightshade.setLexiconPages(new PageText(ConfigHandler.hardcorePassiveGeneration > 0 ? "0a" :"0"), new PagePetalRecipe("1", ModPetalRecipes.nightshadeRecipe));

		endoflame = new BLexiconEntry(LibLexicon.GFLOWER_ENDOFLAME, categoryGenerationFlowers);
		endoflame.setLexiconPages(new PageText("0"), new PageText("1"), new PagePetalRecipe("2", ModPetalRecipes.endoflameRecipe));

		hydroangeas = new BLexiconEntry(LibLexicon.GFLOWER_HYDROANGEAS, categoryGenerationFlowers);
		hydroangeas.setLexiconPages(new PageText(ConfigHandler.hardcorePassiveGeneration > 0 ? "0a" : "0"), new PageImage("2", LibResources.ENTRY_HYDROANGEAS), new PagePetalRecipe("1", ModPetalRecipes.hydroangeasRecipe));

		thermalily = new BLexiconEntry(LibLexicon.GFLOWER_THERMALILY, categoryGenerationFlowers);
		thermalily.setLexiconPages(new PageText(ConfigHandler.thermalilyObsidian ? "0a" : "0"), new PagePetalRecipe("1", ModPetalRecipes.thermalilyRecipe));

		arcaneRose = new BLexiconEntry(LibLexicon.GFLOWER_ARCANE_ROSE, categoryGenerationFlowers);
		arcaneRose.setLexiconPages(new PageText("0"), new PagePetalRecipe("1", ModPetalRecipes.arcaneRoseRecipe));

		munchdew = new BLexiconEntry(LibLexicon.GFLOWER_MUNCHDEW, categoryGenerationFlowers);
		munchdew.setLexiconPages(new PageText("0"), new PagePetalRecipe("1", ModPetalRecipes.munchdewRecipe));

		entropinnyum = new BLexiconEntry(LibLexicon.GFLOWER_ENTROPINNYUM, categoryGenerationFlowers);
		entropinnyum.setLexiconPages(new PageText("0"), new PagePetalRecipe("1", ModPetalRecipes.entropinnyumRecipe));

		kekimurus = new BLexiconEntry(LibLexicon.GFLOWER_KEKIMURUS, categoryGenerationFlowers);
		kekimurus.setLexiconPages(new PageText("0"), new PagePetalRecipe("1", ModPetalRecipes.kekimurusRecipe));

		gourmaryllis = new BLexiconEntry(LibLexicon.GFLOWER_GOURMARYLLIS, categoryGenerationFlowers);
		gourmaryllis.setLexiconPages(new PageText("0"), new PageText("1"), new PageText("2"), new PagePetalRecipe("3", ModPetalRecipes.gourmaryllisRecipe), new PageText("4"), new PageText("5"), new PageText("6"));

		// DEVICES ENTRIES
		pistonRelay = new BLexiconEntry(LibLexicon.DEVICE_PISTON_RELAY, categoryDevices);
		pistonRelay.setLexiconPages(new PageText("0"), new PageText("1"), new PageManaInfusionRecipe("2", ModManaInfusionRecipes.pistonRelayRecipe));

		pylon = new BLexiconEntry(LibLexicon.DEVICE_PYLON, categoryDevices);
		pylon.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipePylon));

		if(ConfigHandler.enchanterEnabled) {
			manaEnchanting = new BLexiconEntry(LibLexicon.DEVICE_MANA_ENCHANTING, categoryDevices);
			manaEnchanting.setLexiconPages(new PageText("0"), new PageText("1"),
					new PageImage("2", LibResources.ENTRY_ENCHANTER0), new PageImage("3", LibResources.ENTRY_ENCHANTER1), new PageImage("4", LibResources.ENTRY_ENCHANTER2), new PageImage("5", LibResources.ENTRY_ENCHANTER3),
					new PageText("6"), new PageText("7"), new PageText("8"), new PageText("9"), new PageText("10"));
		}

		turntable = new BLexiconEntry(LibLexicon.DEVICE_TURNTABLE, categoryDevices);
		turntable.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeTurntable));

		alchemy = new BLexiconEntry(LibLexicon.DEVICE_ALCHEMY, categoryDevices);
		alchemy.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeAlchemyCatalyst),
				new PageManaInfusionRecipe("2", ModManaAlchemyRecipes.leatherRecipe), new PageManaInfusionRecipe("3", ModManaAlchemyRecipes.woodRecipes), new PageManaInfusionRecipe("4", ModManaAlchemyRecipes.saplingRecipes), new PageManaInfusionRecipe("5", ModManaAlchemyRecipes.glowstoneDustRecipe),
				new PageManaInfusionRecipe("6", ModManaAlchemyRecipes.quartzRecipes).setSkipRegistry(), new PageManaInfusionRecipe("7", ModManaAlchemyRecipes.chiseledBrickRecipe), new PageManaInfusionRecipe("8", ModManaAlchemyRecipes.iceRecipe), new PageManaInfusionRecipe("9", ModManaAlchemyRecipes.swampFolliageRecipes),
				new PageManaInfusionRecipe("10", ModManaAlchemyRecipes.fishRecipes), new PageManaInfusionRecipe("11", ModManaAlchemyRecipes.cropRecipes), new PageManaInfusionRecipe("12", ModManaAlchemyRecipes.potatoRecipe), new PageManaInfusionRecipe("13", ModManaAlchemyRecipes.netherWartRecipe),
				new PageManaInfusionRecipe("14", ModManaAlchemyRecipes.gunpowderAndFlintRecipes), new PageManaInfusionRecipe("15", ModManaAlchemyRecipes.nameTagRecipe), new PageManaInfusionRecipe("16", ModManaAlchemyRecipes.stringRecipes), new PageManaInfusionRecipe("17", ModManaAlchemyRecipes.slimeballCactusRecipes),
				new PageManaInfusionRecipe("18", ModManaAlchemyRecipes.enderPearlRecipe), new PageManaInfusionRecipe("19", ModManaAlchemyRecipes.redstoneToGlowstoneRecipes), new PageManaInfusionRecipe("20", ModManaAlchemyRecipes.sandRecipe), new PageManaInfusionRecipe("21", ModManaAlchemyRecipes.redSandRecipe),
				new PageManaInfusionRecipe("22", ModManaAlchemyRecipes.clayBreakdownRecipes), new PageManaInfusionRecipe("23", ModManaAlchemyRecipes.coarseDirtRecipe));

		openCrate = new BLexiconEntry(LibLexicon.DEVICE_OPEN_CRATE, categoryDevices);
		openCrate.setLexiconPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", ModCraftingRecipes.recipeOpenCrate));

		forestEye = new BLexiconEntry(LibLexicon.DEVICE_FOREST_EYE, categoryDevices);
		forestEye.setLexiconPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", ModCraftingRecipes.recipeForestEye));

		forestDrum = new BLexiconEntry(LibLexicon.DEVICE_FOREST_DRUM, categoryDevices);
		forestDrum.setLexiconPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", ModCraftingRecipes.recipeForestDrum));

		platform = new BLexiconEntry(LibLexicon.DEVICE_PLATFORM, categoryDevices);
		platform.setLexiconPages(new PageText("0"), new PageText("2"), new PageCraftingRecipe("1", ModCraftingRecipes.recipePlatform));

		conjurationCatalyst = new ALexiconEntry(LibLexicon.DEVICE_MANA_CONJURATION, categoryDevices);
		conjurationCatalyst.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeConjurationCatalyst), new PageManaInfusionRecipe("2", ModManaConjurationRecipes.redstoneRecipe), new PageManaInfusionRecipe("3", ModManaConjurationRecipes.glowstoneRecipe), new PageManaInfusionRecipe("4", ModManaConjurationRecipes.quartzRecipe),
				new PageManaInfusionRecipe("5", ModManaConjurationRecipes.coalRecipe), new PageManaInfusionRecipe("6", ModManaConjurationRecipes.snowballRecipe), new PageManaInfusionRecipe("7", ModManaConjurationRecipes.netherrackRecipe), new PageManaInfusionRecipe("8", ModManaConjurationRecipes.soulSandRecipe), new PageManaInfusionRecipe("9", ModManaConjurationRecipes.gravelRecipe));

		spectralPlatform = new ALexiconEntry(LibLexicon.DEVICE_SPECTRAL_PLATFORM, categoryDevices);
		spectralPlatform.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeSpectralPlatform));

		gatherDrum = new ALexiconEntry(LibLexicon.DEVICE_GATHER_DRUM, categoryDevices);
		gatherDrum.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeGatherDrum));

		spawnerClaw = new ALexiconEntry(LibLexicon.DEVICE_SPAWNER_CLAW, categoryDevices);
		spawnerClaw.setLexiconPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", ModCraftingRecipes.recipeSpawnerClaw));

		craftCrate = new ALexiconEntry(LibLexicon.DEVICE_CRAFT_CRATE, categoryDevices);
		craftCrate.setLexiconPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", ModCraftingRecipes.recipePlaceholder), new PageText("3"), new PageText("4"), new PageText("7"), new PageImage("5", LibResources.ENTRY_CRAFT_CRATE), new PageCraftingRecipe("6", ModCraftingRecipes.recipeCraftCrate));

		enderEyeBlock = new BLexiconEntry(LibLexicon.DEVICE_ENDER_EYE_BLOCK, categoryDevices);
		enderEyeBlock.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeEnderEyeBlock));

		brewery = new BLexiconEntry(LibLexicon.DEVICE_BREWERY, categoryDevices);
		brewery.setLexiconPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", ModCraftingRecipes.recipeBrewery), new PageCraftingRecipe("3", ModCraftingRecipes.recipeVial), new PageText("4"),
				new PageBrew(ModBrewRecipes.speedBrew, "5a", "5b"), new PageBrew(ModBrewRecipes.strengthBrew, "6a", "6b"),
				new PageBrew(ModBrewRecipes.hasteBrew, "7a", "7b"), new PageBrew(ModBrewRecipes.healingBrew, "8a", "8b"),
				new PageBrew(ModBrewRecipes.jumpBoostBrew, "9a", "9b"), new PageBrew(ModBrewRecipes.regenerationBrew, "10a", "10b"),
				new PageBrew(ModBrewRecipes.weakRegenerationBrew, "17a", "17b"),
				new PageBrew(ModBrewRecipes.resistanceBrew, "11a", "11b"), new PageBrew(ModBrewRecipes.fireResistanceBrew, "12a", "12b"),
				new PageBrew(ModBrewRecipes.waterBreathingBrew, "13a", "13b"), new PageBrew(ModBrewRecipes.invisibilityBrew, "14a", "14b"),
				new PageBrew(ModBrewRecipes.nightVisionBrew, "15a", "15b"), new PageBrew(ModBrewRecipes.absorptionBrew, "16a", "16b"));

		flasks = new ALexiconEntry(LibLexicon.DEVICE_FLASKS, categoryDevices);
		flasks.setLexiconPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", ModCraftingRecipes.recipeFlask));

		redString = new ALexiconEntry(LibLexicon.DEVICE_RED_STRING, categoryDevices);
		redString.setLexiconPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", ModCraftingRecipes.recipeRedString),
				new PageText("3"), new PageCraftingRecipe("4", ModCraftingRecipes.recipeRedStringContainer),
				new PageText("5"), new PageCraftingRecipe("6", ModCraftingRecipes.recipeRedStringDispenser),
				new PageText("7"), new PageCraftingRecipe("8", ModCraftingRecipes.recipeRedStringFertilizer),
				new PageText("9"), new PageCraftingRecipe("10", ModCraftingRecipes.recipeRedStringComparator),
				new PageText("11"), new PageCraftingRecipe("12", ModCraftingRecipes.recipeRedStringRelay));

		// TOOLS ENTRIES
		manaBlaster = new BLexiconEntry(LibLexicon.TOOL_MANA_BLASTER, categoryTools);
		manaBlaster.setLexiconPages(new PageText("0"), new PageText("1"), new PageText("2"), new PageCraftingRecipe("3", ModCraftingRecipes.recipeManaBlaster));

		grassSeeds = new BLexiconEntry(LibLexicon.TOOL_GRASS_SEEDS, categoryTools);
		grassSeeds.setLexiconPages(new PageText("0"), new PageManaInfusionRecipe("1", ModManaInfusionRecipes.grassSeedsRecipe), new PageManaInfusionRecipe("2", ModManaInfusionRecipes.podzolSeedsRecipe), new PageManaInfusionRecipe("3", ModManaInfusionRecipes.mycelSeedsRecipes));

		dirtRod = new BLexiconEntry(LibLexicon.TOOL_DIRT_ROD, categoryTools);
		dirtRod.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeDirtRod));

		terraformRod = new BLexiconEntry(LibLexicon.TOOL_TERRAFORM_ROD, categoryTools);
		terraformRod.setLexiconPages(new PageText("0"), new PageText("1"), new PageText("2"), new PageCraftingRecipe("3", ModCraftingRecipes.recipeTerraformRod));

		manasteelGear = new BLexiconEntry(LibLexicon.TOOL_MANASTEEL_GEAR, categoryTools);
		manasteelGear.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeManasteelPick), new PageCraftingRecipe("2", ModCraftingRecipes.recipeManasteelShovel), new PageCraftingRecipe("3", ModCraftingRecipes.recipeManasteelAxe),
				new PageCraftingRecipe("4", ModCraftingRecipes.recipeManasteelShears), new PageCraftingRecipe("5", ModCraftingRecipes.recipeManasteelSword),
				new PageCraftingRecipe("6", ModCraftingRecipes.recipeManasteelHelm), new PageCraftingRecipe("7", ModCraftingRecipes.recipeManasteelChest), new PageCraftingRecipe("8", ModCraftingRecipes.recipeManasteelLegs), new PageCraftingRecipe("9", ModCraftingRecipes.recipeManasteelBoots));

		terrasteelArmor = new BLexiconEntry(LibLexicon.TOOL_TERRASTEEL_ARMOR, categoryTools);
		terrasteelArmor.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeTerrasteelHelm), new PageCraftingRecipe("2", ModCraftingRecipes.recipeTerrasteelChest), new PageCraftingRecipe("3", ModCraftingRecipes.recipeTerrasteelLegs), new PageCraftingRecipe("4", ModCraftingRecipes.recipeTerrasteelBoots));

		grassHorn = new BLexiconEntry(LibLexicon.TOOL_GRASS_HORN, categoryTools);
		grassHorn.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeGrassHorn), new PageCraftingRecipe("2", ModCraftingRecipes.recipeLeafHorn));

		terraBlade = new BLexiconEntry(LibLexicon.TOOL_TERRA_SWORD, categoryTools);
		terraBlade.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeTerraSword));

		terraPick = new BLexiconEntry(LibLexicon.TOOL_TERRA_PICK, categoryTools);
		terraPick.setLexiconPages(new PageText("0"), new PageText("1"), new PageText("2"), new PageText("3"), new PageText("4"), new PageCraftingRecipe("5", ModCraftingRecipes.recipeTerraPick));

		enderDagger = new BLexiconEntry(LibLexicon.TOOL_ENDER_DAGGER, categoryTools);
		enderDagger.setLexiconPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", ModCraftingRecipes.recipeEnderDagger));

		waterRod = new BLexiconEntry(LibLexicon.TOOL_WATER_ROD, categoryTools);
		waterRod.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeWaterRod));

		elfGear = new ALexiconEntry(LibLexicon.TOOL_ELF_GEAR, categoryTools);
		elfGear.setLexiconPages(new PageText("0"), new PageText("1"), new PageText("2"), new PageCraftingRecipe("3", ModCraftingRecipes.recipeElementiumPick), new PageText("4"), new PageCraftingRecipe("5", ModCraftingRecipes.recipeElementiumShovel),
				new PageText("6"), new PageCraftingRecipe("7", ModCraftingRecipes.recipeElementiumAxe), new PageText("8"), new PageCraftingRecipe("9", ModCraftingRecipes.recipeElementiumShears), new PageText("10"), new PageCraftingRecipe("11", ModCraftingRecipes.recipeElementiumSword),
				new PageCraftingRecipe("12", ModCraftingRecipes.recipeElementiumHelm), new PageCraftingRecipe("13", ModCraftingRecipes.recipeElementiumChest), new PageCraftingRecipe("14", ModCraftingRecipes.recipeElementiumLegs), new PageCraftingRecipe("15", ModCraftingRecipes.recipeElementiumBoots));

		openBucket = new ALexiconEntry(LibLexicon.TOOL_OPEN_BUCKET, categoryTools);
		openBucket.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeOpenBucket));

		spawnerMover = new ALexiconEntry(LibLexicon.TOOL_SPAWNER_MOVER, categoryTools);
		spawnerMover.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeSpawnerMover));

		rainbowRod = new ALexiconEntry(LibLexicon.TOOL_RAINBOW_ROD, categoryTools);
		rainbowRod.setLexiconPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", ModCraftingRecipes.recipeRainbowRod));

		tornadoRod = new BLexiconEntry(LibLexicon.TOOL_TORNADO_ROD, categoryTools);
		tornadoRod.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeTornadoRod));

		fireRod = new BLexiconEntry(LibLexicon.TOOL_FIRE_ROD, categoryTools);
		fireRod.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeFireRod));

		vineBall = new BLexiconEntry(LibLexicon.TOOL_VINE_BALL, categoryTools);
		vineBall.setLexiconPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", ModCraftingRecipes.recipeVineBall), new PageCraftingRecipe("3", ModCraftingRecipes.recipeSlingshot));

		laputaShard = new ALexiconEntry(LibLexicon.TOOL_LAPUTA_SHARD, categoryTools);
		laputaShard.setLexiconPages(new PageText("0"), new PageText("2"), new PageCraftingRecipe("1", ModCraftingRecipes.recipesLaputaShard), new PageCraftingRecipe("3", ModCraftingRecipes.recipesLaputaShardUpgrade));

		virus = new ALexiconEntry(LibLexicon.TOOL_VIRUS, categoryTools);
		virus.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeVirusZombie), new PageCraftingRecipe("2", ModCraftingRecipes.recipeVirusSkeleton));

		skyDirtRod = new ALexiconEntry(LibLexicon.TOOL_SKY_DIRT_ROD, categoryTools);
		skyDirtRod.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeSkyDirtRod));

		enderHand = new BLexiconEntry(LibLexicon.TOOL_ENDER_HAND, categoryTools);
		enderHand.setLexiconPages(new PageText(ConfigHandler.enderPickpocketEnabled ? "0" : "0a"), new PageCraftingRecipe(ConfigHandler.enderPickpocketEnabled ? "1" : "1a", ModCraftingRecipes.recipeEnderHand));

		glassPick = new BLexiconEntry(LibLexicon.TOOL_GLASS_PICK, categoryTools);
		glassPick.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeGlassPick));

		diviningRod = new BLexiconEntry(LibLexicon.TOOL_DIVINING_ROD, categoryTools);
		diviningRod.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeDiviningRod));

		gravityRod = new ALexiconEntry(LibLexicon.TOOL_GRAVITY_ROD, categoryTools);
		gravityRod.setLexiconPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", ModCraftingRecipes.recipeGravityRod));

		regenIvy = new ALexiconEntry(LibLexicon.TOOL_REGEN_IVY, categoryTools);
		regenIvy.setLexiconPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", ModCraftingRecipes.recipeRegenIvy));

		missileRod = new ALexiconEntry(LibLexicon.TOOL_MISSILE_ROD, categoryTools);
		missileRod.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeMissileRod));

		craftingHalo = new BLexiconEntry(LibLexicon.TOOL_CRAFTING_HALO, categoryTools);
		craftingHalo.setLexiconPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", ModCraftingRecipes.recipeCraftingHalo));

		// BAUBLES ENTRIES
		baublesIntro = new BLexiconEntry(LibLexicon.BAUBLE_INTRO, categoryBaubles);
		baublesIntro.setPriority().setLexiconPages(new PageText("0"), new PageImage("1", LibResources.ENTRY_BAUBLES), new PageText("2"));

		tinyPlanet = new BLexiconEntry(LibLexicon.BAUBLE_TINY_PLANET, categoryBaubles);
		tinyPlanet.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeTinyPlanet), new PageCraftingRecipe("2", ModCraftingRecipes.recipeTinyPlanetBlock));

		manaRing = new BLexiconEntry(LibLexicon.BAUBLE_MANA_RING, categoryBaubles);
		manaRing.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeManaRing), new PageCraftingRecipe("2", ModCraftingRecipes.recipeGreaterManaRing));

		auraRing = new BLexiconEntry(LibLexicon.BAUBLE_AURA_RING, categoryBaubles);
		auraRing.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeAuraRing), new PageCraftingRecipe("2", ModCraftingRecipes.recipeGreaterAuraRing));

		travelBelt = new BLexiconEntry(LibLexicon.BAUBLE_TRAVEL_BELT, categoryBaubles);
		travelBelt.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeTravelBelt));

		knockbacklBelt = new BLexiconEntry(LibLexicon.BAUBLE_KNOCKBACK_BELT, categoryBaubles);
		knockbacklBelt.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeKnocbackBelt));

		icePendant = new BLexiconEntry(LibLexicon.BAUBLE_ICE_PENDANT, categoryBaubles);
		icePendant.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeIcePendant));

		lavaPendant = new BLexiconEntry(LibLexicon.BAUBLE_LAVA_PENDANT, categoryBaubles);
		lavaPendant.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeFirePendant));

		goldLaurel = new ALexiconEntry(LibLexicon.BAUBLE_GOLDEN_LAUREL, categoryBaubles);
		goldLaurel.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeGoldenLaurel));

		waterRing = new BLexiconEntry(LibLexicon.BAUBLE_WATER_RING, categoryBaubles);
		waterRing.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeWaterRing));

		miningRing = new BLexiconEntry(LibLexicon.BAUBLE_MINING_RING, categoryBaubles);
		miningRing.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeMiningRing));

		magnetRing = new BLexiconEntry(LibLexicon.BAUBLE_MAGNET_RING, categoryBaubles);
		magnetRing.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeMagnetRing));

		divaCharm = new ALexiconEntry(LibLexicon.BAUBLE_DIVA_CHARM, categoryBaubles);
		divaCharm.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeDivaCharm));

		flightTiara = new ALexiconEntry(LibLexicon.BAUBLE_FLIGHT_TIARA, categoryBaubles);
		flightTiara.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeFlightTiara), new PageText("2"), new PageCraftingRecipe("3", ModCraftingRecipes.recipesWings));

		pixieRing = new ALexiconEntry(LibLexicon.BAUBLE_PIXIE_RING, categoryBaubles);
		pixieRing.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipePixieRing));

		superTravelBelt = new ALexiconEntry(LibLexicon.BAUBLE_SUPER_TRAVEL_BELT, categoryBaubles);
		superTravelBelt.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeSuperTravelBelt));

		reachRing = new ALexiconEntry(LibLexicon.BAUBLE_REACH_RING, categoryBaubles);
		reachRing.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeReachRing));

		itemFinder = new BLexiconEntry(LibLexicon.BAUBLE_ITEM_FINDER, categoryBaubles);
		itemFinder.setLexiconPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", ModCraftingRecipes.recipeItemFinder));

		superLavaPendant = new ALexiconEntry(LibLexicon.BAUBLE_SUPER_LAVA_PENDANT, categoryBaubles);
		superLavaPendant.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeSuperLavaPendant));

		bloodPendant = new BLexiconEntry(LibLexicon.BAUBLE_BLOOD_PENDANT, categoryBaubles);
		bloodPendant.setLexiconPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", ModCraftingRecipes.recipeBloodPendant));

		judgementCloaks = new ALexiconEntry(LibLexicon.BAUBLE_JUDGEMENT_CLOAKS, categoryBaubles);
		judgementCloaks.setLexiconPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", ModCraftingRecipes.recipeHolyCloak), new PageCraftingRecipe("3", ModCraftingRecipes.recipeUnholyCloak));

		// ALFHOMANCY ENTRIES
		alfhomancyIntro = new BLexiconEntry(LibLexicon.ALF_INTRO, categoryAlfhomancy);
		alfhomancyIntro.setPriority().setLexiconPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", ModCraftingRecipes.recipeAlfPortal), new PageCraftingRecipe("3", ModCraftingRecipes.recipeNaturaPylon),
				new PageImage("4", LibResources.ENTRY_PORTAL0), new PageImage("5", LibResources.ENTRY_PORTAL1), new PageImage("6", LibResources.ENTRY_PORTAL2), new PageImage("7", LibResources.ENTRY_PORTAL3),
				new PageImage("8", LibResources.ENTRY_PORTAL4), new PageText("9"), new PageText("10"), new PageText("11"));

		elvenMessage = new ALexiconEntry(LibLexicon.ALF_MESSAGE, categoryAlfhomancy);
		elvenMessage.setPriority().setLexiconPages(new PageImage("0", LibResources.ENTRY_ELVEN_GARDE), new PageLoreText("1"), new PageLoreText("2"), new PageLoreText("3"), new PageLoreText("4"), new PageLoreText("5"), new PageLoreText("6"));

		elvenResources = new ALexiconEntry(LibLexicon.ALF_RESOURCES, categoryAlfhomancy);
		elvenResources.setPriority().setLexiconPages(new PageText("0"), new PageElvenRecipe("1", ModElvenTradeRecipes.dreamwoodRecipe), new PageText("2"), new PageElvenRecipe("3", ModElvenTradeRecipes.elementiumRecipes), new PageElvenRecipe("4", ModElvenTradeRecipes.pixieDustRecipe), new PageElvenRecipe("5", ModElvenTradeRecipes.dragonstoneRecipe), new PageText("6"), new PageElvenRecipe("7", ModElvenTradeRecipes.elvenQuartzRecipe), new PageText("8"), new PageElvenRecipe("9", ModElvenTradeRecipes.alfglassRecipe));

		gaiaRitual = new ALexiconEntry(LibLexicon.ALF_GAIA_RITUAL, categoryAlfhomancy);
		gaiaRitual.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeGaiaPylon), new PageImage("2", LibResources.ENTRY_GAIA_RITUAL), new PageText("3"), new PageText("4"));
		LexiconRecipeMappings.map(new ItemStack(ModItems.manaResource, 1, 5), gaiaRitual, 0);

		elvenLore = new ALexiconEntry(LibLexicon.ALF_LORE, categoryAlfhomancy);
		elvenLore.setLexiconPages(new PageText("0"), new PageLoreText("1"), new PageLoreText("2"), new PageLoreText("3"), new PageLoreText("4"), new PageLoreText("5"), new PageLoreText("6"), new PageLoreText("7"));

		// MISCLENAEOUS ENTRIES
		unstableBlocks = new BLexiconEntry(LibLexicon.MISC_UNSTABLE_BLOCKS, categoryMisc);
		unstableBlocks.setLexiconPages(new PageText("0"), new PageImage("1", LibResources.ENTRY_UNSTABLE_BLOCK), new PageCraftingRecipe("2", ModCraftingRecipes.recipesUnstableBlocks), new PageText("3"), new PageImage("4", LibResources.ENTRY_UNSTABLE_BEACON), new PageCraftingRecipe("5", ModCraftingRecipes.recipesManaBeacons), new PageText("6"), new PageCraftingRecipe("7", ModCraftingRecipes.recipesSignalFlares));

		decorativeBlocks = new BLexiconEntry(LibLexicon.MISC_DECORATIVE_BLOCKS, categoryMisc);
		if(ConfigHandler.darkQuartzEnabled)
			decorativeBlocks.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeLivingrockDecor1), new PageCraftingRecipe("2", ModCraftingRecipes.recipeLivingrockDecor2), new PageCraftingRecipe("3", ModCraftingRecipes.recipeLivingrockDecor3), new PageCraftingRecipe("4", ModCraftingRecipes.recipeLivingrockDecor4),
					new PageCraftingRecipe("5", ModCraftingRecipes.recipeLivingwoodDecor1), new PageCraftingRecipe("6", ModCraftingRecipes.recipeLivingwoodDecor2), new PageCraftingRecipe("7", ModCraftingRecipes.recipeLivingwoodDecor3), new PageCraftingRecipe("8", ModCraftingRecipes.recipeLivingwoodDecor4), new PageCraftingRecipe("9", ModCraftingRecipes.recipeLivingwoodDecor5),
					new PageText("10"), new PageCraftingRecipe("11", ModCraftingRecipes.recipeDarkQuartz), new PageManaInfusionRecipe("12", ModManaInfusionRecipes.manaQuartzRecipe), new PageCraftingRecipe("13", ModCraftingRecipes.recipeBlazeQuartz), new PageCraftingRecipe("14", ModCraftingRecipes.recipesLavenderQuartz), new PageCraftingRecipe("15", ModCraftingRecipes.recipeRedQuartz),
					new PageText("16"), new PageCraftingRecipe("17", ModCraftingRecipes.recipeReedBlock), new PageCraftingRecipe("18", ModCraftingRecipes.recipeThatch), new PageCraftingRecipe("19", ModCraftingRecipes.recipeRoofTile), new PageCraftingRecipe("20", ModCraftingRecipes.recipeNetherBrick), new PageCraftingRecipe("21", ModCraftingRecipes.recipeSoulBrick), new PageCraftingRecipe("22", ModCraftingRecipes.recipeSnowBrick));
		else decorativeBlocks.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeLivingrockDecor1), new PageCraftingRecipe("2", ModCraftingRecipes.recipeLivingrockDecor2), new PageCraftingRecipe("3", ModCraftingRecipes.recipeLivingrockDecor3), new PageCraftingRecipe("4", ModCraftingRecipes.recipeLivingrockDecor4),
				new PageCraftingRecipe("5", ModCraftingRecipes.recipeLivingwoodDecor1), new PageCraftingRecipe("6", ModCraftingRecipes.recipeLivingwoodDecor2), new PageCraftingRecipe("7", ModCraftingRecipes.recipeLivingwoodDecor3), new PageCraftingRecipe("8", ModCraftingRecipes.recipeLivingwoodDecor4), new PageCraftingRecipe("9", ModCraftingRecipes.recipeLivingwoodDecor5),
				new PageText("10"), new PageManaInfusionRecipe("12", ModManaInfusionRecipes.manaQuartzRecipe), new PageCraftingRecipe("13", ModCraftingRecipes.recipeBlazeQuartz), new PageCraftingRecipe("14", ModCraftingRecipes.recipesLavenderQuartz), new PageCraftingRecipe("15", ModCraftingRecipes.recipeRedQuartz),
				new PageText("16"), new PageCraftingRecipe("17", ModCraftingRecipes.recipeReedBlock), new PageCraftingRecipe("18", ModCraftingRecipes.recipeThatch), new PageCraftingRecipe("19", ModCraftingRecipes.recipeRoofTile), new PageCraftingRecipe("20", ModCraftingRecipes.recipeNetherBrick), new PageCraftingRecipe("21", ModCraftingRecipes.recipeSoulBrick), new PageCraftingRecipe("22", ModCraftingRecipes.recipeSnowBrick));

		dispenserTweaks = new BLexiconEntry(LibLexicon.MISC_DISPENSER_TWEAKS, categoryMisc);
		dispenserTweaks.setLexiconPages(new PageText("0"));

		shinyFlowers = new BLexiconEntry(LibLexicon.MISC_SHINY_FLOWERS, categoryMisc);
		shinyFlowers.setLexiconPages(new PageText("0"), new PageText("3"), new PageCraftingRecipe("1", ModCraftingRecipes.recipesShinyFlowers), new PageCraftingRecipe("2", ModCraftingRecipes.recipesMiniIsland));

		prismarine = new BLexiconEntry(LibLexicon.MISC_PRISMARINE, categoryMisc);
		prismarine.setLexiconPages(new PageText("0"), new PageText("1"), new PageManaInfusionRecipe("2", ModManaAlchemyRecipes.prismarineRecipe), new PageCraftingRecipe("3", ModCraftingRecipes.recipePrismarine), new PageCraftingRecipe("4", ModCraftingRecipes.recipePrismarineBrick), new PageCraftingRecipe("5", ModCraftingRecipes.recipeDarkPrismarine), new PageCraftingRecipe("6", ModCraftingRecipes.recipeSeaLamp));

		tinyPotato = new BLexiconEntry(LibLexicon.MISC_TINY_POTATO, categoryMisc);
		tinyPotato.setLexiconPages(new PageText("0"), new PageManaInfusionRecipe("1", ModManaInfusionRecipes.tinyPotatoRecipe));

		headCreating = new ALexiconEntry(LibLexicon.MISC_HEAD_CREATING, categoryMisc);
		headCreating.setLexiconPages(new PageText("0"), new PageRuneRecipe("1", ModRuneRecipes.recipeHead));

		azulejo = new BLexiconEntry(LibLexicon.MISC_AZULEJO, categoryMisc);
		azulejo.setLexiconPages(new PageText("0"), new PageImage("1", LibResources.ENTRY_AZULEJOS), new PageCraftingRecipe("2", ModCraftingRecipes.recipeAzulejo), new PageCraftingRecipe("3", ModCraftingRecipes.recipesAzulejoCycling));

		starfield = new ALexiconEntry(LibLexicon.MISC_STARFIELD, categoryMisc);
		starfield.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModCraftingRecipes.recipeStarfield));

		if(Botania.bcTriggersLoaded) {
			bcIntegration = new BLexiconEntry(LibLexicon.MISC_BC_INTEGRATION, categoryMisc);
			bcIntegration.setLexiconPages(new PageText("0"));
		}
		*/
	}

	public static void postInit() {
	}
}

