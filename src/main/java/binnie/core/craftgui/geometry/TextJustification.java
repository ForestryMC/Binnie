package binnie.core.craftgui.geometry;

public enum TextJustification {
    TOP_LEFT(0.0f, 0.0f),
    TOP_CENTER(0.5f, 0.0f),
    TOP_RIGHT(1.0f, 0.0f),
    MIDDLE_LEFT(0.0f, 0.5f),
    MIDDLE_CENTER(0.5f, 0.5f),
    MIDDLE_RIGHT(1.0f, 0.5f),
    BOTTOM_LEFT(0.0f, 1.0f),
    BOTTOM_CENTER(0.5f, 1.0f),
    BOTTOM_RIGHT(1.0f, 1.0f);

    protected float xOffset;
    protected float yOffset;

    TextJustification(float xOffset, float yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public float getXOffset() {
        return xOffset;
    }

    public float getYOffset() {
        return yOffset;
    }
}
