package binnie.craftgui.controls.scroll;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.events.EventMouse;
import binnie.craftgui.events.EventWidget;

public class ControlScrollableContent<T extends IWidget> extends Control implements IControlScrollable {
	protected T controlChild;
	protected float scrollBarSize;
	float percentageIndex;

	public ControlScrollableContent(final IWidget parent, final float x, final float y, final float w, final float h, final float scrollBarSize) {
		super(parent, x, y, w, h);
		this.percentageIndex = 0.0f;
		if (scrollBarSize != 0.0f) {
			new ControlScroll(this, this.getSize().x() - scrollBarSize, 0.0f, scrollBarSize, this.getSize().y(), this);
		}
		this.addEventHandler(new EventMouse.Wheel.Handler() {
			@Override
			public void onEvent(final EventMouse.Wheel event) {
				if (ControlScrollableContent.this.getRelativeMousePosition().x() > 0.0f && ControlScrollableContent.this.getRelativeMousePosition().y() > 0.0f && ControlScrollableContent.this.getRelativeMousePosition().x() < ControlScrollableContent.this.getSize().x() && ControlScrollableContent.this.getRelativeMousePosition().y() < ControlScrollableContent.this.getSize().y()) {
					if (ControlScrollableContent.this.getMovementRange() == 0.0f) {
						return;
					}
					final float percentageMove = 0.8f / ControlScrollableContent.this.getMovementRange();
					ControlScrollableContent.this.movePercentage(percentageMove * -event.getDWheel());
				}
			}
		});
		this.scrollBarSize = scrollBarSize;
	}

	public void setScrollableContent(final T child) {
		this.controlChild = child;
		if (child == null) {
			return;
		}

		child.setCroppedZone(this, new IArea(1.0F, 1.0F, getSize().x() - 2.0F - this.scrollBarSize, getSize().y() - 2.0F));

		child.addSelfEventHandler(new EventWidget.ChangeSize.Handler() {
			@Override
			public void onEvent(final EventWidget.ChangeSize event) {
				ControlScrollableContent.this.controlChild.setOffset(new IPoint(0.0f, -ControlScrollableContent.this.percentageIndex * ControlScrollableContent.this.getMovementRange()));
				if (ControlScrollableContent.this.getMovementRange() == 0.0f) {
					ControlScrollableContent.this.percentageIndex = 0.0f;
				}
			}
		});
	}

	public T getContent() {
		return this.controlChild;
	}

	@Override
	public float getPercentageShown() {
		if (this.controlChild == null || this.controlChild.getSize().y() == 0.0f) {
			return 1.0f;
		}
		final float shown = this.getSize().y() / this.controlChild.getSize().y();
		return Math.min(shown, 1.0f);
	}

	@Override
	public float getPercentageIndex() {
		return this.percentageIndex;
	}

	@Override
	public void movePercentage(final float percentage) {
		if (this.controlChild == null) {
			return;
		}
		this.percentageIndex += percentage;
		if (this.percentageIndex > 1.0f) {
			this.percentageIndex = 1.0f;
		} else if (this.percentageIndex < 0.0f) {
			this.percentageIndex = 0.0f;
		}
		if (this.getMovementRange() == 0.0f) {
			this.percentageIndex = 0.0f;
		}
		this.controlChild.setOffset(new IPoint(0.0f, -this.percentageIndex * this.getMovementRange()));
	}

	@Override
	public void setPercentageIndex(final float index) {
		this.movePercentage(index - this.percentageIndex);
	}

	@Override
	public float getMovementRange() {
		if (this.controlChild == null) {
			return 0.0f;
		}
		final float range = this.controlChild.getSize().y() - this.getSize().y();
		return Math.max(range, 0.0f);
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
		final float minPercent = (1.0f - shownPercentage) * percentageIndex;
		final float maxPercent = minPercent + shownPercentage;
		if (maxY > maxPercent) {
			this.setPercentageIndex((maxY - shownPercentage) / (1.0f - shownPercentage));
		}
		if (minY < minPercent) {
			this.setPercentageIndex(minY / (1.0f - shownPercentage));
		}
	}
}
