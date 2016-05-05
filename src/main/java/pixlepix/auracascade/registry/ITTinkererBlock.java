package pixlepix.auracascade.registry;

import java.util.ArrayList;

import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by localmacaccount on 6/11/14.
 */
@SuppressWarnings("SameReturnValue")
public interface ITTinkererBlock extends ITTinkererRegisterable {

    public ArrayList<Object> getSpecialParameters();

    public String getBlockName();

    public boolean shouldRegister();

    public boolean shouldDisplayInTab();

    public Class<? extends ItemBlock> getItemBlock();

    public Class<? extends TileEntity> getTileEntity();

}
