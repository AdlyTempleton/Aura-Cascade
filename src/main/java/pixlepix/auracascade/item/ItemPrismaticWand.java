package pixlepix.auracascade.item;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import pixlepix.auracascade.main.EnumColor;
import pixlepix.auracascade.registry.CraftingBenchRecipe;
import pixlepix.auracascade.registry.ITTinkererItem;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

import java.util.ArrayList;
import java.util.List;

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
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
        //More specific selections
        if (!player.isSneaking() && !world.isRemote) {
            if (stack.stackTagCompound == null) {
                stack.stackTagCompound = new NBTTagCompound();
            }

            NBTTagCompound nbt = stack.stackTagCompound;
            if (stack.getItemDamage() == 0) {
                if (nbt.hasKey("x1")) {
                    nbt.setInteger("x2", nbt.getInteger("x1"));
                    nbt.setInteger("y2", nbt.getInteger("y1"));
                    nbt.setInteger("z2", nbt.getInteger("z1"));
                }
                nbt.setInteger("x1", x);
                nbt.setInteger("y1", y);
                nbt.setInteger("z1", z);
                player.addChatComponentMessage(new ChatComponentText("Position set"));
                return true;
            }
        }
        return false;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_) {
        super.addInformation(stack, player, list, p_77624_4_);
        list.add(modes[stack.getItemDamage()]);

    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        int mode = stack.getItemDamage();
        if (player.isSneaking()) {
            NBTTagCompound nbt = stack.stackTagCompound;
            mode++;
            mode = mode % modes.length;
            stack.setItemDamage(mode);
            stack.stackTagCompound = nbt;
            if (!world.isRemote) {
                player.addChatComponentMessage(new ChatComponentText("Switched to: " + modes[mode]));
            }
        } else {
            if (stack.stackTagCompound == null) {
                stack.stackTagCompound = new NBTTagCompound();
            }
            NBTTagCompound nbt = stack.stackTagCompound;
            switch (mode) {
                    /*
                    case 0:

                        //Make sure onItemUseFirst hasn't already grabbed it


                        if (nbt.hasKey("x1")) {
                            nbt.setInteger("x2", nbt.getInteger("x1"));
                            nbt.setInteger("y2", nbt.getInteger("y1"));
                            nbt.setInteger("z2", nbt.getInteger("z1"));
                        }
                        nbt.setInteger("x1", (int) player.posX);
                        nbt.setInteger("y1", (int) player.posY);
                        nbt.setInteger("z1", (int) player.posZ);
                        player.addChatComponentMessage(new ChatComponentText("Position set"));
                        break;
                        */
                case 1:
                    if (nbt.hasKey("x1") && nbt.hasKey("x2")) {
                        nbt.setInteger("cx1", nbt.getInteger("x1"));
                        nbt.setInteger("cy1", nbt.getInteger("y1"));
                        nbt.setInteger("cz1", nbt.getInteger("z1"));
                        nbt.setInteger("cx2", nbt.getInteger("x2"));
                        nbt.setInteger("cy2", nbt.getInteger("y2"));
                        nbt.setInteger("cz2", nbt.getInteger("z2"));

                        //This is how far away the player is from the copy/paste
                        nbt.setInteger("cxo", (int) Math.floor(nbt.getInteger("x1") - player.posX) + 1);
                        nbt.setInteger("cyo", (int) Math.floor(nbt.getInteger("y1") - player.posY));
                        nbt.setInteger("czo", (int) Math.floor(nbt.getInteger("z1") - player.posZ) + 1);

                        if (!world.isRemote) {
                            player.addChatComponentMessage(new ChatComponentText("Copied to clipboard"));
                        }
                    } else {

                        if (!world.isRemote) {
                            player.addChatComponentMessage(new ChatComponentText("Invalid selection"));
                        }
                    }
                    break;
                case 2:
                    int x = (int) player.posX;
                    int y = (int) player.posY;
                    int z = (int) player.posZ;

                    if (nbt.hasKey("cx1")) {
                        int cx1 = nbt.getInteger("cx1");
                        int cy1 = nbt.getInteger("cy1");
                        int cz1 = nbt.getInteger("cz1");
                        int cx2 = nbt.getInteger("cx2");
                        int cy2 = nbt.getInteger("cy2");
                        int cz2 = nbt.getInteger("cz2");

                        int xo = nbt.getInteger("cxo");
                        int yo = nbt.getInteger("cyo");
                        int zo = nbt.getInteger("czo");


                        //For simplicities sake, c*1 is lower than c*2
                        if (cx1 > cx2) {
                            //Yes, yes, bitwise
                            //But thats just a party trick
                            //I mean, if you go to some nerdy parties
                            int t = cx1;
                            cx1 = cx2;
                            cx2 = t;
                        }

                        if (cy1 > cy2) {
                            int t = cy1;
                            cy1 = cy2;
                            cy2 = t;
                        }
                        if (cz1 > cz2) {
                            int t = cz1;
                            cz1 = cz2;
                            cz2 = t;
                        }

                        int xi = cx1;
                        do {
                            int yi = cy1;
                            do {
                                int zi = cz1;
                                do {

                                    int dx = xi - cx1;
                                    int dy = yi - cy1;
                                    int dz = zi - cz1;
                                    if (world.isAirBlock(x + dx + xo, y + dy + yo, z + dz + zo) && !world.isRemote) {
                                        Block block = world.getBlock(cx1 + dx, cy1 + dy, cz1 + dz);
                                        Item item = block.getItem(world, cx1 + dx, cy1 + dy, cz1 + dz);
                                        int worldDmg = world.getBlockMetadata(cx1 + dx, cy1 + dy, cz1 + dz);
                                        int dmg = block.getDamageValue(world, cx1 + dx, cy1 + dy, cz1 + dz);

                                        boolean usesMetadataForPlacing = false;
                                        ArrayList<ItemStack> drops = block.getDrops(world, cx1 + dx, cy1 + dy, cz1 + dz, dmg, 0);
                                        if (drops.size() == 1) {
                                            ItemStack dropStack = drops.get(0);
                                            usesMetadataForPlacing = dropStack.getItem() == item && dropStack.getItemDamage() == 0 && worldDmg != 0;
                                        }

                                        if (player.capabilities.isCreativeMode) {
                                            world.setBlock(x + dx + xo, y + dy + yo, z + dz + zo, block, worldDmg, 3);

                                        } else if (player.inventory.hasItemStack(new ItemStack(item, 1, dmg))) {
                                            int slot = slotOfItemStack(new ItemStack(item, 1, dmg), player.inventory);
                                            if (item instanceof ItemBlock) {
                                                if (!world.isRemote) {
                                                    ((ItemBlock) item).placeBlockAt(player.inventory.getStackInSlot(slot), player, world, x + dx + xo, y + dy + yo, z + dz + zo, 0, 0, 0, 0, dmg);
                                                    if (usesMetadataForPlacing) {
                                                        world.setBlockMetadataWithNotify(x + dx + xo, y + dy + yo, z + dz + zo, worldDmg, 3);
                                                    }
                                                }

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
                            player.addChatComponentMessage(new ChatComponentText("Successfully pasted building"));
                        }
                    } else {
                        if (!world.isRemote) {
                            player.addChatComponentMessage(new ChatComponentText("Nothing copied"));
                        }

                    }
                    break;


            }
        }
        return stack;
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
    public void registerIcons(IIconRegister register) {
        itemIcon = register.registerIcon("aura:prismaticWand");
    }

    @Override
    public int getCreativeTabPriority() {
        return -25;
    }
}
