package pixlepix.auracascade.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import pixlepix.auracascade.block.entity.*;
import pixlepix.auracascade.data.EnumRainbowColor;
import pixlepix.auracascade.main.event.EventHandler;
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

    public static final String name = "fairyCharm";
    @SuppressWarnings("rawtypes")
	public static Class[] fairyClasses = new Class[]{EntityFairy.class, EntityCombatFairy.class, EntityDebuffFairy.class, EntityBuffFairy.class,
            EntityStealFairy.class, EntityPushFairy.class, EntityShooterFairy.class, EntitySaviorFairy.class, EntityFetchFairy.class
            , EntityBaitFairy.class, EntityBreederFairy.class, EntityScareFairy.class, EntityExtinguisherFairy.class, EntityDigFairy.class,
            EntityFallFairy.class, EntityLightFairy.class, EntityXPFairy.class
    };

    protected static String getNameFromFairy(Class<? extends EntityFairy> clazz) {
        if (clazz == EntityFairy.class) {
            return "Fairy";
        }
        if (clazz == EntityBaitFairy.class) {
            return "Baiter Fairy";
        }
        if (clazz == EntityBreederFairy.class) {
            return "Breeder Fairy";
        }
        if (clazz == EntityBuffFairy.class) {
            return "Buffer Fairy";
        }
        if (clazz == EntityCombatFairy.class) {
            return "Fighter Fairy";
        }
        if (clazz == EntityDebuffFairy.class) {
            return "Debuffer Fairy";
        }
        if (clazz == EntityDigFairy.class) {
            return "Digger Fairy";
        }
        if (clazz == EntityExtinguisherFairy.class) {
            return "Extinguisher Fairy";
        }
        if (clazz == EntityFallFairy.class) {
            return "Glider Fairy";
        }
        if (clazz == EntityFetchFairy.class) {
            return "Fetcher Fairy";
        }
        if (clazz == EntityLightFairy.class) {
            return "Lighter Fairy";
        }
        if (clazz == EntityPushFairy.class) {
            return "Pusher Fairy";
        }
        if (clazz == EntitySaviorFairy.class) {
            return "Savior Fairy";
        }
        if (clazz == EntityScareFairy.class) {
            return "Scarer Fairy";
        }
        if (clazz == EntityShooterFairy.class) {
            return "Shooter Fairy";
        }
        if (clazz == EntityStealFairy.class) {
            return "Stealer Fairy";
        }
        if (clazz == EntityXPFairy.class) {
            return "Trainer Fairy";
        }


        return "INVALID_FAIRY";
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand){
        if (!world.isRemote) {
            ItemStack ringStack = EventHandler.getBaubleFromInv(ItemFairyRing.class, player);
            if (ringStack != null) {
                if (ringStack.getTagCompound() == null) {
                    ringStack.setTagCompound(new NBTTagCompound());
                }
                int[] fairies = ringStack.getTagCompound().getIntArray("fairyList");
                if (fairies.length < 15) {
                    int[] newFairies = Arrays.copyOf(fairies, fairies.length + 1);

                    newFairies[newFairies.length - 1] = stack.getItemDamage();


                    ringStack.getTagCompound().setIntArray("fairyList", newFairies);
                    //Trigger the re-spawn of fairies
                    ItemFairyRing.killNearby(player);
                    ItemFairyRing.makeFaries(ringStack, player);


                    consumeInventoryItem(player.inventory, this, stack.getItemDamage());

                  //  AuraCascade.analytics.eventDesign("fairyAdded", fairyClasses[stack.getItemDamage()].getName());
                }
            }
        }
        return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
    }

    //Metadata sensitive version of InventoryPlayer.consumeInventoryItem
    public void consumeInventoryItem(InventoryPlayer inventoryPlayer, Item item, int meta) {
        int i = -1;
        for (int j = 0; j < inventoryPlayer.mainInventory.length; ++j) {
            if (inventoryPlayer.mainInventory[j] != null && inventoryPlayer.mainInventory[j].getItem() == item && inventoryPlayer.mainInventory[j].getItemDamage() == meta) {
                i = j;
            }
        }

        if (i >= 0) {
            if (--inventoryPlayer.mainInventory[i].stackSize <= 0) {
                inventoryPlayer.mainInventory[i] = null;
            }
        }
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 16;
    }

    @SuppressWarnings("unchecked")
	@Override
    public String getItemStackDisplayName(ItemStack stack) {
        if (stack.getItemDamage() >= fairyClasses.length) {
            return "invalid";
        }
        return super.getItemStackDisplayName(stack) + ": " + getNameFromFairy(fairyClasses[stack.getItemDamage()]);
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

        return new ThaumicTinkererRecipeMulti(
                new CraftingBenchRecipe(new ItemStack(this), "BGB", 'B', new ItemStack(Items.BRICK), 'G', ItemMaterial.getGem(EnumRainbowColor.WHITE)),
                new CraftingBenchRecipe(new ItemStack(this, 1, 1), " X ", "XCX", " X ", 'X', new ItemStack(Items.GOLDEN_SWORD), 'C', new ItemStack(this)),
                new CraftingBenchRecipe(new ItemStack(this, 1, 2), " X ", "XCX", " X ", 'X', new ItemStack(Items.POTIONITEM, 1, 8196), 'C', new ItemStack(this)),
                new CraftingBenchRecipe(new ItemStack(this, 1, 3), " X ", "XCX", " X ", 'X', new ItemStack(Items.POTIONITEM, 1, 8257), 'C', new ItemStack(this)),
                new CraftingBenchRecipe(new ItemStack(this, 1, 4), " X ", "XCX", " X ", 'X', new ItemStack(Items.FISHING_ROD), 'C', new ItemStack(this)),
                new CraftingBenchRecipe(new ItemStack(this, 1, 5), " X ", "XCX", " X ", 'X', new ItemStack(Items.SNOWBALL), 'C', new ItemStack(this)),
                new CraftingBenchRecipe(new ItemStack(this, 1, 6), " X ", "XCX", " X ", 'X', new ItemStack(Items.ARROW), 'C', new ItemStack(this)),
                new CraftingBenchRecipe(new ItemStack(this, 1, 7), " X ", "XCX", " X ", 'X', new ItemStack(Items.DIAMOND_SWORD), 'C', new ItemStack(this)),
                new CraftingBenchRecipe(new ItemStack(this, 1, 8), " X ", "XCX", " X ", 'X', new ItemStack(Blocks.CHEST), 'C', new ItemStack(this)),
                new CraftingBenchRecipe(new ItemStack(this, 1, 9), " X ", "XCX", " X ", 'X', new ItemStack(Items.EGG), 'C', new ItemStack(this)),
                new CraftingBenchRecipe(new ItemStack(this, 1, 10), " X ", "XCX", " X ", 'X', new ItemStack(Items.WHEAT), 'C', new ItemStack(this)),
                new CraftingBenchRecipe(new ItemStack(this, 1, 11), " X ", "XCX", " X ", 'X', new ItemStack(Items.GUNPOWDER), 'C', new ItemStack(this)),
                new CraftingBenchRecipe(new ItemStack(this, 1, 12), " X ", "XCX", " X ", 'X', new ItemStack(Items.WATER_BUCKET), 'C', new ItemStack(this)),
                new CraftingBenchRecipe(new ItemStack(this, 1, 13), " X ", "XCX", " X ", 'X', new ItemStack(Items.GOLDEN_PICKAXE), 'C', new ItemStack(this)),
                new CraftingBenchRecipe(new ItemStack(this, 1, 14), " X ", "XCX", " X ", 'X', new ItemStack(Blocks.WOOL), 'C', new ItemStack(this)),
                new CraftingBenchRecipe(new ItemStack(this, 1, 15), " X ", "XCX", " X ", 'X', new ItemStack(Blocks.TORCH), 'C', new ItemStack(this)),
                new CraftingBenchRecipe(new ItemStack(this, 1, 16), " X ", "XCX", " X ", 'X', new ItemStack(Blocks.LAPIS_BLOCK), 'C', new ItemStack(this))
        );
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tabs, List<ItemStack> list) {
        for (int i = 0; i < fairyClasses.length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public boolean getHasSubtypes() {
        return true;
    }

    @Override
    public int getCreativeTabPriority() {
        return -11;
    }
}
