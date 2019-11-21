package binnie.extrabees.modules;

import binnie.core.Constants;
import binnie.core.modules.BlankModule;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.items.types.EnumHiveFrame;
import forestry.api.modules.ForestryModule;

@ForestryModule(moduleID = ExtraBeesModuleUIDs.FRAMES, containerID = Constants.EXTRA_BEES_MOD_ID, name = "Frames", unlocalizedDescription = "extrabees.module.frames")
public class ModuleFrames extends BlankModule {

	public ModuleFrames() {
		super(Constants.EXTRA_BEES_MOD_ID, ExtraBeesModuleUIDs.CORE);
	}

	@Override
	public void preInit() {
		for (EnumHiveFrame frame : EnumHiveFrame.values()) {
			ExtraBees.proxy.registerItem(frame.getItem());
		}
	}

	@Override
	public void registerRecipes() {
		EnumHiveFrame.init();
	}
}
