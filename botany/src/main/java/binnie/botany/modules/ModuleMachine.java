package binnie.botany.modules;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

import forestry.api.core.Tabs;

import binnie.botany.Botany;
import binnie.botany.machines.BotanyMachine;
import binnie.core.Constants;
import binnie.core.Mods;
import binnie.core.machines.MachineGroup;
import binnie.core.modules.BinnieModule;
import binnie.core.modules.BotanyModuleUIDs;
import binnie.core.modules.ExtraTreesModuleUIDs;
import binnie.core.modules.Module;
import binnie.core.modules.ModuleManager;
import binnie.core.util.RecipeUtil;
import binnie.extratrees.items.ExtraTreeItems;

@BinnieModule(moduleID = BotanyModuleUIDs.MACHINES, moduleContainerID = Constants.BOTANY_MOD_ID, name = "Machines", unlocalizedDescription = "botany.module.machines")
public class ModuleMachine extends Module {
	public static Block blockMachine;

	@Override
	public void registerItemsAndBlocks() {
		final MachineGroup machineGroup = new MachineGroup(Botany.instance, "machine", "machine", BotanyMachine.values());
		machineGroup.setCreativeTab(Tabs.tabArboriculture);
		blockMachine = machineGroup.getBlock();
	}

	@Override
	public void init() {
		RecipeUtil recipeUtil = new RecipeUtil(Constants.BOTANY_MOD_ID);
		if (ModuleManager.isModuleEnabled(Constants.EXTRA_TREES_MOD_ID, ExtraTreesModuleUIDs.CARPENTRY)) {
			recipeUtil.addRecipe("tileworker", BotanyMachine.Tileworker.get(1), "wGw", "GsG", "ggg", 'G', Blocks.GLASS, 'g', ExtraTreeItems.PROVEN_GEAR.get(1), 'w', Items.CLAY_BALL, 's', Mods.Forestry.stack("impregnated_casing"));
		}
	}
}
