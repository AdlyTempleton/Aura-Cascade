package pixlepix.auracascade.registry;

import java.util.HashMap;

import net.minecraft.tileentity.TileEntity;

/**
 * Created by pixlepix on 8/2/14.
 */
public interface IMultiTileEntityBlock extends ITTinkererBlock {

    public HashMap<Class<? extends TileEntity>, String> getAdditionalTileEntities();

}
