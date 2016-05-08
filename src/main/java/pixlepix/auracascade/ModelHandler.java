package pixlepix.auracascade;

import java.util.Locale;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import pixlepix.auracascade.block.AuraBlock;
import pixlepix.auracascade.block.AuraBlockCapacitor;
import pixlepix.auracascade.block.BlockBookshelfCoordinator;
import pixlepix.auracascade.block.BlockExplosionContainer;
import pixlepix.auracascade.block.BlockMagicRoad;
import pixlepix.auracascade.block.BlockMonitor;
import pixlepix.auracascade.block.BlockStorageBookshelf;
import pixlepix.auracascade.block.BlockTrampoline;
import pixlepix.auracascade.block.ConsumerBlock;
import pixlepix.auracascade.block.FairyTorch;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.item.AngelsteelToolHelper;
import pixlepix.auracascade.item.ItemAngelJump;
import pixlepix.auracascade.item.ItemAngelStep;
import pixlepix.auracascade.item.ItemAngelsteelAxe;
import pixlepix.auracascade.item.ItemAngelsteelIngot;
import pixlepix.auracascade.item.ItemAngelsteelPickaxe;
import pixlepix.auracascade.item.ItemAngelsteelShovel;
import pixlepix.auracascade.item.ItemAngelsteelSword;
import pixlepix.auracascade.item.ItemAuraCrystal;
import pixlepix.auracascade.item.ItemBlackHole;
import pixlepix.auracascade.item.ItemBlueAmulet;
import pixlepix.auracascade.item.ItemComboSword;
import pixlepix.auracascade.item.ItemExplosionRing;
import pixlepix.auracascade.item.ItemFairyCharm;
import pixlepix.auracascade.item.ItemFairyRing;
import pixlepix.auracascade.item.ItemFoodAmulet;
import pixlepix.auracascade.item.ItemGreenAmulet;
import pixlepix.auracascade.item.ItemLexicon;
import pixlepix.auracascade.item.ItemMaterial;
import pixlepix.auracascade.item.ItemMirror;
import pixlepix.auracascade.item.ItemOrangeAmulet;
import pixlepix.auracascade.item.ItemPrismaticWand;
import pixlepix.auracascade.item.ItemPurpleAmulet;
import pixlepix.auracascade.item.ItemRedAmulet;
import pixlepix.auracascade.item.ItemRedHole;
import pixlepix.auracascade.item.ItemStorageBook;
import pixlepix.auracascade.item.ItemThiefSword;
import pixlepix.auracascade.item.ItemTransmutingSword;
import pixlepix.auracascade.item.ItemYellowAmulet;
import pixlepix.auracascade.item.books.BasicStorageBook;
import pixlepix.auracascade.item.books.DenseStorageBook;
import pixlepix.auracascade.item.books.ExtremelyDenseStorageBook;
import pixlepix.auracascade.item.books.ExtremelyLightStorageBook;
import pixlepix.auracascade.item.books.FarmingStorageBook;
import pixlepix.auracascade.item.books.LightStorageBook;
import pixlepix.auracascade.item.books.MineralStorageBook;
import pixlepix.auracascade.item.books.MobStorageBook;
import pixlepix.auracascade.item.books.ModStorageBook;
import pixlepix.auracascade.item.books.SuperDenseStorageBook;
import pixlepix.auracascade.item.books.SuperLightStorageBook;
import pixlepix.auracascade.item.books.VeryDenseStorageBook;
import pixlepix.auracascade.item.books.VeryLightStorageBook;
import pixlepix.auracascade.registry.BlockRegistry;

public final class ModelHandler {

    // I know this is very hardcoded right now, but it's the fastest to get up and running
    public static void registerModels(){
        registerAngelSteelIngots();
        registerAngelSteelTools();
        registerFairyCharms();
        registerMulticolors();
        registerBooks();
        registerAmulets();

        Item fairyTorchItem = Item.getItemFromBlock(BlockRegistry.getFirstBlockFromClass(FairyTorch.class));
      //  ModelLoader.setCustomMeshDefinition(fairyTorchItem, ("minecraft:barrier"));
        //TODO CustomMeshDefinitions
       // ModelLoader.setCustomMeshDefinition(fairyTorchItem, (ItemMeshDefinition) new ResourceLocation("minecraft:barrier"));
       // ModelLoader.setCustomMeshDefinition(fairyTorchItem, );
        ModelLoader.setCustomModelResourceLocation(fairyTorchItem, 0, new ModelResourceLocation("minecraft:barrier", "inventory"));

        registerItem(BlockRegistry.getFirstItemFromClass(ItemMirror.class));
        registerItem(BlockRegistry.getFirstItemFromClass(ItemFairyRing.class));
        registerItem(BlockRegistry.getFirstItemFromClass(ItemExplosionRing.class));
        registerItem(BlockRegistry.getFirstItemFromClass(ItemBlackHole.class));
        registerItem(BlockRegistry.getFirstItemFromClass(ItemRedHole.class));
        registerItem(BlockRegistry.getFirstItemFromClass(ItemPrismaticWand.class));
        registerItem(BlockRegistry.getFirstItemFromClass(ItemThiefSword.class));
        registerItem(BlockRegistry.getFirstItemFromClass(ItemComboSword.class));
        registerItem(BlockRegistry.getFirstItemFromClass(ItemTransmutingSword.class));
        registerItem(BlockRegistry.getFirstItemFromClass(ItemLexicon.class));

        // ItemBlocks
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(AuraBlock.class, "auraNode")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(AuraBlock.class, "auraNodepumpCreative")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(AuraBlock.class, "auraNodepump")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(AuraBlock.class, "auraNodepumpLight")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(AuraBlock.class, "auraNodepumpFall")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(AuraBlock.class, "auraNodepumpProjectile")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(AuraBlock.class, "auraNodepumpRedstone")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(AuraBlockCapacitor.class, "auraNodecapacitor")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(AuraBlock.class, "auraNodeblack")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(AuraBlock.class, "auraNodeorange")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(AuraBlock.class, "auraNodeconserve")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(AuraBlock.class, "auraNodeflux")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(AuraBlock.class, "auraNodecraftingCenter")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(AuraBlock.class, "auraNodecraftingPedestal")));

        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(AuraBlock.class, "auraNodepumpAlt")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(AuraBlock.class, "auraNodepumpLightAlt")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(AuraBlock.class, "auraNodepumpFallAlt")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(AuraBlock.class, "auraNodepumpProjectileAlt")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(AuraBlock.class, "auraNodepumpRedstoneAlt")));

        registerItem(Item.getItemFromBlock(BlockRegistry.getFirstBlockFromClass(BlockMagicRoad.class)));
        registerItem(Item.getItemFromBlock(BlockRegistry.getFirstBlockFromClass(BlockTrampoline.class)));
        registerItem(Item.getItemFromBlock(BlockRegistry.getFirstBlockFromClass(BlockStorageBookshelf.class)));
        registerItem(Item.getItemFromBlock(BlockRegistry.getFirstBlockFromClass(BlockBookshelfCoordinator.class)));

        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(BlockExplosionContainer.class, "fortifiedWood")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(BlockExplosionContainer.class, "fortifiedGlass")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(BlockExplosionContainer.class, "fortifiedCobblestone")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(BlockExplosionContainer.class, "fortifiedStone")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(BlockExplosionContainer.class, "fortifiedObsidian")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(BlockExplosionContainer.class, "fortifiedDirt")));

        registerItem(Item.getItemFromBlock(BlockRegistry.getFirstBlockFromClass(BlockMonitor.class)));

        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(ConsumerBlock.class, "consumerBlockpotion")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(ConsumerBlock.class, "consumerBlockdye")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(ConsumerBlock.class, "consumerBlockplant")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(ConsumerBlock.class, "consumerBlockfish")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(ConsumerBlock.class, "consumerBlockloot")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(ConsumerBlock.class, "consumerBlockminer")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(ConsumerBlock.class, "consumerBlockore")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(ConsumerBlock.class, "consumerBlockoreAdv")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(ConsumerBlock.class, "consumerBlockenchant")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(ConsumerBlock.class, "consumerBlockmob")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(ConsumerBlock.class, "consumerBlockangel")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(ConsumerBlock.class, "consumerBlockfurnace")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(ConsumerBlock.class, "consumerBlockend")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(ConsumerBlock.class, "consumerBlocknether")));

    }

    private static void registerAngelSteelIngots() {
        Item item = BlockRegistry.getFirstItemFromClass(ItemAngelsteelIngot.class);
        ResourceLocation l = ForgeRegistries.ITEMS.getKey(item);
        for (int i = 0; i < AngelsteelToolHelper.MAX_DEGREE; i++) {
            ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(l, "inventory"));
        }
    }

    private static void registerAngelSteelTools() {
        for (Item i : BlockRegistry.getItemFromClass(ItemAngelsteelAxe.class)) {
            ModelLoader.setCustomModelResourceLocation(i, 0, new ModelResourceLocation("aura:angelsteelAxe", "inventory"));
        }
        for (Item i : BlockRegistry.getItemFromClass(ItemAngelsteelShovel.class)) {
            ModelLoader.setCustomModelResourceLocation(i, 0, new ModelResourceLocation("aura:angelsteelShovel", "inventory"));
        }
        for (Item i : BlockRegistry.getItemFromClass(ItemAngelsteelPickaxe.class)) {
            ModelLoader.setCustomModelResourceLocation(i, 0, new ModelResourceLocation("aura:angelsteelPickaxe", "inventory"));
        }

        for (Item i : BlockRegistry.getItemFromClass(ItemAngelsteelSword.class)) {
           // ModelLoader.addVariantName(i, "aura:angel_sword", "aura:angel_swordBlue", "aura:angel_swordGreen",
           //         "aura:angel_swordOrange", "aura:angel_swordRed", "aura:angel_swordViolet", "aura:angel_swordYellow");
            ModelLoader.registerItemVariants(i, new ResourceLocation("Aura Swords Filler name"));
            ModelLoader.setCustomMeshDefinition(i, new ItemMeshDefinition() {
                @Override
                public ModelResourceLocation getModelLocation(ItemStack stack) {
                    switch (ItemAngelsteelSword.getAura(stack)) {
                        case WHITE_AURA: return new ModelResourceLocation("aura:angel_sword", "inventory");
                        case GREEN_AURA: return new ModelResourceLocation("aura:angel_swordGreen", "inventory");
                        case ORANGE_AURA: return new ModelResourceLocation("aura:angel_swordOrange", "inventory");
                        case YELLOW_AURA: return new ModelResourceLocation("aura:angel_swordYellow", "inventory");
                        case BLUE_AURA: return new ModelResourceLocation("aura:angel_swordBlue", "inventory");
                        case VIOLET_AURA: return new ModelResourceLocation("aura:angel_swordViolet", "inventory");
                        case RED_AURA:
                        default: return new ModelResourceLocation("aura:angel_swordRed", "inventory");
                    }
                }
            });
        }
    }

    private static void registerFairyCharms() {
        Item item = BlockRegistry.getFirstItemFromClass(ItemFairyCharm.class);
        ResourceLocation l = ForgeRegistries.ITEMS.getKey(item);
        for (int i = 0; i < ItemFairyCharm.fairyClasses.length; i++) {
            ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(l, "inventory"));
        }
        ModelLoader.setCustomModelResourceLocation(item, 100, new ModelResourceLocation("aura:fairy_plain", "inventory"));
    }

    private static void registerMulticolors() {
        Item crystal = BlockRegistry.getFirstItemFromClass(ItemAuraCrystal.class);
        for (int i = 0; i < EnumAura.values().length; i++) {
            String name = "aura:" + EnumAura.values()[i].name.toLowerCase(Locale.ROOT) + "Crystal";
            ModelLoader.setCustomModelResourceLocation(crystal, i, new ModelResourceLocation(name, "inventory"));
        }

        for (Item i : BlockRegistry.getItemFromClass(ItemMaterial.class)) {
            registerItem(i);
        }
    }

    private static void registerBooks() {
        @SuppressWarnings("unchecked")
		Set<Class<? extends ItemStorageBook>> classes = ImmutableSet.of(BasicStorageBook.class, DenseStorageBook.class, ExtremelyDenseStorageBook.class,
                ExtremelyLightStorageBook.class, FarmingStorageBook.class, LightStorageBook.class, MineralStorageBook.class, MobStorageBook.class,
                ModStorageBook.class, SuperDenseStorageBook.class, SuperLightStorageBook.class, VeryDenseStorageBook.class, VeryLightStorageBook.class);

        for (Class<? extends ItemStorageBook> clazz : classes) {
            Item item = BlockRegistry.getFirstItemFromClass(clazz);
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation("aura:storageBook", "inventory"));
        }
    }

    private static void registerAmulets() {
        registerItem(BlockRegistry.getFirstItemFromClass(ItemBlueAmulet.class));
        registerItem(BlockRegistry.getFirstItemFromClass(ItemFoodAmulet.class));
        registerItem(BlockRegistry.getFirstItemFromClass(ItemGreenAmulet.class));
        registerItem(BlockRegistry.getFirstItemFromClass(ItemOrangeAmulet.class));
        registerItem(BlockRegistry.getFirstItemFromClass(ItemPurpleAmulet.class));
        registerItem(BlockRegistry.getFirstItemFromClass(ItemRedAmulet.class));
        registerItem(BlockRegistry.getFirstItemFromClass(ItemYellowAmulet.class));
        registerItem(BlockRegistry.getFirstItemFromClass(ItemAngelJump.class));
        registerItem(BlockRegistry.getFirstItemFromClass(ItemAngelStep.class));
    }

    private static void registerItem(Item item) {
        if (item == null) {
            return;
        }

        ResourceLocation l = ForgeRegistries.ITEMS.getKey(item);

        if (l == null) {
            return;
        }

        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(l, "inventory"));
    }

    private ModelHandler() {}
}
