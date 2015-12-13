package pixlepix.auracascade;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameData;
import pixlepix.auracascade.block.AuraBlock;
import pixlepix.auracascade.registry.BlockRegistry;

public final class ModelHandler {

    public static void registerModels() {
        registerItem(Item.getItemFromBlock(BlockRegistry.getBlockFromClassAndName(AuraBlock.class, "auraNode")));
    }

    private static void registerItem(Item item) {
        ResourceLocation l = GameData.getItemRegistry().getNameForObject(item);
        System.out.println(l);
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(l, "inventory"));
    }

    private ModelHandler() {}
}
