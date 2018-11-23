package binnie.extratrees.machines.infuser;

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

public class InfuserMachine extends ExtraTreeMachine.PackageExtraTreeMachine implements IMachineInformation {
	public InfuserMachine() {
		super("infuser");
	}

	@Override
	public void createMachine(Machine machine) {
		new ExtraTreeMachine.ComponentExtraTreeGUI(machine, ExtraTreesGUID.INFUSER);
		ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
		ComponentTankContainer tanks = new ComponentTankContainer(machine);

		TankSlot input = tanks.addTank(Infuser.TANK_INPUT, "input", 5000);
		input.setValidator(new TankValidatorInfuserInput());
		input.forbidExtraction();

		TankSlot output = tanks.addTank(Infuser.TANK_OUTPUT, "output", 5000);
		output.setValidator(new TankValidatorInfuserOutput());
		output.setReadOnly();

		new ComponentPowerReceptor(machine);
		new InfuserLogic(machine);
	}

	@Override
	public TileEntity createTileEntity() {
		return new TileEntityMachine(this);
	}

}
