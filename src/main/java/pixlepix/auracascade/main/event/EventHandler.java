package pixlepix.auracascade.main.event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import baubles.api.BaublesApi;
import baubles.api.IBauble;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
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
import net.minecraftforge.event.entity.living.*;
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
    //TODO reimplement
    @SubscribeEvent
    public void constructEntity(EntityEvent.EntityConstructing event) {
      //  if (event.getEntity() instanceof EntityPlayer && event.getEntity().getExtendedProperties(QuestData.EXT_PROP_NAME) == null) {
     //       QuestData.register((EntityPlayer) event.getEntity());
     //   }
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
       // NBTTagCompound compound = new NBTTagCompound();
       // event.getOriginal().getExtendedProperties(QuestData.EXT_PROP_NAME).saveNBTData(compound);
       // event.getEntityPlayer().getExtendedProperties(QuestData.EXT_PROP_NAME).loadNBTData(compound);
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
        if (event.getEntity() instanceof EntityPlayerMP && !event.getEntity().worldObj.isRemote) {
            AuraCascade.proxy.networkWrapper.sendTo(new PacketSyncQuestData((EntityPlayer) event.getEntity()), (EntityPlayerMP) event.getEntity());
        }
    }


    //Amulet of the shattered stone
    @SubscribeEvent
    public void onExplode(ExplosionEvent.Detonate event) {
        List<Block> affectedBlocks = Arrays.asList(Blocks.GRASS, Blocks.SANDSTONE, Blocks.STONE, Blocks.SAND, Blocks.DIRT, Blocks.COBBLESTONE, Blocks.GRAVEL);
        if (!event.getWorld().isRemote) {
            Explosion explosion = event.getExplosion();
            AxisAlignedBB axisAlignedBB = new AxisAlignedBB(explosion.getPosition().xCoord - 3, explosion.getPosition().yCoord - 3, explosion.getPosition().zCoord - 3, explosion.getPosition().xCoord + 3, explosion.getPosition().yCoord + 3, explosion.getPosition().zCoord + 3);
            List<EntityPlayer> players = event.getWorld().getEntitiesWithinAABB(EntityPlayer.class, axisAlignedBB);
            for (EntityPlayer player : players) {
                if (getBaubleFromInv(ItemExplosionRing.class, player) != null) {
                    Iterator<BlockPos> iterator = explosion.getAffectedBlockPositions().iterator();
                    while (iterator.hasNext()) {
                        BlockPos position = iterator.next();
                        Block block = event.getWorld().getBlockState(position).getBlock();
                        if (!affectedBlocks.contains(block) && block != Blocks.AIR) {
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
            if (entity.worldObj == event.getWorld() && entity.getDistance(event.getX(), event.getY(), event.getZ()) < 50) {
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
        if (event.getEntity() instanceof EntityPlayer && (event.getSource() == DamageSource.lava || event.getSource() == DamageSource.onFire || event.getSource() == DamageSource.inFire)) {
            ItemStack stack = getBaubleFromInv(ItemRedAmulet.class, (EntityPlayer) event.getEntity());
            if (stack != null) {
                if (event.getSource() != DamageSource.lava) {
                    ((EntityPlayer) event.getEntity()).heal(event.getAmount());
                }
                event.setAmount(0);
            }
        }
        if (event.getSource() != null && event.getSource().getEntity() instanceof EntityPlayer) {
            ItemStack stack = ((EntityPlayer) event.getSource().getEntity()).inventory.getCurrentItem();
            if (stack != null && stack.getItem() instanceof ItemComboSword) {
                if (stack.getTagCompound() == null) {
                    stack.setTagCompound(new NBTTagCompound());
                }
                int timeDiff = (int) Math.abs(event.getEntity().worldObj.getTotalWorldTime() - stack.getTagCompound().getLong(ItemComboSword.NBT_TAG_LAST_TIME));

                if (timeDiff < 100 && timeDiff > 4) {
                    int combo = stack.getTagCompound().getInteger(ItemComboSword.NBT_TAG_COMBO_COUNT);

                    double comboMultiplier = ItemComboSword.getComboMultiplier(combo);
                    event.setAmount((float) (event.getAmount() * comboMultiplier));
                    if (combo < 100) {
                        stack.getTagCompound().setInteger(ItemComboSword.NBT_TAG_COMBO_COUNT, stack.getTagCompound().getInteger(ItemComboSword.NBT_TAG_COMBO_COUNT) + 1);
                    }
                } else {
                    stack.getTagCompound().setInteger(ItemComboSword.NBT_TAG_COMBO_COUNT, 0);
                }
                stack.getTagCompound().setLong(ItemComboSword.NBT_TAG_LAST_TIME, event.getEntity().worldObj.getTotalWorldTime());
            }
        }
        if (event.getEntity() instanceof EntityPlayer && event.getSource().isExplosion()) {
            ItemStack stack = getBaubleFromInv(ItemOrangeAmulet.class, (EntityPlayer) event.getEntity());
            if (stack != null) {
                ((EntityPlayer) event.getEntity()).heal(event.getAmount());
                event.setAmount(0);
            }
        }
        if (event.getEntity() instanceof EntityPlayer && event.getSource().isProjectile()) {
            ItemStack stack = getBaubleFromInv(ItemYellowAmulet.class, (EntityPlayer) event.getEntity());
            if (stack != null) {
                event.setAmount(event.getAmount() / 2);
            }
        }
        if (event.getEntity() instanceof EntityPlayer && event.getSource() == DamageSource.fall) {
            ItemStack stack = getBaubleFromInv(ItemGreenAmulet.class, (EntityPlayer) event.getEntity());
            if (stack != null) {
                ((EntityPlayer) event.getEntity()).heal(event.getAmount());
                event.setAmount(0);
            }
        }
        if (event.getEntity() instanceof EntityPlayer && event.getSource() == DamageSource.drown) {
            ItemStack stack = getBaubleFromInv(ItemBlueAmulet.class, (EntityPlayer) event.getEntity());
            if (stack != null) {
                ((EntityPlayer) event.getEntity()).heal(event.getAmount());
                event.setAmount(0);
            }
        }
        if (event.getEntity() instanceof EntityPlayer && event.getSource() == DamageSource.wither) {
            ItemStack stack = getBaubleFromInv(ItemPurpleAmulet.class, (EntityPlayer) event.getEntity());
            if (stack != null) {
                event.setAmount(0);
            }
        }
    }

    //Sword of the Thief
    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event) {
        if (!event.getEntity().worldObj.isRemote && event.getSource().getSourceOfDamage() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getSource().getSourceOfDamage();
            ItemStack swordStack = player.inventory.getCurrentItem();
            if (swordStack != null && swordStack.getItem() instanceof ItemThiefSword) {
                if (event.getEntity() instanceof EntityVillager && new Random().nextInt(4) == 0) {
                    EntityVillager villager = (EntityVillager) event.getEntity();
                    ItemStack dropStack = villager.getRecipes(player).get(0).getItemToSell();
                    EntityItem entityItem = new EntityItem(player.worldObj, event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ, dropStack);
                    event.getDrops().add(entityItem);
                }
            }
        }
    }

    //Differ fairies
    //Angelsteel tool speed buffs
    @SubscribeEvent
    public void onGetBreakSpeed(PlayerEvent.BreakSpeed event) {
        ItemStack item = BaublesApi.getBaubles(event.getEntityPlayer()).getStackInSlot(1);
        if (item != null && item.getItem() instanceof ItemFairyRing && !event.getEntityPlayer().worldObj.isRemote) {
            List<EntityDigFairy> fairyList = event.getEntityPlayer().worldObj.getEntitiesWithinAABB(EntityDigFairy.class, event.getEntityPlayer().getEntityBoundingBox().expand(20, 20, 20));
            int count = -1;
            for (EntityDigFairy digFairy : fairyList) {
                if (digFairy.player == event.getEntityPlayer()) {
                    count++;
                }
            }
            count = Math.min(count, 15);
            event.setNewSpeed((float) (event.getNewSpeed() * Math.pow(1.08, count)));
        }
        if (event.getEntityPlayer().inventory.getCurrentItem() != null && AngelsteelToolHelper.isAngelsteelTool(event.getEntityPlayer().inventory.getCurrentItem().getItem())) {
            if (event.getEntityPlayer().inventory.getCurrentItem().getTagCompound() == null) {
                event.getEntityPlayer().inventory.getCurrentItem().setTagCompound(AngelsteelToolHelper.getRandomBuffCompound(((IAngelsteelTool) event.getEntityPlayer().inventory.getCurrentItem().getItem()).getDegree()));
            }
            ItemStack tool = event.getEntityPlayer().inventory.getCurrentItem();
            if (ForgeHooks.canToolHarvestBlock(event.getEntityPlayer().worldObj, event.getPos(), tool)) {
                int[] buffs = AngelsteelToolHelper.readFromNBT(event.getEntityPlayer().inventory.getCurrentItem().getTagCompound());
                if (buffs.length > 0) {
                    int efficiency = buffs[0];
                    event.setNewSpeed((float) (event.getNewSpeed() * Math.pow(1.3, efficiency)));
                    int shatter = buffs[2];
                    int disintegrate = buffs[3];
                    //1.5F, the hardness of stone, is used as a dividing point
                    //Stone is not affected by either enchant
                    if (event.getState().getBlock().getBlockHardness(event.getState(), event.getEntity().worldObj, event.getPos()) <= 1F) {
                        event.setNewSpeed((float) (event.getNewSpeed() * Math.pow(3, disintegrate)));
                    }
                    if (event.getState().getBlock().getBlockHardness(event.getState(), event.getEntity().worldObj, event.getPos()) >= 2F) {

                        event.setNewSpeed((float) (event.getNewSpeed() * Math.pow(3, shatter)));
                    }
                }
            }
        }
    }

    //Angelsteel fortune buff
    @SubscribeEvent(priority = EventPriority.LOW)
    public void onHarvestEvent(BlockEvent.HarvestDropsEvent event) {

        if (event.getHarvester() != null && event.getHarvester().inventory.getCurrentItem() != null && AngelsteelToolHelper.isAngelsteelTool(event.getHarvester().inventory.getCurrentItem().getItem())) {
            if (event.getHarvester().inventory.getCurrentItem().getTagCompound() == null) {
                event.getHarvester().inventory.getCurrentItem().setTagCompound(AngelsteelToolHelper.getRandomBuffCompound(((IAngelsteelTool) event.getHarvester().inventory.getCurrentItem().getItem()).getDegree()));
            }
            int fortune = AngelsteelToolHelper.readFromNBT(event.getHarvester().inventory.getCurrentItem().getTagCompound())[1];
            if (event.getFortuneLevel() < fortune) {
                //Cancels the event and breaks the block again
                if (event.getDropChance() <= 0 && event.getDrops().size() > 0 && !(event.getState().getBlock() instanceof BlockCrops)) {
                    event.setDropChance(0);
                    event.getDrops().clear();
                    event.getState().getBlock().dropBlockAsItemWithChance(event.getWorld(), event.getPos(), event.getState(), 1F, fortune);
                }
            }
        }
    }


    //Faller fairy
    @SubscribeEvent
    public void onFall(LivingFallEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer entityPlayer = (EntityPlayer) event.getEntityLiving();
            ItemStack item = BaublesApi.getBaubles(entityPlayer).getStackInSlot(1);
            if (item != null && item.getItem() instanceof ItemFairyRing && !entityPlayer.worldObj.isRemote) {
                List<EntityFallFairy> fairyList = entityPlayer.worldObj.getEntitiesWithinAABB(EntityFallFairy.class, entityPlayer.getEntityBoundingBox().expand(20, 20, 20));
                int count = 0;
                for (EntityFallFairy fairy : fairyList) {
                    if (fairy.player == entityPlayer) {
                        count++;
                    }
                }
                event.setDistance((float) (event.getDistance() * Math.pow(.5F, count)));
            }
        }

        //Momentum pump
        BlockPos pos = new BlockPos(event.getEntity());

        for (BlockPos searchPump : PosUtil.inRange(pos, 3)) {
            if (event.getEntity().worldObj.getTileEntity(searchPump) instanceof AuraTilePumpFall) {
                ((AuraTilePumpFall) event.getEntity().worldObj.getTileEntity(searchPump)).onFall(event);
                break;
            }
        }
    }

    //Kill fairies on death
    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer) {
            if (!((EntityPlayer) event.getEntityLiving()).worldObj.getGameRules().getBoolean("keepInventory")) {

                EntityPlayer entityPlayer = (EntityPlayer) event.getEntityLiving();
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
    public void onEatEvent(LivingEntityUseItemEvent.Finish finishEvent) {
    	try{
	        EntityPlayer player = (EntityPlayer) finishEvent.getEntityLiving();
	        ItemStack heldStack = player.inventory.getCurrentItem();
	        if (getBaubleFromInv(ItemFoodAmulet.class, player) != null) {
	            //Check if item is food
	            if (!player.worldObj.isRemote && heldStack != null && (heldStack.getItem().getItemUseAction(heldStack) == EnumAction.EAT || heldStack.getItem().getItemUseAction(heldStack) == EnumAction.DRINK)) {
	                if (heldStack.getItem().getUnlocalizedName().equals("item.apple")) {
	                    player.addPotionEffect(new PotionEffect(MobEffects.WITHER, 6 * 60 * 20, 1));
	                } else {
	                    String name = heldStack.getUnlocalizedName();
	                    Random random = new Random(name.hashCode());
	                    //Limit within vanilla potions, which go up to 24
	                    //Note that there is no potion with id 0
	                    Potion potion;
	                    do {
	                        potion = Potion.getPotionById(random.nextInt(23) + 1);
	                    } while (potion.isInstant());
	                    int duration = Math.max(0, (int) (random.nextGaussian() * 20 * 120 + 20 * 60 * 4));
	                    int amplified = random.nextInt(6);
	                    PotionEffect potionEffect = new PotionEffect(potion.setBeneficial(), duration, amplified);
	                    player.addPotionEffect(potionEffect);
	                }
	            }
	        }
    	}catch(Exception e){
    		System.out.println("This failed and drastic has failed you. Please report this to the 1.9 repository for Aura Cascade by DrasticDemise.");
    	}
    }

}
