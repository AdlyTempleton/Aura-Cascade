package pixlepix.auracascade.item;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import pixlepix.auracascade.data.IAngelsteelTool;
import pixlepix.auracascade.main.AuraUtil;
import pixlepix.auracascade.registry.BlockRegistry;
import pixlepix.auracascade.registry.CraftingBenchRecipe;
import pixlepix.auracascade.registry.ITTinkererItem;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pixlepix on 12/22/14.
 */
public class ItemAngelsteelAxe extends ItemAxe implements ITTinkererItem, IAngelsteelTool {
    public static final String name = "angelsteelAxe";
    public int degree = 0;

    public ItemAngelsteelAxe(Integer i) {
        super(AngelsteelToolHelper.materials[i]);
        this.degree = i;
        setCreativeTab(null);
    }

    public ItemAngelsteelAxe() {
        this(0);
    }

    @Override
    public void registerIcons(IIconRegister register) {
        itemIcon = register.registerIcon("aura:angel_axe");
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        return AngelsteelToolHelper.getDegreeList();
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int p_77663_4_, boolean p_77663_5_) {

        if (!world.isRemote && stack.stackTagCompound == null) {
            stack.stackTagCompound = AngelsteelToolHelper.getRandomBuffCompound(degree);
        }
        super.onUpdate(stack, world, entity, p_77663_4_, p_77663_5_);
    }

    @Override
    public int getCreativeTabPriority() {
        return -5;
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
    public boolean onBlockDestroyed(ItemStack p_150894_1_, World p_150894_2_, Block p_150894_3_, int p_150894_4_, int p_150894_5_, int p_150894_6_, EntityLivingBase p_150894_7_) {
        return true;
    }

    @Override
    public boolean hitEntity(ItemStack p_77644_1_, EntityLivingBase p_77644_2_, EntityLivingBase p_77644_3_) {
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_) {
        super.addInformation(stack, player, list, p_77624_4_);

        AuraUtil.addAngelsteelDesc(list, stack);
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {

        return new CraftingBenchRecipe(new ItemStack(this, 1, 0), "AA ", "AS ", " S ", 'A', new ItemStack(BlockRegistry.getFirstItemFromClass(ItemAngelsteelIngot.class), 1, degree), 'S', new ItemStack(Items.stick));
    }

    @Override
    public int getDegree() {
        return degree;
    }
}
