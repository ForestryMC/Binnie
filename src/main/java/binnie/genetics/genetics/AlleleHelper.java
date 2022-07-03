package binnie.genetics.genetics;

import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.EnumSoilType;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.core.util.I18N;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.EnumTolerance;
import forestry.api.genetics.IAllele;
import forestry.core.genetics.alleles.EnumAllele;
import java.util.Locale;
import org.apache.commons.lang3.text.WordUtils;

public class AlleleHelper extends forestry.core.genetics.alleles.AlleleHelper {
    public static IAllele getAllele(EnumTemperature temperature) {
        return getAllele(getUid(temperature));
    }

    public static IAllele getAllele(EnumHumidity humidity) {
        return getAllele(getUid(humidity));
    }

    public static IAllele getAllele(EnumTolerance tolerance) {
        return getAllele(getUid(tolerance));
    }

    public static IAllele getAllele(EnumAllele.Fertility fertility) {
        return getAllele(getUid(fertility));
    }

    public static IAllele getAllele(EnumAllele.Territory territory) {
        return getAllele(getUid(territory));
    }

    public static IAllele getAllele(EnumAllele.Lifespan lifespan) {
        return getAllele(getUid(lifespan));
    }

    public static IAllele getAllele(EnumAllele.Sappiness sappiness) {
        return getAllele(getUid(sappiness));
    }

    public static IAllele getAllele(EnumAllele.Speed speed) {
        return getAllele(getUid(speed));
    }

    public static IAllele getAllele(EnumAllele.Flowering flowering) {
        return getAllele(getUid(flowering));
    }

    public static IAllele getAllele(EnumAllele.Height height) {
        return getAllele(getUid(height));
    }

    public static IAllele getAllele(EnumAllele.Maturation maturation) {
        return getAllele(getUid(maturation));
    }

    public static IAllele getAllele(EnumAllele.Yield yield) {
        return getAllele(getUid(yield));
    }

    public static IAllele getAllele(EnumAllele.Saplings saplings) {
        return getAllele(getUid(saplings));
    }

    public static IAllele getAllele(EnumFlowerColor color) {
        return color.getAllele();
    }

    public static IAllele getAllele(int number) {
        return getAllele("forestry.i" + number + "d");
    }

    public static IAllele getAllele(boolean bool) {
        if (bool) {
            return getAllele("forestry.boolTrue");
        }
        return getAllele("forestry.boolFalse");
    }

    public static IAllele getAllele(String uid) {
        return AlleleManager.alleleRegistry.getAllele(uid);
    }

    public static String toDisplay(EnumTemperature temperature) {
        return AlleleManager.climateHelper.toDisplay(temperature);
    }

    public static String toDisplay(EnumHumidity humidity) {
        return AlleleManager.climateHelper.toDisplay(humidity);
    }

    public static String toDisplay(EnumMoisture moisture) {
        return I18N.localise("botany.moisture." + moisture.getID());
    }

    public static String toDisplay(EnumAcidity acidity) {
        return I18N.localise("botany.ph." + acidity.getID());
    }

    public static String toDisplay(EnumSoilType soilType) {
        return I18N.localise("botany.soil." + soilType.getID());
    }

    public static String toDisplay(EnumTolerance tolerance) {
        return toAlleleDisplay("tolerance", tolerance.name());
    }

    public static String toDisplay(EnumAllele.Flowering flowering) {
        if (flowering == EnumAllele.Flowering.AVERAGE) {
            return toAlleleDisplay("flowering", "normal");
        }
        return toAlleleDisplay(null, flowering.name());
    }

    public static String toDisplay(EnumAllele.Speed speed) {
        return toAlleleDisplay(null, speed.name());
    }

    public static String toDisplay(EnumAllele.Lifespan lifespan) {
        return toAlleleDisplay("lifespan", lifespan.name());
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

    private static String getUid(EnumTemperature temperature) {
        return getUid("temperature", temperature.name());
    }

    private static String getUid(EnumHumidity humidity) {
        return getUid("humidity", humidity.name());
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

    private static String getUid(String key, String valueName, boolean needCapitalize) {
        if (needCapitalize) {
            valueName = WordUtils.capitalize(valueName.toLowerCase(Locale.ENGLISH));
        }
        valueName = valueName.replace("_", "");
        return "forestry." + key + valueName;
    }

    private static String toAlleleDisplay(String key, String valueName) {
        String name = valueName.toLowerCase().replace("_", "");
        if (key == null) {
            return I18N.localise("forestry.allele." + name);
        }
        return I18N.localise("forestry.allele." + key + "." + name);
    }
}
