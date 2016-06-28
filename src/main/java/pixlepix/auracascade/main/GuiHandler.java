package pixlepix.auracascade.main;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import pixlepix.auracascade.block.tile.TileBookshelfCoordinator;
import pixlepix.auracascade.gui.ContainerCoordinator;
import pixlepix.auracascade.gui.GuiCoordinator;
import pixlepix.auracascade.lexicon.GuiLexicon;

/**
 * Created by pixlepix on 12/27/14.
 */
public class GuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {

        if (id == 1) {
            return new ContainerCoordinator(player.inventory, (TileBookshelfCoordinator) world.getTileEntity(new BlockPos(x, y, z)));
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id == 0) {
            return GuiLexicon.currentOpenLexicon;
        }
        if (id == 1) {
            return new GuiCoordinator(player.inventory, (TileBookshelfCoordinator) world.getTileEntity(new BlockPos(x, y, z)));

        }
        return null;

    }
}