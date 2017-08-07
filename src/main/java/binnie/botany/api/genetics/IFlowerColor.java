package binnie.botany.api.genetics;

import forestry.api.genetics.IAlleleInteger;

public interface IFlowerColor {
	int getColor(boolean dis);

	IAlleleInteger getAllele();

	int getID();

	String getColorName();
}
