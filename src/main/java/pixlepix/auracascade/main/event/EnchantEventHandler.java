package pixlepix.auracascade.main.event;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.StringUtils;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.enchant.EnchantmentManager;
import pixlepix.auracascade.main.AuraUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by localmacaccount on 2/14/15.
 */
public class EnchantEventHandler {

    static Method dropCommon;
    static Method dropRare;
    Block[] ores = new Block[]{Blocks.redstone_ore, Blocks.lapis_ore, Blocks.iron_ore, Blocks.gold_ore, Blocks.coal_ore, Blocks.coal_ore, Blocks.diamond_ore, Blocks.emerald_ore, Blocks.lit_redstone_ore, Blocks.quartz_ore};

    public static void init() {
        dropCommon = ReflectionHelper.findMethod(EntityLivingBase.class, null, new String[]{"func_70628_a", "dropFewItems"}, boolean.class, int.class);
        dropRare = ReflectionHelper.findMethod(EntityLivingBase.class, null, new String[]{"func_82164_bB", "addRandomDrop"});
    }

    public static ItemStack getDoubleResult(ItemStack stack) {
        int[] oreIds = OreDictionary.getOreIDs(stack);
        for (int id : oreIds) {
            String oreName = OreDictionary.getOreName(id);
            if (StringUtils.startsWith(oreName, "ore")) {
                String dustName = StringUtils.replace(oreName, "ore", "ingot");
                if (OreDictionary.getOres(dustName).size() != 0) {
                    ItemStack result = OreDictionary.getOres(dustName).get(0);
                    result.stackSize = 2;
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

    public boolean canMultiplyDrops(BlockEvent.HarvestDropsEvent event) {
        for (ItemStack drop : event.drops) {
            if (getDoubleResult(drop) != drop) {
                return true;
            }
        }
        return false;
    }


    //Start event handling
    @SubscribeEvent(priority = EventPriority.LOW)
    public void onHarvestEvent(BlockEvent.HarvestDropsEvent event) {
        Block block = event.state.getBlock();
        World world = event.world;
        if (event.harvester != null && event.harvester.inventory.getCurrentItem() != null) {
            ItemStack stack = event.harvester.inventory.getCurrentItem();
            //Silk touch
            int multiply = getEffectStrength(stack, EnumAura.RED_AURA, EnumAura.YELLOW_AURA);
            if (new Random().nextInt(4) < multiply && canMultiplyDrops(event)) {
                ArrayList newDrops = new ArrayList(event.drops.size());
                for (ItemStack dropStack : event.drops) {
                    newDrops.add(getDoubleResult(dropStack));
                }
                event.drops.clear();
                event.drops.addAll(newDrops);
            } else {
                if (getEffectStrength(stack, EnumAura.RED_AURA) > 0 && block.canSilkHarvest(world, event.pos, event.state, event.harvester)) {
                    event.dropChance = 0;
                    event.drops.clear();
                    ItemStack itemstack = createStackedBlock(event.state);
                    if (itemstack != null) {
                        dropBlockAsItem(world, event.pos, itemstack);
                    }
                } else {

                    int fortune = getEffectStrength(stack, EnumAura.YELLOW_AURA, EnumAura.YELLOW_AURA);
                    //Crops nullifies the fortune level passed to dropBlockAsItemWithChance
                    if (fortune != 0 && event.fortuneLevel < fortune && !(event.state.getBlock() instanceof BlockCrops)) {
                        //Cancels the event and breaks the block again
                        event.dropChance = 0;
                        event.drops.clear();
                        event.state.getBlock().dropBlockAsItemWithChance(event.world, event.pos, event.state, 1F, fortune);
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
            List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(event.target.posX - 2, event.target.posY - 2, event.target.posZ - 2, event.target.posX + 2, event.target.posY + 2, event.target.posZ + 2));
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
        if (knockback > 0 && !event.target.isEntityInvulnerable(DamageSource.causePlayerDamage(player))) { // todo 1.8.8 check damagesource
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
            Block block = event.world.getBlockState(event.pos).getBlock();
            if (block == Blocks.log || block == Blocks.log2 || containsOredict(block, "log")) {
                ArrayList<BlockPos> checkedLocations = new ArrayList<BlockPos>();
                ArrayList<BlockPos> toSearch = new ArrayList<BlockPos>();
                toSearch.add(event.pos);
                while (toSearch.size() > 0 && treeFeller > 0) {
                    BlockPos nextTuple = toSearch.remove(0);
                    event.world.destroyBlock(nextTuple, true);
                    treeFeller--;
                    for (EnumFacing direction : EnumFacing.VALUES) {
                        BlockPos newTuple = nextTuple.offset(direction);
                        if ((event.world.getBlockState(newTuple).getBlock() == block) && !checkedLocations.contains(newTuple)) {
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
            IBlockState state = event.world.getBlockState(event.pos);
            if (state.getBlock() instanceof IGrowable && state.getBlock() != Blocks.grass) {
                ArrayList<BlockPos> checkedLocations = new ArrayList<BlockPos>();
                ArrayList<BlockPos> toSearch = new ArrayList<BlockPos>();
                toSearch.add(event.pos);
                while (toSearch.size() > 0 && harvester > 0) {
                    BlockPos nextTuple = toSearch.remove(0);
                    event.world.destroyBlock(nextTuple, true);
                    harvester--;
                    for (EnumFacing direction : EnumFacing.VALUES) {
                        BlockPos newTuple = nextTuple.offset(direction);
                        if ((event.world.getBlockState(newTuple).getBlock() == state) && !checkedLocations.contains(newTuple)) {
                            toSearch.add(newTuple);
                            checkedLocations.add(newTuple);
                        }
                    }
                }
            }
        }

        //Silk touch handling is done in another location
        //But this prevents XP drops
        int silk = getEffectStrength(stack, EnumAura.RED_AURA);
        if (silk > 0) {
            event.setExpToDrop(0);
        }
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {
        Entity entity = event.source.getSourceOfDamage();
        if (entity instanceof EntityPlayer && !entity.worldObj.isRemote) {
            ItemStack stack = ((EntityPlayer) entity).getHeldItem();
            int looting = getEffectStrength(stack, EnumAura.VIOLET_AURA, EnumAura.YELLOW_AURA);
            if (looting > 0) {
                try {
                    dropCommon.invoke(event.entity, true, looting);
                    int j = new Random().nextInt(200) - looting;

                    if (j < 5) {
                        dropRare.invoke(event.entity, j <= 0 ? 1 : 0);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
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

            if (ForgeHooks.canToolHarvestBlock(event.entityPlayer.worldObj, event.pos, tool)) {
                Block block = event.state.getBlock();
                int efficiency = getEffectStrength(tool, EnumAura.ORANGE_AURA, EnumAura.ORANGE_AURA);
                event.newSpeed *= Math.pow(1.15, efficiency);
                int shatter = getEffectStrength(tool, EnumAura.ORANGE_AURA, EnumAura.VIOLET_AURA);
                if (shatter > 0 && event.state.getBlock().getBlockHardness(event.entity.worldObj, event.pos) >= 3F) {
                    event.newSpeed *= Math.pow(1.5, shatter);
                }

                int oreSpeed = getEffectStrength(tool, EnumAura.RED_AURA, EnumAura.ORANGE_AURA);

                if (oreSpeed > 0 && (Arrays.asList(ores).contains(event.state.getBlock()) || containsOredict(block, "ore"))) {
                    event.newSpeed *= Math.pow(1.25, oreSpeed);
                }


                int stone = getEffectStrength(tool, EnumAura.YELLOW_AURA, EnumAura.ORANGE_AURA);

                if (stone > 0 && Blocks.stone == block) {
                    event.newSpeed *= Math.pow(1.25, stone);
                }

                int logSpeed = getEffectStrength(tool, EnumAura.ORANGE_AURA, EnumAura.GREEN_AURA);
                if (logSpeed > 0 && block == Blocks.log || block == Blocks.log2 || containsOredict(block, "log")) {
                    event.newSpeed *= Math.pow(1.25, logSpeed);
                }

                int digSpeed = getEffectStrength(tool, EnumAura.ORANGE_AURA, EnumAura.GREEN_AURA);
                if (block == Blocks.grass || block == Blocks.dirt || block == Blocks.gravel || block == Blocks.sand) {
                    event.newSpeed *= Math.pow(1.25, digSpeed);

                }

            }
        }
    }

    public boolean containsOredict(Block block, String name) {

        String oreName = OreDictionary.getOreIDs(new ItemStack(block)).length != 0 ? OreDictionary.getOreName(OreDictionary.getOreIDs(new ItemStack(block))[0]) : null;
        return oreName != null && oreName.contains(name);
    }

    //Copied from Block
    public ItemStack createStackedBlock(IBlockState state)
    {
        int i = 0;
        Item item = Item.getItemFromBlock(state.getBlock());

        if (item != null && item.getHasSubtypes())
        {
            i = state.getBlock().getMetaFromState(state);
        }

        return new ItemStack(item, 1, i);
    }

    protected void dropBlockAsItem(World world, BlockPos pos, ItemStack stack) {
        if (!world.isRemote && world.getGameRules().getBoolean("doTileDrops") && !world.restoringBlockSnapshots) { // do not drop items while restoring blockstates, prevents item dupe
            double d0 = AuraUtil.getDropOffset(world);
            double d1 = AuraUtil.getDropOffset(world);
            double d2 = AuraUtil.getDropOffset(world);
            EntityItem entityitem = new EntityItem(world, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, stack);
            AuraUtil.setItemDelay(entityitem, 10);
            world.spawnEntityInWorld(entityitem);
        }
    }
}
