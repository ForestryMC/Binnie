package binnie.extrabees.genetics;

import java.util.Arrays;
import java.util.Locale;

import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IClassification;
import forestry.apiculture.genetics.alleles.AlleleEffects;
import forestry.core.genetics.IBranchDefinition;
import forestry.core.genetics.alleles.EnumAllele;

import binnie.extrabees.genetics.effect.ExtraBeesEffect;
import binnie.core.genetics.AlleleHelper;

public enum ExtraBeeBranchDefinition implements IBranchDefinition {
	BARREN("Vacapis") {
		@Override
		protected void setBranchProperties(IAllele[] template) {
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.NEVER_SLEEPS, true);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.SPEED, EnumAllele.Speed.SLOWER);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.LIFESPAN, EnumAllele.Lifespan.SHORT);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.UP_1);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.FLOWER_PROVIDER, AlleleHelper.getAllele(ExtraBeesFlowers.DEAD.getUID()));
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.FERTILITY, EnumAllele.Fertility.LOW);
		}
	},
	HOSTILE("Infenapis") {
		@Override
		protected void setBranchProperties(IAllele[] template) {
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.NEVER_SLEEPS, true);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.CAVE_DWELLING, true);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.TOLERATES_RAIN, true);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.SPEED, EnumAllele.Speed.SLOWER);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.LIFESPAN, EnumAllele.Lifespan.SHORT);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.UP_1);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.FLOWER_PROVIDER, AlleleHelper.getAllele(ExtraBeesFlowers.DEAD.getUID()));
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.FERTILITY, EnumAllele.Fertility.LOW);
		}
	},
	ROCKY("Monapis") {
		@Override
		protected void setBranchProperties(IAllele[] template) {
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.NEVER_SLEEPS, true);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.CAVE_DWELLING, true);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.TOLERATES_RAIN, true);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.FERTILITY, EnumAllele.Fertility.LOW);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.LIFESPAN, EnumAllele.Lifespan.SHORT);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.FLOWER_PROVIDER, AlleleHelper.getAllele(ExtraBeesFlowers.ROCK.getUID()));
		}
	},
	METALLIC("Lamminapis") {
		@Override
		protected void setBranchProperties(IAllele[] template) {
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.NEVER_SLEEPS, true);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.CAVE_DWELLING, true);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.TOLERATES_RAIN, true);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.FERTILITY, EnumAllele.Fertility.LOW);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.LIFESPAN, EnumAllele.Lifespan.SHORT);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.FLOWER_PROVIDER, AlleleHelper.getAllele(ExtraBeesFlowers.ROCK.getUID()));
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_2);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.BOTH_2);
		}
	},
	METALLIC2("Metalapis") {
		@Override
		protected void setBranchProperties(IAllele[] template) {
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.NEVER_SLEEPS, true);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.CAVE_DWELLING, true);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.TOLERATES_RAIN, true);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.FERTILITY, EnumAllele.Fertility.LOW);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.LIFESPAN, EnumAllele.Lifespan.SHORT);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.FLOWER_PROVIDER, AlleleHelper.getAllele(ExtraBeesFlowers.ROCK.getUID()));
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_2);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.BOTH_2);
		}
	},
	ALLOY("Allapis"),
	PRECIOUS("Pluriapis") {
		@Override
		protected void setBranchProperties(IAllele[] template) {
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.NEVER_SLEEPS, true);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.CAVE_DWELLING, true);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.TOLERATES_RAIN, true);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.FERTILITY, EnumAllele.Fertility.LOW);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.LIFESPAN, EnumAllele.Lifespan.SHORT);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.FLOWER_PROVIDER, AlleleHelper.getAllele(ExtraBeesFlowers.ROCK.getUID()));
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_2);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.BOTH_2);
		}
	},
	MINERAL("Niphapis") {
		@Override
		protected void setBranchProperties(IAllele[] template) {
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.NEVER_SLEEPS, true);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.CAVE_DWELLING, true);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.TOLERATES_RAIN, true);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.FERTILITY, EnumAllele.Fertility.LOW);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.LIFESPAN, EnumAllele.Lifespan.SHORT);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.FLOWER_PROVIDER, AlleleHelper.getAllele(ExtraBeesFlowers.ROCK.getUID()));
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_2);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.BOTH_2);
		}
	},
	GEMSTONE("Gemmapis") {
		@Override
		protected void setBranchProperties(IAllele[] template) {
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.NEVER_SLEEPS, true);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.CAVE_DWELLING, true);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.TOLERATES_RAIN, true);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.FERTILITY, EnumAllele.Fertility.LOW);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.LIFESPAN, EnumAllele.Lifespan.SHORT);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.FLOWER_PROVIDER, AlleleHelper.getAllele(ExtraBeesFlowers.ROCK.getUID()));
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_2);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.BOTH_2);
		}
	},
	NUCLEAR("Levapis") {
		@Override
		protected void setBranchProperties(IAllele[] template) {
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.NEVER_SLEEPS, true);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.CAVE_DWELLING, true);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.TOLERATES_RAIN, true);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.FLOWER_PROVIDER, AlleleHelper.getAllele(ExtraBeesFlowers.ROCK.getUID()));
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_2);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.BOTH_2);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.FERTILITY, EnumAllele.Fertility.LOW);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.LIFESPAN, EnumAllele.Lifespan.SHORTEST);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.EFFECT, AlleleHelper.getAllele(ExtraBeesEffect.RADIOACTIVE.getUID()));
		}
	},
	HISTORIC("Priscapis") {
		@Override
		protected void setBranchProperties(IAllele[] template) {
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.SPEED, EnumAllele.Speed.SLOWER);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.LIFESPAN, EnumAllele.Lifespan.ELONGATED);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.FLOWERING, EnumAllele.Flowering.SLOW);
		}
	},
	FOSSILIZED("Fosiapis") {
		@Override
		protected void setBranchProperties(IAllele[] template) {
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.SPEED, EnumAllele.Speed.SLOWER);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.LIFESPAN, EnumAllele.Lifespan.NORMAL);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.FLOWERING, EnumAllele.Flowering.SLOW);
		}
	},
	REFINED("Petrapis") {
		@Override
		protected void setBranchProperties(IAllele[] template) {
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.SPEED, EnumAllele.Speed.SLOWER);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.LIFESPAN, EnumAllele.Lifespan.NORMAL);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.FLOWERING, EnumAllele.Flowering.SLOW);
		}
	},
	AQUATIC("Aquapis") {
		@Override
		protected void setBranchProperties(IAllele[] template) {
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.TOLERATES_RAIN, true);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.FLOWERING, EnumAllele.Flowering.SLOWEST);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.FLOWER_PROVIDER, AlleleHelper.getAllele(ExtraBeesFlowers.WATER.getUID()));
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.EFFECT, AlleleHelper.getAllele(ExtraBeesEffect.WATER.getUID()));
		}
	},
	SACCHARINE("Sacchapis") {
		@Override
		protected void setBranchProperties(IAllele[] template) {
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.FLOWER_PROVIDER, AlleleHelper.getAllele(ExtraBeesFlowers.SUGAR.getUID()));
		}
	},
	CLASSICAL("Grecapis"){
		@Override
		protected void setBranchProperties(IAllele[] template) {
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.SPEED, EnumAllele.Speed.SLOWER);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.LIFESPAN, EnumAllele.Lifespan.SHORT);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.FLOWERING, EnumAllele.Flowering.SLOW);
		}
	},
	VOLCANIC("Irrapis") {
		@Override
		protected void setBranchProperties(IAllele[] template) {
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.SPEED, EnumAllele.Speed.SLOWER);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.LIFESPAN, EnumAllele.Lifespan.NORMAL);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.EFFECT, AlleleHelper.getAllele(ExtraBeesEffect.METEOR.getUID()));
		}
	},
	VIRULENT("Virapis") {
		@Override
		protected void setBranchProperties(IAllele[] template) {
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.SPEED, EnumAllele.Speed.SLOWER);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.LIFESPAN, EnumAllele.Lifespan.SHORT);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.UP_1);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.UP_1);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.FLOWER_PROVIDER, EnumAllele.Flowers.JUNGLE);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.EFFECT, AlleleEffects.effectMiasmic);
		}
	},
	VISCOUS("Viscapis") {
		@Override
		protected void setBranchProperties(IAllele[] template) {
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.UP_1);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.UP_1);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.FLOWER_PROVIDER, EnumAllele.Flowers.JUNGLE);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.SPEED, EnumAllele.Speed.SLOW);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.EFFECT, AlleleHelper.getAllele(ExtraBeesEffect.ECTOPLASM.getUID()));
		}
	},
	CAUSTIC("Morbapis") {
		@Override
		protected void setBranchProperties(IAllele[] template) {
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.UP_1);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.UP_1);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.FLOWER_PROVIDER, EnumAllele.Flowers.JUNGLE);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.SPEED, EnumAllele.Speed.FAST);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.FLOWERING, EnumAllele.Flowering.AVERAGE);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.EFFECT, AlleleHelper.getAllele(ExtraBeesEffect.ACID.getUID()));
		}
	},
	ENERGETIC("Incitapis") {
		@Override
		protected void setBranchProperties(IAllele[] template) {
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.CAVE_DWELLING, true);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.FLOWER_PROVIDER, AlleleHelper.getAllele(ExtraBeesFlowers.REDSTONE.getUID()));
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.EFFECT, AlleleHelper.getAllele(ExtraBeesEffect.LIGHTNING.getUID()));
		}
	},
	FARMING("Agriapis"),
	SHADOW("Pullapis") {
		@Override
		protected void setBranchProperties(IAllele[] template) {
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.NEVER_SLEEPS, true);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.SPEED, EnumAllele.Speed.SLOWER);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.LIFESPAN, EnumAllele.Lifespan.NORMAL);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.EFFECT, AlleleHelper.getAllele(ExtraBeesEffect.BLINDNESS.getUID()));
		}
	},
	PRIMARY("Primapis"),
	SECONDARY("Secapis"),
	TERTIARY("Tertiapis"),
	FTB("Eftebeapis") {
		@Override
		protected void setBranchProperties(IAllele[] template) {
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.SPEED, EnumAllele.Speed.SLOWER);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.LIFESPAN, EnumAllele.Lifespan.NORMAL);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.EFFECT, AlleleEffects.effectBeatific);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.FERTILITY, EnumAllele.Fertility.MAXIMUM);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.FLOWERING, EnumAllele.Flowering.MAXIMUM);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.TERRITORY, EnumAllele.Territory.LARGEST);
		}
	},
	QUANTUM("Quantapis") {
		@Override
		protected void setBranchProperties(IAllele[] template) {
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.FERTILITY, EnumAllele.Fertility.LOW);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.SPEED, EnumAllele.Speed.SLOWER);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.LIFESPAN, EnumAllele.Lifespan.LONGER);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.UP_1);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.TERRITORY, EnumAllele.Territory.LARGE);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.FLOWER_PROVIDER, EnumAllele.Flowers.END);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.NEVER_SLEEPS, true);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.EFFECT, AlleleHelper.getAllele(ExtraBeesEffect.GRAVITY.getUID()));
		}
	},
	BOTANIA("Botaniapis") {
		@Override
		protected void setBranchProperties(IAllele[] template) {
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.SPEED, EnumAllele.Speed.SLOWER);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.LIFESPAN, EnumAllele.Lifespan.SHORT);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.FLOWERING, EnumAllele.Flowering.SLOW);
			AlleleHelper.getInstance().set(template, EnumBeeChromosome.FLOWER_PROVIDER, AlleleHelper.getAllele(ExtraBeesFlowers.MYSTICAL.getUID()));
		}
	};

	private static IAllele[] defaultTemplate;

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

	private static IAllele[] getDefaultTemplate() {
		if (defaultTemplate == null) {
			defaultTemplate = new IAllele[EnumBeeChromosome.values().length];

			AlleleHelper.getInstance().set(defaultTemplate, EnumBeeChromosome.SPEED, EnumAllele.Speed.SLOWEST);
			AlleleHelper.getInstance().set(defaultTemplate, EnumBeeChromosome.LIFESPAN, EnumAllele.Lifespan.SHORTER);
			AlleleHelper.getInstance().set(defaultTemplate, EnumBeeChromosome.FERTILITY, EnumAllele.Fertility.NORMAL);
			AlleleHelper.getInstance().set(defaultTemplate, EnumBeeChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.NONE);
			AlleleHelper.getInstance().set(defaultTemplate, EnumBeeChromosome.NEVER_SLEEPS, false);
			AlleleHelper.getInstance().set(defaultTemplate, EnumBeeChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.NONE);
			AlleleHelper.getInstance().set(defaultTemplate, EnumBeeChromosome.TOLERATES_RAIN, false);
			AlleleHelper.getInstance().set(defaultTemplate, EnumBeeChromosome.CAVE_DWELLING, false);
			AlleleHelper.getInstance().set(defaultTemplate, EnumBeeChromosome.FLOWER_PROVIDER, EnumAllele.Flowers.VANILLA);
			AlleleHelper.getInstance().set(defaultTemplate, EnumBeeChromosome.FLOWERING, EnumAllele.Flowering.SLOWEST);
			AlleleHelper.getInstance().set(defaultTemplate, EnumBeeChromosome.TERRITORY, EnumAllele.Territory.AVERAGE);
			AlleleHelper.getInstance().set(defaultTemplate, EnumBeeChromosome.EFFECT, AlleleEffects.effectNone);
		}
		return Arrays.copyOf(defaultTemplate, defaultTemplate.length);
	}
}
