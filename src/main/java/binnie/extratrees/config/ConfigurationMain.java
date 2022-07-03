package binnie.extratrees.config;

import binnie.core.mod.config.ConfigFile;
import binnie.core.mod.config.ConfigProperty;
import binnie.core.mod.config.PropBoolean;

@ConfigFile(filename = "/config/forestry/extratrees/main.conf")
public class ConfigurationMain {
    @ConfigProperty(
            key = "lemon.citrus.family",
            comment = {"Uses reflection to convert the Forestry lemon tree to the Citrus family."})
    @PropBoolean
    public static boolean alterLemon = true;
}
