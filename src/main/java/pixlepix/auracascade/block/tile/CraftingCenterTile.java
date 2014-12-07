package pixlepix.auracascade.block.tile;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import pixlepix.auracascade.data.AuraQuantity;
import pixlepix.auracascade.data.CoordTuple;
import pixlepix.auracascade.data.recipe.PylonRecipe;
import pixlepix.auracascade.data.recipe.PylonRecipeRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by pixlepix on 12/7/14.
 */
public class CraftingCenterTile extends TileEntity {

    public static List<ForgeDirection> pedestalRelativeLocations = Arrays.asList(new ForgeDirection[]{ForgeDirection.EAST, ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.WEST});

    public boolean pedestalsConnected(){
        for(ForgeDirection direction:pedestalRelativeLocations){
            if(!(new CoordTuple(this).add(direction).getTile(worldObj) instanceof AuraTilePedestal)){
                return false;
            }
        }
        return true;
    }

    public PylonRecipe getRecipe(){
        if(!pedestalsConnected()){
            return null;
        }
        List<ItemStack> stacks = new ArrayList<ItemStack>();
        for(ForgeDirection direction:pedestalRelativeLocations){
            AuraTilePedestal pedestal = (AuraTilePedestal) new CoordTuple(this).add(direction).getTile(worldObj);
            stacks.add(pedestal.itemStack);
        }
        for(PylonRecipe recipe:PylonRecipeRegistry.recipes){
            if(recipe.matches(stacks)){
                return recipe;
            }
        }
        return null;

    }


    public void checkRecipeComplete() {
        PylonRecipe recipe= getRecipe();
        if(recipe == null){
            return;
        }
        boolean valid = true;
        for(ForgeDirection direction:pedestalRelativeLocations) {
            AuraTilePedestal pedestal = (AuraTilePedestal) new CoordTuple(this).add(direction).getTile(worldObj);
            AuraQuantity targetAura = recipe.getAuraFromItem(pedestal.itemStack);
            if(targetAura.getNum() > pedestal.powerReceived){
                valid = false;
            }
        }
        if(valid) {
            for (ForgeDirection direction : pedestalRelativeLocations) {
                AuraTilePedestal pedestal = (AuraTilePedestal) new CoordTuple(this).add(direction).getTile(worldObj);
                pedestal.itemStack = null;
                pedestal.powerReceived = 0;
            }
            ItemStack loot = recipe.result.copy();
            EntityItem entityDrop = new EntityItem(worldObj, xCoord + .5, yCoord + 2, zCoord + .5, loot);
            worldObj.spawnEntityInWorld(entityDrop);
        }
    }
}
