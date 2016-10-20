package binnie.craftgui.minecraft.control;

import binnie.core.genetics.IItemStackRepresentitive;
import binnie.craftgui.controls.tab.ControlTab;
import binnie.craftgui.controls.tab.ControlTabBar;
import binnie.craftgui.core.geometry.IPoint;
import net.minecraft.item.ItemStack;

public class ControlTabIcon<T> extends ControlTab<T> {
    private ControlItemDisplay item;

    public ControlTabIcon(final ControlTabBar<T> parent, final float x, final float y, final float w, final float h, final T value) {
        super(parent, x, y, w, h, value);
        this.item = new ControlItemDisplay(this, -8.0f + w / 2.0f, -8.0f + h / 2.0f);
        this.item.hastooltip = false;
    }

    public ItemStack getItemStack() {
        if (this.value instanceof IItemStackRepresentitive) {
            return ((IItemStackRepresentitive) this.value).getItemStackRepresentitive();
        }
        return null;
    }

    @Override
    public void onUpdateClient() {
        super.onUpdateClient();
        this.item.setItemStack(this.getItemStack());
        final float x = ((ControlTabBar) this.getParent()).getDirection().x();
        this.item.setOffset(new IPoint((this.isCurrentSelection() || this.isMouseOver()) ? 0.0f : (-4.0f * x), 0.0f));
    }

    public boolean hasOutline() {
        return false;
    }

    public int getOutlineColour() {
        return 16777215;
    }
}
