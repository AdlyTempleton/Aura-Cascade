package pixlepix.auracascade.item;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.data.IAngelsteelTool;
import pixlepix.auracascade.main.ParticleEffects;
import pixlepix.auracascade.potions.PotionManager;
import pixlepix.auracascade.registry.*;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by localmacaccount on 1/19/15.
 */
public class ItemAngelsteelSword extends ItemSword implements ITTinkererItem, IAngelsteelTool {
    public static final String name = "angelsteelSword";
    public static String[] patrons = new String[]{"Pixlepix", "JGPhoenix"};
    public int degree = 0;
    public EnumAura[] auraSwords = new EnumAura[]{EnumAura.BLUE_AURA, EnumAura.GREEN_AURA, EnumAura.ORANGE_AURA, EnumAura.RED_AURA, EnumAura.VIOLET_AURA, EnumAura.YELLOW_AURA};

    public ItemAngelsteelSword(Integer i) {
        super(AngelsteelToolHelper.materials[i]);
        this.degree = i;
        setCreativeTab(null);
    }

    public ItemAngelsteelSword() {
        this(0);
    }

    public static ItemStack getStackFirstDegree(EnumAura aura) {
        return ((ItemAngelsteelSword) BlockRegistry.getFirstItemFromClass(ItemAngelsteelSword.class)).getStack(aura);
    }

    @Override
    public int getCreativeTabPriority() {
        return -5;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack p_150894_1_, World p_150894_2_, Block state, BlockPos pos, EntityLivingBase p_150894_7_) {
        return true;
    }

    public EnumAura getAura(ItemStack stack) {
        if (stack.getTagCompound() != null && stack.getTagCompound().hasKey("aura")) {
            return EnumAura.values()[stack.getTagCompound().getInteger("aura")];

        }
        return EnumAura.RED_AURA;
    }

    public ItemStack getStack(EnumAura aura) {
        ItemStack stack = new ItemStack(this);
        stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setInteger("aura", aura.ordinal());
        return stack;
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        return AngelsteelToolHelper.getDegreeList();
    }

    @Override
    public String getItemName() {
        return name + degree;
    }

    @Override
    public boolean shouldRegister() {
        return true;
    }

    @Override
    public boolean shouldDisplayInTab() {
        return degree == 0 || degree == AngelsteelToolHelper.MAX_DEGREE;
    }

    @Override
    public boolean getHasSubtypes() {
        return true;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (EnumAura aura : auraSwords) {
            list.add(getStack(aura));
        }
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase entity, EntityLivingBase attacker) {
        EnumAura aura = getAura(stack);
        if (aura == EnumAura.RED_AURA) {
            entity.addPotionEffect(new PotionEffect(PotionManager.potionRed.getId(), degree * degree * 100 + 100));
        }
        if (aura == EnumAura.ORANGE_AURA) {
            entity.addPotionEffect(new PotionEffect(PotionManager.potionOrange.getId(), degree * degree * 100 + 100));
        }
        if (aura == EnumAura.YELLOW_AURA) {
            entity.addPotionEffect(new PotionEffect(PotionManager.potionYellow.getId(), degree * degree * 100 + 100));
        }
        if (aura == EnumAura.GREEN_AURA) {
            entity.addPotionEffect(new PotionEffect(PotionManager.potionGreen.getId(), degree * degree * 100 + 100));
        }
        if (aura == EnumAura.BLUE_AURA) {
            entity.addPotionEffect(new PotionEffect(PotionManager.potionBlue.getId(), degree * degree * 100 + 100));
        }
        if (aura == EnumAura.VIOLET_AURA) {
            entity.addPotionEffect(new PotionEffect(PotionManager.potionPurple.getId(), degree * degree * 100 + 100));
        }
        return true;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        EntityPlayer player = AuraCascade.proxy.getPlayer();
        if (player != null && Arrays.asList(patrons).contains(player.getDisplayName())) {
            return "Sword of the Patron";
        }
        return getAura(stack).name + " " + super.getItemStackDisplayName(stack);
    }

    @Override
    public void onUpdate(ItemStack stack, World w, Entity e, int p_77663_4_, boolean p_77663_5_) {
        if (w.isRemote && e instanceof EntityPlayer && Arrays.asList(patrons).contains(((EntityPlayer) e).getDisplayName())) {
            float hue = (w.getTotalWorldTime() % 1200) / 1200F;
            Color color = Color.getHSBColor(hue, 1F, .5F);
            Random r = new Random();
            ParticleEffects.spawnParticle("squareLong", e.posX, e.posY + .5, e.posZ, r.nextFloat() / 5, .5, r.nextFloat() / 5, color.getRed(), color.getGreen(), color.getBlue());
        }
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        ThaumicTinkererRecipeMulti result = new ThaumicTinkererRecipeMulti();
        ArrayList<ThaumicTinkererRecipe> recipes = new ArrayList<ThaumicTinkererRecipe>();
        for (EnumAura aura : auraSwords) {
            recipes.add(new CraftingBenchRecipe(getStack(aura), " A ", " A ", " C ", 'A', new ItemStack(BlockRegistry.getFirstItemFromClass(ItemAngelsteelIngot.class), 1, degree), 'C', ItemAuraCrystal.getCrystalFromAura(aura)));
        }
        result.recipes = recipes;
        return result;
    }

    @Override
    public int getDegree() {
        return degree;
    }
}
