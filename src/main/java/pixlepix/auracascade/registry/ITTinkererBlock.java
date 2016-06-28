package pixlepix.auracascade.registry;

import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;

/**
 * Created by localmacaccount on 6/11/14.
 */
public interface ITTinkererBlock extends ITTinkererRegisterable {

    ArrayList<Object> getSpecialParameters();

    String getBlockName();

    boolean shouldRegister();

    boolean shouldDisplayInTab();

    Class<? extends ItemBlock> getItemBlock();

    Class<? extends TileEntity> getTileEntity();

}
