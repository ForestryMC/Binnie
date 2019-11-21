package binnie.extratrees.machines.infuser;

import binnie.core.gui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.core.machines.inventory.TankSlot;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.extratrees.gui.ExtraTreesGUID;
import binnie.extratrees.machines.ExtraTreeMachine;
import net.minecraft.tileentity.TileEntity;

public class InfuserMachine extends ExtraTreeMachine.PackageExtraTreeMachine implements IMachineInformation {
	public InfuserMachine() {
		super("infuser");
	}

	@Override
	public void createMachine(final Machine machine) {
		new ExtraTreeMachine.ComponentExtraTreeGUI(machine, ExtraTreesGUID.INFUSER);
		final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
		final ComponentTankContainer tanks = new ComponentTankContainer(machine);

		final TankSlot input = tanks.addTank(Infuser.TANK_INPUT, "input", 5000);
		input.setValidator(new TankValidatorInfuserInput());
		input.forbidExtraction();

		final TankSlot output = tanks.addTank(Infuser.TANK_OUTPUT, "output", 5000);
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
