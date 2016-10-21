package binnie.craftgui.core.geometry;

public enum TextJustification {
    TopLeft(0.0f, 0.0f),
    TopCenter(0.5f, 0.0f),
    TopRight(1.0f, 0.0f),
    MiddleLeft(0.0f, 0.5f),
    MiddleCenter(0.5f, 0.5f),
    MiddleRight(1.0f, 0.5f),
    BottomLeft(0.0f, 1.0f),
    BottomCenter(0.5f, 1.0f),
    BottomRight(1.0f, 1.0f);

    float xOffset;
    float yOffset;

    TextJustification(final float xOffset, final float yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public float getXOffset() {
        return this.xOffset;
    }

    public float getYOffset() {
        return this.yOffset;
    }
}
