package pixlepix.auracascade.data;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by pixlepix on 12/29/14.
 */
public interface IToolTip {
    public List<String> getTooltipData(World world, EntityPlayer player, BlockPos pos);
}
