package pixlepix.auracascade.data;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.LinkedList;
import java.util.List;

public class CoordTuple {

    private int x;
    private int y;
    private int z;


    public CoordTuple(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    public CoordTuple(TileEntity tileEntity) {
        this.x = tileEntity.getPos().getX();
        this.y = tileEntity.getPos().getY();
        this.z = tileEntity.getPos().getZ();
    }

    public CoordTuple(BlockPos pos) {
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    public static BlockPos pos(CoordTuple tuple) {
        return new BlockPos(tuple.getX(), tuple.getY(), tuple.getZ());
    }

    public static Vec3 vec(CoordTuple tuple) {
        return new Vec3(tuple.getX(), tuple.getY(), tuple.getZ());
    }

    public void setBlockToAir(World world) {
        world.setBlockToAir(new BlockPos(x, y, z));
    }

    public CoordTuple add(EnumFacing dir) {
        return new CoordTuple(x + dir.getFrontOffsetX(), y + dir.getFrontOffsetY(), z + dir.getFrontOffsetZ());
    }

    public CoordTuple add(EnumFacing dir, int mult) {
        return new CoordTuple(x + dir.getFrontOffsetX() * mult, y + dir.getFrontOffsetY() * mult, z + dir.getFrontOffsetZ() * mult);
    }

    public double dist(CoordTuple other) {
        return Math.sqrt((x - other.x) * (x - other.x) + (y - other.y) * (y - other.y) + (z - other.z) * (z - other.z));
    }

    public CoordTuple scale(int scalar) {
        return new CoordTuple(x * scalar, y * scalar, z * scalar);
    }

    public CoordTuple subtract(CoordTuple other) {
        return new CoordTuple(x - other.getX(), y - other.getY(), z - other.getZ());
    }

    public double dist(TileEntity te) {
        return dist(new CoordTuple(te));
    }

    public TileEntity getTile(World w) {
        return w.getTileEntity(new BlockPos(x, y, z));
    }

    public Block getBlock(World w) {
        return w.getBlockState(new BlockPos(x, y, z)).getBlock();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public EnumFacing getDirectionTo(CoordTuple other) {
        int xDiff = other.x - x;
        int yDiff = other.y - y;
        int zDiff = other.z - z;

        //Make sure the tuples vary on only one dimension
        int count = 0;
        if (xDiff != 0) {
            count++;
        }
        if (yDiff != 0) {
            count++;
        }
        if (zDiff != 0) {
            count++;
        }
        if (count == 1) {
            for (EnumFacing direction : EnumFacing.VALUES) {
                if (direction.getFrontOffsetX() == (int) Math.signum(xDiff) && direction.getFrontOffsetY() == (int) Math.signum(yDiff) && direction.getFrontOffsetZ() == (int) Math.signum(zDiff)) {
                    return direction;
                }
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CoordTuple && ((CoordTuple) obj).x == x && ((CoordTuple) obj).y == y && ((CoordTuple) obj).z == z;
    }

    @Override
    public int hashCode() {
        return x * 3542 + y * 234523 + z * 43258796;
    }

    public AxisAlignedBB getBoundingBox(int range) {
        return new AxisAlignedBB(x - range, y - range, z - range, x + range + 1, y + range + 1, z + range + 1);
    }

    public List<CoordTuple> inRange(int range) {
        LinkedList<CoordTuple> result = new LinkedList<CoordTuple>();
        for (int xi = -range; xi < range + 1; xi++) {
            for (int yi = -range; yi < range + 1; yi++) {
                for (int zi = -range; zi < range + 1; zi++) {
                    result.add(new CoordTuple(x + xi, y + yi, z + zi));
                }
            }
        }
        return result;
    }

    public int getMeta(World worldObj) {
        return worldObj.getBlockMetadata(x, y, z);
    }
}
