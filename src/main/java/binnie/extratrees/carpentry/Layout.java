package binnie.extratrees.carpentry;

import binnie.extratrees.api.IDesignSystem;
import binnie.extratrees.api.ILayout;
import binnie.extratrees.api.IPattern;
import net.minecraft.util.IIcon;

public class Layout implements ILayout {
    protected IPattern pattern;
    protected boolean inverted;

    private Layout(IPattern pattern, boolean inverted) {
        this.pattern = pattern;
        this.inverted = inverted;
    }

    private Layout(IPattern pattern) {
        this(pattern, false);
    }

    public static ILayout get(IPattern pattern, boolean inverted) {
        return new Layout(pattern, inverted);
    }

    public static ILayout get(IPattern pattern) {
        return new Layout(pattern, false);
    }

    @Override
    public IPattern getPattern() {
        return pattern;
    }

    @Override
    public boolean isInverted() {
        return inverted;
    }

    ILayout newLayout(ILayout newLayout) {
        return new Layout(newLayout.getPattern(), inverted ^ newLayout.isInverted());
    }

    @Override
    public ILayout rotateRight() {
        return rotateLeft().rotateLeft().rotateLeft();
    }

    @Override
    public ILayout rotateLeft() {
        return newLayout(pattern.getRotation());
    }

    @Override
    public ILayout flipHorizontal() {
        return newLayout(pattern.getHorizontalFlip());
    }

    @Override
    public ILayout flipVertical() {
        return newLayout(pattern.getHorizontalFlip().rotateLeft().rotateLeft());
    }

    @Override
    public IIcon getPrimaryIcon(IDesignSystem system) {
        return inverted ? pattern.getSecondaryIcon(system) : pattern.getPrimaryIcon(system);
    }

    @Override
    public IIcon getSecondaryIcon(IDesignSystem system) {
        return inverted ? pattern.getPrimaryIcon(system) : pattern.getSecondaryIcon(system);
    }

    @Override
    public ILayout invert() {
        return new Layout(pattern, !inverted);
    }
}
