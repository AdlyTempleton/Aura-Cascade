package pixlepix.auracascade.item;

import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;

import java.util.ArrayList;

/**
 * Created by pixlepix on 12/21/14.
 */
public class AngelsteelToolHelper {

    public static final int MAX_DEGREE = 12;

    public static ArrayList<Object> getDegreeList(boolean execludeZero){
        ArrayList<Object> integers = new ArrayList<Object>();
        for(int i = (execludeZero ? 1 : 0) ; i < MAX_DEGREE; i++){
            integers.add(new Integer(i));
        }
        return integers;
    }


    public static final Item.ToolMaterial[] materials = new Item.ToolMaterial[MAX_DEGREE];

    public static void initMaterials(){

        for(int i=0; i<MAX_DEGREE; i++){
            materials[i] = EnumHelper.addToolMaterial("ANGELSTEEL" + i, 4, -1, (float) (8F * Math.pow(1.5, i)), (float) (5F * Math.pow(1.5, i)), 40);
        }
    }

}
