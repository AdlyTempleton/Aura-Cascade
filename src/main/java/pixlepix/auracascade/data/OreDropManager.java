package pixlepix.auracascade.data;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by localmacaccount on 6/6/15.
 */
public class OreDropManager {
    public static HashMap<String, Integer> oreNames = new HashMap<String, Integer>();

    public static void addOreWeight(String name, int value) {
        //Nether ores registers stuff whether or not the overworld version exists
        if (name.contains("Nether") && !(OreDictionary.getOres(name.replace("Nether", "")).size() > 0)) {
            return;
        }
        oreNames.put(name, value);
    }

    public static void init() {

        //Ore weights courtesy of Vazkii's Botania

        addOreWeight("oreAluminum", 3940); // Tinkers' Construct
        addOreWeight("oreAmber", 2075); // Thaumcraft
        addOreWeight("oreApatite", 1595); // Forestry
        addOreWeight("oreBlueTopaz", 3195); // Ars Magica
        addOreWeight("oreCertusQuartz", 3975); // Applied Energistics
        addOreWeight("oreChimerite", 3970); // Ars Magica
        addOreWeight("oreCinnabar", 2585); // Thaumcraft
        addOreWeight("oreCoal", 46525); // Vanilla
        addOreWeight("oreCopper", 8325); // IC2, Thermal Expansion, Tinkers' Construct, etc.
        addOreWeight("oreDark", 1350); // EvilCraft
        addOreWeight("oreDarkIron", 1700); // Factorization
        addOreWeight("oreDiamond", 1265); // Vanilla
        addOreWeight("oreEmerald", 780); // Vanilla
        addOreWeight("oreGalena", 1000); // Factorization
        addOreWeight("oreGold", 2970); // Vanilla
        addOreWeight("oreInfusedAir", 925); // Thaumcraft
        addOreWeight("oreInfusedEarth", 925); // Thaumcraft
        addOreWeight("oreInfusedEntropy", 925); // Thaumcraft
        addOreWeight("oreInfusedFire", 925); // Thaumcraft
        addOreWeight("oreInfusedOrder", 925); // Thaumcraft
        addOreWeight("oreInfusedWater", 925); // Thaumcraft
        addOreWeight("oreIron", 20665); // Vanilla
        addOreWeight("oreLapis", 1285); // Vanilla
        addOreWeight("oreLead", 7985); // IC2, Thermal Expansion, Factorization, etc.
        addOreWeight("oreMCropsEssence", 3085); // Magical Crops
        addOreWeight("oreNickel", 2275); // Thermal Expansion
        addOreWeight("oreOlivine", 1100); // Project RED
        addOreWeight("oreRedstone", 6885); // Vanilla
        addOreWeight("oreRuby", 1100); // Project RED
        addOreWeight("oreSapphire", 1100); // Project RED
        addOreWeight("oreSilver", 6300); // Thermal Expansion, Factorization, etc.
        addOreWeight("oreSulfur", 1105); // Railcraft
        addOreWeight("oreTin", 9450); // IC2, Thermal Expansion, etc.
        addOreWeight("oreUranium", 1337); // IC2
        addOreWeight("oreVinteum", 5925); // Ars Magica
        addOreWeight("oreYellorite", 3520); // Big Reactors
        addOreWeight("oreZinc", 6485); // Flaxbeard's Steam Power
        addOreWeight("oreMythril", 6485); // Simple Ores2
        addOreWeight("oreAdamantium", 2275); // Simple Ores2
        addOreWeight("oreTungsten", 3520); // Simple Tungsten

        addOreWeight("oreQuartz", 19600); // Vanilla
        addOreWeight("oreCobalt", 500); // Tinker's Construct
        addOreWeight("oreArdite", 500); // Tinker's Construct
        addOreWeight("oreFirestone", 5); // Railcraft
        addOreWeight("oreNetherCoal", 17000); // Nether Ores
        addOreWeight("oreNetherCopper", 4700); // Nether Ores
        addOreWeight("oreNetherDiamond", 175); // Nether Ores
        addOreWeight("oreNetherEssence", 2460); // Magical Crops
        addOreWeight("oreNetherGold", 3635); // Nether Ores
        addOreWeight("oreNetherIron", 5790); // Nether Ores
        addOreWeight("oreNetherLapis", 3250); // Nether Ores
        addOreWeight("oreNetherLead", 2790); // Nether Ores
        addOreWeight("oreNetherNickel", 1790); // Nether Ores
        addOreWeight("oreNetherPlatinum", 170); // Nether Ores
        addOreWeight("oreNetherRedstone", 5600); // Nether Ores
        addOreWeight("oreNetherSilver", 1550); // Nether Ores
        addOreWeight("oreNetherSteel", 1690); // Nether Ores
        addOreWeight("oreNetherTin", 3750); // Nether Ores
        addOreWeight("oreFyrite", 1000); // Netherrocks
        addOreWeight("oreAshstone", 1000); // Netherrocks
        addOreWeight("oreDragonstone", 175); // Netherrocks
        addOreWeight("oreArgonite", 1000); // Netherrocks
        addOreWeight("oreOnyx", 500); // SimpleOres 2
        addOreWeight("oreHaditeCoal", 500); // Hadite
    }

    //Also Botania's code
    public static ItemStack getOreToPut() {
        List<WeightedRandom.Item> values = new ArrayList<net.minecraft.util.WeightedRandom.Item>();
        for (String s : oreNames.keySet())
            values.add(new StringRandomItem(oreNames.get(s), s));

        String ore = ((StringRandomItem) WeightedRandom.getRandomItem(new Random(), values)).s;

        List<ItemStack> ores = OreDictionary.getOres(ore);

        for (ItemStack stack : ores) {
            Item item = stack.getItem();
            String clname = item.getClass().getName();

            if (clname.startsWith("gregtech") || clname.startsWith("gregapi")) {
                continue;
            }

            return stack.copy();
        }

        return getOreToPut();
    }

    private static class StringRandomItem extends WeightedRandom.Item {

        public String s;

        public StringRandomItem(int par1, String s) {
            super(par1);
            this.s = s;
        }

    }
}
