package binnie.botany.modules;

import binnie.core.Constants;
import binnie.core.modules.BlankModule;
import binnie.core.modules.BotanyModuleUIDs;
import forestry.api.modules.ForestryModule;

@ForestryModule(moduleID = BotanyModuleUIDs.CORE, containerID = Constants.BOTANY_MOD_ID, name = "Core", coreModule = true)
public class ModuleCore extends BlankModule {

	public ModuleCore() {
		super("forestry", "core");
	}
}
