package pixlepix.auracascade.village;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import pixlepix.auracascade.block.AuraBlock;

import java.util.List;
import java.util.Random;

/**
 * Created by localmacaccount on 3/27/15.
 */
public class ComponentAuraHut extends StructureVillagePieces.Hall {
    @Override
    public boolean addComponentParts(World world, Random random, StructureBoundingBox box) {
        super.addComponentParts(world, random, box);
        
        placeBlockAtCurrentPosition(world, AuraBlock.getBlockFromName(""), 2, 2, 2, 0, box);
        
        return true;
    }
    public ComponentAuraHut(StructureVillagePieces.Start p_i2099_1_, int p_i2099_2_, Random p_i2099_3_, StructureBoundingBox p_i2099_4_, int p_i2099_5_)
    {
        super(p_i2099_1_, p_i2099_2_, p_i2099_3_, p_i2099_4_, p_i2099_5_);
    }

    public static ComponentAuraHut buildComponent (StructureVillagePieces.Start villagePiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
    {
        StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p1, p2, p3, 0, 0, 0, 7, 6, 7, p4);
        return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new ComponentAuraHut(villagePiece, p5, random, structureboundingbox, p4) : null;
    }
}
