package binnie.botany.api.genetics;

import binnie.botany.api.gardening.EnumAcidity;
import binnie.botany.api.gardening.EnumMoisture;
import forestry.api.genetics.IAlleleSpeciesBuilder;

public interface IAlleleFlowerSpeciesBuilder extends IAlleleSpeciesBuilder {
	@Override
	IAlleleFlowerSpecies build();

	IAlleleFlowerSpeciesBuilder setPH(EnumAcidity acidity);

	IAlleleFlowerSpeciesBuilder setMoisture(EnumMoisture moisture);
}
