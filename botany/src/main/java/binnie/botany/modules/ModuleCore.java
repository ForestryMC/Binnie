package binnie.botany.modules;

import binnie.core.Constants;
import binnie.core.modules.BinnieModule;
import binnie.core.modules.BotanyModuleUIDs;
import binnie.core.modules.Module;

@BinnieModule(moduleID = BotanyModuleUIDs.CORE, moduleContainerID = Constants.BOTANY_MOD_ID, name = "Core", coreModule = true)
public class ModuleCore extends Module {
	@Override
	public void preInit() {
		// ignored
	}

	@Override
	public void init() {
		// ignored
	}

	@Override
	public void postInit() {
		// ignored
	}

	@Override
	public boolean canBeDisabled() {
		return false;
	}
}
