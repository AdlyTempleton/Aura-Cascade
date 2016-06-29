package pixlepix.auracascade.item;

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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.data.EnumRainbowColor;
import pixlepix.auracascade.data.IAngelsteelTool;
import pixlepix.auracascade.main.ParticleEffects;
import pixlepix.auracascade.potions.PotionManager;
import pixlepix.auracascade.registry.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by localmacaccount on 1/19/15.
 */
public class ItemAngelsteelSword extends ItemSword implements ITTinkererItem, IAngelsteelTool {
    public static final String name = "angelsteelSword";
    public static String[] patrons = new String[]{"Pixlepix", "JGPhoenix"};
    public int degree = 0;
    public EnumRainbowColor[] auraSwords = new EnumRainbowColor[]{EnumRainbowColor.BLUE, EnumRainbowColor.GREEN, EnumRainbowColor.ORANGE, EnumRainbowColor.RED, EnumRainbowColor.VIOLET, EnumRainbowColor.YELLOW};

    public ItemAngelsteelSword(Integer i) {
        super(AngelsteelToolHelper.materials[i]);
        this.degree = i;
        setCreativeTab(null);
    }

    public ItemAngelsteelSword() {
        this(0);
    }

    public static ItemStack getStackFirstDegree(EnumRainbowColor aura) {
        return ((ItemAngelsteelSword) BlockRegistry.getFirstItemFromClass(ItemAngelsteelSword.class)).getStack(aura);
    }



    @Override
    public int getCreativeTabPriority() {
        return -5;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        return true;
    }

    public static EnumRainbowColor getAura(ItemStack stack) {
        if (stack.getTagCompound() != null && stack.getTagCompound().hasKey("color")) {
            return EnumRainbowColor.values()[stack.getTagCompound().getInteger("color")];

        }
        return EnumRainbowColor.RED;
    }

    public ItemStack getStack(EnumRainbowColor aura) {
        ItemStack stack = new ItemStack(this);
        stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setInteger("color", aura.ordinal());
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
    public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> list) {
        for (EnumRainbowColor aura : auraSwords) {
            list.add(getStack(aura));
        }
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase entity, EntityLivingBase attacker) {
        EnumRainbowColor aura = getAura(stack);
        if (aura == EnumRainbowColor.RED) {
            entity.addPotionEffect(new PotionEffect(PotionManager.potionRed, degree * degree * 100 + 100));
        }
        if (aura == EnumRainbowColor.ORANGE) {
            entity.addPotionEffect(new PotionEffect(PotionManager.potionOrange, degree * degree * 100 + 100));
        }
        if (aura == EnumRainbowColor.YELLOW) {
            entity.addPotionEffect(new PotionEffect(PotionManager.potionYellow, degree * degree * 100 + 100));
        }
        if (aura == EnumRainbowColor.GREEN) {
            entity.addPotionEffect(new PotionEffect(PotionManager.potionGreen, degree * degree * 100 + 100));
        }
        if (aura == EnumRainbowColor.BLUE) {
            entity.addPotionEffect(new PotionEffect(PotionManager.potionBlue, degree * degree * 100 + 100));
        }
        if (aura == EnumRainbowColor.VIOLET) {
            entity.addPotionEffect(new PotionEffect(PotionManager.potionPurple, degree * degree * 100 + 100));
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
        if (w.isRemote && e instanceof EntityPlayer && Arrays.asList(patrons).contains(e.getDisplayName().getUnformattedText())) {
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
        for (EnumRainbowColor aura : auraSwords) {
            recipes.add(new CraftingBenchRecipe(getStack(aura), " A ", " A ", " C ", 'A', new ItemStack(BlockRegistry.getFirstItemFromClass(ItemAngelsteelIngot.class), 1, degree), 'C', ItemMaterial.getIngot(aura)));
        }
        result.recipes = recipes;
        return result;
    }

    @Override
    public int getDegree() {
        return degree;
    }
}
