package binnie.craftgui.minecraft.control;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.*;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.events.EventWidget;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.resource.minecraft.CraftGUITexture;
import net.minecraft.item.ItemStack;

public abstract class ControlSlotBase extends Control implements ITooltip {
    private ControlItemDisplay itemDisplay;

    public ControlSlotBase(final IWidget parent, final float x, final float y) {
        this(parent, x, y, 18);
    }

    public ControlSlotBase(final IWidget parent, final float x, final float y, final int size) {
        super(parent, x, y, size, size);
        this.addAttribute(Attribute.MouseOver);
        this.itemDisplay = new ControlItemDisplay(this, 1.0f, 1.0f, size - 2);
        this.addSelfEventHandler(new EventWidget.ChangeSize.Handler() {
            @Override
            public void onEvent(final EventWidget.ChangeSize event) {
                if (ControlSlotBase.this.itemDisplay != null) {
                    ControlSlotBase.this.itemDisplay.setSize(ControlSlotBase.this.getSize().sub(new IPoint(2.0f, 2.0f)));
                }
            }
        });
    }

    protected void setRotating() {
        this.itemDisplay.setRotating();
    }

    @Override
    public void onRenderBackground() {
        final int size = (int) this.getSize().x();
        CraftGUI.Render.texture(CraftGUITexture.Slot, this.getArea());
        if (this.getSuperParent().getMousedOverWidget() == this) {
            CraftGUI.Render.gradientRect(new IArea(new IPoint(1.0f, 1.0f), this.getArea().size().sub(new IPoint(2.0f, 2.0f))), -2130706433, -2130706433);
        }
    }

    @Override
    public void onUpdateClient() {
        super.onUpdateClient();
        this.itemDisplay.setItemStack(this.getItemStack());
    }

    @Override
    public void getTooltip(final Tooltip tooltip) {
        final ItemStack item = this.getItemStack();
        if (item == null) {
            return;
        }
        tooltip.add(item.getTooltip(((Window) this.getSuperParent()).getPlayer(), false));
    }

    public abstract ItemStack getItemStack();
}
