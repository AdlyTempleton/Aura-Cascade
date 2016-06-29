package pixlepix.auracascade.main.event;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.StringUtils;
import pixlepix.auracascade.data.EnumRainbowColor;
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
    Block[] ores = new Block[]{Blocks.REDSTONE_ORE, Blocks.LAPIS_ORE, Blocks.IRON_ORE, Blocks.GOLD_ORE, Blocks.COAL_ORE, Blocks.DIAMOND_ORE, Blocks.EMERALD_ORE, Blocks.LIT_REDSTONE_ORE, Blocks.QUARTZ_ORE};

    public static void init() {
    	//TODO FIX THESE. FUCK REFLECTION
     //   dropCommon = ReflectionHelper.findMethod(EntityLivingBase.class, null, new String[]{"func_70628_a", "dropFewItems"}, boolean.class, int.class);
        //dropRare = ReflectionHelper.findMethod(EntityLivingBase.class, null, new String[]{"func_82164_bB", "addRandomDrop"});
        
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
                EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.red, stack),
                EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.orange, stack),
                EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.yellow, stack),
                EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.green, stack),
                EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.blue, stack),
                EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.purple, stack)
        };
    }

    public int getIndexFromAura(EnumRainbowColor aura) {
        if (aura == EnumRainbowColor.RED) {
            return 0;
        }
        if (aura == EnumRainbowColor.ORANGE) {
            return 1;
        }
        if (aura == EnumRainbowColor.YELLOW) {
            return 2;
        }
        if (aura == EnumRainbowColor.GREEN) {
            return 3;
        }
        if (aura == EnumRainbowColor.BLUE) {
            return 4;
        }
        if (aura == EnumRainbowColor.VIOLET) {
            return 5;
        }
        return -1;
    }

    public int getEffectStrength(ItemStack stack, EnumRainbowColor aura) {
        return getEffectData(stack)[getIndexFromAura(aura)];
    }

    public int getEffectStrength(ItemStack stack, EnumRainbowColor aura, EnumRainbowColor aura2) {
        return (int) Math.ceil(Math.sqrt(getEffectStrength(stack, aura) * getEffectStrength(stack, aura2)));
    }

    public boolean canMultiplyDrops(BlockEvent.HarvestDropsEvent event) {
        for (ItemStack drop : event.getDrops()) {
            if (getDoubleResult(drop) != drop) {
                return true;
            }
        }
        return false;
    }


    //Start event handling
    @SubscribeEvent(priority = EventPriority.LOW)
    public void onHarvestEvent(BlockEvent.HarvestDropsEvent event) {
        Block block = event.getState().getBlock();
        World world = event.getWorld();
        if (event.getHarvester() != null && event.getHarvester().inventory.getCurrentItem() != null) {
            ItemStack stack = event.getHarvester().inventory.getCurrentItem();
            //Silk touch
            int multiply = getEffectStrength(stack, EnumRainbowColor.RED, EnumRainbowColor.YELLOW);
            if (new Random().nextInt(4) < multiply && canMultiplyDrops(event)) {
                ArrayList<ItemStack> newDrops = new ArrayList<ItemStack>(event.getDrops().size());
                for (ItemStack dropStack : event.getDrops()) {
                    newDrops.add(getDoubleResult(dropStack));
                }
                event.getDrops().clear();
                event.getDrops().addAll(newDrops);
            } else {
                if (getEffectStrength(stack, EnumRainbowColor.RED) > 0 && block.canSilkHarvest(world, event.getPos(), event.getState(), event.getHarvester())) {
                    event.setDropChance(0);
                    event.getDrops().clear();
                    ItemStack itemstack = createStackedBlock(event.getState());
                    if (itemstack != null) {
                        dropBlockAsItem(world, event.getPos(), itemstack);
                    }
                } else {

                    int fortune = getEffectStrength(stack, EnumRainbowColor.YELLOW, EnumRainbowColor.YELLOW);
                    //Crops nullifies the fortune level passed to dropBlockAsItemWithChance
                    if (fortune != 0 && event.getFortuneLevel() < fortune && !(event.getState().getBlock() instanceof BlockCrops)) {
                        //Cancels the event and breaks the block again
                        event.setDropChance(0);
                        event.getDrops().clear();
                        event.getState().getBlock().dropBlockAsItemWithChance(event.getWorld(), event.getPos(), event.getState(), 1F, fortune);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onAttack(AttackEntityEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        ItemStack tool = player.inventory.getCurrentItem();

        int areaOfEffect = getEffectStrength(tool, EnumRainbowColor.VIOLET, EnumRainbowColor.BLUE);
        if (areaOfEffect != 0) {
            World world = event.getTarget().worldObj;
            List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(event.getTarget().posX - 2, event.getTarget().posY - 2, event.getTarget().posZ - 2, event.getTarget().posX + 2, event.getTarget().posY + 2, event.getTarget().posZ + 2));
            for (EntityLivingBase entityLivingBase : list) {
                if (entityLivingBase != event.getEntityLiving() && entityLivingBase != event.getTarget()) {
                    entityLivingBase.attackEntityFrom(DamageSource.causeIndirectMagicDamage(event.getEntityPlayer(), event.getTarget()), areaOfEffect);
                    int fire = getEffectStrength(tool, EnumRainbowColor.YELLOW, EnumRainbowColor.BLUE);
                    if (fire > 0) {
                        entityLivingBase.setFire(10 * fire);
                    }
                }

            }
        }

        int knockback = getEffectStrength(tool, EnumRainbowColor.BLUE, EnumRainbowColor.BLUE);
        if (knockback > 0 && !event.getTarget().isEntityInvulnerable(DamageSource.causePlayerDamage(player))) { // todo 1.8.8 check damagesource
            event.getTarget().addVelocity((double) (-MathHelper.sin(event.getEntity().rotationYaw * (float) Math.PI / 180.0F) * (float) knockback * 0.5F), 0.1D, (double) (MathHelper.cos(event.getEntity().rotationYaw * (float) Math.PI / 180.0F) * (float) knockback * 0.5F));
        }

        int recoil = getEffectStrength(tool, EnumRainbowColor.RED, EnumRainbowColor.BLUE);
        if (recoil > 0) {
            event.getEntityPlayer().attackEntityFrom(DamageSource.causeIndirectMagicDamage(event.getEntityPlayer(), event.getEntityPlayer()), recoil);
        }

        int lifeSteal = getEffectStrength(tool, EnumRainbowColor.GREEN, EnumRainbowColor.BLUE);
        if (lifeSteal > 0) {
            event.getEntityPlayer().heal((float) Math.ceil(lifeSteal / 2));
        }

        int fire = getEffectStrength(tool, EnumRainbowColor.YELLOW, EnumRainbowColor.BLUE);
        if (fire > 0) {
            event.getTarget().setFire(20 * fire);
        }

    }

    @SubscribeEvent
    public void getDamage(LivingHurtEvent attackEvent) {
        if (attackEvent.getSource() != null && attackEvent.getSource().getEntity() instanceof EntityPlayer) {
            ItemStack tool = ((EntityPlayer) attackEvent.getSource().getEntity()).inventory.getCurrentItem();
            int sharpness = getEffectStrength(tool, EnumRainbowColor.VIOLET, EnumRainbowColor.VIOLET);
            if (sharpness > 0) {
                attackEvent.setAmount((float) (attackEvent.getAmount() + .5 * sharpness));
            }

            int dullness = getEffectStrength(tool, EnumRainbowColor.VIOLET, EnumRainbowColor.GREEN);
            if (dullness > 0) {
                attackEvent.setAmount(attackEvent.getAmount() - dullness);
                if (attackEvent.getAmount() < 0) {
                    attackEvent.setAmount(0);

                }
            }
        }
        if (attackEvent.getEntity() instanceof EntityPlayer) {
            ItemStack heldStack = ((EntityPlayer) attackEvent.getEntity()).inventory.getCurrentItem();
            if (heldStack != null) {
                int protection = getEffectStrength(heldStack, EnumRainbowColor.RED, EnumRainbowColor.VIOLET);
                if (protection > 0) {
                    attackEvent.setAmount((float) (attackEvent.getAmount() * Math.pow(.9, protection)));
                }
            }
        }
    }

    @SubscribeEvent
    public void onBreakBlock(BlockEvent.BreakEvent event) {
        ItemStack stack = event.getPlayer().inventory.getCurrentItem();
        int treeFeller = getEffectStrength(stack, EnumRainbowColor.GREEN, EnumRainbowColor.GREEN) * 25;
        if (treeFeller > 0 && !event.getWorld().isRemote) {
            Block block = event.getWorld().getBlockState(event.getPos()).getBlock();
            if (block == Blocks.LOG || block == Blocks.LOG2 || containsOredict(block, "log")) {
                ArrayList<BlockPos> checkedLocations = new ArrayList<BlockPos>();
                ArrayList<BlockPos> toSearch = new ArrayList<BlockPos>();
                toSearch.add(event.getPos());
                while (toSearch.size() > 0 && treeFeller > 0) {
                    BlockPos nextTuple = toSearch.remove(0);
                    event.getWorld().destroyBlock(nextTuple, true);
                    treeFeller--;
                    for (EnumFacing direction : EnumFacing.VALUES) {
                        BlockPos newTuple = nextTuple.offset(direction);
                        if ((event.getWorld().getBlockState(newTuple).getBlock() == block) && !checkedLocations.contains(newTuple)) {
                            toSearch.add(newTuple);
                            checkedLocations.add(newTuple);
                        }
                    }
                }
            }
        }

        //Similar code to tree feller
        int harvester = getEffectStrength(stack, EnumRainbowColor.GREEN, EnumRainbowColor.YELLOW) * 25;
        if (harvester > 0 && !event.getWorld().isRemote) {
            IBlockState state = event.getWorld().getBlockState(event.getPos());
            if (state.getBlock() instanceof IGrowable && state.getBlock() != Blocks.GRASS) {
                ArrayList<BlockPos> checkedLocations = new ArrayList<BlockPos>();
                ArrayList<BlockPos> toSearch = new ArrayList<BlockPos>();
                toSearch.add(event.getPos());
                while (toSearch.size() > 0 && harvester > 0) {
                    BlockPos nextTuple = toSearch.remove(0);
                    event.getWorld().destroyBlock(nextTuple, true);
                    harvester--;
                    for (EnumFacing direction : EnumFacing.VALUES) {
                        BlockPos newTuple = nextTuple.offset(direction);
                        if ((event.getWorld().getBlockState(newTuple).getBlock() == state) && !checkedLocations.contains(newTuple)) {
                            toSearch.add(newTuple);
                            checkedLocations.add(newTuple);
                        }
                    }
                }
            }
        }

        //Silk touch handling is done in another location
        //But this prevents XP drops
        int silk = getEffectStrength(stack, EnumRainbowColor.RED);
        if (silk > 0) {
            event.setExpToDrop(0);
        }
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {
        Entity entity = event.getSource().getSourceOfDamage();
        if (entity instanceof EntityPlayer && !entity.worldObj.isRemote) {
            ItemStack stack = ((EntityPlayer) entity).inventory.getCurrentItem();
            int looting = getEffectStrength(stack, EnumRainbowColor.VIOLET, EnumRainbowColor.YELLOW);
            if (looting > 0) {
                try {
                    dropCommon.invoke(event.getEntity(), true, looting);
                    int j = new Random().nextInt(200) - looting;

                    if (j < 5) {
                        dropRare.invoke(event.getEntity(), j <= 0 ? 1 : 0);
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @SubscribeEvent
    public void onGetBreakSpeed(PlayerEvent.BreakSpeed event) {
        if (event.getEntityPlayer().inventory.getCurrentItem() != null) {
            ItemStack tool = event.getEntityPlayer().inventory.getCurrentItem();

            int miningDebuff = getEffectStrength(tool, EnumRainbowColor.RED, EnumRainbowColor.GREEN);
            if (miningDebuff > 0) {
                event.setNewSpeed((float) (event.getNewSpeed() / Math.pow(3, miningDebuff)));
            }

            if (ForgeHooks.canToolHarvestBlock(event.getEntityPlayer().worldObj, event.getPos(), tool)) {
                Block block = event.getState().getBlock();
                int efficiency = getEffectStrength(tool, EnumRainbowColor.ORANGE, EnumRainbowColor.ORANGE);
                event.setNewSpeed((float) (event.getNewSpeed() * Math.pow(1.15, efficiency)));
                int shatter = getEffectStrength(tool, EnumRainbowColor.ORANGE, EnumRainbowColor.VIOLET);
                if (shatter > 0 && event.getState().getBlock().getBlockHardness(event.getState(), event.getEntity().worldObj, event.getPos()) >= 3F) {
                    event.setNewSpeed((float) (event.getNewSpeed() * Math.pow(1.5, shatter)));
                }

                int oreSpeed = getEffectStrength(tool, EnumRainbowColor.RED, EnumRainbowColor.ORANGE);

                if (oreSpeed > 0 && (Arrays.asList(ores).contains(event.getState().getBlock()) || containsOredict(block, "ore"))) {
                    event.setNewSpeed((float) (event.getNewSpeed() * Math.pow(1.25, oreSpeed)));
                }


                int stone = getEffectStrength(tool, EnumRainbowColor.YELLOW, EnumRainbowColor.ORANGE);

                if (stone > 0 && Blocks.STONE == block) {
                    event.setNewSpeed((float) (event.getNewSpeed() * Math.pow(1.25, stone)));
                }

                int logSpeed = getEffectStrength(tool, EnumRainbowColor.ORANGE, EnumRainbowColor.GREEN);
                if (logSpeed > 0 && block == Blocks.LOG || block == Blocks.LOG2 || containsOredict(block, "log")) {
                    event.setNewSpeed((float) (event.getNewSpeed() * Math.pow(1.25, logSpeed)));
                }

                int digSpeed = getEffectStrength(tool, EnumRainbowColor.ORANGE, EnumRainbowColor.GREEN);
                if (block == Blocks.GRASS || block == Blocks.DIRT || block == Blocks.GRAVEL || block == Blocks.SAND) {
                    event.setNewSpeed((float) (event.getNewSpeed() * Math.pow(1.25, digSpeed)));

                }

            }
        }
    }

    public boolean containsOredict(Block block, String name) {
        if (block == null || Item.getItemFromBlock(block) == null) {
            return false;
        }
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
