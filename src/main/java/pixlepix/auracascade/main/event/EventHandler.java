package pixlepix.auracascade.main.event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.block.entity.EntityDigFairy;
import pixlepix.auracascade.block.entity.EntityFallFairy;
import pixlepix.auracascade.block.entity.EntityScareFairy;
import pixlepix.auracascade.block.tile.AuraTilePumpFall;
import pixlepix.auracascade.data.IAngelsteelTool;
import pixlepix.auracascade.data.PosUtil;
import pixlepix.auracascade.data.QuestData;
import pixlepix.auracascade.item.AngelsteelToolHelper;
import pixlepix.auracascade.item.ItemBlueAmulet;
import pixlepix.auracascade.item.ItemComboSword;
import pixlepix.auracascade.item.ItemExplosionRing;
import pixlepix.auracascade.item.ItemFairyRing;
import pixlepix.auracascade.item.ItemFoodAmulet;
import pixlepix.auracascade.item.ItemGreenAmulet;
import pixlepix.auracascade.item.ItemLexicon;
import pixlepix.auracascade.item.ItemOrangeAmulet;
import pixlepix.auracascade.item.ItemPurpleAmulet;
import pixlepix.auracascade.item.ItemRedAmulet;
import pixlepix.auracascade.item.ItemThiefSword;
import pixlepix.auracascade.item.ItemYellowAmulet;
import pixlepix.auracascade.main.Config;
import pixlepix.auracascade.network.PacketSyncQuestData;
import pixlepix.auracascade.registry.BlockRegistry;

/**
 * Created by pixlepix on 12/16/14.
 */
public class EventHandler {
    public static final String BOOK_TAG = "HAS_RECEIVED_AURA_BOOK";
    public ArrayList<EntityScareFairy> scareFairies = new ArrayList<EntityScareFairy>();

    //Helper method
    public static ItemStack getBaubleFromInv(Class<? extends IBauble> clazz, EntityPlayer player) {
        IInventory inv = BaublesApi.getBaubles(player);
        for (int i = 0; i < 4; i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack != null && clazz.isInstance(stack.getItem())) {
                return stack;
            }
        }
        return null;
    }

    //Lexicon auto give
    @SubscribeEvent
    public void onWorldLoad(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event) {
        EntityPlayer player = event.player;
        if (!player.worldObj.isRemote && Config.giveBook) {
            if (!player.getEntityData().hasKey(BOOK_TAG) || !player.getEntityData().getBoolean(BOOK_TAG)) {
                player.getEntityData().setBoolean(BOOK_TAG, true);
                player.inventory.addItemStackToInventory(new ItemStack(BlockRegistry.getFirstItemFromClass(ItemLexicon.class)));
            }

        }

    }

    //QuestData management
    @SubscribeEvent
    public void constructEntity(EntityEvent.EntityConstructing event) {
        if (event.entity instanceof EntityPlayer && event.entity.getExtendedProperties(QuestData.EXT_PROP_NAME) == null) {
            QuestData.register((EntityPlayer) event.entity);
        }
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
        NBTTagCompound compound = new NBTTagCompound();
        event.original.getExtendedProperties(QuestData.EXT_PROP_NAME).saveNBTData(compound);
        event.entityPlayer.getExtendedProperties(QuestData.EXT_PROP_NAME).loadNBTData(compound);
    }

    //Lexicon auto give
    @SubscribeEvent
    public void onPlayerRespawn(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent event) {
        EntityPlayer player = event.player;
        player.getEntityData().setBoolean(BOOK_TAG, true);
        AuraCascade.proxy.networkWrapper.sendTo(new PacketSyncQuestData(event.player), (EntityPlayerMP) event.player);


    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (event.entity instanceof EntityPlayerMP && !event.entity.worldObj.isRemote) {
            AuraCascade.proxy.networkWrapper.sendTo(new PacketSyncQuestData((EntityPlayer) event.entity), (EntityPlayerMP) event.entity);
        }
    }


    //Amulet of the shattered stone
    @SubscribeEvent
    public void onExplode(ExplosionEvent.Detonate event) {
        List<Block> affectedBlocks = Arrays.asList(Blocks.grass, Blocks.sandstone, Blocks.stone, Blocks.sand, Blocks.dirt, Blocks.cobblestone, Blocks.gravel);
        if (!event.world.isRemote) {
            Explosion explosion = event.explosion;
            AxisAlignedBB axisAlignedBB = new AxisAlignedBB(explosion.getPosition().xCoord - 3, explosion.getPosition().yCoord - 3, explosion.getPosition().zCoord - 3, explosion.getPosition().xCoord + 3, explosion.getPosition().yCoord + 3, explosion.getPosition().zCoord + 3);
            List<EntityPlayer> players = event.world.getEntitiesWithinAABB(EntityPlayer.class, axisAlignedBB);
            for (EntityPlayer player : players) {
                if (getBaubleFromInv(ItemExplosionRing.class, player) != null) {
                    Iterator<BlockPos> iterator = explosion.getAffectedBlockPositions().iterator();
                    while (iterator.hasNext()) {
                        BlockPos position = iterator.next();
                        Block block = event.world.getBlockState(position).getBlock();
                        if (!affectedBlocks.contains(block) && block != Blocks.air) {
                            iterator.remove();
                        }
                    }
                }
            }
        }
    }


    //Scarer Fairies
    @SubscribeEvent
    public void onLivingSpawn(LivingSpawnEvent.CheckSpawn event) {
        int scareCount = 0;
        for (Entity entity : scareFairies) {
            if (entity.worldObj == event.world && entity.getDistance(event.x, event.y, event.z) < 50) {
                scareCount += 1;
            }
        }
        Random random = new Random();
        if (scareCount > 0) {
            if (random.nextInt(25) <= scareCount) {
                event.setResult(Event.Result.DENY);
            }
        }
    }

    //Amulets of protection
    @SuppressWarnings("ConstantConditions")
    @SubscribeEvent
    public void onEntityAttacked(LivingHurtEvent event) {
        if (event.entity instanceof EntityPlayer && (event.source == DamageSource.lava || event.source == DamageSource.onFire || event.source == DamageSource.inFire)) {
            ItemStack stack = getBaubleFromInv(ItemRedAmulet.class, (EntityPlayer) event.entity);
            if (stack != null) {
                if (event.source != DamageSource.lava) {
                    ((EntityPlayer) event.entity).heal(event.ammount);
                }
                event.ammount = 0;
            }
        }
        if (event.source != null && event.source.getEntity() instanceof EntityPlayer) {
            ItemStack stack = ((EntityPlayer) event.source.getEntity()).inventory.getCurrentItem();
            if (stack != null && stack.getItem() instanceof ItemComboSword) {
                if (stack.getTagCompound() == null) {
                    stack.setTagCompound(new NBTTagCompound());
                }
                int timeDiff = (int) Math.abs(event.entity.worldObj.getTotalWorldTime() - stack.getTagCompound().getLong(ItemComboSword.NBT_TAG_LAST_TIME));

                if (timeDiff < 100 && timeDiff > 4) {
                    int combo = stack.getTagCompound().getInteger(ItemComboSword.NBT_TAG_COMBO_COUNT);

                    double comboMultiplier = ItemComboSword.getComboMultiplier(combo);
                    event.ammount *= comboMultiplier;
                    if (combo < 100) {
                        stack.getTagCompound().setInteger(ItemComboSword.NBT_TAG_COMBO_COUNT, stack.getTagCompound().getInteger(ItemComboSword.NBT_TAG_COMBO_COUNT) + 1);
                    }
                } else {
                    stack.getTagCompound().setInteger(ItemComboSword.NBT_TAG_COMBO_COUNT, 0);
                }
                stack.getTagCompound().setLong(ItemComboSword.NBT_TAG_LAST_TIME, event.entity.worldObj.getTotalWorldTime());
            }
        }
        if (event.entity instanceof EntityPlayer && event.source.isExplosion()) {
            ItemStack stack = getBaubleFromInv(ItemOrangeAmulet.class, (EntityPlayer) event.entity);
            if (stack != null) {
                ((EntityPlayer) event.entity).heal(event.ammount);
                event.ammount = 0;
            }
        }
        if (event.entity instanceof EntityPlayer && event.source.isProjectile()) {
            ItemStack stack = getBaubleFromInv(ItemYellowAmulet.class, (EntityPlayer) event.entity);
            if (stack != null) {
                event.ammount /= 2;
            }
        }
        if (event.entity instanceof EntityPlayer && event.source == DamageSource.fall) {
            ItemStack stack = getBaubleFromInv(ItemGreenAmulet.class, (EntityPlayer) event.entity);
            if (stack != null) {
                ((EntityPlayer) event.entity).heal(event.ammount);
                event.ammount = 0;
            }
        }
        if (event.entity instanceof EntityPlayer && event.source == DamageSource.drown) {
            ItemStack stack = getBaubleFromInv(ItemBlueAmulet.class, (EntityPlayer) event.entity);
            if (stack != null) {
                ((EntityPlayer) event.entity).heal(event.ammount);
                event.ammount = 0;
            }
        }
        if (event.entity instanceof EntityPlayer && event.source == DamageSource.wither) {
            ItemStack stack = getBaubleFromInv(ItemPurpleAmulet.class, (EntityPlayer) event.entity);
            if (stack != null) {
                event.ammount = 0;
            }
        }
    }

    //Sword of the Thief
    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event) {
        if (!event.entity.worldObj.isRemote && event.source.getSourceOfDamage() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.source.getSourceOfDamage();
            ItemStack swordStack = player.inventory.getCurrentItem();
            if (swordStack != null && swordStack.getItem() instanceof ItemThiefSword) {
                if (event.entity instanceof EntityVillager && new Random().nextInt(4) == 0) {
                    EntityVillager villager = (EntityVillager) event.entity;
                    ItemStack dropStack = villager.getRecipes(player).get(0).getItemToSell();
                    EntityItem entityItem = new EntityItem(player.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, dropStack);
                    event.drops.add(entityItem);
                }
            }
        }
    }

    //Differ fairies
    //Angelsteel tool speed buffs
    @SubscribeEvent
    public void onGetBreakSpeed(PlayerEvent.BreakSpeed event) {
        ItemStack item = BaublesApi.getBaubles(event.entityPlayer).getStackInSlot(1);
        if (item != null && item.getItem() instanceof ItemFairyRing && !event.entityPlayer.worldObj.isRemote) {
            List<EntityDigFairy> fairyList = event.entityPlayer.worldObj.getEntitiesWithinAABB(EntityDigFairy.class, event.entityPlayer.getEntityBoundingBox().expand(20, 20, 20));
            int count = -1;
            for (EntityDigFairy digFairy : fairyList) {
                if (digFairy.player == event.entityPlayer) {
                    count++;
                }
            }
            count = Math.min(count, 15);
            event.newSpeed *= Math.pow(1.08, count);
        }
        if (event.entityPlayer.inventory.getCurrentItem() != null && AngelsteelToolHelper.isAngelsteelTool(event.entityPlayer.inventory.getCurrentItem().getItem())) {
            if (event.entityPlayer.inventory.getCurrentItem().getTagCompound() == null) {
                event.entityPlayer.inventory.getCurrentItem().setTagCompound(AngelsteelToolHelper.getRandomBuffCompound(((IAngelsteelTool) event.entityPlayer.inventory.getCurrentItem().getItem()).getDegree()));
            }
            ItemStack tool = event.entityPlayer.inventory.getCurrentItem();
            if (ForgeHooks.canToolHarvestBlock(event.entityPlayer.worldObj, event.pos, tool)) {
                int[] buffs = AngelsteelToolHelper.readFromNBT(event.entityPlayer.inventory.getCurrentItem().getTagCompound());
                if (buffs.length > 0) {
                    int efficiency = buffs[0];
                    event.newSpeed *= Math.pow(1.3, efficiency);
                    int shatter = buffs[2];
                    int disintegrate = buffs[3];
                    //1.5F, the hardness of stone, is used as a dividing point
                    //Stone is not affected by either enchant
                    if (event.state.getBlock().getBlockHardness(event.entity.worldObj, event.pos) <= 1F) {
                        event.newSpeed *= Math.pow(3, disintegrate);
                    }
                    if (event.state.getBlock().getBlockHardness(event.entity.worldObj, event.pos) >= 2F) {

                        event.newSpeed *= Math.pow(3, shatter);
                    }
                }
            }
        }
    }

    //Angelsteel fortune buff
    @SubscribeEvent(priority = EventPriority.LOW)
    public void onHarvestEvent(BlockEvent.HarvestDropsEvent event) {

        if (event.harvester != null && event.harvester.inventory.getCurrentItem() != null && AngelsteelToolHelper.isAngelsteelTool(event.harvester.inventory.getCurrentItem().getItem())) {
            if (event.harvester.inventory.getCurrentItem().getTagCompound() == null) {
                event.harvester.inventory.getCurrentItem().setTagCompound(AngelsteelToolHelper.getRandomBuffCompound(((IAngelsteelTool) event.harvester.inventory.getCurrentItem().getItem()).getDegree()));
            }
            int fortune = AngelsteelToolHelper.readFromNBT(event.harvester.inventory.getCurrentItem().getTagCompound())[1];
            if (event.fortuneLevel < fortune) {
                //Cancels the event and breaks the block again
                if (event.dropChance <= 0 && event.drops.size() > 0 && !(event.state.getBlock() instanceof BlockCrops)) {
                    event.dropChance = 0;
                    event.drops.clear();
                    event.state.getBlock().dropBlockAsItemWithChance(event.world, event.pos, event.state, 1F, fortune);
                }
            }
        }
    }


    //Faller fairy
    @SubscribeEvent
    public void onFall(LivingFallEvent event) {
        if (event.entityLiving instanceof EntityPlayer) {
            EntityPlayer entityPlayer = (EntityPlayer) event.entityLiving;
            ItemStack item = BaublesApi.getBaubles(entityPlayer).getStackInSlot(1);
            if (item != null && item.getItem() instanceof ItemFairyRing && !entityPlayer.worldObj.isRemote) {
                List<EntityFallFairy> fairyList = entityPlayer.worldObj.getEntitiesWithinAABB(EntityFallFairy.class, entityPlayer.getEntityBoundingBox().expand(20, 20, 20));
                int count = 0;
                for (EntityFallFairy fairy : fairyList) {
                    if (fairy.player == entityPlayer) {
                        count++;
                    }
                }
                event.distance *= Math.pow(.5F, count);
            }
        }

        //Momentum pump
        BlockPos pos = new BlockPos(event.entity);

        for (BlockPos searchPump : PosUtil.inRange(pos, 3)) {
            if (event.entity.worldObj.getTileEntity(searchPump) instanceof AuraTilePumpFall) {
                ((AuraTilePumpFall) event.entity.worldObj.getTileEntity(searchPump)).onFall(event);
                break;
            }
        }
    }

    //Kill fairies on death
    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event) {
        if (event.entityLiving instanceof EntityPlayer) {
            if (!((EntityPlayer) event.entityLiving).worldObj.getGameRules().getBoolean("keepInventory")) {

                EntityPlayer entityPlayer = (EntityPlayer) event.entityLiving;
                ItemStack item = getBaubleFromInv(ItemFairyRing.class, entityPlayer);
                if (item != null && item.getItem() instanceof ItemFairyRing) {
                    ItemFairyRing.killNearby(entityPlayer);
                }
            }
        }
    }

    //Kill fairies on logout
    @SubscribeEvent
    public void onPlayerLogout(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent event) {
        ItemStack item = getBaubleFromInv(ItemFairyRing.class, event.player);
        if (item != null && item.getItem() instanceof ItemFairyRing && !event.player.worldObj.isRemote) {
            ItemFairyRing.killNearby(event.player);
        }

    }

    //Respawn fairies on login
    @SubscribeEvent
    public void onPlayerLogin(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event) {
        ItemStack item = getBaubleFromInv(ItemFairyRing.class, event.player);
        if (item != null && item.getItem() instanceof ItemFairyRing && !event.player.worldObj.isRemote) {
            ItemFairyRing.makeFaries(item, event.player);
        }

    }

    @SubscribeEvent
    public void onEatEvent(PlayerUseItemEvent.Finish finishEvent) {
        EntityPlayer player = finishEvent.entityPlayer;
        ItemStack heldStack = player.inventory.getCurrentItem();
        if (getBaubleFromInv(ItemFoodAmulet.class, player) != null) {
            //Check if item is food
            if (!player.worldObj.isRemote && heldStack != null && (heldStack.getItem().getItemUseAction(heldStack) == EnumAction.EAT || heldStack.getItem().getItemUseAction(heldStack) == EnumAction.DRINK)) {
                if (heldStack.getItem().getUnlocalizedName().equals("item.apple")) {
                    player.addPotionEffect(new PotionEffect(Potion.wither.field_76415_H, 6 * 60 * 20, 1));
                } else {
                    String name = heldStack.getUnlocalizedName();
                    Random random = new Random(name.hashCode());
                    //Limit within vanilla potions, which go up to 24
                    //Note that there is no potion with id 0
                    Potion potion;
                    do {
                        potion = Potion.field_76425_a[random.nextInt(23) + 1];
                    } while (potion.isInstant());
                    int duration = Math.max(0, (int) (random.nextGaussian() * 20 * 120 + 20 * 60 * 4));
                    int amplified = random.nextInt(6);
                    PotionEffect potionEffect = new PotionEffect(potion.field_76415_H, duration, amplified);
                    player.addPotionEffect(potionEffect);
                }
            }
        }
    }

}
