package binnie.extrabees.genetics;

import com.google.common.base.Preconditions;
import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeMutationBuilder;
import forestry.api.climate.IClimateProvider;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IGenome;
import forestry.api.genetics.IMutationCondition;
import forestry.apiculture.genetics.BeeDefinition;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;

import static forestry.apiculture.genetics.BeeDefinition.AUSTERE;
import static forestry.apiculture.genetics.BeeDefinition.BOGGY;
import static forestry.apiculture.genetics.BeeDefinition.COMMON;
import static forestry.apiculture.genetics.BeeDefinition.CULTIVATED;
import static forestry.apiculture.genetics.BeeDefinition.DEMONIC;
import static forestry.apiculture.genetics.BeeDefinition.DILIGENT;
import static forestry.apiculture.genetics.BeeDefinition.ENDED;
import static forestry.apiculture.genetics.BeeDefinition.EXOTIC;
import static forestry.apiculture.genetics.BeeDefinition.FARMERLY;
import static forestry.apiculture.genetics.BeeDefinition.FIENDISH;
import static forestry.apiculture.genetics.BeeDefinition.FOREST;
import static forestry.apiculture.genetics.BeeDefinition.FRUGAL;
import static forestry.apiculture.genetics.BeeDefinition.HERMITIC;
import static forestry.apiculture.genetics.BeeDefinition.IMPERIAL;
import static forestry.apiculture.genetics.BeeDefinition.INDUSTRIOUS;
import static forestry.apiculture.genetics.BeeDefinition.MAJESTIC;
import static forestry.apiculture.genetics.BeeDefinition.MARSHY;
import static forestry.apiculture.genetics.BeeDefinition.MEADOWS;
import static forestry.apiculture.genetics.BeeDefinition.MIRY;
import static forestry.apiculture.genetics.BeeDefinition.MODEST;
import static forestry.apiculture.genetics.BeeDefinition.MONASTIC;
import static forestry.apiculture.genetics.BeeDefinition.NOBLE;
import static forestry.apiculture.genetics.BeeDefinition.RURAL;
import static forestry.apiculture.genetics.BeeDefinition.SECLUDED;
import static forestry.apiculture.genetics.BeeDefinition.SINISTER;
import static forestry.apiculture.genetics.BeeDefinition.SPECTRAL;
import static forestry.apiculture.genetics.BeeDefinition.TROPICAL;
import static forestry.apiculture.genetics.BeeDefinition.UNWEARY;
import static forestry.apiculture.genetics.BeeDefinition.VALIANT;
import static forestry.apiculture.genetics.BeeDefinition.WINTRY;

public class ExtraBeeMutation {

	public static void doInit() {
		registerMutation(MEADOWS, FRUGAL, ExtraBeesSpecies.ARID, 10);
		registerMutation(FOREST, FRUGAL, ExtraBeesSpecies.ARID, 10);
		registerMutation(ExtraBeesSpecies.ARID, COMMON, ExtraBeesSpecies.BARREN, 8);
		registerMutation(ExtraBeesSpecies.ARID, ExtraBeesSpecies.BARREN, ExtraBeesSpecies.DESOLATE, 8);
		registerMutation(ExtraBeesSpecies.BARREN, FOREST, ExtraBeesSpecies.GNAWING, 15);
		registerMutation(ExtraBeesSpecies.DESOLATE, MEADOWS, ExtraBeesSpecies.ROTTEN, 15);
		registerMutation(ExtraBeesSpecies.DESOLATE, FOREST, ExtraBeesSpecies.BONE, 15);
		registerMutation(ExtraBeesSpecies.DESOLATE, MODEST, ExtraBeesSpecies.CREEPER, 15);
		registerMutation(ExtraBeesSpecies.BARREN, MARSHY, ExtraBeesSpecies.DECOMPOSING, 15);
		registerMutation(ExtraBeesSpecies.ROCK, DILIGENT, ExtraBeesSpecies.STONE, 12);
		registerMutation(ExtraBeesSpecies.STONE, UNWEARY, ExtraBeesSpecies.GRANITE, 10);
		registerMutation(ExtraBeesSpecies.GRANITE, INDUSTRIOUS, ExtraBeesSpecies.MINERAL, 6);
		registerMutation(ExtraBeesSpecies.MINERAL, MEADOWS, ExtraBeesSpecies.IRON, 5);
		registerMutation(ExtraBeesSpecies.MINERAL, FOREST, ExtraBeesSpecies.IRON, 5);
		registerMutation(ExtraBeesSpecies.MINERAL, WINTRY, ExtraBeesSpecies.COPPER, 5);
		registerMutation(ExtraBeesSpecies.MINERAL, MODEST, ExtraBeesSpecies.COPPER, 5);
		registerMutation(ExtraBeesSpecies.MINERAL, MARSHY, ExtraBeesSpecies.TIN, 5);
		registerMutation(ExtraBeesSpecies.MINERAL, TROPICAL, ExtraBeesSpecies.TIN, 5);
		registerMutation(ExtraBeesSpecies.MINERAL, MEADOWS, ExtraBeesSpecies.LEAD, 5);
		registerMutation(ExtraBeesSpecies.MINERAL, MODEST, ExtraBeesSpecies.LEAD, 5);
		registerMutation(ExtraBeesSpecies.MINERAL, WINTRY, ExtraBeesSpecies.ZINC, 5);
		registerMutation(ExtraBeesSpecies.MINERAL, TROPICAL, ExtraBeesSpecies.ZINC, 5);
		registerMutation(ExtraBeesSpecies.MINERAL, FOREST, ExtraBeesSpecies.NICKEL, 5);
		registerMutation(ExtraBeesSpecies.MINERAL, MARSHY, ExtraBeesSpecies.NICKEL, 5);
		registerMutation(ExtraBeesSpecies.MINERAL, CULTIVATED, ExtraBeesSpecies.TITANIUM, 3);
		registerMutation(ExtraBeesSpecies.MINERAL, COMMON, ExtraBeesSpecies.TUNGSTATE, 3);
		registerMutation(ExtraBeesSpecies.ZINC, MAJESTIC, ExtraBeesSpecies.SILVER, 2);
		registerMutation(ExtraBeesSpecies.TIN, MAJESTIC, ExtraBeesSpecies.SILVER, 2);
		registerMutation(ExtraBeesSpecies.LEAD, MAJESTIC, ExtraBeesSpecies.SILVER, 2);
		registerMutation(ExtraBeesSpecies.TITANIUM, MAJESTIC, ExtraBeesSpecies.SILVER, 3);
		registerMutation(ExtraBeesSpecies.IRON, MAJESTIC, ExtraBeesSpecies.GOLD, 2);
		registerMutation(ExtraBeesSpecies.COPPER, MAJESTIC, ExtraBeesSpecies.GOLD, 2);
		registerMutation(ExtraBeesSpecies.NICKEL, MAJESTIC, ExtraBeesSpecies.GOLD, 2);
		registerMutation(ExtraBeesSpecies.TUNGSTATE, MAJESTIC, ExtraBeesSpecies.GOLD, 3);
		registerMutation(ExtraBeesSpecies.GOLD, ExtraBeesSpecies.SILVER, ExtraBeesSpecies.PLATINUM, 2);
		registerMutation(ExtraBeesSpecies.MINERAL, IMPERIAL, ExtraBeesSpecies.LAPIS, 5);
		registerMutation(ExtraBeesSpecies.LAPIS, FOREST, ExtraBeesSpecies.EMERALD, 5);
		registerMutation(ExtraBeesSpecies.LAPIS, MODEST, ExtraBeesSpecies.RUBY, 5);
		registerMutation(ExtraBeesSpecies.LAPIS, ExtraBeesSpecies.WATER, ExtraBeesSpecies.SAPPHIRE, 5);
		registerMutation(ExtraBeesSpecies.LAPIS, CULTIVATED, ExtraBeesSpecies.DIAMOND, 5);
		registerMutation(ExtraBeesSpecies.PREHISTORIC, ExtraBeesSpecies.MINERAL, ExtraBeesSpecies.UNSTABLE, 5);
		registerMutation(ExtraBeesSpecies.UNSTABLE, ExtraBeesSpecies.IRON, ExtraBeesSpecies.NUCLEAR, 5);
		registerMutation(ExtraBeesSpecies.UNSTABLE, ExtraBeesSpecies.COPPER, ExtraBeesSpecies.NUCLEAR, 5);
		registerMutation(ExtraBeesSpecies.UNSTABLE, ExtraBeesSpecies.TIN, ExtraBeesSpecies.NUCLEAR, 5);
		registerMutation(ExtraBeesSpecies.UNSTABLE, ExtraBeesSpecies.ZINC, ExtraBeesSpecies.NUCLEAR, 5);
		registerMutation(ExtraBeesSpecies.UNSTABLE, ExtraBeesSpecies.NICKEL, ExtraBeesSpecies.NUCLEAR, 5);
		registerMutation(ExtraBeesSpecies.UNSTABLE, ExtraBeesSpecies.LEAD, ExtraBeesSpecies.NUCLEAR, 5);
		registerMutation(ExtraBeesSpecies.NUCLEAR, ExtraBeesSpecies.GOLD, ExtraBeesSpecies.RADIOACTIVE, 5);
		registerMutation(ExtraBeesSpecies.NUCLEAR, ExtraBeesSpecies.SILVER, ExtraBeesSpecies.RADIOACTIVE, 5);
		registerMutation(ExtraBeesSpecies.NUCLEAR, FRUGAL, ExtraBeesSpecies.YELLORIUM, 5);
		registerMutation(ExtraBeesSpecies.NUCLEAR, ExtraBeesSpecies.YELLORIUM, ExtraBeesSpecies.CYANITE, 5);
		registerMutation(ExtraBeesSpecies.YELLORIUM, ExtraBeesSpecies.CYANITE, ExtraBeesSpecies.BLUTONIUM, 5);
		registerMutation(NOBLE, DILIGENT, ExtraBeesSpecies.ANCIENT, 10);
		registerMutation(ExtraBeesSpecies.ANCIENT, SECLUDED, ExtraBeesSpecies.PRIMEVAL, 8);
		registerMutation(ExtraBeesSpecies.PRIMEVAL, ExtraBeesSpecies.ANCIENT, ExtraBeesSpecies.PREHISTORIC, 8);
		registerMutation(ExtraBeesSpecies.PREHISTORIC, IMPERIAL, ExtraBeesSpecies.RELIC, 8);
		registerMutation(ExtraBeesSpecies.PRIMEVAL, ExtraBeesSpecies.GROWING, ExtraBeesSpecies.COAL, 8);
		registerMutation(ExtraBeesSpecies.PRIMEVAL, RURAL, ExtraBeesSpecies.COAL, 8);
		registerMutation(ExtraBeesSpecies.PRIMEVAL, MIRY, ExtraBeesSpecies.RESIN, 8);
		registerMutation(ExtraBeesSpecies.PRIMEVAL, ExtraBeesSpecies.OCEAN, ExtraBeesSpecies.OIL, 8);
		registerMutation(ExtraBeesSpecies.PRIMEVAL, FRUGAL, ExtraBeesSpecies.OIL, 8);
		registerMutation(ExtraBeesSpecies.OIL, INDUSTRIOUS, ExtraBeesSpecies.DISTILLED, 8);
		registerMutation(ExtraBeesSpecies.DISTILLED, ExtraBeesSpecies.OIL, ExtraBeesSpecies.FUEL, 8);
		registerMutation(ExtraBeesSpecies.DISTILLED, ExtraBeesSpecies.COAL, ExtraBeesSpecies.CREOSOTE, 8);
		registerMutation(ExtraBeesSpecies.DISTILLED, ExtraBeesSpecies.RESIN, ExtraBeesSpecies.LATEX, 8);
		registerMutation(ExtraBeesSpecies.WATER, DILIGENT, ExtraBeesSpecies.RIVER, 10).restrictBiomeType(BiomeDictionary.Type.RIVER);
		registerMutation(ExtraBeesSpecies.WATER, DILIGENT, ExtraBeesSpecies.OCEAN, 10).restrictBiomeType(BiomeDictionary.Type.OCEAN);
		registerMutation(ExtraBeesSpecies.BLACK, ExtraBeesSpecies.OCEAN, ExtraBeesSpecies.INK, 8);
		registerMutation(FOREST, DILIGENT, ExtraBeesSpecies.GROWING, 10);
		registerMutation(ExtraBeesSpecies.GROWING, UNWEARY, ExtraBeesSpecies.THRIVING, 10);
		registerMutation(ExtraBeesSpecies.THRIVING, INDUSTRIOUS, ExtraBeesSpecies.BLOOMING, 8);
		registerMutation(VALIANT, DILIGENT, ExtraBeesSpecies.SWEET, 15);
		registerMutation(ExtraBeesSpecies.SWEET, RURAL, ExtraBeesSpecies.SUGAR, 15);
		registerMutation(ExtraBeesSpecies.SWEET, ExtraBeesSpecies.GROWING, ExtraBeesSpecies.RIPENING, 5);
		registerMutation(ExtraBeesSpecies.SWEET, ExtraBeesSpecies.THRIVING, ExtraBeesSpecies.FRUIT, 5);
		registerMutation(FARMERLY, MEADOWS, ExtraBeesSpecies.ALCOHOL, 10);
		registerMutation(FARMERLY, MEADOWS, ExtraBeesSpecies.FARM, 10);
		registerMutation(ExtraBeesSpecies.WATER, FARMERLY, ExtraBeesSpecies.MILK, 10);
		registerMutation(FARMERLY, TROPICAL, ExtraBeesSpecies.COFFEE, 10);
		registerMutation(ExtraBeesSpecies.WATER, MIRY, ExtraBeesSpecies.SWAMP, 10);
		registerMutation(ExtraBeesSpecies.SWAMP, BOGGY, ExtraBeesSpecies.BOGGY, 8);
		registerMutation(ExtraBeesSpecies.BOGGY, ExtraBeesSpecies.SWAMP, ExtraBeesSpecies.FUNGAL, 8);
		registerMutation(BOGGY, MIRY, ExtraBeesSpecies.FUNGAL, 8);
		registerMutation(ExtraBeesSpecies.WATER, FOREST, COMMON, 15);
		registerMutation(ExtraBeesSpecies.WATER, MEADOWS, COMMON, 15);
		registerMutation(ExtraBeesSpecies.WATER, MODEST, COMMON, 15);
		registerMutation(ExtraBeesSpecies.WATER, TROPICAL, COMMON, 15);
		registerMutation(ExtraBeesSpecies.WATER, MARSHY, COMMON, 15);
		registerMutation(ExtraBeesSpecies.WATER, WINTRY, COMMON, 15);
		registerMutation(ExtraBeesSpecies.WATER, ExtraBeesSpecies.ROCK, COMMON.getTemplate(), 15);
		registerMutation(ExtraBeesSpecies.WATER, ExtraBeesSpecies.BASALT, COMMON.getTemplate(), 15);
		registerMutation(ExtraBeesSpecies.ROCK, FOREST, COMMON, 15);
		registerMutation(ExtraBeesSpecies.ROCK, MEADOWS, COMMON, 15);
		registerMutation(ExtraBeesSpecies.ROCK, MODEST, COMMON, 15);
		registerMutation(ExtraBeesSpecies.ROCK, TROPICAL, COMMON, 15);
		registerMutation(ExtraBeesSpecies.ROCK, MARSHY, COMMON, 15);
		registerMutation(ExtraBeesSpecies.ROCK, WINTRY, COMMON, 15);
		registerMutation(ExtraBeesSpecies.ROCK, ExtraBeesSpecies.BASALT, COMMON.getTemplate(), 15);
		registerMutation(ExtraBeesSpecies.BASALT, FOREST, COMMON, 15);
		registerMutation(ExtraBeesSpecies.BASALT, MEADOWS, COMMON, 15);
		registerMutation(ExtraBeesSpecies.BASALT, MODEST, COMMON, 15);
		registerMutation(ExtraBeesSpecies.BASALT, TROPICAL, COMMON, 15);
		registerMutation(ExtraBeesSpecies.BASALT, MARSHY, COMMON, 15);
		registerMutation(ExtraBeesSpecies.BASALT, WINTRY, COMMON, 15);
		registerMutation(ExtraBeesSpecies.WATER, COMMON, CULTIVATED, 12);
		registerMutation(ExtraBeesSpecies.ROCK, COMMON, CULTIVATED, 12);
		registerMutation(ExtraBeesSpecies.BASALT, COMMON, CULTIVATED, 12);
		registerMutation(ExtraBeesSpecies.BASALT, FIENDISH, ExtraBeesSpecies.TEMPERED, 30).restrictBiomeType(BiomeDictionary.Type.NETHER);
		registerMutation(ExtraBeesSpecies.TEMPERED, DEMONIC, ExtraBeesSpecies.VOLCANIC, 20).restrictBiomeType(BiomeDictionary.Type.NETHER);
		registerMutation(ExtraBeesSpecies.BASALT, CULTIVATED, SINISTER, 60).restrictBiomeType(BiomeDictionary.Type.NETHER);
		registerMutation(ExtraBeesSpecies.BASALT, SINISTER, FIENDISH, 40).restrictBiomeType(BiomeDictionary.Type.NETHER);
		registerMutation(SINISTER, TROPICAL, ExtraBeesSpecies.MALICIOUS, 10);
		registerMutation(ExtraBeesSpecies.MALICIOUS, TROPICAL, ExtraBeesSpecies.INFECTIOUS, 8);
		registerMutation(ExtraBeesSpecies.MALICIOUS, ExtraBeesSpecies.INFECTIOUS, ExtraBeesSpecies.VIRULENT, 8);
		registerMutation(ExtraBeesSpecies.WATER, EXOTIC, ExtraBeesSpecies.VISCOUS, 10);
		registerMutation(ExtraBeesSpecies.VISCOUS, EXOTIC, ExtraBeesSpecies.GLUTINOUS, 8);
		registerMutation(ExtraBeesSpecies.VISCOUS, ExtraBeesSpecies.GLUTINOUS, ExtraBeesSpecies.STICKY, 8);
		registerMutation(ExtraBeesSpecies.MALICIOUS, ExtraBeesSpecies.VISCOUS, ExtraBeesSpecies.CORROSIVE, 10);
		registerMutation(ExtraBeesSpecies.CORROSIVE, FIENDISH, ExtraBeesSpecies.CAUSTIC, 8);
		registerMutation(ExtraBeesSpecies.CORROSIVE, ExtraBeesSpecies.CAUSTIC, ExtraBeesSpecies.ACIDIC, 4);
		registerMutation(CULTIVATED, VALIANT, ExtraBeesSpecies.EXCITED, 10);
		registerMutation(ExtraBeesSpecies.EXCITED, DILIGENT, ExtraBeesSpecies.ENERGETIC, 8);
		registerMutation(ExtraBeesSpecies.EXCITED, ExtraBeesSpecies.ENERGETIC, ExtraBeesSpecies.ECSTATIC, 8);
		registerMutation(WINTRY, DILIGENT, ExtraBeesSpecies.ARTIC, 10);
		registerMutation(ExtraBeesSpecies.OCEAN, ExtraBeesSpecies.ARTIC, ExtraBeesSpecies.FREEZING, 10);
		registerMutation(ExtraBeesSpecies.ROCK, SINISTER, ExtraBeesSpecies.SHADOW, 10);
		registerMutation(ExtraBeesSpecies.SHADOW, ExtraBeesSpecies.ROCK, ExtraBeesSpecies.DARKENED, 8);
		registerMutation(ExtraBeesSpecies.SHADOW, ExtraBeesSpecies.DARKENED, ExtraBeesSpecies.ABYSS, 8);
		registerMutation(FOREST, VALIANT, ExtraBeesSpecies.RED, 5);
		registerMutation(MEADOWS, VALIANT, ExtraBeesSpecies.YELLOW, 5);
		registerMutation(ExtraBeesSpecies.WATER, VALIANT, ExtraBeesSpecies.BLUE, 5);
		registerMutation(TROPICAL, VALIANT, ExtraBeesSpecies.GREEN, 5);
		registerMutation(ExtraBeesSpecies.ROCK, VALIANT, ExtraBeesSpecies.BLACK, 5);
		registerMutation(WINTRY, VALIANT, ExtraBeesSpecies.WHITE, 5);
		registerMutation(MARSHY, VALIANT, ExtraBeesSpecies.BROWN, 5);
		registerMutation(ExtraBeesSpecies.RED, ExtraBeesSpecies.YELLOW, ExtraBeesSpecies.ORANGE, 5);
		registerMutation(ExtraBeesSpecies.GREEN, ExtraBeesSpecies.BLUE, ExtraBeesSpecies.CYAN, 5);
		registerMutation(ExtraBeesSpecies.RED, ExtraBeesSpecies.BLUE, ExtraBeesSpecies.PURPLE, 5);
		registerMutation(ExtraBeesSpecies.BLACK, ExtraBeesSpecies.WHITE, ExtraBeesSpecies.GRAY, 5);
		registerMutation(ExtraBeesSpecies.BLUE, ExtraBeesSpecies.WHITE, ExtraBeesSpecies.LIGHTBLUE, 5);
		registerMutation(ExtraBeesSpecies.RED, ExtraBeesSpecies.WHITE, ExtraBeesSpecies.PINK, 5);
		registerMutation(ExtraBeesSpecies.GREEN, ExtraBeesSpecies.WHITE, ExtraBeesSpecies.LIMEGREEN, 5);
		registerMutation(ExtraBeesSpecies.PURPLE, ExtraBeesSpecies.PINK, ExtraBeesSpecies.MAGENTA, 5);
		registerMutation(ExtraBeesSpecies.GRAY, ExtraBeesSpecies.WHITE, ExtraBeesSpecies.LIGHTGRAY, 5);
		registerMutation(ExtraBeesSpecies.TEMPERED, ExtraBeesSpecies.EXCITED, ExtraBeesSpecies.GLOWSTONE, 5);
		registerMutation(ExtraBeesSpecies.DESOLATE, AUSTERE, ExtraBeesSpecies.HAZARDOUS, 5);
		registerMutation(ExtraBeesSpecies.RELIC, ENDED, ExtraBeesSpecies.JADED, 2).addMutationCondition(new ConditionPerson("jadedcat"));
		registerMutation(ExtraBeesSpecies.EXCITED, AUSTERE, ExtraBeesSpecies.CELEBRATORY, 5);
		registerMutation(SECLUDED, ENDED, ExtraBeesSpecies.UNUSUAL, 5);
		registerMutation(ExtraBeesSpecies.UNUSUAL, HERMITIC, ExtraBeesSpecies.SPATIAL, 5);
		registerMutation(ExtraBeesSpecies.SPATIAL, SPECTRAL, ExtraBeesSpecies.QUANTUM, 5);
		registerMutation(NOBLE, MONASTIC, ExtraBeesSpecies.MYSTICAL, 5);
	}

	public static IBeeMutationBuilder registerMutation(BeeDefinition allele0, BeeDefinition allele1, ExtraBeesSpecies mutation, int chance) {
		return registerMutation(allele0.getGenome().getPrimary(), allele1.getGenome().getPrimary(), mutation, chance);
	}

	public static IBeeMutationBuilder registerMutation(IAlleleBeeSpecies allele0, BeeDefinition allele1, BeeDefinition mutation, int chance) {
		return registerMutation(allele0, allele1.getGenome().getPrimary(), mutation.getTemplate(), chance);
	}

	public static IBeeMutationBuilder registerMutation(IAlleleBeeSpecies allele0, BeeDefinition allele1, ExtraBeesSpecies mutation, int chance) {
		return registerMutation(allele0, allele1.getGenome().getPrimary(), mutation, chance);
	}

	public static IBeeMutationBuilder registerMutation(IAlleleBeeSpecies allele0, IAlleleBeeSpecies allele1, ExtraBeesSpecies mutation, int chance) {
		return registerMutation(allele0, allele1, mutation.getTemplate(), chance);
	}

	public static IBeeMutationBuilder registerMutation(IAlleleBeeSpecies allele0, IAlleleBeeSpecies allele1, IAllele[] template, int chance) {
		Preconditions.checkNotNull(allele0);
		Preconditions.checkNotNull(allele1);
		Preconditions.checkNotNull(template);
		return BeeManager.beeMutationFactory.createMutation(allele0, allele1, template, chance);
	}

	static class ConditionPerson implements IMutationCondition {

		String name;

		public ConditionPerson(final String name) {
			this.name = name;
		}

		@Override
		public String getDescription() {
			return "Can only be bred by " + this.name;
		}

		@Override
		public float getChance(World world, BlockPos pos, IAllele allele0, IAllele allele1, IGenome genome0, IGenome genome1, IClimateProvider climate) {
			TileEntity tileEnity = world.getTileEntity(pos);
			if (tileEnity instanceof IBeeHousing) {
				IBeeHousing housing = (IBeeHousing) tileEnity;
				if (housing.getOwner() != null && housing.getOwner().getName() != null && housing.getOwner().getName().equals(this.name)) {
					return 1;
				}
			}
			return 0;
		}
	}
}
