package binnie.botany.modules;

import forestry.api.modules.ForestryModule;

import binnie.core.Constants;
import binnie.core.modules.BlankModule;
import binnie.core.modules.BotanyModuleUIDs;

@ForestryModule(moduleID = BotanyModuleUIDs.CORE, containerID = Constants.BOTANY_MOD_ID, name = "Core", coreModule = true)
public class ModuleCore extends BlankModule {

	public ModuleCore() {
		super("forestry", "core");
	}

	@Override
	public boolean canBeDisabled() {
		return false;
	}
}
