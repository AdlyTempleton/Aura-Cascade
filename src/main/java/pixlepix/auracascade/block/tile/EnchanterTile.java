package pixlepix.auracascade.block.tile;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.math.NumberUtils;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.data.CoordTuple;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.enchant.EnchantmentManager;
import pixlepix.auracascade.item.ItemAuraCrystal;
import pixlepix.auracascade.main.AuraUtil;
import pixlepix.auracascade.network.PacketBurst;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

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
        ArrayList<EntityItem> items = (ArrayList<EntityItem>) worldObj.getEntitiesWithinAABB(EntityItem.class, new CoordTuple(this).getBoundingBox(3));
        for (EntityItem item : items) {
            ItemStack toolStack = item.getEntityItem();
            if (EnumEnchantmentType.digger.canEnchantItem(toolStack.getItem()) || EnumEnchantmentType.weapon.canEnchantItem(toolStack.getItem())) {

                ArrayList<EntityItem> nextItems = (ArrayList<EntityItem>) worldObj.getEntitiesWithinAABB(EntityItem.class, new CoordTuple(this).getBoundingBox(3));
                for (EntityItem crystal : nextItems) {
                    if (crystal.getEntityItem().getItem() instanceof ItemAuraCrystal) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void onUsePower() {
        AuraCascade.analytics.eventDesign("consumerEnchant", AuraUtil.formatLocation(this));
        ArrayList<EntityItem> items = (ArrayList<EntityItem>) worldObj.getEntitiesWithinAABB(EntityItem.class, new CoordTuple(this).getBoundingBox(3));
        for (EntityItem item : items) {
            ItemStack toolStack = item.getEntityItem();
            if (EnumEnchantmentType.digger.canEnchantItem(toolStack.getItem()) || EnumEnchantmentType.weapon.canEnchantItem(toolStack.getItem())) {

                ArrayList<EntityItem> nextItems = (ArrayList<EntityItem>) worldObj.getEntitiesWithinAABB(EntityItem.class, new CoordTuple(this).getBoundingBox(3));
                for (EntityItem crystal : nextItems) {
                    if (crystal.getEntityItem().getItem() instanceof ItemAuraCrystal) {
                        ItemStack crystalStack = crystal.getEntityItem();
                        EnumAura aura = EnumAura.values()[crystalStack.getItemDamage()];
                        Enchantment enchant = getEnchantFromAura(aura);
                        if (enchant != null) {
                            int level = EnchantmentHelper.getEnchantmentLevel(enchant.effectId, toolStack);
                            if (isSuccessful(toolStack)) {
                                Map enchantMap = EnchantmentHelper.getEnchantments(toolStack);
                                enchantMap.put(enchant.effectId, level + 1);
                                EnchantmentHelper.setEnchantments(enchantMap, toolStack);
                            }
                            crystalStack.stackSize--;
                            AuraCascade.proxy.networkWrapper.sendToAllAround(new PacketBurst(1, item.posX, item.posY, item.posZ), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 32));

                            if (crystalStack.stackSize <= 0) {
                                crystal.setDead();
                            }
                            return;
                        }
                    }

                }
            }
        }
    }

    public int getTotalLevel(ItemStack stack) {
        return EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.red.effectId, stack)
                + EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.orange.effectId, stack)
                + EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.yellow.effectId, stack)
                + EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.green.effectId, stack)
                + EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.blue.effectId, stack)
                + EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.purple.effectId, stack);
    }

    public int getMaxLevel(ItemStack stack) {
        return NumberUtils.max(new int[]{EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.red.effectId, stack)
                , EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.orange.effectId, stack)
                , EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.yellow.effectId, stack)
                , EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.green.effectId, stack)
                , EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.blue.effectId, stack)
                , EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.purple.effectId, stack)});
    }
    public double getSuccessRate(ItemStack stack) {
        int totalLevel = getTotalLevel(stack);
        return Math.pow(.75, totalLevel) * Math.pow(.25, Math.max(0, getMaxLevel(stack) - 4));
    }

    public boolean isSuccessful(ItemStack stack) {
        return new Random().nextDouble() < getSuccessRate(stack);

    }

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
