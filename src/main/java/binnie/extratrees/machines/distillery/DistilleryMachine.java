package binnie.extratrees.machines.distillery;

import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.core.machines.inventory.MachineSide;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.core.ExtraTreesGUID;
import binnie.extratrees.machines.ExtraTreeMachine;
import net.minecraft.tileentity.TileEntity;

public class DistilleryMachine extends ExtraTreeMachine.PackageExtraTreeMachine implements IMachineInformation {
	public static final int TANK_INPUT = 0;
	public static final int TANK_OUTPUT = 1;

	public DistilleryMachine() {
		super("distillery", ExtraTreeTexture.distilleryTexture, true);
	}

	@Override
	public void createMachine(final Machine machine) {
		new ExtraTreeMachine.ComponentExtraTreeGUI(machine, ExtraTreesGUID.Distillery);
		final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
		final ComponentTankContainer tanks = new ComponentTankContainer(machine);
		tanks.addTank(TANK_INPUT, "input", 5000);
		tanks.getTankSlot(TANK_INPUT).setValidator(new TankValidatorDistilleryInput());
		tanks.getTankSlot(TANK_INPUT).setOutputSides(MachineSide.TopAndBottom);
		tanks.addTank(TANK_OUTPUT, "output", 5000);
		tanks.getTankSlot(TANK_OUTPUT).setValidator(new TankValidatorDistilleryOutput());
		tanks.getTankSlot(TANK_OUTPUT).setReadOnly();
		tanks.getTankSlot(TANK_OUTPUT).setOutputSides(MachineSide.Sides);
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
