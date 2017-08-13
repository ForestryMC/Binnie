package binnie.core.gui.geometry;

import binnie.core.api.gui.IPoint;
import binnie.core.gui.IWidget;
import binnie.core.gui.controls.core.IControlValue;
import binnie.core.gui.events.EventValueChanged;

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
		horizontalGrid(px, py, TextJustification.MIDDLE_CENTER, 0, widgets);
	}

	public static void horizontalGrid(final int px, final int py, final TextJustification just, final int spacing, final IWidget... widgets) {
		int x = 0;
		int h = 0;
		for (final IWidget widget : widgets) {
			h = Math.max(h, widget.getSize().yPos());
		}
		for (final IWidget widget : widgets) {
			widget.setPosition(new Point(px + x, py + Math.round((h - widget.getSize().yPos()) * just.getYOffset())));
			x += widget.getSize().xPos() + spacing;
		}
	}

	public static void verticalGrid(final int px, final int py, final IWidget... widgets) {
		horizontalGrid(px, py, TextJustification.MIDDLE_CENTER, 0, widgets);
	}

	public static void verticalGrid(final int px, final int py, final TextJustification just, final int spacing, final IWidget... widgets) {
		int y = 0;
		int w = 0;
		for (final IWidget widget : widgets) {
			w = Math.max(w, widget.getSize().xPos());
		}
		for (final IWidget widget : widgets) {
			widget.setPosition(new Point(px + Math.round((w - widget.getSize().xPos()) * just.getXOffset()), py + y));
			y += widget.getSize().yPos() + spacing;
		}
	}

	public static <T> void linkWidgets(final IControlValue<T> tab, final IControlValue<T> target) {
		tab.addSelfEventHandler(EventValueChanged.class, event -> {
			target.setValue((T) event.getValue());
		});
		target.addSelfEventHandler(EventValueChanged.class, event -> {
			tab.setValue((T) event.getValue());
		});
	}
}
