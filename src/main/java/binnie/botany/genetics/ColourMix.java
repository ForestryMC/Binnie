package binnie.botany.genetics;

import binnie.botany.api.IColourMix;
import binnie.botany.api.IFlowerColour;

public class ColourMix implements IColourMix {
    IFlowerColour colour1;
    IFlowerColour colour2;
    IFlowerColour result;
    int chance;

    @Override
    public IFlowerColour getColour1() {
        return this.colour1;
    }

    @Override
    public IFlowerColour getColour2() {
        return this.colour2;
    }

    @Override
    public boolean isMutation(final IFlowerColour a, final IFlowerColour b) {
        return (a == this.colour1 && b == this.colour2) || (a == this.colour2 && b == this.colour1);
    }

    @Override
    public int getChance() {
        return this.chance;
    }

    @Override
    public IFlowerColour getResult() {
        return this.result;
    }

    public ColourMix(final IFlowerColour colour1, final IFlowerColour colour2, final IFlowerColour result, final int chance) {
        this.colour1 = colour1;
        this.colour2 = colour2;
        this.result = result;
        this.chance = chance;
    }
}
