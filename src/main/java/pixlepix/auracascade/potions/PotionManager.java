package pixlepix.auracascade.potions;

import net.minecraft.potion.Potion;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.main.Config;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by localmacaccount on 1/19/15.
 */
public class PotionManager {

    public static Potion potionRed;
    public static Potion potionOrange;
    public static Potion potionYellow;
    public static Potion potionGreen;
    public static Potion potionBlue;
    public static Potion potionPurple;


    public static void init() {
        //Code based on potion code from WayOfTime
        //This extends the potion array, removing the ridiculously-low vanilla limit
        Potion[] potionTypes;
        for (Field f : Potion.class.getDeclaredFields()) {
            f.setAccessible(true);
            try {
                if (f.getName().equals("potionTypes") || f.getName().equals("field_76425_a")) {
                    Field modfield = Field.class.getDeclaredField("modifiers");
                    modfield.setAccessible(true);
                    modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);
                    potionTypes = (Potion[]) f.get(null);
                    if (potionTypes.length < 128) {
                        final Potion[] newPotionTypes = new Potion[128];
                        System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
                        f.set(null, newPotionTypes);
                    }
                }
            } catch (Exception e) {
                AuraCascade.log.error("Severe error extending potion array, please report this to the mod author:", e);
            }
        }

        potionRed = new PotionRedCurse(Config.potionRed);
        potionOrange = new PotionOrangeCurse(Config.potionOrange);
        potionYellow = new PotionYellowCurse(Config.potionYellow);
        potionGreen = new PotionGreenCurse(Config.potionGreen);
        potionBlue = new PotionBlueCurse(Config.potionBlue);
        potionPurple = new PotionVioletCurse(Config.potionViolet);
    }

}
