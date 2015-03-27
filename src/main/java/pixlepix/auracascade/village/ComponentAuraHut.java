package pixlepix.auracascade.village;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import pixlepix.auracascade.block.AuraBlock;

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
}
