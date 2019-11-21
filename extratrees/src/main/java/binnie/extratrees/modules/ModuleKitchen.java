package binnie.extratrees.modules;

import binnie.core.Constants;
import binnie.core.modules.BlankModule;
import binnie.core.modules.ExtraTreesModuleUIDs;
import forestry.api.modules.ForestryModule;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

@ForestryModule(moduleID = ExtraTreesModuleUIDs.KITCHEN, containerID = Constants.EXTRA_TREES_MOD_ID, name = "Kitchen", unlocalizedDescription = "extratrees.module.kitchen")
public class ModuleKitchen extends BlankModule {

	public static Block blockKitchen = Blocks.AIR;

	public ModuleKitchen() {
		super(Constants.EXTRA_TREES_MOD_ID, ExtraTreesModuleUIDs.CORE);
	}

	@Override
	public void registerItemsAndBlocks() {
		// TODO implement kitchen
		/*final MachineGroup machineGroup = new MachineGroup(ExtraTrees.instance, "kitchen", "kitchen", KitchenMachine.values());
		machineGroup.setCreativeTab(Tabs.tabArboriculture);
		machineGroup.customRenderer = false;
		blockKitchen = machineGroup.getBlock();*/
	}
}
