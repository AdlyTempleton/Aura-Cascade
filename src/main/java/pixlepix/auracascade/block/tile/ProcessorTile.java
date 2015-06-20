package pixlepix.auracascade.block.tile;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.StringUtils;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.item.ItemMaterial;
import pixlepix.auracascade.network.PacketBurst;

import java.util.Arrays;
import java.util.List;

/**
 * Created by pixlepix on 12/21/14.
 */
public class ProcessorTile extends ConsumerTile {
    public static int MAX_PROGRESS = 60;
    public static int POWER_PER_PROGRESS = 150;

    public static ItemStack getDoubleResult(ItemStack stack) {
        int[] oreIds = OreDictionary.getOreIDs(stack);
        for (int id : oreIds) {
            String oreName = OreDictionary.getOreName(id);
            if (StringUtils.startsWith(oreName, "ore")) {
                String dustName = StringUtils.replace(oreName, "ore", "dust");
                if (OreDictionary.getOres(dustName).size() != 0) {
                    return OreDictionary.getOres(dustName).get(0).copy();
                }
            }
        }
        return null;
    }

    @Override
    public int getMaxProgress() {
        return MAX_PROGRESS;
    }

    @Override
    public int getPowerPerProgress() {
        return POWER_PER_PROGRESS;
    }

    @Override
    public boolean validItemsNearby() {
        int range = 3;
        List<EntityItem> nearbyItems = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord - range, yCoord - range, zCoord - range, xCoord + range, yCoord + range, zCoord + range));
        for (EntityItem entityItem : nearbyItems) {
            ItemStack stack = entityItem.getEntityItem();
            if (stack.getItem() == Items.iron_ingot) {

                List<EntityItem> nearbyItemsColor = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord - range, yCoord - range, zCoord - range, xCoord + range, yCoord + range, zCoord + range));
                for (EntityItem entityItemColor : nearbyItemsColor) {
                    ItemStack woolStack = entityItemColor.getEntityItem();
                    if (woolStack.getItem() == Item.getItemFromBlock(Blocks.wool)) {

                        return true;
                    }
                }
            } else if (stack.getItem() instanceof ItemMaterial) {
                //Array containing found gems of different materials
                //Position corresponds to EnumAura.ordinal
                //Initially found itemstack does NOT get species treatment
                if (((ItemMaterial) stack.getItem()).materialIndex == 1) {
                    //Find gems
                    EntityItem[] foundGems = new EntityItem[8];
                    //List<EntityItem> nearbyItemsGem = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord - range, yCoord - range, zCoord - range, xCoord + range, yCoord + range, zCoord + range));
                    for (EntityItem entityItemGem : nearbyItems) {
                        Item item = entityItemGem.getEntityItem().getItem();
                        if (item instanceof ItemMaterial) {
                            ItemMaterial itemMaterial = (ItemMaterial) item;
                            if (itemMaterial.materialIndex == 1) {
                                foundGems[itemMaterial.aura.ordinal()] = entityItemGem;
                            }
                        }
                    }

                    //Check if 8 gems have been found
                    if (!Arrays.asList(foundGems).contains(null)) {
                        return true;
                    }
                }
            }
            if (getDoubleResult(stack) != null) {
                return true;
            }
        }
        return false;
    }

    public int oreMultFactor() {
        return 2;
    }

    @Override
    public void onUsePower() {
        int range = 3;
        ItemStack resultStack = null;
        List<EntityItem> nearbyItems = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord - range, yCoord - range, zCoord - range, xCoord + range, yCoord + range, zCoord + range));
        for (EntityItem entityItem : nearbyItems) {
            ItemStack stack = entityItem.getEntityItem();
            if (stack.getItem() == Items.iron_ingot) {

                List<EntityItem> nearbyItemsColor = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord - range, yCoord - range, zCoord - range, xCoord + range, yCoord + range, zCoord + range));
                for (EntityItem entityItemColor : nearbyItemsColor) {
                    ItemStack woolStack = entityItemColor.getEntityItem();
                    if (woolStack.getItem() == Item.getItemFromBlock(Blocks.wool)) {

                        //Get stack
                        int meta = woolStack.getItemDamage();
                        EnumAura color = EnumAura.getColorFromDyeMeta(meta);
                        Item ingotItem = ItemMaterial.getItemFromSpecs(new ItemMaterial.MaterialPair(color, 0));
                        resultStack = new ItemStack(ingotItem);

                        //Decr both stacks
                        entityItemColor.getEntityItem().stackSize--;
                        if (entityItemColor.getEntityItem().stackSize <= 0) {
                            entityItemColor.setDead();
                        }

                        entityItem.getEntityItem().stackSize--;
                        if (entityItem.getEntityItem().stackSize <= 0) {
                            entityItem.setDead();
                        }
                        break;
                    }
                }
            } else if (stack.getItem() instanceof ItemMaterial) {
                //Array containing found gems of different materials
                //Position corresponds to EnumAura.ordinal
                //Initially found itemstack does NOT get species treatment
                if (((ItemMaterial) stack.getItem()).materialIndex == 1) {
                    //Find gems
                    EntityItem[] foundGems = new EntityItem[8];
                    //List<EntityItem> nearbyItemsGem = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord - range, yCoord - range, zCoord - range, xCoord + range, yCoord + range, zCoord + range));
                    for (EntityItem entityItemGem : nearbyItems) {
                        Item item = entityItemGem.getEntityItem().getItem();
                        if (item instanceof ItemMaterial) {
                            ItemMaterial itemMaterial = (ItemMaterial) item;
                            if (itemMaterial.materialIndex == 1) {
                                foundGems[itemMaterial.aura.ordinal()] = entityItemGem;
                            }
                        }
                    }

                    //Check if 8 gems have been found
                    if (!Arrays.asList(foundGems).contains(null)) {
                        resultStack = new ItemStack(ItemMaterial.getItemFromSpecs(new ItemMaterial.MaterialPair(EnumAura.WHITE_AURA, 2)));
                        //Decr
                        for (EntityItem itemToDecr : foundGems) {
                            itemToDecr.getEntityItem().stackSize--;
                        }
                    }
                }
            }
            if (getDoubleResult(stack) != null) {
                resultStack = getDoubleResult(stack);
                resultStack.stackSize = oreMultFactor();
                //Kill the stack
                if (stack.stackSize == 0) {
                    entityItem.setDead();
                } else {
                    stack.stackSize--;
                }
            }

            //Spawn in world
            if (spawnInWorld(resultStack, entityItem)) {
                return;
            }
        }
    }

    public boolean spawnInWorld(ItemStack resultStack, EntityItem entityItem) {
        if (resultStack != null) {
            EntityItem newEntity = new EntityItem(worldObj, entityItem.posX, entityItem.posY, entityItem.posZ, resultStack);

            newEntity.delayBeforeCanPickup = entityItem.delayBeforeCanPickup;
            newEntity.motionX = entityItem.motionX;
            newEntity.motionY = entityItem.motionY;
            newEntity.motionZ = entityItem.motionZ;

            worldObj.spawnEntityInWorld(newEntity);

            AuraCascade.proxy.networkWrapper.sendToAllAround(new PacketBurst(1, newEntity.posX, newEntity.posY, newEntity.posZ), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 32));

            return true;
        }
        return false;
    }
}