package binnie.botany.api;

import forestry.api.genetics.IAlleleInteger;

public interface IFlowerColor {
    int getColor(boolean dis);

    IAlleleInteger getAllele();

    int getID();

    String getName();
}
