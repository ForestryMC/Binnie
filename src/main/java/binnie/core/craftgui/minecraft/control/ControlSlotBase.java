// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.minecraft.control;

import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.events.EventWidget;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import net.minecraft.item.ItemStack;

public abstract class ControlSlotBase extends Control implements ITooltip
{
	private ControlItemDisplay itemDisplay;

	public ControlSlotBase(final IWidget parent, final float x, final float y) {
		this(parent, x, y, 18);
	}

	public ControlSlotBase(final IWidget parent, final float x, final float y, final int size) {
		super(parent, x, y, size, size);
		addAttribute(Attribute.MouseOver);
		itemDisplay = new ControlItemDisplay(this, 1.0f, 1.0f, size - 2);
		addSelfEventHandler(new EventWidget.ChangeSize.Handler() {
			@Override
			public void onEvent(final EventWidget.ChangeSize event) {
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
		final int size = (int) getSize().x();
		CraftGUI.Render.texture(CraftGUITexture.Slot, getArea());
		if (getSuperParent().getMousedOverWidget() == this) {
			CraftGUI.Render.gradientRect(new IArea(new IPoint(1.0f, 1.0f), getArea().size().sub(new IPoint(2.0f, 2.0f))), -2130706433, -2130706433);
		}
	}

	@Override
	public void onUpdateClient() {
		super.onUpdateClient();
		itemDisplay.setItemStack(getItemStack());
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
		final ItemStack item = getItemStack();
		if (item == null) {
			return;
		}
		tooltip.add(item.getTooltip(((Window) getSuperParent()).getPlayer(), false));
	}

	public abstract ItemStack getItemStack();
}
