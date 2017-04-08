package binnie.extratrees.machines.distillery;

import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.core.machines.inventory.MachineSide;
import binnie.core.machines.inventory.TankSlot;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.core.ExtraTreesGUID;
import binnie.extratrees.machines.ExtraTreeMachine;
import net.minecraft.tileentity.TileEntity;

public class DistilleryMachine extends ExtraTreeMachine.PackageExtraTreeMachine implements IMachineInformation {
	public static final int TANK_INPUT = 0;
	public static final int TANK_OUTPUT = 1;

	public DistilleryMachine() {
		super("distillery", ExtraTreeTexture.Distillery, true);
	}

	@Override
	public void createMachine(final Machine machine) {
		new ExtraTreeMachine.ComponentExtraTreeGUI(machine, ExtraTreesGUID.Distillery);
		final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
		final ComponentTankContainer tanks = new ComponentTankContainer(machine);

		final TankSlot input = tanks.addTank(TANK_INPUT, "input", 5000);
		input.setValidator(new TankValidatorDistilleryInput());
		input.setOutputSides(MachineSide.TopAndBottom);

		final TankSlot output = tanks.addTank(TANK_OUTPUT, "output", 5000);
		output.setValidator(new TankValidatorDistilleryOutput());
		output.setReadOnly();
		output.setOutputSides(MachineSide.Sides);

		new ComponentPowerReceptor(machine);
		new DistilleryLogic(machine);
	}

	@Override
	public TileEntity createTileEntity() {
		return new TileEntityMachine(this);
	}

	@Override
	public void register() {
	}
}
