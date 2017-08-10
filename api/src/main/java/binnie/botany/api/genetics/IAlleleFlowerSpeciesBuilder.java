package binnie.botany.api.genetics;

import forestry.api.genetics.IAlleleSpeciesBuilder;

import binnie.botany.api.gardening.EnumAcidity;
import binnie.botany.api.gardening.EnumMoisture;

public interface IAlleleFlowerSpeciesBuilder extends IAlleleSpeciesBuilder {
	@Override
	IAlleleFlowerSpecies build();

	IAlleleFlowerSpeciesBuilder setPH(EnumAcidity acidity);

	IAlleleFlowerSpeciesBuilder setMoisture(EnumMoisture moisture);
}
