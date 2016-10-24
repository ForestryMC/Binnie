package binnie.extrabees.genetics;

import binnie.Binnie;
import binnie.core.genetics.ForestryAllele;
import forestry.api.apiculture.*;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IGenome;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ExtraBeeMutation implements IBeeMutation {
    public static List<IBeeMutation> mutations = new ArrayList<>();
    MutationRequirement req;
    IAlleleBeeSpecies species0;
    IAlleleBeeSpecies species1;
    IAllele[] template;
    int chance;

    public static void doInit() {
        final IAlleleBeeSpecies[] vanilla = new IAlleleBeeSpecies[0];
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Meadows.getAllele(), ForestryAllele.BeeSpecies.Frugal.getAllele(), ExtraBeesSpecies.ARID, 10);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Forest.getAllele(), ForestryAllele.BeeSpecies.Frugal.getAllele(), ExtraBeesSpecies.ARID, 10);
        new ExtraBeeMutation(ExtraBeesSpecies.ARID, ForestryAllele.BeeSpecies.Common.getAllele(), ExtraBeesSpecies.BARREN, 8);
        new ExtraBeeMutation(ExtraBeesSpecies.ARID, ExtraBeesSpecies.BARREN, ExtraBeesSpecies.DESOLATE, 8);
        new ExtraBeeMutation(ExtraBeesSpecies.BARREN, ForestryAllele.BeeSpecies.Forest.getAllele(), ExtraBeesSpecies.GNAWING, 15);
        new ExtraBeeMutation(ExtraBeesSpecies.DESOLATE, ForestryAllele.BeeSpecies.Meadows.getAllele(), ExtraBeesSpecies.ROTTEN, 15);
        new ExtraBeeMutation(ExtraBeesSpecies.DESOLATE, ForestryAllele.BeeSpecies.Forest.getAllele(), ExtraBeesSpecies.BONE, 15);
        new ExtraBeeMutation(ExtraBeesSpecies.DESOLATE, ForestryAllele.BeeSpecies.Modest.getAllele(), ExtraBeesSpecies.CREEPER, 15);
        new ExtraBeeMutation(ExtraBeesSpecies.BARREN, ForestryAllele.BeeSpecies.Marshy.getAllele(), ExtraBeesSpecies.DECOMPOSING, 15);
        new ExtraBeeMutation(ExtraBeesSpecies.ROCK, ForestryAllele.BeeSpecies.Diligent.getAllele(), ExtraBeesSpecies.STONE, 12);
        new ExtraBeeMutation(ExtraBeesSpecies.STONE, ForestryAllele.BeeSpecies.Unweary.getAllele(), ExtraBeesSpecies.GRANITE, 10);
        new ExtraBeeMutation(ExtraBeesSpecies.GRANITE, ForestryAllele.BeeSpecies.Industrious.getAllele(), ExtraBeesSpecies.MINERAL, 6);
        new ExtraBeeMutation(ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Meadows.getAllele(), ExtraBeesSpecies.IRON, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Forest.getAllele(), ExtraBeesSpecies.IRON, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Wintry.getAllele(), ExtraBeesSpecies.COPPER, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Modest.getAllele(), ExtraBeesSpecies.COPPER, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Marshy.getAllele(), ExtraBeesSpecies.TIN, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Tropical.getAllele(), ExtraBeesSpecies.TIN, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Meadows.getAllele(), ExtraBeesSpecies.LEAD, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Modest.getAllele(), ExtraBeesSpecies.LEAD, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Wintry.getAllele(), ExtraBeesSpecies.ZINC, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Tropical.getAllele(), ExtraBeesSpecies.ZINC, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Forest.getAllele(), ExtraBeesSpecies.NICKEL, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Marshy.getAllele(), ExtraBeesSpecies.NICKEL, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Cultivated.getAllele(), ExtraBeesSpecies.TITANIUM, 3);
        new ExtraBeeMutation(ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Common.getAllele(), ExtraBeesSpecies.TUNGSTATE, 3);
        new ExtraBeeMutation(ExtraBeesSpecies.ZINC, ForestryAllele.BeeSpecies.Majestic.getAllele(), ExtraBeesSpecies.SILVER, 2);
        new ExtraBeeMutation(ExtraBeesSpecies.TIN, ForestryAllele.BeeSpecies.Majestic.getAllele(), ExtraBeesSpecies.SILVER, 2);
        new ExtraBeeMutation(ExtraBeesSpecies.LEAD, ForestryAllele.BeeSpecies.Majestic.getAllele(), ExtraBeesSpecies.SILVER, 2);
        new ExtraBeeMutation(ExtraBeesSpecies.TITANIUM, ForestryAllele.BeeSpecies.Majestic.getAllele(), ExtraBeesSpecies.SILVER, 3);
        new ExtraBeeMutation(ExtraBeesSpecies.IRON, ForestryAllele.BeeSpecies.Majestic.getAllele(), ExtraBeesSpecies.GOLD, 2);
        new ExtraBeeMutation(ExtraBeesSpecies.COPPER, ForestryAllele.BeeSpecies.Majestic.getAllele(), ExtraBeesSpecies.GOLD, 2);
        new ExtraBeeMutation(ExtraBeesSpecies.NICKEL, ForestryAllele.BeeSpecies.Majestic.getAllele(), ExtraBeesSpecies.GOLD, 2);
        new ExtraBeeMutation(ExtraBeesSpecies.TUNGSTATE, ForestryAllele.BeeSpecies.Majestic.getAllele(), ExtraBeesSpecies.GOLD, 3);
        new ExtraBeeMutation(ExtraBeesSpecies.GOLD, ExtraBeesSpecies.SILVER, ExtraBeesSpecies.PLATINUM, 2);
        new ExtraBeeMutation(ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Imperial.getAllele(), ExtraBeesSpecies.LAPIS, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.LAPIS, ForestryAllele.BeeSpecies.Forest.getAllele(), ExtraBeesSpecies.EMERALD, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.LAPIS, ForestryAllele.BeeSpecies.Modest.getAllele(), ExtraBeesSpecies.RUBY, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.LAPIS, ExtraBeesSpecies.WATER, ExtraBeesSpecies.SAPPHIRE, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.LAPIS, ForestryAllele.BeeSpecies.Cultivated.getAllele(), ExtraBeesSpecies.DIAMOND, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.PREHISTORIC, ExtraBeesSpecies.MINERAL, ExtraBeesSpecies.UNSTABLE, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.UNSTABLE, ExtraBeesSpecies.IRON, ExtraBeesSpecies.NUCLEAR, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.UNSTABLE, ExtraBeesSpecies.COPPER, ExtraBeesSpecies.NUCLEAR, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.UNSTABLE, ExtraBeesSpecies.TIN, ExtraBeesSpecies.NUCLEAR, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.UNSTABLE, ExtraBeesSpecies.ZINC, ExtraBeesSpecies.NUCLEAR, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.UNSTABLE, ExtraBeesSpecies.NICKEL, ExtraBeesSpecies.NUCLEAR, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.UNSTABLE, ExtraBeesSpecies.LEAD, ExtraBeesSpecies.NUCLEAR, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.NUCLEAR, ExtraBeesSpecies.GOLD, ExtraBeesSpecies.RADIOACTIVE, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.NUCLEAR, ExtraBeesSpecies.SILVER, ExtraBeesSpecies.RADIOACTIVE, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.NUCLEAR, ForestryAllele.BeeSpecies.Frugal.getAllele(), ExtraBeesSpecies.YELLORIUM, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.NUCLEAR, ExtraBeesSpecies.YELLORIUM, ExtraBeesSpecies.CYANITE, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.YELLORIUM, ExtraBeesSpecies.CYANITE, ExtraBeesSpecies.BLUTONIUM, 5);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Noble.getAllele(), ForestryAllele.BeeSpecies.Diligent.getAllele(), ExtraBeesSpecies.ANCIENT, 10);
        new ExtraBeeMutation(ExtraBeesSpecies.ANCIENT, ForestryAllele.BeeSpecies.Secluded.getAllele(), ExtraBeesSpecies.PRIMEVAL, 8);
        new ExtraBeeMutation(ExtraBeesSpecies.PRIMEVAL, ExtraBeesSpecies.ANCIENT, ExtraBeesSpecies.PREHISTORIC, 8);
        new ExtraBeeMutation(ExtraBeesSpecies.PREHISTORIC, ForestryAllele.BeeSpecies.Imperial.getAllele(), ExtraBeesSpecies.RELIC, 8);
        new ExtraBeeMutation(ExtraBeesSpecies.PRIMEVAL, ExtraBeesSpecies.GROWING, ExtraBeesSpecies.COAL, 8);
        new ExtraBeeMutation(ExtraBeesSpecies.PRIMEVAL, ForestryAllele.BeeSpecies.Rural.getAllele(), ExtraBeesSpecies.COAL, 8);
        new ExtraBeeMutation(ExtraBeesSpecies.PRIMEVAL, ForestryAllele.BeeSpecies.Miry.getAllele(), ExtraBeesSpecies.RESIN, 8);
        new ExtraBeeMutation(ExtraBeesSpecies.PRIMEVAL, ExtraBeesSpecies.OCEAN, ExtraBeesSpecies.OIL, 8);
        new ExtraBeeMutation(ExtraBeesSpecies.PRIMEVAL, ForestryAllele.BeeSpecies.Frugal.getAllele(), ExtraBeesSpecies.OIL, 8);
        new ExtraBeeMutation(ExtraBeesSpecies.OIL, ForestryAllele.BeeSpecies.Industrious.getAllele(), ExtraBeesSpecies.DISTILLED, 8);
        new ExtraBeeMutation(ExtraBeesSpecies.DISTILLED, ExtraBeesSpecies.OIL, ExtraBeesSpecies.FUEL, 8);
        new ExtraBeeMutation(ExtraBeesSpecies.DISTILLED, ExtraBeesSpecies.COAL, ExtraBeesSpecies.CREOSOTE, 8);
        new ExtraBeeMutation(ExtraBeesSpecies.DISTILLED, ExtraBeesSpecies.RESIN, ExtraBeesSpecies.LATEX, 8);
        new ExtraBeeMutation(ExtraBeesSpecies.WATER, ForestryAllele.BeeSpecies.Diligent.getAllele(), ExtraBeesSpecies.RIVER, 10, new RequirementBiomeType(BiomeDictionary.Type.RIVER));
        new ExtraBeeMutation(ExtraBeesSpecies.WATER, ForestryAllele.BeeSpecies.Diligent.getAllele(), ExtraBeesSpecies.OCEAN, 10, new RequirementBiomeType(BiomeDictionary.Type.OCEAN));
        new ExtraBeeMutation(ExtraBeesSpecies.BLACK, ExtraBeesSpecies.OCEAN, ExtraBeesSpecies.INK, 8);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Forest.getAllele(), ForestryAllele.BeeSpecies.Diligent.getAllele(), ExtraBeesSpecies.GROWING, 10);
        new ExtraBeeMutation(ExtraBeesSpecies.GROWING, ForestryAllele.BeeSpecies.Unweary.getAllele(), ExtraBeesSpecies.THRIVING, 10);
        new ExtraBeeMutation(ExtraBeesSpecies.THRIVING, ForestryAllele.BeeSpecies.Industrious.getAllele(), ExtraBeesSpecies.BLOOMING, 8);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Valiant.getAllele(), ForestryAllele.BeeSpecies.Diligent.getAllele(), ExtraBeesSpecies.SWEET, 15);
        new ExtraBeeMutation(ExtraBeesSpecies.SWEET, ForestryAllele.BeeSpecies.Rural.getAllele(), ExtraBeesSpecies.SUGAR, 15);
        new ExtraBeeMutation(ExtraBeesSpecies.SWEET, ExtraBeesSpecies.GROWING, ExtraBeesSpecies.RIPENING, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.SWEET, ExtraBeesSpecies.THRIVING, ExtraBeesSpecies.FRUIT, 5);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Farmerly.getAllele(), ForestryAllele.BeeSpecies.Meadows.getAllele(), ExtraBeesSpecies.ALCOHOL, 10);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Farmerly.getAllele(), ForestryAllele.BeeSpecies.Meadows.getAllele(), ExtraBeesSpecies.FARM, 10);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Farmerly.getAllele(), ExtraBeesSpecies.WATER, ExtraBeesSpecies.MILK, 10);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Farmerly.getAllele(), ForestryAllele.BeeSpecies.Tropical.getAllele(), ExtraBeesSpecies.COFFEE, 10);
        new ExtraBeeMutation(ExtraBeesSpecies.WATER, ForestryAllele.BeeSpecies.Miry.getAllele(), ExtraBeesSpecies.SWAMP, 10);
        new ExtraBeeMutation(ExtraBeesSpecies.SWAMP, ForestryAllele.BeeSpecies.Boggy.getAllele(), ExtraBeesSpecies.BOGGY, 8);
        new ExtraBeeMutation(ExtraBeesSpecies.BOGGY, ExtraBeesSpecies.SWAMP, ExtraBeesSpecies.FUNGAL, 8);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Boggy.getAllele(), ForestryAllele.BeeSpecies.Miry.getAllele(), ExtraBeesSpecies.FUNGAL, 8);
        new ExtraBeeMutation(ExtraBeesSpecies.WATER, ForestryAllele.BeeSpecies.Forest.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation(ExtraBeesSpecies.WATER, ForestryAllele.BeeSpecies.Meadows.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation(ExtraBeesSpecies.WATER, ForestryAllele.BeeSpecies.Modest.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation(ExtraBeesSpecies.WATER, ForestryAllele.BeeSpecies.Tropical.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation(ExtraBeesSpecies.WATER, ForestryAllele.BeeSpecies.Marshy.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation(ExtraBeesSpecies.WATER, ForestryAllele.BeeSpecies.Wintry.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation(ExtraBeesSpecies.WATER, ExtraBeesSpecies.ROCK, ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation(ExtraBeesSpecies.WATER, ExtraBeesSpecies.BASALT, ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation(ExtraBeesSpecies.ROCK, ForestryAllele.BeeSpecies.Forest.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation(ExtraBeesSpecies.ROCK, ForestryAllele.BeeSpecies.Meadows.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation(ExtraBeesSpecies.ROCK, ForestryAllele.BeeSpecies.Modest.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation(ExtraBeesSpecies.ROCK, ForestryAllele.BeeSpecies.Tropical.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation(ExtraBeesSpecies.ROCK, ForestryAllele.BeeSpecies.Marshy.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation(ExtraBeesSpecies.ROCK, ForestryAllele.BeeSpecies.Wintry.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation(ExtraBeesSpecies.ROCK, ExtraBeesSpecies.BASALT, ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation(ExtraBeesSpecies.BASALT, ForestryAllele.BeeSpecies.Forest.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation(ExtraBeesSpecies.BASALT, ForestryAllele.BeeSpecies.Meadows.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation(ExtraBeesSpecies.BASALT, ForestryAllele.BeeSpecies.Modest.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation(ExtraBeesSpecies.BASALT, ForestryAllele.BeeSpecies.Tropical.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation(ExtraBeesSpecies.BASALT, ForestryAllele.BeeSpecies.Marshy.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation(ExtraBeesSpecies.BASALT, ForestryAllele.BeeSpecies.Wintry.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation(ExtraBeesSpecies.WATER, ForestryAllele.BeeSpecies.Common.getAllele(), ForestryAllele.BeeSpecies.Cultivated.getTemplate(), 12);
        new ExtraBeeMutation(ExtraBeesSpecies.ROCK, ForestryAllele.BeeSpecies.Common.getAllele(), ForestryAllele.BeeSpecies.Cultivated.getTemplate(), 12);
        new ExtraBeeMutation(ExtraBeesSpecies.BASALT, ForestryAllele.BeeSpecies.Common.getAllele(), ForestryAllele.BeeSpecies.Cultivated.getTemplate(), 12);
        new ExtraBeeMutation(ExtraBeesSpecies.BASALT, ForestryAllele.BeeSpecies.Fiendish.getAllele(), ExtraBeesSpecies.TEMPERED, 30, new RequirementBiomeType(BiomeDictionary.Type.NETHER));
        new ExtraBeeMutation(ExtraBeesSpecies.TEMPERED, ForestryAllele.BeeSpecies.Demonic.getAllele(), ExtraBeesSpecies.VOLCANIC, 20, new RequirementBiomeType(BiomeDictionary.Type.NETHER));
        new ExtraBeeMutation(ExtraBeesSpecies.BASALT, ForestryAllele.BeeSpecies.Cultivated.getAllele(), ForestryAllele.BeeSpecies.Sinister.getTemplate(), 60, new RequirementBiomeType(BiomeDictionary.Type.NETHER));
        new ExtraBeeMutation(ExtraBeesSpecies.BASALT, ForestryAllele.BeeSpecies.Sinister.getAllele(), ForestryAllele.BeeSpecies.Fiendish.getTemplate(), 40, new RequirementBiomeType(BiomeDictionary.Type.NETHER));
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Sinister.getAllele(), ForestryAllele.BeeSpecies.Tropical.getAllele(), ExtraBeesSpecies.MALICIOUS, 10);
        new ExtraBeeMutation(ExtraBeesSpecies.MALICIOUS, ForestryAllele.BeeSpecies.Tropical.getAllele(), ExtraBeesSpecies.INFECTIOUS, 8);
        new ExtraBeeMutation(ExtraBeesSpecies.MALICIOUS, ExtraBeesSpecies.INFECTIOUS, ExtraBeesSpecies.VIRULENT, 8);
        new ExtraBeeMutation(ExtraBeesSpecies.WATER, ForestryAllele.BeeSpecies.Exotic.getAllele(), ExtraBeesSpecies.VISCOUS, 10);
        new ExtraBeeMutation(ExtraBeesSpecies.VISCOUS, ForestryAllele.BeeSpecies.Exotic.getAllele(), ExtraBeesSpecies.GLUTINOUS, 8);
        new ExtraBeeMutation(ExtraBeesSpecies.VISCOUS, ExtraBeesSpecies.GLUTINOUS, ExtraBeesSpecies.STICKY, 8);
        new ExtraBeeMutation(ExtraBeesSpecies.MALICIOUS, ExtraBeesSpecies.VISCOUS, ExtraBeesSpecies.CORROSIVE, 10);
        new ExtraBeeMutation(ExtraBeesSpecies.CORROSIVE, ForestryAllele.BeeSpecies.Fiendish.getAllele(), ExtraBeesSpecies.CAUSTIC, 8);
        new ExtraBeeMutation(ExtraBeesSpecies.CORROSIVE, ExtraBeesSpecies.CAUSTIC, ExtraBeesSpecies.ACIDIC, 4);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Cultivated.getAllele(), ForestryAllele.BeeSpecies.Valiant.getAllele(), ExtraBeesSpecies.EXCITED, 10);
        new ExtraBeeMutation(ExtraBeesSpecies.EXCITED, ForestryAllele.BeeSpecies.Diligent.getAllele(), ExtraBeesSpecies.ENERGETIC, 8);
        new ExtraBeeMutation(ExtraBeesSpecies.EXCITED, ExtraBeesSpecies.ENERGETIC, ExtraBeesSpecies.ECSTATIC, 8);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Wintry.getAllele(), ForestryAllele.BeeSpecies.Diligent.getAllele(), ExtraBeesSpecies.ARTIC, 10);
        new ExtraBeeMutation(ExtraBeesSpecies.OCEAN, ExtraBeesSpecies.ARTIC, ExtraBeesSpecies.FREEZING, 10);
        new ExtraBeeMutation(ExtraBeesSpecies.ROCK, ForestryAllele.BeeSpecies.Sinister.getAllele(), ExtraBeesSpecies.SHADOW, 10);
        new ExtraBeeMutation(ExtraBeesSpecies.SHADOW, ExtraBeesSpecies.ROCK, ExtraBeesSpecies.DARKENED, 8);
        new ExtraBeeMutation(ExtraBeesSpecies.SHADOW, ExtraBeesSpecies.DARKENED, ExtraBeesSpecies.ABYSS, 8);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Forest.getAllele(), ForestryAllele.BeeSpecies.Valiant.getAllele(), ExtraBeesSpecies.RED, 5);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Meadows.getAllele(), ForestryAllele.BeeSpecies.Valiant.getAllele(), ExtraBeesSpecies.YELLOW, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.WATER, ForestryAllele.BeeSpecies.Valiant.getAllele(), ExtraBeesSpecies.BLUE, 5);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Tropical.getAllele(), ForestryAllele.BeeSpecies.Valiant.getAllele(), ExtraBeesSpecies.GREEN, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.ROCK, ForestryAllele.BeeSpecies.Valiant.getAllele(), ExtraBeesSpecies.BLACK, 5);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Wintry.getAllele(), ForestryAllele.BeeSpecies.Valiant.getAllele(), ExtraBeesSpecies.WHITE, 5);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Marshy.getAllele(), ForestryAllele.BeeSpecies.Valiant.getAllele(), ExtraBeesSpecies.BROWN, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.RED, ExtraBeesSpecies.YELLOW, ExtraBeesSpecies.ORANGE, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.GREEN, ExtraBeesSpecies.BLUE, ExtraBeesSpecies.CYAN, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.RED, ExtraBeesSpecies.BLUE, ExtraBeesSpecies.PURPLE, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.BLACK, ExtraBeesSpecies.WHITE, ExtraBeesSpecies.GRAY, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.BLUE, ExtraBeesSpecies.WHITE, ExtraBeesSpecies.LIGHTBLUE, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.RED, ExtraBeesSpecies.WHITE, ExtraBeesSpecies.PINK, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.GREEN, ExtraBeesSpecies.WHITE, ExtraBeesSpecies.LIMEGREEN, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.PURPLE, ExtraBeesSpecies.PINK, ExtraBeesSpecies.MAGENTA, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.GRAY, ExtraBeesSpecies.WHITE, ExtraBeesSpecies.LIGHTGRAY, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.TEMPERED, ExtraBeesSpecies.EXCITED, ExtraBeesSpecies.GLOWSTONE, 5);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Austere.getAllele(), ExtraBeesSpecies.DESOLATE, ExtraBeesSpecies.HAZARDOUS, 5);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Ended.getAllele(), ExtraBeesSpecies.RELIC, ExtraBeesSpecies.JADED, 2, new RequirementPerson("jadedcat"));
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Austere.getAllele(), ExtraBeesSpecies.EXCITED, ExtraBeesSpecies.CELEBRATORY, 5);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Secluded.getAllele(), ForestryAllele.BeeSpecies.Ended.getAllele(), ExtraBeesSpecies.UNUSUAL, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.UNUSUAL, ForestryAllele.BeeSpecies.Hermitic.getAllele(), ExtraBeesSpecies.SPATIAL, 5);
        new ExtraBeeMutation(ExtraBeesSpecies.SPATIAL, ForestryAllele.BeeSpecies.Spectral.getAllele(), ExtraBeesSpecies.QUANTUM, 5);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Noble.getAllele(), ForestryAllele.BeeSpecies.Monastic.getAllele(), ExtraBeesSpecies.MYSTICAL, 5);
        for (final IBeeMutation mutation : ExtraBeeMutation.mutations) {
            Binnie.Genetics.getBeeRoot().registerMutation(mutation);
        }
    }

    public ExtraBeeMutation(final IAlleleBeeSpecies allele0, final IAlleleBeeSpecies allele1, final ExtraBeesSpecies mutation, final int chance) {
        this(allele0, allele1, mutation.getTemplate(), chance, null);
    }

    public ExtraBeeMutation(final IAlleleBeeSpecies allele0, final IAlleleBeeSpecies allele1, final ExtraBeesSpecies mutation, final int chance, final MutationRequirement req) {
        this(allele0, allele1, mutation.getTemplate(), chance, req);
    }

    public ExtraBeeMutation(final IAlleleBeeSpecies allele0, final IAlleleBeeSpecies allele1, final IAllele[] mutation, final int chance) {
        this(allele0, allele1, mutation, chance, null);
    }

    public ExtraBeeMutation(final IAlleleBeeSpecies allele0, final IAlleleBeeSpecies allele1, final IAllele[] mutation, final int chance, final MutationRequirement req) {
        this.species0 = null;
        this.species1 = null;
        this.template = new IAllele[0];
        this.chance = 80;
        this.chance = chance;
        this.req = req;
        this.species0 = allele0;
        this.species1 = allele1;
        this.template = mutation;
        if (this.species0 != null && this.species1 != null && this.template != null) {
            ExtraBeeMutation.mutations.add(this);
        }
    }

    @Override
    public IAlleleSpecies getAllele0() {
        return this.species0;
    }

    @Override
    public IAlleleSpecies getAllele1() {
        return this.species1;
    }

    @Override
    public IAllele[] getTemplate() {
        return this.template;
    }

    @Override
    public float getBaseChance() {
        return this.chance;
    }

    @Override
    public boolean isPartner(final IAllele allele) {
        return allele.getUID().equals(this.species0.getUID()) || allele.getUID().equals(this.species1.getUID());
    }

    @Override
    public IAllele getPartner(final IAllele allele) {
        return allele.getUID().equals(this.species0.getUID()) ? this.species1 : this.species0;
    }

    @Override
    public boolean isSecret() {
        return false;
    }

    @Override
    public float getChance(final IBeeHousing housing, final IAlleleBeeSpecies allele0, final IAlleleBeeSpecies allele1, final IBeeGenome genome0, final IBeeGenome genome1) {
        if (this.species0 == null || this.species1 == null || allele0 == null || allele1 == null) {
            return 0.0f;
        }

        final World world = housing.getWorldObj();
        final Biome biome = housing.getBiome();
        if (this.req != null && !this.req.fufilled(housing, allele0, allele1, genome0, genome1)) {
            return 0.0f;
        }

        final int processedChance = Math.round(this.chance * BeeManager.beeRoot.createBeeHousingModifier(housing).getMutationModifier(genome0, genome1, 1.0f)
                * Binnie.Genetics.getBeeRoot().getBeekeepingMode(world).getBeeModifier().getMutationModifier(genome0, genome1, 1.0f));
        if (this.species0.getUID().equals(allele0.getUID()) && this.species1.getUID().equals(allele1.getUID())) {
            return processedChance;
        }
        if (this.species1.getUID().equals(allele0.getUID()) && this.species0.getUID().equals(allele1.getUID())) {
            return processedChance;
        }
        return 0.0f;
    }

    @Override
    public Collection<String> getSpecialConditions() {
        final List<String> conditions = new ArrayList<>();
        if (this.req != null) {
            Collections.addAll(conditions, this.req.tooltip());
        }
        return conditions;
    }

    @Override
    public IBeeRoot getRoot() {
        return Binnie.Genetics.getBeeRoot();
    }

    abstract static class MutationRequirement {
        public abstract String[] tooltip();

        public abstract boolean fufilled(final IBeeHousing p0, final IAllele p1, final IAllele p2, final IGenome p3, final IGenome p4);
    }

    static class RequirementBiomeType extends MutationRequirement {
        BiomeDictionary.Type type;

        public RequirementBiomeType(final BiomeDictionary.Type type) {
            this.type = type;
        }

        @Override
        public String[] tooltip() {
            return new String[]{"Is restricted to " + this.type + "-like biomes."};
        }

        @Override
        public boolean fufilled(final IBeeHousing housing, final IAllele allele0, final IAllele allele1, final IGenome genome0, final IGenome genome1) {
            return BiomeDictionary.isBiomeOfType(housing.getBiome(), this.type);
        }
    }

    static class RequirementPerson extends MutationRequirement {
        String name;

        public RequirementPerson(final String name) {
            this.name = name;
        }

        @Override
        public String[] tooltip() {
            return new String[]{"Can only be bred by " + this.name};
        }

        @Override
        public boolean fufilled(final IBeeHousing housing, final IAllele allele0, final IAllele allele1, final IGenome genome0, final IGenome genome1) {
            return housing.getOwner().getName() != null && housing.getOwner().getName().equals(this.name);
        }
    }
}
