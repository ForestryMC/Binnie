package binnie.extrabees.genetics;

import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IClassification;
import forestry.apiculture.genetics.alleles.AlleleEffect;
import forestry.core.genetics.IBranchDefinition;
import forestry.core.genetics.alleles.AlleleHelper;
import forestry.core.genetics.alleles.EnumAllele;

import java.util.Arrays;
import java.util.Locale;

public enum ExtraBeeBranchDefinition implements IBranchDefinition {
	BARREN("Vacapis") {
		
	},
	HOSTILE("Infenapis"),
	ROCKY("Monapis"),
	METALLIC("Lamminapis"),
	METALLIC2("Metalapis"),
	ALLOY("Allapis"),
	PRECIOUS("Pluriapis"),
	MINERAL("Niphapis"),
	GEMSTONE("Gemmapis"),
	NUCLEAR("Levapis"),
	HISTORIC("Priscapis"),
	FOSSILIZED("Fosiapis"),
	REFINED("Petrapis"),
	AQUATIC("Aquapis"),
	SACCHARINE("Sacchapis"),
	CLASSICAL("Grecapis"),
	VOLCANIC("Irrapis"),
	VIRULENT("Virapis"),
	VISCOUS("Viscapis"),
	CAUSTIC("Morbapis"),
	ENERGETIC("Incitapis"),
	FARMING("Agriapis"),
	SHADOW("Pullapis"),
	PRIMARY("Primapis"),
	SECONDARY("Secapis"),
	TERTIARY("Tertiapis"),
	FTB("Eftebeapis"),
	QUANTUM("Quantapis"),
	BOTANIA("Botaniapis");

	private final IClassification branch;

	ExtraBeeBranchDefinition(String scientific) {
		branch = BeeManager.beeFactory.createBranch(name().toLowerCase(Locale.ENGLISH), scientific);
	}

	protected void setBranchProperties(IAllele[] template) {
		// ignored
	}

	@Override
	public final IAllele[] getTemplate() {
		IAllele[] template = getDefaultTemplate();
		setBranchProperties(template);
		return template;
	}

	@Override
	public final IClassification getBranch() {
		return branch;
	}

	private static IAllele[] defaultTemplate;

	private static IAllele[] getDefaultTemplate() {
		if (defaultTemplate == null) {
			defaultTemplate = new IAllele[EnumBeeChromosome.values().length];
			AlleleHelper.instance.set(defaultTemplate, EnumBeeChromosome.SPEED, EnumAllele.Speed.SLOWEST);
			AlleleHelper.instance.set(defaultTemplate, EnumBeeChromosome.LIFESPAN, EnumAllele.Lifespan.SHORTER);
			AlleleHelper.instance.set(defaultTemplate, EnumBeeChromosome.FERTILITY, EnumAllele.Fertility.NORMAL);
			AlleleHelper.instance.set(defaultTemplate, EnumBeeChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.NONE);
			AlleleHelper.instance.set(defaultTemplate, EnumBeeChromosome.NOCTURNAL, false);
			AlleleHelper.instance.set(defaultTemplate, EnumBeeChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.NONE);
			AlleleHelper.instance.set(defaultTemplate, EnumBeeChromosome.TOLERANT_FLYER, false);
			AlleleHelper.instance.set(defaultTemplate, EnumBeeChromosome.CAVE_DWELLING, false);
			AlleleHelper.instance.set(defaultTemplate, EnumBeeChromosome.FLOWER_PROVIDER, EnumAllele.Flowers.VANILLA);
			AlleleHelper.instance.set(defaultTemplate, EnumBeeChromosome.FLOWERING, EnumAllele.Flowering.SLOWEST);
			AlleleHelper.instance.set(defaultTemplate, EnumBeeChromosome.TERRITORY, EnumAllele.Territory.AVERAGE);
			AlleleHelper.instance.set(defaultTemplate, EnumBeeChromosome.EFFECT, AlleleEffect.effectNone);
		}
		return Arrays.copyOf(defaultTemplate, defaultTemplate.length);
	}
}
