// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.controls.scroll;

import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;

public class ControlScrollBar extends Control
{
	protected final IControlScrollable scrollable;

	public ControlScrollBar(final ControlScroll parent) {
		this(parent, 0.0f, 0.0f, parent.getSize().x(), parent.getSize().y(), parent.getScrollableWidget());
	}

	public ControlScrollBar(final IWidget parent, final float x, final float y, final float w, final float h, final IControlScrollable scrollable2) {
		super(parent, x, y, w, h);
		scrollable = scrollable2;
		addAttribute(Attribute.MouseOver);
		addSelfEventHandler(new EventMouse.Drag.Handler() {
			@Override
			public void onEvent(final EventMouse.Drag event) {
				scrollable.movePercentage(event.getDy() / (h() - getBarHeight()));
			}
		});
		addSelfEventHandler(new EventMouse.Down.Handler() {
			@Override
			public void onEvent(final EventMouse.Down event) {
				final float shownPercentage = scrollable.getPercentageShown();
				final float percentageIndex = scrollable.getPercentageIndex();
				final float minPercent = (1.0f - shownPercentage) * percentageIndex;
				final float maxPercent = minPercent + shownPercentage;
				float clickedPercentage = getRelativeMousePosition().y() / (h() - 2.0f);
				clickedPercentage = Math.max(Math.min(clickedPercentage, 1.0f), 0.0f);
				if (clickedPercentage > maxPercent) {
					scrollable.setPercentageIndex((clickedPercentage - shownPercentage) / (1.0f - shownPercentage));
				}
				if (clickedPercentage < minPercent) {
					scrollable.setPercentageIndex(clickedPercentage / (1.0f - shownPercentage));
				}
			}
		});
	}

	@Override
	public void onUpdateClient() {
	}

	@Override
	public boolean isEnabled() {
		return scrollable.getPercentageShown() < 0.99f;
	}

	public float getBarHeight() {
		return h() * scrollable.getPercentageShown();
	}

	protected IArea getRenderArea() {
		float height = getBarHeight();
		if (height < 6.0f) {
			height = 6.0f;
		}
		final float yOffset = ((int) h() - (int) getBarHeight()) * scrollable.getPercentageIndex();
		return new IArea(0.0f, yOffset, getSize().x(), height);
	}

	@Override
	public void onRenderBackground() {
		final IArea renderArea = getRenderArea();
		Object texture = CraftGUITexture.ScrollDisabled;
		if (isMouseOver()) {
			texture = CraftGUITexture.ScrollHighlighted;
		}
		else if (isEnabled()) {
			texture = CraftGUITexture.Scroll;
		}
		CraftGUI.Render.texture(texture, renderArea);
	}
}
