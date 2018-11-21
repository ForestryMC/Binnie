package binnie.botany.modules;

import forestry.api.modules.ForestryModule;

import binnie.core.Constants;
import binnie.core.modules.BinnieModule;
import binnie.core.modules.BotanyModuleUIDs;

@ForestryModule(moduleID = BotanyModuleUIDs.CORE, containerID = Constants.BOTANY_MOD_ID, name = "Core", coreModule = true)
public class ModuleCore extends BinnieModule {

	public ModuleCore() {
		super("forestry", "core");
	}
}
