package binnie.extrabees.apiary;

import binnie.core.IInitializable;
import binnie.core.machines.MachineGroup;
import binnie.core.machines.inventory.ValidatorSprite;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.apiary.machine.AlvearyMachine;
import binnie.extrabees.apiary.machine.AlvearyMutator;
import binnie.extrabees.apiary.machine.AlvearyStimulator;
import binnie.extrabees.client.GuiHack;
import binnie.extrabees.items.types.EnumHiveFrame;
import binnie.extrabees.utils.BinnieCircuitLayout;
import binnie.extrabees.utils.BinnieCircuitSocketType;
import binnie.extrabees.utils.Utils;
import forestry.api.core.Tabs;
import forestry.apiculture.PluginApiculture;
import forestry.apiculture.blocks.BlockAlvearyType;
import forestry.core.PluginCore;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ModuleApiary implements IInitializable {

	public static Block blockComponent;
	public static ValidatorSprite spriteMutator;
	BinnieCircuitLayout stimulatorLayout;

	@Override
	public void preInit() {
		final MachineGroup machineGroup = new MachineGroup(GuiHack.INSTANCE, "alveay", "alveary", AlvearyMachine.values());
		machineGroup.setCreativeTab(Tabs.tabApiculture);
		GameRegistry.registerTileEntity(TileExtraBeeAlveary.class, "extrabees.tile.alveary");
		ModuleApiary.blockComponent = machineGroup.getBlock();
		AlvearyMutator.addMutationItem(new ItemStack(Blocks.SOUL_SAND), 1.5f);
		AlvearyMutator.addMutationItem(Utils.getIC2Item("UranFuel"), 4.0f);
		AlvearyMutator.addMutationItem(Utils.getIC2Item("MOXFuel"), 10.0f);
		AlvearyMutator.addMutationItem(Utils.getIC2Item("Plutonium"), 8.0f);
		AlvearyMutator.addMutationItem(Utils.getIC2Item("smallPlutonium"), 5.0f);
		AlvearyMutator.addMutationItem(Utils.getIC2Item("Uran235"), 4.0f);
		AlvearyMutator.addMutationItem(Utils.getIC2Item("smallUran235"), 2.5f);
		AlvearyMutator.addMutationItem(Utils.getIC2Item("Uran238"), 2.0f);
		AlvearyMutator.addMutationItem(new ItemStack(Items.ENDER_PEARL), 2.0f);
		AlvearyMutator.addMutationItem(new ItemStack(Items.ENDER_EYE), 4.0f);
		for (final EnumHiveFrame frame : EnumHiveFrame.values()) {
			ExtraBees.proxy.registerItem(frame.getItem());
		}
	}

	@Override
	public void postInit() {
		EnumHiveFrame.init();
		ItemStack alveary = PluginApiculture.getBlocks().getAlvearyBlock(BlockAlvearyType.PLAIN);
		Item thermionicTubes = PluginCore.getItems().tubes;
		Item chipsets = PluginCore.getItems().circuitboards;

		GameRegistry.addRecipe(AlvearyMachine.Mutator.get(1), "g g", " a ", "t t", 'g', Items.GOLD_INGOT, 'a', alveary, 't', new ItemStack(thermionicTubes, 1, 5));
		GameRegistry.addRecipe(AlvearyMachine.Frame.get(1), "iii", "tat", " t ", 'i', Items.IRON_INGOT, 'a', alveary, 't', new ItemStack(thermionicTubes, 1, 4));
		GameRegistry.addRecipe(AlvearyMachine.RainShield.get(1), " b ", "bab", "t t", 'b', Items.BRICK, 'a', alveary, 't', new ItemStack(thermionicTubes, 1, 4));
		GameRegistry.addRecipe(AlvearyMachine.Lighting.get(1), "iii", "iai", " t ", 'i', Items.GLOWSTONE_DUST, 'a', alveary, 't', new ItemStack(thermionicTubes, 1, 4));
		GameRegistry.addRecipe(AlvearyMachine.Stimulator.get(1), "kik", "iai", " t ", 'i', Items.GOLD_NUGGET, 'a', alveary, 't', new ItemStack(thermionicTubes, 1, 4), 'k', new ItemStack(chipsets, 1, 2));
		GameRegistry.addRecipe(AlvearyMachine.Hatchery.get(1), "i i", " a ", "iti", 'i', Blocks.GLASS_PANE, 'a', alveary, 't', new ItemStack(thermionicTubes, 1, 5));
		GameRegistry.addRecipe(new ShapedOreRecipe(AlvearyMachine.Transmission.get(1), " t ", "tat", " t ", 'a', alveary, 't', "gearTin"));
		for (final AlvearyStimulator.CircuitType type : AlvearyStimulator.CircuitType.values()) {
			type.createCircuit(this.stimulatorLayout);
		}
	}

	@Override
	public void init() {
		this.stimulatorLayout = new BinnieCircuitLayout("Stimulator", BinnieCircuitSocketType.STIMULATOR);
		ModuleApiary.spriteMutator = new ValidatorSprite(GuiHack.INSTANCE, "validator/mutator.0", "validator/mutator.1");
	}
}
