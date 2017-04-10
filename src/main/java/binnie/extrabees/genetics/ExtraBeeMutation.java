package binnie.extrabees.genetics;

import binnie.core.genetics.ForestryAllele;
import com.google.common.base.Preconditions;
import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeMutationBuilder;
import forestry.api.climate.IClimateProvider;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IGenome;
import forestry.api.genetics.IMutationCondition;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;

public class ExtraBeeMutation {
	public static void doInit() {
		registerMutation(ForestryAllele.BeeSpecies.Meadows.getAllele(), ForestryAllele.BeeSpecies.Frugal.getAllele(), ExtraBeesSpecies.ARID, 10);
		registerMutation(ForestryAllele.BeeSpecies.Forest.getAllele(), ForestryAllele.BeeSpecies.Frugal.getAllele(), ExtraBeesSpecies.ARID, 10);
		registerMutation(ExtraBeesSpecies.ARID, ForestryAllele.BeeSpecies.Common.getAllele(), ExtraBeesSpecies.BARREN, 8);
		registerMutation(ExtraBeesSpecies.ARID, ExtraBeesSpecies.BARREN, ExtraBeesSpecies.DESOLATE, 8);
		registerMutation(ExtraBeesSpecies.BARREN, ForestryAllele.BeeSpecies.Forest.getAllele(), ExtraBeesSpecies.GNAWING, 15);
		registerMutation(ExtraBeesSpecies.DESOLATE, ForestryAllele.BeeSpecies.Meadows.getAllele(), ExtraBeesSpecies.ROTTEN, 15);
		registerMutation(ExtraBeesSpecies.DESOLATE, ForestryAllele.BeeSpecies.Forest.getAllele(), ExtraBeesSpecies.BONE, 15);
		registerMutation(ExtraBeesSpecies.DESOLATE, ForestryAllele.BeeSpecies.Modest.getAllele(), ExtraBeesSpecies.CREEPER, 15);
		registerMutation(ExtraBeesSpecies.BARREN, ForestryAllele.BeeSpecies.Marshy.getAllele(), ExtraBeesSpecies.DECOMPOSING, 15);
		registerMutation(ExtraBeesSpecies.ROCK, ForestryAllele.BeeSpecies.Diligent.getAllele(), ExtraBeesSpecies.STONE, 12);
		registerMutation(ExtraBeesSpecies.STONE, ForestryAllele.BeeSpecies.Unweary.getAllele(), ExtraBeesSpecies.GRANITE, 10);
		registerMutation(ExtraBeesSpecies.GRANITE, ForestryAllele.BeeSpecies.Industrious.getAllele(), ExtraBeesSpecies.MINERAL, 6);
		registerMutation(ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Meadows.getAllele(), ExtraBeesSpecies.IRON, 5);
		registerMutation(ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Forest.getAllele(), ExtraBeesSpecies.IRON, 5);
		registerMutation(ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Wintry.getAllele(), ExtraBeesSpecies.COPPER, 5);
		registerMutation(ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Modest.getAllele(), ExtraBeesSpecies.COPPER, 5);
		registerMutation(ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Marshy.getAllele(), ExtraBeesSpecies.TIN, 5);
		registerMutation(ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Tropical.getAllele(), ExtraBeesSpecies.TIN, 5);
		registerMutation(ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Meadows.getAllele(), ExtraBeesSpecies.LEAD, 5);
		registerMutation(ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Modest.getAllele(), ExtraBeesSpecies.LEAD, 5);
		registerMutation(ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Wintry.getAllele(), ExtraBeesSpecies.ZINC, 5);
		registerMutation(ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Tropical.getAllele(), ExtraBeesSpecies.ZINC, 5);
		registerMutation(ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Forest.getAllele(), ExtraBeesSpecies.NICKEL, 5);
		registerMutation(ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Marshy.getAllele(), ExtraBeesSpecies.NICKEL, 5);
		registerMutation(ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Cultivated.getAllele(), ExtraBeesSpecies.TITANIUM, 3);
		registerMutation(ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Common.getAllele(), ExtraBeesSpecies.TUNGSTATE, 3);
		registerMutation(ExtraBeesSpecies.ZINC, ForestryAllele.BeeSpecies.Majestic.getAllele(), ExtraBeesSpecies.SILVER, 2);
		registerMutation(ExtraBeesSpecies.TIN, ForestryAllele.BeeSpecies.Majestic.getAllele(), ExtraBeesSpecies.SILVER, 2);
		registerMutation(ExtraBeesSpecies.LEAD, ForestryAllele.BeeSpecies.Majestic.getAllele(), ExtraBeesSpecies.SILVER, 2);
		registerMutation(ExtraBeesSpecies.TITANIUM, ForestryAllele.BeeSpecies.Majestic.getAllele(), ExtraBeesSpecies.SILVER, 3);
		registerMutation(ExtraBeesSpecies.IRON, ForestryAllele.BeeSpecies.Majestic.getAllele(), ExtraBeesSpecies.GOLD, 2);
		registerMutation(ExtraBeesSpecies.COPPER, ForestryAllele.BeeSpecies.Majestic.getAllele(), ExtraBeesSpecies.GOLD, 2);
		registerMutation(ExtraBeesSpecies.NICKEL, ForestryAllele.BeeSpecies.Majestic.getAllele(), ExtraBeesSpecies.GOLD, 2);
		registerMutation(ExtraBeesSpecies.TUNGSTATE, ForestryAllele.BeeSpecies.Majestic.getAllele(), ExtraBeesSpecies.GOLD, 3);
		registerMutation(ExtraBeesSpecies.GOLD, ExtraBeesSpecies.SILVER, ExtraBeesSpecies.PLATINUM, 2);
		registerMutation(ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Imperial.getAllele(), ExtraBeesSpecies.LAPIS, 5);
		registerMutation(ExtraBeesSpecies.LAPIS, ForestryAllele.BeeSpecies.Forest.getAllele(), ExtraBeesSpecies.EMERALD, 5);
		registerMutation(ExtraBeesSpecies.LAPIS, ForestryAllele.BeeSpecies.Modest.getAllele(), ExtraBeesSpecies.RUBY, 5);
		registerMutation(ExtraBeesSpecies.LAPIS, ExtraBeesSpecies.WATER, ExtraBeesSpecies.SAPPHIRE, 5);
		registerMutation(ExtraBeesSpecies.LAPIS, ForestryAllele.BeeSpecies.Cultivated.getAllele(), ExtraBeesSpecies.DIAMOND, 5);
		registerMutation(ExtraBeesSpecies.PREHISTORIC, ExtraBeesSpecies.MINERAL, ExtraBeesSpecies.UNSTABLE, 5);
		registerMutation(ExtraBeesSpecies.UNSTABLE, ExtraBeesSpecies.IRON, ExtraBeesSpecies.NUCLEAR, 5);
		registerMutation(ExtraBeesSpecies.UNSTABLE, ExtraBeesSpecies.COPPER, ExtraBeesSpecies.NUCLEAR, 5);
		registerMutation(ExtraBeesSpecies.UNSTABLE, ExtraBeesSpecies.TIN, ExtraBeesSpecies.NUCLEAR, 5);
		registerMutation(ExtraBeesSpecies.UNSTABLE, ExtraBeesSpecies.ZINC, ExtraBeesSpecies.NUCLEAR, 5);
		registerMutation(ExtraBeesSpecies.UNSTABLE, ExtraBeesSpecies.NICKEL, ExtraBeesSpecies.NUCLEAR, 5);
		registerMutation(ExtraBeesSpecies.UNSTABLE, ExtraBeesSpecies.LEAD, ExtraBeesSpecies.NUCLEAR, 5);
		registerMutation(ExtraBeesSpecies.NUCLEAR, ExtraBeesSpecies.GOLD, ExtraBeesSpecies.RADIOACTIVE, 5);
		registerMutation(ExtraBeesSpecies.NUCLEAR, ExtraBeesSpecies.SILVER, ExtraBeesSpecies.RADIOACTIVE, 5);
		registerMutation(ExtraBeesSpecies.NUCLEAR, ForestryAllele.BeeSpecies.Frugal.getAllele(), ExtraBeesSpecies.YELLORIUM, 5);
		registerMutation(ExtraBeesSpecies.NUCLEAR, ExtraBeesSpecies.YELLORIUM, ExtraBeesSpecies.CYANITE, 5);
		registerMutation(ExtraBeesSpecies.YELLORIUM, ExtraBeesSpecies.CYANITE, ExtraBeesSpecies.BLUTONIUM, 5);
		registerMutation(ForestryAllele.BeeSpecies.Noble.getAllele(), ForestryAllele.BeeSpecies.Diligent.getAllele(), ExtraBeesSpecies.ANCIENT, 10);
		registerMutation(ExtraBeesSpecies.ANCIENT, ForestryAllele.BeeSpecies.Secluded.getAllele(), ExtraBeesSpecies.PRIMEVAL, 8);
		registerMutation(ExtraBeesSpecies.PRIMEVAL, ExtraBeesSpecies.ANCIENT, ExtraBeesSpecies.PREHISTORIC, 8);
		registerMutation(ExtraBeesSpecies.PREHISTORIC, ForestryAllele.BeeSpecies.Imperial.getAllele(), ExtraBeesSpecies.RELIC, 8);
		registerMutation(ExtraBeesSpecies.PRIMEVAL, ExtraBeesSpecies.GROWING, ExtraBeesSpecies.COAL, 8);
		registerMutation(ExtraBeesSpecies.PRIMEVAL, ForestryAllele.BeeSpecies.Rural.getAllele(), ExtraBeesSpecies.COAL, 8);
		registerMutation(ExtraBeesSpecies.PRIMEVAL, ForestryAllele.BeeSpecies.Miry.getAllele(), ExtraBeesSpecies.RESIN, 8);
		registerMutation(ExtraBeesSpecies.PRIMEVAL, ExtraBeesSpecies.OCEAN, ExtraBeesSpecies.OIL, 8);
		registerMutation(ExtraBeesSpecies.PRIMEVAL, ForestryAllele.BeeSpecies.Frugal.getAllele(), ExtraBeesSpecies.OIL, 8);
		registerMutation(ExtraBeesSpecies.OIL, ForestryAllele.BeeSpecies.Industrious.getAllele(), ExtraBeesSpecies.DISTILLED, 8);
		registerMutation(ExtraBeesSpecies.DISTILLED, ExtraBeesSpecies.OIL, ExtraBeesSpecies.FUEL, 8);
		registerMutation(ExtraBeesSpecies.DISTILLED, ExtraBeesSpecies.COAL, ExtraBeesSpecies.CREOSOTE, 8);
		registerMutation(ExtraBeesSpecies.DISTILLED, ExtraBeesSpecies.RESIN, ExtraBeesSpecies.LATEX, 8);
		registerMutation(ExtraBeesSpecies.WATER, ForestryAllele.BeeSpecies.Diligent.getAllele(), ExtraBeesSpecies.RIVER, 10).restrictBiomeType(BiomeDictionary.Type.RIVER);
		registerMutation(ExtraBeesSpecies.WATER, ForestryAllele.BeeSpecies.Diligent.getAllele(), ExtraBeesSpecies.OCEAN, 10).restrictBiomeType(BiomeDictionary.Type.OCEAN);
		registerMutation(ExtraBeesSpecies.BLACK, ExtraBeesSpecies.OCEAN, ExtraBeesSpecies.INK, 8);
		registerMutation(ForestryAllele.BeeSpecies.Forest.getAllele(), ForestryAllele.BeeSpecies.Diligent.getAllele(), ExtraBeesSpecies.GROWING, 10);
		registerMutation(ExtraBeesSpecies.GROWING, ForestryAllele.BeeSpecies.Unweary.getAllele(), ExtraBeesSpecies.THRIVING, 10);
		registerMutation(ExtraBeesSpecies.THRIVING, ForestryAllele.BeeSpecies.Industrious.getAllele(), ExtraBeesSpecies.BLOOMING, 8);
		registerMutation(ForestryAllele.BeeSpecies.Valiant.getAllele(), ForestryAllele.BeeSpecies.Diligent.getAllele(), ExtraBeesSpecies.SWEET, 15);
		registerMutation(ExtraBeesSpecies.SWEET, ForestryAllele.BeeSpecies.Rural.getAllele(), ExtraBeesSpecies.SUGAR, 15);
		registerMutation(ExtraBeesSpecies.SWEET, ExtraBeesSpecies.GROWING, ExtraBeesSpecies.RIPENING, 5);
		registerMutation(ExtraBeesSpecies.SWEET, ExtraBeesSpecies.THRIVING, ExtraBeesSpecies.FRUIT, 5);
		registerMutation(ForestryAllele.BeeSpecies.Farmerly.getAllele(), ForestryAllele.BeeSpecies.Meadows.getAllele(), ExtraBeesSpecies.ALCOHOL, 10);
		registerMutation(ForestryAllele.BeeSpecies.Farmerly.getAllele(), ForestryAllele.BeeSpecies.Meadows.getAllele(), ExtraBeesSpecies.FARM, 10);
		registerMutation(ForestryAllele.BeeSpecies.Farmerly.getAllele(), ExtraBeesSpecies.WATER, ExtraBeesSpecies.MILK, 10);
		registerMutation(ForestryAllele.BeeSpecies.Farmerly.getAllele(), ForestryAllele.BeeSpecies.Tropical.getAllele(), ExtraBeesSpecies.COFFEE, 10);
		registerMutation(ExtraBeesSpecies.WATER, ForestryAllele.BeeSpecies.Miry.getAllele(), ExtraBeesSpecies.SWAMP, 10);
		registerMutation(ExtraBeesSpecies.SWAMP, ForestryAllele.BeeSpecies.Boggy.getAllele(), ExtraBeesSpecies.BOGGY, 8);
		registerMutation(ExtraBeesSpecies.BOGGY, ExtraBeesSpecies.SWAMP, ExtraBeesSpecies.FUNGAL, 8);
		registerMutation(ForestryAllele.BeeSpecies.Boggy.getAllele(), ForestryAllele.BeeSpecies.Miry.getAllele(), ExtraBeesSpecies.FUNGAL, 8);
		registerMutation(ExtraBeesSpecies.WATER, ForestryAllele.BeeSpecies.Forest.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
		registerMutation(ExtraBeesSpecies.WATER, ForestryAllele.BeeSpecies.Meadows.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
		registerMutation(ExtraBeesSpecies.WATER, ForestryAllele.BeeSpecies.Modest.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
		registerMutation(ExtraBeesSpecies.WATER, ForestryAllele.BeeSpecies.Tropical.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
		registerMutation(ExtraBeesSpecies.WATER, ForestryAllele.BeeSpecies.Marshy.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
		registerMutation(ExtraBeesSpecies.WATER, ForestryAllele.BeeSpecies.Wintry.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
		registerMutation(ExtraBeesSpecies.WATER, ExtraBeesSpecies.ROCK, ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
		registerMutation(ExtraBeesSpecies.WATER, ExtraBeesSpecies.BASALT, ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
		registerMutation(ExtraBeesSpecies.ROCK, ForestryAllele.BeeSpecies.Forest.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
		registerMutation(ExtraBeesSpecies.ROCK, ForestryAllele.BeeSpecies.Meadows.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
		registerMutation(ExtraBeesSpecies.ROCK, ForestryAllele.BeeSpecies.Modest.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
		registerMutation(ExtraBeesSpecies.ROCK, ForestryAllele.BeeSpecies.Tropical.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
		registerMutation(ExtraBeesSpecies.ROCK, ForestryAllele.BeeSpecies.Marshy.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
		registerMutation(ExtraBeesSpecies.ROCK, ForestryAllele.BeeSpecies.Wintry.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
		registerMutation(ExtraBeesSpecies.ROCK, ExtraBeesSpecies.BASALT, ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
		registerMutation(ExtraBeesSpecies.BASALT, ForestryAllele.BeeSpecies.Forest.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
		registerMutation(ExtraBeesSpecies.BASALT, ForestryAllele.BeeSpecies.Meadows.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
		registerMutation(ExtraBeesSpecies.BASALT, ForestryAllele.BeeSpecies.Modest.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
		registerMutation(ExtraBeesSpecies.BASALT, ForestryAllele.BeeSpecies.Tropical.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
		registerMutation(ExtraBeesSpecies.BASALT, ForestryAllele.BeeSpecies.Marshy.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
		registerMutation(ExtraBeesSpecies.BASALT, ForestryAllele.BeeSpecies.Wintry.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
		registerMutation(ExtraBeesSpecies.WATER, ForestryAllele.BeeSpecies.Common.getAllele(), ForestryAllele.BeeSpecies.Cultivated.getTemplate(), 12);
		registerMutation(ExtraBeesSpecies.ROCK, ForestryAllele.BeeSpecies.Common.getAllele(), ForestryAllele.BeeSpecies.Cultivated.getTemplate(), 12);
		registerMutation(ExtraBeesSpecies.BASALT, ForestryAllele.BeeSpecies.Common.getAllele(), ForestryAllele.BeeSpecies.Cultivated.getTemplate(), 12);
		registerMutation(ExtraBeesSpecies.BASALT, ForestryAllele.BeeSpecies.Fiendish.getAllele(), ExtraBeesSpecies.TEMPERED, 30).restrictBiomeType(BiomeDictionary.Type.NETHER);
		registerMutation(ExtraBeesSpecies.TEMPERED, ForestryAllele.BeeSpecies.Demonic.getAllele(), ExtraBeesSpecies.VOLCANIC, 20).restrictBiomeType(BiomeDictionary.Type.NETHER);
		registerMutation(ExtraBeesSpecies.BASALT, ForestryAllele.BeeSpecies.Cultivated.getAllele(), ForestryAllele.BeeSpecies.Sinister.getTemplate(), 60).restrictBiomeType(BiomeDictionary.Type.NETHER);
		registerMutation(ExtraBeesSpecies.BASALT, ForestryAllele.BeeSpecies.Sinister.getAllele(), ForestryAllele.BeeSpecies.Fiendish.getTemplate(), 40).restrictBiomeType(BiomeDictionary.Type.NETHER);
		registerMutation(ForestryAllele.BeeSpecies.Sinister.getAllele(), ForestryAllele.BeeSpecies.Tropical.getAllele(), ExtraBeesSpecies.MALICIOUS, 10);
		registerMutation(ExtraBeesSpecies.MALICIOUS, ForestryAllele.BeeSpecies.Tropical.getAllele(), ExtraBeesSpecies.INFECTIOUS, 8);
		registerMutation(ExtraBeesSpecies.MALICIOUS, ExtraBeesSpecies.INFECTIOUS, ExtraBeesSpecies.VIRULENT, 8);
		registerMutation(ExtraBeesSpecies.WATER, ForestryAllele.BeeSpecies.Exotic.getAllele(), ExtraBeesSpecies.VISCOUS, 10);
		registerMutation(ExtraBeesSpecies.VISCOUS, ForestryAllele.BeeSpecies.Exotic.getAllele(), ExtraBeesSpecies.GLUTINOUS, 8);
		registerMutation(ExtraBeesSpecies.VISCOUS, ExtraBeesSpecies.GLUTINOUS, ExtraBeesSpecies.STICKY, 8);
		registerMutation(ExtraBeesSpecies.MALICIOUS, ExtraBeesSpecies.VISCOUS, ExtraBeesSpecies.CORROSIVE, 10);
		registerMutation(ExtraBeesSpecies.CORROSIVE, ForestryAllele.BeeSpecies.Fiendish.getAllele(), ExtraBeesSpecies.CAUSTIC, 8);
		registerMutation(ExtraBeesSpecies.CORROSIVE, ExtraBeesSpecies.CAUSTIC, ExtraBeesSpecies.ACIDIC, 4);
		registerMutation(ForestryAllele.BeeSpecies.Cultivated.getAllele(), ForestryAllele.BeeSpecies.Valiant.getAllele(), ExtraBeesSpecies.EXCITED, 10);
		registerMutation(ExtraBeesSpecies.EXCITED, ForestryAllele.BeeSpecies.Diligent.getAllele(), ExtraBeesSpecies.ENERGETIC, 8);
		registerMutation(ExtraBeesSpecies.EXCITED, ExtraBeesSpecies.ENERGETIC, ExtraBeesSpecies.ECSTATIC, 8);
		registerMutation(ForestryAllele.BeeSpecies.Wintry.getAllele(), ForestryAllele.BeeSpecies.Diligent.getAllele(), ExtraBeesSpecies.ARTIC, 10);
		registerMutation(ExtraBeesSpecies.OCEAN, ExtraBeesSpecies.ARTIC, ExtraBeesSpecies.FREEZING, 10);
		registerMutation(ExtraBeesSpecies.ROCK, ForestryAllele.BeeSpecies.Sinister.getAllele(), ExtraBeesSpecies.SHADOW, 10);
		registerMutation(ExtraBeesSpecies.SHADOW, ExtraBeesSpecies.ROCK, ExtraBeesSpecies.DARKENED, 8);
		registerMutation(ExtraBeesSpecies.SHADOW, ExtraBeesSpecies.DARKENED, ExtraBeesSpecies.ABYSS, 8);
		registerMutation(ForestryAllele.BeeSpecies.Forest.getAllele(), ForestryAllele.BeeSpecies.Valiant.getAllele(), ExtraBeesSpecies.RED, 5);
		registerMutation(ForestryAllele.BeeSpecies.Meadows.getAllele(), ForestryAllele.BeeSpecies.Valiant.getAllele(), ExtraBeesSpecies.YELLOW, 5);
		registerMutation(ExtraBeesSpecies.WATER, ForestryAllele.BeeSpecies.Valiant.getAllele(), ExtraBeesSpecies.BLUE, 5);
		registerMutation(ForestryAllele.BeeSpecies.Tropical.getAllele(), ForestryAllele.BeeSpecies.Valiant.getAllele(), ExtraBeesSpecies.GREEN, 5);
		registerMutation(ExtraBeesSpecies.ROCK, ForestryAllele.BeeSpecies.Valiant.getAllele(), ExtraBeesSpecies.BLACK, 5);
		registerMutation(ForestryAllele.BeeSpecies.Wintry.getAllele(), ForestryAllele.BeeSpecies.Valiant.getAllele(), ExtraBeesSpecies.WHITE, 5);
		registerMutation(ForestryAllele.BeeSpecies.Marshy.getAllele(), ForestryAllele.BeeSpecies.Valiant.getAllele(), ExtraBeesSpecies.BROWN, 5);
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
		registerMutation(ForestryAllele.BeeSpecies.Austere.getAllele(), ExtraBeesSpecies.DESOLATE, ExtraBeesSpecies.HAZARDOUS, 5);
		registerMutation(ForestryAllele.BeeSpecies.Ended.getAllele(), ExtraBeesSpecies.RELIC, ExtraBeesSpecies.JADED, 2).addMutationCondition(new ConditionPerson("jadedcat"));
		registerMutation(ForestryAllele.BeeSpecies.Austere.getAllele(), ExtraBeesSpecies.EXCITED, ExtraBeesSpecies.CELEBRATORY, 5);
		registerMutation(ForestryAllele.BeeSpecies.Secluded.getAllele(), ForestryAllele.BeeSpecies.Ended.getAllele(), ExtraBeesSpecies.UNUSUAL, 5);
		registerMutation(ExtraBeesSpecies.UNUSUAL, ForestryAllele.BeeSpecies.Hermitic.getAllele(), ExtraBeesSpecies.SPATIAL, 5);
		registerMutation(ExtraBeesSpecies.SPATIAL, ForestryAllele.BeeSpecies.Spectral.getAllele(), ExtraBeesSpecies.QUANTUM, 5);
		registerMutation(ForestryAllele.BeeSpecies.Noble.getAllele(), ForestryAllele.BeeSpecies.Monastic.getAllele(), ExtraBeesSpecies.MYSTICAL, 5);
	}

	public static IBeeMutationBuilder registerMutation(IAlleleBeeSpecies allele0, IAlleleBeeSpecies allele1, ExtraBeesSpecies mutation, int chance) {
		return registerMutation(allele0, allele1, mutation.getTemplate(), chance);
	}

	public static IBeeMutationBuilder registerMutation(IAlleleBeeSpecies allele0, IAlleleBeeSpecies allele1, IAllele[] template, int chance) {
		Preconditions.checkNotNull(allele0);
		Preconditions.checkNotNull(allele1);
		Preconditions.checkNotNull(template);
		return BeeManager.beeMutationFactory.createMutation(allele0, allele0, template, chance);
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
			if(tileEnity instanceof IBeeHousing){
				IBeeHousing housing = (IBeeHousing) tileEnity;
				if(housing.getOwner() != null && housing.getOwner().getName() != null && housing.getOwner().getName().equals(this.name)){
					return 1;
				}
			}
			return 0;
		}
	}
}
