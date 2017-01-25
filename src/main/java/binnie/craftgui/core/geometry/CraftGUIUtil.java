package binnie.craftgui.core.geometry;

import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.events.EventValueChanged;

public class CraftGUIUtil {
	public static void alignToWidget(final IWidget target, final IWidget relativeTo) {
		final IPoint startPos = target.getAbsolutePosition();
		final IPoint endPos = relativeTo.getAbsolutePosition();
		moveWidget(target, endPos.sub(startPos));
	}

	public static void moveWidget(final IWidget target, final IPoint movement) {
		target.setPosition(target.getPosition().add(movement));
	}

	public static void horizontalGrid(final int px, final int py, final IWidget... widgets) {
		horizontalGrid(px, py, TextJustification.MiddleCenter, 0, widgets);
	}

	public static void horizontalGrid(final int px, final int py, final TextJustification just, final int spacing, final IWidget... widgets) {
		int x = 0;
		int h = 0;
		for (final IWidget widget : widgets) {
			h = Math.max(h, widget.getSize().y());
		}
		for (final IWidget widget : widgets) {
			widget.setPosition(new IPoint(px + x, py + Math.round((h - widget.getSize().y()) * just.getYOffset())));
			x += widget.getSize().x() + spacing;
		}
	}

	public static void verticalGrid(final int px, final int py, final IWidget... widgets) {
		horizontalGrid(px, py, TextJustification.MiddleCenter, 0, widgets);
	}

	public static void verticalGrid(final int px, final int py, final TextJustification just, final int spacing, final IWidget... widgets) {
		int y = 0;
		int w = 0;
		for (final IWidget widget : widgets) {
			w = Math.max(w, widget.getSize().x());
		}
		for (final IWidget widget : widgets) {
			widget.setPosition(new IPoint(px + Math.round((w - widget.getSize().x()) * just.getXOffset()), py + y));
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
