package binnie.genetics.genetics;

import binnie.botany.Botany;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.EnumSoilType;
import binnie.core.util.I18N;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.EnumTolerance;
import forestry.core.genetics.alleles.EnumAllele;
import org.apache.commons.lang3.text.WordUtils;

import java.util.Locale;

public class AlleleHelper {
	public static String toDisplay(EnumTemperature temperature) {
		return AlleleManager.climateHelper.toDisplay(temperature);
	}

	public static String toDisplay(EnumHumidity humidity) {
		return AlleleManager.climateHelper.toDisplay(humidity);
	}

	public static String toDisplay(EnumMoisture moisture) {
		return I18N.localise(Botany.instance, "moisture." + moisture.getID());
	}

	public static String toDisplay(EnumAcidity acidity) {
		return I18N.localise(Botany.instance, "ph." + acidity.getID());
	}

	public static String toDisplay(EnumSoilType soilType) {
		return I18N.localise(Botany.instance, "soil." + soilType.getID());
	}
	
	public static String toDisplay(EnumTolerance tolerance) {
		return toAlleleDisplay("tolerance", tolerance.name());
	}

	public static String getUid(EnumAllele.Fertility fertility) {
		return getUid("fertility", fertility.name());
	}

	public static String getUid(EnumAllele.Lifespan lifespan) {
		return getUid("lifespan", lifespan.name());
	}

	public static String getUid(EnumAllele.Sappiness sappiness) {
		return getUid("sappiness", sappiness.name());
	}

	public static String getUid(EnumAllele.Saplings saplings) {
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

	public static String getUid(EnumAllele.Height height) {
		if (height == EnumAllele.Height.AVERAGE) {
			return getUid("height", "Max10", false);
		}
		return getUid("height", height.name());
	}

	public static String getUid(EnumAllele.Maturation maturation) {
		return getUid("maturation", maturation.name());
	}

	public static String getUid(EnumAllele.Territory territory) {
		return getUid("territory", territory.name());
	}

	public static String getUid(EnumAllele.Yield yield) {
		if (yield == EnumAllele.Yield.AVERAGE) {
			return getUid("yield", "Default", false);
		}
		return getUid("yield", yield.name());
	}

	public static String getUid(EnumAllele.Fireproof fireproof) {
		return getUid("bool", fireproof.name());
	}

	public static String getUid(EnumAllele.Flowering flowering) {
		return getUid("flowering", flowering.name());
	}

	public static String getUid(EnumTolerance tolerance) {
		return getUid("tolerance", tolerance.name());
	}

	public static String getUid(EnumAllele.Speed speed) {
		return getUid("speed", speed.name());
	}

	protected static String getUid(String key, String valueName) {
		return getUid(key, valueName, true);
	}

	protected static String getUid(String key, String valueName, boolean needCapitalize) {
		if (needCapitalize) {
			valueName = WordUtils.capitalize(valueName.toLowerCase(Locale.ENGLISH));
		}
		return "forestry." + key + valueName;
	}
	
	protected static String toAlleleDisplay(String key, String valueName) {
		String name = valueName.toLowerCase().replace("_", "");
		return I18N.localise("forestry.allele." + key + "." + name);
	}
}
