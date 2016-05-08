package pixlepix.auracascade.village;

import java.util.List;
import java.util.Random;

import net.minecraft.util.EnumFacing;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

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
    public StructureVillagePieces.Village buildComponent(StructureVillagePieces.PieceWeight villagePiece, StructureVillagePieces.Start startPiece, @SuppressWarnings("rawtypes") List pieces, Random random, int p1, int p2, int p3, EnumFacing p4, int p5) {
        return ComponentAuraHut.buildComponent(startPiece, pieces, random, p1, p2, p3, p4, p5);
    }
}