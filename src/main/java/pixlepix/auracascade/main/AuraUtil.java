package pixlepix.auracascade.main;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.block.BlockMonitor;
import pixlepix.auracascade.item.AngelsteelToolHelper;
import pixlepix.auracascade.network.PacketBurst;

import java.util.Comparator;
import java.util.List;

/**
 * Created by pixlepix on 11/29/14.
 */
public class AuraUtil {

    //Code copies from Pahimar
    public static Comparator<ItemStack> comparator = new Comparator<ItemStack>() {
        public int compare(ItemStack itemStack1, ItemStack itemStack2) {
            if (itemStack1 != null && itemStack2 != null) {
// Sort on itemID
                if (Item.getIdFromItem(itemStack1.getItem()) - Item.getIdFromItem(itemStack2.getItem()) == 0) {
// Sort on item
                    if (itemStack1.getItem() == itemStack2.getItem()) {
// Then sort on meta
                        if (itemStack1.getItemDamage() == itemStack2.getItemDamage()) {
// Then sort on NBT
                            if (itemStack1.hasTagCompound() && itemStack2.hasTagCompound()) {
// Then sort on stack size
                                if (ItemStack.areItemStackTagsEqual(itemStack1, itemStack2)) {
                                    return (itemStack1.stackSize - itemStack2.stackSize);
                                } else {
                                    return (itemStack1.getTagCompound().hashCode() - itemStack2.getTagCompound().hashCode());
                                }
                            } else if (!(itemStack1.hasTagCompound()) && itemStack2.hasTagCompound()) {
                                return -1;
                            } else if (itemStack1.hasTagCompound() && !(itemStack2.hasTagCompound())) {
                                return 1;
                            } else {
                                return (itemStack1.stackSize - itemStack2.stackSize);
                            }
                        } else {
                            return (itemStack1.getItemDamage() - itemStack2.getItemDamage());
                        }
                    } else {
                        return itemStack1.getItem().getUnlocalizedName(itemStack1).compareToIgnoreCase(itemStack2.getItem().getUnlocalizedName(itemStack2));
                    }
                } else {
                    return Item.getIdFromItem(itemStack1.getItem()) - Item.getIdFromItem(itemStack2.getItem());
                }
            } else if (itemStack1 != null) {
                return -1;
            } else if (itemStack2 != null) {
                return 1;
            } else {
                return 0;
            }
        }
    };

    public static void keepAlive(TileEntity te, int range) {
        List<EntityItem> nearbyItems = te.getWorld().getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(te.getPos().add(-range, -range, -range), te.getPos().add(range, range, range)));
        for (EntityItem entityItem : nearbyItems) {
            entityItem.lifespan = Integer.MAX_VALUE;
            setItemAge(entityItem, 0);
        }
    }

    public static String formatLocation(TileEntity te) {
        return te.getPos().getX() + "|" + te.getPos().getY() + "|" + te.getPos().getZ();
    }

    public static String formatLocation(BlockPos te) {
        return te.getX() + "|" + te.getY() + "|" + te.getZ();
    }

    public static String formatLocation(Entity entity) {
        return entity.posX + "|" + entity.posY + "|" + entity.posZ;

    }
    

    public static void updateMonitor(World w, BlockPos pos) {
        for (EnumFacing d1 : EnumFacing.VALUES) {
            Block b = w.getBlockState(pos.offset(d1)).getBlock();
            if (b instanceof BlockMonitor) {

                for (EnumFacing d2 : EnumFacing.VALUES) {
                    Block b2 = w.getBlockState(pos.offset(d1).offset(d2)).getBlock();
                    b2.onNeighborBlockChange(w, pos.offset(d1).offset(d2), w.getBlockState(pos.offset(d1).offset(d2)), b);
                }
            }
        }
    }

    public static void respawnItemWithParticles(World worldObj, EntityItem oldItem, ItemStack stack) {
        EntityItem newEntity = new EntityItem(worldObj, oldItem.posX, oldItem.posY, oldItem.posZ, stack);

        setItemDelay(newEntity, getItemDelay(oldItem));
        newEntity.motionX = oldItem.motionX;
        newEntity.motionY = oldItem.motionY;
        newEntity.motionZ = oldItem.motionZ;

        worldObj.spawnEntityInWorld(newEntity);

        AuraCascade.proxy.networkWrapper.sendToAllAround(new PacketBurst(1, newEntity.posX, newEntity.posY, newEntity.posZ), new NetworkRegistry.TargetPoint(worldObj.provider.getDimensionId(), (int) oldItem.posX, (int) oldItem.posY, (int) oldItem.posZ, 32));


    }

    public static ItemStack decrStackSize(IInventory tile, int slot, int amt) {
        ItemStack stack = tile.getStackInSlot(slot);
        if (stack != null) {
            if (stack.stackSize <= amt) {
                tile.setInventorySlotContents(slot, null);
            } else {
                stack = stack.splitStack(amt);
                if (stack.stackSize == 0) {
                    tile.setInventorySlotContents(slot, null);
                }
            }
        }

        ((TileEntity) tile).getWorld().markBlockForUpdate(((TileEntity) tile).getPos());
        return stack;
    }

    public static void addAngelsteelDesc(List infoList, ItemStack stack) {
        if (AngelsteelToolHelper.hasValidBuffs(stack)) {
            int[] buffs = AngelsteelToolHelper.readFromNBT(stack.getTagCompound());
            infoList.add("Angel's Efficiency: " + buffs[0]);
            infoList.add("Angel's Fortune: " + buffs[1]);
            infoList.add("Angel's Shatter: " + buffs[2]);
            infoList.add("Angel's Disintegrate: " + buffs[3]);
        }

    }

    public static double getDropOffset(World w) {
        return (w.rand.nextFloat() * .7) + (double) (1.0F - .7) * 0.5D;
    }

    public static void diamondBurst(Entity entity, String particle) {
        BlockPos centerPos = new BlockPos(entity).up();
        BlockPos topPos = centerPos.up(5);
        for (EnumFacing e : EnumFacing.HORIZONTALS) {
            EnumFacing primaryDirection = e;
            EnumFacing connectingDirection = e.rotateYCCW();
            BlockPos corner = centerPos.offset(primaryDirection, 5);
            BlockPos connectingCorner = centerPos.offset(connectingDirection, 5);
            AuraCascade.proxy.networkWrapper.sendToAllAround(new PacketBurst(corner, centerPos, particle, 1, 1, 1, 1), new NetworkRegistry.TargetPoint(entity.worldObj.provider.getDimensionId(), entity.posX, entity.posY, entity.posZ, 32));
            AuraCascade.proxy.networkWrapper.sendToAllAround(new PacketBurst(corner, connectingCorner, particle, 1, 1, 1, 1), new NetworkRegistry.TargetPoint(entity.worldObj.provider.getDimensionId(), entity.posX, entity.posY, entity.posZ, 32));
            AuraCascade.proxy.networkWrapper.sendToAllAround(new PacketBurst(corner, topPos, particle, 1, 1, 1, 1), new NetworkRegistry.TargetPoint(entity.worldObj.provider.getDimensionId(), entity.posX, entity.posY, entity.posZ, 32));

        }
    }

    public static int getItemAge(EntityItem item) {
        return ((Integer) ObfuscationReflectionHelper.getPrivateValue(EntityItem.class, item, "age", "field_70292_b", "c"));
    }

    public static void setItemAge(EntityItem item, int age) {
        ObfuscationReflectionHelper.setPrivateValue(EntityItem.class, item, age, "age", "field_70292_b", "c");
    }

    public static int getItemDelay(EntityItem item) {
        return ((Integer) ObfuscationReflectionHelper.getPrivateValue(EntityItem.class, item, "delayBeforeCanPickup", "field_145804_b", "d"));
    }

    public static void setItemDelay(EntityItem item, int age) {
        ObfuscationReflectionHelper.setPrivateValue(EntityItem.class, item, age, "delayBeforeCanPickup", "field_145804_b", "d");
    }
}
