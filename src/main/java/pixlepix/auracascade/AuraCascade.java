package pixlepix.auracascade;


import de.npe.gameanalytics.SimpleAnalytics;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import pixlepix.auracascade.compat.IMCManager;
import pixlepix.auracascade.main.CommonProxy;
import pixlepix.auracascade.main.ConstantMod;

@Mod(modid = ConstantMod.modId, name = ConstantMod.modName, version = ConstantMod.version, dependencies = "required-after:Baubles", acceptedMinecraftVersions = "[1.9.0]")
public class AuraCascade {

    public static SimpleAnalytics analytics;
    
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

    @EventHandler
    public void onIMC(FMLInterModComms.IMCEvent event) {
        IMCManager.onIMC(event);

    }
}
