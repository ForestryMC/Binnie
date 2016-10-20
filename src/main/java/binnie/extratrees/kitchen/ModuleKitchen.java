// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.kitchen;

import net.minecraft.block.Block;
import forestry.api.core.Tabs;
import binnie.core.machines.MachineGroup;
import binnie.extratrees.ExtraTrees;
import binnie.core.IInitializable;

public class ModuleKitchen implements IInitializable
{
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
