package pixlepix.auracascade.main;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;
import pixlepix.auracascade.block.entity.EntityFairy;
import pixlepix.auracascade.block.tile.AuraTilePedestal;
import pixlepix.auracascade.lexicon.*;
import pixlepix.auracascade.render.RenderEntityFairy;
import pixlepix.auracascade.render.RenderPedestal;

public class ClientProxy extends CommonProxy {


    @Override
    public World getWorld(){
        return Minecraft.getMinecraft().theWorld;
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        FMLCommonHandler.instance().bus().register(new ClientTickHandler());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
        ClientRegistry.bindTileEntitySpecialRenderer(AuraTilePedestal.class, new RenderPedestal());
        RenderingRegistry.registerEntityRenderingHandler(EntityFairy.class, new RenderEntityFairy());
    }

    @Override
    public EffectRenderer getEffectRenderer() {
        return Minecraft.getMinecraft().effectRenderer;
    }


    @Override
    public void setEntryToOpen(LexiconEntry entry) {
        GuiLexicon.currentOpenLexicon = new GuiLexiconEntry(entry, new GuiLexiconIndex(entry.category));
    }

    @Override
    public void addEffectBypassingLimit(EntityFX entityFX) {
        Minecraft.getMinecraft().effectRenderer.fxLayers[entityFX.getFXLayer()].add(entityFX);
    }
}
