package pixlepix.auracascade.main.event;

import baubles.api.BaublesApi;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBookshelf;
import net.minecraft.block.IGrowable;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.StringUtils;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.block.entity.EntityDigFairy;
import pixlepix.auracascade.block.tile.TileStorageBookshelf;
import pixlepix.auracascade.data.CoordTuple;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.data.IAngelsteelTool;
import pixlepix.auracascade.enchant.EnchantmentManager;
import pixlepix.auracascade.item.AngelsteelToolHelper;
import pixlepix.auracascade.item.ItemFairyRing;
import scala.actors.threadpool.Arrays;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by localmacaccount on 2/14/15.
 */
public class EnchantEventHandler {

    Block[] ores = new Block[]{Blocks.redstone_ore, Blocks.lapis_ore, Blocks.iron_ore, Blocks.gold_ore, Blocks.coal_ore, Blocks.coal_ore, Blocks.diamond_ore, Blocks.emerald_ore, Blocks.lit_redstone_ore, Blocks.quartz_ore};

    public static ItemStack getTripleResult(ItemStack stack) {
        int[] oreIds = OreDictionary.getOreIDs(stack);
        for (int id : oreIds) {
            String oreName = OreDictionary.getOreName(id);
            if (StringUtils.startsWith(oreName, "ore")) {
                String dustName = StringUtils.replace(oreName, "ore", "ingot");
                if (OreDictionary.getOres(dustName).size() != 0) {
                    ItemStack result = OreDictionary.getOres(dustName).get(0);
                    result.stackSize = 3;
                    return result;
                }
            }
        }
        return stack;
    }

    public int[] getEffectData(ItemStack stack) {
        return new int[]{
                EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.red.effectId, stack),
                EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.orange.effectId, stack),
                EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.yellow.effectId, stack),
                EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.green.effectId, stack),
                EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.blue.effectId, stack),
                EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.purple.effectId, stack)
        };
    }

    public int getIndexFromAura(EnumAura aura) {
        if (aura == EnumAura.RED_AURA) {
            return 0;
        }
        if (aura == EnumAura.ORANGE_AURA) {
            return 1;
        }
        if (aura == EnumAura.YELLOW_AURA) {
            return 2;
        }
        if (aura == EnumAura.GREEN_AURA) {
            return 3;
        }
        if (aura == EnumAura.BLUE_AURA) {
            return 4;
        }
        if (aura == EnumAura.VIOLET_AURA) {
            return 5;
        }
        return -1;
    }

    public int getEffectStrength(ItemStack stack, EnumAura aura) {
        return getEffectData(stack)[getIndexFromAura(aura)];
    }

    public int getEffectStrength(ItemStack stack, EnumAura aura, EnumAura aura2) {
        return (int) Math.ceil(Math.sqrt(getEffectStrength(stack, aura) * getEffectStrength(stack, aura2)));
    }

    //Start event handling
    @SubscribeEvent(priority = EventPriority.LOW)
    public void onHarvestEvent(BlockEvent.HarvestDropsEvent event) {
        Block block = event.block;
        World world = event.world;
        if (event.harvester != null && event.harvester.inventory.getCurrentItem() != null) {
            ItemStack stack = event.harvester.inventory.getCurrentItem();
            //Silk touch
            int multiply = getEffectStrength(stack, EnumAura.RED_AURA, EnumAura.YELLOW_AURA);
            if (new Random().nextInt(4) < multiply) {
                ArrayList newDrops = new ArrayList(event.drops.size());
                for (ItemStack dropStack : event.drops) {
                    newDrops.add(getTripleResult(dropStack));
                }
                event.drops.clear();
                event.drops.addAll(newDrops);
            } else {
                if (getEffectStrength(stack, EnumAura.RED_AURA) > 0 && block.canSilkHarvest(world, event.harvester, event.x, event.y, event.z, event.blockMetadata)) {
                    event.dropChance = 0;
                    event.drops.clear();
                    ItemStack itemstack = createStackedBlock(block, event.blockMetadata);
                    if (itemstack != null) {
                        dropBlockAsItem(world, event.x, event.y, event.z, itemstack);
                    }
                } else {

                    int fortune = getEffectStrength(stack, EnumAura.YELLOW_AURA, EnumAura.YELLOW_AURA);
                    if (fortune != 0 && event.fortuneLevel < fortune) {
                        //Cancels the event and breaks the block again
                        event.dropChance = 0;
                        event.drops.clear();
                        event.block.dropBlockAsItemWithChance(event.world, event.x, event.y, event.z, event.blockMetadata, 1F, fortune);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onAttack(AttackEntityEvent event) {
        EntityPlayer player = event.entityPlayer;
        ItemStack tool = player.getHeldItem();

        int areaOfEffect = getEffectStrength(tool, EnumAura.VIOLET_AURA, EnumAura.BLUE_AURA);
        if (areaOfEffect != 0) {
            World world = event.target.worldObj;
            List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(event.target.posX - 2, event.target.posY - 2, event.target.posZ - 2, event.target.posX + 2, event.target.posY + 2, event.target.posZ + 2));
            for (EntityLivingBase entityLivingBase : list) {
                if (entityLivingBase != event.entityLiving && entityLivingBase != event.target) {
                    entityLivingBase.attackEntityFrom(DamageSource.causeIndirectMagicDamage(event.entityPlayer, event.target), areaOfEffect);
                    int fire = getEffectStrength(tool, EnumAura.YELLOW_AURA, EnumAura.BLUE_AURA);
                    if (fire > 0) {
                        entityLivingBase.setFire(10 * fire);
                    }
                }

            }
        }
        
        int knockback = getEffectStrength(tool, EnumAura.BLUE_AURA, EnumAura.BLUE_AURA);
        if (knockback > 0) {
            event.target.addVelocity((double) (-MathHelper.sin(event.entity.rotationYaw * (float) Math.PI / 180.0F) * (float) knockback * 0.5F), 0.1D, (double) (MathHelper.cos(event.entity.rotationYaw * (float) Math.PI / 180.0F) * (float) knockback * 0.5F));
        }

        int recoil = getEffectStrength(tool, EnumAura.RED_AURA, EnumAura.BLUE_AURA);
        if (recoil > 0) {
            event.entityPlayer.attackEntityFrom(DamageSource.causeIndirectMagicDamage(event.entityPlayer, event.entityPlayer), recoil);
        }

        int lifeSteal = getEffectStrength(tool, EnumAura.GREEN_AURA, EnumAura.BLUE_AURA);
        if (lifeSteal > 0) {
            event.entityPlayer.heal((float) Math.ceil(lifeSteal / 2));
        }

        int fire = getEffectStrength(tool, EnumAura.YELLOW_AURA, EnumAura.BLUE_AURA);
        if (fire > 0) {
            event.target.setFire(20 * fire);
        }

    }

    @SubscribeEvent
    public void getDamage(LivingHurtEvent attackEvent) {
        if (attackEvent.source != null && attackEvent.source.getEntity() instanceof EntityPlayer) {
            ItemStack tool = ((EntityPlayer) attackEvent.source.getEntity()).getHeldItem();
            int sharpness = getEffectStrength(tool, EnumAura.VIOLET_AURA, EnumAura.VIOLET_AURA);
            if (sharpness > 0) {
                attackEvent.ammount += .5 * sharpness;
            }

            int dullness = getEffectStrength(tool, EnumAura.VIOLET_AURA, EnumAura.GREEN_AURA);
            if (dullness > 0) {
                attackEvent.ammount -= dullness;
                if (attackEvent.ammount < 0) {
                    attackEvent.ammount = 0;

                }
            }
        }
        if (attackEvent.entity instanceof EntityPlayer) {
            ItemStack heldStack = ((EntityPlayer) attackEvent.entity).getHeldItem();
            if (heldStack != null) {
                int protection = getEffectStrength(heldStack, EnumAura.RED_AURA, EnumAura.VIOLET_AURA);
                if (protection > 0) {
                    attackEvent.ammount *= Math.pow(.9, protection);
                }
            }
        }
    }

    @SubscribeEvent
    public void onBreakBlock(BlockEvent.BreakEvent event) {
        ItemStack stack = event.getPlayer().getCurrentEquippedItem();
        int treeFeller = getEffectStrength(stack, EnumAura.GREEN_AURA, EnumAura.GREEN_AURA) * 25;
        if (treeFeller > 0 && !event.world.isRemote) {
            Block block = event.world.getBlock(event.x, event.y, event.z);
            if (block == Blocks.log || block == Blocks.log2 || containsOredict(block, "log")) {
                ArrayList<CoordTuple> checkedLocations = new ArrayList<CoordTuple>();
                ArrayList<CoordTuple> toSearch = new ArrayList<CoordTuple>();
                toSearch.add(new CoordTuple(event.x, event.y, event.z));
                while (toSearch.size() > 0 && treeFeller > 0) {
                    CoordTuple nextTuple = toSearch.remove(0);
                    event.world.func_147480_a(nextTuple.getX(), nextTuple.getY(), nextTuple.getZ(), true);
                    treeFeller--;
                    for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
                        CoordTuple newTuple = nextTuple.add(direction);
                        if ((newTuple.getBlock(event.world) == block) && !checkedLocations.contains(newTuple)) {
                            toSearch.add(newTuple);
                            checkedLocations.add(newTuple);
                        }
                    }
                }
            }
        }

        //Similar code to tree feller
        int harvester = getEffectStrength(stack, EnumAura.GREEN_AURA, EnumAura.YELLOW_AURA) * 25;
        if (harvester > 0 && !event.world.isRemote) {
            Block block = event.world.getBlock(event.x, event.y, event.z);
            int meta = event.world.getBlockMetadata(event.x, event.y, event.z);
            if (block instanceof IGrowable && block != Blocks.grass) {
                ArrayList<CoordTuple> checkedLocations = new ArrayList<CoordTuple>();
                ArrayList<CoordTuple> toSearch = new ArrayList<CoordTuple>();
                toSearch.add(new CoordTuple(event.x, event.y, event.z));
                while (toSearch.size() > 0 && harvester > 0) {
                    CoordTuple nextTuple = toSearch.remove(0);
                    event.world.func_147480_a(nextTuple.getX(), nextTuple.getY(), nextTuple.getZ(), true);
                    harvester--;
                    for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
                        CoordTuple newTuple = nextTuple.add(direction);
                        if ((newTuple.getBlock(event.world) == block) && !checkedLocations.contains(newTuple) && newTuple.getMeta(event.world) == meta) {
                            toSearch.add(newTuple);
                            checkedLocations.add(newTuple);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {
        Entity entity = event.source.getSourceOfDamage();
        if (entity instanceof EntityPlayer) {
            ItemStack stack = ((EntityPlayer) entity).getHeldItem();
            int looting = getEffectStrength(stack, EnumAura.VIOLET_AURA, EnumAura.YELLOW_AURA);
            for (EntityItem item : event.drops) {
                item.getEntityItem().stackSize *= looting;
            }
        }
    }

    @SubscribeEvent
    public void onGetBreakSpeed(PlayerEvent.BreakSpeed event) {
        if (event.entityPlayer.inventory.getCurrentItem() != null) {
            ItemStack tool = event.entityPlayer.inventory.getCurrentItem();

            int miningDebuff = getEffectStrength(tool, EnumAura.RED_AURA, EnumAura.GREEN_AURA);
            if (miningDebuff > 0) {
                event.newSpeed /= Math.pow(3, miningDebuff);
            }
            
            if (ForgeHooks.canToolHarvestBlock(event.block, event.metadata, tool)) {
                Block block = event.block;
                int efficiency = getEffectStrength(tool, EnumAura.ORANGE_AURA, EnumAura.ORANGE_AURA);
                event.newSpeed *= Math.pow(1.3, efficiency);
                int shatter = getEffectStrength(tool, EnumAura.ORANGE_AURA, EnumAura.VIOLET_AURA);
                if (shatter > 0 && event.block.getBlockHardness(event.entity.worldObj, event.x, event.y, event.z) >= 3F) {
                    event.newSpeed *= Math.pow(2, shatter);
                }

                int oreSpeed = getEffectStrength(tool, EnumAura.RED_AURA, EnumAura.ORANGE_AURA);

                if (oreSpeed > 0 && (Arrays.asList(ores).contains(event.block) || containsOredict(block, "ore"))) {
                    event.newSpeed *= Math.pow(1.5, oreSpeed);
                }


                int stone = getEffectStrength(tool, EnumAura.YELLOW_AURA, EnumAura.ORANGE_AURA);

                if (stone > 0 && Blocks.stone == block) {
                    event.newSpeed *= Math.pow(1.5, stone);
                }

                int logSpeed = getEffectStrength(tool, EnumAura.ORANGE_AURA, EnumAura.GREEN_AURA);
                if (logSpeed > 0 && block == Blocks.log || block == Blocks.log2 || containsOredict(block, "log")) {
                    event.newSpeed *= Math.pow(1.4, logSpeed);
                }

                int digSpeed = getEffectStrength(tool, EnumAura.ORANGE_AURA, EnumAura.GREEN_AURA);
                if (block == Blocks.grass || block == Blocks.dirt || block == Blocks.gravel || block == Blocks.sand) {
                    event.newSpeed *= Math.pow(1.4, digSpeed);

                }

            }
        }
    }

    public boolean containsOredict(Block block, String name) {

        String oreName = OreDictionary.getOreIDs(new ItemStack(block)).length != 0 ? OreDictionary.getOreName(OreDictionary.getOreIDs(new ItemStack(block))[0]) : null;
        return oreName != null && oreName.contains(name);
    }

    //Copied from Block
    public ItemStack createStackedBlock(Block block, int meta) {
        int j = 0;
        Item item = Item.getItemFromBlock(block);
        if (item != null && item.getHasSubtypes()) {
            j = meta;
        }
        return new ItemStack(item, 1, j);
    }

    protected void dropBlockAsItem(World world, int x, int y, int z, ItemStack stack) {
        if (!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops") && !world.restoringBlockSnapshots) { // do not drop items while restoring blockstates, prevents item dupe
            float f = 0.7F;
            double d0 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            double d1 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            double d2 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            EntityItem entityitem = new EntityItem(world, (double) x + d0, (double) y + d1, (double) z + d2, stack);
            entityitem.delayBeforeCanPickup = 10;
            world.spawnEntityInWorld(entityitem);
        }
    }
}
