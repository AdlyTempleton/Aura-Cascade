package cofh.api.tileentity;

/**
 * Implement this interface on Tile Entities which cache their redstone status.
 * <p/>
 * Note that {@link IRedstoneControl} is an extension of this.
 *
 * @author King Lemming
 */
public interface IRedstoneCache {

    boolean isPowered();

    void setPowered(boolean isPowered);

}
