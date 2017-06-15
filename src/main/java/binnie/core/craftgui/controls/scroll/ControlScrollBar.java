package binnie.core.craftgui.controls.scroll;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;

public class ControlScrollBar extends Control {
	protected final IControlScrollable scrollable;

	public ControlScrollBar(final ControlScroll parent) {
		this(parent, 0, 0, parent.getSize().x(), parent.getSize().y(), parent.getScrollableWidget());
	}

	public ControlScrollBar(final IWidget parent, final int x, final int y, final int w, final int h, final IControlScrollable scrollable2) {
		super(parent, x, y, w, h);
		this.scrollable = scrollable2;
		this.addAttribute(Attribute.MouseOver);
		this.addSelfEventHandler(new EventMouse.Drag.Handler() {
			@Override
			public void onEvent(final EventMouse.Drag event) {
				ControlScrollBar.this.scrollable.movePercentage(event.getDy() / (ControlScrollBar.this.height() - ControlScrollBar.this.getBarHeight()));
			}
		});
		this.addSelfEventHandler(new EventMouse.Down.Handler() {
			@Override
			public void onEvent(final EventMouse.Down event) {
				final float shownPercentage = ControlScrollBar.this.scrollable.getPercentageShown();
				final float percentageIndex = ControlScrollBar.this.scrollable.getPercentageIndex();
				final float minPercent = (1.0f - shownPercentage) * percentageIndex;
				final float maxPercent = minPercent + shownPercentage;
				float clickedPercentage = ControlScrollBar.this.getRelativeMousePosition().y() / (ControlScrollBar.this.height() - 2.0f);
				clickedPercentage = Math.max(Math.min(clickedPercentage, 1.0f), 0.0f);
				if (clickedPercentage > maxPercent) {
					ControlScrollBar.this.scrollable.setPercentageIndex((clickedPercentage - shownPercentage) / (1.0f - shownPercentage));
				}
				if (clickedPercentage < minPercent) {
					ControlScrollBar.this.scrollable.setPercentageIndex(clickedPercentage / (1.0f - shownPercentage));
				}
			}
		});
	}

	@Override
	public void onUpdateClient() {
	}

	@Override
	public boolean isEnabled() {
		return this.scrollable.getPercentageShown() < 0.99f;
	}

	public int getBarHeight() {
		return Math.round(this.height() * this.scrollable.getPercentageShown());
	}

	protected Area getRenderArea() {
		int height = this.getBarHeight();
		if (height < 6) {
			height = 6;
		}
		final int yOffset = Math.round((this.height() - this.getBarHeight()) * this.scrollable.getPercentageIndex());
		return new Area(0, yOffset, this.getSize().x(), height);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		final Area renderArea = this.getRenderArea();
		Object texture = CraftGUITexture.ScrollDisabled;
		if (this.isMouseOver()) {
			texture = CraftGUITexture.ScrollHighlighted;
		} else if (this.isEnabled()) {
			texture = CraftGUITexture.Scroll;
		}
		CraftGUI.render.texture(texture, renderArea);
	}
}
