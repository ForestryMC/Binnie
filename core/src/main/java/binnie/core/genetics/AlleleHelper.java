package binnie.core.genetics;

import java.util.Locale;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.EnumTolerance;
import forestry.api.genetics.IAllele;
import forestry.core.genetics.alleles.EnumAllele;

import binnie.core.util.I18N;

import javax.annotation.Nullable;

public class AlleleHelper extends forestry.core.genetics.alleles.AlleleHelper {
	public static IAllele getAllele(final EnumTemperature temperature) {
		return getAllele(getUid(temperature));
	}

	public static IAllele getAllele(final EnumHumidity humidity) {
		return getAllele(getUid(humidity));
	}

	public static IAllele getAllele(final EnumTolerance tolerance) {
		return getAllele(getUid(tolerance));
	}

	public static IAllele getAllele(final EnumAllele.Fertility fertility) {
		return getAllele(getUid(fertility));
	}

	public static IAllele getAllele(final EnumAllele.Territory territory) {
		return getAllele(getUid(territory));
	}

	public static IAllele getAllele(final EnumAllele.Lifespan lifespan) {
		return getAllele(getUid(lifespan));
	}

	public static IAllele getAllele(final EnumAllele.Sappiness sappiness) {
		return getAllele(getUid(sappiness));
	}

	public static IAllele getAllele(final EnumAllele.Speed speed) {
		return getAllele(getUid(speed));
	}

	public static IAllele getAllele(final EnumAllele.Flowering flowering) {
		return getAllele(getUid(flowering));
	}

	public static IAllele getAllele(final EnumAllele.Height height) {
		return getAllele(getUid(height));
	}

	public static IAllele getAllele(final EnumAllele.Maturation maturation) {
		return getAllele(getUid(maturation));
	}

	public static IAllele getAllele(final EnumAllele.Yield yield) {
		return getAllele(getUid(yield));
	}

	public static IAllele getAllele(final EnumAllele.Saplings saplings) {
		return getAllele(getUid(saplings));
	}

	public static IAllele getAllele(final int number) {
		return getAllele("forestry.i" + number + 'd');
	}

	public static IAllele getAllele(final boolean bool) {
		if (bool) {
			return getAllele("forestry.boolTrue");
		}
		return getAllele("forestry.boolFalse");
	}

	public static IAllele getAllele(final String uid) {
		return AlleleManager.alleleRegistry.getAllele(uid);
	}

	public static String toDisplay(final EnumTemperature temperature) {
		return AlleleManager.climateHelper.toDisplay(temperature);
	}

	public static String toDisplay(final EnumHumidity humidity) {
		return AlleleManager.climateHelper.toDisplay(humidity);
	}

//	public static String toDisplay(EnumMoisture moisture) {
//		return I18N.localise("botany.moisture." + moisture.getName());
//	}
//
//	public static String toDisplay(EnumAcidity acidity) {
//		return I18N.localise("botany.ph." + acidity.getName());
//	}
//
//	public static String toDisplay(EnumSoilType soilType) {
//		return I18N.localise("botany.soil." + soilType.getName());
//	}

	public static String toDisplay(final EnumTolerance tolerance) {
		return toAlleleDisplay("tolerance", tolerance.name());
	}

	public static String toDisplay(final EnumAllele.Flowering flowering) {
		if (flowering == EnumAllele.Flowering.AVERAGE) {
			return toAlleleDisplay("flowering", "normal");
		}
		return toAlleleDisplay(null, flowering.name());
	}

	public static String toDisplay(final EnumAllele.Speed speed) {
		return toAlleleDisplay(null, speed.name());
	}

	public static String toDisplay(final EnumAllele.Lifespan lifespan) {
		return toAlleleDisplay("lifespan", lifespan.name());
	}

	public static String getUid(final EnumAllele.Fertility fertility) {
		return getUid("fertility", fertility.name());
	}

	public static String getUid(final EnumAllele.Lifespan lifespan) {
		return getUid("lifespan", lifespan.name());
	}

	public static String getUid(final EnumAllele.Sappiness sappiness) {
		return getUid("sappiness", sappiness.name());
	}

	private static String getUid(final EnumTemperature temperature) {
		return getUid("temperature", temperature.name());
	}

	private static String getUid(final EnumHumidity humidity) {
		return getUid("humidity", humidity.name());
	}

	public static String getUid(final EnumAllele.Saplings saplings) {
		if (saplings == EnumAllele.Saplings.AVERAGE) {
			return getUid("saplings", "Default");
		}
		if (saplings == EnumAllele.Saplings.HIGH) {
			return getUid("saplings", "Double");
		}
		if (saplings == EnumAllele.Saplings.HIGHER) {
			return getUid("saplings", "Triple");
		}
		return getUid("saplings", saplings.name());
	}

	public static String getUid(final EnumAllele.Height height) {
		if (height == EnumAllele.Height.AVERAGE) {
			return getUid("height", "Max10", false);
		}
		return getUid("height", height.name());
	}

	public static String getUid(final EnumAllele.Maturation maturation) {
		return getUid("maturation", maturation.name());
	}

	public static String getUid(final EnumAllele.Territory territory) {
		return getUid("territory", territory.name());
	}

	public static String getUid(final EnumAllele.Yield yield) {
		if (yield == EnumAllele.Yield.AVERAGE) {
			return getUid("yield", "Default", false);
		}
		return getUid("yield", yield.name());
	}

	public static String getUid(final EnumAllele.Fireproof fireproof) {
		return getUid("bool", fireproof.name());
	}

	public static String getUid(final EnumAllele.Flowering flowering) {
		return getUid("flowering", flowering.name());
	}

	public static String getUid(final EnumTolerance tolerance) {
		return getUid("tolerance", tolerance.name());
	}

	public static String getUid(final EnumAllele.Speed speed) {
		return getUid("speed", speed.name());
	}

	protected static String getUid(final String key, final String valueName) {
		return getUid(key, valueName, true);
	}

	private static final Pattern PATTERN_REPLACEMENT = Pattern.compile("_");

	private static String getUid(final String key, String valueName, final boolean needCapitalize) {
		if (needCapitalize) {
			valueName = WordUtils.capitalize(valueName.toLowerCase(Locale.ENGLISH));
		}
		valueName = PATTERN_REPLACEMENT.matcher(valueName).replaceAll(StringUtils.EMPTY);
		return "forestry." + key + valueName;
	}

	private static String toAlleleDisplay(final @Nullable String key, final String valueName) {
		String name = PATTERN_REPLACEMENT.matcher(valueName.toLowerCase()).replaceAll(StringUtils.EMPTY);
		if (key == null) {
			return I18N.localise("forestry.allele." + name);
		}
		return I18N.localise("forestry.allele." + key + '.' + name);
	}
}
