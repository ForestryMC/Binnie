package binnie.botany.api;

import forestry.api.genetics.IAlleleInteger;

public interface IFlowerColour {
    int getColor(final boolean p0);

    IAlleleInteger getAllele();

    int getID();

    String getName();
}
