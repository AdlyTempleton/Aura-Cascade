package pixlepix.auracascade.main;

import net.minecraft.block.Block;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.relauncher.Side;
import pixlepix.auracascade.AuraAnalytics;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.QuestManager;
import pixlepix.auracascade.block.AuraBlock;
import pixlepix.auracascade.block.entity.EntityFairy;
import pixlepix.auracascade.block.entity.EntityMinerExplosion;
import pixlepix.auracascade.data.OreDropManager;
import pixlepix.auracascade.data.recipe.ProcessorRecipeRegistry;
import pixlepix.auracascade.data.recipe.PylonRecipeRegistry;
import pixlepix.auracascade.enchant.EnchantmentManager;
import pixlepix.auracascade.item.AngelsteelToolHelper;
import pixlepix.auracascade.lexicon.LexiconData;
import pixlepix.auracascade.lexicon.LexiconEntry;
import pixlepix.auracascade.main.compat.TCCompat;
import pixlepix.auracascade.main.event.EnchantEventHandler;
import pixlepix.auracascade.main.event.EventHandler;
import pixlepix.auracascade.network.CoordinatorScrollHandler;
import pixlepix.auracascade.network.PacketAngelJump;
import pixlepix.auracascade.network.PacketBurst;
import pixlepix.auracascade.network.PacketCoordinatorScroll;
import pixlepix.auracascade.network.PacketFairyRequestUpdate;
import pixlepix.auracascade.network.PacketFairyUpdate;
import pixlepix.auracascade.network.PacketSyncQuestData;
import pixlepix.auracascade.potions.PotionManager;
import pixlepix.auracascade.registry.BlockRegistry;
import pixlepix.auracascade.registry.ModCreativeTab;
import pixlepix.auracascade.village.AuraHutHandler;
import pixlepix.auracascade.village.ComponentAuraHut;

public class CommonProxy {

    public static EventHandler eventHandler;

    public static EnchantEventHandler eventHandlerEnch;


    public int renderPass;
    public BlockRegistry registry;
    public SimpleNetworkWrapper networkWrapper;
    public Block chiselBookshelf;


    public void preInit(FMLPreInitializationEvent event) {
        Config.init(event);

        AuraCascade.analytics = new AuraAnalytics(ConstantMod.version, ConstantMod.analyticsKey, ConstantMod.analyticsKeySecret);
       
        ModCreativeTab.INSTANCE = new ModCreativeTab();
        AngelsteelToolHelper.initMaterials();
        registry = new BlockRegistry();
        registry.preInit();
        System.out.println("I mate it past Registry.preInit()");
        EnchantEventHandler.init();

        System.out.println("Made it past enchant handler");
        networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(ConstantMod.modId);
        networkWrapper.registerMessage(PacketBurst.class, PacketBurst.class, 0, Side.CLIENT);

        networkWrapper.registerMessage(PacketFairyUpdate.class, PacketFairyUpdate.class, 1, Side.CLIENT);
        networkWrapper.registerMessage(PacketFairyRequestUpdate.class, PacketFairyRequestUpdate.class, 2, Side.SERVER);
        networkWrapper.registerMessage(CoordinatorScrollHandler.class, PacketCoordinatorScroll.class, 3, Side.SERVER);
        networkWrapper.registerMessage(PacketAngelJump.PacketAngelJumpHandler.class, PacketAngelJump.class, 4, Side.SERVER);
        networkWrapper.registerMessage(PacketSyncQuestData.PacketSyncQuestDataHandler.class, PacketSyncQuestData.class, 5, Side.CLIENT);


        if (Config.villageGeneration) {
            VillagerRegistry.instance().registerVillageCreationHandler(new AuraHutHandler());
        }
        System.out.println("Made it past village gen");
        MapGenStructureIO.registerStructureComponent(ComponentAuraHut.class, "aura:auraHut");
    }

    public void addToTutorial(LexiconEntry entry) {
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
        ProcessorRecipeRegistry.init();
        PotionManager.init();
        eventHandler = new EventHandler();
        MinecraftForge.EVENT_BUS.register(eventHandler);
        EnchantmentManager.init();
        eventHandlerEnch = new EnchantEventHandler();
        MinecraftForge.EVENT_BUS.register(eventHandlerEnch);
        EntityRegistry.registerModEntity(EntityFairy.class, "Fairy", 0, AuraCascade.instance, 50, 250, true);
        EntityRegistry.registerModEntity(EntityMinerExplosion.class, "ExplosionMiner", 1, AuraCascade.instance, 50, 40, true);
        QuestManager.init();
    }

    public void postInit(FMLPostInitializationEvent event) {
        registry.postInit();
        LexiconData.init();
        chiselBookshelf = GameRegistry.findBlock("chisel", "chisel.blockBookshelf");
        if (Loader.isModLoaded("Thaumcraft")) {
            TCCompat.postInit();
        }

        //RiM IMC for blacklisting aura nodes
        for (Block block : BlockRegistry.getBlockFromClass(AuraBlock.class)) {
            FMLInterModComms.sendMessage("JAKJ_RedstoneInMotion", "blacklistHard", Block.blockRegistry.getNameForObject(block).toString());
        }
        OreDropManager.init();
    }
    public void setLexiconStack(ItemStack stack) {
    }

    public void addBlockDestroyEffects(BlockPos tuple) {
    }

    public EffectRenderer getEffectRenderer() {
        return null;
    }

    public void addEffectBypassingLimit(EntityFX entityFX) {

    }
}
