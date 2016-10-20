// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.config;

import binnie.core.mod.config.PropBoolean;
import binnie.core.mod.config.ConfigProperty;
import binnie.core.mod.config.ConfigFile;

@ConfigFile(filename = "/config/forestry/extratrees/main.conf")
public class ConfigurationMain
{
	@ConfigProperty(key = "lemon.citrus.family", comment = { "Uses reflection to convert the Forestry lemon tree to the Citrus family." })
	@PropBoolean
	public static boolean alterLemon;

	static {
		ConfigurationMain.alterLemon = true;
	}
}
