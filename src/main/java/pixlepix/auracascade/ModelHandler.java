package pixlepix.auracascade;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameData;
import pixlepix.auracascade.block.AuraBlock;
import pixlepix.auracascade.block.AuraBlockCapacitor;
import pixlepix.auracascade.block.BlockBookshelfCoordinator;
import pixlepix.auracascade.block.BlockMagicRoad;
import pixlepix.auracascade.block.BlockStorageBookshelf;
import pixlepix.auracascade.block.BlockTrampoline;
import pixlepix.auracascade.registry.BlockRegistry;

public final class ModelHandler {

    public static void registerModels() {
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

    }

    private static void registerItem(Item item) {
        ResourceLocation l = GameData.getItemRegistry().getNameForObject(item);
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(l, "inventory"));
    }

    private ModelHandler() {}
}
