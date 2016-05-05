package pixlepix.auracascade.block.tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.data.AuraQuantity;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.data.recipe.PylonRecipe;
import pixlepix.auracascade.data.recipe.PylonRecipeRegistry;
import pixlepix.auracascade.network.PacketBurst;

/**
 * Created by pixlepix on 12/7/14.
 */
public class CraftingCenterTile extends TileEntity {

    public static List<EnumFacing> pedestalRelativeLocations = Arrays.asList(EnumFacing.HORIZONTALS);

    public boolean pedestalsConnected() {
        for (EnumFacing direction : pedestalRelativeLocations) {
            if (!(worldObj.getTileEntity(getPos().offset(direction)) instanceof AuraTilePedestal)) {
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
        for (EnumFacing direction : pedestalRelativeLocations) {
            AuraTilePedestal pedestal = (AuraTilePedestal) worldObj.getTileEntity(getPos().offset(direction));
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
        for (EnumFacing direction : pedestalRelativeLocations) {
            AuraTilePedestal pedestal = (AuraTilePedestal) worldObj.getTileEntity(getPos().offset(direction));
            AuraQuantity targetAura = recipe.getAuraFromItem(pedestal.itemStack);
            if (targetAura.getNum() > pedestal.powerReceived) {
                valid = false;
            }
        }
        if (valid) {
            for (EnumFacing direction : pedestalRelativeLocations) {
                AuraTilePedestal pedestal = (AuraTilePedestal) worldObj.getTileEntity(getPos().offset(direction));
                //Particles and sparklez
                for (EnumFacing beamDir : EnumFacing.VALUES) {
                    if (beamDir != direction && beamDir != direction.getOpposite()) {
                        BlockPos mid = pedestal.getPos().offset(beamDir).offset(direction);
                        EnumAura aura = recipe.getAuraFromItem(pedestal.itemStack).getType();
                        burst(mid, pedestal.getPos(), "happyVillager", aura, 1);

                        burst(mid, getPos(), "happyVillager", aura, 1);

                    }
                }

                pedestal.itemStack = null;
                pedestal.powerReceived = 0;
            }
            ItemStack loot = recipe.result.copy();

            AuraCascade.analytics.eventDesign("vortexCraft", loot.getUnlocalizedName());
            EntityItem entityDrop = new EntityItem(worldObj, getPos().getX() + .5, getPos().getY() + 2, getPos().getZ() + .5, loot);
            worldObj.spawnEntityInWorld(entityDrop);
            AuraCascade.proxy.networkWrapper.sendToAllAround(new PacketBurst(3, getPos().getX() + .5, getPos().getY() + 2, getPos().getZ() + .5), new NetworkRegistry.TargetPoint(worldObj.provider.func_177502_q(), getPos().getX(),getPos().getY(), getPos().getZ(), 32));

        }
    }

    public void burst(BlockPos origin, BlockPos target, String particle, EnumAura aura, double composition) {

        AuraCascade.proxy.networkWrapper.sendToAllAround(new PacketBurst(origin, target, particle, aura.r, aura.g, aura.b, composition), new NetworkRegistry.TargetPoint(worldObj.provider.func_177502_q(), getPos().getX(),getPos().getY(), getPos().getZ(), 32));

    }

}
