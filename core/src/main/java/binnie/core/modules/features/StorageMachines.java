package binnie.core.modules.features;

import net.minecraft.creativetab.CreativeTabs;

import binnie.core.BinnieCore;
import binnie.core.Constants;
import binnie.core.features.FeatureProvider;
import binnie.core.features.IFeatureRegistry;
import binnie.core.machines.MachineGroup;
import binnie.core.machines.storage.Compartment;
import binnie.core.modules.BinnieCoreModuleUIDs;

@FeatureProvider(containerId = Constants.CORE_MOD_ID, moduleID = BinnieCoreModuleUIDs.STORAGE)
public class StorageMachines {
	public static final MachineGroup COMPARTMENT = features().createMachine("machine.storage", "storage", Compartment.values()).setCreativeTab(CreativeTabs.BUILDING_BLOCKS);

	private static IFeatureRegistry features() {
		return BinnieCore.instance.registry(BinnieCoreModuleUIDs.STORAGE);
	}

	private StorageMachines() {
	}
}
