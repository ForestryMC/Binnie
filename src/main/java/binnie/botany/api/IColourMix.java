package binnie.botany.api;

public interface IColourMix {
    IFlowerColor getColor1();

    IFlowerColor getColor2();

    boolean isMutation(IFlowerColor color1, IFlowerColor color2);

    int getChance();

    IFlowerColor getResult();
}
