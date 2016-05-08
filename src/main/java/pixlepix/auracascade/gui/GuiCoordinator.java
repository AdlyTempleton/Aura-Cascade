package pixlepix.auracascade.gui;

import java.io.IOException;

import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.FMLClientHandler;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.block.tile.TileBookshelfCoordinator;
import pixlepix.auracascade.network.PacketCoordinatorScroll;

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

        GlStateManager.color(1, 1, 1, 1);
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

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        //Code handling scrolling
        //Comes from creative inventory gui
        //Not from GuiContainer
        boolean flag = Mouse.isButtonDown(0);
        int k = this.guiLeft;
        int l = this.guiTop;
        int i1 = k + 175;
        int j1 = l + 18;
        int k1 = i1 + 14;
        int l1 = j1 + 112;

        if (!this.wasClicking && flag && mouseX >= i1 && mouseY >= j1 && mouseX < k1 && mouseY < l1) {
            this.isScrolling = true;
        }

        if (!flag) {
            this.isScrolling = false;
        }

        this.wasClicking = flag;

        if (this.isScrolling) {
            this.currentScroll = ((float) (mouseY - j1) - 7.5F) / ((float) (l1 - j1) - 15.0F);

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
        int i = this.guiLeft;
        int j = this.guiTop;
        this.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        // super.drawScreen(mouseX, mouseY, partialTicks);
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)i, (float)j, 0.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableRescaleNormal();
        this.theSlot = null;
        int k3 = 240;
        int l3 = 240;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)k3 / 1.0F, (float)l3 / 1.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        for (int i2 = 0; i2 < this.inventorySlots.inventorySlots.size(); ++i2)
        {
            Slot slot = this.inventorySlots.inventorySlots.get(i2);
            this.drawSlot(slot);

          //  if (this.isMouseOverSlot(slot, mouseX, mouseY) && slot.canBeHovered())
            if(false)
            {
                this.theSlot = slot;
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                int j2 = slot.xDisplayPosition;
                int k2 = slot.yDisplayPosition;
                GlStateManager.colorMask(true, true, true, false);
                this.drawGradientRect(j2, k2, j2 + 16, k2 + 16, -2130706433, -2130706433);
                GlStateManager.colorMask(true, true, true, true);
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
            }
        }

        RenderHelper.disableStandardItemLighting();
        this.drawGuiContainerForegroundLayer(mouseX, mouseY);
        RenderHelper.enableGUIStandardItemLighting();
        InventoryPlayer inventoryplayer = this.mc.thePlayer.inventory;
        ItemStack itemstack = this.draggedStack == null ? inventoryplayer.getItemStack() : this.draggedStack;

        if (itemstack != null)
        {
            int j2 = 8;
            int k2 = this.draggedStack == null ? 8 : 16;
            String s = null;

            if (this.draggedStack != null && this.isRightMouseClick)
            {
                itemstack = itemstack.copy();
                itemstack.stackSize = MathHelper.ceiling_float_int((float)itemstack.stackSize / 2.0F);
            }
            else if (this.dragSplitting && this.dragSplittingSlots.size() > 1)
            {
                itemstack = itemstack.copy();
                itemstack.stackSize = this.dragSplittingRemnant;

                if (itemstack.stackSize == 0)
                {
                    s = "" + TextFormatting.YELLOW + "0";
                }
            }

            this.drawItemStack(itemstack, mouseX - i - j2, mouseY - j - k2, s);
        }

        if (this.returningStack != null)
        {
            float f = (float)(Minecraft.getSystemTime() - this.returningStackTime) / 100.0F;

            if (f >= 1.0F)
            {
                f = 1.0F;
                this.returningStack = null;
            }

            int l2 = this.returningStackDestSlot.xDisplayPosition - this.touchUpX;
            int i3 = this.returningStackDestSlot.yDisplayPosition - this.touchUpY;
            int l4 = this.touchUpX + (int)((float)l2 * f);
            int i2 = this.touchUpY + (int)((float)i3 * f);
            this.drawItemStack(this.returningStack, l4, i2, null);
        }

        GlStateManager.popMatrix();

        if (inventoryplayer.getItemStack() == null && this.theSlot != null && this.theSlot.getHasStack())
        {
            ItemStack itemstack1 = this.theSlot.getStack();
            this.renderToolTip(itemstack1, mouseX, mouseY);
        }

        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();
    }

    private void drawItemStack(ItemStack p_146982_1_, int p_146982_2_, int p_146982_3_, String p_146982_4_) {
        GlStateManager.translate(0.0F, 0.0F, 32.0F);
        this.zLevel = 200.0F;
        itemRender.zLevel = 200.0F;
        FontRenderer font = null;
        if (p_146982_1_ != null) font = p_146982_1_.getItem().getFontRenderer(p_146982_1_);
        if (font == null) font = fontRendererObj;
        itemRender.renderItemAndEffectIntoGUI(p_146982_1_, p_146982_2_, p_146982_3_);
        itemRender.renderItemOverlayIntoGUI(font, p_146982_1_, p_146982_2_, p_146982_3_ - (this.draggedStack == null ? 0 : 8), p_146982_4_);
        this.zLevel = 0.0F;
        itemRender.zLevel = 0.0F;
    }

    private void drawItemStackLarge(ItemStack stack, int x, int y) {


        GlStateManager.translate(0.0F, 0.0F, 32.0F);
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

        itemRender.renderItemAndEffectIntoGUI(stack, x, y);
        itemRender.renderItemOverlayIntoGUI(font, stack, x, y - (this.draggedStack == null ? 0 : 8), number);

        this.zLevel = 0.0F;
        itemRender.zLevel = 0.0F;

    }
    //TODO reimplement drawslot
   // @Override
    public void drawSlot(Slot slotIn) {
        int i = slotIn.xDisplayPosition;
        int j = slotIn.yDisplayPosition;
        ItemStack itemstack = slotIn.getStack();
        boolean flag = false;
        boolean flag1 = slotIn == this.clickedSlot && this.draggedStack != null && !this.isRightMouseClick;
        ItemStack itemstack1 = this.mc.thePlayer.inventory.getItemStack();
        String s = null;

        if (slotIn == this.clickedSlot && this.draggedStack != null && this.isRightMouseClick && itemstack != null) {
            itemstack = itemstack.copy();
            itemstack.stackSize /= 2;
        } else if (this.dragSplitting && this.dragSplittingSlots.contains(slotIn) && itemstack1 != null) {
            if (this.dragSplittingSlots.size() == 1) {
                return;
            }

            if (Container.canAddItemToSlot(slotIn, itemstack1, true) && this.inventorySlots.canDragIntoSlot(slotIn)) {
                itemstack = itemstack1.copy();
                flag = true;
                Container.computeStackSize(this.dragSplittingSlots, this.dragSplittingLimit, itemstack, slotIn.getStack() == null ? 0 : slotIn.getStack().stackSize);

                if (itemstack.stackSize > itemstack.getMaxStackSize()) {
                    s = TextFormatting.YELLOW + "" + itemstack.getMaxStackSize();
                    itemstack.stackSize = itemstack.getMaxStackSize();
                }

                if (itemstack.stackSize > slotIn.getSlotStackLimit()) {
                    s = TextFormatting.YELLOW + "" + slotIn.getSlotStackLimit();
                    itemstack.stackSize = slotIn.getSlotStackLimit();
                }
            } else {
                this.dragSplittingSlots.remove(slotIn);
                //TODO Reimplement this. requires ASM probably
               // this.updateActivePotionEffects();
            }
        }

        this.zLevel = 100.0F;
        itemRender.zLevel = 100.0F;

        if (itemstack == null) {
            TextureAtlasSprite iicon = slotIn.getBackgroundSprite();

            if (iicon != null) {
                GlStateManager.disableLighting();
                this.mc.getTextureManager().bindTexture(slotIn.getBackgroundLocation());
                this.drawTexturedModalRect(i, j, iicon, 16, 16);
                GlStateManager.enableLighting();
                flag1 = true;
            }
        }

        if (!flag1) {
            if (flag) {
                drawRect(i, j, i + 16, j + 16, -2130706433);
            }

            GlStateManager.enableDepth();
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
