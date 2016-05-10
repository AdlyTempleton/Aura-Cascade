package pixlepix.auracascade.registry;

import net.minecraft.tileentity.TileEntity;

import java.util.HashMap;

/**
 * Created by pixlepix on 8/2/14.
 */
public interface IMultiTileEntityBlock extends ITTinkererBlock {

    public HashMap<Class<? extends TileEntity>, String> getAdditionalTileEntities();

}
