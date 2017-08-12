package binnie.extratrees.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

import binnie.core.modules.IConfigHandler;
import binnie.core.modules.IModuleContainer;

public class ConfigurationMain implements IConfigHandler {
	public static boolean alterLemon = true;

	private final Configuration config;

	public ConfigurationMain(IModuleContainer container) {
		this.config = new Configuration(new File(container.getConfigFolder(), "main.cfg"));
	}

	@Override
	public void loadConfig() {
		//TODO: Localise comment
		alterLemon = config.getBoolean("lemon.citrus.family", "general", alterLemon, "Uses reflection to convert the Forestry lemon tree to the Citrus family.");
		if(config.hasChanged()){
			config.save();
		}
	}
}
