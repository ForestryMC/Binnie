// 
// Decompiled by Procyon v0.5.30
// 

package binnie.craftgui.core.geometry;

import binnie.craftgui.events.EventValueChanged;
import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.core.IWidget;

public class CraftGUIUtil
{
	public static void alignToWidget(final IWidget target, final IWidget relativeTo) {
		final IPoint startPos = target.getAbsolutePosition();
		final IPoint endPos = relativeTo.getAbsolutePosition();
		moveWidget(target, endPos.sub(startPos));
	}

	public static void moveWidget(final IWidget target, final IPoint movement) {
		target.setPosition(target.getPosition().add(movement));
	}

	public static void horizontalGrid(final float px, final float py, final IWidget... widgets) {
		horizontalGrid(px, py, TextJustification.MiddleCenter, 0.0f, widgets);
	}

	public static void horizontalGrid(final float px, final float py, final TextJustification just, final float spacing, final IWidget... widgets) {
		float x = 0.0f;
		float h = 0.0f;
		for (final IWidget widget : widgets) {
			h = Math.max(h, widget.getSize().y());
		}
		for (final IWidget widget : widgets) {
			widget.setPosition(new IPoint(px + x, py + (h - widget.getSize().y()) * just.yOffset));
			x += widget.getSize().x() + spacing;
		}
	}

	public static void verticalGrid(final float px, final float py, final IWidget... widgets) {
		horizontalGrid(px, py, TextJustification.MiddleCenter, 0.0f, widgets);
	}

	public static void verticalGrid(final float px, final float py, final TextJustification just, final float spacing, final IWidget... widgets) {
		float y = 0.0f;
		float w = 0.0f;
		for (final IWidget widget : widgets) {
			w = Math.max(w, widget.getSize().x());
		}
		for (final IWidget widget : widgets) {
			widget.setPosition(new IPoint(px + (w - widget.getSize().x()) * just.xOffset, py + y));
			y += widget.getSize().y() + spacing;
		}
	}

	public static <T> void linkWidgets(final IControlValue<T> tab, final IControlValue<T> target) {
		tab.addSelfEventHandler(new EventValueChanged.Handler() {
			@Override
			public void onEvent(final EventValueChanged event) {
				target.setValue((T) event.getValue());
			}
		});
		target.addSelfEventHandler(new EventValueChanged.Handler() {
			@Override
			public void onEvent(final EventValueChanged event) {
				tab.setValue((T) event.getValue());
			}
		});
	}
}
