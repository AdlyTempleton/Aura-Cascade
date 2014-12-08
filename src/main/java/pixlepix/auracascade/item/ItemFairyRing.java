package pixlepix.auracascade.item;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import pixlepix.auracascade.block.entity.EntityFairy;
import pixlepix.auracascade.registry.ITTinkererItem;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pixlepix on 12/8/14.
 */
public class ItemFairyRing extends Item implements ITTinkererItem, IBauble {
    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return BaubleType.RING;
    }

    @Override
    public void onWornTick(ItemStack itemStack, EntityLivingBase entityLivingBase) {

    }

    @Override
    public void onEquipped(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        if(entityLivingBase instanceof EntityPlayer) {
            EntityFairy fairy = new EntityFairy(entityLivingBase.worldObj);
            fairy.player = (EntityPlayer) entityLivingBase;
            ((EntityPlayer) entityLivingBase).worldObj.spawnEntityInWorld(fairy);
        }
    }

    @Override
    public void onUnequipped(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        List<EntityFairy> fairies = entityLivingBase.worldObj.getEntitiesWithinAABB(EntityFairy.class, AxisAlignedBB.getBoundingBox(entityLivingBase.posX - 10, entityLivingBase.posY - 10, entityLivingBase.posZ - 10, entityLivingBase.posX + 10, entityLivingBase.posY + 10, entityLivingBase.posZ + 10));
        for(EntityFairy fairy: fairies){
            if(fairy.player == entityLivingBase){
                fairy.setDead();
            }
        }

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

    public static final String name = "fairyRing";

    @Override
    public String getItemName() {
        return name;
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
        return null;
    }
}
