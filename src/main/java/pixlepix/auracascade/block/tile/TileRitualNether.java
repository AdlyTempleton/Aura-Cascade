package pixlepix.auracascade.block.tile;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import pixlepix.auracascade.AuraCascade;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by localmacaccount on 2/8/15.
 */
public class TileRitualNether extends ConsumerTile implements ITickable {
    LinkedList<BlockPos> toSearch = new LinkedList<BlockPos>();
    Biome targetBiome;
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
    public void update() {
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
            if (worldObj.getBiomeForCoordsBody(getPos().east()) == targetBiome
                    && !toSearch.contains(getPos().east())) {
                toSearch.addLast(getPos().east());
            }
            if (worldObj.getBiomeForCoordsBody(getPos().west()) == targetBiome
                    && !toSearch.contains(getPos().west())) {
                toSearch.addLast(getPos().west());
            }
            if (worldObj.getBiomeForCoordsBody(getPos().south()) == targetBiome
                    && !toSearch.contains(getPos().south())) {
                toSearch.addLast(getPos().south());
            }
            if (worldObj.getBiomeForCoordsBody(getPos().north()) == targetBiome
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
       // AuraCascade.analytics.eventDesign("consumerRitual", AuraUtil.formatLocation(this));
        worldObj.getBiomeForCoordsBody(getPos());
        if (!(Biome.getIdForBiome(worldObj.getChunkFromBlockCoords(pos).getBiome(pos, worldObj.getBiomeProvider())) == getBiomeId())) {
            //BlockPoss are used for convenience, but y-values are irrelavent
            toSearch.addFirst(getPos());
            targetBiome = worldObj.getBiomeForCoordsBody(getPos());
            started = true;
        }
 
    }

    public Block getMappedBlock(Block b) {
        if (b == Blocks.STONE) {
            return Blocks.NETHERRACK;
        }
        if (b == Blocks.GRASS || b == Blocks.DIRT) {
            return new Random().nextInt(3) == 0 ? Blocks.SOUL_SAND : Blocks.NETHERRACK;
        }
        if (b == Blocks.LOG || b == Blocks.LOG2 || b == Blocks.LEAVES || b == Blocks.LEAVES2) {
            return Blocks.GLOWSTONE;
        }
        if (b == Blocks.TALLGRASS) {
            return Blocks.NETHER_WART;
        }
        if (b == Blocks.GRAVEL || b == Blocks.SAND) {
            return Blocks.SOUL_SAND;
        }
        if (b == Blocks.WATER || b == Blocks.FLOWING_WATER) {
            return Blocks.LAVA;
        }
        if (b == Blocks.SNOW || b == Blocks.SNOW_LAYER) {
            return Blocks.AIR;

        }
        return null;
    }
}
