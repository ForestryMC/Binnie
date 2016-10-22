package binnie.botany.api;

public interface IColourMix {
    IFlowerColour getColourFirst();

    IFlowerColour getColourSecond();

    boolean isMutation(IFlowerColour colourFirst, IFlowerColour colourSecond);

    int getChance();

    IFlowerColour getResult();
}
