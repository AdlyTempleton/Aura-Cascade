package pixlepix.auracascade.item;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.block.BlockPhantomOre;
import pixlepix.auracascade.data.CoordTuple;
import pixlepix.auracascade.network.PacketBurst;
import pixlepix.auracascade.registry.BlockRegistry;
import pixlepix.auracascade.registry.ITTinkererItem;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

import java.util.ArrayList;

/**
 * Created by localmacaccount on 6/6/15.
 */
public class ItemDiviningRod extends Item implements ITTinkererItem {

    public ItemDiviningRod() {
        super();
        setMaxStackSize(1);
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        return null;
    }

    @Override
    public String getItemName() {
        return "oreDiviner";
    }

    @Override
    public boolean shouldRegister() {
        return true;
    }

    @Override
    public boolean shouldDisplayInTab() {
        return true;
    }

    public boolean hasClearLineOfSight(World world, CoordTuple tuple, CoordTuple tuple1) {
        int x = tuple1.getX();
        int y = tuple1.getY();
        int z = tuple1.getZ();

        Vec3 originalVector = Vec3.createVectorHelper(tuple.getX() - x, tuple.getY() - y, tuple.getZ() - z);
        Vec3 vec3 = originalVector.normalize();
        double f = 0;
        while (true) {
            f += .1;
            x = (int) (tuple.getX() + f * vec3.xCoord);
            y = (int) (tuple.getY() + f * vec3.yCoord);
            z = (int) (tuple.getZ() + f * vec3.zCoord);

            if (new CoordTuple(x, y, z).equals(tuple)) {
                return true;
            }
            if (new CoordTuple(x, y, z).equals(new CoordTuple(tuple.getX(), tuple.getY(), tuple.getZ()))) {
                continue;
            }
            if (!world.isAirBlock(x, y, z) && new CoordTuple(x, y, z).dist(tuple1) >= 1.5 && new CoordTuple(x, y, z).dist(tuple) >= 1.5) {
                return false;
            }
            if (f > originalVector.lengthVector()) {
                return true;

            }
        }

    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return null;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            boolean foundAny = false;
            int x = (int) player.posX;
            int y = (int) (player.posY + 1);
            int z = (int) player.posZ;
            for (int i = -16; i < 16; i++) {
                for (int j = -16; j < 16; j++) {
                    for (int k = -16; k < 16; k++) {
                        if (BlockPhantomOre.isPhantomOreBlock(x + i, y + j, z + k)) {
                            if (hasClearLineOfSight(world, new CoordTuple(x, y, z), new CoordTuple(x + i, y + j, z + k))) {
                                if (world.isAirBlock(x + i, y + j, z + k)) {
                                    world.setBlock(x + i, y + j, z + k, BlockRegistry.getFirstBlockFromClass(BlockPhantomOre.class));
                                    AuraCascade.proxy.networkWrapper.sendToAllAround(new PacketBurst(new CoordTuple(x, y, z), new CoordTuple(x + i, y + j, z + k), "square", 0, 0, 0, 1), new NetworkRegistry.TargetPoint(world.provider.dimensionId, x, y, z, 32));
                                    foundAny = true;
                                }
                            }
                        }
                    }
                }
            }

            if (foundAny) {
                stack.stackSize--;
            }
        }
        return stack;
    }

    @Override
    public void registerIcons(IIconRegister icon) {
        itemIcon = icon.registerIcon("aura:diviner");
    }

    @Override
    public int getCreativeTabPriority() {
        return 23;
    }
}
