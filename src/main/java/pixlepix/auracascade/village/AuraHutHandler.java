package pixlepix.auracascade.village;

import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraft.world.gen.structure.StructureVillagePieces;

import java.util.List;
import java.util.Random;

/**
 * Created by localmacaccount on 3/27/15.
 */
public class AuraHutHandler implements VillagerRegistry.IVillageCreationHandler {
    @Override
    public StructureVillagePieces.PieceWeight getVillagePieceWeight(Random random, int i) {
        return new StructureVillagePieces.PieceWeight(ComponentAuraHut.class, 300, 1);
    }

    @Override
    public Class<?> getComponentClass() {
        return ComponentAuraHut.class;
    }

    @Override
    public Object buildComponent(StructureVillagePieces.PieceWeight villagePiece, StructureVillagePieces.Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5) {
        return ComponentAuraHut.buildComponent(startPiece, pieces, random, p1, p2, p3, p4, p5);
    }
}