package binnie.core.gui.controls.scroll;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.IWidget;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.events.EventMouse;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.resource.minecraft.CraftGUITexture;

public class ControlScrollBar extends Control {
	protected final IControlScrollable scrollable;

	public ControlScrollBar(final ControlScroll parent) {
		this(parent, 0, 0, parent.getSize().xPos(), parent.getSize().yPos(), parent.getScrollableWidget());
	}

	public ControlScrollBar(final IWidget parent, final int x, final int y, final int w, final int h, final IControlScrollable scrollable2) {
		super(parent, x, y, w, h);
		this.scrollable = scrollable2;
		this.addAttribute(Attribute.MOUSE_OVER);
		this.addSelfEventHandler(new EventMouse.Drag.Handler() {
			@Override
			public void onEvent(final EventMouse.Drag event) {
				ControlScrollBar.this.scrollable.movePercentage(event.getDy() / (float) (ControlScrollBar.this.getHeight() - ControlScrollBar.this.getBarHeight()));
			}
		});
		this.addSelfEventHandler(new EventMouse.Down.Handler() {
			@Override
			public void onEvent(final EventMouse.Down event) {
				final float shownPercentage = ControlScrollBar.this.scrollable.getPercentageShown();
				final float percentageIndex = ControlScrollBar.this.scrollable.getPercentageIndex();
				final float minPercent = (1.0f - shownPercentage) * percentageIndex;
				final float maxPercent = minPercent + shownPercentage;
				float clickedPercentage = (float)ControlScrollBar.this.getRelativeMousePosition().yPos() / (ControlScrollBar.this.getHeight() - 2.0f);
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
		return Math.round(this.getHeight() * this.scrollable.getPercentageShown());
	}

	protected Area getRenderArea() {
		int height = this.getBarHeight();
		if (height < 6) {
			height = 6;
		}
		final int yOffset = Math.round((this.getHeight() - this.getBarHeight()) * this.scrollable.getPercentageIndex());
		return new Area(0, yOffset, this.getSize().xPos(), height);
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
		CraftGUI.RENDER.texture(texture, renderArea);
	}
}
