package binnie.botany.api;

import forestry.api.genetics.*;

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
        return this.toString().toLowerCase();
    }

    @Override
    public ISpeciesRoot getSpeciesRoot() {
        return AlleleManager.alleleRegistry.getSpeciesRoot("rootFlowers");
    }
}
