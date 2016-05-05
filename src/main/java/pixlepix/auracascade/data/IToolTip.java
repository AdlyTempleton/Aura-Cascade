package pixlepix.auracascade.data;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by pixlepix on 12/29/14.
 */
@SuppressWarnings("UnusedParameters")
public interface IToolTip {
    public List<String> getTooltipData(World world, EntityPlayer player, BlockPos pos);
}
