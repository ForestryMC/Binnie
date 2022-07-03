package binnie.core.craftgui.controls.scroll;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.events.EventWidget;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;

public class ControlScrollableContent<T extends IWidget> extends Control implements IControlScrollable {
    protected T controlChild;
    protected float scrollBarSize;
    protected float percentageIndex;

    public ControlScrollableContent(IWidget parent, float x, float y, float w, float h, float scrollBarSize) {
        super(parent, x, y, w, h);
        this.scrollBarSize = scrollBarSize;
        if (scrollBarSize != 0.0f) {
            new ControlScroll(
                    this,
                    getSize().x() - scrollBarSize,
                    0.0f,
                    scrollBarSize,
                    getSize().y(),
                    this);
        }

        percentageIndex = 0.0f;
        addEventHandler(mouseWheelHandler);
    }

    @Override
    public void onUpdateClient() {
        setPercentageIndex(getPercentageIndex());
    }

    private EventMouse.Wheel.Handler mouseWheelHandler = new EventMouse.Wheel.Handler() {
        @Override
        public void onEvent(EventMouse.Wheel event) {
            IPoint mousePos = getRelativeMousePosition();
            if (mousePos.x() > 0.0f
                    && mousePos.y() > 0.0f
                    && mousePos.x() < getSize().x()
                    && mousePos.y() < getSize().y()) {
                if (getMovementRange() == 0.0f) {
                    return;
                }
                float percentageMove = 0.8f / getMovementRange();
                movePercentage(percentageMove * -event.getDWheel());
            }
        }
    };

    public void setScrollableContent(T child) {
        controlChild = child;
        if (child == null) {
            return;
        }

        child.setCroppedZone(
                this,
                new IArea(
                        1.0F,
                        1.0F,
                        getSize().x() - 2.0F - scrollBarSize,
                        getSize().y() - 2.0F));
        child.addSelfEventHandler(new EventWidget.ChangeSize.Handler() {
            @Override
            public void onEvent(EventWidget.ChangeSize event) {
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
        float shown = getSize().y() / controlChild.getSize().y();
        return Math.min(shown, 1.0f);
    }

    @Override
    public float getPercentageIndex() {
        return percentageIndex;
    }

    @Override
    public void movePercentage(float percentage) {
        if (controlChild == null) {
            return;
        }

        percentageIndex += percentage;
        if (percentageIndex > 1.0f) {
            percentageIndex = 1.0f;
        } else if (percentageIndex < 0.0f) {
            percentageIndex = 0.0f;
        }

        if (getMovementRange() == 0.0f) {
            percentageIndex = 0.0f;
        }
        controlChild.setOffset(new IPoint(0.0f, -percentageIndex * getMovementRange()));
    }

    @Override
    public void setPercentageIndex(float index) {
        movePercentage(index - percentageIndex);
    }

    @Override
    public float getMovementRange() {
        if (controlChild == null) {
            return 0.0f;
        }
        float range = controlChild.getSize().y() - getSize().y();
        return Math.max(range, 0.0f);
    }

    public void ensureVisible(float minY, float maxY, float totalY) {
        minY /= totalY;
        maxY /= totalY;
        float shownPercentage = getPercentageShown();
        float percentageIndex = getPercentageIndex();
        float minPercent = (1.0f - shownPercentage) * percentageIndex;
        float maxPercent = minPercent + shownPercentage;

        if (maxY > maxPercent) {
            setPercentageIndex((maxY - shownPercentage) / (1.0f - shownPercentage));
        }
        if (minY < minPercent) {
            setPercentageIndex(minY / (1.0f - shownPercentage));
        }
    }
}
