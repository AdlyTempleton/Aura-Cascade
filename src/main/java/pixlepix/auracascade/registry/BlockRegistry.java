package pixlepix.auracascade.registry;

import com.google.common.collect.Lists;
import com.google.common.reflect.ClassPath;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.main.ConstantMod;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockRegistry {

    private ArrayList<Class<? extends ITTinkererItem>> itemClasses = new ArrayList<Class<? extends ITTinkererItem>>();
    private static HashMap<Class<? extends ITTinkererItem>, ArrayList<ITTinkererItem>> itemRegistry = new HashMap<Class<? extends ITTinkererItem>, ArrayList<ITTinkererItem>>();

    private ArrayList<Class<? extends ITTinkererBlock>> blockClasses = new ArrayList<Class<? extends ITTinkererBlock>>();
    private static HashMap<Class<? extends ITTinkererBlock>, ArrayList<ITTinkererBlock>> blockRegistry = new HashMap<Class<? extends ITTinkererBlock>, ArrayList<ITTinkererBlock>>();

    public static HashMap<ITTinkererRegisterable, ThaumicTinkererRecipe> recipeMap = new HashMap<ITTinkererRegisterable, ThaumicTinkererRecipe>();

    public static ThaumicTinkererRecipe getRecipe(ITTinkererRegisterable item) {
        return recipeMap.get(item);
    }

    public static ThaumicTinkererRecipe getFirstRecipeFromItem(Class<? extends ITTinkererItem> item) {
        return recipeMap.get(getFirstItemFromClass(item));
    }

    public void registerClasses() {
        try {
            ClassPath classPath = ClassPath.from(this.getClass().getClassLoader());
            for (ClassPath.ClassInfo classInfo : classPath.getTopLevelClassesRecursive("pixlepix.auracascade.block")) {
                Class<?> classLoad = classInfo.load();
                if (ITTinkererBlock.class.isAssignableFrom(classLoad) && Block.class.isAssignableFrom(classLoad) && !Modifier.isAbstract(classLoad.getModifiers())) {
                    blockClasses.add((Class<? extends ITTinkererBlock>) classLoad);
                }
            }
            for (ClassPath.ClassInfo classInfo : classPath.getTopLevelClassesRecursive("pixlepix.auracascade.item")) {
                Class<?> classLoad = classInfo.load();
                if (ITTinkererItem.class.isAssignableFrom(classLoad) && Item.class.isAssignableFrom(classLoad) && !ItemBlock.class.isAssignableFrom(classLoad) && !Modifier.isAbstract(classLoad.getModifiers())) {
                    itemClasses.add((Class<? extends ITTinkererItem>) classLoad);
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
        for (Class<? extends ITTinkererBlock> clazz : blockClasses) {
            try {
                ITTinkererBlock tinkererBlock = clazz.newInstance();
                Block block = (Block) tinkererBlock;

                if (block == null) {
                    AuraCascade.log.debug("{} returned a null block upon registration", clazz.getName());
                    continue;
                }

                if (tinkererBlock.shouldRegister()) {
                    block.setBlockName(tinkererBlock.getBlockName());
                    ArrayList<ITTinkererBlock> blockList = new ArrayList<ITTinkererBlock>();
                    blockList.add(tinkererBlock);

                    ArrayList<Object> params = tinkererBlock.getSpecialParameters();
                    if (params != null && !params.isEmpty()) {
                        for (Object param : params) {
                            Constructor<? extends ITTinkererBlock> constructor = clazz.getConstructor(param.getClass());
                            if (constructor != null) {
                                ITTinkererBlock nextBlock = constructor.newInstance(param);
                                ((Block) nextBlock).setBlockName(nextBlock.getBlockName());
                                blockList.add(nextBlock);
                            }
                        }
                    }
                    blockRegistry.put(clazz, blockList);

                    if (tinkererBlock.getItemBlock() != null) {
                        Class<? extends ItemBlock> itemBlock = tinkererBlock.getItemBlock();
                        ItemBlock newItem = itemBlock.getConstructor(Block.class).newInstance(block);
                        ITTinkererItem tinkererItem = (ITTinkererItem) newItem;
                        newItem.setUnlocalizedName(tinkererItem.getItemName());
                        itemRegistry.put(tinkererItem.getClass(), Lists.<ITTinkererItem>newArrayList(tinkererItem));
                    }
                }
            } catch (Exception e) {
                AuraCascade.log.fatal("Fatal error loading Aura Cascade");
                AuraCascade.log.fatal("--------------------------------", e);
            }
        }
        for (Class<? extends ITTinkererItem> clazz : itemClasses) {
            try {
                ITTinkererItem tinkererItem = clazz.newInstance();
                Item newItem = (Item) tinkererItem;

                if (newItem == null) {
                    AuraCascade.log.debug("{} returned a null item upon registration", clazz.getName());
                    continue;
                }

                if (tinkererItem.shouldRegister()) {
                    newItem.setUnlocalizedName(tinkererItem.getItemName());
                    ArrayList<ITTinkererItem> itemList = new ArrayList<ITTinkererItem>();
                    itemList.add(tinkererItem);

                    ArrayList<Object> params = tinkererItem.getSpecialParameters();
                    AuraCascade.log.info("Starting: " + tinkererItem.getItemName());
                    if (params != null && !params.isEmpty()) {
                        for (Object param : params) {
                            AuraCascade.log.info(param.toString());
                            Constructor<? extends ITTinkererItem> constructor = clazz.getConstructor(param.getClass());
                            if (constructor != null) {
                                ITTinkererItem nextItem = constructor.newInstance(param);
                                ((Item) nextItem).setUnlocalizedName(nextItem.getItemName());
                                itemList.add(nextItem);
                            }
                        }
                    }
                    AuraCascade.log.info("Finished");
                    itemRegistry.put(clazz, itemList);
                }
            } catch (Exception e) {
                AuraCascade.log.fatal("Fatal error loading Aura Cascade");
                AuraCascade.log.fatal("--------------------------------", e);
            }
        }

        for (ArrayList<ITTinkererBlock> blockArrayList : blockRegistry.values()) {
            for (ITTinkererBlock tinkererBlock : blockArrayList) {
                Block block = (Block) tinkererBlock;
                Class<? extends ItemBlock> itemClazz = tinkererBlock.getItemBlock();
                if (itemClazz != null) {
                    GameRegistry.registerBlock(block, itemClazz, tinkererBlock.getBlockName());
                } else {
                    GameRegistry.registerBlock(block, tinkererBlock.getBlockName());
                }
                Class<? extends TileEntity> tileEntity = tinkererBlock.getTileEntity();
                if (tileEntity != null) {
                    GameRegistry.registerTileEntity(tileEntity, ConstantMod.prefixMod + tinkererBlock.getBlockName());
                }
                if (block instanceof IMultiTileEntityBlock) {
                    for (Map.Entry<Class<? extends TileEntity>, String> tile : ((IMultiTileEntityBlock) block).getAdditionalTileEntities().entrySet()) {
                        GameRegistry.registerTileEntity(tile.getKey(), tile.getValue());
                    }
                }
                if (tinkererBlock.shouldDisplayInTab() && FMLCommonHandler.instance().getSide() == Side.CLIENT) {
                    ModCreativeTab.INSTANCE.addBlock(block);
                }
            }
        }

    }

    public static List<Item> getItemFromClass(Class clazz) {
        return (List) itemRegistry.get(clazz);
    }

    public static Item getFirstItemFromClass(Class<? extends ITTinkererItem> clazz) {
        return itemRegistry.get(clazz) != null ? (Item) itemRegistry.get(clazz).get(0) : null;
    }

    public static Item getItemFromClassAndName(Class clazz, String s) {
        List<Item> items = getItemFromClass(clazz);

        if (items == null || items.isEmpty())
            return null;

        for (Item item : items) {
            if (item.getUnlocalizedName().equals(s)) {
                return item;
            }
        }
        return null;
    }

    public static Block getBlockFromClassAndName(Class clazz, String s) {
        List<Block> blocks = getBlockFromClass(clazz);

        if (blocks == null || blocks.isEmpty())
            return null;

        for (Block block : blocks) {
            if (block.getUnlocalizedName().equals(s)) {
                return block;
            }
        }
        return null;
    }

    public static List<Block> getBlockFromClass(Class clazz) {
        return (List) blockRegistry.get(clazz);
    }

    public static Block getFirstBlockFromClass(Class clazz) {
        return blockRegistry.get(clazz) != null ? (Block) blockRegistry.get(clazz).get(0) : null;
    }

    public void init() {

        for (ArrayList<ITTinkererItem> itemArrayList : itemRegistry.values()) {
            for (ITTinkererItem item : itemArrayList) {
                registerRecipe(item);
            }
        }

        for (ArrayList<ITTinkererBlock> blockArrayList : blockRegistry.values()) {
            for (ITTinkererBlock block : blockArrayList) {
                registerRecipe(block);

            }
        }

        for (ArrayList<ITTinkererItem> itemArrayList : itemRegistry.values()) {
            for (ITTinkererItem tinkererItem : itemArrayList) {
                Item item = (Item) tinkererItem;
                GameRegistry.registerItem(item, tinkererItem.getItemName());

                if (tinkererItem.shouldDisplayInTab() && FMLCommonHandler.instance().getSide() == Side.CLIENT) {
                    ModCreativeTab.INSTANCE.addItem(item);
                }
            }
        }

    }

    public void postInit() {

        ModCreativeTab.INSTANCE.addAllItemsAndBlocks();
    }

}
