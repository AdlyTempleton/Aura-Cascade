package pixlepix.auracascade.main;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.block.entity.EntityFairy;
import pixlepix.auracascade.data.CoordTuple;
import pixlepix.auracascade.data.recipe.PylonRecipeRegistry;
import pixlepix.auracascade.item.AngelsteelToolHelper;
import pixlepix.auracascade.lexicon.LexiconData;
import pixlepix.auracascade.lexicon.LexiconEntry;
import pixlepix.auracascade.network.*;
import pixlepix.auracascade.potions.PotionManager;
import pixlepix.auracascade.registry.BlockRegistry;
import pixlepix.auracascade.registry.ModCreativeTab;

public class CommonProxy {

    public static EventHandler eventHandler;
    public BlockRegistry registry;
    public SimpleNetworkWrapper networkWrapper;

    public Block chiselBookshelf;

    public void preInit(FMLPreInitializationEvent event) {
        ModCreativeTab.INSTANCE = new ModCreativeTab();
        AngelsteelToolHelper.initMaterials();
        registry = new BlockRegistry();
        registry.preInit();


        networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(ConstantMod.modId);
        networkWrapper.registerMessage(PacketBurst.class, PacketBurst.class, 0, Side.CLIENT);

        networkWrapper.registerMessage(PacketFairyUpdate.class, PacketFairyUpdate.class, 1, Side.CLIENT);
        networkWrapper.registerMessage(PacketFairyRequestUpdate.class, PacketFairyRequestUpdate.class, 2, Side.SERVER);
        networkWrapper.registerMessage(CoordinatorScrollHandler.class, PacketCoordinatorScroll.class, 3, Side.SERVER);
    }

    public void setEntryToOpen(LexiconEntry entry) {

    }

    public World getWorld() {
        return null;
    }

    public EntityPlayer getPlayer() {
        return null;
    }

    public void init(FMLInitializationEvent event) {
        registry.init();

        NetworkRegistry.INSTANCE.registerGuiHandler(AuraCascade.instance, new GuiHandler());

        PylonRecipeRegistry.init();
        PotionManager.init();
        eventHandler = new EventHandler();
        MinecraftForge.EVENT_BUS.register(eventHandler);
        FMLCommonHandler.instance().bus().register(eventHandler);
        EntityRegistry.registerModEntity(EntityFairy.class, "Fairy", 0, AuraCascade.instance, 50, 250, true);
    }

    public void postInit(FMLPostInitializationEvent event) {
        registry.postInit();
        LexiconData.init();
        LexiconData.postInit();
        chiselBookshelf = GameRegistry.findBlock("chisel", "chisel.blockBookshelf");
    }

    public void addBlockDestroyEffects(CoordTuple tuple) {
    }

    public EffectRenderer getEffectRenderer() {
        return null;
    }

    public void addEffectBypassingLimit(EntityFX entityFX) {

    }
}
