// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.controls.tab;

import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.controls.core.IControlValue;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.events.EventValueChanged;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.minecraft.control.ControlItemDisplay;
import binnie.core.craftgui.minecraft.control.ControlTabIcon;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;

public class ControlTab<T> extends Control implements ITooltip, IControlValue<T>
{
	private ControlTabBar<T> tabBar;
	protected T value;

	public ControlTab(final ControlTabBar<T> parent, final float x, final float y, final float w, final float h, final T value) {
		super(parent, x, y, w, h);
		setValue(value);
		tabBar = parent;
		addAttribute(Attribute.MouseOver);
		addSelfEventHandler(new EventMouse.Down.Handler() {
			@Override
			public void onEvent(final EventMouse.Down event) {
				callEvent(new EventValueChanged<Object>(getWidget(), getValue()));
			}
		});
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
		final String name = getName();
		if (name != null && !name.isEmpty()) {
			tooltip.add(getName());
		}
		if (value instanceof ITooltip) {
			((ITooltip) value).getTooltip(tooltip);
		}
	}

	@Override
	public T getValue() {
		return value;
	}

	@Override
	public void setValue(final T value) {
		this.value = value;
	}

	public boolean isCurrentSelection() {
		return getValue() != null && getValue().equals(tabBar.getValue());
	}

	public Position getTabPosition() {
		return tabBar.position;
	}

	public String getName() {
		return value.toString();
	}

	@Override
	public void onRenderBackground() {
		Object texture = CraftGUITexture.TabDisabled;
		if (isMouseOver()) {
			texture = CraftGUITexture.TabHighlighted;
		}
		else if (isCurrentSelection()) {
			texture = CraftGUITexture.Tab;
		}
		final Texture lTexture = CraftGUI.Render.getTexture(texture);
		final Position position = getTabPosition();
		Texture iTexture = lTexture.crop(position, 8.0f);
		final IArea area = getArea();
		if (texture == CraftGUITexture.TabDisabled) {
			if (position == Position.Top || position == Position.Left) {
				area.setPosition(area.getPosition().sub(new IPoint(4 * position.x(), 4 * position.y())));
				area.setSize(area.getSize().add(new IPoint(4 * position.x(), 4 * position.y())));
			}
			else {
				area.setSize(area.getSize().sub(new IPoint(4 * position.x(), 4 * position.y())));
			}
		}
		CraftGUI.Render.texture(iTexture, area);
		if (this instanceof ControlTabIcon) {
			final ControlTabIcon icon = (ControlTabIcon) this;
			final ControlItemDisplay item = (ControlItemDisplay) getWidgets().get(0);
			if (texture == CraftGUITexture.TabDisabled) {
				item.setColour(-1431655766);
			}
			else {
				item.setColour(-1);
			}
			if (icon.hasOutline()) {
				iTexture = CraftGUI.Render.getTexture(CraftGUITexture.TabOutline);
				iTexture = iTexture.crop(position, 8.0f);
				CraftGUI.Render.colour(icon.getOutlineColour());
				CraftGUI.Render.texture(iTexture, area.inset(2));
			}
		}
	}
}
