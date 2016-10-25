package binnie.botany.genetics;

import binnie.botany.api.EnumFlowerChromosome;
import forestry.api.genetics.IAllele;
import forestry.core.genetics.alleles.AlleleHelper;
import forestry.core.genetics.alleles.EnumAllele;

public class FlowerTemplates {
    public static IAllele[] getDefaultTemplate() {
        final IAllele[] alleles = new IAllele[EnumFlowerChromosome.values().length];
        AlleleHelper.instance.set(alleles, EnumFlowerChromosome.SPECIES, FlowerDefinition.Dandelion.getSpecies());
        AlleleHelper.instance.set(alleles, EnumFlowerChromosome.PRIMARY, EnumFlowerColor.Red);
        AlleleHelper.instance.set(alleles, EnumFlowerChromosome.SECONDARY, EnumFlowerColor.Red);
        AlleleHelper.instance.set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.NORMAL);
        AlleleHelper.instance.set(alleles, EnumFlowerChromosome.TERRITORY, EnumAllele.Territory.AVERAGE);
        AlleleHelper.instance.set(alleles, EnumFlowerChromosome.EFFECT, ModuleGenetics.alleleEffectNone);
        AlleleHelper.instance.set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.NORMAL);
        AlleleHelper.instance.set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.NONE);
        AlleleHelper.instance.set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.NONE);
        AlleleHelper.instance.set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.NONE);
        AlleleHelper.instance.set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.AVERAGE);
        AlleleHelper.instance.set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.Green);
        return alleles;
    }
}
