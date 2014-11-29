package pixlepix.auracascade.data;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

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

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CoordTuple && ((CoordTuple) obj).x == x && ((CoordTuple) obj).y == y && ((CoordTuple) obj).z == z;
    }

    @Override
    public int hashCode() {
        return x * 3542 + y * 234523 + z * 43258796;
    }
}
