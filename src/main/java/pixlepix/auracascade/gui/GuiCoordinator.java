package pixlepix.auracascade.gui;

import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.block.tile.TileBookshelfCoordinator;
import pixlepix.auracascade.network.PacketCoordinatorScroll;

import java.io.IOException;

/**
 * Created by localmacaccount on 1/24/15.
 */
public class GuiCoordinator extends GuiContainer {

    public static InventoryBasic invBasic = new InventoryBasic("tmp", true, 66);
    public TileBookshelfCoordinator te;
    public ContainerCoordinator containerCoordinator;
    public GuiTextField searchField;
    /**
     * Amount scrolled in Creative mode inventory (0 = top, 1 = bottom)
     */
    private float currentScroll;
    /**
     * True if the scrollbar is being dragged
     */
    private boolean isScrolling;
    /**
     * True if the left mouse button was held down last time drawScreen was called.
     */
    private boolean wasClicking;
    private boolean unfocused = false;

    public GuiCoordinator(InventoryPlayer inventoryPlayer, TileBookshelfCoordinator tileEntity) {
        super(new ContainerCoordinator(inventoryPlayer, tileEntity));
        this.containerCoordinator = (ContainerCoordinator) inventorySlots;
        this.te = tileEntity;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        ResourceLocation tex = new ResourceLocation("aura", "textures/gui/coordinator.png");
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(tex);
    }

    @Override
    public void initGui() {
        super.initGui();
        this.searchField = new GuiTextField(0, this.fontRendererObj, this.guiLeft + 82, this.guiTop + 6, 89, this.fontRendererObj.FONT_HEIGHT);
        this.searchField.setMaxStringLength(15);
        this.searchField.setEnableBackgroundDrawing(true);
        this.searchField.setVisible(true);
        this.searchField.setFocused(true);
        this.searchField.setCanLoseFocus(false);
        this.searchField.setTextColor(16777215);
    }

    @Override
    protected void keyTyped(char p_73869_1_, int p_73869_2_) throws IOException {
        if (this.unfocused) {
            this.unfocused = false;
            this.searchField.setText("");
        }

        if (!this.checkHotbarKeys(p_73869_2_)) {
            if (this.searchField.textboxKeyTyped(p_73869_1_, p_73869_2_)) {
                this.updateSearch();
            } else {
                super.keyTyped(p_73869_1_, p_73869_2_);
            }
        }
    }

    private void updateSearch() {
        ContainerCoordinator container = (ContainerCoordinator) this.inventorySlots;
        String filter = searchField.getText().toUpperCase();
        container.scrollTo(container.lastScroll, filter);
        AuraCascade.proxy.networkWrapper.sendToServer(new PacketCoordinatorScroll(Minecraft.getMinecraft().thePlayer, filter, containerCoordinator.lastScroll));
    }

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int i = Mouse.getEventDWheel();

        if (i != 0) {
            int j = te.getSizeInventory() / 7 - 5 + 1;

            if (i > 0) {
                i = 1;
            }

            if (i < 0) {
                i = -1;
            }

            this.currentScroll = (float) ((double) this.currentScroll - (double) i / (double) j);

            if (this.currentScroll < 0.0F) {
                this.currentScroll = 0.0F;
            }

            if (this.currentScroll > 1.0F) {
                this.currentScroll = 1.0F;
            }

            String filter = searchField.getText().toUpperCase();
            ((ContainerCoordinator) this.inventorySlots).scrollTo(this.currentScroll);
            AuraCascade.proxy.networkWrapper.sendToServer(new PacketCoordinatorScroll(Minecraft.getMinecraft().thePlayer, filter, ((ContainerCoordinator) this.inventorySlots).lastScroll));

        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        ResourceLocation tex = new ResourceLocation("aura", "textures/gui/coordinator.png");
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(tex);

        GL11.glColor4f(1, 1, 1, 1);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;

        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);


        int i1 = this.guiLeft + 161;
        int k = this.guiTop + 18;
        int l = k + 52;

        this.searchField.drawTextBox();

        this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/creative_inventory/tabs.png"));

        this.drawTexturedModalRect(i1, k + (int) ((float) (l - k - 17) * this.currentScroll), 232, 0, 12, 15);
    }

    public void drawScreen(int x, int y, float p_73863_3_) {

        //Code handling scrolling
        //Comes from creative inventory gui
        //Not from GuiContainer
        boolean flag = Mouse.isButtonDown(0);
        int k = this.guiLeft;
        int l = this.guiTop;
        int i1 = k + 161;
        int j1 = l + 18;
        int k1 = i1 + 14;
        int l1 = j1 + 52;

        if (!this.wasClicking && flag && x >= i1 && y >= j1 && x < k1 && y < l1) {
            this.isScrolling = true;
        }

        if (!flag) {
            this.isScrolling = false;
        }

        this.wasClicking = flag;

        if (this.isScrolling) {
            this.currentScroll = ((float) (y - j1) - 7.5F) / ((float) (l1 - j1) - 15.0F);

            if (this.currentScroll < 0.0F) {
                this.currentScroll = 0.0F;
            }

            if (this.currentScroll > 1.0F) {
                this.currentScroll = 1.0F;
            }

            String filter = searchField.getText().toUpperCase();
            ((ContainerCoordinator) this.inventorySlots).scrollTo(this.currentScroll);
            AuraCascade.proxy.networkWrapper.sendToServer(new PacketCoordinatorScroll(Minecraft.getMinecraft().thePlayer, filter, containerCoordinator.lastScroll));

        }
        //Override super drawScreen to make use of custom item rendering
        this.drawDefaultBackground();
        k = this.guiLeft;
        l = this.guiTop;
        this.drawGuiContainerBackgroundLayer(p_73863_3_, x, y);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glPushMatrix();
        GL11.glTranslatef((float) k, (float) l, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        this.theSlot = null;
        short short1 = 240;
        short short2 = 240;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) short1 / 1.0F, (float) short2 / 1.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        for (i1 = 0; i1 < this.inventorySlots.inventorySlots.size(); ++i1) {
            Slot slot = (Slot) this.inventorySlots.inventorySlots.get(i1);
            if (slot instanceof SlotCoordinator) {
                this.drawSlot(slot);
            } else {
                this.drawSlot(slot);
            }

            if (this.isMouseOverSlot(slot, x, y) && slot.canBeHovered()) {
                this.theSlot = slot;
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                j1 = slot.xDisplayPosition;
                k1 = slot.yDisplayPosition;
                GL11.glColorMask(true, true, true, false);
                this.drawGradientRect(j1, k1, j1 + 16, k1 + 16, -2130706433, -2130706433);
                GL11.glColorMask(true, true, true, true);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
            }
        }

        //Forge: Force lighting to be disabled as there are some issue where lighting would
        //incorrectly be applied based on items that are in the inventory.
        GL11.glDisable(GL11.GL_LIGHTING);
        this.drawGuiContainerForegroundLayer(x, y);
        GL11.glEnable(GL11.GL_LIGHTING);
        InventoryPlayer inventoryplayer = this.mc.thePlayer.inventory;
        ItemStack itemstack = this.draggedStack == null ? inventoryplayer.getItemStack() : this.draggedStack;

        if (itemstack != null) {
            byte b0 = 8;
            k1 = this.draggedStack == null ? 8 : 16;
            String s = null;

            if (this.draggedStack != null && this.isRightMouseClick) {
                itemstack = itemstack.copy();
                itemstack.stackSize = MathHelper.ceiling_float_int((float) itemstack.stackSize / 2.0F);
            } else if (this.dragSplitting && this.dragSplittingSlots.size() > 1) {
                itemstack = itemstack.copy();
                itemstack.stackSize = this.dragSplittingRemnant;

                if (itemstack.stackSize == 0) {
                    s = "" + EnumChatFormatting.YELLOW + "0";
                }
            }

            this.drawItemStack(itemstack, x - k - b0, y - l - k1, s);
        }

        if (this.returningStack != null) {
            float f1 = (float) (Minecraft.getSystemTime() - this.returningStackTime) / 100.0F;

            if (f1 >= 1.0F) {
                f1 = 1.0F;
                this.returningStack = null;
            }

            k1 = this.returningStackDestSlot.xDisplayPosition - this.field_147011_y;
            int j2 = this.returningStackDestSlot.yDisplayPosition - this.field_147010_z;
            l1 = this.field_147011_y + (int) ((float) k1 * f1);
            int i2 = this.field_147010_z + (int) ((float) j2 * f1);
            this.drawItemStack(this.returningStack, l1, i2, null);
        }

        GL11.glPopMatrix();

        if (inventoryplayer.getItemStack() == null && this.theSlot != null && this.theSlot.getHasStack()) {
            ItemStack itemstack1 = this.theSlot.getStack();
            this.renderToolTip(itemstack1, x, y);
        }

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        RenderHelper.enableStandardItemLighting();
    }

    private void drawItemStack(ItemStack p_146982_1_, int p_146982_2_, int p_146982_3_, String p_146982_4_) {
        GL11.glTranslatef(0.0F, 0.0F, 32.0F);
        this.zLevel = 200.0F;
        itemRender.zLevel = 200.0F;
        FontRenderer font = null;
        if (p_146982_1_ != null) font = p_146982_1_.getItem().getFontRenderer(p_146982_1_);
        if (font == null) font = fontRendererObj;
        itemRender.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), p_146982_1_, p_146982_2_, p_146982_3_);
        itemRender.renderItemOverlayIntoGUI(font, this.mc.getTextureManager(), p_146982_1_, p_146982_2_, p_146982_3_ - (this.draggedStack == null ? 0 : 8), p_146982_4_);
        this.zLevel = 0.0F;
        itemRender.zLevel = 0.0F;
    }

    private void drawItemStackLarge(ItemStack stack, int x, int y) {


        GL11.glTranslatef(0.0F, 0.0F, 32.0F);
        this.zLevel = 200.0F;
        itemRender.zLevel = 200.0F;
        FontRenderer font = null;
        if (stack != null) {
            font = stack.getItem().getFontRenderer(stack);
        }
        if (font == null) font = fontRendererObj;

        String number = "";
        if (stack != null) {

            number = "" + stack.stackSize;
            if (stack.stackSize > 1000) {
                number = ((stack.stackSize / 1000)) + "k";
            }

            if (stack.stackSize > 1000000) {
                number = ((stack.stackSize / 1000000)) + "M";
            }
            if (stack.stackSize > 1000000000) {
                number = ((stack.stackSize / 1000000000)) + "B";
            }
            if (stack.stackSize == 0) {
                number = null;

            }
        }

        itemRender.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), stack, x, y);
        itemRender.renderItemOverlayIntoGUI(font, this.mc.getTextureManager(), stack, x, y - (this.draggedStack == null ? 0 : 8), number);

        this.zLevel = 0.0F;
        itemRender.zLevel = 0.0F;

    }

    public void drawSlot(Slot p_146977_1_) {
        int i = p_146977_1_.xDisplayPosition;
        int j = p_146977_1_.yDisplayPosition;
        ItemStack itemstack = p_146977_1_.getStack();
        boolean flag = false;
        boolean flag1 = p_146977_1_ == this.clickedSlot && this.draggedStack != null && !this.isRightMouseClick;
        ItemStack itemstack1 = this.mc.thePlayer.inventory.getItemStack();
        String s = null;

        if (p_146977_1_ == this.clickedSlot && this.draggedStack != null && this.isRightMouseClick && itemstack != null) {
            itemstack = itemstack.copy();
            itemstack.stackSize /= 2;
        } else if (this.field_147007_t && this.field_147008_s.contains(p_146977_1_) && itemstack1 != null) {
            if (this.field_147008_s.size() == 1) {
                return;
            }

            if (Container.func_94527_a(p_146977_1_, itemstack1, true) && this.inventorySlots.canDragIntoSlot(p_146977_1_)) {
                itemstack = itemstack1.copy();
                flag = true;
                Container.func_94525_a(this.field_147008_s, this.field_146987_F, itemstack, p_146977_1_.getStack() == null ? 0 : p_146977_1_.getStack().stackSize);

                if (itemstack.stackSize > itemstack.getMaxStackSize()) {
                    s = EnumChatFormatting.YELLOW + "" + itemstack.getMaxStackSize();
                    itemstack.stackSize = itemstack.getMaxStackSize();
                }

                if (itemstack.stackSize > p_146977_1_.getSlotStackLimit()) {
                    s = EnumChatFormatting.YELLOW + "" + p_146977_1_.getSlotStackLimit();
                    itemstack.stackSize = p_146977_1_.getSlotStackLimit();
                }
            } else {
                this.field_147008_s.remove(p_146977_1_);
                this.func_146980_g();
            }
        }

        this.zLevel = 100.0F;
        itemRender.zLevel = 100.0F;

        if (itemstack == null) {
            IIcon iicon = p_146977_1_.getBackgroundIconIndex();

            if (iicon != null) {
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_BLEND); // Forge: Blending needs to be enabled for this.
                this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
                this.drawTexturedModelRectFromIcon(i, j, iicon, 16, 16);
                GL11.glDisable(GL11.GL_BLEND); // Forge: And clean that up
                GL11.glEnable(GL11.GL_LIGHTING);
                flag1 = true;
            }
        }

        if (!flag1) {
            if (flag) {
                drawRect(i, j, i + 16, j + 16, -2130706433);
            }

            GL11.glEnable(GL11.GL_DEPTH_TEST);
            if (itemstack != null && itemstack.stackSize > 999) {
                drawItemStackLarge(itemstack, i, j);
            } else {
                drawItemStack(itemstack, i, j, s);

            }
        }

        itemRender.zLevel = 0.0F;
        this.zLevel = 0.0F;
    }
}
