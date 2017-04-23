// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.controls.scroll;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.events.EventWidget;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;

public class ControlScrollableContent<T extends IWidget> extends Control implements IControlScrollable
{
	protected T controlChild;
	protected float scrollBarSize;
	float percentageIndex;

	public ControlScrollableContent(final IWidget parent, final float x, final float y, final float w, final float h, final float scrollBarSize) {
		super(parent, x, y, w, h);
		percentageIndex = 0.0f;
		if (scrollBarSize != 0.0f) {
			new ControlScroll(this, getSize().x() - scrollBarSize, 0.0f, scrollBarSize, getSize().y(), this);
		}
		addEventHandler(new EventMouse.Wheel.Handler() {
			@Override
			public void onEvent(final EventMouse.Wheel event) {
				if (getRelativeMousePosition().x() > 0.0f && getRelativeMousePosition().y() > 0.0f && getRelativeMousePosition().x() < getSize().x() && getRelativeMousePosition().y() < getSize().y()) {
					if (getMovementRange() == 0.0f) {
						return;
					}
					final float percentageMove = 0.8f / getMovementRange();
					movePercentage(percentageMove * -event.getDWheel());
				}
			}
		});
		this.scrollBarSize = scrollBarSize;
	}

	public void setScrollableContent(final T child) {
		controlChild = child;
		if (child == null) {
			return;
		}

		child.setCroppedZone(this, new IArea(1.0F, 1.0F, getSize().x() - 2.0F - scrollBarSize, getSize().y() - 2.0F));

		child.addSelfEventHandler(new EventWidget.ChangeSize.Handler() {
			@Override
			public void onEvent(final EventWidget.ChangeSize event) {
				controlChild.setOffset(new IPoint(0.0f, -percentageIndex * getMovementRange()));
				if (getMovementRange() == 0.0f) {
					percentageIndex = 0.0f;
				}
			}
		});
	}

	public T getContent() {
		return controlChild;
	}

	@Override
	public float getPercentageShown() {
		if (controlChild == null || controlChild.getSize().y() == 0.0f) {
			return 1.0f;
		}
		final float shown = getSize().y() / controlChild.getSize().y();
		return Math.min(shown, 1.0f);
	}

	@Override
	public float getPercentageIndex() {
		return percentageIndex;
	}

	@Override
	public void movePercentage(final float percentage) {
		if (controlChild == null) {
			return;
		}
		percentageIndex += percentage;
		if (percentageIndex > 1.0f) {
			percentageIndex = 1.0f;
		}
		else if (percentageIndex < 0.0f) {
			percentageIndex = 0.0f;
		}
		if (getMovementRange() == 0.0f) {
			percentageIndex = 0.0f;
		}
		controlChild.setOffset(new IPoint(0.0f, -percentageIndex * getMovementRange()));
	}

	@Override
	public void setPercentageIndex(final float index) {
		movePercentage(index - percentageIndex);
	}

	@Override
	public float getMovementRange() {
		if (controlChild == null) {
			return 0.0f;
		}
		final float range = controlChild.getSize().y() - getSize().y();
		return Math.max(range, 0.0f);
	}

	@Override
	public void onUpdateClient() {
		setPercentageIndex(getPercentageIndex());
	}

	public void ensureVisible(float minY, float maxY, final float totalY) {
		minY /= totalY;
		maxY /= totalY;
		final float shownPercentage = getPercentageShown();
		final float percentageIndex = getPercentageIndex();
		final float minPercent = (1.0f - shownPercentage) * percentageIndex;
		final float maxPercent = minPercent + shownPercentage;
		if (maxY > maxPercent) {
			setPercentageIndex((maxY - shownPercentage) / (1.0f - shownPercentage));
		}
		if (minY < minPercent) {
			setPercentageIndex(minY / (1.0f - shownPercentage));
		}
	}
}
