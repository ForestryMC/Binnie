package binnie.core.craftgui.minecraft.control;

import binnie.core.craftgui.controls.tab.ControlTab;
import binnie.core.craftgui.controls.tab.ControlTabBar;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.genetics.IItemStackRepresentative;
import net.minecraft.item.ItemStack;

public class ControlTabIcon<T> extends ControlTab<T> {
    private ControlItemDisplay item;

    public ControlTabIcon(ControlTabBar<T> parent, float x, float y, float w, float h, T value) {
        super(parent, x, y, w, h, value);
        item = new ControlItemDisplay(this, -8.0f + w / 2.0f, -8.0f + h / 2.0f);
        item.hastooltip = false;
    }

    public ItemStack getItemStack() {
        if (value instanceof IItemStackRepresentative) {
            return ((IItemStackRepresentative) value).getItemStackRepresentative();
        }
        return null;
    }

    @Override
    public void onUpdateClient() {
        super.onUpdateClient();
        item.setItemStack(getItemStack());
        float x = ((ControlTabBar) getParent()).getDirection().x();
        item.setOffset(new IPoint((isCurrentSelection() || isMouseOver()) ? 0.0f : (-4.0f * x), 0.0f));
    }

    public boolean hasOutline() {
        return false;
    }

    public int getOutlineColor() {
        return 0xffffff;
    }
}
