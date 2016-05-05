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

        this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 1, 3, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 3, 0, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 0, 1, 2, 0, 3, Blocks.dirt.getDefaultState(), Blocks.dirt.getDefaultState(), false);


        this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 5, 1, 2, 5, 3, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);


        this.setBlockState(p_74875_1_, Blocks.sandstone.getDefaultState(), 1, 4, 0, p_74875_3_);
        this.setBlockState(p_74875_1_, Blocks.sandstone.getDefaultState(), 2, 4, 0, p_74875_3_);
        this.setBlockState(p_74875_1_, Blocks.sandstone.getDefaultState(), 1, 4, 4, p_74875_3_);
        this.setBlockState(p_74875_1_, Blocks.sandstone.getDefaultState(), 2, 4, 4, p_74875_3_);
        this.setBlockState(p_74875_1_, Blocks.sandstone.getDefaultState(), 0, 4, 1, p_74875_3_);
        this.setBlockState(p_74875_1_, Blocks.sandstone.getDefaultState(), 0, 4, 2, p_74875_3_);
        this.setBlockState(p_74875_1_, Blocks.sandstone.getDefaultState(), 0, 4, 3, p_74875_3_);
        this.setBlockState(p_74875_1_, Blocks.sandstone.getDefaultState(), 3, 4, 1, p_74875_3_);
        this.setBlockState(p_74875_1_, Blocks.sandstone.getDefaultState(), 3, 4, 2, p_74875_3_);
        this.setBlockState(p_74875_1_, Blocks.sandstone.getDefaultState(), 3, 4, 3, p_74875_3_);
        this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 0, 0, 3, 0, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
        this.fillWithBlocks(p_74875_1_, p_74875_3_, 3, 1, 0, 3, 3, 0, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
        this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 4, 0, 3, 4, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
        this.fillWithBlocks(p_74875_1_, p_74875_3_, 3, 1, 4, 3, 3, 4, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
        this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 1, 0, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.fillWithBlocks(p_74875_1_, p_74875_3_, 3, 1, 1, 3, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 0, 2, 3, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 4, 2, 3, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.setBlockState(p_74875_1_, Blocks.glass_pane.getDefaultState(), 0, 2, 2, p_74875_3_);
        this.setBlockState(p_74875_1_, Blocks.glass_pane.getDefaultState(), 3, 2, 2, p_74875_3_);

        this.setBlockState(p_74875_1_, Blocks.air.getDefaultState(), 1, 1, 0, p_74875_3_);
        this.setBlockState(p_74875_1_, Blocks.air.getDefaultState(), 1, 2, 0, p_74875_3_);
        this.placeDoorCurrentPosition(p_74875_1_, p_74875_3_, p_74875_2_, 1, 1, 0, EnumFacing.SOUTH);

        if (this.getBlockStateFromPos(p_74875_1_, 1, 0, -1, p_74875_3_).getBlock().getMaterial() == Material.air && this.getBlockStateFromPos(p_74875_1_, 1, -1, -1, p_74875_3_).getBlock().getMaterial() != Material.air) {
           
        	this.setBlockState(p_74875_1_, Blocks.stone_stairs.getStateFromMeta(this.func_151555_a(Blocks.stone_stairs, 3)), 1, 0, -1, p_74875_3_);
        }

        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 4; ++j) {
                this.clearCurrentPositionBlocksUpwards(p_74875_1_, j, 6, i, p_74875_3_);
                this.replaceAirAndLiquidDownwards(p_74875_1_, Blocks.cobblestone.getDefaultState(), j, -1, i, p_74875_3_);
            }
        }

        this.spawnVillagers(p_74875_1_, p_74875_3_, 1, 1, 2, 1);

        //Begin Aura Cascade specific setup
        this.setBlockState(p_74875_1_, AuraBlock.getBlockFromName("pump").getDefaultState(), 2, 1, 3, p_74875_3_);
        this.setBlockState(p_74875_1_, AuraBlock.getBlockFromName("").getDefaultState(), 2, 1, 2, p_74875_3_);

        this.setBlockState(p_74875_1_, AuraBlock.getBlockFromName("").getDefaultState(), 2, 2, 3, p_74875_3_);
        this.setBlockState(p_74875_1_, AuraBlock.getBlockFromName("").getDefaultState(), 2, 2, 2, p_74875_3_);

        this.setBlockState(p_74875_1_, ConsumerBlock.getBlockFromName("furnace").getDefaultState(), 2, 1, 1, p_74875_3_);
        return true;
    }
}
