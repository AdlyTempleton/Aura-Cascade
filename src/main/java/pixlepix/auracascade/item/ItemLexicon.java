package pixlepix.auracascade.item;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.lexicon.CategoryManager;
import pixlepix.auracascade.lexicon.ILexiconable;
import pixlepix.auracascade.lexicon.LexiconEntry;
import pixlepix.auracascade.lexicon.common.core.helper.ItemNBTHelper;
import pixlepix.auracascade.registry.ITTinkererItem;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pixlepix on 12/27/14.
 * *HEAVILY* based off of/stolen from Vazkii
 */
public class ItemLexicon extends Item implements ITTinkererItem {
    private static final String TAG_KNOWLEDGE_PREFIX = "knowledge.";
    private static final String TAG_FORCED_MESSAGE = "forcedMessage";
    private static final String TAG_QUEUE_TICKS = "queueTicks";

    private static final String name = "lexicon";

    boolean skipSound = false;

    public ItemLexicon() {
        super();
        setMaxStackSize(1);
    }

    public static void setForcedPage(ItemStack stack, String forced) {
        ItemNBTHelper.setString(stack, TAG_FORCED_MESSAGE, forced);
    }

    public static String getForcedPage(ItemStack stack) {
        return ItemNBTHelper.getString(stack, TAG_FORCED_MESSAGE, "");
    }

    private static LexiconEntry getEntryFromForce(ItemStack stack) {
        String force = getForcedPage(stack);
        for (LexiconEntry entry : CategoryManager.getAllEntries())
            if (entry.unlocalizedName.equals(force)) {
                return entry;
            }

        return null;
    }

    public static int getQueueTicks(ItemStack stack) {
        return ItemNBTHelper.getInt(stack, TAG_QUEUE_TICKS, 0);
    }

    public static void setQueueTicks(ItemStack stack, int ticks) {
        ItemNBTHelper.setInt(stack, TAG_QUEUE_TICKS, ticks);
    }

    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
        if (par2EntityPlayer.isSneaking()) {
            Block block = par3World.getBlock(par4, par5, par6);
            if (block != null) {
                if (block instanceof ILexiconable) {
                    LexiconEntry entry = ((ILexiconable) block).getEntry(par3World, par4, par5, par6, par2EntityPlayer, par1ItemStack);
                    if (entry != null) {
                        AuraCascade.proxy.setEntryToOpen(entry);
                        par2EntityPlayer.openGui(AuraCascade.instance, 0, par3World, 0, 0, 0);
                        if (!par3World.isRemote) {
                            par3World.playSoundAtEntity(par2EntityPlayer, "aura:lexiconOpen", 0.5F, 1F);
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void addStringToTooltip(String s, List<String> tooltip) {
        tooltip.add(s.replaceAll("&", "\u00a7"));
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        String force = getForcedPage(par1ItemStack);
        if (force != null && !force.isEmpty()) {
            LexiconEntry entry = getEntryFromForce(par1ItemStack);
            if (entry != null)
                AuraCascade.proxy.setEntryToOpen(entry);
            else
                par3EntityPlayer.addChatMessage(new ChatComponentTranslation("aura.misc.cantOpen").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
            setForcedPage(par1ItemStack, "");
        }
        par3EntityPlayer.openGui(AuraCascade.instance, 0, par2World, 0, 0, 0);
        if (!par2World.isRemote && !skipSound)
            par2World.playSoundAtEntity(par3EntityPlayer, "aura:lexiconOpen", 0.5F, 1F);
        skipSound = false;
        return par1ItemStack;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int idk, boolean something) {
        int ticks = getQueueTicks(stack);
        if (ticks > 0 && entity instanceof EntityPlayer) {
            skipSound = ticks < 5;
            if (ticks == 1)
                onItemRightClick(stack, world, (EntityPlayer) entity);
            setQueueTicks(stack, ticks - 1);
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack par1ItemStack) {
        return EnumRarity.uncommon;
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        return null;
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
}
