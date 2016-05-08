package pixlepix.auracascade.village;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import pixlepix.auracascade.block.AuraBlock;
import pixlepix.auracascade.block.ConsumerBlock;

/**
 * Created by localmacaccount on 3/27/15.
 */
public class ComponentAuraHut extends StructureVillagePieces.WoodHut {

    public ComponentAuraHut(StructureVillagePieces.Start p_i2099_1_, int p_i2099_2_, Random p_i2099_3_, StructureBoundingBox p_i2099_4_, EnumFacing p_i2099_5_) {
        super(p_i2099_1_, p_i2099_2_, p_i2099_3_, p_i2099_4_, p_i2099_5_);
    }


    public ComponentAuraHut() {
        super();

    }

    public static ComponentAuraHut buildComponent(StructureVillagePieces.Start villagePiece, List<StructureComponent> pieces, Random random, int p1, int p2, int p3, EnumFacing p4, int p5) {
        StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p1, p2, p3, 0, 0, 0, 7, 6, 7, p4);
        return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new ComponentAuraHut(villagePiece, p5, random, structureboundingbox, p4) : null;
    }
    @Override
    public boolean addComponentParts(World worldIn, Random random, StructureBoundingBox structureBoundingBox) {
        if (this.field_143015_k < 0) {
            this.field_143015_k = this.getAverageGroundLevel(worldIn, structureBoundingBox);

            if (this.field_143015_k < 0) {
                return true;
            }

            this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 6 - 1, 0);
        }

        this.fillWithBlocks(worldIn, structureBoundingBox, 1, 1, 1, 3, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.fillWithBlocks(worldIn, structureBoundingBox, 0, 0, 0, 3, 0, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        this.fillWithBlocks(worldIn, structureBoundingBox, 1, 0, 1, 2, 0, 3, Blocks.dirt.getDefaultState(), Blocks.dirt.getDefaultState(), false);


        this.fillWithBlocks(worldIn, structureBoundingBox, 1, 5, 1, 2, 5, 3, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);


        this.setBlockState(worldIn, Blocks.sandstone.getDefaultState(), 1, 4, 0, structureBoundingBox);
        this.setBlockState(worldIn, Blocks.sandstone.getDefaultState(), 2, 4, 0, structureBoundingBox);
        this.setBlockState(worldIn, Blocks.sandstone.getDefaultState(), 1, 4, 4, structureBoundingBox);
        this.setBlockState(worldIn, Blocks.sandstone.getDefaultState(), 2, 4, 4, structureBoundingBox);
        this.setBlockState(worldIn, Blocks.sandstone.getDefaultState(), 0, 4, 1, structureBoundingBox);
        this.setBlockState(worldIn, Blocks.sandstone.getDefaultState(), 0, 4, 2, structureBoundingBox);
        this.setBlockState(worldIn, Blocks.sandstone.getDefaultState(), 0, 4, 3, structureBoundingBox);
        this.setBlockState(worldIn, Blocks.sandstone.getDefaultState(), 3, 4, 1, structureBoundingBox);
        this.setBlockState(worldIn, Blocks.sandstone.getDefaultState(), 3, 4, 2, structureBoundingBox);
        this.setBlockState(worldIn, Blocks.sandstone.getDefaultState(), 3, 4, 3, structureBoundingBox);
        this.fillWithBlocks(worldIn, structureBoundingBox, 0, 1, 0, 0, 3, 0, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
        this.fillWithBlocks(worldIn, structureBoundingBox, 3, 1, 0, 3, 3, 0, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
        this.fillWithBlocks(worldIn, structureBoundingBox, 0, 1, 4, 0, 3, 4, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
        this.fillWithBlocks(worldIn, structureBoundingBox, 3, 1, 4, 3, 3, 4, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
        this.fillWithBlocks(worldIn, structureBoundingBox, 0, 1, 1, 0, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.fillWithBlocks(worldIn, structureBoundingBox, 3, 1, 1, 3, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.fillWithBlocks(worldIn, structureBoundingBox, 1, 1, 0, 2, 3, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.fillWithBlocks(worldIn, structureBoundingBox, 1, 1, 4, 2, 3, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBox);
        this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 3, 2, 2, structureBoundingBox);

        this.setBlockState(worldIn, Blocks.air.getDefaultState(), 1, 1, 0, structureBoundingBox);
        this.setBlockState(worldIn, Blocks.air.getDefaultState(), 1, 2, 0, structureBoundingBox);
        this.placeDoorCurrentPosition(worldIn, structureBoundingBox, random, 1, 1, 0, EnumFacing.SOUTH);

        if (this.getBlockStateFromPos(worldIn, 2, 0, -1, structureBoundingBox).getMaterial() == Material.air && this.getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBox).getMaterial() != Material.air) {
        	this.setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(3), 1, 0, -1, structureBoundingBox);
        }

        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 4; ++j) {
                this.clearCurrentPositionBlocksUpwards(worldIn, j, 6, i, structureBoundingBox);
                this.replaceAirAndLiquidDownwards(worldIn, Blocks.cobblestone.getDefaultState(), j, -1, i, structureBoundingBox);
            }
        }

        this.spawnVillagers(worldIn, structureBoundingBox, 1, 1, 2, 1);

        //Begin Aura Cascade specific setup
        this.setBlockState(worldIn, AuraBlock.getBlockFromName("pump").getDefaultState(), 2, 1, 3, structureBoundingBox);
        this.setBlockState(worldIn, AuraBlock.getBlockFromName("").getDefaultState(), 2, 1, 2, structureBoundingBox);

        this.setBlockState(worldIn, AuraBlock.getBlockFromName("").getDefaultState(), 2, 2, 3, structureBoundingBox);
        this.setBlockState(worldIn, AuraBlock.getBlockFromName("").getDefaultState(), 2, 2, 2, structureBoundingBox);

        this.setBlockState(worldIn, ConsumerBlock.getBlockFromName("furnace").getDefaultState(), 2, 1, 1, structureBoundingBox);
        return true;
    }
}
