package pixlepix.auracascade.block;

import cpw.mods.fml.common.ModAPIManager;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.block.tile.*;
import pixlepix.auracascade.data.AuraQuantity;
import pixlepix.auracascade.data.CoordTuple;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.data.IToolTip;
import pixlepix.auracascade.item.ItemAuraCrystal;
import pixlepix.auracascade.item.ItemMaterial;
import pixlepix.auracascade.main.Config;
import pixlepix.auracascade.main.EnumColor;
import pixlepix.auracascade.network.PacketBurst;
import pixlepix.auracascade.registry.BlockRegistry;
import pixlepix.auracascade.registry.CraftingBenchRecipe;
import pixlepix.auracascade.registry.ITTinkererBlock;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

import java.util.ArrayList;
import java.util.List;

public class AuraBlock extends Block implements IToolTip, ITTinkererBlock, ITileEntityProvider {

    public static String name = "auraNode";
    //"" is default
    //"pump" is AuraTilePump\
    //"black" is AuraTileBlack etc
    String type;
    private IIcon topIcon;
    private IIcon sideIcon;
    private IIcon botIcon;


    public AuraBlock(String type) {
        super(Material.glass);
        this.type = type;

        if (!type.equals("craftingCenter")) {
            setBlockBounds(.25F, .25F, .25F, .75F, .75F, .75F);
        }
        setLightOpacity(0);
        setHardness(2F);
    }

    public AuraBlock() {
        this("");
        setHardness(2F);
    }

    public static AuraBlock getBlockFromName(String name) {
        List<Block> blockList = BlockRegistry.getBlockFromClass(AuraBlock.class);
        for (Block b : blockList) {
            if (((AuraBlock) b).type.equals(name)) {
                return (AuraBlock) b;
            }
        }
        return null;
    }

    public static ItemStack getAuraNodeItemstack() {
        List<Block> blockList = BlockRegistry.getBlockFromClass(AuraBlock.class);
        for (Block b : blockList) {
            if (((AuraBlock) b).type.equals("")) {
                return new ItemStack(b);
            }
        }
        AuraCascade.log.warn("Failed to find aura node itemstack. Something has gone horribly wrong");
        return null;
    }

    public static ItemStack getAuraNodePumpItemstack() {
        List<Block> blockList = BlockRegistry.getBlockFromClass(AuraBlock.class);
        for (Block b : blockList) {
            if (((AuraBlock) b).type.equals("pump")) {
                return new ItemStack(b);
            }
        }
        AuraCascade.log.warn("Failed to find aura node pump itemstack. Something has gone horribly wrong");
        return null;
    }

    //Prevents being moved by RIM
    public static boolean _Immovable() {
        return true;
    }

    @Override
    public String getHarvestTool(int metadata) {
        return "pickaxe";
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World world, int x, int y, int z, int meta) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity instanceof AuraTile) {
            int aura = ((AuraTile) tileEntity).storage.getTotalAura();
            return (int) Math.floor(Math.log10(aura));
        } else {
            return super.getComparatorInputOverride(world, x, y, z, meta);
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float fx, float fy, float fz) {
        if (!world.isRemote && world.getTileEntity(x, y, z) instanceof AuraTile) {

            if (world.getTileEntity(x, y, z) instanceof AuraTileCapacitor && player.isSneaking()) {
                AuraTileCapacitor capacitor = (AuraTileCapacitor) world.getTileEntity(x, y, z);
                capacitor.storageValueIndex = (capacitor.storageValueIndex + 1) % capacitor.storageValues.length;
                player.addChatComponentMessage(new ChatComponentText("Max Storage: " + capacitor.storageValues[capacitor.storageValueIndex]));
                world.markBlockForUpdate(x, y, z);
                return true;
            } else if (world.getTileEntity(x, y, z) instanceof AuraTilePedestal && !player.isSneaking()) {
                AuraTilePedestal pedestal = (AuraTilePedestal) world.getTileEntity(x, y, z);

                //Remove current itemstack from pedestal
                if (pedestal.itemStack != null) {
                    EntityItem item = new EntityItem(world, player.posX, player.posY, player.posZ, pedestal.itemStack);
                    world.spawnEntityInWorld(item);
                }

                pedestal.itemStack = player.inventory.getCurrentItem() != null ? player.inventory.decrStackSize(player.inventory.currentItem, 1) : null;
                world.markBlockForUpdate(x, y, z);
                world.notifyBlockChange(x, y, z, this);
                return true;

            } else if (world.getTileEntity(x, y, z) instanceof AuraTile && player.inventory.getCurrentItem() == null) {
                player.addChatComponentMessage(new ChatComponentText("Aura:"));
                for (EnumAura aura : EnumAura.values()) {
                    if (((AuraTile) world.getTileEntity(x, y, z)).storage.get(aura) != 0) {
                        player.addChatComponentMessage(new ChatComponentText(aura.name + " Aura: " + ((AuraTile) world.getTileEntity(x, y, z)).storage.get(aura)));
                    }
                }

                if (world.getTileEntity(x, y, z) instanceof AuraTilePumpBase) {

                    player.addChatComponentMessage(new ChatComponentText("Power: " + ((AuraTilePumpBase) world.getTileEntity(x, y, z)).pumpPower));
                }
                return true;
            }
        } else if (!world.isRemote && world.getTileEntity(x, y, z) instanceof CraftingCenterTile && player.inventory.getCurrentItem() == null) {
            CraftingCenterTile tile = (CraftingCenterTile) world.getTileEntity(x, y, z);
            if (tile.getRecipe() != null) {
                player.addChatComponentMessage(new ChatComponentText(EnumColor.DARK_BLUE + "Making: " + tile.getRecipe().result.getDisplayName()));
                for (ForgeDirection direction : CraftingCenterTile.pedestalRelativeLocations) {
                    AuraTilePedestal pedestal = (AuraTilePedestal) new CoordTuple(x, y, z).add(direction).getTile(world);
                    if (tile.getRecipe() != null && tile.getRecipe().getAuraFromItem(pedestal.itemStack) != null) {
                        player.addChatComponentMessage(new ChatComponentText("" + EnumColor.AQUA + pedestal.powerReceived + "/" + tile.getRecipe().getAuraFromItem(pedestal.itemStack).getNum() + " (" + tile.getRecipe().getAuraFromItem(pedestal.itemStack).getType().name + ")"));
                    } else {
                        AuraCascade.log.warn("Invalid recipe when checking crafting center");
                    }
                }
            } else {
                player.addChatComponentMessage(new ChatComponentText("No Recipe Selected"));
            }
            return true;
        }
        return true;
    }


    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        super.onEntityCollidedWithBlock(world, x, y, z, entity);

        TileEntity te = world.getTileEntity(x, y, z);
        if (entity instanceof EntityItem && !world.isRemote) {
            ItemStack stack = ((EntityItem) entity).getEntityItem();
            if (stack.getItem() instanceof ItemAuraCrystal) {
                if (te instanceof AuraTile) {
                    ((AuraTile) te).storage.add(new AuraQuantity(EnumAura.values()[stack.getItemDamage()], 1000 * stack.stackSize));
                    world.markBlockForUpdate(x, y, z);
                    world.notifyBlockChange(x, y, z, this);
                    AuraCascade.proxy.networkWrapper.sendToAllAround(new PacketBurst(1, entity.posX, entity.posY, entity.posZ), new NetworkRegistry.TargetPoint(world.provider.dimensionId, x, y, z, 32));

                    entity.setDead();
                }

            }
        }
        if (!world.isRemote && te instanceof AuraTilePumpProjectile) {
            ((AuraTilePumpProjectile) te).onEntityCollidedWithBlock(entity);
        }
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {


        if(type.contains("Alt")){

            topIcon = iconRegister.registerIcon("aura:" + type.replace("Alt", "") + "Node_Top");
            sideIcon = iconRegister.registerIcon("aura:" + type.replace("Alt", "") + "Node_Side_Alt");

            botIcon = iconRegister.registerIcon("aura:" + type.replace("Alt", "") + "Node_Bottom");
        }else {
            sideIcon = iconRegister.registerIcon("aura:" + type + "Node_Side");

            botIcon = iconRegister.registerIcon("aura:" + type + "Node_Bottom");
            topIcon = iconRegister.registerIcon("aura:" + type + "Node_Top");
        }


    }

    @Override
    public IIcon getIcon(int side, int meta) {
        switch (side) {
            case 0:
                return botIcon;
            case 1:
                return topIcon;
            default:
                return sideIcon;
        }
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int p_149749_6_) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof IInventory) {
            IInventory inv = (IInventory) te;
            for (int i = 0; i < inv.getSizeInventory(); i++) {
                if (inv.getStackInSlot(i) != null) {
                    float f = 0.7F;
                    double d0 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
                    double d1 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
                    double d2 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
                    EntityItem entityitem = new EntityItem(world, (double) x + d0, (double) y + d1, (double) z + d2, inv.getStackInSlot(i));
                    entityitem.delayBeforeCanPickup = 10;
                    world.spawnEntityInWorld(entityitem);
                }
            }
        }
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        // TODO Auto-generated method stub
        if (type.equals("pump")) {
            return new CraftingBenchRecipe(new ItemStack(this), "ILI", "INI", "ILI", 'I', new ItemStack(Items.iron_ingot), 'L', new ItemStack(Items.dye, 1, 4), 'N', getAuraNodeItemstack());
        }
        if (type.equals("black")) {
            return new CraftingBenchRecipe(new ItemStack(this), " I ", "INI", " I ", 'I', ItemMaterial.getIngot(EnumAura.BLACK_AURA), 'N', getAuraNodeItemstack());
        }
        if (type.equals("conserve")) {
            return new CraftingBenchRecipe(new ItemStack(this), "B", "N", "B", 'B', ItemMaterial.getIngot(EnumAura.BLUE_AURA), 'N', getAuraNodeItemstack());
        }
        if (type.equals("capacitor")) {
            return new CraftingBenchRecipe(new ItemStack(this), "Y", "N", "G", 'Y', ItemMaterial.getIngot(EnumAura.YELLOW_AURA), 'G', ItemMaterial.getIngot(EnumAura.GREEN_AURA), 'N', getAuraNodeItemstack());
        }

        if (type.equals("craftingPedestal")) {
            return new CraftingBenchRecipe(new ItemStack(this), "BBB", "BNB", "BBB", 'B', ItemMaterial.getIngot(EnumAura.BLUE_AURA), 'N', getAuraNodeItemstack());
        }


        if (type.equals("craftingCenter")) {
            return new CraftingBenchRecipe(new ItemStack(this), "GGG", "RDR", "RRR", 'G', new ItemStack(Items.gold_ingot), 'R', ItemMaterial.getIngot(EnumAura.RED_AURA), 'D', new ItemStack(Items.diamond));
        }


        if (type.equals("orange")) {
            return new CraftingBenchRecipe(new ItemStack(this), " O ", "ONO", " O ", 'O', ItemMaterial.getIngot(EnumAura.ORANGE_AURA), 'N', getAuraNodeItemstack());
        }

        if (type.equals("pumpProjectile")) {
            return new CraftingBenchRecipe(new ItemStack(this), "XXX", "IPI", "GIG", 'X', new ItemStack(Items.arrow), 'I', ItemMaterial.getGem(EnumAura.VIOLET_AURA), 'G', ItemMaterial.getIngot(EnumAura.VIOLET_AURA), 'P', getAuraNodePumpItemstack());
        }
        if (type.equals("pumpFall")) {
            return new CraftingBenchRecipe(new ItemStack(this), "XXX", "IPI", "GIG", 'X', new ItemStack(Items.water_bucket), 'I', ItemMaterial.getGem(EnumAura.BLUE_AURA), 'G', ItemMaterial.getIngot(EnumAura.BLUE_AURA), 'P', getAuraNodePumpItemstack());
        }
        if (type.equals("pumpLight")) {
            return new CraftingBenchRecipe(new ItemStack(this), "XXX", "IPI", "GIG", 'X', new ItemStack(Blocks.glowstone), 'I', ItemMaterial.getGem(EnumAura.YELLOW_AURA), 'G', ItemMaterial.getIngot(EnumAura.YELLOW_AURA), 'P', getAuraNodePumpItemstack());
        }
        if (type.equals("pumpRedstone")) {
            return new CraftingBenchRecipe(new ItemStack(this), "XXX", "IPI", "GIG", 'X', new ItemStack(Blocks.redstone_block), 'I', ItemMaterial.getGem(EnumAura.RED_AURA), 'G', ItemMaterial.getIngot(EnumAura.RED_AURA), 'P', getAuraNodePumpItemstack());
        }
        if(type.equals("pumpAlt")){
            return new CraftingBenchRecipe(new ItemStack(this), " E ", "EPE", " E ", 'P', new ItemStack(getBlockFromName("pump")));
        }
        if(type.equals("pumpRedstoneAlt")){
            return new CraftingBenchRecipe(new ItemStack(this), " E ", "EPE", " E ", 'P', new ItemStack(getBlockFromName("pumpRedstone")));
        }
        if(type.equals("pumpLightAlt")){
            return new CraftingBenchRecipe(new ItemStack(this), " E ", "EPE", " E ", 'P', new ItemStack(getBlockFromName("pumpLight")));
        }
        if(type.equals("pumpFallAlt")){
            return new CraftingBenchRecipe(new ItemStack(this), " E ", "EPE", " E ", 'P', new ItemStack(getBlockFromName("pumpFall")));
        }
        if (type.equals("pumpProjectileAlt")) {
            return new CraftingBenchRecipe(new ItemStack(this), " E ", "EPE", " E ", 'P', new ItemStack(getBlockFromName("pumpProjectile")));
        }
        if (type.equals("flux")) {
            return new CraftingBenchRecipe(new ItemStack(this), "III", "RNR", "III", 'R', ItemMaterial.getGem(EnumAura.RED_AURA), 'I', ItemMaterial.getGem(EnumAura.ORANGE_AURA), 'N', getAuraNodeItemstack());
        }
        return new CraftingBenchRecipe(new ItemStack(this), "PPP", "PRP", "PPP", 'P', new ItemStack(Blocks.glass_pane), 'R', new ItemStack(Blocks.redstone_block));
    }

    @Override
    public int getCreativeTabPriority() {
        if (type.equals("pump") || type.equals("") || type.equals("flux")) {
            return 100;
        }
        if (type.contains("Alt")) {
            return -25;
        }

        if (type.contains("pump")) {
            return 75;
        }
        return 50;
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        // TODO Auto-generated method stub
        ArrayList result = new ArrayList<Object>();
        result.add("pump");
        result.add("black");
        result.add("conserve");
        result.add("capacitor");

        result.add("craftingCenter");

        result.add("craftingPedestal");
        result.add("orange");

        result.add("pumpAlt");

        result.add("pumpProjectile");
        result.add("pumpFall");
        result.add("pumpLight");
        result.add("pumpRedstone");
        result.add("pumpProjectileAlt");
        result.add("pumpFallAlt");
        result.add("pumpLightAlt");
        result.add("pumpRedstoneAlt");


        if (ModAPIManager.INSTANCE.hasAPI("CoFHAPI|energy")) {
            result.add("flux");
        }
        return result;
    }

    @Override
    public String getBlockName() {
        // TODO Auto-generated method stub
        return name + type;
    }

    @Override
    public boolean shouldRegister() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean shouldDisplayInTab() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public Class<? extends ItemBlock> getItemBlock() {
        // TODO Auto-generated method stub
        return null;
    }



    @Override
    public Class<? extends TileEntity> getTileEntity() {

        if (type.equals("pump")) {
            return AuraTilePump.class;
        }
        if (type.equals("black")) {
            return AuraTileBlack.class;
        }
        if (type.equals("conserve")) {
            return AuraTileConserve.class;
        }
        if (type.equals("capacitor")) {
            return AuraTileCapacitor.class;
        }

        if (type.equals("craftingPedestal")) {
            return AuraTilePedestal.class;
        }

        if (type.equals("craftingCenter")) {
            return CraftingCenterTile.class;
        }
        if (type.equals("flux")) {
            return AuraTileRF.class;

        }


        if (type.equals("orange")) {
            return AuraTileOrange.class;
        }
        if (type.equals("pumpProjectile")) {
            return AuraTilePumpProjectile.class;
        }
        if (type.equals("pumpFall")) {
            return AuraTilePumpFall.class;
        }
        if (type.equals("pumpLight")) {
            return AuraTilePumpLight.class;
        }
        if (type.equals("pumpRedstone")) {
            return AuraTilePumpRedstone.class;
        }
        if (type.equals("pumpAlt")) {
            return AuraTilePumpAlt.class;
        }
        if (type.equals("pumpProjectileAlt")) {
            return AuraTilePumpProjectileAlt.class;
        }
        if (type.equals("pumpFallAlt")) {
            return AuraTilePumpFallAlt.class;
        }
        if (type.equals("pumpLightAlt")) {
            return AuraTilePumpLightAlt.class;
        }
        if (type.equals("pumpRedstoneAlt")) {
            return AuraTilePumpRedstoneAlt.class;
        }
        return AuraTile.class;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {

        try {
            return getTileEntity().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int damageDropped(int meta) {
        return meta;
    }

    @Override
    public List<String> getTooltipData(World world, EntityPlayer player, int x, int y, int z) {
        List<String> result = new ArrayList<String>();
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity instanceof AuraTile) {

            if (tileEntity instanceof AuraTileCapacitor) {
                AuraTileCapacitor capacitor = (AuraTileCapacitor) tileEntity;
                result.add("Max Storage: " + capacitor.storageValues[capacitor.storageValueIndex]);

            }
            if (((AuraTile) tileEntity).storage.getTotalAura() > 0) {
                result.add("Aura Stored: ");
                for (EnumAura aura : EnumAura.values()) {
                    if (((AuraTile) tileEntity).storage.get(aura) != 0) {
                        result.add("    " + aura.name + " Aura: " + ((AuraTile) tileEntity).storage.get(aura));
                    }
                }
            } else {
                result.add("No Aura");
            }
            if (tileEntity instanceof AuraTileCapacitor) {
                if (((AuraTileCapacitor) tileEntity).ticksDisabled > 0) {
                    result.add("Time until functional: " + ((AuraTileCapacitor) tileEntity).ticksDisabled / 20);
                }
            }
            if (tileEntity instanceof AuraTilePumpBase) {
                result.add("Time left: " + ((AuraTilePumpBase) tileEntity).pumpPower + " seconds");
                result.add("Power: " + ((AuraTilePumpBase) tileEntity).pumpSpeed + " power per second");
                if(((AuraTilePumpBase) tileEntity).isAlternator()){
                    AuraTilePumpBase altPump = (AuraTilePumpBase) tileEntity;
                    int power = (int) (altPump.pumpSpeed * altPump.getAlternatingFactor());
                    result.add("Phase Power: " + power);
                }
            }

            if (ModAPIManager.INSTANCE.hasAPI("CoFHAPI|energy")) {
                if(tileEntity instanceof AuraTileRF){
                    AuraTileRF auraTileRF = (AuraTileRF) tileEntity;
                    result.add("RF/t Output: " + auraTileRF.lastPower * Config.powerFactor);
                }

            }

        } else if (tileEntity instanceof CraftingCenterTile) {
            CraftingCenterTile tile = (CraftingCenterTile) tileEntity;
            if (tile.getRecipe() != null) {
                result.add("Making: " + tile.getRecipe().result.getDisplayName());
                for (ForgeDirection direction : CraftingCenterTile.pedestalRelativeLocations) {
                    AuraTilePedestal pedestal = (AuraTilePedestal) new CoordTuple(x, y, z).add(direction).getTile(world);
                    if (tile.getRecipe() != null && tile.getRecipe().getAuraFromItem(pedestal.itemStack) != null) {
                        result.add("    " + pedestal.powerReceived + "/" + tile.getRecipe().getAuraFromItem(pedestal.itemStack).getNum() + " (" + tile.getRecipe().getAuraFromItem(pedestal.itemStack).getType().name + ")");
                    } else {
                        AuraCascade.log.warn("Invalid recipe when checking crafting center");
                    }
                }
            } else {
                result.add("No Valid Recipe Selected");
            }

        }
        return result;
    }
}

