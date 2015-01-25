package pixlepix.auracascade.gui;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import pixlepix.auracascade.block.tile.TileBookshelfCoordinator;
import pixlepix.auracascade.block.tile.TileStorageBookshelf;

import java.awt.event.KeyEvent;
import java.util.List;

/**
 * Created by localmacaccount on 1/24/15.
 */
public class GuiCoordinator extends GuiContainer {

    public static InventoryBasic invBasic = new InventoryBasic("tmp", true, 66);
    public TileBookshelfCoordinator te;
    public ContainerCoordinator containerCoordinator;
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

    public GuiCoordinator(InventoryPlayer inventoryPlayer, TileBookshelfCoordinator tileEntity) {
        super(new ContainerCoordinator(inventoryPlayer, tileEntity));
        this.te = tileEntity;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        ResourceLocation tex = new ResourceLocation("aura", "textures/gui/coordinator.png");
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(tex);
    }

    @Override
    public void drawScreen(int x, int y, float p_73863_3_) {
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

            ((ContainerCoordinator) this.inventorySlots).scrollTo(this.currentScroll);
        }
        super.drawScreen(x, y, p_73863_3_);
    }


    public void handleMouseInput() {
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

            ((ContainerCoordinator) this.inventorySlots).scrollTo(this.currentScroll);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        ResourceLocation tex = new ResourceLocation("aura", "textures/gui/coordinator.png");
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(tex);

        GL11.glColor4f(1, 1, 1, 1);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;

        //Render slots
        for (int i = 0; i < te.getSizeInventory(); i++) {
            int xi = x + i % 7;
            int yi = y + i / 7;
            this.drawTexturedModalRect(16 + xi * 18, 17 + yi * 18, 0, ySize, xSize, ySize);
        }

        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);


        int i1 = this.guiLeft + 161;
        int k = this.guiTop + 18;
        int l = k + 52;

        this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/creative_inventory/tabs.png"));

        this.drawTexturedModalRect(i1, k + (int) ((float) (l - k - 17) * this.currentScroll), 232, 0, 12, 15);


    }
}
