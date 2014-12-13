package pixlepix.auracascade.main;

import baubles.api.BaublesApi;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.EntityTracker;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.block.entity.EntityFairy;
import pixlepix.auracascade.data.recipe.PylonRecipeRegistry;
import pixlepix.auracascade.network.PacketBurst;
import pixlepix.auracascade.network.PacketFairyRequestUpdate;
import pixlepix.auracascade.network.PacketFairyUpdate;
import pixlepix.auracascade.registry.BlockRegistry;
import pixlepix.auracascade.registry.ModCreativeTab;
import sun.org.mozilla.javascript.internal.ast.Block;

public class CommonProxy {

    public BlockRegistry registry;
    public SimpleNetworkWrapper networkWrapper;

    public void preInit(FMLPreInitializationEvent event){
        ModCreativeTab.INSTANCE = new ModCreativeTab();
        registry = new BlockRegistry();
        registry.preInit();
        networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(ConstantMod.modId);
        networkWrapper.registerMessage(PacketBurst.class, PacketBurst.class, 0, Side.CLIENT);

        networkWrapper.registerMessage(PacketFairyUpdate.class, PacketFairyUpdate.class, 1, Side.CLIENT);
        networkWrapper.registerMessage(PacketFairyRequestUpdate.class, PacketFairyRequestUpdate.class, 0, Side.SERVER);

    }

    public World getWorld(){
        return null;
    }

    public void init(FMLInitializationEvent event){
        registry.init();
        PylonRecipeRegistry.init();
        EntityRegistry.registerModEntity(EntityFairy.class, "Fairy", 0, AuraCascade.instance, 20, 500, true);


    }
    public void postInit(FMLPostInitializationEvent event){
        registry.postInit();
    }

}
