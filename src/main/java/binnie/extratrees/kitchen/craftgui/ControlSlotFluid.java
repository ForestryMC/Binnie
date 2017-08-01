package binnie.extratrees.kitchen.craftgui;

import javax.annotation.Nullable;

import net.minecraftforge.fluids.FluidStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.ITooltip;
import binnie.core.gui.IWidget;
import binnie.core.gui.Tooltip;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.events.EventWidget;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.renderer.RenderUtil;
import binnie.core.gui.resource.minecraft.CraftGUITexture;

public class ControlSlotFluid extends Control implements ITooltip {
	ControlFluidDisplay itemDisplay;
	@Nullable
	FluidStack fluidStack;

	public ControlSlotFluid(final IWidget parent, final int x, final int y, @Nullable final FluidStack fluid) {
		this(parent, x, y, 18, fluid);
	}

	public ControlSlotFluid(final IWidget parent, final int x, final int y, final int size, @Nullable final FluidStack fluid) {
		super(parent, x, y, size, size);
		this.addAttribute(Attribute.MOUSE_OVER);
		this.itemDisplay = new ControlFluidDisplay(this, 1, 1, size - 2);
		this.fluidStack = fluid;
		this.addSelfEventHandler(new EventWidget.ChangeSize.Handler() {
			@Override
			public void onEvent(final EventWidget.ChangeSize event) {
				ControlSlotFluid.this.itemDisplay.setSize(ControlSlotFluid.this.getSize().sub(new Point(2, 2)));
			}
		});
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		final int size = this.getSize().xPos();
		CraftGUI.RENDER.texture(CraftGUITexture.Slot, this.getArea());
		if (this.getTopParent().getMousedOverWidget() == this) {
			RenderUtil.drawGradientRect(new Area(new Point(1, 1), this.getArea().size().sub(new Point(2, 2))), -2130706433, -2130706433);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onUpdateClient() {
		super.onUpdateClient();
		this.itemDisplay.setFluidStack(this.getFluidStack());
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
		final FluidStack item = this.getFluidStack();
		if (item == null) {
			return;
		}
		tooltip.add(item.getFluid().getLocalizedName(item));
	}

	@Nullable
	public FluidStack getFluidStack() {
		return this.fluidStack;
	}
}
