package pixlepix.auracascade.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.EnumHelper;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by pixlepix on 12/21/14.
 */
public class AngelsteelToolHelper {

    public static final int MAX_DEGREE = 11;
    public static final String NBT_BUFF_ARRAY_NAME = "angelbuffs";
    public static final Item.ToolMaterial[] materials = new Item.ToolMaterial[MAX_DEGREE];

    public static ArrayList<Object> getDegreeList() {
        ArrayList<Object> integers = new ArrayList<Object>();
        for (int i = 1; i < MAX_DEGREE; i++) {
            integers.add(i);
        }
        return integers;
    }

    //Some notes on the data storage for buff values
    // Array of integers, where each position represents the level for a certain aspect
    // [0]: Efficiency
    // [1]: Fortune
    // [2]: Shatter
    // [3]: Disintegrate
    public static int[] getRandomBuffSet(int lvl) {
        int[] result = new int[4];
        Random rand = new Random();
        for (int i = 0; i < lvl * 2; i++) {
            result[rand.nextInt(4)]++;
        }
        return result;
    }

    public static void writeToNBT(NBTTagCompound nbtTagCompound, int[] buffs) {
        nbtTagCompound.setIntArray(NBT_BUFF_ARRAY_NAME, buffs);
    }

    public static int[] readFromNBT(NBTTagCompound nbtTagCompound) {
        return nbtTagCompound.getIntArray(NBT_BUFF_ARRAY_NAME);
    }

    public static boolean hasValidBuffs(ItemStack stack) {
        return stack.stackTagCompound != null && stack.stackTagCompound.hasKey(NBT_BUFF_ARRAY_NAME);
    }

    public static NBTTagCompound getRandomBuffCompound(int lvl) {
        NBTTagCompound compound = new NBTTagCompound();
        writeToNBT(compound, getRandomBuffSet(lvl));
        return compound;
    }

    public static boolean isAngelsteelTool(Item item) {
        return item instanceof ItemAngelsteelAxe || item instanceof ItemAngelsteelShovel || item instanceof ItemAngelsteelPickaxe;
    }

    public static void initMaterials() {

        for (int i = 0; i < MAX_DEGREE; i++) {
            materials[i] = EnumHelper.addToolMaterial("ANGELSTEEL" + i, 5, 10, (float) (5F * Math.pow(1.15, i)), (float) (3F * Math.pow(1.1, i)), 10);
        }
    }

}
