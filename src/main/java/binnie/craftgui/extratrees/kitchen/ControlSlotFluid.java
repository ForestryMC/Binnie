package binnie.craftgui.extratrees.kitchen;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.ITooltip;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.Tooltip;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.events.EventWidget;
import binnie.craftgui.resource.minecraft.CraftGUITexture;
import net.minecraftforge.fluids.FluidStack;

public class ControlSlotFluid extends Control implements ITooltip {
	ControlFluidDisplay itemDisplay;
	FluidStack fluidStack;

	public ControlSlotFluid(final IWidget parent, final int x, final int y, final FluidStack fluid) {
		this(parent, x, y, 18, fluid);
	}

	public ControlSlotFluid(final IWidget parent, final int x, final int y, final int size, final FluidStack fluid) {
		super(parent, x, y, size, size);
		this.addAttribute(Attribute.MouseOver);
		this.itemDisplay = new ControlFluidDisplay(this, 1.0f, 1.0f, size - 2);
		this.fluidStack = fluid;
		this.addSelfEventHandler(new EventWidget.ChangeSize.Handler() {
			@Override
			public void onEvent(final EventWidget.ChangeSize event) {
				if (ControlSlotFluid.this.itemDisplay != null) {
					ControlSlotFluid.this.itemDisplay.setSize(ControlSlotFluid.this.getSize().sub(new IPoint(2.0f, 2.0f)));
				}
			}
		});
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
		this.itemDisplay.setItemStack(this.getFluidStack());
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
		final FluidStack item = this.getFluidStack();
		if (item == null) {
			return;
		}
		tooltip.add(item.getFluid().getLocalizedName(item));
	}

	public FluidStack getFluidStack() {
		return this.fluidStack;
	}
}
