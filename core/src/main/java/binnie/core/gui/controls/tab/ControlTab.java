package binnie.core.gui.controls.tab;

import net.minecraft.client.util.ITooltipFlag;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.api.gui.Alignment;
import binnie.core.api.gui.IArea;
import binnie.core.api.gui.ITexture;
import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.ITooltip;
import binnie.core.gui.Tooltip;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.controls.core.IControlValue;
import binnie.core.gui.events.EventMouse;
import binnie.core.gui.events.EventValueChanged;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.minecraft.control.ControlItemDisplay;
import binnie.core.gui.minecraft.control.ControlTabIcon;
import binnie.core.gui.renderer.RenderUtil;
import binnie.core.gui.resource.textures.CraftGUITexture;

public class ControlTab<T> extends Control implements ITooltip, IControlValue<T> {
	protected T value;
	private ControlTabBar<T> tabBar;

	public ControlTab(final ControlTabBar<T> parent, final int x, final int y, final int w, final int h, final T value) {
		super(parent, x, y, w, h);
		this.value = value;
		this.tabBar = parent;
		this.addAttribute(Attribute.MOUSE_OVER);
		this.addSelfEventHandler(EventMouse.Down.class, event -> {
			ControlTab.this.callEvent(new EventValueChanged<Object>(ControlTab.this.getWidget(), ControlTab.this.getValue()));
		});
	}

	@Override
	@SideOnly(Side.CLIENT)
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

	public Alignment getTabPosition() {
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
		final ITexture lTexture = CraftGUI.RENDER.getTexture(texture);
		final Alignment alignment = this.getTabPosition();
		ITexture iTexture = lTexture.crop(alignment, 8);
		final IArea area = this.getArea();
		if (texture == CraftGUITexture.TAB_DISABLED) {
			if (alignment == Alignment.TOP || alignment == Alignment.LEFT) {
				area.setPosition(area.getPosition().sub(new Point(4 * alignment.x(), 4 * alignment.y())));
				area.setSize(area.getSize().add(new Point(4 * alignment.x(), 4 * alignment.y())));
			} else {
				area.setSize(area.getSize().sub(new Point(4 * alignment.x(), 4 * alignment.y())));
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
				iTexture = iTexture.crop(alignment, 8);
				RenderUtil.setColour(icon.getOutlineColour());
				CraftGUI.RENDER.texture(iTexture, area.inset(2));
			}
		}
	}
}
