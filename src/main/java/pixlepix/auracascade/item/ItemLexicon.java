package pixlepix.auracascade.item;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.QuestManager;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.lexicon.CategoryManager;
import pixlepix.auracascade.lexicon.ILexiconable;
import pixlepix.auracascade.lexicon.LexiconEntry;
import pixlepix.auracascade.lexicon.common.core.helper.ItemNBTHelper;
import pixlepix.auracascade.registry.CraftingBenchRecipe;
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
    public EnumActionResult onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (par2EntityPlayer.isSneaking()) {
            Block block = par3World.getBlockState(pos).getBlock();
            if (block != null) {
                if (block instanceof ILexiconable) {
                    LexiconEntry entry = ((ILexiconable) block).getEntry(par3World, pos, par2EntityPlayer, par1ItemStack);
                    if (entry != null) {
                        AuraCascade.proxy.setEntryToOpen(entry);
                        AuraCascade.proxy.setLexiconStack(par1ItemStack);

                        par2EntityPlayer.openGui(AuraCascade.instance, 0, par3World, 0, 0, 0);
                        if (!par3World.isRemote) {
                        	//TODO fix sounds
                            //par3World.playSoundAtEntity(par2EntityPlayer, "aura:lexiconOpen", 0.5F, 1F);
                        }
                        return EnumActionResult.PASS;
                    }
                }
            }
        }
        return EnumActionResult.FAIL;
    }

    private void addStringToTooltip(String s, List<String> tooltip) {
        tooltip.add(s.replaceAll("&", "\u00a7"));
    }

    @Override
    public  ActionResult<ItemStack> onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, EnumHand hand){
        String force = getForcedPage(par1ItemStack);
        if (force != null && !force.isEmpty()) {
            LexiconEntry entry = getEntryFromForce(par1ItemStack);
            if (entry != null) {
                AuraCascade.proxy.setEntryToOpen(entry);
            } else {
                par3EntityPlayer.addChatMessage(new TextComponentString("aura.misc.cantOpen"));
            }
            setForcedPage(par1ItemStack, "");
        }

        QuestManager.check(par3EntityPlayer);

        AuraCascade.proxy.setLexiconStack(par1ItemStack);
        par3EntityPlayer.openGui(AuraCascade.instance, 0, par2World, 0, 0, 0);
        if (!par2World.isRemote && !skipSound)
        	//TODO Fix soundat
        //par2World.playSoundAt(par3EntityPlayer, "aura:lexiconOpen", 0.5F, 1F);
        skipSound = false;
        return new ActionResult<ItemStack>(EnumActionResult.PASS, par1ItemStack);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int idk, boolean something) {
        int ticks = getQueueTicks(stack);
        if (ticks > 0 && entity instanceof EntityPlayer) {
            skipSound = ticks < 5;
            if (ticks == 1)
                onItemRightClick(stack, world, (EntityPlayer) entity, EnumHand.MAIN_HAND);
            setQueueTicks(stack, ticks - 1);
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack par1ItemStack) {
        return EnumRarity.UNCOMMON;
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
        return new CraftingBenchRecipe(new ItemStack(this), "CB", "  ", 'C', ItemAuraCrystal.getCrystalFromAura(EnumAura.WHITE_AURA), 'B', new ItemStack(Items.BOOK));
    }

    @Override
    public int getCreativeTabPriority() {
        return 150;
    }
}
