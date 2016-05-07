package pixlepix.auracascade.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import pixlepix.auracascade.main.EnumColor;
import pixlepix.auracascade.main.ParticleEffects;
import pixlepix.auracascade.registry.CraftingBenchRecipe;
import pixlepix.auracascade.registry.ITTinkererItem;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

/**
 * Created by localmacaccount on 5/16/15.
 */
public class ItemPrismaticWand extends Item implements ITTinkererItem {

    public static String[] modes = new String[]{EnumColor.AQUA + "Selection", EnumColor.YELLOW + "Copy", EnumColor.ORANGE + "Paste"};


    public ItemPrismaticWand() {
        super();
        setMaxStackSize(1);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        //More specific selections
        if (!player.isSneaking() && !world.isRemote) {
            if (stack.getTagCompound() == null) {
                stack.setTagCompound(new NBTTagCompound());
            }

            NBTTagCompound nbt = stack.getTagCompound();
            if (stack.getItemDamage() == 0) {
                if (nbt.hasKey("x1")) {
                    nbt.setInteger("x2", nbt.getInteger("x1"));
                    nbt.setInteger("y2", nbt.getInteger("y1"));
                    nbt.setInteger("z2", nbt.getInteger("z1"));
                }
                nbt.setInteger("x1", pos.getX());
                nbt.setInteger("y1", pos.getY());
                nbt.setInteger("z1", pos.getZ());
                player.addChatComponentMessage(new TextComponentString("Position set"));
                return EnumActionResult.PASS;
            }
        }
        return EnumActionResult.FAIL;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean p_77624_4_) {
        super.addInformation(stack, player, list, p_77624_4_);
        list.add(modes[stack.getItemDamage()]);

    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand){
        int mode = stack.getItemDamage();
        if (player.isSneaking()) {
            NBTTagCompound nbt = stack.getTagCompound();
            mode++;
            mode = mode % modes.length;
            stack.setItemDamage(mode);
            stack.setTagCompound(nbt);
            if (!world.isRemote) {
                player.addChatComponentMessage(new TextComponentString("Switched to: " + modes[mode]));
            }
        } else {
            if (stack.getTagCompound() == null) {
                stack.setTagCompound(new NBTTagCompound());
            }
            NBTTagCompound nbt = stack.getTagCompound();
            switch (mode) {
                case 1:
                    if (nbt.hasKey("x1") && nbt.hasKey("x2")) {
                        nbt.setInteger("cx1", nbt.getInteger("x1"));
                        nbt.setInteger("cy1", nbt.getInteger("y1"));
                        nbt.setInteger("cz1", nbt.getInteger("z1"));
                        nbt.setInteger("cx2", nbt.getInteger("x2"));
                        nbt.setInteger("cy2", nbt.getInteger("y2"));
                        nbt.setInteger("cz2", nbt.getInteger("z2"));

                        //This is how far away the player is from the copy/paste
                        nbt.setInteger("cxo", nbt.getInteger("x1") - roundToZero(player.posX));
                        nbt.setInteger("cyo", nbt.getInteger("y1") - roundToZero(player.posY));
                        nbt.setInteger("czo", nbt.getInteger("z1") - roundToZero(player.posZ));

                        if (!world.isRemote) {
                            player.addChatComponentMessage(new TextComponentString("Copied to clipboard"));
                        }
                    } else {

                        if (!world.isRemote) {
                            player.addChatComponentMessage(new TextComponentString("Invalid selection"));
                        }
                    }
                    break;
                case 2:
                    int x = (int) player.posX;
                    int y = (int) player.posY;
                    int z = (int) player.posZ;

                    if (nbt.hasKey("cx1")) {
                        //Selection boundaries
                        int cx1 = nbt.getInteger("cx1");
                        int cy1 = nbt.getInteger("cy1");
                        int cz1 = nbt.getInteger("cz1");
                        int cx2 = nbt.getInteger("cx2");
                        int cy2 = nbt.getInteger("cy2");
                        int cz2 = nbt.getInteger("cz2");

                        //Offset from where the player standed as they calculated
                        int xo = nbt.getInteger("cxo");
                        int yo = nbt.getInteger("cyo");
                        int zo = nbt.getInteger("czo");


                        //For simplicities sake, c*1 is lower than c*2
                        if (cx1 > cx2) {
                            //Yes, yes, bitwise
                            //But thats just a party trick
                            //I mean, if you go to some nerdy parties

                            //xo, yo, zo were originally calculated relative to x1, y1, z1
                            //When we swap x1, we need to recalculate xo
                            //The players position when they copy-pasted = cx1 - xo
                            //New xo = newX1 - playerPos 
                            //= cx2 - (cx1 - xo)
                            //= xo + (cx2 - cx1)
                            xo += (cx2 - cx1);
                            int t = cx1;
                            cx1 = cx2;
                            cx2 = t;


                        }

                        if (cy1 > cy2) {

                            yo += (cy2 - cy1);
                            int t = cy1;
                            cy1 = cy2;
                            cy2 = t;
                        }
                        if (cz1 > cz2) {
                            zo += (cz2 - cz1);
                            int t = cz1;
                            cz1 = cz2;
                            cz2 = t;
                        }

                        //itetate through selection
                        int xi = cx1;
                        do {
                            int yi = cy1;
                            do {
                                int zi = cz1;
                                do {

                                    //Offsets are calculated. These offsets should be the same between the target and the destination
                                    int dx = xi - cx1;
                                    int dy = yi - cy1;
                                    int dz = zi - cz1;

                                    BlockPos oldPos = new BlockPos(cx1 + dx, cy1 + dy, cz1 + dz);
                                    BlockPos newPos = new BlockPos(x + dx + xo, y + dy + yo, z + dz + zo);

                                    if (world.isAirBlock(newPos)) {
                                        Block block = world.getBlockState(oldPos).getBlock();
                                        Item item = Item.getItemFromBlock(block);
                                        int worldDmg = block.getMetaFromState(world.getBlockState(oldPos));
                                        //TODO Test that this properly gets damage dropped
                                        int dmg = block.damageDropped(world.getBlockState(oldPos));

                                        boolean usesMetadataForPlacing = false;
                                        List<ItemStack> drops = block.getDrops(world, oldPos, block.getStateFromMeta(dmg), 0);
                                        if (drops.size() == 1) {
                                            ItemStack dropStack = drops.get(0);
                                            usesMetadataForPlacing = dropStack.getItem() == item && dropStack.getItemDamage() == 0 && worldDmg != 0;
                                        }

                                        if (player.capabilities.isCreativeMode) {
                                            if (!world.isRemote) {
                                                world.setBlockState(newPos, block.getStateFromMeta(worldDmg), 3);
                                                particles(newPos);
                                            }

                                        } else if (player.inventory.hasItemStack(new ItemStack(item, 1, dmg))) {
                                            int slot = slotOfItemStack(new ItemStack(item, 1, dmg), player.inventory);
                                            if (item instanceof ItemBlock) {
                                                if (!world.isRemote) {
                                                    ((ItemBlock) item).placeBlockAt(player.inventory.getStackInSlot(slot), player, world, newPos, EnumFacing.DOWN, 0, 0, 0, ((ItemBlock) item).block.getStateFromMeta(dmg));
                                                    if (usesMetadataForPlacing) {
                                                        world.setBlockState(newPos, world.getBlockState(newPos).getBlock().getStateFromMeta(worldDmg), 3);
                                                    }
                                                }
                                                particles(newPos);

                                                player.inventory.decrStackSize(slot, 1);
                                            }
                                        }
                                    }

                                    zi++;
                                } while (zi <= cz2);
                                yi++;
                            } while (yi <= cy2);
                            xi++;
                        } while (xi <= cx2);

                        if (!world.isRemote) {
                            player.addChatComponentMessage(new TextComponentString("Successfully pasted building"));
                        }
                    } else {
                        if (!world.isRemote) {
                            player.addChatComponentMessage(new TextComponentString("Nothing copied"));
                        }

                    }
                    break;


            }
        }
        return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
    }

    public int roundToZero(double d) {
        return (int) (d > 0 ? Math.floor(d) : Math.ceil(d));

    }

    private void particles(BlockPos pos) {
        for (EnumFacing direction : EnumFacing.VALUES) {
            for (int i = 0; i < 3; i++) {
                Random random = new Random();
                double x = pos.getX() + random.nextDouble();
                double y = pos.getY() + random.nextDouble();
                double z = pos.getZ() + random.nextDouble();
                ParticleEffects.spawnParticle("witchMagic", x, y, z, 0, 0, 0, 0, 34, 264);
            }
        }

    }

    //Adapted from InventoryPlayer.hasItemStack
    public int slotOfItemStack(ItemStack stack, InventoryPlayer inv) {
        int i;

        for (i = 0; i < inv.mainInventory.length; ++i) {
            if (inv.mainInventory[i] != null && inv.mainInventory[i].isItemEqual(stack)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        return null;
    }

    @Override
    public String getItemName() {
        return "prismaticWand";
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
        return new CraftingBenchRecipe(new ItemStack(this), " P ", " I ", " I ", 'P', ItemMaterial.getPrism(), 'I', new ItemStack(Items.blaze_rod));
    }

    @Override
    public int getCreativeTabPriority() {
        return -25;
    }
}
