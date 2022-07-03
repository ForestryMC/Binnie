package binnie.botany.genetics;

import binnie.botany.api.EnumFlowerChromosome;
import binnie.genetics.genetics.AlleleHelper;
import forestry.api.genetics.EnumTolerance;
import forestry.api.genetics.IAllele;
import forestry.core.genetics.alleles.EnumAllele;

public class FlowerTemplates {
    public static IAllele[] getDefaultTemplate() {
        IAllele[] alleles = new IAllele[EnumFlowerChromosome.values().length];
        alleles[EnumFlowerChromosome.SPECIES.ordinal()] = FlowerSpecies.POPPY;
        alleles[EnumFlowerChromosome.PRIMARY.ordinal()] = AlleleHelper.getAllele(EnumFlowerColor.RED);
        alleles[EnumFlowerChromosome.SECONDARY.ordinal()] = AlleleHelper.getAllele(EnumFlowerColor.RED);
        alleles[EnumFlowerChromosome.FERTILITY.ordinal()] = AlleleHelper.getAllele(EnumAllele.Fertility.NORMAL);
        alleles[EnumFlowerChromosome.TERRITORY.ordinal()] = AlleleHelper.getAllele(EnumAllele.Territory.AVERAGE);
        alleles[EnumFlowerChromosome.EFFECT.ordinal()] = ModuleGenetics.alleleEffectNone;
        alleles[EnumFlowerChromosome.LIFESPAN.ordinal()] = AlleleHelper.getAllele(EnumAllele.Lifespan.NORMAL);
        alleles[EnumFlowerChromosome.TEMPERATURE_TOLERANCE.ordinal()] = AlleleHelper.getAllele(EnumTolerance.NONE);
        alleles[EnumFlowerChromosome.HUMIDITY_TOLERANCE.ordinal()] = AlleleHelper.getAllele(EnumTolerance.NONE);
        alleles[EnumFlowerChromosome.PH_TOLERANCE.ordinal()] = AlleleHelper.getAllele(EnumTolerance.NONE);
        alleles[EnumFlowerChromosome.SAPPINESS.ordinal()] = AlleleHelper.getAllele(EnumAllele.Sappiness.AVERAGE);
        alleles[EnumFlowerChromosome.STEM.ordinal()] = AlleleHelper.getAllele(EnumFlowerColor.GREEN);
        return alleles;
    }
}
