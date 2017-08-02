package binnie.botany.genetics;

import forestry.api.genetics.IAllele;
import forestry.core.genetics.alleles.AlleleHelper;
import forestry.core.genetics.alleles.EnumAllele;

import binnie.botany.api.EnumFlowerChromosome;

public class FlowerTemplates {
	public static IAllele[] getDefaultTemplate() {
		IAllele[] alleles = new IAllele[EnumFlowerChromosome.values().length];
		AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.SPECIES, FlowerDefinition.Poppy.getSpecies());
		AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.PRIMARY, EnumFlowerColor.Red.getFlowerColorAllele());
		AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.SECONDARY, EnumFlowerColor.Red.getFlowerColorAllele());
		AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.NORMAL);
		AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.TERRITORY, EnumAllele.Territory.AVERAGE);
		AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.EFFECT, ModuleGenetics.alleleEffectNone);
		AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.NORMAL);
		AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.NONE);
		AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.NONE);
		AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.NONE);
		AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.AVERAGE);
		AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.Green.getFlowerColorAllele());
		return alleles;
	}
}
