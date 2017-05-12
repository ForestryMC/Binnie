package binnie.extrabees.genetics;

import binnie.genetics.genetics.AlleleHelper;
import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IAlleleBeeSpeciesCustom;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.genetics.IAllele;
import forestry.apiculture.genetics.Bee;
import forestry.apiculture.genetics.BeeBranchDefinition;
import forestry.apiculture.genetics.BeeDefinition;
import forestry.apiculture.genetics.BeeVariation;
import forestry.apiculture.genetics.IBeeDefinition;
import forestry.core.genetics.IBranchDefinition;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.text.WordUtils;

import java.awt.Color;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Locale;

public enum ExtraBeeDefinition implements IBeeDefinition {
	/* BARREN BRANCH */
	ARID(ExtraBeeBranchDefinition.BARREN, "aridus", true, new Color(0xbee854), new Color(0xcbe374)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*AlleleHelper.instance.set(template, EnumBeeChromosome.SPEED, EnumAllele.Speed.FAST);
			AlleleHelper.instance.set(template, EnumBeeChromosome.LIFESPAN, EnumAllele.Lifespan.SHORTEST);
			ExtraBeeDefinition.ARID
				.importTemplate(ForestryAllele.BeeSpecies.Modest)
				.addProduct(binnie.extrabees.products.EnumHoneyComb.BARREN, 0.30f)
				.setHumidity(EnumHumidity.ARID)
				.setFlowerProvider(ExtraBeesFlowers.DEAD.getUID())
				.setTemperatureTolerance(EnumTolerance.UP_1)*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.MEADOWS, BeeDefinition.FRUGAL, 10);
			registerMutation(BeeDefinition.FOREST, BeeDefinition.FRUGAL, 10);
		}
	},
	BARREN(ExtraBeeBranchDefinition.BARREN, "infelix", true, new Color(0xe0d263), new Color(0xcbe374)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.BARREN
			.importTemplate(ExtraBeeDefinition.ARID)
			.setFertility(EnumAllele.Fertility.LOW)
			.addProduct(EnumHoneyComb.BARREN, 0.30f);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.COMMON, BARREN, 10);
		}
	},
	DESOLATE(ExtraBeeBranchDefinition.BARREN, "desolo", false, new Color(0xd1b890), new Color(0xcbe374)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.DESOLATE
			.addProduct(EnumHoneyComb.BARREN, 0.30f)
			.importTemplate(ExtraBeeDefinition.BARREN)
			.setEffect(ExtraBeesEffect.HUNGER.getUID())
			.setNocturnal()
			.setHasEffect(true);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(ARID, BARREN, 10);
		}
	},
	DECOMPOSING(ExtraBeeBranchDefinition.BARREN, "aegrus", true, new Color(0x523711), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.DECOMPOSING
			.importTemplate(ExtraBeeDefinition.BARREN)
			.addProduct(EnumHoneyComb.BARREN, 0.30f)
			.addSpecialty(EnumHoneyComb.COMPOST, 0.08f);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.MARSHY, BARREN, 15);
		}
	},
	GNAWING(ExtraBeeBranchDefinition.BARREN, "apica", true, new Color(0xe874b0), new Color(0xcbe374)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.GNAWING
			.importTemplate(ExtraBeeDefinition.BARREN)
			.setFlowerProvider(ExtraBeesFlowers.WOOD.getUID())
			.addProduct(EnumHoneyComb.BARREN, 0.25f)
			.addSpecialty(EnumHoneyComb.SAWDUST, 0.25f);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.FOREST, BARREN, 15);
		}
	},

	/* HOSTILE BRANCH */
	ROTTEN(ExtraBeeBranchDefinition.HOSTILE, "caries", true, new Color(0xbfe0b6), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.ROTTEN
			.importTemplate(ExtraBeeDefinition.DESOLATE)
			.setNocturnal()
			.setCaveDwelling()
			.setTolerantFlyer()
			.setEffect(ExtraBeesEffect.SPAWN_ZOMBIE.getUID())
			.addProduct(EnumHoneyComb.BARREN, 0.30f)
			.addSpecialty(EnumHoneyComb.ROTTEN, 0.10f);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.MEADOWS, DESOLATE, 15);
		}
	},
	BONE(ExtraBeeBranchDefinition.HOSTILE, "os", true, new Color(0xe9ede8), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.BONE
			.importTemplate(ExtraBeeDefinition.ROTTEN)
			.addProduct(EnumHoneyComb.BARREN, 0.30f)
			.addSpecialty(EnumHoneyComb.BONE, 0.10f)
			.setEffect(ExtraBeesEffect.SPAWN_SKELETON.getUID());*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.FOREST, DESOLATE, 15);
		}
	},
	CREEPER(ExtraBeeBranchDefinition.HOSTILE, "erepo", true, new Color(0x2ce615), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.CREEPER
			.importTemplate(ExtraBeeDefinition.ROTTEN)
			.setAllDay()
			.addProduct(EnumHoneyComb.BARREN, 0.30f)
			.addSpecialty(ItemHoneyComb.VanillaComb.POWDERY.get(), 0.08f)
			.setEffect(ExtraBeesEffect.SPAWN_CREEPER.getUID());*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.MODEST, DESOLATE, 15);
		}
	},

	/* ROCKY BRANCH */
	ROCK(ExtraBeeBranchDefinition.ROCKY, "saxum", true, new Color(0xa8a8a8), new Color(0x999999)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.ROCK
			.addProduct(EnumHoneyComb.STONE, 0.30f)
			.setIsSecret(false)
			.setAllDay()
			.setCaveDwelling()
			.setTolerantFlyer()
			.setTemperatureTolerance(EnumTolerance.BOTH_1)
			.setHumidityTolerance(EnumTolerance.BOTH_1)
			.setFlowerProvider(ExtraBeesFlowers.ROCK.getUID())
			.setFertility(EnumAllele.Fertility.LOW)
			.setLifespan(EnumAllele.Lifespan.SHORT)*/
		}
	},
	STONE(ExtraBeeBranchDefinition.ROCKY, "lapis", false, new Color(0x757575), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.STONE
			.addProduct(EnumHoneyComb.STONE, 0.30f)
			.importTemplate(ExtraBeeDefinition.ROCK)
			.setSecondaryColor(rockBody);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.DILIGENT, ROCK, 12);
		}
	},
	GRANITE(ExtraBeeBranchDefinition.ROCKY, "granum", true, new Color(0x695555), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.GRANITE
			.addProduct(EnumHoneyComb.STONE, 0.30f)
			.importTemplate(ExtraBeeDefinition.STONE)
			.setTemperatureTolerance(EnumTolerance.BOTH_2)
			.setHumidityTolerance(EnumTolerance.BOTH_2)
			.setSecondaryColor(rockBody);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.UNWEARY, STONE, 10);
		}
	},
	MINERAL(ExtraBeeBranchDefinition.ROCKY, "minerale", true, new Color(0x6e757d), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.MINERAL
			.addProduct(EnumHoneyComb.STONE, 0.30f)
			.importTemplate(ExtraBeeDefinition.GRANITE)
			.setSecondaryColor(rockBody);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.INDUSTRIOUS, GRANITE, 6);
		}
	},

	/* METALLIC BRANCH */
	COPPER(ExtraBeeBranchDefinition.METALLIC, "cuprous", true, new Color(0xd16308), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.COPPER
			.addProduct(EnumHoneyComb.STONE, 0.20f)
			.addSpecialty(EnumHoneyComb.COPPER, 0.06f)
			.importTemplate(ExtraBeeDefinition.MINERAL)
			.setSecondaryColor(rockBody);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.WINTRY, MINERAL, 5);
			registerMutation(BeeDefinition.MODEST, MINERAL, 5);
		}
	},
	TIN(ExtraBeeBranchDefinition.METALLIC, "stannus", true, new Color(0xbdb1bd), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.TIN.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeeDefinition.TIN.addSpecialty(EnumHoneyComb.TIN, 0.06f);
		ExtraBeeDefinition.TIN.importTemplate(ExtraBeeDefinition.MINERAL);
		ExtraBeeDefinition.TIN.setSecondaryColor(rockBody);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.MARSHY, MINERAL, 5);
			registerMutation(BeeDefinition.TROPICAL, MINERAL, 5);
		}
	},
	IRON(ExtraBeeBranchDefinition.METALLIC, "ferrous", false, new Color(0xa87058), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.IRON.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeeDefinition.IRON.addSpecialty(EnumHoneyComb.IRON, 0.05f);
		ExtraBeeDefinition.IRON.importTemplate(ExtraBeeDefinition.MINERAL);
		ExtraBeeDefinition.IRON.setSecondaryColor(rockBody);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.MEADOWS, MINERAL, 5);
			registerMutation(BeeDefinition.FOREST, MINERAL, 5);
		}
	},
	LEAD(ExtraBeeBranchDefinition.METALLIC, "plumbous", true, new Color(0xad8bab), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.LEAD.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeeDefinition.LEAD.addSpecialty(EnumHoneyComb.LEAD, 0.50f);
		ExtraBeeDefinition.LEAD.importTemplate(ExtraBeeDefinition.MINERAL);
		ExtraBeeDefinition.LEAD.setSecondaryColor(rockBody);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.MEADOWS, MINERAL, 5);
			registerMutation(BeeDefinition.MODEST, MINERAL, 5);
		}
	},

	/* METALLIC2 BRANCH */
	ZINC(ExtraBeeBranchDefinition.METALLIC2, "spelta", true, new Color(0xedebff), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.ZINC.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeeDefinition.ZINC.addSpecialty(EnumHoneyComb.ZINC, 0.05f);
		ExtraBeeDefinition.ZINC.importTemplate(ExtraBeeDefinition.MINERAL);
		ExtraBeeDefinition.ZINC.setSecondaryColor(rockBody);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.WINTRY, MINERAL, 5);
			registerMutation(BeeDefinition.TROPICAL, MINERAL, 5);
		}
	},
	TITANIUM(ExtraBeeBranchDefinition.METALLIC2, "titania", true, new Color(0xb0aae3), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.TITANIUM.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeeDefinition.TITANIUM.addSpecialty(EnumHoneyComb.TITANIUM, 0.02f);
		ExtraBeeDefinition.TITANIUM.importTemplate(ExtraBeeDefinition.MINERAL);
		ExtraBeeDefinition.TITANIUM.setSecondaryColor(rockBody);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.CULTIVATED, MINERAL, 3);
		}
	},
	TUNGSTATE(ExtraBeeBranchDefinition.METALLIC2, "wolfram", true, new Color(0x131214), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.TUNGSTATE.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeeDefinition.TUNGSTATE.addSpecialty(EnumHoneyComb.TUNGSTEN, 0.01f);
		ExtraBeeDefinition.TUNGSTATE.importTemplate(ExtraBeeDefinition.MINERAL);
		ExtraBeeDefinition.TUNGSTATE.setSecondaryColor(rockBody);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.COMMON, MINERAL, 3);
		}
	},
	NICKEL(ExtraBeeBranchDefinition.METALLIC2, "claro", true, new Color(0xffdefc), new Color(0x999999)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.NICKEL.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeeDefinition.NICKEL.addSpecialty(EnumHoneyComb.NICKEL, 0.05f);
		ExtraBeeDefinition.NICKEL.importTemplate(ExtraBeeDefinition.MINERAL);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.FOREST, MINERAL, 5);
			registerMutation(BeeDefinition.MARSHY, MINERAL, 5);
		}
	},

	/* PRECIOUS BRANCH */
	GOLD(ExtraBeeBranchDefinition.PRECIOUS, "aureus", true, new Color(0xe6cc0b), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.GOLD.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeeDefinition.GOLD.addSpecialty(EnumHoneyComb.GOLD, 0.02f);
		ExtraBeeDefinition.GOLD.importTemplate(ExtraBeeDefinition.MINERAL);
		ExtraBeeDefinition.GOLD.setSecondaryColor(rockBody);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.MAJESTIC, IRON, 2);
			registerMutation(BeeDefinition.MAJESTIC, COPPER, 2);
			registerMutation(BeeDefinition.MAJESTIC, NICKEL, 2);
			registerMutation(BeeDefinition.MAJESTIC, TUNGSTATE, 2);
		}
	},
	SILVER(ExtraBeeBranchDefinition.PRECIOUS, "argentus", false, new Color(0x43455b), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.SILVER.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeeDefinition.SILVER.addSpecialty(EnumHoneyComb.SILVER, 0.02f);
		ExtraBeeDefinition.SILVER.importTemplate(ExtraBeeDefinition.MINERAL);
		ExtraBeeDefinition.SILVER.setSecondaryColor(rockBody);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.MAJESTIC, ZINC, 2);
			registerMutation(BeeDefinition.MAJESTIC, TIN, 2);
			registerMutation(BeeDefinition.MAJESTIC, LEAD, 2);
			registerMutation(BeeDefinition.MAJESTIC, TITANIUM, 2);
		}
	},
	//ELECTRUM,
	PLATINUM(ExtraBeeBranchDefinition.PRECIOUS, "platina", false, new Color(0xdbdbdb), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.PLATINUM.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeeDefinition.PLATINUM.addSpecialty(EnumHoneyComb.PLATINUM, 0.01f);
		ExtraBeeDefinition.PLATINUM.importTemplate(ExtraBeeDefinition.MINERAL);
		ExtraBeeDefinition.PLATINUM.setSecondaryColor(rockBody);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(GOLD, SILVER, 2);
		}
	},

	/* MINERAL BRANCH */
	LAPIS(ExtraBeeBranchDefinition.MINERAL, "lazuli", true, new Color(0x3d2cdb), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.LAPIS.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeeDefinition.LAPIS.addSpecialty(EnumHoneyComb.LAPIS, 0.05f);
		ExtraBeeDefinition.LAPIS.importTemplate(ExtraBeeDefinition.MINERAL);
		ExtraBeeDefinition.LAPIS.setSecondaryColor(rockBody);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.IMPERIAL, MINERAL, 5);
		}
	},
	//SODALITE,
	//PYRITE,
	//BAUXITE,
	//CINNABAR,
	//SPHALERITE,

	/* GEMSTONE BRANCH */
	EMERALD(ExtraBeeBranchDefinition.GEMSTONE, "emerala", true, new Color(0x1cff03), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.EMERALD.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeeDefinition.EMERALD.addSpecialty(EnumHoneyComb.EMERALD, 0.04f);
		ExtraBeeDefinition.EMERALD.importTemplate(ExtraBeeDefinition.MINERAL);
		ExtraBeeDefinition.EMERALD.setSecondaryColor(rockBody);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.FOREST, LAPIS, 5);
		}
	},
	RUBY(ExtraBeeBranchDefinition.GEMSTONE, "ruba", true, new Color(0xd60000), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.RUBY.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeeDefinition.RUBY.addSpecialty(EnumHoneyComb.RUBY, 0.03f);
		ExtraBeeDefinition.RUBY.importTemplate(ExtraBeeDefinition.MINERAL);
		ExtraBeeDefinition.RUBY.setSecondaryColor(rockBody);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.MODEST, LAPIS, 5);
		}
	},
	SAPPHIRE(ExtraBeeBranchDefinition.GEMSTONE, "saphhira", true, new Color(0x0a47ff), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.SAPPHIRE.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeeDefinition.SAPPHIRE.addSpecialty(EnumHoneyComb.SAPPHIRE, 0.03f);
		ExtraBeeDefinition.SAPPHIRE.importTemplate(ExtraBeeDefinition.MINERAL);
		ExtraBeeDefinition.SAPPHIRE.setSecondaryColor(rockBody);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(WATER, LAPIS, 5);
		}
	},
	//OLIVINE,
	DIAMOND(ExtraBeeBranchDefinition.GEMSTONE, "diama", true, new Color(0x7fbdfa), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.DIAMOND.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeeDefinition.DIAMOND.addSpecialty(EnumHoneyComb.DIAMOND, 0.01f);
		ExtraBeeDefinition.DIAMOND.importTemplate(ExtraBeeDefinition.MINERAL);
		ExtraBeeDefinition.DIAMOND.setSecondaryColor(rockBody);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.CULTIVATED, LAPIS, 5);
		}
	},

	/* NUCLEAR BRANCH */
	UNSTABLE(ExtraBeeBranchDefinition.NUCLEAR, "levis", false, new Color(0x3e8c34), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.UNSTABLE.importTemplate(ExtraBeeDefinition.MINERAL);
		ExtraBeeDefinition.UNSTABLE.addProduct(EnumHoneyComb.BARREN, 0.20f);
		ExtraBeeDefinition.UNSTABLE.setEffect(ExtraBeesEffect.RADIOACTIVE.getUID());
		ExtraBeeDefinition.UNSTABLE.setFertility(EnumAllele.Fertility.LOW);
		ExtraBeeDefinition.UNSTABLE.setLifespan(EnumAllele.Lifespan.SHORTEST);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(PREHISTORIC, MINERAL, 5);
		}
	},
	NUCLEAR(ExtraBeeBranchDefinition.NUCLEAR, "nucleus", false, new Color(0x41cc2f), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.NUCLEAR.importTemplate(ExtraBeeDefinition.UNSTABLE);
		ExtraBeeDefinition.NUCLEAR.addProduct(EnumHoneyComb.BARREN, 0.20f);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(UNSTABLE, IRON, 5);
			registerMutation(UNSTABLE, COPPER, 5);
			registerMutation(UNSTABLE, TIN, 5);
			registerMutation(UNSTABLE, ZINC, 5);
			registerMutation(UNSTABLE, NICKEL, 5);
			registerMutation(UNSTABLE, LEAD, 5);
		}
	},
	RADIOACTIVE(ExtraBeeBranchDefinition.NUCLEAR, "fervens", false, new Color(0x1eff00), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.RADIOACTIVE.importTemplate(ExtraBeeDefinition.NUCLEAR);
		ExtraBeeDefinition.RADIOACTIVE.addProduct(EnumHoneyComb.BARREN, 0.20f);
		ExtraBeeDefinition.RADIOACTIVE.addSpecialty(EnumHoneyComb.URANIUM, 0.02f);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(NUCLEAR, GOLD, 5);
			registerMutation(NUCLEAR, SILVER, 5);
		}
	},
	YELLORIUM(ExtraBeeBranchDefinition.NUCLEAR, "yellori", true, new Color(0xd5ed00), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.YELLORIUM
			.importTemplate(ExtraBeeDefinition.NUCLEAR)
			.addProduct(EnumHoneyComb.BARREN, 0.20f)
			.addSpecialty(EnumHoneyComb.YELLORIUM, 0.02f)
			.setEffect(ExtraBeesEffect.RADIOACTIVE.getUID())
			.setFertility(EnumAllele.Fertility.LOW)
			.setLifespan(EnumAllele.Lifespan.SHORTEST);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.FRUGAL, NUCLEAR, 5);
		}
	},
	CYANITE(ExtraBeeBranchDefinition.NUCLEAR, "cyanita", true, new Color(0x0086ed), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.CYANITE
			.importTemplate(ExtraBeeDefinition.YELLORIUM)
			.addProduct(EnumHoneyComb.BARREN, 0.20f)
			.addSpecialty(EnumHoneyComb.CYANITE, 0.01f);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(NUCLEAR, YELLORIUM, 5);
		}
	},
	BLUTONIUM(ExtraBeeBranchDefinition.NUCLEAR, "caruthus", true, new Color(0x1b00e6), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
			registerMutation(CYANITE, YELLORIUM, 5);
		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.BLUTONIUM
			.importTemplate(ExtraBeeDefinition.CYANITE)
			.addProduct(EnumHoneyComb.BARREN, 0.20f)
			.addSpecialty(EnumHoneyComb.BLUTONIUM, 0.01f);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(CYANITE, YELLORIUM, 5);
		}
	},

	/* HISTORIC BRANCH */
	ANCIENT(ExtraBeeBranchDefinition.HISTORIC, "antiquus", true, new Color(0xf2db8f), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.ANCIENT.importTemplate(ForestryAllele.BeeSpecies.Noble);
		ExtraBeeDefinition.ANCIENT.addProduct(EnumHoneyComb.OLD, 0.30f);
		ExtraBeeDefinition.ANCIENT.setLifespan(EnumAllele.Lifespan.ELONGATED);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.NOBLE, BeeDefinition.DILIGENT, 10);
		}
	},
	PRIMEVAL(ExtraBeeBranchDefinition.HISTORIC, "priscus", true, new Color(0xb3a67b), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.PRIMEVAL.importTemplate(ExtraBeeDefinition.ANCIENT);
		ExtraBeeDefinition.PRIMEVAL.addProduct(EnumHoneyComb.OLD, 0.30f);
		ExtraBeeDefinition.PRIMEVAL.setLifespan(EnumAllele.Lifespan.LONG);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.SECLUDED, ANCIENT, 8);
		}
	},
	PREHISTORIC(ExtraBeeBranchDefinition.HISTORIC, "pristinus", false, new Color(0x6e5a40), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.PREHISTORIC.importTemplate(ExtraBeeDefinition.ANCIENT);
		ExtraBeeDefinition.PREHISTORIC.addProduct(EnumHoneyComb.OLD, 0.30f);
		ExtraBeeDefinition.PREHISTORIC.setLifespan(EnumAllele.Lifespan.LONGER);
		ExtraBeeDefinition.PREHISTORIC.setFertility(EnumAllele.Fertility.LOW);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(PRIMEVAL, ANCIENT, 8);
		}
	},
	RELIC(ExtraBeeBranchDefinition.HISTORIC, "sapiens", true, new Color(0x4d3e16), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.RELIC.importTemplate(ExtraBeeDefinition.ANCIENT);
		ExtraBeeDefinition.RELIC.addProduct(EnumHoneyComb.OLD, 0.30f);
		ExtraBeeDefinition.RELIC.setHasEffect(true);
		ExtraBeeDefinition.RELIC.setLifespan(EnumAllele.Lifespan.LONGEST);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.IMPERIAL, PREHISTORIC, 8);
		}
	},

	/* FOSSILIZED BRANCH */
	COAL(ExtraBeeBranchDefinition.FOSSILIZED, "carbo", true, new Color(0x7a7648), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.COAL.importTemplate(ExtraBeeDefinition.ANCIENT);
		ExtraBeeDefinition.COAL.setLifespan(EnumAllele.Lifespan.NORMAL);
		ExtraBeeDefinition.COAL.addProduct(EnumHoneyComb.OLD, 0.20f);
		ExtraBeeDefinition.COAL.addSpecialty(EnumHoneyComb.COAL, 0.08f);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(PRIMEVAL, GROWING, 8);
			registerMutation(BeeDefinition.RURAL, PRIMEVAL, 8);
		}
	},
	RESIN(ExtraBeeBranchDefinition.FOSSILIZED, "lacrima", false, new Color(0xa6731b), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.RESIN.importTemplate(ExtraBeeDefinition.COAL);
		ExtraBeeDefinition.RESIN.addProduct(EnumHoneyComb.OLD, 0.20f);
		ExtraBeeDefinition.RESIN.addSpecialty(EnumHoneyComb.RESIN, 0.05f);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.MIRY, PRIMEVAL, 8);
		}
	},
	OIL(ExtraBeeBranchDefinition.FOSSILIZED, "lubricus", true, new Color(0x574770), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.OIL.importTemplate(ExtraBeeDefinition.COAL);
		ExtraBeeDefinition.OIL.addProduct(EnumHoneyComb.OLD, 0.20f);
		ExtraBeeDefinition.OIL.addSpecialty(EnumHoneyComb.OIL, 0.05f);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(OCEAN, PRIMEVAL, 8);
			registerMutation(BeeDefinition.FRUGAL, PRIMEVAL, 8);
		}
	},
	//	PEAT,

	/* REFINED BRANCH */
	DISTILLED(ExtraBeeBranchDefinition.REFINED, "distilli", false, new Color(0x356356), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.DISTILLED.importTemplate(ExtraBeeDefinition.OIL);
		ExtraBeeDefinition.DISTILLED.addProduct(EnumHoneyComb.OIL, 0.10f);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.INDUSTRIOUS, OIL, 8);
		}
	},
	FUEL(ExtraBeeBranchDefinition.REFINED, "refina", true, new Color(0xffc003), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.FUEL.importTemplate(ExtraBeeDefinition.OIL);
		ExtraBeeDefinition.FUEL.addProduct(EnumHoneyComb.OIL, 0.10f);
		ExtraBeeDefinition.FUEL.addSpecialty(EnumHoneyComb.FUEL, 0.04f);
		ExtraBeeDefinition.FUEL.setHasEffect(true);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(DISTILLED, OIL, 8);
		}
	},
	CREOSOTE(ExtraBeeBranchDefinition.REFINED, "creosota", true, new Color(0x979e13), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.CREOSOTE.importTemplate(ExtraBeeDefinition.COAL);
		ExtraBeeDefinition.CREOSOTE.addProduct(EnumHoneyComb.COAL, 0.10f);
		ExtraBeeDefinition.CREOSOTE.addSpecialty(EnumHoneyComb.CREOSOTE, 0.07f);
		ExtraBeeDefinition.CREOSOTE.setHasEffect(true);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(DISTILLED, COAL, 8);
		}
	},
	LATEX(ExtraBeeBranchDefinition.REFINED, "latex", true, new Color(0x494a3e), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.LATEX.importTemplate(ExtraBeeDefinition.RESIN);
		ExtraBeeDefinition.LATEX.addProduct(EnumHoneyComb.RESIN, 0.10f);
		ExtraBeeDefinition.LATEX.addSpecialty(EnumHoneyComb.LATEX, 0.05f);
		ExtraBeeDefinition.LATEX.setHasEffect(true);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(DISTILLED, RESIN, 8);
		}
	},

	/* AQUATIC BRANCH */
	WATER(ExtraBeeBranchDefinition.AQUATIC, "aqua", true, new Color(0x94a2ff), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.WATER.addProduct(EnumHoneyComb.WATER, 0.30f);
		ExtraBeeDefinition.WATER.setIsSecret(false);
		ExtraBeeDefinition.WATER.setTolerantFlyer();
		ExtraBeeDefinition.WATER.setHumidityTolerance(EnumTolerance.BOTH_1);
		ExtraBeeDefinition.WATER.setFlowerProvider(ExtraBeesFlowers.WATER.getUID());
		ExtraBeeDefinition.WATER.setFlowering(EnumAllele.Flowering.SLOW);
		ExtraBeeDefinition.WATER.setEffect(ExtraBeesEffect.WATER.getUID());
		ExtraBeeDefinition.WATER.setHumidity(EnumHumidity.DAMP);*/
		}
	},
	RIVER(ExtraBeeBranchDefinition.AQUATIC, "flumen", true, new Color(0x83b3d4), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.RIVER.importTemplate(ExtraBeeDefinition.WATER);
		ExtraBeeDefinition.RIVER.addProduct(EnumHoneyComb.WATER, 0.30f);
		ExtraBeeDefinition.RIVER.addSpecialty(EnumHoneyComb.CLAY, 0.20f);
		ExtraBeeDefinition.RIVER.importTemplate(ExtraBeeDefinition.WATER);*/
		}

		@Override
		protected void registerMutations() {
//			registerMutation(BeeDefinition.DILIGENT, WATER, 10);
//			new ExtraBeeMutation(ExtraBeeDefinition.WATER, BeeDefinition.DILIGENT, ExtraBeeDefinition.RIVER, 10, new ExtraBeeMutation.RequirementBiomeType(BiomeDictionary.Type.RIVER));
		}
	},
	OCEAN(ExtraBeeBranchDefinition.AQUATIC, "mare", false, new Color(0x1d2ead), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.OCEAN.importTemplate(ExtraBeeDefinition.WATER);
		ExtraBeeDefinition.OCEAN.addProduct(EnumHoneyComb.WATER, 0.30f);
		ExtraBeeDefinition.OCEAN.importTemplate(ExtraBeeDefinition.WATER);*/
		}

		@Override
		protected void registerMutations() {
//			registerMutation(BeeDefinition.DILIGENT, WATER, 10);
//			new ExtraBeeMutation(ExtraBeeDefinition.WATER, , ExtraBeeDefinition.OCEAN, 10, new ExtraBeeMutation.RequirementBiomeType(BiomeDictionary.Type.OCEAN));
		}
	},
	INK(ExtraBeeBranchDefinition.AQUATIC, "atramentum", true, new Color(0xe1447), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.INK.importTemplate(ExtraBeeDefinition.OCEAN);
		ExtraBeeDefinition.INK.addProduct(EnumHoneyComb.WATER, 0.30f);
		ExtraBeeDefinition.INK.addSpecialty(new ItemStack(Items.dye, 1, 0), 0.10f);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BLACK, OCEAN, 8);
		}
	},

	/* AGRARIAN BRANCH */
	GROWING(BeeBranchDefinition.AGRARIAN, "tyrelli", true, new Color(0x5bebd8), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.GROWING.importTemplate(ForestryAllele.BeeSpecies.Forest);
		ExtraBeeDefinition.GROWING.addProduct(ItemHoneyComb.VanillaComb.HONEY.get(), 0.35f);
		ExtraBeeDefinition.GROWING.setFlowering(EnumAllele.Flowering.AVERAGE);
		ExtraBeeDefinition.GROWING.setFlowerProvider(ExtraBeesFlowers.LEAVES.getUID());*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.FOREST, BeeDefinition.DILIGENT, 10);
		}
	},
	FARM(BeeBranchDefinition.AGRARIAN, "ager", true, new Color(0x75db60), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.FARM.addProduct(ItemHoneyComb.VanillaComb.WHEATEN.get(), 0.30f);
		ExtraBeeDefinition.FARM.addSpecialty(EnumHoneyComb.SEED, 0.10f);
		ExtraBeeDefinition.FARM.importTemplate(ForestryAllele.BeeSpecies.Rural);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.FARMERLY, BeeDefinition.MEADOWS, 10);
		}
	},
	THRIVING(BeeBranchDefinition.AGRARIAN, "thriva", true, new Color(0x34e37d), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.THRIVING.importTemplate(ExtraBeeDefinition.GROWING);
		ExtraBeeDefinition.THRIVING.addProduct(ItemHoneyComb.VanillaComb.HONEY.get(), 0.35f);
		ExtraBeeDefinition.THRIVING.setFlowering(EnumAllele.Flowering.FAST);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.UNWEARY, GROWING, 10);
		}
	},
	BLOOMING(BeeBranchDefinition.AGRARIAN, "blooma", true, new Color(0x0abf34), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.BLOOMING.importTemplate(ExtraBeeDefinition.THRIVING);
		ExtraBeeDefinition.BLOOMING.setFlowering(EnumAllele.Flowering.FASTEST);
		ExtraBeeDefinition.BLOOMING.addProduct(ItemHoneyComb.VanillaComb.HONEY.get(), 0.35f);
		ExtraBeeDefinition.BLOOMING.setFlowerProvider(ExtraBeesFlowers.SAPLING.getUID());
		ExtraBeeDefinition.BLOOMING.setEffect(ExtraBeesEffect.BONEMEAL_SAPLING.getUID());*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.INDUSTRIOUS, THRIVING, 8);
		}
	},

	/* SACCHARINE BRANCH */
	SWEET(ExtraBeeBranchDefinition.SACCHARINE, "mellitus", true, new Color(0xfc51f1), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.SWEET.importTemplate(ForestryAllele.BeeSpecies.Rural);
		ExtraBeeDefinition.SWEET.addProduct(ItemHoneyComb.VanillaComb.HONEY.get(), 0.40f);
		ExtraBeeDefinition.SWEET.addProduct(new ItemStack(Items.sugar, 1, 0), 0.10f);
		ExtraBeeDefinition.SWEET.setFlowerProvider(ExtraBeesFlowers.SUGAR.getUID());*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.VALIANT, BeeDefinition.DILIGENT, 15);
		}
	},
	SUGAR(ExtraBeeBranchDefinition.SACCHARINE, "dulcis", true, new Color(0xe6d3e0), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.SUGAR.addProduct(ItemHoneyComb.VanillaComb.HONEY.get(), 0.40f);
		ExtraBeeDefinition.SUGAR.addProduct(new ItemStack(Items.sugar, 1, 0), 0.20f);
		ExtraBeeDefinition.SUGAR.importTemplate(ExtraBeeDefinition.SWEET);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.RURAL, SWEET, 15);
		}
	},
	RIPENING(ExtraBeeBranchDefinition.SACCHARINE, "ripa", true, new Color(0xb2c75d), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.RIPENING.addProduct(ItemHoneyComb.VanillaComb.HONEY.get(), 0.30f);
		ExtraBeeDefinition.RIPENING.addProduct(new ItemStack(Items.sugar, 1, 0), 0.10f);
		ExtraBeeDefinition.RIPENING.addSpecialty(EnumHoneyComb.FRUIT, 0.10f);
		ExtraBeeDefinition.RIPENING.setFlowerProvider(ExtraBeesFlowers.FRUIT.getUID());
		ExtraBeeDefinition.RIPENING.importTemplate(ExtraBeeDefinition.SUGAR);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(SWEET, GROWING, 5);
		}
	},
	FRUIT(ExtraBeeBranchDefinition.SACCHARINE, "pomum", true, new Color(0xdb5876), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.FRUIT.importTemplate(ExtraBeeDefinition.RIPENING);
		ExtraBeeDefinition.FRUIT.addProduct(ItemHoneyComb.VanillaComb.HONEY.get(), 0.30f);
		ExtraBeeDefinition.FRUIT.addProduct(new ItemStack(Items.sugar, 1, 0), 0.15f);
		ExtraBeeDefinition.FRUIT.addSpecialty(EnumHoneyComb.FRUIT, 0.20f);
		ExtraBeeDefinition.FRUIT.setEffect(ExtraBeesEffect.BONEMEAL_FRUIT.getUID());
		ExtraBeeDefinition.FRUIT.setHasEffect(true);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(SWEET, THRIVING, 5);
		}
	},

	/* FARMING BRANCH */
	ALCOHOL(ExtraBeeBranchDefinition.FARMING, "vinum", false, new Color(0xe88a61), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.ALCOHOL.importTemplate(ExtraBeeDefinition.SWEET);
		ExtraBeeDefinition.ALCOHOL.addProduct(ItemHoneyComb.VanillaComb.WHEATEN.get(), 0.30f);
		ExtraBeeDefinition.ALCOHOL.addSpecialty(EnumHoneyComb.ALCOHOL, 0.10f);
		ExtraBeeDefinition.ALCOHOL.setEffect("forestry.effectDrunkard");*/
		}

		@Override
		protected void registerMutations() {
//			new ExtraBeeMutation(BeeDefinition.FARMERLY, BeeDefinition.MEADOWS, ExtraBeeDefinition.ALCOHOL, 10);
		}
	},
	MILK(ExtraBeeBranchDefinition.FARMING, "lacteus", true, new Color(0xe3e8e8), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.MILK.addProduct(ItemHoneyComb.VanillaComb.WHEATEN.get(), 0.30f);
		ExtraBeeDefinition.MILK.addSpecialty(EnumHoneyComb.MILK, 0.10f);
		ExtraBeeDefinition.MILK.importTemplate(ForestryAllele.BeeSpecies.Rural);*/
		}

		@Override
		protected void registerMutations() {
//			new ExtraBeeMutation(BeeDefinition.FARMERLY, ExtraBeeDefinition.WATER, ExtraBeeDefinition.MILK, 10);
		}
	},
	COFFEE(ExtraBeeBranchDefinition.FARMING, "arabica", true, new Color(0x8c5e30), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.COFFEE.addProduct(ItemHoneyComb.VanillaComb.WHEATEN.get(), 0.30f);
		ExtraBeeDefinition.COFFEE.addSpecialty(EnumHoneyComb.COFFEE, 0.08f);
		ExtraBeeDefinition.COFFEE.importTemplate(ForestryAllele.BeeSpecies.Rural);*/
		}

		@Override
		protected void registerMutations() {
//			new ExtraBeeMutation(BeeDefinition.FARMERLY, BeeDefinition.TROPICAL, ExtraBeeDefinition.COFFEE, 10);
		}
	},
	//CITRUS,
	//MINT,

	/* BOGGY BRANCH */
	SWAMP(BeeBranchDefinition.BOGGY, "paludis", true, new Color(0x356933), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.SWAMP.addProduct(ItemHoneyComb.VanillaComb.MOSSY.get(), 0.30f);
		ExtraBeeDefinition.SWAMP.importTemplate(ForestryAllele.BeeSpecies.Marshy);
		ExtraBeeDefinition.SWAMP.setHumidity(EnumHumidity.DAMP);
		ExtraBeeDefinition.SWAMP.setEffect(ExtraBeesEffect.SLOW.getUID());*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.MIRY, WATER, 10);
		}
	},
	BOGGY(BeeBranchDefinition.BOGGY, "lama", false, new Color(0x785c29), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.BOGGY.importTemplate(ExtraBeeDefinition.SWAMP);
		ExtraBeeDefinition.BOGGY.addProduct(ItemHoneyComb.VanillaComb.MOSSY.get(), 0.30f);
		ExtraBeeDefinition.BOGGY.importTemplate(ExtraBeeDefinition.SWAMP);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.BOGGY, SWAMP, 8);
		}
	},
	FUNGAL(BeeBranchDefinition.BOGGY, "boletus", true, new Color(0xd16200), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.FUNGAL.importTemplate(ExtraBeeDefinition.BOGGY);
		ExtraBeeDefinition.FUNGAL.addProduct(ItemHoneyComb.VanillaComb.MOSSY.get(), 0.30f);
		ExtraBeeDefinition.FUNGAL.addSpecialty(EnumHoneyComb.FUNGAL, 0.15f);
		ExtraBeeDefinition.FUNGAL.importTemplate(ExtraBeeDefinition.BOGGY);
		ExtraBeeDefinition.FUNGAL.setEffect(ExtraBeesEffect.BONEMEAL_MUSHROOM.getUID());
		ExtraBeeDefinition.FUNGAL.setHasEffect(true);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.BOGGY, BeeDefinition.MIRY, 8);
			registerMutation(BeeDefinition.BOGGY, FUNGAL, 8);
		}
	},
	
	/* CLASSICAL BRANCH */
	//MARBLE,
	//ROMAN,
	//GREEK,
	//CLASSICAL,

	/* VOLCANIC BRANCH */
	BASALT(ExtraBeeBranchDefinition.VOLCANIC, "aceri", true, new Color(0x8c6969), new Color(0x9a2323)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.BASALT.addProduct(ItemHoneyComb.VanillaComb.SIMMERING.get(), 0.25f);
		ExtraBeeDefinition.BASALT.importTemplate(ForestryAllele.BeeSpecies.Sinister);
		ExtraBeeDefinition.BASALT.setEffect("forestry.effectAggressive");
		ExtraBeeDefinition.BASALT.setHumidity(EnumHumidity.ARID);
		ExtraBeeDefinition.BASALT.setTemperature(EnumTemperature.HELLISH);*/
		}
	},
	TEMPERED(ExtraBeeBranchDefinition.VOLCANIC, "iratus", false, new Color(0x8a4848), new Color(0x9a2323)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.TEMPERED.addProduct(ItemHoneyComb.VanillaComb.SIMMERING.get(), 0.25f);
		ExtraBeeDefinition.TEMPERED.importTemplate(ExtraBeeDefinition.BASALT);
		ExtraBeeDefinition.TEMPERED.setEffect(ExtraBeesEffect.METEOR.getUID());*/
		}

		@Override
		protected void registerMutations() {
//			new ExtraBeeMutation(ExtraBeeDefinition.BASALT, BeeDefinition.FIENDISH, ExtraBeeDefinition.TEMPERED, 30, new ExtraBeeMutation.RequirementBiomeType(BiomeDictionary.Type.NETHER));
		}
	},
	//ANGRY,
	VOLCANIC(ExtraBeeBranchDefinition.VOLCANIC, "volcano", true, new Color(0x4d0c0c), new Color(0x9a2323)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.VOLCANIC.importTemplate(ExtraBeeDefinition.TEMPERED);
		ExtraBeeDefinition.VOLCANIC.addProduct(ItemHoneyComb.VanillaComb.SIMMERING.get(), 0.25f);
		ExtraBeeDefinition.VOLCANIC.addSpecialty(EnumHoneyComb.BLAZE, 0.10f);
		ExtraBeeDefinition.VOLCANIC.setHasEffect(true);*/
		}

		@Override
		protected void registerMutations() {
//			new ExtraBeeMutation(ExtraBeeDefinition.TEMPERED, BeeDefinition.DEMONIC, ExtraBeeDefinition.VOLCANIC, 20, new ExtraBeeMutation.RequirementBiomeType(BiomeDictionary.Type.NETHER));
		}
	},
	GLOWSTONE(ExtraBeeBranchDefinition.VOLCANIC, "glowia", true, new Color(0xe0c61b), new Color(0x9a2323)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.GLOWSTONE.importTemplate(ExtraBeeDefinition.BASALT);
		ExtraBeeDefinition.GLOWSTONE.addProduct(EnumHoneyComb.GLOWSTONE, 0.15f);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(ExtraBeeDefinition.TEMPERED, ExtraBeeDefinition.EXCITED, 5);
		}
	},

	/* VIRULENT BRANCH */
	MALICIOUS(ExtraBeeBranchDefinition.VIRULENT, "acerbus", true, new Color(0x782a77), new Color(0x069764)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.MALICIOUS.importTemplate(ForestryAllele.BeeSpecies.Tropical);
		ExtraBeeDefinition.MALICIOUS.addProduct(ItemHoneyComb.VanillaComb.SILKY.get(), 0.25f);
		ExtraBeeDefinition.MALICIOUS.setHumidity(EnumHumidity.DAMP);
		ExtraBeeDefinition.MALICIOUS.setTemperature(EnumTemperature.WARM);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.SINISTER, BeeDefinition.TROPICAL, 10);
		}
	},
	INFECTIOUS(ExtraBeeBranchDefinition.VIRULENT, "contagio", true, new Color(0xb82eb5), new Color(0x069764)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.INFECTIOUS.importTemplate(ExtraBeeDefinition.MALICIOUS);
		ExtraBeeDefinition.INFECTIOUS.addProduct(ItemHoneyComb.VanillaComb.SILKY.get(), 0.25f);
		ExtraBeeDefinition.INFECTIOUS.setFlowering(EnumAllele.Flowering.SLOW);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.TROPICAL, ExtraBeeDefinition.MALICIOUS, 8);
		}
	},
	VIRULENT(ExtraBeeBranchDefinition.VIRULENT, "morbus", false, new Color(0xf013ec), new Color(0x069764)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.VIRULENT.importTemplate(ExtraBeeDefinition.INFECTIOUS);
		ExtraBeeDefinition.VIRULENT.addProduct(ItemHoneyComb.VanillaComb.SILKY.get(), 0.25f);
		ExtraBeeDefinition.VIRULENT.addSpecialty(EnumHoneyComb.VENOMOUS, 0.12f);
		ExtraBeeDefinition.VIRULENT.setFlowering(EnumAllele.Flowering.AVERAGE);
		ExtraBeeDefinition.VIRULENT.setHasEffect(true);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(ExtraBeeDefinition.MALICIOUS, ExtraBeeDefinition.INFECTIOUS, 8);
		}
	},

	/* VISCOUS BRANCH */
	VISCOUS(ExtraBeeBranchDefinition.VISCOUS, "liquidus", true, new Color(0x9470e), new Color(0x069764)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.VISCOUS.importTemplate(ForestryAllele.BeeSpecies.Tropical);
		ExtraBeeDefinition.VISCOUS.setEffect(ExtraBeesEffect.ECTOPLASM.getUID());
		ExtraBeeDefinition.VISCOUS.addProduct(ItemHoneyComb.VanillaComb.SILKY.get(), 0.25f);
		ExtraBeeDefinition.VISCOUS.setHumidity(EnumHumidity.DAMP);
		ExtraBeeDefinition.VISCOUS.setSpeed(EnumAllele.Speed.SLOW);
		ExtraBeeDefinition.VISCOUS.setTemperature(EnumTemperature.WARM);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.EXOTIC, ExtraBeeDefinition.WATER, 10);
		}
	},
	GLUTINOUS(ExtraBeeBranchDefinition.VISCOUS, "glutina", true, new Color(0x1d8c27), new Color(0x069764)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.GLUTINOUS.importTemplate(ExtraBeeDefinition.VISCOUS);
		ExtraBeeDefinition.GLUTINOUS.addProduct(ItemHoneyComb.VanillaComb.SILKY.get(), 0.25f);
		ExtraBeeDefinition.GLUTINOUS.setSpeed(EnumAllele.Speed.NORMAL);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.EXOTIC, ExtraBeeDefinition.VISCOUS, 8);
		}
	},
	STICKY(ExtraBeeBranchDefinition.VISCOUS, "lentesco", true, new Color(0x17e328), new Color(0x069764)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.STICKY.importTemplate(ExtraBeeDefinition.GLUTINOUS);
		ExtraBeeDefinition.STICKY.addProduct(ItemHoneyComb.VanillaComb.SILKY.get(), 0.25f);
		ExtraBeeDefinition.STICKY.addSpecialty(EnumHoneyComb.SLIME, 0.12f);
		ExtraBeeDefinition.STICKY.setSpeed(EnumAllele.Speed.FAST);
		ExtraBeeDefinition.STICKY.setHasEffect(true);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(ExtraBeeDefinition.VISCOUS, ExtraBeeDefinition.GLUTINOUS, 8);
		}
	},

	/* CAUSTIC BRANCH */
	CORROSIVE(ExtraBeeBranchDefinition.CAUSTIC, "corrumpo", false, new Color(0x4a5c0b), new Color(0x069764)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.CORROSIVE.importTemplate(ExtraBeeDefinition.STICKY);
		ExtraBeeDefinition.CORROSIVE.setHumidity(EnumHumidity.DAMP);
		ExtraBeeDefinition.CORROSIVE.setTemperature(EnumTemperature.WARM);
		ExtraBeeDefinition.CORROSIVE.setEffect(ExtraBeesEffect.ACID.getUID());
		ExtraBeeDefinition.CORROSIVE.setFlowering(EnumAllele.Flowering.AVERAGE);
		ExtraBeeDefinition.CORROSIVE.addProduct(ItemHoneyComb.VanillaComb.SILKY.get(), 20f);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(ExtraBeeDefinition.MALICIOUS, ExtraBeeDefinition.VISCOUS, 10);
		}
	},
	CAUSTIC(ExtraBeeBranchDefinition.CAUSTIC, "torrens", true, new Color(0x84a11d), new Color(0x069764)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.CAUSTIC.importTemplate(ExtraBeeDefinition.CORROSIVE);
		ExtraBeeDefinition.CAUSTIC.addProduct(ItemHoneyComb.VanillaComb.SILKY.get(), 0.25f);
		ExtraBeeDefinition.CAUSTIC.addSpecialty(EnumHoneyComb.ACIDIC, 0.03f);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.FIENDISH, ExtraBeeDefinition.CORROSIVE, 8);
		}
	},
	ACIDIC(ExtraBeeBranchDefinition.CAUSTIC, "acidus", true, new Color(0xc0f016), new Color(0x069764)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.ACIDIC.importTemplate(ExtraBeeDefinition.CAUSTIC);
		ExtraBeeDefinition.ACIDIC.addProduct(ItemHoneyComb.VanillaComb.SILKY.get(), 0.20f);
		ExtraBeeDefinition.ACIDIC.addSpecialty(EnumHoneyComb.ACIDIC, 0.16f);
		ExtraBeeDefinition.ACIDIC.setHasEffect(true);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(ExtraBeeDefinition.CORROSIVE, ExtraBeeDefinition.CAUSTIC, 4);
		}
	},

	/* ENERGETIC BRANCH */
	EXCITED(ExtraBeeBranchDefinition.ENERGETIC, "excita", true, new Color(0xff4545), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.EXCITED.setEffect(ExtraBeesEffect.LIGHTNING.getUID());
		ExtraBeeDefinition.EXCITED.addProduct(EnumHoneyComb.REDSTONE, 0.10f);
		ExtraBeeDefinition.EXCITED.setCaveDwelling();
		ExtraBeeDefinition.EXCITED.setFlowerProvider(ExtraBeesFlowers.REDSTONE.getUID());*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.VALIANT, BeeDefinition.CULTIVATED, 10);
		}
	},
	ENERGETIC(ExtraBeeBranchDefinition.ENERGETIC, "energia", false, new Color(0xe835c7), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.ENERGETIC.importTemplate(ExtraBeeDefinition.EXCITED);
		ExtraBeeDefinition.ENERGETIC.setEffect(ExtraBeesEffect.LIGHTNING.getUID());
		ExtraBeeDefinition.ENERGETIC.addProduct(EnumHoneyComb.REDSTONE, 0.12f);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.DILIGENT, ExtraBeeDefinition.EXCITED, 8);
		}
	},
	ECSTATIC(ExtraBeeBranchDefinition.ENERGETIC, "ecstatica", true, new Color(0xaf35e8), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.ECSTATIC.importTemplate(ExtraBeeDefinition.ENERGETIC);
		ExtraBeeDefinition.ECSTATIC.setEffect(ExtraBeesEffect.Power.getUID());
		ExtraBeeDefinition.ECSTATIC.addProduct(EnumHoneyComb.REDSTONE, 0.20f);
		ExtraBeeDefinition.ECSTATIC.addSpecialty(EnumHoneyComb.IC2ENERGY, 0.08f);
		ExtraBeeDefinition.ECSTATIC.setHasEffect(true);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(ExtraBeeDefinition.EXCITED, ExtraBeeDefinition.ENERGETIC, 8);
		}
	},

	/* FROZEN BRANCH */
	ARTIC(BeeBranchDefinition.FROZEN, "artica", true, new Color(0xade0e0), new Color(0xdaf5f3)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.ARTIC.importTemplate(ForestryAllele.BeeSpecies.Wintry);
		ExtraBeeDefinition.ARTIC.addProduct(ItemHoneyComb.VanillaComb.FROZEN.get(), 0.25f);
		ExtraBeeDefinition.ARTIC.setTemperature(EnumTemperature.ICY);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.WINTRY, BeeDefinition.DILIGENT, 10);
		}
	},
	FREEZING(BeeBranchDefinition.FROZEN, "glacia", true, new Color(0x7be3e3), new Color(0xdaf5f3)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.FREEZING.importTemplate(ExtraBeeDefinition.ARTIC);
		ExtraBeeDefinition.FREEZING.addProduct(ItemHoneyComb.VanillaComb.FROZEN.get(), 0.20f);
		ExtraBeeDefinition.FREEZING.addSpecialty(EnumHoneyComb.GLACIAL, 0.10f);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(ExtraBeeDefinition.OCEAN, ExtraBeeDefinition.ARTIC, 10);
		}
	},

	/* SHADOW BRANCH */
	SHADOW(ExtraBeeBranchDefinition.SHADOW, "shadowa", false, new Color(0x595959), new Color(0x333333)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.SHADOW.importTemplate(ExtraBeeDefinition.BASALT);
		ExtraBeeDefinition.SHADOW.setNocturnal();
		ExtraBeeDefinition.SHADOW.addProduct(EnumHoneyComb.SHADOW, 0.05f);
		ExtraBeeDefinition.SHADOW.setEffect(ExtraBeesEffect.BLINDNESS.getUID());
		ExtraBeeDefinition.SHADOW.setAllDay(false);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.SINISTER, ExtraBeeDefinition.ROCK, 10);
		}
	},
	DARKENED(ExtraBeeBranchDefinition.SHADOW, "darka", true, new Color(0x332e33), new Color(0x333333)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.DARKENED.addProduct(EnumHoneyComb.SHADOW, 0.10f);
		ExtraBeeDefinition.DARKENED.setNocturnal();
		ExtraBeeDefinition.DARKENED.importTemplate(ExtraBeeDefinition.SHADOW);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(ExtraBeeDefinition.SHADOW, ExtraBeeDefinition.ROCK, 8);
		}
	},
	ABYSS(ExtraBeeBranchDefinition.SHADOW, "abyssba", true, new Color(0x210821), new Color(0x333333)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.ABYSS.importTemplate(ExtraBeeDefinition.DARKENED);
		ExtraBeeDefinition.ABYSS.setNocturnal();
		ExtraBeeDefinition.ABYSS.addProduct(EnumHoneyComb.SHADOW, 0.25f);
		ExtraBeeDefinition.ABYSS.setEffect(ExtraBeesEffect.WITHER.getUID());
		ExtraBeeDefinition.ABYSS.setHasEffect(true);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(ExtraBeeDefinition.SHADOW, ExtraBeeDefinition.DARKENED, 8);
		}
	},

	/* PRIMARY BRNCH */
	RED(ExtraBeeBranchDefinition.PRIMARY, "rubra", true, new Color(0xff0000), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {

		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.FOREST, BeeDefinition.VALIANT, 5);
		}
	},
	YELLOW(ExtraBeeBranchDefinition.PRIMARY, "fulvus", true, new Color(0xffdd00), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {

		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.MEADOWS, BeeDefinition.VALIANT, 5);
		}
	},
	BLUE(ExtraBeeBranchDefinition.PRIMARY, "caeruleus", true, new Color(0x0022ff), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {

		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.VALIANT, ExtraBeeDefinition.WATER, 5);
		}
	},
	GREEN(ExtraBeeBranchDefinition.PRIMARY, "prasinus", true, new Color(0x009900), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {

		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.TROPICAL, BeeDefinition.VALIANT, 5);
		}
	},
	BLACK(ExtraBeeBranchDefinition.PRIMARY, "niger", true, new Color(0x575757), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {

		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.VALIANT, ExtraBeeDefinition.ROCK, 5);
		}
	},
	WHITE(ExtraBeeBranchDefinition.PRIMARY, "albus", true, new Color(0xffffff), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {

		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.WINTRY, BeeDefinition.VALIANT, 5);
		}
	},
	BROWN(ExtraBeeBranchDefinition.PRIMARY, "fuscus", true, new Color(0x5c350f), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {

		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.MARSHY, BeeDefinition.VALIANT, 5);
		}
	},

	/* SECONDARY BRANCH */
	ORANGE(ExtraBeeBranchDefinition.SECONDARY, "flammeus", true, new Color(0xff9d00), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {

		}

		@Override
		protected void registerMutations() {
			registerMutation(RED, YELLOW, 5);
		}
	},
	CYAN(ExtraBeeBranchDefinition.SECONDARY, "cyana", true, new Color(0x00ffe5), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {

		}

		@Override
		protected void registerMutations() {
			registerMutation(GREEN, BLUE, 5);
		}
	},
	PURPLE(ExtraBeeBranchDefinition.SECONDARY, "purpureus", true, new Color(0xae00ff), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {

		}

		@Override
		protected void registerMutations() {
			registerMutation(RED, BLUE, 5);
		}
	},
	GRAY(ExtraBeeBranchDefinition.SECONDARY, "ravus", true, new Color(0xbababa), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {

		}

		@Override
		protected void registerMutations() {
			registerMutation(BLACK, WHITE, 5);
		}
	},
	LIGHTBLUE(ExtraBeeBranchDefinition.SECONDARY, "aqua", true, new Color(0x009dff), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {

		}

		@Override
		protected void registerMutations() {
			registerMutation(BLUE, WHITE, 5);
		}
	},
	PINK(ExtraBeeBranchDefinition.SECONDARY, "rosaceus", true, new Color(0xff80df), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {

		}

		@Override
		protected void registerMutations() {
			registerMutation(RED, WHITE, 5);
		}
	},
	LIMEGREEN(ExtraBeeBranchDefinition.SECONDARY, "lima", true, new Color(0x00ff08), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {

		}

		@Override
		protected void registerMutations() {
			registerMutation(GREEN, WHITE, 5);
		}
	},

	/* TERTIARY BRANCH */
	MAGENTA(ExtraBeeBranchDefinition.TERTIARY, "fuchsia", true, new Color(0xff00cc), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {

		}

		@Override
		protected void registerMutations() {
			registerMutation(PURPLE, PINK, 5);
		}
	},
	LIGHTGRAY(ExtraBeeBranchDefinition.TERTIARY, "canus", true, new Color(0xc9c9c9), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {

		}

		@Override
		protected void registerMutations() {
			registerMutation(GRAY, WHITE, 5);
		}
	},

	/* FESTIVE BRANCH */
	CELEBRATORY(BeeBranchDefinition.FESTIVE, "celeba", true, new Color(0xfa0a6a), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.CELEBRATORY.importTemplate(ForestryAllele.BeeSpecies.Merry);
		ExtraBeeDefinition.CELEBRATORY.setEffect(ExtraBeesEffect.FIREWORKS.getUID());*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.AUSTERE, EXCITED, 5);
		}
	},

	/* FTB BRANCH */
	JADED(ExtraBeeBranchDefinition.FTB, "jadeca", true, new Color(0xfa0a6a), new Color(0xdc8aeb)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.JADED.importTemplate(ForestryAllele.BeeSpecies.Imperial);
		ExtraBeeDefinition.JADED.setFertility(EnumAllele.Fertility.MAXIMUM);
		ExtraBeeDefinition.JADED.setFlowering(EnumAllele.Flowering.MAXIMUM);
		ExtraBeeDefinition.JADED.setTerritory(EnumAllele.Territory.LARGEST);
		ExtraBeeDefinition.JADED.addProduct(ItemHoneyComb.VanillaComb.HONEY.get(), 0.30f);
		ExtraBeeDefinition.JADED.addSpecialty(Mods.Forestry.stack("pollen"), 0.20f);
		ExtraBeeDefinition.JADED.setHasEffect(true);
		ExtraBeeDefinition.JADED.addSpecialty(EnumHoneyComb.PURPLE, 0.15f);
		ExtraBeeDefinition.JADED.isCounted = false;*/
		}

		@Override
		protected void registerMutations() {
//			registerMutation(BeeDefinition.ENDED, RELIC, 2);
//			new ExtraBeeMutation(BeeDefinition.ENDED, ExtraBeeDefinition.RELIC, ExtraBeeDefinition.JADED, 2, new ExtraBeeMutation.RequirementPerson("jadedcat"));
		}
	},

	/* AUSTERE BRANCH */
	HAZARDOUS(BeeBranchDefinition.AUSTERE, "infensus", true, new Color(0xb06c28), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.HAZARDOUS.importTemplate(ForestryAllele.BeeSpecies.Austere);
		ExtraBeeDefinition.HAZARDOUS.addProduct(EnumHoneyComb.SALTPETER, 0.12f);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.AUSTERE, DESOLATE, 5);
		}
	},

	/* ALLOY BRANCH */
	//INVAR,
	//BRONZE,
	//BRASS,
	//STEEL,

	/* QUANTUM BRANCH */
	QUANTUM(ExtraBeeBranchDefinition.QUANTUM, "quanta", true, new Color(0x37c5db), new Color(0xd50fdb)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.QUANTUM.importTemplate(ExtraBeeDefinition.QUANTUM);
		ExtraBeeDefinition.QUANTUM.setEffect(ExtraBeesEffect.TELEPORT.getUID());
		ExtraBeeDefinition.QUANTUM.addProduct(ItemHoneyComb.VanillaComb.QUARTZ.get(), 0.25f);
		ExtraBeeDefinition.QUANTUM.addSpecialty(EnumHoneyComb.CERTUS, 0.15f);
		ExtraBeeDefinition.QUANTUM.addSpecialty(EnumHoneyComb.ENDERPEARL, 0.15f);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.SPECTRAL, SPATIAL, 5);
		}
	},
	SPATIAL(ExtraBeeBranchDefinition.QUANTUM, "spatia", true, new Color(0x4c1be0), new Color(0xa44ecc)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.SPATIAL.importTemplate(ExtraBeeDefinition.UNUSUAL);
		ExtraBeeDefinition.SPATIAL.setEffect(ExtraBeesEffect.GRAVITY.getUID());
		ExtraBeeDefinition.SPATIAL.addProduct(ItemHoneyComb.VanillaComb.QUARTZ.get(), 0.25f);
		ExtraBeeDefinition.SPATIAL.addSpecialty(EnumHoneyComb.CERTUS, 0.05f);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.HERMITIC, UNUSUAL, 5);
		}
	},
	UNUSUAL(ExtraBeeBranchDefinition.QUANTUM, "daniella", true, new Color(0x59a4ba), new Color(0xbaa2eb)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.UNUSUAL.importTemplate(ForestryAllele.BeeSpecies.Ended);
		ExtraBeeDefinition.UNUSUAL.setEffect(ExtraBeesEffect.GRAVITY.getUID());
		ExtraBeeDefinition.UNUSUAL.addProduct(ItemHoneyComb.VanillaComb.QUARTZ.get(), 0.25f);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.SECLUDED, BeeDefinition.ENDED, 5);
		}
	},

	/* BOTANIA BRANCH */
	MYSTICAL(ExtraBeeBranchDefinition.BOTANIA, "mystica", true, new Color(0x46a722), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeeDefinition.MYSTICAL.importTemplate(ForestryAllele.BeeSpecies.Noble);
		for (Map.Entry<ItemStack, Float> entry : BeeDefinition.NOBLE.getProductChances().entrySet()) {
			ExtraBeeDefinition.MYSTICAL.addProduct(entry.getKey(), entry.getValue());
		}

		ExtraBeeDefinition.MYSTICAL.setFlowerProvider(ExtraBeesFlowers.MYSTICAL.getUID());
		for (ExtraBeeDefinition species2 : values()) {
			if (species2.state != EnumBeeSpeciesState.ACTIVE) {
				AlleleManager.alleleRegistry.blacklistAllele(species2.getUID());
			}

			for (EnumBeeChromosome chromo : EnumBeeChromosome.values()) {
				if (chromo != EnumBeeChromosome.HUMIDITY) {
					IAllele allele = species2.template[chromo.ordinal()];
					if (allele == null || !chromo.getAlleleClass().isInstance(allele)) {
						throw new RuntimeException(species2.getName() + " has an invalid " + chromo.toString() + " chromosome!");
					}
				}
			}
		}*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(BeeDefinition.NOBLE, BeeDefinition.MONASTIC, 5);
		}
	};

	private static final EnumSet<ExtraBeeDefinition> overworldHiveBees = EnumSet.of(ROCK);

	private final IBranchDefinition branch;
	private final IAlleleBeeSpeciesCustom species;

	private IAllele[] template;
	private IBeeGenome genome;

	ExtraBeeDefinition(IBranchDefinition branch, String binomial, boolean dominant, Color primary, Color secondary) {
		String lowercaseName = this.toString().toLowerCase(Locale.ENGLISH);
		String species = "species" + WordUtils.capitalize(lowercaseName);
		String uid = "binnie." + species;
		String description = "for.description." + species;
		String name = "for.bees.species." + lowercaseName;

		this.branch = branch;
		if (branch != null) {
			this.species = BeeManager.beeFactory.createSpecies(uid, dominant, "Binnie", name, description, branch.getBranch(), binomial, primary.getRGB(), secondary.getRGB());
		} else {
			this.species = null;
		}
	}

	protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
		// ignored
	}

	protected void setAlleles(IAllele[] template) {
		// ignored
	}

	protected void registerMutations() {
		// can be found in hive
	}

	protected boolean isNeedRegister() {
		return branch != null;
	}

	public static void initBees() {
		for (ExtraBeeDefinition bee : values()) {
			if (bee.isNeedRegister()) {
				bee.init();
			}
		}
		for (ExtraBeeDefinition bee : values()) {
			if (bee.isNeedRegister()) {
				bee.registerMutations();
			}
		}

//		new ExtraBeeMutation(ExtraBeeDefinition.WATER, BeeDefinition.FOREST, ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
//		new ExtraBeeMutation(ExtraBeeDefinition.WATER, BeeDefinition.MEADOWS, ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
//		new ExtraBeeMutation(ExtraBeeDefinition.WATER, BeeDefinition.MODEST, ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
//		new ExtraBeeMutation(ExtraBeeDefinition.WATER, BeeDefinition.TROPICAL, ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
//		new ExtraBeeMutation(ExtraBeeDefinition.WATER, BeeDefinition.MARSHY, ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
//		new ExtraBeeMutation(ExtraBeeDefinition.WATER, BeeDefinition.WINTRY, ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
//		new ExtraBeeMutation(ExtraBeeDefinition.WATER, ExtraBeeDefinition.ROCK, ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
//		new ExtraBeeMutation(ExtraBeeDefinition.WATER, ExtraBeeDefinition.BASALT, ForestryAllele.BeeSpecies.Common.getTemplate(), 15);

//		new ExtraBeeMutation(ExtraBeeDefinition.ROCK, BeeDefinition.FOREST, ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
//		new ExtraBeeMutation(ExtraBeeDefinition.ROCK, BeeDefinition.MEADOWS, ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
//		new ExtraBeeMutation(ExtraBeeDefinition.ROCK, BeeDefinition.MODEST, ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
//		new ExtraBeeMutation(ExtraBeeDefinition.ROCK, BeeDefinition.TROPICAL, ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
//		new ExtraBeeMutation(ExtraBeeDefinition.ROCK, BeeDefinition.MARSHY, ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
//		new ExtraBeeMutation(ExtraBeeDefinition.ROCK, BeeDefinition.WINTRY, ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
//		new ExtraBeeMutation(ExtraBeeDefinition.ROCK, ExtraBeeDefinition.BASALT, ForestryAllele.BeeSpecies.Common.getTemplate(), 15);

//		new ExtraBeeMutation(ExtraBeeDefinition.BASALT, BeeDefinition.FOREST, ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
//		new ExtraBeeMutation(ExtraBeeDefinition.BASALT, BeeDefinition.MEADOWS, ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
//		new ExtraBeeMutation(ExtraBeeDefinition.BASALT, BeeDefinition.MODEST, ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
//		new ExtraBeeMutation(ExtraBeeDefinition.BASALT, BeeDefinition.TROPICAL, ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
//		new ExtraBeeMutation(ExtraBeeDefinition.BASALT, BeeDefinition.MARSHY, ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
//		new ExtraBeeMutation(ExtraBeeDefinition.BASALT, BeeDefinition.WINTRY, ForestryAllele.BeeSpecies.Common.getTemplate(), 15);

//		new ExtraBeeMutation(ExtraBeeDefinition.WATER, BeeDefinition.COMMON, ForestryAllele.BeeSpecies.Cultivated.getTemplate(), 12);
//		new ExtraBeeMutation(ExtraBeeDefinition.ROCK, BeeDefinition.COMMON, ForestryAllele.BeeSpecies.Cultivated.getTemplate(), 12);
//		new ExtraBeeMutation(ExtraBeeDefinition.BASALT, BeeDefinition.COMMON, ForestryAllele.BeeSpecies.Cultivated.getTemplate(), 12);

//		new ExtraBeeMutation(ExtraBeeDefinition.BASALT, BeeDefinition.CULTIVATED, ForestryAllele.BeeSpecies.Sinister.getTemplate(), 60, new ExtraBeeMutation.RequirementBiomeType(BiomeDictionary.Type.NETHER));
//		new ExtraBeeMutation(ExtraBeeDefinition.BASALT, BeeDefinition.SINISTER, ForestryAllele.BeeSpecies.Fiendish.getTemplate(), 40, new ExtraBeeMutation.RequirementBiomeType(BiomeDictionary.Type.NETHER));
	}

	private void init() {
		if (!overworldHiveBees.contains(this)) {
			species.setIsSecret();
		}
		setSpeciesProperties(species);

		template = branch.getTemplate();
		AlleleHelper.instance.set(template, EnumBeeChromosome.SPECIES, species);
		setAlleles(template);

		genome = BeeManager.beeRoot.templateAsGenome(template);
		BeeManager.beeRoot.registerTemplate(template);
	}

	protected final void registerMutation(ExtraBeeDefinition parent1, ExtraBeeDefinition parent2, int chance) {
		registerMutation(parent1.species, parent2.species, chance);
	}

	protected final void registerMutation(BeeDefinition parent1, ExtraBeeDefinition parent2, int chance) {
		registerMutation(parent1.getGenome().getPrimary(), parent2.species, chance);
	}

	protected final void registerMutation(BeeDefinition parent1, BeeDefinition parent2, int chance) {
		registerMutation(parent1.getGenome().getPrimary(), parent2.getGenome().getPrimary(), chance);
	}

	protected final void registerMutation(IAlleleBeeSpecies parent1, IAlleleBeeSpecies parent2, int chance) {
		BeeManager.beeMutationFactory.createMutation(parent1, parent2, getTemplate(), chance);
	}

	@Override
	public final IAllele[] getTemplate() {
		return Arrays.copyOf(template, template.length);
	}

	@Override
	public final IBeeGenome getGenome() {
		return genome;
	}

	@Override
	public final IBee getIndividual() {
		return new Bee(genome);
	}

	@Override
	public final ItemStack getMemberStack(EnumBeeType beeType) {
		IBee bee = getIndividual();
		return BeeManager.beeRoot.getMemberStack(bee, beeType.ordinal());
	}

	public final IBeeDefinition getRainResist() {
		return new BeeVariation.RainResist(this);
	}
}
