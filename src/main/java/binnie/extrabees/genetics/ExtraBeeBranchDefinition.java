package binnie.extrabees.genetics;

import binnie.extrabees.genetics.effect.ExtraBeesEffect;
import binnie.genetics.genetics.AlleleHelper;
import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IClassification;
import forestry.apiculture.genetics.alleles.AlleleEffect;
import forestry.core.genetics.IBranchDefinition;
import forestry.core.genetics.alleles.EnumAllele;

import java.util.Arrays;
import java.util.Locale;

public enum ExtraBeeBranchDefinition implements IBranchDefinition {
	BARREN("Vacapis") {
		@Override
		protected void setBranchProperties(IAllele[] template) {
			AlleleHelper.instance.set(template, EnumBeeChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
			AlleleHelper.instance.set(template, EnumBeeChromosome.NOCTURNAL, true);
			AlleleHelper.instance.set(template, EnumBeeChromosome.SPEED, EnumAllele.Speed.SLOWER);
			AlleleHelper.instance.set(template, EnumBeeChromosome.LIFESPAN, EnumAllele.Lifespan.SHORT);
			AlleleHelper.instance.set(template, EnumBeeChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.UP_1);
			AlleleHelper.instance.set(template, EnumBeeChromosome.FLOWER_PROVIDER, AlleleHelper.getAllele(ExtraBeesFlowers.DEAD.getUID()));
			AlleleHelper.instance.set(template, EnumBeeChromosome.FERTILITY, EnumAllele.Fertility.LOW);
		}
	},
	HOSTILE("Infenapis") {
		@Override
		protected void setBranchProperties(IAllele[] template) {
			AlleleHelper.instance.set(template, EnumBeeChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
			AlleleHelper.instance.set(template, EnumBeeChromosome.NOCTURNAL, true);
			AlleleHelper.instance.set(template, EnumBeeChromosome.CAVE_DWELLING, true);
			AlleleHelper.instance.set(template, EnumBeeChromosome.TOLERANT_FLYER, true);
			AlleleHelper.instance.set(template, EnumBeeChromosome.SPEED, EnumAllele.Speed.SLOWER);
			AlleleHelper.instance.set(template, EnumBeeChromosome.LIFESPAN, EnumAllele.Lifespan.SHORT);
			AlleleHelper.instance.set(template, EnumBeeChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.UP_1);
			AlleleHelper.instance.set(template, EnumBeeChromosome.FLOWER_PROVIDER, AlleleHelper.getAllele(ExtraBeesFlowers.DEAD.getUID()));
			AlleleHelper.instance.set(template, EnumBeeChromosome.FERTILITY, EnumAllele.Fertility.LOW);
		}
	},
	ROCKY("Monapis") {
		@Override
		protected void setBranchProperties(IAllele[] template) {
			AlleleHelper.instance.set(template, EnumBeeChromosome.NOCTURNAL, true);
			AlleleHelper.instance.set(template, EnumBeeChromosome.CAVE_DWELLING, true);
			AlleleHelper.instance.set(template, EnumBeeChromosome.TOLERANT_FLYER, true);
			AlleleHelper.instance.set(template, EnumBeeChromosome.FERTILITY, EnumAllele.Fertility.LOW);
			AlleleHelper.instance.set(template, EnumBeeChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
			AlleleHelper.instance.set(template, EnumBeeChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
			AlleleHelper.instance.set(template, EnumBeeChromosome.LIFESPAN, EnumAllele.Lifespan.SHORT);
			AlleleHelper.instance.set(template, EnumBeeChromosome.FLOWER_PROVIDER, AlleleHelper.getAllele(ExtraBeesFlowers.ROCK.getUID()));
		}
	},
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
	AQUATIC("Aquapis") {
		@Override
		protected void setBranchProperties(IAllele[] template) {
			AlleleHelper.instance.set(template, EnumBeeChromosome.TOLERANT_FLYER, true);
			AlleleHelper.instance.set(template, EnumBeeChromosome.FLOWERING, EnumAllele.Flowering.SLOWEST);
			AlleleHelper.instance.set(template, EnumBeeChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
			AlleleHelper.instance.set(template, EnumBeeChromosome.FLOWER_PROVIDER, AlleleHelper.getAllele(ExtraBeesFlowers.WATER.getUID()));
			AlleleHelper.instance.set(template, EnumBeeChromosome.EFFECT, AlleleHelper.getAllele(ExtraBeesEffect.WATER.getUID()));
		}
	},
	SACCHARINE("Sacchapis"),
	CLASSICAL("Grecapis"),
	VOLCANIC("Irrapis"),
	VIRULENT("Virapis"),
	VISCOUS("Viscapis"),
	CAUSTIC("Morbapis"),
	ENERGETIC("Incitapis") {
		@Override
		protected void setBranchProperties(IAllele[] template) {
			AlleleHelper.instance.set(template, EnumBeeChromosome.CAVE_DWELLING, true);
			AlleleHelper.instance.set(template, EnumBeeChromosome.FLOWER_PROVIDER, AlleleHelper.getAllele(ExtraBeesFlowers.REDSTONE.getUID()));
			AlleleHelper.instance.set(template, EnumBeeChromosome.EFFECT, AlleleHelper.getAllele(ExtraBeesEffect.LIGHTNING.getUID()));
		}
	},
	FARMING("Agriapis"),
	SHADOW("Pullapis") {
		@Override
		protected void setBranchProperties(IAllele[] template) {
			AlleleHelper.instance.set(template, EnumBeeChromosome.NOCTURNAL, true);
		}
	},
	PRIMARY("Primapis"),
	SECONDARY("Secapis"),
	TERTIARY("Tertiapis"),
	FTB("Eftebeapis"),
	QUANTUM("Quantapis"),
	BOTANIA("Botaniapis");

	private final IClassification branch;

	ExtraBeeBranchDefinition(String scientific) {
		branch = BeeManager.beeFactory.createBranch(name().toLowerCase(Locale.ENGLISH), scientific);
		IClassification parent = AlleleManager.alleleRegistry.getClassification("family.apidae");
		if (parent != null) {
			parent.addMemberGroup(branch);
		}
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
