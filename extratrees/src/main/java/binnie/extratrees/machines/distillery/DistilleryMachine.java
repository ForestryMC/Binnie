package binnie.extratrees.machines.distillery;

import net.minecraft.tileentity.TileEntity;

import binnie.core.gui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.core.machines.inventory.TankSlot;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.extratrees.gui.ExtraTreesGUID;
import binnie.extratrees.machines.ExtraTreeMachine;
import binnie.extratrees.machines.distillery.window.TankValidatorDistilleryInput;
import binnie.extratrees.machines.distillery.window.TankValidatorDistilleryOutput;

public class DistilleryMachine extends ExtraTreeMachine.PackageExtraTreeMachine implements IMachineInformation {
	public static final int TANK_INPUT = 0;
	public static final int TANK_OUTPUT = 1;

	public DistilleryMachine() {
		super("distillery");
	}

	@Override
	public void createMachine(final Machine machine) {
		new ExtraTreeMachine.ComponentExtraTreeGUI(machine, ExtraTreesGUID.DISTILLERY);
		final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
		final ComponentTankContainer tanks = new ComponentTankContainer(machine);

		final TankSlot input = tanks.addTank(TANK_INPUT, "input", 5000);
		input.setValidator(new TankValidatorDistilleryInput());
		input.forbidExtraction();

		final TankSlot output = tanks.addTank(TANK_OUTPUT, "output", 5000);
		output.setValidator(new TankValidatorDistilleryOutput());
		output.setReadOnly();

		new ComponentPowerReceptor(machine);
		new DistilleryLogic(machine);
	}

	@Override
	public TileEntity createTileEntity() {
		return new TileEntityMachine(this);
	}

}
