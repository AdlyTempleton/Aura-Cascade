package pixlepix.auracascade.main;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

/**
 * Created by localmacaccount on 1/12/15.
 */
public class Config {

    public static int pumpCoalSpeed = 200;
    public static int pumpCoalDuration = 1;
    public static int pumpRedstoneSpeed = 10;
    public static int pumpRedstoneDuration = 1500;
    public static int pumpTorchSpeed = 750;
    public static int pumpTorchDuration = 30;
    public static int pumpGlowstoneSpeed = 750;
    public static int pumpGlowstoneDuration = 180;
    public static int pumpFallSpeed = 250;
    public static int pumpFallDuration = 2;
    public static int pumpEggSpeed = 100;
    public static int pumpEggDuration = 90;
    public static int pumpArrowSpeed = 1000;
    public static int pumpArrowDuration = 20;
    public static int pumpSnowballSpeed = 400;
    public static int pumpSnowballDuration = 10;
    static Configuration config;

    public static void init(FMLPreInitializationEvent event) {
        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();

        pumpCoalSpeed = config.getInt("pumpCoalSpeed", Configuration.CATEGORY_GENERAL, pumpCoalSpeed, 1, Integer.MAX_VALUE, "");
        pumpCoalDuration = config.getInt("pumpCoalDuration", Configuration.CATEGORY_GENERAL, pumpCoalDuration, 1, Integer.MAX_VALUE, "");

        pumpRedstoneSpeed = config.getInt("pumpRedstoneSpeed", Configuration.CATEGORY_GENERAL, pumpRedstoneSpeed, 1, Integer.MAX_VALUE, "");
        pumpRedstoneDuration = config.getInt("pumpRedstoneDuration", Configuration.CATEGORY_GENERAL, pumpRedstoneDuration, 1, Integer.MAX_VALUE, "");

        pumpFallSpeed = config.getInt("pumpFallSpeed", Configuration.CATEGORY_GENERAL, pumpFallSpeed, 1, Integer.MAX_VALUE, "");
        pumpFallDuration = config.getInt("pumpFallDuration", Configuration.CATEGORY_GENERAL, pumpFallDuration, 1, Integer.MAX_VALUE, "");

        pumpTorchSpeed = config.getInt("pumpTorchSpeed", Configuration.CATEGORY_GENERAL, pumpTorchSpeed, 1, Integer.MAX_VALUE, "");
        pumpTorchDuration = config.getInt("pumpTorchDuration", Configuration.CATEGORY_GENERAL, pumpTorchDuration, 1, Integer.MAX_VALUE, "");

        pumpGlowstoneSpeed = config.getInt("pumpGlowstoneSpeed", Configuration.CATEGORY_GENERAL, pumpGlowstoneSpeed, 1, Integer.MAX_VALUE, "");
        pumpGlowstoneDuration = config.getInt("pumpGlowstoneDuration", Configuration.CATEGORY_GENERAL, pumpGlowstoneDuration, 1, Integer.MAX_VALUE, "");

        pumpEggSpeed = config.getInt("pumpEggSpeed", Configuration.CATEGORY_GENERAL, pumpEggSpeed, 1, Integer.MAX_VALUE, "");
        pumpEggDuration = config.getInt("pumpEggDuration", Configuration.CATEGORY_GENERAL, pumpEggDuration, 1, Integer.MAX_VALUE, "");

        pumpSnowballSpeed = config.getInt("pumpSnowballSpeed", Configuration.CATEGORY_GENERAL, pumpSnowballSpeed, 1, Integer.MAX_VALUE, "");
        pumpSnowballDuration = config.getInt("pumpSnowballDuration", Configuration.CATEGORY_GENERAL, pumpSnowballDuration, 1, Integer.MAX_VALUE, "");

        pumpArrowSpeed = config.getInt("pumpArrowSpeed", Configuration.CATEGORY_GENERAL, pumpArrowSpeed, 1, Integer.MAX_VALUE, "");
        pumpArrowDuration = config.getInt("pumpArrowDuration", Configuration.CATEGORY_GENERAL, pumpArrowDuration, 1, Integer.MAX_VALUE, "");

        config.save();
    }
}
