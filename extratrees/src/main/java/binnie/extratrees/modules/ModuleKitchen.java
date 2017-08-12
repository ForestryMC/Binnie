package binnie.extratrees.modules;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import binnie.core.Constants;
import binnie.core.modules.ExtraTreesModuleUIDs;
import binnie.core.modules.BinnieModule;
import binnie.core.modules.Module;

@BinnieModule(moduleID = ExtraTreesModuleUIDs.KITCHEN, moduleContainerID = Constants.EXTRA_TREES_MOD_ID, name = "Kitchen", unlocalizedDescription = "extratrees.module.kitchen")
public class ModuleKitchen extends Module {

	public static Block blockKitchen = Blocks.AIR;

	@Override
	public void registerItemsAndBlocks() {
		// TODO implement kitchen
		/*final MachineGroup machineGroup = new MachineGroup(ExtraTrees.instance, "kitchen", "kitchen", KitchenMachine.values());
		machineGroup.setCreativeTab(Tabs.tabArboriculture);
		machineGroup.customRenderer = false;
		blockKitchen = machineGroup.getBlock();*/
	}

	@Override
	public void init() {
	}

	@Override
	public void postInit() {
	}
}
