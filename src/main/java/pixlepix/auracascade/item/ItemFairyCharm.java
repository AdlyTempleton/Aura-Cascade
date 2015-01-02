package pixlepix.auracascade.item;

import baubles.api.BaublesApi;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import pixlepix.auracascade.block.entity.*;
import pixlepix.auracascade.data.AuraQuantity;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.data.recipe.PylonRecipe;
import pixlepix.auracascade.data.recipe.PylonRecipeComponent;
import pixlepix.auracascade.registry.CraftingBenchRecipe;
import pixlepix.auracascade.registry.ITTinkererItem;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;
import pixlepix.auracascade.registry.ThaumicTinkererRecipeMulti;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by pixlepix on 12/9/14.
 */
public class ItemFairyCharm extends Item implements ITTinkererItem {

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if(!world.isRemote && BaublesApi.getBaubles(player).getStackInSlot(1) != null && BaublesApi.getBaubles(player).getStackInSlot(1).getItem() instanceof ItemFairyRing){
            ItemStack ringStack = BaublesApi.getBaubles(player).getStackInSlot(1);
            if(ringStack.stackTagCompound == null){
                ringStack.stackTagCompound = new NBTTagCompound();
            }
            int[] fairies = ringStack.stackTagCompound.getIntArray("fairyList");
            if(fairies.length < 15) {
                int[] newFairies = Arrays.copyOf(fairies, fairies.length + 1);

                newFairies[newFairies.length - 1] = stack.getItemDamage();


                ringStack.stackTagCompound.setIntArray("fairyList", newFairies);
                //Trigger the re-spawn of fairies
                ItemFairyRing.killNearby(ringStack, player);
                ItemFairyRing.makeFaries(ringStack, player);
            }

            player.inventory.consumeInventoryItem(this);
        }
        return stack;
    }

    //Used in RenderEntityFairy
    public IIcon fairyIcon;

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        fairyIcon = iconRegister.registerIcon("aura:fairy_plain");
        itemIcon = iconRegister.registerIcon("aura:fairy_charm");
    }

    @Override
    public IIcon getIconFromDamage(int damage) {
        return damage == 100 ? fairyIcon : itemIcon;
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 16;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return super.getItemStackDisplayName(stack) + ": "+getNameFromFairy(fairyClasses[stack.getItemDamage()]);
    }

    public static final String name = "fairyCharm";

    public static Class[] fairyClasses = new Class[]{EntityFairy.class, EntityCombatFairy.class, EntityDebuffFairy.class, EntityBuffFairy.class,
            EntityStealFairy.class, EntityPushFairy.class, EntityShooterFairy.class, EntitySaviorFairy.class, EntityFetchFairy.class
            ,EntityBaitFairy.class, EntityBreederFairy.class, EntityScareFairy.class, EntityExtinguisherFairy.class, EntityDigFairy.class,
            EntityFallFairy.class, EntityLightFairy.class, EntityXPFairy.class
    };

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

        return new ThaumicTinkererRecipeMulti(
                new PylonRecipe(new ItemStack(this), new PylonRecipeComponent(new AuraQuantity(EnumAura.WHITE_AURA, 125000), new ItemStack(Items.diamond))),
                new CraftingBenchRecipe(new ItemStack(this, 1, 1), " X ", "XCX", " X ", 'X', new ItemStack(Items.golden_sword), 'C', new ItemStack(this)),
                new CraftingBenchRecipe(new ItemStack(this, 1, 2), " X ", "XCX", " X ", 'X', new ItemStack(Items.potionitem,1 , 8196), 'C', new ItemStack(this)),
                new CraftingBenchRecipe(new ItemStack(this, 1, 3), " X ", "XCX", " X ", 'X', new ItemStack(Items.potionitem, 1, 8257), 'C', new ItemStack(this)),
                new CraftingBenchRecipe(new ItemStack(this, 1, 4), " X ", "XCX", " X ", 'X', new ItemStack(Items.fishing_rod), 'C', new ItemStack(this)),
                new CraftingBenchRecipe(new ItemStack(this, 1, 5), " X ", "XCX", " X ", 'X', new ItemStack(Items.snowball), 'C', new ItemStack(this)),
                new CraftingBenchRecipe(new ItemStack(this, 1, 6), " X ", "XCX", " X ", 'X', new ItemStack(Items.arrow), 'C', new ItemStack(this)),
                new CraftingBenchRecipe(new ItemStack(this, 1, 7), " X ", "XCX", " X ", 'X', new ItemStack(Items.diamond_sword), 'C', new ItemStack(this)),
                new CraftingBenchRecipe(new ItemStack(this, 1, 8), " X ", "XCX", " X ", 'X', new ItemStack(Blocks.chest), 'C', new ItemStack(this)),
                new CraftingBenchRecipe(new ItemStack(this, 1, 9), " X ", "XCX", " X ", 'X', new ItemStack(Items.egg), 'C', new ItemStack(this)),
                new CraftingBenchRecipe(new ItemStack(this, 1, 10), " X ", "XCX", " X ", 'X', new ItemStack(Items.wheat), 'C', new ItemStack(this)),
                new CraftingBenchRecipe(new ItemStack(this, 1, 11), " X ", "XCX", " X ", 'X', new ItemStack(Items.gunpowder), 'C', new ItemStack(this)),
                new CraftingBenchRecipe(new ItemStack(this, 1, 12), " X ", "XCX", " X ", 'X', new ItemStack(Items.water_bucket), 'C', new ItemStack(this)),
                new CraftingBenchRecipe(new ItemStack(this, 1, 13), " X ", "XCX", " X ", 'X', new ItemStack(Items.golden_pickaxe), 'C', new ItemStack(this)),
                new CraftingBenchRecipe(new ItemStack(this, 1, 14), " X ", "XCX", " X ", 'X', new ItemStack(Blocks.wool), 'C', new ItemStack(this)),
                new CraftingBenchRecipe(new ItemStack(this, 1, 15), " X ", "XCX", " X ", 'X', new ItemStack(Blocks.torch), 'C', new ItemStack(this)),
                new CraftingBenchRecipe(new ItemStack(this, 1, 16), " X ", "XCX", " X ", 'X', new ItemStack(Blocks.lapis_block), 'C', new ItemStack(this))
                );
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tabs, List list) {
        for(int i=0; i<fairyClasses.length; i++){
            list.add(new ItemStack(item, 1, i));
        }
    }

    public static String getNameFromFairy(Class<? extends EntityFairy> clazz){
        if(clazz == EntityFairy.class){
            return "Fairy";
        }
        if(clazz == EntityBaitFairy.class){
            return "Baiter Fairy";
        }
        if(clazz == EntityBreederFairy.class){
            return "Breeder Fairy";
        }
        if(clazz == EntityBuffFairy.class){
            return "Buffer Fairy";
        }if(clazz == EntityCombatFairy.class){
            return "Fighter Fairy";
        }
        if(clazz == EntityDebuffFairy.class){
            return "Debuffer Fairy";
        }
        if(clazz == EntityDigFairy.class){
            return "Digger Fairy";
        }
        if(clazz == EntityExtinguisherFairy.class){
            return "Extinguisher Fairy";
        }
        if(clazz == EntityFallFairy.class){
            return "Glider Fairy";
        }
        if(clazz == EntityFetchFairy.class){
            return "Fetcher Fairy";
        }
        if(clazz == EntityLightFairy.class){
            return "Lighter Fairy";
        }
        if(clazz == EntityPushFairy.class){
            return "Pusher Fairy";
        }
        if(clazz == EntitySaviorFairy.class){
            return "Savior Fairy";
        }
        if(clazz == EntityScareFairy.class){
            return "Scarer Fairy";
        }if(clazz == EntityShooterFairy.class){
            return "Shooter Fairy";
        }if(clazz == EntityStealFairy.class){
            return "Stealer Fairy";
        }if(clazz == EntityXPFairy.class){
            return "Trainer Fairy";
        }



        return "INVALID_FAIRY";
    }

    @Override
    public boolean getHasSubtypes() {
        return true;
    }
}
