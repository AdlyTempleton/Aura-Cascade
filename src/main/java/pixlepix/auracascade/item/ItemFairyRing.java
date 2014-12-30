package pixlepix.auracascade.item;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.block.entity.EntityFairy;
import pixlepix.auracascade.data.AuraQuantity;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.data.recipe.PylonRecipe;
import pixlepix.auracascade.data.recipe.PylonRecipeComponent;
import pixlepix.auracascade.registry.ITTinkererItem;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by pixlepix on 12/8/14.
 */
public class ItemFairyRing extends Item implements ITTinkererItem, IBauble {
    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return BaubleType.RING;
    }

    @Override
    public void onWornTick(ItemStack itemStack, EntityLivingBase entityLivingBase) {

    }

    @Override
    public void onEquipped(ItemStack ringStack, EntityLivingBase entityLivingBase) {
        makeFaries(ringStack, entityLivingBase);
    }

    public static void makeFaries(ItemStack ringStack, EntityLivingBase entity) {
        if(entity instanceof EntityPlayer && !((EntityPlayer) entity).worldObj.isRemote) {

            if(ringStack.stackTagCompound == null){
                ringStack.stackTagCompound = new NBTTagCompound();
            }
            int[] tagList = ringStack.stackTagCompound.getIntArray("fairyList");
            for(int i : tagList){
                Class<? extends EntityFairy> fairyClass = ItemFairyCharm.fairyClasses[i];
                EntityFairy fairy = null;
                try {
                    fairy = fairyClass.getConstructor(World.class).newInstance(((EntityPlayer) entity).worldObj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                fairy.setPosition(((EntityPlayer) entity).posX, ((EntityPlayer) entity).posY, ((EntityPlayer) entity).posZ);
                fairy.player = (EntityPlayer) entity;

                ((EntityPlayer) entity).worldObj.spawnEntityInWorld(fairy);
            }

        }
    }

    @Override
    public void onUnequipped(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        killNearby(itemStack, entityLivingBase);

    }

    public static void killNearby(ItemStack itemStack, EntityLivingBase entityLivingBase){
        List<EntityFairy> fairies = entityLivingBase.worldObj.getEntitiesWithinAABB(EntityFairy.class, AxisAlignedBB.getBoundingBox(entityLivingBase.posX - 50, entityLivingBase.posY - 50, entityLivingBase.posZ - 50, entityLivingBase.posX + 50, entityLivingBase.posY + 50, entityLivingBase.posZ + 50));
        for (EntityFairy fairy : fairies) {
            if (fairy.player == entityLivingBase) {
                fairy.setDead();

            }
        }
    }

    @Override
    public boolean canEquip(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        return true;
    }

    @Override
    public boolean canUnequip(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        return true;
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        return null;
    }

    public static final String name = "fairyRing";

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
        return new PylonRecipe(new ItemStack(this),
                new PylonRecipeComponent(new AuraQuantity(EnumAura.BLUE_AURA, 50000), new ItemStack(Blocks.diamond_block)),

                new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 50000), new ItemStack(Blocks.iron_block)),

                new PylonRecipeComponent(new AuraQuantity(EnumAura.YELLOW_AURA, 50000), new ItemStack(Blocks.gold_block)),

                new PylonRecipeComponent(new AuraQuantity(EnumAura.RED_AURA, 50000), new ItemStack(Blocks.redstone_block)));
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_) {
        super.addInformation(stack, player, list, p_77624_4_);
        if(stack.stackTagCompound == null){
            stack.stackTagCompound = new NBTTagCompound();
        }
        int[] tagList = stack.stackTagCompound.getIntArray("fairyList");
        list.add(tagList.length+"/15");
        for(int i : tagList){
            Class<? extends EntityFairy> fairyClass = ItemFairyCharm.fairyClasses[i];
            list.add(ItemFairyCharm.getNameFromFairy(fairyClass));
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if(stack.stackTagCompound == null){
            stack.stackTagCompound = new NBTTagCompound();
        }
        if(!world.isRemote && player.isSneaking()){
            int[] fairyCharms = stack.stackTagCompound.getIntArray("fairyList");
            Random random = new Random();
            for(int i: fairyCharms){
                EntityItem item = new EntityItem(world, player.posX + random.nextDouble() -.5D, player.posY + random.nextDouble() -.5D, player.posZ + random.nextDouble() -.5D, new ItemStack(AuraCascade.proxy.registry.getFirstItemFromClass(ItemFairyCharm.class), 1, i));
                world.spawnEntityInWorld(item);
            }
            stack.stackTagCompound.setIntArray("fairyList", new int[0]);

        }
        return stack;
    }
}
