package pixlepix.auracascade;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import pixlepix.auracascade.block.AuraBlock;
import pixlepix.auracascade.block.ConsumerBlock;
import pixlepix.auracascade.data.EnumRainbowColor;
import pixlepix.auracascade.data.Quest;
import pixlepix.auracascade.item.*;
import pixlepix.auracascade.registry.BlockRegistry;

import java.util.ArrayList;

/**
 * Created by localmacaccount on 5/31/15.
 */
public class QuestManager {

    public static ArrayList<Quest> quests = new ArrayList<Quest>();


    public static void check(EntityPlayer player) {
        /*
        for (Quest quest : quests) {
            if (!quest.hasCompleted(player) && Config.questline) {
                if (player.inventory.hasItemStack(quest.target)) {
                    if (!player.worldObj.isRemote) {
                        EntityItem entityItem = new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, quest.result.copy());
                        AuraUtil.setItemDelay(entityItem, 0);
                        player.worldObj.spawnEntityInWorld(entityItem);
                        player.addChatComponentMessage(new TextComponentString("Quest \'" + quest.string + "\' Completed"));
                    } else {
                    	//TODO Fix sounds
                       // player.worldObj.playSoundAtEntity(player, "fireworks.launch", 0.5F, 1F);
                    }
                    quest.complete(player);
                }
            }
        }
        */
    }

    public static void init() {
        Item crystal = BlockRegistry.getFirstItemFromClass(ItemAuraCrystal.class);
        quests.add(new Quest("Crystals", new ItemStack(crystal), new ItemStack(crystal)));
        quests.add(new Quest("Nodes", AuraBlock.getAuraNodeItemstack(), new ItemStack(AuraBlock.getBlockFromName(""), 16)));
        quests.add(new Quest("Pumps", AuraBlock.getAuraNodePumpItemstack(), new ItemStack(Items.COAL, 64)));
        quests.add(new Quest("Furance", new ItemStack(ConsumerBlock.getBlockFromName("furnace")), new ItemStack(crystal, 64)));
        quests.add(new Quest("Processor", new ItemStack(ConsumerBlock.getBlockFromName("ore")), new ItemStack(Blocks.IRON_ORE, 8)));
        quests.add(new Quest("Dye", new ItemStack(ConsumerBlock.getBlockFromName("dye")), new ItemStack(Blocks.WOOL, 32)));
        quests.add(new Quest("Arcane Ingot", ItemMaterial.getIngot(EnumRainbowColor.WHITE), ItemMaterial.getIngot(EnumRainbowColor.WHITE, 15)));
        quests.add(new Quest("Vortex Infusion", new ItemStack(AuraBlock.getBlockFromName("craftingCenter")), new ItemStack(AuraBlock.getBlockFromName("craftingPedestal"), 4)));
        quests.add(new Quest("Arcane Gem", ItemMaterial.getGem(EnumRainbowColor.WHITE), ItemMaterial.getGem(EnumRainbowColor.WHITE, 8)));
        quests.add(new Quest("Arcane Prism", ItemMaterial.getPrism(), ItemMaterial.getPrism()));
        quests.add(new Quest("Synthesizer", new ItemStack(ConsumerBlock.getBlockFromName("angel")), new ItemStack(crystal, 64)));
        quests.add(new Quest("Angel's Steel", new ItemStack(BlockRegistry.getFirstItemFromClass(ItemAngelsteelIngot.class), 1, 1), new ItemStack(BlockRegistry.getFirstItemFromClass(ItemAngelsteelIngot.class), 15, 0)));
        quests.add(new Quest("Fairies", new ItemStack(BlockRegistry.getFirstItemFromClass(ItemFairyRing.class)), new ItemStack(BlockRegistry.getFirstItemFromClass(ItemFairyCharm.class))));
    }

}
