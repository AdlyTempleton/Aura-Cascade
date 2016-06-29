package pixlepix.auracascade.registry;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import pixlepix.auracascade.item.ItemAuraCrystal;
import pixlepix.auracascade.main.ConstantMod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ModCreativeTab extends CreativeTabs {
    public static ModCreativeTab INSTANCE;
    //Holds the registered items and blocks before they are sorted
    public ArrayList<ItemStack> creativeTabQueue = new ArrayList<ItemStack>();
    List<ItemStack> list = new ArrayList<ItemStack>();

    public ModCreativeTab() {
        super(ConstantMod.modId);
    }

    @Override
    public ItemStack getIconItemStack() {
        return new ItemStack(BlockRegistry.getFirstItemFromClass(ItemAuraCrystal.class));
    }

    @Override
    public Item getTabIconItem() {
        return Items.STICK;
    }

    @Override
    public void displayAllRelevantItems(List<ItemStack> list) {
        list.addAll(this.list);
    }

    public void addItem(Item item) {

        item.getSubItems(item, this, creativeTabQueue);
        item.setCreativeTab(this);
    }

    public void addBlock(Block block) {
        addItem(Item.getItemFromBlock(block));
        block.setCreativeTab(this);
    }

    public void addAllItemsAndBlocks() {
        Collections.sort(creativeTabQueue, new ItemStackCompatator());
        list.addAll(creativeTabQueue);
    }
}
