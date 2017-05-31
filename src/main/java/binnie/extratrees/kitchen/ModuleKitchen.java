package binnie.extratrees.kitchen;

import binnie.core.IInitializable;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class ModuleKitchen implements IInitializable {

	public Block blockKitchen = Blocks.AIR;

	@Override
	public void preInit() {
		// TODO implement kitchen
		//		final MachineGroup machineGroup = new MachineGroup(ExtraTrees.instance, "kitchen", "kitchen", KitchenMachine.values());
		//		machineGroup.setCreativeTab(Tabs.tabArboriculture);
		//		machineGroup.customRenderer = false;
		//		blockKitchen = machineGroup.getBlock();
	}

	@Override
	public void init() {
	}

	@Override
	public void postInit() {
	}
}
