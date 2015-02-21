package pixlepix.auracascade.main.event;

import baubles.api.BaublesApi;
import baubles.api.IBauble;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.village.MerchantRecipe;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import net.minecraftforge.event.world.BlockEvent;
import pixlepix.auracascade.block.entity.EntityDigFairy;
import pixlepix.auracascade.block.entity.EntityFallFairy;
import pixlepix.auracascade.block.entity.EntityScareFairy;
import pixlepix.auracascade.block.tile.AuraTilePumpFall;
import pixlepix.auracascade.data.CoordTuple;
import pixlepix.auracascade.data.IAngelsteelTool;
import pixlepix.auracascade.item.*;
import pixlepix.auracascade.main.Config;
import pixlepix.auracascade.registry.BlockRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    public void onWorldLoad(cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event) {
        EntityPlayer player = event.player;
        if (!player.worldObj.isRemote && Config.giveBook) {
            if (!player.getEntityData().hasKey(BOOK_TAG) || !player.getEntityData().getBoolean(BOOK_TAG)) {
                player.getEntityData().setBoolean(BOOK_TAG, true);
                player.inventory.addItemStackToInventory(new ItemStack(BlockRegistry.getFirstItemFromClass(ItemLexicon.class)));
            }

        }

    }

    //Lexicon auto give
    @SubscribeEvent
    public void onPlayerRespawn(cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent event) {
        EntityPlayer player = event.player;
        player.getEntityData().setBoolean(BOOK_TAG, true);
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
            ItemStack swordStack = player.getCurrentEquippedItem();
            if (swordStack != null && swordStack.getItem() instanceof ItemThiefSword) {
                if (event.entity instanceof EntityVillager && new Random().nextInt(4) == 0) {
                    EntityVillager villager = (EntityVillager) event.entity;
                    ItemStack dropStack = ((MerchantRecipe) villager.getRecipes(player).get(0)).getItemToSell();
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
        if (event.block == Blocks.stone && item != null && item.getItem() instanceof ItemFairyRing && !event.entityPlayer.worldObj.isRemote) {
            List<EntityDigFairy> fairyList = event.entityPlayer.worldObj.getEntitiesWithinAABB(EntityDigFairy.class, event.entityPlayer.boundingBox.expand(20, 20, 20));
            int count = -1;
            for (EntityDigFairy digFairy : fairyList) {
                if (digFairy.player == event.entityPlayer) {
                    count++;
                }
            }
            count = Math.min(count, 15);
            if (new Random().nextInt(50) <= count) {
                event.newSpeed = Float.MAX_VALUE;
            }
        }
        if (event.entityPlayer.inventory.getCurrentItem() != null && AngelsteelToolHelper.isAngelsteelTool(event.entityPlayer.inventory.getCurrentItem().getItem())) {
            if (event.entityPlayer.inventory.getCurrentItem().stackTagCompound == null) {
                event.entityPlayer.inventory.getCurrentItem().stackTagCompound = AngelsteelToolHelper.getRandomBuffCompound(((IAngelsteelTool) event.entityPlayer.inventory.getCurrentItem().getItem()).getDegree());
            }
            ItemStack tool = event.entityPlayer.inventory.getCurrentItem();
            if (ForgeHooks.canToolHarvestBlock(event.block, event.metadata, tool)) {
                int[] buffs = AngelsteelToolHelper.readFromNBT(event.entityPlayer.inventory.getCurrentItem().stackTagCompound);
                if (buffs.length > 0) {
                    int efficiency = buffs[0];
                    event.newSpeed *= Math.pow(1.3, efficiency);
                    int shatter = buffs[2];
                    int disintegrate = buffs[3];
                    //1.5F, the hardness of stone, is used as a dividing point
                    //Stone is not affected by either enchant
                    if (event.block.getBlockHardness(event.entity.worldObj, event.x, event.y, event.z) <= 1F) {
                        event.newSpeed *= Math.pow(3, disintegrate);
                    }
                    if (event.block.getBlockHardness(event.entity.worldObj, event.x, event.y, event.z) >= 2F) {

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
            if (event.harvester.inventory.getCurrentItem().stackTagCompound == null) {
                event.harvester.inventory.getCurrentItem().stackTagCompound = AngelsteelToolHelper.getRandomBuffCompound(((IAngelsteelTool) event.harvester.inventory.getCurrentItem().getItem()).getDegree());
            }
            int fortune = AngelsteelToolHelper.readFromNBT(event.harvester.inventory.getCurrentItem().stackTagCompound)[1];
            if (event.fortuneLevel < fortune) {
                //Cancels the event and breaks the block again
                event.dropChance = 0;
                event.drops.clear();
                event.block.dropBlockAsItemWithChance(event.world, event.x, event.y, event.z, event.blockMetadata, 1F, fortune);
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
                List<EntityFallFairy> fairyList = entityPlayer.worldObj.getEntitiesWithinAABB(EntityFallFairy.class, entityPlayer.boundingBox.expand(20, 20, 20));
                int count = 0;
                for (EntityFallFairy fairy : fairyList) {
                    if (fairy.player == entityPlayer) {
                        count++;
                    }
                }
                event.distance *= Math.pow(.85F, count);
            }
        }

        //Momentum pump
        int x = (int) event.entity.posX;
        int y = (int) event.entity.posY;
        int z = (int) event.entity.posZ;
        CoordTuple tuple = new CoordTuple(x, y, z);
        for (CoordTuple searchPump : tuple.inRange(3)) {
            if (searchPump.getTile(event.entity.worldObj) instanceof AuraTilePumpFall) {
                ((AuraTilePumpFall) searchPump.getTile(event.entity.worldObj)).onFall(event);
                break;
            }
        }
    }

    //Kill fairies on death
    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event) {
        if (event.entityLiving instanceof EntityPlayer && !((EntityPlayer) event.entityLiving).worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) {

            EntityPlayer entityPlayer = (EntityPlayer) event.entityLiving;
            ItemStack item = BaublesApi.getBaubles(entityPlayer).getStackInSlot(1);
            if (item != null && item.getItem() instanceof ItemFairyRing && !entityPlayer.worldObj.isRemote) {
                ItemFairyRing.killNearby(entityPlayer);
            }
        }
    }

    //Kill fairies on logout
    @SubscribeEvent
    public void onPlayerLogout(cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent event) {
        ItemStack item = BaublesApi.getBaubles(event.player).getStackInSlot(1);
        if (item != null && item.getItem() instanceof ItemFairyRing && !event.player.worldObj.isRemote) {
            ItemFairyRing.killNearby(event.player);
        }

    }

    //Respawn fairies on login
    @SubscribeEvent
    public void onPlayerLogin(cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event) {
        ItemStack item = BaublesApi.getBaubles(event.player).getStackInSlot(1);
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
            if (!player.worldObj.isRemote && heldStack != null && (heldStack.getItem().getItemUseAction(heldStack) == EnumAction.eat || heldStack.getItem().getItemUseAction(heldStack) == EnumAction.drink)) {
                if (heldStack.getItem().getUnlocalizedName().equals("item.apple")) {
                    player.addPotionEffect(new PotionEffect(Potion.wither.id, 6 * 60 * 20, 1));
                } else {
                    String name = heldStack.getUnlocalizedName();
                    Random random = new Random(name.hashCode());
                    //Limit within vanilla potions, which go up to 24
                    //Note that there is no potion with id 0
                    Potion potion;
                    do {
                        potion = Potion.potionTypes[random.nextInt(23) + 1];
                    } while (potion.isInstant());
                    int duration = Math.max(0, (int) (random.nextGaussian() * 20 * 120 + 20 * 60 * 4));
                    int amplified = random.nextInt(6);
                    PotionEffect potionEffect = new PotionEffect(potion.id, duration, amplified);
                    player.addPotionEffect(potionEffect);
                }
            }
        }
    }

}
