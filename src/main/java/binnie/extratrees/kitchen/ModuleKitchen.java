package binnie.extratrees.kitchen;

import binnie.core.IInitializable;
import binnie.core.machines.MachineGroup;
import binnie.extratrees.ExtraTrees;
import forestry.api.core.Tabs;
import net.minecraft.block.Block;

public class ModuleKitchen implements IInitializable {
	
	public Block blockKitchen;
	
	@Override
	public void preInit() {
		final MachineGroup machineGroup = new MachineGroup(ExtraTrees.instance, "kitchen", "kitchen", KitchenMachine.values());
		machineGroup.setCreativeTab(Tabs.tabArboriculture);
		machineGroup.customRenderer = false;
		blockKitchen = machineGroup.getBlock();
	}

	@Override
	public void init() {
	}

	@Override
	public void postInit() {
	}
}
