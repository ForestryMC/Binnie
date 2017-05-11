package binnie.extrabees.genetics;

import binnie.core.genetics.ForestryAllele;
import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IAlleleBeeSpeciesCustom;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.genetics.IAllele;
import forestry.apiculture.genetics.Bee;
import forestry.apiculture.genetics.BeeVariation;
import forestry.apiculture.genetics.IBeeDefinition;
import forestry.core.genetics.alleles.AlleleHelper;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.text.WordUtils;

import java.awt.*;
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
			ExtraBeesSpecies.ARID
				.importTemplate(ForestryAllele.BeeSpecies.Modest)
				.addProduct(binnie.extrabees.products.EnumHoneyComb.BARREN, 0.30f)
				.setHumidity(EnumHumidity.ARID)
				.setFlowerProvider(ExtraBeesFlowers.DEAD.getUID())
				.setTemperatureTolerance(EnumTolerance.UP_1)*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(ForestryAllele.BeeSpecies.Meadows.getAllele(), ForestryAllele.BeeSpecies.Frugal.getAllele(), 10);
			registerMutation(ForestryAllele.BeeSpecies.Forest.getAllele(), ForestryAllele.BeeSpecies.Frugal.getAllele(), 10);
		}
	},
	BARREN(ExtraBeeBranchDefinition.BARREN, "infelix", true, new Color(0xe0d263), new Color(0xcbe374)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.BARREN
			.importTemplate(ExtraBeesSpecies.ARID)
			.setFertility(EnumAllele.Fertility.LOW)
			.addProduct(EnumHoneyComb.BARREN, 0.30f);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(ForestryAllele.BeeSpecies.Common.getAllele(), BARREN, 10);
		}
	},
	DESOLATE(ExtraBeeBranchDefinition.BARREN, "desolo", false, new Color(0xd1b890), new Color(0xcbe374)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.DESOLATE
			.addProduct(EnumHoneyComb.BARREN, 0.30f)
			.importTemplate(ExtraBeesSpecies.BARREN)
			.setEffect(ExtraBeesEffect.HUNGER.getUID())
			.setNocturnal()
			.setHasEffect(true);*/
		}

		@Override
		protected void registerMutations() {
			registerMutation(ARID, BARREN, 10);
		}
	},
	GNAWING("apica", true, new Color(0xe874b0)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.GNAWING
			.importTemplate(ExtraBeesSpecies.BARREN)
			.setFlowerProvider(ExtraBeesFlowers.WOOD.getUID())
			.addProduct(EnumHoneyComb.BARREN, 0.25f)
			.addSpecialty(EnumHoneyComb.SAWDUST, 0.25f);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	ROTTEN("caries", true, new Color(0xbfe0b6)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.ROTTEN
			.importTemplate(ExtraBeesSpecies.DESOLATE)
			.setNocturnal()
			.setCaveDwelling()
			.setTolerantFlyer()
			.setEffect(ExtraBeesEffect.SPAWN_ZOMBIE.getUID())
			.addProduct(EnumHoneyComb.BARREN, 0.30f)
			.addSpecialty(EnumHoneyComb.ROTTEN, 0.10f);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	BONE("os", true, new Color(0xe9ede8)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.BONE
			.importTemplate(ExtraBeesSpecies.ROTTEN)
			.addProduct(EnumHoneyComb.BARREN, 0.30f)
			.addSpecialty(EnumHoneyComb.BONE, 0.10f)
			.setEffect(ExtraBeesEffect.SPAWN_SKELETON.getUID());*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	CREEPER("erepo", true, new Color(0x2ce615)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.CREEPER
			.importTemplate(ExtraBeesSpecies.ROTTEN)
			.setAllDay()
			.addProduct(EnumHoneyComb.BARREN, 0.30f)
			.addSpecialty(ItemHoneyComb.VanillaComb.POWDERY.get(), 0.08f)
			.setEffect(ExtraBeesEffect.SPAWN_CREEPER.getUID());*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	DECOMPOSING("aegrus", true, new Color(0x523711)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.DECOMPOSING
			.importTemplate(ExtraBeesSpecies.BARREN)
			.addProduct(EnumHoneyComb.BARREN, 0.30f)
			.addSpecialty(EnumHoneyComb.COMPOST, 0.08f);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	ROCK("saxum", true, new Color(0xa8a8a8), new Color(0x999999)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.ROCK
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

		@Override
		protected void registerMutations() {

		}
	},
	STONE("lapis", false, new Color(0x757575), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.STONE
			.addProduct(EnumHoneyComb.STONE, 0.30f)
			.importTemplate(ExtraBeesSpecies.ROCK)
			.setSecondaryColor(rockBody);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	GRANITE("granum", true, new Color(0x695555), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.GRANITE
			.addProduct(EnumHoneyComb.STONE, 0.30f)
			.importTemplate(ExtraBeesSpecies.STONE)
			.setTemperatureTolerance(EnumTolerance.BOTH_2)
			.setHumidityTolerance(EnumTolerance.BOTH_2)
			.setSecondaryColor(rockBody);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	MINERAL("minerale", true, new Color(0x6e757d), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.MINERAL
			.addProduct(EnumHoneyComb.STONE, 0.30f)
			.importTemplate(ExtraBeesSpecies.GRANITE)
			.setSecondaryColor(rockBody);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	COPPER("cuprous", true, new Color(0xd16308), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.COPPER
			.addProduct(EnumHoneyComb.STONE, 0.20f)
			.addSpecialty(EnumHoneyComb.COPPER, 0.06f)
			.importTemplate(ExtraBeesSpecies.MINERAL)
			.setSecondaryColor(rockBody);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	TIN("stannus", true, new Color(0xbdb1bd), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.TIN.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeesSpecies.TIN.addSpecialty(EnumHoneyComb.TIN, 0.06f);
		ExtraBeesSpecies.TIN.importTemplate(ExtraBeesSpecies.MINERAL);
		ExtraBeesSpecies.TIN.setSecondaryColor(rockBody);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	IRON("ferrous", false, new Color(0xa87058), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.IRON.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeesSpecies.IRON.addSpecialty(EnumHoneyComb.IRON, 0.05f);
		ExtraBeesSpecies.IRON.importTemplate(ExtraBeesSpecies.MINERAL);
		ExtraBeesSpecies.IRON.setSecondaryColor(rockBody);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	LEAD("plumbous", true, new Color(0xad8bab), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.LEAD.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeesSpecies.LEAD.addSpecialty(EnumHoneyComb.LEAD, 0.50f);
		ExtraBeesSpecies.LEAD.importTemplate(ExtraBeesSpecies.MINERAL);
		ExtraBeesSpecies.LEAD.setSecondaryColor(rockBody);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	ZINC("spelta", true, new Color(0xedebff), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.ZINC.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeesSpecies.ZINC.addSpecialty(EnumHoneyComb.ZINC, 0.05f);
		ExtraBeesSpecies.ZINC.importTemplate(ExtraBeesSpecies.MINERAL);
		ExtraBeesSpecies.ZINC.setSecondaryColor(rockBody);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	TITANIUM("titania", true, new Color(0xb0aae3), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.TITANIUM.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeesSpecies.TITANIUM.addSpecialty(EnumHoneyComb.TITANIUM, 0.02f);
		ExtraBeesSpecies.TITANIUM.importTemplate(ExtraBeesSpecies.MINERAL);
		ExtraBeesSpecies.TITANIUM.setSecondaryColor(rockBody);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	//BRONZE,
	//BRASS,
	//STEEL,
	TUNGSTATE("wolfram", true, new Color(0x131214), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.TUNGSTATE.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeesSpecies.TUNGSTATE.addSpecialty(EnumHoneyComb.TUNGSTEN, 0.01f);
		ExtraBeesSpecies.TUNGSTATE.importTemplate(ExtraBeesSpecies.MINERAL);
		ExtraBeesSpecies.TUNGSTATE.setSecondaryColor(rockBody);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	GOLD("aureus", true, new Color(0xe6cc0b), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.GOLD.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeesSpecies.GOLD.addSpecialty(EnumHoneyComb.GOLD, 0.02f);
		ExtraBeesSpecies.GOLD.importTemplate(ExtraBeesSpecies.MINERAL);
		ExtraBeesSpecies.GOLD.setSecondaryColor(rockBody);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	SILVER("argentus", false, new Color(0x43455b), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.SILVER.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeesSpecies.SILVER.addSpecialty(EnumHoneyComb.SILVER, 0.02f);
		ExtraBeesSpecies.SILVER.importTemplate(ExtraBeesSpecies.MINERAL);
		ExtraBeesSpecies.SILVER.setSecondaryColor(rockBody);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	//	ELECTRUM,
	PLATINUM("platina", false, new Color(0xdbdbdb), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.PLATINUM.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeesSpecies.PLATINUM.addSpecialty(EnumHoneyComb.PLATINUM, 0.01f);
		ExtraBeesSpecies.PLATINUM.importTemplate(ExtraBeesSpecies.MINERAL);
		ExtraBeesSpecies.PLATINUM.setSecondaryColor(rockBody);*/
		}

		@Override
		protected void registerMutations() {

		}
	},

	LAPIS("lazuli", true, new Color(0x3d2cdb), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.LAPIS.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeesSpecies.LAPIS.addSpecialty(EnumHoneyComb.LAPIS, 0.05f);
		ExtraBeesSpecies.LAPIS.importTemplate(ExtraBeesSpecies.MINERAL);
		ExtraBeesSpecies.LAPIS.setSecondaryColor(rockBody);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	//SODALITE,
	//PYRITE,
	//BAUXITE,
	//CINNABAR,
	//SPHALERITE,
	EMERALD("emerala", true, new Color(0x1cff03), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.EMERALD.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeesSpecies.EMERALD.addSpecialty(EnumHoneyComb.EMERALD, 0.04f);
		ExtraBeesSpecies.EMERALD.importTemplate(ExtraBeesSpecies.MINERAL);
		ExtraBeesSpecies.EMERALD.setSecondaryColor(rockBody);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	RUBY("ruba", true, new Color(0xd60000), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.RUBY.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeesSpecies.RUBY.addSpecialty(EnumHoneyComb.RUBY, 0.03f);
		ExtraBeesSpecies.RUBY.importTemplate(ExtraBeesSpecies.MINERAL);
		ExtraBeesSpecies.RUBY.setSecondaryColor(rockBody);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	SAPPHIRE("saphhira", true, new Color(0x0a47ff), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.SAPPHIRE.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeesSpecies.SAPPHIRE.addSpecialty(EnumHoneyComb.SAPPHIRE, 0.03f);
		ExtraBeesSpecies.SAPPHIRE.importTemplate(ExtraBeesSpecies.MINERAL);
		ExtraBeesSpecies.SAPPHIRE.setSecondaryColor(rockBody);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	//OLIVINE,
	DIAMOND("diama", true, new Color(0x7fbdfa), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.DIAMOND.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeesSpecies.DIAMOND.addSpecialty(EnumHoneyComb.DIAMOND, 0.01f);
		ExtraBeesSpecies.DIAMOND.importTemplate(ExtraBeesSpecies.MINERAL);
		ExtraBeesSpecies.DIAMOND.setSecondaryColor(rockBody);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	UNSTABLE("levis", false, new Color(0x3e8c34), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.UNSTABLE.importTemplate(ExtraBeesSpecies.MINERAL);
		ExtraBeesSpecies.UNSTABLE.addProduct(EnumHoneyComb.BARREN, 0.20f);
		ExtraBeesSpecies.UNSTABLE.setEffect(ExtraBeesEffect.RADIOACTIVE.getUID());
		ExtraBeesSpecies.UNSTABLE.setFertility(EnumAllele.Fertility.LOW);
		ExtraBeesSpecies.UNSTABLE.setLifespan(EnumAllele.Lifespan.SHORTEST);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	NUCLEAR("nucleus", false, new Color(0x41cc2f), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.NUCLEAR.importTemplate(ExtraBeesSpecies.UNSTABLE);
		ExtraBeesSpecies.NUCLEAR.addProduct(EnumHoneyComb.BARREN, 0.20f);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	RADIOACTIVE("fervens", false, new Color(0x1eff00), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.RADIOACTIVE.importTemplate(ExtraBeesSpecies.NUCLEAR);
		ExtraBeesSpecies.RADIOACTIVE.addProduct(EnumHoneyComb.BARREN, 0.20f);
		ExtraBeesSpecies.RADIOACTIVE.addSpecialty(EnumHoneyComb.URANIUM, 0.02f);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	ANCIENT("antiquus", true, new Color(0xf2db8f), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.ANCIENT.importTemplate(ForestryAllele.BeeSpecies.Noble);
		ExtraBeesSpecies.ANCIENT.addProduct(EnumHoneyComb.OLD, 0.30f);
		ExtraBeesSpecies.ANCIENT.setLifespan(EnumAllele.Lifespan.ELONGATED);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	PRIMEVAL("priscus", true, new Color(0xb3a67b), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.PRIMEVAL.importTemplate(ExtraBeesSpecies.ANCIENT);
		ExtraBeesSpecies.PRIMEVAL.addProduct(EnumHoneyComb.OLD, 0.30f);
		ExtraBeesSpecies.PRIMEVAL.setLifespan(EnumAllele.Lifespan.LONG);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	PREHISTORIC("pristinus", false, new Color(0x6e5a40), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.PREHISTORIC.importTemplate(ExtraBeesSpecies.ANCIENT);
		ExtraBeesSpecies.PREHISTORIC.addProduct(EnumHoneyComb.OLD, 0.30f);
		ExtraBeesSpecies.PREHISTORIC.setLifespan(EnumAllele.Lifespan.LONGER);
		ExtraBeesSpecies.PREHISTORIC.setFertility(EnumAllele.Fertility.LOW);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	RELIC("sapiens", true, new Color(0x4d3e16), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.RELIC.importTemplate(ExtraBeesSpecies.ANCIENT);
		ExtraBeesSpecies.RELIC.addProduct(EnumHoneyComb.OLD, 0.30f);
		ExtraBeesSpecies.RELIC.setHasEffect(true);
		ExtraBeesSpecies.RELIC.setLifespan(EnumAllele.Lifespan.LONGEST);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	COAL("carbo", true, new Color(0x7a7648), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.COAL.importTemplate(ExtraBeesSpecies.ANCIENT);
		ExtraBeesSpecies.COAL.setLifespan(EnumAllele.Lifespan.NORMAL);
		ExtraBeesSpecies.COAL.addProduct(EnumHoneyComb.OLD, 0.20f);
		ExtraBeesSpecies.COAL.addSpecialty(EnumHoneyComb.COAL, 0.08f);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	RESIN("lacrima", false, new Color(0xa6731b), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.RESIN.importTemplate(ExtraBeesSpecies.COAL);
		ExtraBeesSpecies.RESIN.addProduct(EnumHoneyComb.OLD, 0.20f);
		ExtraBeesSpecies.RESIN.addSpecialty(EnumHoneyComb.RESIN, 0.05f);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	OIL("lubricus", true, new Color(0x574770), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.OIL.importTemplate(ExtraBeesSpecies.COAL);
		ExtraBeesSpecies.OIL.addProduct(EnumHoneyComb.OLD, 0.20f);
		ExtraBeesSpecies.OIL.addSpecialty(EnumHoneyComb.OIL, 0.05f);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	//	PEAT,
	DISTILLED("distilli", false, new Color(0x356356), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.DISTILLED.importTemplate(ExtraBeesSpecies.OIL);
		ExtraBeesSpecies.DISTILLED.addProduct(EnumHoneyComb.OIL, 0.10f);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	FUEL("refina", true, new Color(0xffc003), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.FUEL.importTemplate(ExtraBeesSpecies.OIL);
		ExtraBeesSpecies.FUEL.addProduct(EnumHoneyComb.OIL, 0.10f);
		ExtraBeesSpecies.FUEL.addSpecialty(EnumHoneyComb.FUEL, 0.04f);
		ExtraBeesSpecies.FUEL.setHasEffect(true);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	CREOSOTE("creosota", true, new Color(0x979e13), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.CREOSOTE.importTemplate(ExtraBeesSpecies.COAL);
		ExtraBeesSpecies.CREOSOTE.addProduct(EnumHoneyComb.COAL, 0.10f);
		ExtraBeesSpecies.CREOSOTE.addSpecialty(EnumHoneyComb.CREOSOTE, 0.07f);
		ExtraBeesSpecies.CREOSOTE.setHasEffect(true);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	LATEX("latex", true, new Color(0x494a3e), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.LATEX.importTemplate(ExtraBeesSpecies.RESIN);
		ExtraBeesSpecies.LATEX.addProduct(EnumHoneyComb.RESIN, 0.10f);
		ExtraBeesSpecies.LATEX.addSpecialty(EnumHoneyComb.LATEX, 0.05f);
		ExtraBeesSpecies.LATEX.setHasEffect(true);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	WATER("aqua", true, new Color(0x94a2ff), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.WATER.addProduct(EnumHoneyComb.WATER, 0.30f);
		ExtraBeesSpecies.WATER.setIsSecret(false);
		ExtraBeesSpecies.WATER.setTolerantFlyer();
		ExtraBeesSpecies.WATER.setHumidityTolerance(EnumTolerance.BOTH_1);
		ExtraBeesSpecies.WATER.setFlowerProvider(ExtraBeesFlowers.WATER.getUID());
		ExtraBeesSpecies.WATER.setFlowering(EnumAllele.Flowering.SLOW);
		ExtraBeesSpecies.WATER.setEffect(ExtraBeesEffect.WATER.getUID());
		ExtraBeesSpecies.WATER.setHumidity(EnumHumidity.DAMP);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	RIVER("flumen", true, new Color(0x83b3d4), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.RIVER.importTemplate(ExtraBeesSpecies.WATER);
		ExtraBeesSpecies.RIVER.addProduct(EnumHoneyComb.WATER, 0.30f);
		ExtraBeesSpecies.RIVER.addSpecialty(EnumHoneyComb.CLAY, 0.20f);
		ExtraBeesSpecies.RIVER.importTemplate(ExtraBeesSpecies.WATER);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	OCEAN("mare", false, new Color(0x1d2ead), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.OCEAN.importTemplate(ExtraBeesSpecies.WATER);
		ExtraBeesSpecies.OCEAN.addProduct(EnumHoneyComb.WATER, 0.30f);
		ExtraBeesSpecies.OCEAN.importTemplate(ExtraBeesSpecies.WATER);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	INK("atramentum", true, new Color(0xe1447), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.INK.importTemplate(ExtraBeesSpecies.OCEAN);
		ExtraBeesSpecies.INK.addProduct(EnumHoneyComb.WATER, 0.30f);
		ExtraBeesSpecies.INK.addSpecialty(new ItemStack(Items.dye, 1, 0), 0.10f);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	GROWING("tyrelli", true, new Color(0x5bebd8), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.GROWING.importTemplate(ForestryAllele.BeeSpecies.Forest);
		ExtraBeesSpecies.GROWING.addProduct(ItemHoneyComb.VanillaComb.HONEY.get(), 0.35f);
		ExtraBeesSpecies.GROWING.setFlowering(EnumAllele.Flowering.AVERAGE);
		ExtraBeesSpecies.GROWING.setFlowerProvider(ExtraBeesFlowers.LEAVES.getUID());*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	THRIVING("thriva", true, new Color(0x34e37d), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.THRIVING.importTemplate(ExtraBeesSpecies.GROWING);
		ExtraBeesSpecies.THRIVING.addProduct(ItemHoneyComb.VanillaComb.HONEY.get(), 0.35f);
		ExtraBeesSpecies.THRIVING.setFlowering(EnumAllele.Flowering.FAST);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	BLOOMING("blooma", true, new Color(0x0abf34), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.BLOOMING.importTemplate(ExtraBeesSpecies.THRIVING);
		ExtraBeesSpecies.BLOOMING.setFlowering(EnumAllele.Flowering.FASTEST);
		ExtraBeesSpecies.BLOOMING.addProduct(ItemHoneyComb.VanillaComb.HONEY.get(), 0.35f);
		ExtraBeesSpecies.BLOOMING.setFlowerProvider(ExtraBeesFlowers.SAPLING.getUID());
		ExtraBeesSpecies.BLOOMING.setEffect(ExtraBeesEffect.BONEMEAL_SAPLING.getUID());*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	SWEET("mellitus", true, new Color(0xfc51f1), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.SWEET.importTemplate(ForestryAllele.BeeSpecies.Rural);
		ExtraBeesSpecies.SWEET.addProduct(ItemHoneyComb.VanillaComb.HONEY.get(), 0.40f);
		ExtraBeesSpecies.SWEET.addProduct(new ItemStack(Items.sugar, 1, 0), 0.10f);
		ExtraBeesSpecies.SWEET.setFlowerProvider(ExtraBeesFlowers.SUGAR.getUID());*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	SUGAR("dulcis", true, new Color(0xe6d3e0), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.SUGAR.addProduct(ItemHoneyComb.VanillaComb.HONEY.get(), 0.40f);
		ExtraBeesSpecies.SUGAR.addProduct(new ItemStack(Items.sugar, 1, 0), 0.20f);
		ExtraBeesSpecies.SUGAR.importTemplate(ExtraBeesSpecies.SWEET);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	RIPENING("ripa", true, new Color(0xb2c75d), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.RIPENING.addProduct(ItemHoneyComb.VanillaComb.HONEY.get(), 0.30f);
		ExtraBeesSpecies.RIPENING.addProduct(new ItemStack(Items.sugar, 1, 0), 0.10f);
		ExtraBeesSpecies.RIPENING.addSpecialty(EnumHoneyComb.FRUIT, 0.10f);
		ExtraBeesSpecies.RIPENING.setFlowerProvider(ExtraBeesFlowers.FRUIT.getUID());
		ExtraBeesSpecies.RIPENING.importTemplate(ExtraBeesSpecies.SUGAR);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	FRUIT("pomum", true, new Color(0xdb5876), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.FRUIT.importTemplate(ExtraBeesSpecies.RIPENING);
		ExtraBeesSpecies.FRUIT.addProduct(ItemHoneyComb.VanillaComb.HONEY.get(), 0.30f);
		ExtraBeesSpecies.FRUIT.addProduct(new ItemStack(Items.sugar, 1, 0), 0.15f);
		ExtraBeesSpecies.FRUIT.addSpecialty(EnumHoneyComb.FRUIT, 0.20f);
		ExtraBeesSpecies.FRUIT.setEffect(ExtraBeesEffect.BONEMEAL_FRUIT.getUID());
		ExtraBeesSpecies.FRUIT.setHasEffect(true);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	ALCOHOL("vinum", false, new Color(0xe88a61), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.ALCOHOL.importTemplate(ExtraBeesSpecies.SWEET);
		ExtraBeesSpecies.ALCOHOL.addProduct(ItemHoneyComb.VanillaComb.WHEATEN.get(), 0.30f);
		ExtraBeesSpecies.ALCOHOL.addSpecialty(EnumHoneyComb.ALCOHOL, 0.10f);
		ExtraBeesSpecies.ALCOHOL.setEffect("forestry.effectDrunkard");*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	FARM("ager", true, new Color(0x75db60), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.FARM.addProduct(ItemHoneyComb.VanillaComb.WHEATEN.get(), 0.30f);
		ExtraBeesSpecies.FARM.addSpecialty(EnumHoneyComb.SEED, 0.10f);
		ExtraBeesSpecies.FARM.importTemplate(ForestryAllele.BeeSpecies.Rural);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	MILK("lacteus", true, new Color(0xe3e8e8), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.MILK.addProduct(ItemHoneyComb.VanillaComb.WHEATEN.get(), 0.30f);
		ExtraBeesSpecies.MILK.addSpecialty(EnumHoneyComb.MILK, 0.10f);
		ExtraBeesSpecies.MILK.importTemplate(ForestryAllele.BeeSpecies.Rural);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	COFFEE("arabica", true, new Color(0x8c5e30), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.COFFEE.addProduct(ItemHoneyComb.VanillaComb.WHEATEN.get(), 0.30f);
		ExtraBeesSpecies.COFFEE.addSpecialty(EnumHoneyComb.COFFEE, 0.08f);
		ExtraBeesSpecies.COFFEE.importTemplate(ForestryAllele.BeeSpecies.Rural);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	//CITRUS,
	//MINT,
	SWAMP("paludis", true, new Color(0x356933), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.SWAMP.addProduct(ItemHoneyComb.VanillaComb.MOSSY.get(), 0.30f);
		ExtraBeesSpecies.SWAMP.importTemplate(ForestryAllele.BeeSpecies.Marshy);
		ExtraBeesSpecies.SWAMP.setHumidity(EnumHumidity.DAMP);
		ExtraBeesSpecies.SWAMP.setEffect(ExtraBeesEffect.SLOW.getUID());*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	BOGGY("lama", false, new Color(0x785c29), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.BOGGY.importTemplate(ExtraBeesSpecies.SWAMP);
		ExtraBeesSpecies.BOGGY.addProduct(ItemHoneyComb.VanillaComb.MOSSY.get(), 0.30f);
		ExtraBeesSpecies.BOGGY.importTemplate(ExtraBeesSpecies.SWAMP);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	FUNGAL("boletus", true, new Color(0xd16200), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.FUNGAL.importTemplate(ExtraBeesSpecies.BOGGY);
		ExtraBeesSpecies.FUNGAL.addProduct(ItemHoneyComb.VanillaComb.MOSSY.get(), 0.30f);
		ExtraBeesSpecies.FUNGAL.addSpecialty(EnumHoneyComb.FUNGAL, 0.15f);
		ExtraBeesSpecies.FUNGAL.importTemplate(ExtraBeesSpecies.BOGGY);
		ExtraBeesSpecies.FUNGAL.setEffect(ExtraBeesEffect.BONEMEAL_MUSHROOM.getUID());
		ExtraBeesSpecies.FUNGAL.setHasEffect(true);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	//MARBLE,
	//ROMAN,
	//GREEK,
	//CLASSICAL,
	BASALT("aceri", true, new Color(0x8c6969), new Color(0x9a2323)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.BASALT.addProduct(ItemHoneyComb.VanillaComb.SIMMERING.get(), 0.25f);
		ExtraBeesSpecies.BASALT.importTemplate(ForestryAllele.BeeSpecies.Sinister);
		ExtraBeesSpecies.BASALT.setEffect("forestry.effectAggressive");
		ExtraBeesSpecies.BASALT.setHumidity(EnumHumidity.ARID);
		ExtraBeesSpecies.BASALT.setTemperature(EnumTemperature.HELLISH);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	TEMPERED("iratus", false, new Color(0x8a4848), new Color(0x9a2323)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.TEMPERED.addProduct(ItemHoneyComb.VanillaComb.SIMMERING.get(), 0.25f);
		ExtraBeesSpecies.TEMPERED.importTemplate(ExtraBeesSpecies.BASALT);
		ExtraBeesSpecies.TEMPERED.setEffect(ExtraBeesEffect.METEOR.getUID());*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	//ANGRY,
	VOLCANIC("volcano", true, new Color(0x4d0c0c), new Color(0x9a2323)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.VOLCANIC.importTemplate(ExtraBeesSpecies.TEMPERED);
		ExtraBeesSpecies.VOLCANIC.addProduct(ItemHoneyComb.VanillaComb.SIMMERING.get(), 0.25f);
		ExtraBeesSpecies.VOLCANIC.addSpecialty(EnumHoneyComb.BLAZE, 0.10f);
		ExtraBeesSpecies.VOLCANIC.setHasEffect(true);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	MALICIOUS("acerbus", true, new Color(0x782a77), new Color(0x069764)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.MALICIOUS.importTemplate(ForestryAllele.BeeSpecies.Tropical);
		ExtraBeesSpecies.MALICIOUS.addProduct(ItemHoneyComb.VanillaComb.SILKY.get(), 0.25f);
		ExtraBeesSpecies.MALICIOUS.setHumidity(EnumHumidity.DAMP);
		ExtraBeesSpecies.MALICIOUS.setTemperature(EnumTemperature.WARM);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	INFECTIOUS("contagio", true, new Color(0xb82eb5), new Color(0x069764)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.INFECTIOUS.importTemplate(ExtraBeesSpecies.MALICIOUS);
		ExtraBeesSpecies.INFECTIOUS.addProduct(ItemHoneyComb.VanillaComb.SILKY.get(), 0.25f);
		ExtraBeesSpecies.INFECTIOUS.setFlowering(EnumAllele.Flowering.SLOW);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	VIRULENT("morbus", false, new Color(0xf013ec), new Color(0x069764)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.VIRULENT.importTemplate(ExtraBeesSpecies.INFECTIOUS);
		ExtraBeesSpecies.VIRULENT.addProduct(ItemHoneyComb.VanillaComb.SILKY.get(), 0.25f);
		ExtraBeesSpecies.VIRULENT.addSpecialty(EnumHoneyComb.VENOMOUS, 0.12f);
		ExtraBeesSpecies.VIRULENT.setFlowering(EnumAllele.Flowering.AVERAGE);
		ExtraBeesSpecies.VIRULENT.setHasEffect(true);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	VISCOUS("liquidus", true, new Color(0x9470e), new Color(0x069764)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.VISCOUS.importTemplate(ForestryAllele.BeeSpecies.Tropical);
		ExtraBeesSpecies.VISCOUS.setEffect(ExtraBeesEffect.ECTOPLASM.getUID());
		ExtraBeesSpecies.VISCOUS.addProduct(ItemHoneyComb.VanillaComb.SILKY.get(), 0.25f);
		ExtraBeesSpecies.VISCOUS.setHumidity(EnumHumidity.DAMP);
		ExtraBeesSpecies.VISCOUS.setSpeed(EnumAllele.Speed.SLOW);
		ExtraBeesSpecies.VISCOUS.setTemperature(EnumTemperature.WARM);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	GLUTINOUS("glutina", true, new Color(0x1d8c27), new Color(0x069764)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.GLUTINOUS.importTemplate(ExtraBeesSpecies.VISCOUS);
		ExtraBeesSpecies.GLUTINOUS.addProduct(ItemHoneyComb.VanillaComb.SILKY.get(), 0.25f);
		ExtraBeesSpecies.GLUTINOUS.setSpeed(EnumAllele.Speed.NORMAL);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	STICKY("lentesco", true, new Color(0x17e328), new Color(0x069764)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.STICKY.importTemplate(ExtraBeesSpecies.GLUTINOUS);
		ExtraBeesSpecies.STICKY.addProduct(ItemHoneyComb.VanillaComb.SILKY.get(), 0.25f);
		ExtraBeesSpecies.STICKY.addSpecialty(EnumHoneyComb.SLIME, 0.12f);
		ExtraBeesSpecies.STICKY.setSpeed(EnumAllele.Speed.FAST);
		ExtraBeesSpecies.STICKY.setHasEffect(true);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	CORROSIVE("corrumpo", false, new Color(0x4a5c0b), new Color(0x069764)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.CORROSIVE.importTemplate(ExtraBeesSpecies.STICKY);
		ExtraBeesSpecies.CORROSIVE.setHumidity(EnumHumidity.DAMP);
		ExtraBeesSpecies.CORROSIVE.setTemperature(EnumTemperature.WARM);
		ExtraBeesSpecies.CORROSIVE.setEffect(ExtraBeesEffect.ACID.getUID());
		ExtraBeesSpecies.CORROSIVE.setFlowering(EnumAllele.Flowering.AVERAGE);
		ExtraBeesSpecies.CORROSIVE.addProduct(ItemHoneyComb.VanillaComb.SILKY.get(), 20f);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	CAUSTIC("torrens", true, new Color(0x84a11d), new Color(0x069764)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.CAUSTIC.importTemplate(ExtraBeesSpecies.CORROSIVE);
		ExtraBeesSpecies.CAUSTIC.addProduct(ItemHoneyComb.VanillaComb.SILKY.get(), 0.25f);
		ExtraBeesSpecies.CAUSTIC.addSpecialty(EnumHoneyComb.ACIDIC, 0.03f);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	ACIDIC("acidus", true, new Color(0xc0f016), new Color(0x069764)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.ACIDIC.importTemplate(ExtraBeesSpecies.CAUSTIC);
		ExtraBeesSpecies.ACIDIC.addProduct(ItemHoneyComb.VanillaComb.SILKY.get(), 0.20f);
		ExtraBeesSpecies.ACIDIC.addSpecialty(EnumHoneyComb.ACIDIC, 0.16f);
		ExtraBeesSpecies.ACIDIC.setHasEffect(true);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	EXCITED("excita", true, new Color(0xff4545), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.EXCITED.setEffect(ExtraBeesEffect.LIGHTNING.getUID());
		ExtraBeesSpecies.EXCITED.addProduct(EnumHoneyComb.REDSTONE, 0.10f);
		ExtraBeesSpecies.EXCITED.setCaveDwelling();
		ExtraBeesSpecies.EXCITED.setFlowerProvider(ExtraBeesFlowers.REDSTONE.getUID());*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	ENERGETIC("energia", false, new Color(0xe835c7), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.ENERGETIC.importTemplate(ExtraBeesSpecies.EXCITED);
		ExtraBeesSpecies.ENERGETIC.setEffect(ExtraBeesEffect.LIGHTNING.getUID());
		ExtraBeesSpecies.ENERGETIC.addProduct(EnumHoneyComb.REDSTONE, 0.12f);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	ECSTATIC("ecstatica", true, new Color(0xaf35e8), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.ECSTATIC.importTemplate(ExtraBeesSpecies.ENERGETIC);
		ExtraBeesSpecies.ECSTATIC.setEffect(ExtraBeesEffect.Power.getUID());
		ExtraBeesSpecies.ECSTATIC.addProduct(EnumHoneyComb.REDSTONE, 0.20f);
		ExtraBeesSpecies.ECSTATIC.addSpecialty(EnumHoneyComb.IC2ENERGY, 0.08f);
		ExtraBeesSpecies.ECSTATIC.setHasEffect(true);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	ARTIC("artica", true, new Color(0xade0e0), new Color(0xdaf5f3)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.ARTIC.importTemplate(ForestryAllele.BeeSpecies.Wintry);
		ExtraBeesSpecies.ARTIC.addProduct(ItemHoneyComb.VanillaComb.FROZEN.get(), 0.25f);
		ExtraBeesSpecies.ARTIC.setTemperature(EnumTemperature.ICY);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	FREEZING("glacia", true, new Color(0x7be3e3), new Color(0xdaf5f3)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.FREEZING.importTemplate(ExtraBeesSpecies.ARTIC);
		ExtraBeesSpecies.FREEZING.addProduct(ItemHoneyComb.VanillaComb.FROZEN.get(), 0.20f);
		ExtraBeesSpecies.FREEZING.addSpecialty(EnumHoneyComb.GLACIAL, 0.10f);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	SHADOW("shadowa", false, new Color(0x595959), new Color(0x333333)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.SHADOW.importTemplate(ExtraBeesSpecies.BASALT);
		ExtraBeesSpecies.SHADOW.setNocturnal();
		ExtraBeesSpecies.SHADOW.addProduct(EnumHoneyComb.SHADOW, 0.05f);
		ExtraBeesSpecies.SHADOW.setEffect(ExtraBeesEffect.BLINDNESS.getUID());
		ExtraBeesSpecies.SHADOW.setAllDay(false);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	DARKENED("darka", true, new Color(0x332e33), new Color(0x333333)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.DARKENED.addProduct(EnumHoneyComb.SHADOW, 0.10f);
		ExtraBeesSpecies.DARKENED.setNocturnal();
		ExtraBeesSpecies.DARKENED.importTemplate(ExtraBeesSpecies.SHADOW);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	ABYSS("abyssba", true, new Color(0x210821), new Color(0x333333)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.ABYSS.importTemplate(ExtraBeesSpecies.DARKENED);
		ExtraBeesSpecies.ABYSS.setNocturnal();
		ExtraBeesSpecies.ABYSS.addProduct(EnumHoneyComb.SHADOW, 0.25f);
		ExtraBeesSpecies.ABYSS.setEffect(ExtraBeesEffect.WITHER.getUID());
		ExtraBeesSpecies.ABYSS.setHasEffect(true);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	RED("rubra", true, new Color(0xff0000), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {

		}

		@Override
		protected void registerMutations() {

		}
	},
	YELLOW("fulvus", true, new Color(0xffdd00), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {

		}

		@Override
		protected void registerMutations() {

		}
	},
	BLUE("caeruleus", true, new Color(0x0022ff), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {

		}

		@Override
		protected void registerMutations() {

		}
	},
	GREEN("prasinus", true, new Color(0x009900), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {

		}

		@Override
		protected void registerMutations() {

		}
	},
	BLACK("niger", true, new Color(0x575757), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {

		}

		@Override
		protected void registerMutations() {

		}
	},
	WHITE("albus", true, new Color(0xffffff), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {

		}

		@Override
		protected void registerMutations() {

		}
	},
	BROWN("fuscus", true, new Color(0x5c350f), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {

		}

		@Override
		protected void registerMutations() {

		}
	},
	ORANGE("flammeus", true, new Color(0xff9d00), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {

		}

		@Override
		protected void registerMutations() {

		}
	},
	CYAN("cyana", true, new Color(0x00ffe5), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {

		}

		@Override
		protected void registerMutations() {

		}
	},
	PURPLE("purpureus", true, new Color(0xae00ff), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {

		}

		@Override
		protected void registerMutations() {

		}
	},
	GRAY("ravus", true, new Color(0xbababa), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {

		}

		@Override
		protected void registerMutations() {

		}
	},
	LIGHTBLUE("aqua", true, new Color(0x009dff), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {

		}

		@Override
		protected void registerMutations() {

		}
	},
	PINK("rosaceus", true, new Color(0xff80df), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {

		}

		@Override
		protected void registerMutations() {

		}
	},
	LIMEGREEN("lima", true, new Color(0x00ff08), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {

		}

		@Override
		protected void registerMutations() {

		}
	},
	MAGENTA("fuchsia", true, new Color(0xff00cc), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {

		}

		@Override
		protected void registerMutations() {

		}
	},
	LIGHTGRAY("canus", true, new Color(0xc9c9c9), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {

		}

		@Override
		protected void registerMutations() {

		}
	},
	CELEBRATORY("celeba", true, new Color(0xfa0a6a), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.CELEBRATORY.importTemplate(ForestryAllele.BeeSpecies.Merry);
		ExtraBeesSpecies.CELEBRATORY.setEffect(ExtraBeesEffect.FIREWORKS.getUID());*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	JADED("jadeca", true, new Color(0xfa0a6a), new Color(0xdc8aeb)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.JADED.importTemplate(ForestryAllele.BeeSpecies.Imperial);
		ExtraBeesSpecies.JADED.setFertility(EnumAllele.Fertility.MAXIMUM);
		ExtraBeesSpecies.JADED.setFlowering(EnumAllele.Flowering.MAXIMUM);
		ExtraBeesSpecies.JADED.setTerritory(EnumAllele.Territory.LARGEST);
		ExtraBeesSpecies.JADED.addProduct(ItemHoneyComb.VanillaComb.HONEY.get(), 0.30f);
		ExtraBeesSpecies.JADED.addSpecialty(Mods.Forestry.stack("pollen"), 0.20f);
		ExtraBeesSpecies.JADED.setHasEffect(true);
		ExtraBeesSpecies.JADED.addSpecialty(EnumHoneyComb.PURPLE, 0.15f);
		ExtraBeesSpecies.JADED.isCounted = false;*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	GLOWSTONE("glowia", true, new Color(0xe0c61b), new Color(0x9a2323)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.GLOWSTONE.importTemplate(ExtraBeesSpecies.BASALT);
		ExtraBeesSpecies.GLOWSTONE.addProduct(EnumHoneyComb.GLOWSTONE, 0.15f);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	HAZARDOUS("infensus", true, new Color(0xb06c28), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.HAZARDOUS.importTemplate(ForestryAllele.BeeSpecies.Austere);
		ExtraBeesSpecies.HAZARDOUS.addProduct(EnumHoneyComb.SALTPETER, 0.12f);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	NICKEL("claro", true, new Color(0xffdefc), new Color(0x999999)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.NICKEL.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeesSpecies.NICKEL.addSpecialty(EnumHoneyComb.NICKEL, 0.05f);
		ExtraBeesSpecies.NICKEL.importTemplate(ExtraBeesSpecies.MINERAL);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	//INVAR,
	QUANTUM("quanta", true, new Color(0x37c5db), new Color(0xd50fdb)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.QUANTUM.importTemplate(ExtraBeesSpecies.QUANTUM);
		ExtraBeesSpecies.QUANTUM.setEffect(ExtraBeesEffect.TELEPORT.getUID());
		ExtraBeesSpecies.QUANTUM.addProduct(ItemHoneyComb.VanillaComb.QUARTZ.get(), 0.25f);
		ExtraBeesSpecies.QUANTUM.addSpecialty(EnumHoneyComb.CERTUS, 0.15f);
		ExtraBeesSpecies.QUANTUM.addSpecialty(EnumHoneyComb.ENDERPEARL, 0.15f);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	SPATIAL("spatia", true, new Color(0x4c1be0), new Color(0xa44ecc)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.SPATIAL.importTemplate(ExtraBeesSpecies.UNUSUAL);
		ExtraBeesSpecies.SPATIAL.setEffect(ExtraBeesEffect.GRAVITY.getUID());
		ExtraBeesSpecies.SPATIAL.addProduct(ItemHoneyComb.VanillaComb.QUARTZ.get(), 0.25f);
		ExtraBeesSpecies.SPATIAL.addSpecialty(EnumHoneyComb.CERTUS, 0.05f);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	UNUSUAL("daniella", true, new Color(0x59a4ba), new Color(0xbaa2eb)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.UNUSUAL.importTemplate(ForestryAllele.BeeSpecies.Ended);
		ExtraBeesSpecies.UNUSUAL.setEffect(ExtraBeesEffect.GRAVITY.getUID());
		ExtraBeesSpecies.UNUSUAL.addProduct(ItemHoneyComb.VanillaComb.QUARTZ.get(), 0.25f);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	YELLORIUM("yellori", true, new Color(0xd5ed00), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.YELLORIUM
			.importTemplate(ExtraBeesSpecies.NUCLEAR)
			.addProduct(EnumHoneyComb.BARREN, 0.20f)
			.addSpecialty(EnumHoneyComb.YELLORIUM, 0.02f)
			.setEffect(ExtraBeesEffect.RADIOACTIVE.getUID())
			.setFertility(EnumAllele.Fertility.LOW)
			.setLifespan(EnumAllele.Lifespan.SHORTEST);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	CYANITE("cyanita", true, new Color(0x0086ed), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.CYANITE
			.importTemplate(ExtraBeesSpecies.YELLORIUM)
			.addProduct(EnumHoneyComb.BARREN, 0.20f)
			.addSpecialty(EnumHoneyComb.CYANITE, 0.01f);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	BLUTONIUM("caruthus", true, new Color(0x1b00e6), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.BLUTONIUM
			.importTemplate(ExtraBeesSpecies.CYANITE)
			.addProduct(EnumHoneyComb.BARREN, 0.20f)
			.addSpecialty(EnumHoneyComb.BLUTONIUM, 0.01f);*/
		}

		@Override
		protected void registerMutations() {

		}
	},
	MYSTICAL("mystica", true, new Color(0x46a722), new Color(0xffffff)) {
		@Override
		protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {

		}

		@Override
		protected void setAlleles(IAllele[] template) {
			/*ExtraBeesSpecies.MYSTICAL.importTemplate(ForestryAllele.BeeSpecies.Noble);
		for (Map.Entry<ItemStack, Float> entry : ForestryAllele.BeeSpecies.Noble.getAllele().getProductChances().entrySet()) {
			ExtraBeesSpecies.MYSTICAL.addProduct(entry.getKey(), entry.getValue());
		}

		ExtraBeesSpecies.MYSTICAL.setFlowerProvider(ExtraBeesFlowers.MYSTICAL.getUID());
		for (ExtraBeesSpecies species2 : values()) {
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

		}
	};

	private static final EnumSet<ExtraBeeDefinition> overworldHiveBees = EnumSet.of(ROCK);

	private final ExtraBeeBranchDefinition branch;
	private final IAlleleBeeSpeciesCustom species;

	private IAllele[] template;
	private IBeeGenome genome;

	ExtraBeeDefinition(ExtraBeeBranchDefinition branch, String binomial, boolean dominant, Color primary, Color secondary) {
		String lowercaseName = this.toString().toLowerCase(Locale.ENGLISH);
		String species = "species" + WordUtils.capitalize(lowercaseName);
		String uid = "binnie." + species;
		String description = "for.description." + species;
		String name = "for.bees.species." + lowercaseName;

		this.branch = branch;
		this.species = BeeManager.beeFactory.createSpecies(uid, dominant, "Binnie", name, description, branch.getBranch(), binomial, primary.getRGB(), secondary.getRGB());
	}

	protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
		// ignored
	}

	protected void setAlleles(IAllele[] template) {
		// ignored
	}

	protected abstract void registerMutations();

	public static void initBees() {
		for (ExtraBeeDefinition bee : values()) {
			bee.init();
		}
		for (ExtraBeeDefinition bee : values()) {
			bee.registerMutations();
		}
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

	protected final void registerMutation(IAlleleBeeSpecies parent1, ExtraBeeDefinition parent2, int chance) {
		registerMutation(parent1, parent2.species, chance);
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
