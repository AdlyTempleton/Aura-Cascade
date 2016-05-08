package pixlepix.auracascade.registry;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;
import com.google.common.reflect.ClassPath;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import pixlepix.auracascade.main.ConstantMod;

public class BlockRegistry {

    public static HashMap<ITTinkererRegisterable, ThaumicTinkererRecipe> recipeMap = new HashMap<ITTinkererRegisterable, ThaumicTinkererRecipe>();
    @SuppressWarnings("rawtypes")
	private static HashMap<Class, ArrayList<Item>> itemRegistry = new HashMap<Class, ArrayList<Item>>();
    @SuppressWarnings("rawtypes")
	private static HashMap<Class, ArrayList<Block>> blockRegistry = new HashMap<Class, ArrayList<Block>>();
    @SuppressWarnings("rawtypes")
	private ArrayList<Class> itemClasses = new ArrayList<Class>();
    @SuppressWarnings("rawtypes")
	private ArrayList<Class> blockClasses = new ArrayList<Class>();

    public static Set<Item> getAllItems() {
        Set<Item> ret = Sets.newHashSet();
        for (List<Item> list : itemRegistry.values()) {
            ret.addAll(list);
        }
        return ret;
    }

    public static ThaumicTinkererRecipe getRecipe(ITTinkererRegisterable item) {
        return recipeMap.get(item);
    }

    public static ThaumicTinkererRecipe getFirstRecipeFromItem(Class<? extends Item> item) {
        return recipeMap.get(getFirstItemFromClass(item));
    }

    public static ThaumicTinkererRecipe getFirstRecipeFromBlock(Class<? extends Block> item) {
        return recipeMap.get(getFirstBlockFromClass(item));
    }

    public static ArrayList<Item> getItemFromClass(Class<?> clazz) {
        return itemRegistry.get(clazz);
    }

    public static Item getFirstItemFromClass(Class<? extends Item> clazz) {
        return itemRegistry.get(clazz) != null ? itemRegistry.get(clazz).get(0) : null;
    }

    public static Item getItemFromClassAndName(Class<?> clazz, String s) {
        if (itemRegistry.get(clazz) == null) {
            return null;
        }
        for (Item i : getItemFromClass(clazz)) {
            if (((ITTinkererItem) i).getItemName().equals(s)) {
                return i;
            }
        }
        return null;
    }

    public static Block getBlockFromClassAndName(Class<?> clazz, String s) {
        if (blockRegistry.get(clazz) == null) {
            return null;
        }
        for (Block i : getBlockFromClass(clazz)) {
            if (((ITTinkererBlock) i).getBlockName().equals(s)) {
                return i;
            }
        }
        return null;
    }

    public static ArrayList<Block> getBlockFromClass(Class<?> clazz) {
        return blockRegistry.get(clazz);
    }

    public static Block getFirstBlockFromClass(Class<? extends Block> clazz) {
        return blockRegistry.get(clazz) != null ? blockRegistry.get(clazz).get(0) : null;
    }

    public void registerClasses() {
        try {
            ClassPath classPath = ClassPath.from(this.getClass().getClassLoader());
            for (ClassPath.ClassInfo classInfo : classPath.getTopLevelClassesRecursive("pixlepix.auracascade.block")) {
                if (ITTinkererBlock.class.isAssignableFrom(classInfo.load()) && !Modifier.isAbstract(classInfo.load().getModifiers())) {
                    blockClasses.add(classInfo.load());
                }
            }
            for (ClassPath.ClassInfo classInfo : classPath.getTopLevelClassesRecursive("pixlepix.auracascade.item")) {
                if (ITTinkererItem.class.isAssignableFrom(classInfo.load()) && !ItemBlock.class.isAssignableFrom(classInfo.load()) && !Modifier.isAbstract(classInfo.load().getModifiers())) {
                    itemClasses.add(classInfo.load());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registerRecipe(ITTinkererRegisterable nextItem) {
        ThaumicTinkererRecipe thaumicTinkererRecipe = nextItem.getRecipeItem();
        if (thaumicTinkererRecipe != null) {
            thaumicTinkererRecipe.registerRecipe();
            recipeMap.put(nextItem, thaumicTinkererRecipe);
        }
    }

    public void preInit() {
        registerClasses();
        for (Class<?> clazz : blockClasses) {
            try {
                Block newBlock = (Block) clazz.newInstance();
                if (((ITTinkererBlock) newBlock).shouldRegister()) {
                    newBlock.setUnlocalizedName(((ITTinkererBlock) newBlock).getBlockName());
                    ArrayList<Block> blockList = new ArrayList<Block>();
                    blockList.add(newBlock);

                    if (((ITTinkererBlock) newBlock).getSpecialParameters() != null) {
                        for (Object param : ((ITTinkererBlock) newBlock).getSpecialParameters()) {

                            for (Constructor<?> constructor : clazz.getConstructors()) {
                                if (constructor.getParameterTypes().length > 0 && constructor.getParameterTypes()[0].isAssignableFrom(param.getClass())) {
                                    Block nextBlock = (Block) clazz.getConstructor(param.getClass()).newInstance(param);
                                    nextBlock.setUnlocalizedName(((ITTinkererBlock) nextBlock).getBlockName());
                                    blockList.add(nextBlock);
                                    break;
                                }
                            }
                        }
                    }
                    blockRegistry.put(clazz, blockList);

                    if (((ITTinkererBlock) newBlock).getItemBlock() != null) {
                        Item newItem = ((ITTinkererBlock) newBlock).getItemBlock().getConstructor(Block.class).newInstance(newBlock);
                        newItem.setUnlocalizedName(((ITTinkererItem) newItem).getItemName());
                        ArrayList<Item> itemList = new ArrayList<Item>();
                        itemList.add(newItem);
                        itemRegistry.put(((ITTinkererBlock) newBlock).getItemBlock(), itemList);

                    }
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        for (Class<?> clazz : itemClasses) {
            try {
                Item newItem = (Item) clazz.newInstance();
                if (((ITTinkererItem) newItem).shouldRegister()) {
                    newItem.setUnlocalizedName(((ITTinkererItem) newItem).getItemName());
                    ArrayList<Item> itemList = new ArrayList<Item>();
                    itemList.add(newItem);
                    if (((ITTinkererItem) newItem).getSpecialParameters() != null) {
                        for (Object param : ((ITTinkererItem) newItem).getSpecialParameters()) {
                            for (Constructor<?> constructor : clazz.getConstructors()) {
                                if (constructor.getParameterTypes().length > 0 && constructor.getParameterTypes()[0].isAssignableFrom(param.getClass())) {
                                    Item nextItem = (Item) constructor.newInstance(param);
                                    nextItem.setUnlocalizedName(((ITTinkererItem) nextItem).getItemName());
                                    itemList.add(nextItem);
                                    break;
                                }
                            }
                        }
                    }
                    itemRegistry.put(clazz, itemList);
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Made it past item classes");
        for (ArrayList<Block> blockArrayList : blockRegistry.values()) {
            for (Block block : blockArrayList) {
                if (((ITTinkererBlock) block).getItemBlock() != null) {
                	//TODO these may break
                    //GameRegistry.registerBlock(block, ((ITTinkererBlock) block).getItemBlock(), ((ITTinkererBlock) block).getBlockName());
                    ForgeRegistries.BLOCKS.register(block);
                } else {
                    //GameRegistry.registerBlock(block, ((ITTinkererBlock) block).getBlockName());
                    ForgeRegistries.BLOCKS.register(block);
                }
                if (((ITTinkererBlock) block).getTileEntity() != null) {
                    GameRegistry.registerTileEntity(((ITTinkererBlock) block).getTileEntity(), ConstantMod.prefixMod + ((ITTinkererBlock) block).getBlockName());
                }
                if (block instanceof IMultiTileEntityBlock) {
                    for (Map.Entry<Class<? extends TileEntity>, String> tile : ((IMultiTileEntityBlock) block).getAdditionalTileEntities().entrySet()) {
                        GameRegistry.registerTileEntity(tile.getKey(), tile.getValue());
                    }
                }
                if (((ITTinkererBlock) block).shouldDisplayInTab() && FMLCommonHandler.instance().getSide() == Side.CLIENT) {
                    ModCreativeTab.INSTANCE.addBlock(block);
                }
            }
        }

        for (ArrayList<Item> itemArrayList : itemRegistry.values()) {
            for (Item item : itemArrayList) {
                if (!(item instanceof ItemBlock)) {
                	//TODO this may have broken.
                  //  GameRegistry.registerItem(item, ((ITTinkererItem) item).getItemName());
                    ForgeRegistries.ITEMS.register(item);
                    if (((ITTinkererItem) item).shouldDisplayInTab() && FMLCommonHandler.instance().getSide() == Side.CLIENT) {
                        ModCreativeTab.INSTANCE.addItem(item);
                    }
                }
            }
        }
    }

    public void init() {

        for (ArrayList<Item> itemArrayList : itemRegistry.values()) {
            for (Item item : itemArrayList) {
                registerRecipe((ITTinkererRegisterable) item);
            }
        }

        for (ArrayList<Block> blockArrayList : blockRegistry.values()) {
            for (Block block : blockArrayList) {
                registerRecipe((ITTinkererRegisterable) block);
            }
        }

    }

    public void postInit() {

        ModCreativeTab.INSTANCE.addAllItemsAndBlocks();
    }

}
