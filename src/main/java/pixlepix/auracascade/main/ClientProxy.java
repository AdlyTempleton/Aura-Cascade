package pixlepix.auracascade.main;

        import cpw.mods.fml.client.registry.ClientRegistry;
        import cpw.mods.fml.client.registry.RenderingRegistry;
        import cpw.mods.fml.common.event.FMLPostInitializationEvent;
        import net.minecraft.client.Minecraft;
        import net.minecraft.client.particle.EffectRenderer;
        import net.minecraft.world.World;
        import pixlepix.auracascade.block.entity.EntityFairy;
        import pixlepix.auracascade.block.tile.AuraTilePedestal;
        import pixlepix.auracascade.block.tile.AuraTilePump;
        import pixlepix.auracascade.render.RenderEntityFairy;
        import pixlepix.auracascade.render.RenderPedestal;

public class ClientProxy extends CommonProxy {
    @Override
    public World getWorld(){
        return Minecraft.getMinecraft().theWorld;
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
}
