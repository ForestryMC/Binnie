package binnie.core.craftgui.controls.tab;

import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.controls.core.IControlValue;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.events.EventValueChanged;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.minecraft.control.ControlItemDisplay;
import binnie.core.craftgui.minecraft.control.ControlTabIcon;
import binnie.core.craftgui.renderer.RenderUtil;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ControlTab<T> extends Control implements ITooltip, IControlValue<T> {
	protected T value;
	private ControlTabBar<T> tabBar;

	public ControlTab(final ControlTabBar<T> parent, final int x, final int y, final int w, final int h, final T value) {
		super(parent, x, y, w, h);
		this.value = value;
		this.tabBar = parent;
		this.addAttribute(Attribute.MouseOver);
		this.addSelfEventHandler(new EventMouse.Down.Handler() {
			@Override
			public void onEvent(final EventMouse.Down event) {
				ControlTab.this.callEvent(new EventValueChanged<Object>(ControlTab.this.getWidget(), ControlTab.this.getValue()));
			}
		});
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
		final String name = this.getName();
		if (name != null && !name.isEmpty()) {
			tooltip.add(this.getName());
		}
		if (this.value instanceof ITooltip) {
			((ITooltip) this.value).getTooltip(tooltip);
		}
	}

	@Override
	public T getValue() {
		return this.value;
	}

	@Override
	public void setValue(final T value) {
		this.value = value;
	}

	public boolean isCurrentSelection() {
		return this.getValue().equals(this.tabBar.getValue());
	}

	public Position getTabPosition() {
		return this.tabBar.getDirection();
	}

	public String getName() {
		return this.value.toString();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		Object texture = CraftGUITexture.TabDisabled;
		if (this.isMouseOver()) {
			texture = CraftGUITexture.TabHighlighted;
		} else if (this.isCurrentSelection()) {
			texture = CraftGUITexture.Tab;
		}
		final Texture lTexture = CraftGUI.render.getTexture(texture);
		final Position position = this.getTabPosition();
		Texture iTexture = lTexture.crop(position, 8);
		final Area area = this.getArea();
		if (texture == CraftGUITexture.TabDisabled) {
			if (position == Position.Top || position == Position.LEFT) {
				area.setPosition(area.getPosition().sub(new Point(4 * position.x(), 4 * position.y())));
				area.setSize(area.getSize().add(new Point(4 * position.x(), 4 * position.y())));
			} else {
				area.setSize(area.getSize().sub(new Point(4 * position.x(), 4 * position.y())));
			}
		}
		CraftGUI.render.texture(iTexture, area);
		if (this instanceof ControlTabIcon) {
			final ControlTabIcon icon = (ControlTabIcon) this;
			final ControlItemDisplay item = (ControlItemDisplay) this.getWidgets().get(0);
			if (texture == CraftGUITexture.TabDisabled) {
				item.setColour(-1431655766);
			} else {
				item.setColour(-1);
			}
			if (icon.hasOutline()) {
				iTexture = CraftGUI.render.getTexture(CraftGUITexture.TabOutline);
				iTexture = iTexture.crop(position, 8);
				RenderUtil.setColour(icon.getOutlineColour());
				CraftGUI.render.texture(iTexture, area.inset(2));
			}
		}
	}
}
