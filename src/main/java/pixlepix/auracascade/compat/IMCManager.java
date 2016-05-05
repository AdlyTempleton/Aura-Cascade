package pixlepix.auracascade.compat;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInterModComms;

/**
 * Created by localmacaccount on 5/8/15.
 */
public class IMCManager {
    public static ArrayList<ItemStack> lootBlacklist = new ArrayList<ItemStack>();

    public static boolean isStackBlacklistedFromLoot(ItemStack stack) {
        for (ItemStack stack1 : lootBlacklist) {
            if (stack.getItem() == stack1.getItem()) {
                return true;
            }
        }
        return false;
    }

    //Note that, although IMC passes an itemstack, it will block it per Item-object
    public static void onIMC(FMLInterModComms.IMCEvent event) {
        for (final FMLInterModComms.IMCMessage imcMessage : event.getMessages()) {
            if (imcMessage.key.toLowerCase().equals("lootblacklist") && imcMessage.isItemStackMessage()) {
                lootBlacklist.add(imcMessage.getItemStackValue());
            }
        }
    }
}
