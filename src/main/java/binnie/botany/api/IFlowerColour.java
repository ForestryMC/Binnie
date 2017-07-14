package binnie.botany.api;

import forestry.api.genetics.IAlleleInteger;

public interface IFlowerColour {
	int getColor(boolean dis);

	IAlleleInteger getAllele();

	int getID();

	String getColorName();
}
