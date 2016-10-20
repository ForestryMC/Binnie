package binnie.botany.genetics;

import binnie.botany.api.EnumFlowerChromosome;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;

public class FlowerTemplates {
    public static IAllele[] getDefaultTemplate() {
        final IAllele[] alleles = new IAllele[EnumFlowerChromosome.values().length];
        alleles[EnumFlowerChromosome.SPECIES.ordinal()] = FlowerSpecies.Poppy;
        alleles[EnumFlowerChromosome.PRIMARY.ordinal()] = EnumFlowerColor.Red.getAllele();
        alleles[EnumFlowerChromosome.SECONDARY.ordinal()] = EnumFlowerColor.Red.getAllele();
        alleles[EnumFlowerChromosome.FERTILITY.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.fertilityNormal");
        alleles[EnumFlowerChromosome.TERRITORY.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.territoryDefault");
        alleles[EnumFlowerChromosome.EFFECT.ordinal()] = ModuleGenetics.alleleEffectNone;
        alleles[EnumFlowerChromosome.LIFESPAN.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.lifespanNormal");
        alleles[EnumFlowerChromosome.TEMPERATURE_TOLERANCE.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.toleranceNone");
        alleles[EnumFlowerChromosome.HUMIDITY_TOLERANCE.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.toleranceNone");
        alleles[EnumFlowerChromosome.PH_TOLERANCE.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.toleranceNone");
        alleles[EnumFlowerChromosome.SAPPINESS.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.sappinessAverage");
        alleles[EnumFlowerChromosome.STEM.ordinal()] = EnumFlowerColor.Green.getAllele();
        return alleles;
    }
}
