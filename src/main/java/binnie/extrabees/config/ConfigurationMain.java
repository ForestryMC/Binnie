package binnie.extrabees.config;

import binnie.core.mod.config.ConfigFile;
import binnie.core.mod.config.ConfigProperty;
import binnie.core.mod.config.PropBoolean;
import binnie.core.mod.config.PropInteger;

@ConfigFile(filename = "/config/forestry/extrabees/main.conf")
public class ConfigurationMain {
    @ConfigProperty(key = "canQuarryMineHives")
    @PropBoolean
    public static boolean canQuarryMineHives;
    @ConfigProperty(key = "waterHiveRate")
    @PropInteger
    public static int waterHiveRate;
    @ConfigProperty(key = "rockHiveRate")
    @PropInteger
    public static int rockHiveRate;
    @ConfigProperty(key = "netherHiveRate")
    @PropInteger
    public static int netherHiveRate;
    @ConfigProperty(key = "marbleHiveRate")
    @PropInteger
    public static int marbleHiveRate;

    static {
        ConfigurationMain.canQuarryMineHives = true;
        ConfigurationMain.waterHiveRate = 1;
        ConfigurationMain.rockHiveRate = 2;
        ConfigurationMain.netherHiveRate = 2;
        ConfigurationMain.marbleHiveRate = 2;
    }
}
