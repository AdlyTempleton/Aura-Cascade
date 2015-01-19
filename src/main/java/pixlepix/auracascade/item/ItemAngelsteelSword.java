package pixlepix.auracascade.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.data.IAngelsteelTool;
import pixlepix.auracascade.potions.PotionManager;
import pixlepix.auracascade.registry.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by localmacaccount on 1/19/15.
 */
public class ItemAngelsteelSword extends ItemSword implements ITTinkererItem, IAngelsteelTool {
    public static final String name = "angelsteelSword";
    public int degree = 0;
    public EnumAura[] auraSwords = new EnumAura[]{EnumAura.BLUE_AURA, EnumAura.GREEN_AURA, EnumAura.ORANGE_AURA, EnumAura.RED_AURA, EnumAura.VIOLET_AURA, EnumAura.YELLOW_AURA};
    public HashMap<EnumAura, IIcon> iconHashMap = new HashMap<EnumAura, IIcon>();

    public ItemAngelsteelSword(Integer i) {
        super(AngelsteelToolHelper.materials[i]);
        this.degree = i;
        setCreativeTab(null);
    }

    public ItemAngelsteelSword() {
        this(0);
    }

    @Override
    public void registerIcons(IIconRegister register) {
        itemIcon = register.registerIcon("aura:angel_sword");
        for (EnumAura aura : auraSwords) {
            iconHashMap.put(aura, register.registerIcon("aura:angel_sword_" + aura.name));
        }
    }

    @Override
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
        if (stack.stackTagCompound != null && stack.stackTagCompound.hasKey("aura")) {
            EnumAura aura = EnumAura.values()[stack.stackTagCompound.getInteger("aura")];

        }
        return super.getIcon(stack, renderPass, player, usingItem, useRemaining);
    }

    public EnumAura getAura(ItemStack stack) {
        if (stack.stackTagCompound != null && stack.stackTagCompound.hasKey("aura")) {
            return EnumAura.values()[stack.stackTagCompound.getInteger("aura")];

        }
        return null;
    }

    public ItemStack getStack(EnumAura aura) {
        ItemStack stack = new ItemStack(this);
        stack.stackTagCompound = new NBTTagCompound();
        stack.stackTagCompound.setInteger("aura", aura.ordinal());
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
            entity.addPotionEffect(new PotionEffect(PotionManager.potionRed.getId(), degree * degree * 100));
        }
        if (aura == EnumAura.ORANGE_AURA) {
            entity.addPotionEffect(new PotionEffect(PotionManager.potionOrange.getId(), degree * degree * 100));
        }
        if (aura == EnumAura.YELLOW_AURA) {
            entity.addPotionEffect(new PotionEffect(PotionManager.potionYellow.getId(), degree * degree * 100));
        }
        if (aura == EnumAura.GREEN_AURA) {
            entity.addPotionEffect(new PotionEffect(PotionManager.potionGreen.getId(), degree * degree * 100));
        }
        if (aura == EnumAura.BLUE_AURA) {
            entity.addPotionEffect(new PotionEffect(PotionManager.potionBlue.getId(), degree * degree * 100));
        }
        if (aura == EnumAura.VIOLET_AURA) {
            entity.addPotionEffect(new PotionEffect(PotionManager.potionPurple.getId(), degree * degree * 100));
        }
        return super.hitEntity(stack, entity, attacker);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return super.getItemStackDisplayName(stack) + getAura(stack).name;
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
