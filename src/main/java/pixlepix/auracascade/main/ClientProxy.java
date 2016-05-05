package pixlepix.auracascade.main;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import pixlepix.auracascade.KeyBindings;
import pixlepix.auracascade.ModelHandler;
import pixlepix.auracascade.block.entity.EntityFairy;
import pixlepix.auracascade.block.tile.AuraTilePedestal;
import pixlepix.auracascade.lexicon.ClientTickHandler;
import pixlepix.auracascade.lexicon.GuiLexicon;
import pixlepix.auracascade.lexicon.GuiLexiconEntry;
import pixlepix.auracascade.lexicon.GuiLexiconIndex;
import pixlepix.auracascade.lexicon.LexiconEntry;
import pixlepix.auracascade.main.event.ClientEventHandler;
import pixlepix.auracascade.render.OverlayRender;
import pixlepix.auracascade.render.RenderEntityFairy;
import pixlepix.auracascade.render.RenderPedestal;

public class ClientProxy extends CommonProxy {


    @Override
    public World getWorld() {
        return Minecraft.getMinecraft().theWorld;
    }

    @Override
    public void preInit(FMLPreInitializationEvent evt) {
        super.preInit(evt);
        ModelHandler.registerModels();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        MinecraftForge.EVENT_BUS.register(new ClientTickHandler());
        MinecraftForge.EVENT_BUS.register(new OverlayRender());

        KeyBindings.init();

        ClientEventHandler clientEventHandler = new ClientEventHandler();
        MinecraftForge.EVENT_BUS.register(clientEventHandler);


    }
    @Override
    public void addToTutorial(LexiconEntry entry) {

        GuiLexicon.tutorialMaster.add(entry);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
        ClientRegistry.bindTileEntitySpecialRenderer(AuraTilePedestal.class, new RenderPedestal());
        RenderingRegistry.registerEntityRenderingHandler(EntityFairy.class, new RenderEntityFairy(Minecraft.getMinecraft().getRenderManager()));
    }

    @Override
    public EntityPlayer getPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }

    @Override
    public EffectRenderer getEffectRenderer() {
        return Minecraft.getMinecraft().effectRenderer;
    }


    @Override
    public void setEntryToOpen(LexiconEntry entry) {
        GuiLexicon.currentOpenLexicon = new GuiLexiconEntry(entry, new GuiLexiconIndex(entry.category));
    }


    public void addBlockDestroyEffects(BlockPos pos) {

        Minecraft.getMinecraft().effectRenderer.addBlockDestroyEffects(pos, Minecraft.getMinecraft().theWorld.getBlockState(pos));


    }

    @Override
    public void setLexiconStack(ItemStack stack) {
        GuiLexicon.stackUsed = stack;
    }

    @Override
    public void addEffectBypassingLimit(EntityFX entityFX) {
        if (Config.overrideMaxParticleLimit) {
        	//TODO Changed getAlpha to func_174838_j.
            Minecraft.getMinecraft().effectRenderer.fxLayers[entityFX.getFXLayer()][entityFX.func_174838_j() != 1 ? 0 : 1].add(entityFX);
        } else {
            Minecraft.getMinecraft().theWorld.spawnEntityInWorld(entityFX);
        }
    }
}
