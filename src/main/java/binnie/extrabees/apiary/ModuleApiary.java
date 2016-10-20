// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extrabees.apiary;

import forestry.api.circuits.ICircuitLayout;
import binnie.extrabees.apiary.machine.AlvearyStimulator;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import binnie.core.Mods;
import binnie.extrabees.apiary.machine.AlvearyMutator;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Blocks;
import binnie.core.BinnieCore;
import forestry.api.core.Tabs;
import binnie.core.machines.MachineGroup;
import binnie.extrabees.apiary.machine.AlvearyMachine;
import binnie.extrabees.ExtraBees;
import binnie.core.circuits.BinnieCircuitLayout;
import net.minecraft.block.Block;
import binnie.core.IInitializable;

public class ModuleApiary implements IInitializable
{
	public static Block blockComponent;
	BinnieCircuitLayout stimulatorLayout;

	@Override
	public void preInit() {
		final MachineGroup machineGroup = new MachineGroup(ExtraBees.instance, "alveay", "alveary", AlvearyMachine.values());
		machineGroup.setCreativeTab(Tabs.tabApiculture);
		BinnieCore.proxy.registerTileEntity(TileExtraBeeAlveary.class, "extrabees.tile.alveary", BinnieCore.proxy.createObject("binnie.core.machines.RendererMachine"));
		ModuleApiary.blockComponent = machineGroup.getBlock();
		AlvearyMutator.addMutationItem(new ItemStack(Blocks.soul_sand), 1.5f);
		AlvearyMutator.addMutationItem(Mods.IC2.stack("UranFuel"), 4.0f);
		AlvearyMutator.addMutationItem(Mods.IC2.stack("MOXFuel"), 10.0f);
		AlvearyMutator.addMutationItem(Mods.IC2.stack("Plutonium"), 8.0f);
		AlvearyMutator.addMutationItem(Mods.IC2.stack("smallPlutonium"), 5.0f);
		AlvearyMutator.addMutationItem(Mods.IC2.stack("Uran235"), 4.0f);
		AlvearyMutator.addMutationItem(Mods.IC2.stack("smallUran235"), 2.5f);
		AlvearyMutator.addMutationItem(Mods.IC2.stack("Uran238"), 2.0f);
		AlvearyMutator.addMutationItem(new ItemStack(Items.ender_pearl), 2.0f);
		AlvearyMutator.addMutationItem(new ItemStack(Items.ender_eye), 4.0f);
		for (final EnumHiveFrame frame : EnumHiveFrame.values()) {
			GameRegistry.registerItem(frame.item = new ItemHiveFrame(frame), "hiveFrame." + frame.name().toLowerCase());
		}
	}

	@Override
	public void postInit() {
		EnumHiveFrame.init();
		GameRegistry.addRecipe(AlvearyMachine.Mutator.get(1), new Object[] { "g g", " a ", "t t", 'g', Items.gold_ingot, 'a', Mods.Forestry.block("alveary"), 't', new ItemStack(Mods.Forestry.item("thermionicTubes"), 1, 5) });
		GameRegistry.addRecipe(AlvearyMachine.Frame.get(1), new Object[] { "iii", "tat", " t ", 'i', Items.iron_ingot, 'a', Mods.Forestry.block("alveary"), 't', new ItemStack(Mods.Forestry.item("thermionicTubes"), 1, 4) });
		GameRegistry.addRecipe(AlvearyMachine.RainShield.get(1), new Object[] { " b ", "bab", "t t", 'b', Items.brick, 'a', Mods.Forestry.block("alveary"), 't', new ItemStack(Mods.Forestry.item("thermionicTubes"), 1, 4) });
		GameRegistry.addRecipe(AlvearyMachine.Lighting.get(1), new Object[] { "iii", "iai", " t ", 'i', Items.glowstone_dust, 'a', Mods.Forestry.block("alveary"), 't', new ItemStack(Mods.Forestry.item("thermionicTubes"), 1, 4) });
		GameRegistry.addRecipe(AlvearyMachine.Stimulator.get(1), new Object[] { "kik", "iai", " t ", 'i', Items.gold_nugget, 'a', Mods.Forestry.block("alveary"), 't', new ItemStack(Mods.Forestry.item("thermionicTubes"), 1, 4), 'k', new ItemStack(Mods.Forestry.item("chipsets"), 1, 2) });
		GameRegistry.addRecipe(AlvearyMachine.Hatchery.get(1), new Object[] { "i i", " a ", "iti", 'i', Blocks.glass_pane, 'a', Mods.Forestry.block("alveary"), 't', new ItemStack(Mods.Forestry.item("thermionicTubes"), 1, 5) });
		GameRegistry.addRecipe(new ShapedOreRecipe(AlvearyMachine.Transmission.get(1), new Object[] { " t ", "tat", " t ", 'a', Mods.Forestry.block("alveary"), 't', "gearTin" }));
		for (final AlvearyStimulator.CircuitType type : AlvearyStimulator.CircuitType.values()) {
			type.createCircuit(this.stimulatorLayout);
		}
	}

	@Override
	public void init() {
		this.stimulatorLayout = new BinnieCircuitLayout(ExtraBees.instance, "Stimulator");
	}
}
