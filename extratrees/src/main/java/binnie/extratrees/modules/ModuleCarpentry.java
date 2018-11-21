package binnie.extratrees.modules;

import forestry.api.modules.ForestryModule;

import binnie.core.Constants;
import binnie.core.modules.BinnieModule;
import binnie.core.modules.ExtraTreesModuleUIDs;
import binnie.design.DesignHelper;
import binnie.design.api.IDesignSystem;
import binnie.design.blocks.DesignBlock;
import binnie.extratrees.api.CarpentryManager;
import binnie.extratrees.carpentry.CarpentryInterface;

@ForestryModule(moduleID = ExtraTreesModuleUIDs.CARPENTRY, containerID = Constants.EXTRA_TREES_MOD_ID, name = "Carpentry", unlocalizedDescription = "extratrees.module.carpentry")
public class ModuleCarpentry extends BinnieModule {

	static {
		CarpentryManager.carpentryInterface = new CarpentryInterface();
	}

	public ModuleCarpentry() {
		super(Constants.EXTRA_TREES_MOD_ID, ExtraTreesModuleUIDs.MACHINES);
	}

	public static DesignBlock getCarpentryPanel(final IDesignSystem system, final int meta) {
		final DesignBlock block = DesignHelper.getDesignBlock(system, meta);
		block.setPanel();
		return block;
	}
}
