package binnie.botany.api;

public interface IColourMix {
    IFlowerColour getColour1();

    IFlowerColour getColour2();

    boolean isMutation(final IFlowerColour p0, final IFlowerColour p1);

    int getChance();

    IFlowerColour getResult();
}
