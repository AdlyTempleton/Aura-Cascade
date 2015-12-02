package pixlepix.auracascade.item;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.network.PacketBurst;
import pixlepix.auracascade.registry.CraftingBenchRecipe;
import pixlepix.auracascade.registry.ITTinkererItem;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

import java.util.ArrayList;

/**
 * Created by localmacaccount on 5/30/15.
 */
public class ItemAngelStep extends Item implements IBauble, ITTinkererItem {
    public ItemAngelStep() {
        super();
        setMaxStackSize(1);
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return BaubleType.BELT;
    }

    @Override
    public void onWornTick(ItemStack itemStack, EntityLivingBase player) {
        if (player.isCollidedHorizontally) {
            player.stepHeight += .3;
            AuraCascade.proxy.networkWrapper.sendToAllAround(new PacketBurst(8, player.posX, player.posY - 1.5, player.posZ), new NetworkRegistry.TargetPoint(player.worldObj.provider.dimensionId, player.posX, player.posY, player.posZ, 32));
        } else if (player.stepHeight > 2) {
            player.stepHeight = 2;
        }
    }

    @Override
    public void registerIcons(IIconRegister reg) {
        itemIcon = reg.registerIcon("aura:beltAngel");
    }

    @Override
    public void onEquipped(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        entityLivingBase.stepHeight = 2;
    }

    @Override
    public void onUnequipped(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        entityLivingBase.stepHeight = .5F;
    }

    @Override
    public boolean canEquip(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        return true;
    }

    @Override
    public boolean canUnequip(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        return true;
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        return null;
    }

    @Override
    public String getItemName() {
        return "angelStepBelt";
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
    public ThaumicTinkererRecipe getRecipeItem() {
        return new CraftingBenchRecipe(new ItemStack(this), " F ", "FPF", " F ", 'F', new ItemStack(Items.feather), 'P', ItemMaterial.getPrism());
    }

    @Override
    public int getCreativeTabPriority() {
        return -30;
    }
}
