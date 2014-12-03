package pixlepix.auracascade.data;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.LinkedList;
import java.util.List;

public class CoordTuple {

	public CoordTuple(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
    public CoordTuple(TileEntity tileEntity) {
        this.x = tileEntity.xCoord;
        this.y = tileEntity.yCoord;
        this.z = tileEntity.zCoord;
    }


	public double dist(CoordTuple other){
        return Math.sqrt((x - other.x) * (x - other.x) + (y - other.y) * (y - other.y) + (z - other.z) * (z - other.z));
    }

	public CoordTuple scale(int scalar){
		return new CoordTuple(x * scalar, y * scalar, z*scalar);
	}

	public CoordTuple subtract(CoordTuple other){
		return new CoordTuple(x - other.getX(), y - other.getY(), z - other.getZ());
	}

	public static Vec3 vec(CoordTuple tuple){
		return Vec3.createVectorHelper(tuple.getX(), tuple.getY(), tuple.getZ());
	}

    public double dist(TileEntity te){
        return dist(new CoordTuple(te));
    }

    public TileEntity getTile(World w){
        return w.getTileEntity(x, y, z);
    }
    public Block getBlock(World w){
        return w.getBlock(x, y, z);
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

	private int x;
	private int y;
	private int z;

	public ForgeDirection getDirectionTo(CoordTuple other){
		int xDiff = other.x - x;
		int yDiff = other.y - y;
		int zDiff = other.z - z;

		//Make sure the tuples vary on only one dimension
		int count = 0;
		if(xDiff != 0){
			count ++;
		}
		if(yDiff != 0){
			count ++;
		}
		if(zDiff != 0){
			count ++;
		}
		if(count == 1) {
			for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
				if (direction.offsetX == (int) Math.signum(xDiff) && direction.offsetY == (int) Math.signum(yDiff) && direction.offsetZ == (int) Math.signum(zDiff)) {
					return direction;
				}
			}
		}
		return ForgeDirection.UNKNOWN;
	}

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CoordTuple && ((CoordTuple) obj).x == x && ((CoordTuple) obj).y == y && ((CoordTuple) obj).z == z;
    }

    @Override
    public int hashCode() {
        return x * 3542 + y * 234523 + z * 43258796;
    }

	public AxisAlignedBB getBoundingBox(int range){
		return AxisAlignedBB.getBoundingBox(x - range, y - range, z - range, z + range, y + range, z + range);
	}
	public List<CoordTuple> inRange(int range){
		LinkedList<CoordTuple> result = new LinkedList<CoordTuple>();
		for(int xi = -range; xi < range + 1;xi ++){
			for(int yi = -range; yi < range + 1;yi ++){
				for(int zi = -range; zi < range + 1; zi ++){
					result.add(new CoordTuple(x + xi, y + yi, z + zi));
				}
			}
		}
		return result;
	}
}
