package binnie.core.craftgui.minecraft.control;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.events.EventWidget;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import net.minecraft.item.ItemStack;

public abstract class ControlSlotBase extends Control implements ITooltip {
    private ControlItemDisplay itemDisplay;

    public ControlSlotBase(IWidget parent, float x, float y) {
        this(parent, x, y, 18);
    }

    public ControlSlotBase(IWidget parent, float x, float y, int size) {
        super(parent, x, y, size, size);
        addAttribute(WidgetAttribute.MOUSE_OVER);
        itemDisplay = new ControlItemDisplay(this, 1.0f, 1.0f, size - 2);
        addSelfEventHandler(new EventWidget.ChangeSize.Handler() {
            @Override
            public void onEvent(EventWidget.ChangeSize event) {
                if (itemDisplay != null) {
                    itemDisplay.setSize(getSize().sub(new IPoint(2.0f, 2.0f)));
                }
            }
        });
    }

    protected void setRotating() {
        itemDisplay.setRotating();
    }

    @Override
    public void onRenderBackground() {
        CraftGUI.render.texture(CraftGUITexture.Slot, getArea());
        if (getSuperParent().getMousedOverWidget() == this) {
            CraftGUI.render.gradientRect(
                    new IArea(new IPoint(1.0f, 1.0f), getArea().size().sub(new IPoint(2.0f, 2.0f))),
                    0x80ffffff,
                    0x80ffffff);
        }
    }

    @Override
    public void onUpdateClient() {
        super.onUpdateClient();
        itemDisplay.setItemStack(getItemStack());
    }

    @Override
    public void getTooltip(Tooltip tooltip) {
        ItemStack item = getItemStack();
        if (item == null) {
            return;
        }
        tooltip.add(item.getTooltip(((Window) getSuperParent()).getPlayer(), false));
    }

    public abstract ItemStack getItemStack();
}
