package pixlepix.auracascade.main;

import baubles.api.BaublesApi;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import pixlepix.auracascade.block.entity.EntityDigFairy;
import pixlepix.auracascade.block.entity.EntityFallFairy;
import pixlepix.auracascade.block.entity.EntityScareFairy;
import pixlepix.auracascade.block.tile.AuraTilePumpFall;
import pixlepix.auracascade.data.CoordTuple;
import pixlepix.auracascade.data.IAngelsteelTool;
import pixlepix.auracascade.item.AngelsteelToolHelper;
import pixlepix.auracascade.item.ItemFairyRing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by pixlepix on 12/16/14.
 */
public class EventHandler {
    public ArrayList<EntityScareFairy> scareFairies = new ArrayList<EntityScareFairy>();

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
            int[] buffs = AngelsteelToolHelper.readFromNBT(event.entityPlayer.inventory.getCurrentItem().stackTagCompound);
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
                event.block.dropBlockAsItemWithChance(event.world, event.x, event.y, event.z, event.blockMetadata, 1F, fortune);
            }
        }
    }


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


}
