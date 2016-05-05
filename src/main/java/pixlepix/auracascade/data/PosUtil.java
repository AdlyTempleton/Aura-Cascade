package pixlepix.auracascade.data;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public final class PosUtil {

    public static EnumFacing directionTo(BlockPos from, BlockPos to) {
        int xDiff = to.getX() - from.getX();
        int yDiff = to.getY() - from.getY();
        int zDiff = to.getZ() - from.getZ();

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

    public static AxisAlignedBB getBoundingBox(BlockPos pos, int range) {
        return new AxisAlignedBB(pos.add(-range, -range, -range), pos.add(range + 1, range + 1, range + 1));
    }

    public static Iterable<BlockPos> inRange(BlockPos from, int range) {
        return BlockPos.getAllInBox(from.add(-range, -range, -range), from.add(range, range, range));
    }

    private PosUtil() {}
}
