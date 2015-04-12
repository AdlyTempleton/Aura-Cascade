package pixlepix.auracascade.item;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StatCollector;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.network.PacketBurst;
import pixlepix.auracascade.registry.ISpecialCreativeSort;
import pixlepix.auracascade.registry.ITTinkererItem;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pixlepix on 12/21/14.
 */
public class ItemAngelsteelIngot extends Item implements ITTinkererItem, ISpecialCreativeSort {
    public static final String name = "ingotAngelSteel";

    @Override
    public ArrayList<Object> getSpecialParameters() {
        return null;
    }

    @Override
    public void registerIcons(IIconRegister register) {
        itemIcon = register.registerIcon("aura:angelsteel");
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return super.getItemStackDisplayName(stack).replace("%n", StatCollector.translateToLocal(stack.getItemDamage() + ".aurasteel.name"));
    }

    @Override
    public boolean onEntityItemUpdate(EntityItem entityItem) {
        if (!entityItem.worldObj.isRemote && entityItem.getEntityItem().getItemDamage() < AngelsteelToolHelper.MAX_DEGREE - 1) {
            EntityItem[] targetStacks = new EntityItem[3];
            targetStacks[0] = entityItem;
            int i = 1;

            if (entityItem.getEntityItem().stackSize == 2) {
                targetStacks[1] = entityItem;
                i = 2;
            } else if (entityItem.getEntityItem().stackSize >= 3) {
                targetStacks[1] = entityItem;
                targetStacks[2] = entityItem;
                i = 3;
            }

            int degree = entityItem.getEntityItem().getItemDamage();

            if (i != 3) {
                AxisAlignedBB range = AxisAlignedBB.getBoundingBox(entityItem.posX - 1, entityItem.posY - 1, entityItem.posZ - 1, entityItem.posX + 1, entityItem.posY + 1, entityItem.posZ + 1);
                List<EntityItem> entityItems = entityItem.worldObj.getEntitiesWithinAABB(EntityItem.class, range);
                for (EntityItem nearbyItem : entityItems) {
                    ItemStack nearbyStack = nearbyItem.getEntityItem();
                    if (nearbyItem != entityItem && nearbyStack.getItem() == this && nearbyStack.getItemDamage() == degree) {
                        if (nearbyStack.stackSize == 2) {
                            targetStacks[1] = entityItem;
                            i = 2;
                        } else if (nearbyStack.stackSize >= 3) {
                            targetStacks[1] = entityItem;
                            targetStacks[2] = entityItem;
                            i = 3;
                        }

                    }
                }
            }

            if (i == 3) {
                for (EntityItem item : targetStacks) {
                    item.getEntityItem().stackSize--;
                }
                EntityItem item = new EntityItem(entityItem.worldObj, entityItem.posX, entityItem.posY, entityItem.posZ, new ItemStack(this, 1, degree + 1));
                entityItem.worldObj.spawnEntityInWorld(item);
                AuraCascade.proxy.networkWrapper.sendToAllAround(new PacketBurst(1, item.posX, item.posY, item.posZ), new NetworkRegistry.TargetPoint(item.worldObj.provider.dimensionId, item.posX, item.posY, item.posZ, 32));
            }
        }
        return false;
    }

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

    @Override
    public int getCreativeTabPriority() {
        return -5;
    }

    @Override
    public boolean getHasSubtypes() {
        return true;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < AngelsteelToolHelper.MAX_DEGREE; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public int compare(ItemStack stack, ItemStack otherStack) {
        if (otherStack.getItem() instanceof ItemAngelsteelIngot) {
            return (stack.getItemDamage() - otherStack.getItemDamage());
        }
        return stack.getDisplayName().compareToIgnoreCase(otherStack.getDisplayName());
    }
}
