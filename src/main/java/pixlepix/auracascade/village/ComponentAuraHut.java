package pixlepix.auracascade.village;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import pixlepix.auracascade.block.AuraBlock;
import pixlepix.auracascade.block.ConsumerBlock;

import java.util.List;
import java.util.Random;

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

    public static ComponentAuraHut buildComponent(StructureVillagePieces.Start villagePiece, List pieces, Random random, int p1, int p2, int p3, EnumFacing p4, int p5) {
        StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p1, p2, p3, 0, 0, 0, 7, 6, 7, p4);
        return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new ComponentAuraHut(villagePiece, p5, random, structureboundingbox, p4) : null;
    }

    public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
        if (this.field_143015_k < 0) {
            this.field_143015_k = this.getAverageGroundLevel(p_74875_1_, p_74875_3_);

            if (this.field_143015_k < 0) {
                return true;
            }

            this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 6 - 1, 0);
        }

        this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 1, 3, 5, 4, Blocks.air, Blocks.air, false);
        this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 3, 0, 4, Blocks.cobblestone, Blocks.cobblestone, false);
        this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 0, 1, 2, 0, 3, Blocks.dirt, Blocks.dirt, false);


        this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 5, 1, 2, 5, 3, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);


        this.placeBlockAtCurrentPosition(p_74875_1_, Blocks.sandstone, 0, 1, 4, 0, p_74875_3_);
        this.placeBlockAtCurrentPosition(p_74875_1_, Blocks.sandstone, 0, 2, 4, 0, p_74875_3_);
        this.placeBlockAtCurrentPosition(p_74875_1_, Blocks.sandstone, 0, 1, 4, 4, p_74875_3_);
        this.placeBlockAtCurrentPosition(p_74875_1_, Blocks.sandstone, 0, 2, 4, 4, p_74875_3_);
        this.placeBlockAtCurrentPosition(p_74875_1_, Blocks.sandstone, 0, 0, 4, 1, p_74875_3_);
        this.placeBlockAtCurrentPosition(p_74875_1_, Blocks.sandstone, 0, 0, 4, 2, p_74875_3_);
        this.placeBlockAtCurrentPosition(p_74875_1_, Blocks.sandstone, 0, 0, 4, 3, p_74875_3_);
        this.placeBlockAtCurrentPosition(p_74875_1_, Blocks.sandstone, 0, 3, 4, 1, p_74875_3_);
        this.placeBlockAtCurrentPosition(p_74875_1_, Blocks.sandstone, 0, 3, 4, 2, p_74875_3_);
        this.placeBlockAtCurrentPosition(p_74875_1_, Blocks.sandstone, 0, 3, 4, 3, p_74875_3_);
        this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 0, 0, 3, 0, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
        this.fillWithBlocks(p_74875_1_, p_74875_3_, 3, 1, 0, 3, 3, 0, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
        this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 4, 0, 3, 4, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
        this.fillWithBlocks(p_74875_1_, p_74875_3_, 3, 1, 4, 3, 3, 4, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
        this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 1, 0, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.fillWithBlocks(p_74875_1_, p_74875_3_, 3, 1, 1, 3, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 0, 2, 3, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 4, 2, 3, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.placeBlockAtCurrentPosition(p_74875_1_, Blocks.glass_pane, 0, 0, 2, 2, p_74875_3_);
        this.placeBlockAtCurrentPosition(p_74875_1_, Blocks.glass_pane, 0, 3, 2, 2, p_74875_3_);

        this.placeBlockAtCurrentPosition(p_74875_1_, Blocks.air, 0, 1, 1, 0, p_74875_3_);
        this.placeBlockAtCurrentPosition(p_74875_1_, Blocks.air, 0, 1, 2, 0, p_74875_3_);
        this.placeDoorAtCurrentPosition(p_74875_1_, p_74875_3_, p_74875_2_, 1, 1, 0, this.getMetadataWithOffset(Blocks.wooden_door, 1));

        if (this.getBlockAtCurrentPosition(p_74875_1_, 1, 0, -1, p_74875_3_).getMaterial() == Material.air && this.getBlockAtCurrentPosition(p_74875_1_, 1, -1, -1, p_74875_3_).getMaterial() != Material.air) {
            this.placeBlockAtCurrentPosition(p_74875_1_, Blocks.stone_stairs, this.getMetadataWithOffset(Blocks.stone_stairs, 3), 1, 0, -1, p_74875_3_);
        }

        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 4; ++j) {
                this.clearCurrentPositionBlocksUpwards(p_74875_1_, j, 6, i, p_74875_3_);
                this.func_151554_b(p_74875_1_, Blocks.cobblestone, 0, j, -1, i, p_74875_3_);
            }
        }

        this.spawnVillagers(p_74875_1_, p_74875_3_, 1, 1, 2, 1);

        //Begin Aura Cascade specific setup
        this.placeBlockAtCurrentPosition(p_74875_1_, AuraBlock.getBlockFromName("pump"), 0, 2, 1, 3, p_74875_3_);
        this.placeBlockAtCurrentPosition(p_74875_1_, AuraBlock.getBlockFromName(""), 0, 2, 1, 2, p_74875_3_);

        this.placeBlockAtCurrentPosition(p_74875_1_, AuraBlock.getBlockFromName(""), 0, 2, 2, 3, p_74875_3_);
        this.placeBlockAtCurrentPosition(p_74875_1_, AuraBlock.getBlockFromName(""), 0, 2, 2, 2, p_74875_3_);

        this.placeBlockAtCurrentPosition(p_74875_1_, ConsumerBlock.getBlockFromName("furnace"), 0, 2, 1, 1, p_74875_3_);
        return true;
    }
}
