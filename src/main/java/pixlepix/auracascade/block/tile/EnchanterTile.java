package pixlepix.auracascade.block.tile;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.math.NumberUtils;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.data.PosUtil;
import pixlepix.auracascade.enchant.EnchantmentManager;
import pixlepix.auracascade.item.ItemMaterial;
import pixlepix.auracascade.main.AuraUtil;
import pixlepix.auracascade.network.PacketBurst;

/**
 * Created by localmacaccount on 2/14/15.
 */
public class EnchanterTile extends ConsumerTile {
    @Override
    public int getMaxProgress() {
        return 1000;
    }

    @Override
    public int getPowerPerProgress() {
        return 500;
    }

    @Override
    public boolean validItemsNearby() {
        ArrayList<EntityItem> items = (ArrayList<EntityItem>) worldObj.getEntitiesWithinAABB(EntityItem.class, PosUtil.getBoundingBox(getPos(), 3));
        for (EntityItem item : items) {
            ItemStack toolStack = item.getEntityItem();
            if (EnumEnchantmentType.DIGGER.canEnchantItem(toolStack.getItem()) || EnumEnchantmentType.WEAPON.canEnchantItem(toolStack.getItem())) {

                ArrayList<EntityItem> nextItems = (ArrayList<EntityItem>) worldObj.getEntitiesWithinAABB(EntityItem.class, PosUtil.getBoundingBox(getPos(), 3));
                for (EntityItem ingot : nextItems) {
                    if (ingot.getEntityItem().getItem() instanceof ItemMaterial && ((ItemMaterial) ingot.getEntityItem().getItem()).materialIndex == 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
	@Override
    public void onUsePower() {
        AuraCascade.analytics.eventDesign("consumerEnchant", AuraUtil.formatLocation(this));
        ArrayList<EntityItem> items = (ArrayList<EntityItem>) worldObj.getEntitiesWithinAABB(EntityItem.class, PosUtil.getBoundingBox(getPos(), 3));
        for (EntityItem item : items) {
            ItemStack toolStack = item.getEntityItem();
            if (EnumEnchantmentType.DIGGER.canEnchantItem(toolStack.getItem()) || EnumEnchantmentType.WEAPON.canEnchantItem(toolStack.getItem())) {

                ArrayList<EntityItem> nextItems = (ArrayList<EntityItem>) worldObj.getEntitiesWithinAABB(EntityItem.class, PosUtil.getBoundingBox(getPos(), 3));
                for (EntityItem ingot : nextItems) {
                    if (ingot.getEntityItem().getItem() instanceof ItemMaterial && ((ItemMaterial) ingot.getEntityItem().getItem()).materialIndex == 0) {
                        ItemStack ingotStack = ingot.getEntityItem();
                        EnumAura aura = ((ItemMaterial) ingotStack.getItem()).aura;
                        Enchantment enchant = getEnchantFromAura(aura);
                        if (enchant != null) {
                            int level = EnchantmentHelper.getEnchantmentLevel(enchant, toolStack);
                            if (isSuccessful(toolStack)) {
                                @SuppressWarnings("rawtypes")
								Map enchantMap = EnchantmentHelper.getEnchantments(toolStack);
                                enchantMap.put(Enchantment.getEnchantmentID(enchant), level + 1);
                                EnchantmentHelper.setEnchantments(enchantMap, toolStack);
                            }
                            ingotStack.stackSize--;
                            AuraCascade.proxy.networkWrapper.sendToAllAround(new PacketBurst(1, item.posX, item.posY, item.posZ), new NetworkRegistry.TargetPoint(worldObj.provider.getDimension(), getPos().getX(), getPos().getY(), getPos().getZ(), 32));

                            if (ingotStack.stackSize <= 0) {
                                ingot.setDead();
                            }
                            return;
                        }
                    }

                }
            }
        }
    }

    public int getTotalLevel(ItemStack stack) {
    	//TODO Enchant System, fix.
        return EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.red, stack)
                + EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.orange, stack)
                + EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.yellow, stack)
                + EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.green, stack)
                + EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.blue, stack)
                + EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.purple, stack);
    }

    public int getMaxLevel(ItemStack stack) {
        return NumberUtils.max(new int[]{EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.red, stack)
                , EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.orange, stack)
                , EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.yellow, stack)
                , EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.green, stack)
                , EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.blue, stack)
                , EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.purple, stack)});
    }
    public double getSuccessRate(ItemStack stack) {
        int totalLevel = getTotalLevel(stack);
        return Math.pow(.75, totalLevel) * Math.pow(.25, Math.max(0, getMaxLevel(stack) - 4));
    }

    public boolean isSuccessful(ItemStack stack) {
        return new Random().nextDouble() < getSuccessRate(stack);

    }

    @SuppressWarnings("incomplete-switch")
	public Enchantment getEnchantFromAura(EnumAura aura) {
        switch (aura) {
            case RED_AURA:
                return EnchantmentManager.red;
            case ORANGE_AURA:
                return EnchantmentManager.orange;
            case YELLOW_AURA:
                return EnchantmentManager.yellow;
            case BLUE_AURA:
                return EnchantmentManager.blue;
            case GREEN_AURA:
                return EnchantmentManager.green;
            case VIOLET_AURA:
                return EnchantmentManager.purple;
        }
        return null;
    }
}
