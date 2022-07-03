package binnie.extratrees.craftgui;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.events.EventWidget;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import net.minecraftforge.fluids.FluidStack;

public class ControlSlotFluid extends Control implements ITooltip {
    protected ControlFluidDisplay itemDisplay;
    protected FluidStack fluidStack;

    public ControlSlotFluid(IWidget parent, int x, int y, FluidStack fluid) {
        this(parent, x, y, 18, fluid);
    }

    public ControlSlotFluid(IWidget parent, int x, int y, int size, FluidStack fluid) {
        super(parent, x, y, size, size);
        addAttribute(WidgetAttribute.MOUSE_OVER);
        itemDisplay = new ControlFluidDisplay(this, 1.0f, 1.0f, size - 2);
        fluidStack = fluid;
        addSelfEventHandler(new EventWidget.ChangeSize.Handler() {
            @Override
            public void onEvent(EventWidget.ChangeSize event) {
                if (itemDisplay != null) {
                    itemDisplay.setSize(getSize().sub(new IPoint(2.0f, 2.0f)));
                }
            }
        });
    }

    @Override
    public void onRenderBackground() {
        int size = (int) getSize().x();
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
        itemDisplay.setItemStack(getFluidStack());
    }

    @Override
    public void getTooltip(Tooltip tooltip) {
        FluidStack item = getFluidStack();
        if (item == null) {
            return;
        }
        tooltip.add(item.getFluid().getLocalizedName());
    }

    public FluidStack getFluidStack() {
        return fluidStack;
    }
}
