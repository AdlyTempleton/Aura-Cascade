package pixlepix.auracascade.main;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.world.World;
import pixlepix.auracascade.network.PacketBurst;
import pixlepix.auracascade.registry.BlockRegistry;
import pixlepix.auracascade.registry.ModCreativeTab;
import sun.org.mozilla.javascript.internal.ast.Block;

public class CommonProxy {

    public static BlockRegistry registry;
    public static SimpleNetworkWrapper networkWrapper;

    public void preInit(FMLPreInitializationEvent event){
        ModCreativeTab.INSTANCE = new ModCreativeTab();
        registry = new BlockRegistry();
        registry.preInit();
        networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(ConstantMod.modId);
        networkWrapper.registerMessage(PacketBurst.class, PacketBurst.class, 0, Side.CLIENT);

    }
    public void init(FMLInitializationEvent event){
        registry.init();
    }
    public void postInit(FMLPostInitializationEvent event){
        registry.postInit();
    }

}
