package binnie.core.craftgui.controls.scroll;

import javax.annotation.Nullable;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.events.EventWidget;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.Point;

public class ControlScrollableContent<T extends IWidget> extends Control implements IControlScrollable {
	@Nullable
	protected T controlChild;
	protected int scrollBarSize;
	float percentageIndex;

	public ControlScrollableContent(final IWidget parent, final int x, final int y, final int w, final int h, final int scrollBarSize) {
		super(parent, x, y, w, h);
		this.percentageIndex = 0;
		if (scrollBarSize != 0) {
			new ControlScroll(this, this.getSize().x() - scrollBarSize, 0, scrollBarSize, this.getSize().y(), this);
		}
		this.addEventHandler(new EventMouse.Wheel.Handler() {
			@Override
			public void onEvent(final EventMouse.Wheel event) {
				if (ControlScrollableContent.this.getRelativeMousePosition().x() > 0 && ControlScrollableContent.this.getRelativeMousePosition().y() > 0 && ControlScrollableContent.this.getRelativeMousePosition().x() < ControlScrollableContent.this.getSize().x() && ControlScrollableContent.this.getRelativeMousePosition().y() < ControlScrollableContent.this.getSize().y()) {
					if (ControlScrollableContent.this.getMovementRange() == 0) {
						return;
					}
					final float percentageMove = 0.8f / ControlScrollableContent.this.getMovementRange();
					ControlScrollableContent.this.movePercentage(percentageMove * -event.getDWheel());
				}
			}
		});
		this.scrollBarSize = scrollBarSize;
	}

	public void setScrollableContent(@Nullable final T child) {
		this.controlChild = child;
		if (child == null) {
			return;
		}

		child.setCroppedZone(this, new Area(1, 1, getSize().x() - 2 - this.scrollBarSize, getSize().y() - 2));

		child.addSelfEventHandler(new EventWidget.ChangeSize.Handler() {
			@Override
			public void onEvent(final EventWidget.ChangeSize event) {
				child.setOffset(new Point(0, Math.round(-ControlScrollableContent.this.percentageIndex * ControlScrollableContent.this.getMovementRange())));
				if (ControlScrollableContent.this.getMovementRange() == 0) {
					ControlScrollableContent.this.percentageIndex = 0;
				}
			}
		});
	}

	@Nullable
	public T getContent() {
		return this.controlChild;
	}

	@Override
	public float getPercentageShown() {
		if (this.controlChild == null || this.controlChild.getSize().y() == 0) {
			return 1;
		}
		float shown = (float)this.getSize().y() / (float) this.controlChild.getSize().y();
		return Math.min(shown, 1);
	}

	@Override
	public float getPercentageIndex() {
		return this.percentageIndex;
	}

	@Override
	public void setPercentageIndex(final float index) {
		this.movePercentage(index - this.percentageIndex);
	}

	@Override
	public void movePercentage(final float percentage) {
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
		final float range = this.controlChild.getSize().y() - this.getSize().y();
		return Math.max(range, 0);
	}

	@Override
	public void onUpdateClient() {
		this.setPercentageIndex(this.getPercentageIndex());
	}

	public void ensureVisible(float minY, float maxY, final float totalY) {
		minY /= totalY;
		maxY /= totalY;
		final float shownPercentage = this.getPercentageShown();
		final float percentageIndex = this.getPercentageIndex();
		final float minPercent = (1 - shownPercentage) * percentageIndex;
		final float maxPercent = minPercent + shownPercentage;
		if (maxY > maxPercent) {
			this.setPercentageIndex((maxY - shownPercentage) / (1 - shownPercentage));
		}
		if (minY < minPercent) {
			this.setPercentageIndex(minY / (1 - shownPercentage));
		}
	}
}
