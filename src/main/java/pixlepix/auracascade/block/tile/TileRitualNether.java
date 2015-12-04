package pixlepix.auracascade.block.tile;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.main.AuraUtil;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by localmacaccount on 2/8/15.
 */
public class TileRitualNether extends ConsumerTile {
    LinkedList<BlockPos> toSearch = new LinkedList<BlockPos>();
    BiomeGenBase targetBiome;
    boolean started = false;

    @Override
    public int getMaxProgress() {
        return 100;
    }

    @Override
    public int getPowerPerProgress() {
        return 5000;
    }

    @Override
    public boolean validItemsNearby() {
        return true;
    }

    public byte getBiomeId() {

        //8 is hardcoded value for hell biome
        return 8;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        int count = 0;
        if (!worldObj.isRemote && toSearch.size() == 0 && started) {
            worldObj.setBlockToAir(getPos());
        }
        while (toSearch.size() > 0) {
            BlockPos pos = toSearch.getFirst();
            toSearch.removeFirst();
            int x = pos.getX();
            int z = pos.getZ();
            if (getPos().distanceSq(pos) > 150 * 150) {
                continue;
            }
            Chunk chunk = worldObj.getChunkFromBlockCoords(pos);
            byte[] biomeData = chunk.getBiomeArray();
            biomeData[(z & 15) << 4 | (x & 15)] = getBiomeId();
            boolean particle = true;
            for (int y = 0; y < 255; y++) {
                Block b = getMappedBlock(worldObj.getBlockState(new BlockPos(x, y, z)).getBlock());
                if (b != null) {
                    worldObj.setBlockState(new BlockPos(x, y, z), b.getDefaultState(), 2);
                    if (particle) {
                        particle = false;
                        AuraCascade.proxy.addBlockDestroyEffects(new BlockPos(x, y, z));
                    }
                }
            }
            if (worldObj.getBiomeGenForCoords(getPos().east()) == targetBiome
                    && !toSearch.contains(getPos().east())) {
                toSearch.addLast(getPos().east());
            }
            if (worldObj.getBiomeGenForCoords(getPos().west()) == targetBiome
                    && !toSearch.contains(getPos().west())) {
                toSearch.addLast(getPos().west());
            }
            if (worldObj.getBiomeGenForCoords(getPos().south()) == targetBiome
                    && !toSearch.contains(getPos().south())) {
                toSearch.addLast(getPos().south());
            }
            if (worldObj.getBiomeGenForCoords(getPos().north()) == targetBiome
                    && !toSearch.contains(getPos().north())) {
                toSearch.addLast(getPos().north());
            }
            count++;
            if (count > 30) {
                break;

            }
        }

    }

    @Override
    public void onUsePower() {
        AuraCascade.analytics.eventDesign("consumerRitual", AuraUtil.formatLocation(this));
        if (!(worldObj.getBiomeGenForCoords(getPos()).biomeID == getBiomeId())) {
            //BlockPoss are used for convenience, but y-values are irrelavent
            toSearch.addFirst(getPos());
            targetBiome = worldObj.getBiomeGenForCoords(getPos());
            started = true;
        }
    }

    public Block getMappedBlock(Block b) {
        if (b == Blocks.stone) {
            return Blocks.netherrack;
        }
        if (b == Blocks.grass || b == Blocks.dirt) {
            return new Random().nextInt(3) == 0 ? Blocks.soul_sand : Blocks.netherrack;
        }
        if (b == Blocks.log || b == Blocks.log2 || b == Blocks.leaves || b == Blocks.leaves2) {
            return Blocks.glowstone;
        }
        if (b == Blocks.tallgrass) {
            return Blocks.nether_wart;
        }
        if (b == Blocks.gravel || b == Blocks.sand) {
            return Blocks.soul_sand;
        }
        if (b == Blocks.water || b == Blocks.flowing_water) {
            return Blocks.lava;
        }
        if (b == Blocks.snow || b == Blocks.snow_layer) {
            return Blocks.air;

        }
        return null;
    }
}
