package binnie.core.gui.controls.tab;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.ITooltip;
import binnie.core.gui.Tooltip;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.controls.core.IControlValue;
import binnie.core.gui.events.EventMouse;
import binnie.core.gui.events.EventValueChanged;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.geometry.Position;
import binnie.core.gui.minecraft.control.ControlItemDisplay;
import binnie.core.gui.minecraft.control.ControlTabIcon;
import binnie.core.gui.renderer.RenderUtil;
import binnie.core.gui.resource.Texture;
import binnie.core.gui.resource.minecraft.CraftGUITexture;

public class ControlTab<T> extends Control implements ITooltip, IControlValue<T> {
	protected T value;
	private ControlTabBar<T> tabBar;

	public ControlTab(final ControlTabBar<T> parent, final int x, final int y, final int w, final int h, final T value) {
		super(parent, x, y, w, h);
		this.value = value;
		this.tabBar = parent;
		this.addAttribute(Attribute.MOUSE_OVER);
		this.addSelfEventHandler(new EventMouse.Down.Handler() {
			@Override
			public void onEvent(final EventMouse.Down event) {
				ControlTab.this.callEvent(new EventValueChanged<Object>(ControlTab.this.getWidget(), ControlTab.this.getValue()));
			}
		});
	}

	@Override
	public void getTooltip(final Tooltip tooltip, ITooltipFlag tooltipFlag) {
		final String name = this.getName();
		if (name != null && !name.isEmpty()) {
			tooltip.add(this.getName());
		}
		if (this.value instanceof ITooltip) {
			((ITooltip) this.value).getTooltip(tooltip, tooltipFlag);
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
		Object texture = CraftGUITexture.TAB_DISABLED;
		if (this.isMouseOver()) {
			texture = CraftGUITexture.TAB_HIGHLIGHTED;
		} else if (this.isCurrentSelection()) {
			texture = CraftGUITexture.TAB;
		}
		final Texture lTexture = CraftGUI.RENDER.getTexture(texture);
		final Position position = this.getTabPosition();
		Texture iTexture = lTexture.crop(position, 8);
		final Area area = this.getArea();
		if (texture == CraftGUITexture.TAB_DISABLED) {
			if (position == Position.Top || position == Position.LEFT) {
				area.setPosition(area.getPosition().sub(new Point(4 * position.x(), 4 * position.y())));
				area.setSize(area.getSize().add(new Point(4 * position.x(), 4 * position.y())));
			} else {
				area.setSize(area.getSize().sub(new Point(4 * position.x(), 4 * position.y())));
			}
		}
		CraftGUI.RENDER.texture(iTexture, area);
		if (this instanceof ControlTabIcon) {
			final ControlTabIcon icon = (ControlTabIcon) this;
			final ControlItemDisplay item = (ControlItemDisplay) getFirstChild();
			if (texture == CraftGUITexture.TAB_DISABLED) {
				item.setColor(-1431655766);
			} else {
				item.setColor(-1);
			}
			if (icon.hasOutline()) {
				iTexture = CraftGUI.RENDER.getTexture(CraftGUITexture.TAB_OUTLINE);
				iTexture = iTexture.crop(position, 8);
				RenderUtil.setColour(icon.getOutlineColour());
				CraftGUI.RENDER.texture(iTexture, area.inset(2));
			}
		}
	}
}
