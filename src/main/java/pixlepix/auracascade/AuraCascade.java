package pixlepix.auracascade;


import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import pixlepix.auracascade.main.CommonProxy;
import pixlepix.auracascade.main.ConstantMod;

@Mod(modid = ConstantMod.modId, name = ConstantMod.modName, version = ConstantMod.version, dependencies = "required-after:Baubles")
public class AuraCascade {

    @Instance(ConstantMod.modId)
    public static AuraCascade instance;

    @SidedProxy(clientSide = ConstantMod.clientProxy, serverSide = ConstantMod.commonProxy)
    public static CommonProxy proxy;
    public static Logger log;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        log = event.getModLog();
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);

    }
}
