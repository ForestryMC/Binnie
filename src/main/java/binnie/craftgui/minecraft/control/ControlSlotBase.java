package binnie.craftgui.minecraft.control;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.ITooltip;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.Tooltip;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.renderer.RenderUtil;
import binnie.craftgui.events.EventWidget;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.resource.minecraft.CraftGUITexture;
import net.minecraft.item.ItemStack;

public abstract class ControlSlotBase extends Control implements ITooltip {
	private ControlItemDisplay itemDisplay;

	public ControlSlotBase(final IWidget parent, final int x, final int y) {
		this(parent, x, y, 18);
	}

	public ControlSlotBase(final IWidget parent, final int x, final int y, final int size) {
		super(parent, x, y, size, size);
		this.addAttribute(Attribute.MouseOver);
		this.itemDisplay = new ControlItemDisplay(this, 1, 1, size - 2);
		this.addSelfEventHandler(new EventWidget.ChangeSize.Handler() {
			@Override
			public void onEvent(final EventWidget.ChangeSize event) {
				if (ControlSlotBase.this.itemDisplay != null) {
					ControlSlotBase.this.itemDisplay.setSize(ControlSlotBase.this.getSize().sub(new IPoint(2, 2)));
				}
			}
		});
	}

	protected void setRotating() {
		this.itemDisplay.setRotating();
	}

	@Override
	public void onRenderBackground(int guiWidth, int guiHeight) {
		final int size = (int) this.getSize().x();
		CraftGUI.render.texture(CraftGUITexture.Slot, this.getArea());
		if (this.getSuperParent().getMousedOverWidget() == this) {
			RenderUtil.drawGradientRect(new IArea(new IPoint(1, 1), this.getArea().size().sub(new IPoint(2, 2))), -2130706433, -2130706433);
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
