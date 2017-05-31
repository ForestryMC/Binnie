package binnie.botany.api;

import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleArea;
import forestry.api.genetics.IAlleleFloat;
import forestry.api.genetics.IAlleleInteger;
import forestry.api.genetics.IAlleleTolerance;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.ISpeciesRoot;

import java.util.Locale;

public enum EnumFlowerChromosome implements IChromosomeType {
	SPECIES(IAlleleFlowerSpecies.class),
	PRIMARY(IAlleleInteger.class),
	SECONDARY(IAlleleInteger.class),
	FERTILITY(IAlleleInteger.class),
	TERRITORY(IAlleleArea.class),
	EFFECT(IAlleleFlowerEffect.class),
	LIFESPAN(IAlleleInteger.class),
	TEMPERATURE_TOLERANCE(IAlleleTolerance.class),
	HUMIDITY_TOLERANCE(IAlleleTolerance.class),
	PH_TOLERANCE(IAlleleTolerance.class),
	SAPPINESS(IAlleleFloat.class),
	STEM(IAlleleInteger.class);

	private Class<? extends IAllele> cls;

	EnumFlowerChromosome(final Class<? extends IAllele> cls) {
		this.cls = cls;
	}

	@Override
	public Class<? extends IAllele> getAlleleClass() {
		return this.cls;
	}

	@Override
	public String getName() {
		return this.toString().toLowerCase(Locale.ENGLISH);
	}

	@Override
	public ISpeciesRoot getSpeciesRoot() {
		return AlleleManager.alleleRegistry.getSpeciesRoot("rootFlowers");
	}
}
