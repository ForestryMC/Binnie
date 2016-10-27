package binnie.extratrees.kitchen;

import binnie.core.IInitializable;
import binnie.core.machines.MachineGroup;
import binnie.extratrees.ExtraTrees;
import forestry.api.core.Tabs;

public class ModuleKitchen implements IInitializable {
	@Override
	public void preInit() {
		final MachineGroup machineGroup = new MachineGroup(ExtraTrees.instance, "kitchen", "kitchen", KitchenMachine.values());
		machineGroup.setCreativeTab(Tabs.tabArboriculture);
		machineGroup.customRenderer = false;
		ExtraTrees.blockKitchen = machineGroup.getBlock();
	}

	@Override
	public void init() {
	}

	@Override
	public void postInit() {
	}
}
