package pixlepix.auracascade;

import com.google.common.collect.ImmutableSet;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import pixlepix.auracascade.block.*;
import pixlepix.auracascade.data.EnumRainbowColor;
import pixlepix.auracascade.item.*;
import pixlepix.auracascade.registry.BlockRegistry;

import java.util.Locale;
import java.util.Set;

public final class ModelHandler {

    // I know this is very hardcoded right now, but it's the fastest to get up and running
    public static void registerModels(){
        registerAngelSteelIngots();
        registerAngelSteelTools();
        registerFairyCharms();
        registerMulticolors();
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
        registerItem(BlockRegistry.getFirstItemFromClass(ItemAuraCrystal.class));

        // ItemBlocks
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(AuraBlock.class, "auraNode")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(AuraBlock.class, "auraNodepumpCreative")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(AuraBlock.class, "auraNodepump")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(AuraBlock.class, "auraNodepumpLight")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(AuraBlock.class, "auraNodepumpFall")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(AuraBlock.class, "auraNodepumpProjectile")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(AuraBlock.class, "auraNodepumpRedstone")));
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(AuraBlockCapacitor.class, "auraNodecapacitor")));
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
            assert item != null;
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
            ModelLoader.registerItemVariants(i, new ResourceLocation("aura:angel_sword"), new ResourceLocation(("aura:angel_sword")),  new ResourceLocation("aura:angel_swordGreen"),  new ResourceLocation("aura:angel_swordOrange")
                    , new ResourceLocation("aura:angel_swordRed"), new ResourceLocation("aura:angel_swordViolet"), new ResourceLocation("aura:angel_swordYellow"), new ResourceLocation("aura:angel_swordBlue"));
            ModelLoader.setCustomMeshDefinition(i, new ItemMeshDefinition() {
                @Override
                public ModelResourceLocation getModelLocation(ItemStack stack) {
                    switch (ItemAngelsteelSword.getAura(stack)) {
                        case WHITE: return new ModelResourceLocation("aura:angel_sword", "inventory");
                        case GREEN: return new ModelResourceLocation("aura:angel_swordGreen", "inventory");
                        case ORANGE: return new ModelResourceLocation("aura:angel_swordOrange", "inventory");
                        case YELLOW: return new ModelResourceLocation("aura:angel_swordYellow", "inventory");
                        case BLUE: return new ModelResourceLocation("aura:angel_swordBlue", "inventory");
                        case VIOLET: return new ModelResourceLocation("aura:angel_swordViolet", "inventory");
                        case RED:
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
            assert item != null;
            ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(l, "inventory"));
        }
        ModelLoader.setCustomModelResourceLocation(item, 100, new ModelResourceLocation("aura:fairy_plain", "inventory"));
    }

    private static void registerMulticolors() {
        for (Item i : BlockRegistry.getItemFromClass(ItemMaterial.class)) {
            registerItem(i);
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
