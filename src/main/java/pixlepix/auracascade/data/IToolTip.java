package pixlepix.auracascade.data;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by pixlepix on 12/29/14.
 */
@SuppressWarnings("UnusedParameters")
public interface IToolTip {
    public List<String> getTooltipData(World world, EntityPlayer player, int x, int y, int z);
}
