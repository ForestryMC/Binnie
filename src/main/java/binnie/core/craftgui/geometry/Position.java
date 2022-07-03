package binnie.core.craftgui.geometry;

public enum Position {
    TOP(0, -1),
    BOTTOM(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    protected int x;
    protected int y;

    Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public Position opposite() {
        switch (this) {
            case BOTTOM:
                return Position.TOP;

            case LEFT:
                return Position.RIGHT;

            case RIGHT:
                return Position.LEFT;

            case TOP:
                return Position.BOTTOM;
        }
        return null;
    }
}
