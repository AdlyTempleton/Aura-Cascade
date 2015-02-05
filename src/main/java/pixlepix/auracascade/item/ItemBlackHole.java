package pixlepix.auracascade.item;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import pixlepix.auracascade.data.AuraQuantity;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.data.recipe.PylonRecipe;
import pixlepix.auracascade.data.recipe.PylonRecipeComponent;
import pixlepix.auracascade.registry.ITTinkererItem;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

import java.util.ArrayList;

/**
 * Created by localmacaccount on 2/5/15.
 */
public class ItemBlackHole extends Item implements ITTinkererItem {
    @Override
    public ArrayList<Object> getSpecialParameters() {
        return null;
    }

    @Override
    public String getItemName() {
        return "blackHole";
    }

    @Override
    public boolean shouldRegister() {
        return true;
    }

    @Override
    public boolean shouldDisplayInTab() {
        return true;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {
        if (entity instanceof EntityPlayer && !world.isRemote && world.getTotalWorldTime() % 100 == 0) {
            InventoryPlayer inv = ((EntityPlayer) entity).inventory;
            for (int i = 0; i < inv.getSizeInventory(); i++) {
                if (inv.getStackInSlot(i) != null && Block.getBlockFromItem(inv.getStackInSlot(i).getItem()) == Blocks.cobblestone) {
                    inv.setInventorySlotContents(i, null);
                }
            }
        }
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return new PylonRecipe(new ItemStack(this), new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 100000), new ItemStack(Blocks.cobblestone)));
    }

    @Override
    public void registerIcons(IIconRegister register) {
        itemIcon = register.registerIcon("aura:blackHole");
    }
}
