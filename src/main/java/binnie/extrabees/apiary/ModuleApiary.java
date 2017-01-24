package binnie.extrabees.apiary;

import binnie.Constants;
import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import binnie.core.Mods;
import binnie.core.circuits.BinnieCircuitLayout;
import binnie.core.circuits.BinnieCircuitSocketType;
import binnie.core.machines.MachineGroup;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.apiary.machine.AlvearyMachine;
import binnie.extrabees.apiary.machine.AlvearyMutator;
import binnie.extrabees.apiary.machine.AlvearyStimulator;
import forestry.api.core.Tabs;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ModuleApiary implements IInitializable {
	public static Block blockComponent;
	BinnieCircuitLayout stimulatorLayout;


	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	@Mod.EventHandler
	public void texture(TextureStitchEvent e) {
		Minecraft.getMinecraft().getTextureMapBlocks().registerSprite(new ResourceLocation(Constants.EXTRA_BEES_MOD_ID, "items/validator/mutator.0"));
		Minecraft.getMinecraft().getTextureMapBlocks().registerSprite(new ResourceLocation(Constants.EXTRA_BEES_MOD_ID, "items/validator/mutator.1"));
	}

	@Override
	public void preInit() {
		final MachineGroup machineGroup = new MachineGroup(ExtraBees.instance, "alveay", "alveary", AlvearyMachine.values());
		machineGroup.setCreativeTab(Tabs.tabApiculture);
		BinnieCore.proxy.registerTileEntity(TileExtraBeeAlveary.class, "extrabees.tile.alveary", null);
		ModuleApiary.blockComponent = machineGroup.getBlock();
		AlvearyMutator.addMutationItem(new ItemStack(Blocks.SOUL_SAND), 1.5f);
		AlvearyMutator.addMutationItem(Mods.IC2.stack("UranFuel"), 4.0f);
		AlvearyMutator.addMutationItem(Mods.IC2.stack("MOXFuel"), 10.0f);
		AlvearyMutator.addMutationItem(Mods.IC2.stack("Plutonium"), 8.0f);
		AlvearyMutator.addMutationItem(Mods.IC2.stack("smallPlutonium"), 5.0f);
		AlvearyMutator.addMutationItem(Mods.IC2.stack("Uran235"), 4.0f);
		AlvearyMutator.addMutationItem(Mods.IC2.stack("smallUran235"), 2.5f);
		AlvearyMutator.addMutationItem(Mods.IC2.stack("Uran238"), 2.0f);
		AlvearyMutator.addMutationItem(new ItemStack(Items.ENDER_PEARL), 2.0f);
		AlvearyMutator.addMutationItem(new ItemStack(Items.ENDER_EYE), 4.0f);
		for (final EnumHiveFrame frame : EnumHiveFrame.values()) {
			frame.item = new ItemHiveFrame(frame).setRegistryName("hiveFrame." + frame.name().toLowerCase());
			ExtraBees.proxy.registerItem(frame.item);
		}
	}

	@Override
	public void postInit() {
		EnumHiveFrame.init();
		Block alveary = Mods.Forestry.block("alveary.plain");
		Item thermionicTubes = Mods.Forestry.item("thermionicTubes");
		Item chipsets = Mods.Forestry.item("chipsets");

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
		this.stimulatorLayout = new BinnieCircuitLayout(ExtraBees.instance, "Stimulator", BinnieCircuitSocketType.STIMULATOR);
	}
}
