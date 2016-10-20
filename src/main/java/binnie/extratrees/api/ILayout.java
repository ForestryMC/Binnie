package binnie.extratrees.api;

public interface ILayout {
    IPattern getPattern();

    boolean isInverted();

    ILayout rotateRight();

    ILayout rotateLeft();

    ILayout flipHorizontal();

    ILayout flipVertical();

    ILayout invert();

//	IIcon getPrimaryIcon(final IDesignSystem p0);
//
//	IIcon getSecondaryIcon(final IDesignSystem p0);
}
