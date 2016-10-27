package binnie.botany.api;

import forestry.api.genetics.IAlleleSpeciesBuilder;

public interface IAlleleFlowerSpeciesBuilder extends IAlleleSpeciesBuilder {

	@Override
	IAlleleFlowerSpecies build();

	IAlleleFlowerSpeciesBuilder setPH(EnumAcidity acidity);

	IAlleleFlowerSpeciesBuilder setMoisture(EnumMoisture moisture);

}
