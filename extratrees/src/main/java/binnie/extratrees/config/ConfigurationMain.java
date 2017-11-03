package binnie.extratrees.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

import binnie.core.modules.IConfigHandler;
import binnie.core.modules.ModuleContainer;

public class ConfigurationMain implements IConfigHandler {
	public static boolean alterLemon = true;
	public static boolean hopeField = true;

	private final Configuration config;

	public ConfigurationMain(ModuleContainer container) {
		this.config = new Configuration(new File(container.getConfigFolder(), "main.cfg"));
	}

	@Override
	public void loadConfig() {
		//TODO: Localise comment
		//Core Module
		hopeField = config.getBoolean("village.hopeField", "general", alterLemon, "Adds a hope field to the village generation.");
		//Wood Module
		alterLemon = config.getBoolean("lemon.citrus.family", "general", alterLemon, "Uses reflection to convert the Forestry lemon tree to the Citrus family.");
		if(config.hasChanged()){
			config.save();
		}
	}
}
