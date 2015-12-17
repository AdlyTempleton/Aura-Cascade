package pixlepix.auracascade.main.compat;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.registry.GameData;
import pixlepix.auracascade.block.AuraBlock;
import pixlepix.auracascade.block.AuraBlockCapacitor;
import pixlepix.auracascade.registry.BlockRegistry;
import thaumcraft.api.ThaumcraftApi;

/**
 * Created by localmacaccount on 2/27/15.
 */
public class TCCompat {

    public static void postInit() {
        for (Block b : BlockRegistry.getBlockFromClass(AuraBlock.class)) {
            FMLInterModComms.sendMessage("thaumcraft", "portableHoleBlacklist", GameData.getBlockRegistry().getNameForObject(b).toString());
        }

        for (Block b : BlockRegistry.getBlockFromClass(AuraBlockCapacitor.class)) {
            FMLInterModComms.sendMessage("thaumcraft", "portableHoleBlacklist", GameData.getBlockRegistry().getNameForObject(b).toString());
        }
    }
}
