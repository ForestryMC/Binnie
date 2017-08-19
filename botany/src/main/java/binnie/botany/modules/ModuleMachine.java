package binnie.botany.modules;

import binnie.botany.Botany;
import binnie.botany.machines.BotanyMachine;
import binnie.core.Constants;
import binnie.core.Mods;
import binnie.core.machines.MachineGroup;
import binnie.core.modules.BinnieModule;
import binnie.core.modules.BotanyModuleUIDs;
import binnie.core.modules.Module;
import binnie.core.util.RecipeUtil;
import forestry.api.core.Tabs;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

@BinnieModule(moduleID = BotanyModuleUIDs.MACHINES, moduleContainerID = Constants.BOTANY_MOD_ID, name = "Machines", unlocalizedDescription = "botany.module.machines")
public class ModuleMachine implements Module {
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
		ItemStack tileworkerBase;
		Item provenGear = ForgeRegistries.ITEMS.getValue(new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, "proven_gear"));
		if (provenGear != null) {
			tileworkerBase = new ItemStack(provenGear);
		} else {
			tileworkerBase = Mods.Forestry.stack("oak_stick");
		}
		recipeUtil.addRecipe("tileworker", BotanyMachine.Tileworker.get(1),
				"wGw", "GsG", "ggg",
				'G', Blocks.GLASS,
				'g', tileworkerBase,
				'w', Items.CLAY_BALL,
				's', Mods.Forestry.stack("impregnated_casing")
		);
	}
}
