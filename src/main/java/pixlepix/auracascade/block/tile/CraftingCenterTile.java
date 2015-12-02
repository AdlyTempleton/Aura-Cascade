package pixlepix.auracascade.block.tile;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.data.AuraQuantity;
import pixlepix.auracascade.data.CoordTuple;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.data.recipe.PylonRecipe;
import pixlepix.auracascade.data.recipe.PylonRecipeRegistry;
import pixlepix.auracascade.network.PacketBurst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by pixlepix on 12/7/14.
 */
public class CraftingCenterTile extends TileEntity {

    public static List<ForgeDirection> pedestalRelativeLocations = Arrays.asList(ForgeDirection.EAST, ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.WEST);

    public boolean pedestalsConnected() {
        for (ForgeDirection direction : pedestalRelativeLocations) {
            if (!(new CoordTuple(this).add(direction).getTile(worldObj) instanceof AuraTilePedestal)) {
                return false;
            }
        }
        return true;
    }

    public PylonRecipe getRecipe() {
        if (!pedestalsConnected()) {
            return null;
        }
        List<ItemStack> stacks = new ArrayList<ItemStack>();
        for (ForgeDirection direction : pedestalRelativeLocations) {
            AuraTilePedestal pedestal = (AuraTilePedestal) new CoordTuple(this).add(direction).getTile(worldObj);
            stacks.add(pedestal.itemStack);
        }
        for (PylonRecipe recipe : PylonRecipeRegistry.recipes) {
            if (recipe.matches(stacks)) {
                return recipe;
            }
        }
        return null;

    }


    public void checkRecipeComplete() {
        PylonRecipe recipe = getRecipe();
        if (recipe == null) {
            return;
        }
        boolean valid = true;
        for (ForgeDirection direction : pedestalRelativeLocations) {
            AuraTilePedestal pedestal = (AuraTilePedestal) new CoordTuple(this).add(direction).getTile(worldObj);
            AuraQuantity targetAura = recipe.getAuraFromItem(pedestal.itemStack);
            if (targetAura.getNum() > pedestal.powerReceived) {
                valid = false;
            }
        }
        if (valid) {
            for (ForgeDirection direction : pedestalRelativeLocations) {
                AuraTilePedestal pedestal = (AuraTilePedestal) new CoordTuple(this).add(direction).getTile(worldObj);
                //Particles and sparklez
                for (ForgeDirection beamDir : ForgeDirection.VALID_DIRECTIONS) {
                    if (beamDir != direction && beamDir != direction.getOpposite()) {
                        CoordTuple mid = new CoordTuple(pedestal).add(beamDir).add(direction);
                        EnumAura aura = recipe.getAuraFromItem(pedestal.itemStack).getType();
                        burst(mid, new CoordTuple(pedestal), "happyVillager", aura, 1);

                        burst(mid, new CoordTuple(this), "happyVillager", aura, 1);

                    }
                }

                pedestal.itemStack = null;
                pedestal.powerReceived = 0;
            }
            ItemStack loot = recipe.result.copy();

            AuraCascade.analytics.eventDesign("vortexCraft", loot.getUnlocalizedName());
            EntityItem entityDrop = new EntityItem(worldObj, xCoord + .5, yCoord + 2, zCoord + .5, loot);
            worldObj.spawnEntityInWorld(entityDrop);
            AuraCascade.proxy.networkWrapper.sendToAllAround(new PacketBurst(3, xCoord + .5, yCoord + 2, zCoord + .5), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 32));

        }
    }

    public void burst(CoordTuple origin, CoordTuple target, String particle, EnumAura aura, double composition) {

        AuraCascade.proxy.networkWrapper.sendToAllAround(new PacketBurst(origin, target, particle, aura.r, aura.g, aura.b, composition), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 32));

    }

}
