package binnie.botany.genetics;

import binnie.botany.api.IColourMix;
import binnie.botany.api.IFlowerColor;

public class ColorMix implements IColourMix {
    protected IFlowerColor color1;
    protected IFlowerColor color2;
    protected IFlowerColor result;
    protected int chance;

    public ColorMix(IFlowerColor color1, IFlowerColor color2, IFlowerColor result, int chance) {
        this.color1 = color1;
        this.color2 = color2;
        this.result = result;
        this.chance = chance;
    }

    @Override
    public IFlowerColor getColor1() {
        return color1;
    }

    @Override
    public IFlowerColor getColor2() {
        return color2;
    }

    @Override
    public boolean isMutation(IFlowerColor color1, IFlowerColor color2) {
        return (color1 == this.color1 && color2 == this.color2) || (color1 == this.color2 && color2 == this.color1);
    }

    @Override
    public int getChance() {
        return chance;
    }

    @Override
    public IFlowerColor getResult() {
        return result;
    }
}
