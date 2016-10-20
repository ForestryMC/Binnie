package binnie.extratrees.api;

public interface IDesign {
    String getName();

    ILayout getTopPattern();

    ILayout getBottomPattern();

    ILayout getNorthPattern();

    ILayout getEastPattern();

    ILayout getSouthPattern();

    ILayout getWestPattern();
}
