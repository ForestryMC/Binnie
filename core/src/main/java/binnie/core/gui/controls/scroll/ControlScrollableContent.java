package binnie.core.gui.controls.scroll;

import javax.annotation.Nullable;

import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.events.EventMouse;
import binnie.core.gui.events.EventWidget;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Point;

public class ControlScrollableContent<T extends IWidget> extends Control implements IControlScrollable {
	@Nullable
	private T controlChild;
	private final int scrollBarSize;
	private float percentageIndex;

	public ControlScrollableContent(IWidget parent, int x, int y, int w, int h, int scrollBarSize) {
		super(parent, x, y, w, h);
		this.percentageIndex = 0;
		if (scrollBarSize != 0) {
			new ControlScroll(this, this.getSize().xPos() - scrollBarSize, 0, scrollBarSize, this.getSize().yPos(), this);
		}
		this.addEventHandler(EventMouse.Wheel.class, event -> {
			if (ControlScrollableContent.this.getRelativeMousePosition().xPos() > 0 && ControlScrollableContent.this.getRelativeMousePosition().yPos() > 0 && ControlScrollableContent.this.getRelativeMousePosition().xPos() < ControlScrollableContent.this.getSize().xPos() && ControlScrollableContent.this.getRelativeMousePosition().yPos() < ControlScrollableContent.this.getSize().yPos()) {
				if (ControlScrollableContent.this.getMovementRange() == 0) {
					return;
				}
				float percentageMove = 0.8f / ControlScrollableContent.this.getMovementRange();
				ControlScrollableContent.this.movePercentage(percentageMove * -event.getDWheel());
			}
		});
		this.scrollBarSize = scrollBarSize;
	}

	public void setScrollableContent(@Nullable T child) {
		this.controlChild = child;
		if (child == null) {
			return;
		}

		child.setCroppedZone(this, new Area(1, 1, getSize().xPos() - 2 - this.scrollBarSize, getSize().yPos() - 2));

		child.addSelfEventHandler(EventWidget.ChangeSize.class, event -> {
			child.setOffset(new Point(0, Math.round(-ControlScrollableContent.this.percentageIndex * ControlScrollableContent.this.getMovementRange())));
			if (ControlScrollableContent.this.getMovementRange() == 0) {
				ControlScrollableContent.this.percentageIndex = 0;
			}
		});
	}

	@Nullable
	public T getContent() {
		return this.controlChild;
	}

	@Override
	public float getPercentageShown() {
		if (this.controlChild == null || this.controlChild.getSize().yPos() == 0) {
			return 1;
		}
		float shown = (float) this.getSize().yPos() / (float) this.controlChild.getSize().yPos();
		return Math.min(shown, 1);
	}

	@Override
	public float getPercentageIndex() {
		return this.percentageIndex;
	}

	@Override
	public void setPercentageIndex(float index) {
		this.movePercentage(index - this.percentageIndex);
	}

	@Override
	public void movePercentage(float percentage) {
		if (this.controlChild == null) {
			return;
		}
		this.percentageIndex += percentage;
		if (this.percentageIndex > 1) {
			this.percentageIndex = 1;
		} else if (this.percentageIndex < 0) {
			this.percentageIndex = 0;
		}
		if (this.getMovementRange() == 0) {
			this.percentageIndex = 0;
		}
		this.controlChild.setOffset(new Point(0, Math.round(-this.percentageIndex * this.getMovementRange())));
	}

	@Override
	public float getMovementRange() {
		if (this.controlChild == null) {
			return 0;
		}
		float range = this.controlChild.getSize().yPos() - this.getSize().yPos();
		return Math.max(range, 0);
	}

	@Override
	public void onUpdateClient() {
		this.setPercentageIndex(this.getPercentageIndex());
	}

	public void ensureVisible(float minY, float maxY, float totalY) {
		minY /= totalY;
		maxY /= totalY;
		float shownPercentage = this.getPercentageShown();
		float percentageIndex = this.getPercentageIndex();
		float minPercent = (1 - shownPercentage) * percentageIndex;
		float maxPercent = minPercent + shownPercentage;
		if (maxY > maxPercent) {
			this.setPercentageIndex((maxY - shownPercentage) / (1 - shownPercentage));
		}
		if (minY < minPercent) {
			this.setPercentageIndex(minY / (1 - shownPercentage));
		}
	}

	public int getScrollBarSize() {
		return scrollBarSize;
	}
}
