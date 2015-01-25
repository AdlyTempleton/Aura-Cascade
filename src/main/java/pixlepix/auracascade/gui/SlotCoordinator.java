package pixlepix.auracascade.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

/**
 * Created by localmacaccount on 1/24/15.
 */
public class SlotCoordinator extends Slot {

    public SlotCoordinator(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
        super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
        this.backgroundIcon = new IIcon() {
            @Override
            public int getIconWidth() {
                return 16;
            }

            @Override
            public int getIconHeight() {
                return 16;
            }

            @Override
            public float getMinU() {
                return 176;
            }

            @Override
            public float getMaxU() {
                return 176 + 16;
            }

            @Override
            public float getInterpolatedU(double d) {
                return (float) (176F + d * 16F);
            }

            @Override
            public float getMinV() {
                return 166;
            }

            @Override
            public float getMaxV() {
                return 166 + 16;
            }

            @Override
            public float getInterpolatedV(double d) {

                return (float) (166F + d * 16F);
            }

            @Override
            public String getIconName() {
                return "slot";
            }
        };
        this.setBackgroundIconTexture(new ResourceLocation("aura", "/textures/gui/coordinator.png"));
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return inventory.isItemValidForSlot(getSlotIndex(), stack);
    }

}
