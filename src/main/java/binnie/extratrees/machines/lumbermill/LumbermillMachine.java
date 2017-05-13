package binnie.extratrees.machines.lumbermill;

import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.*;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.core.ExtraTreesGUID;
import binnie.extratrees.machines.ExtraTreeMachine;
import net.minecraft.tileentity.TileEntity;

public class LumbermillMachine extends ExtraTreeMachine.PackageExtraTreeMachine implements IMachineInformation {
	public static final int SLOT_LOG = 0;
	public static final int SLOT_PLANKS = 1;
	public static final int SLOT_BARK = 2;
	public static final int SLOT_SAWDUST = 3;
	public static final int TANK_WATER = 0;
	public static final int TANK_WATER_CAPACITY = 10000;

	public LumbermillMachine() {
		super("lumbermill", ExtraTreeTexture.Lumbermill, true);
	}

	@Override
	public void createMachine(final Machine machine) {
		new ExtraTreeMachine.ComponentExtraTreeGUI(machine, ExtraTreesGUID.Lumbermill);
		final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);

		InventorySlot slotLog = inventory.addSlot(SLOT_LOG, "input");
		slotLog.setValidator(new SlotValidatorLog());
		slotLog.forbidExtraction();

		inventory.addSlot(SLOT_PLANKS, "output").setReadOnly();
		inventory.addSlot(SLOT_BARK, "byproduct").setReadOnly();
		inventory.addSlot(SLOT_SAWDUST, "byproduct").setReadOnly();

		final ComponentTankContainer tanks = new ComponentTankContainer(machine);
		TankSlot tankWater = tanks.addTank(TANK_WATER, "input", TANK_WATER_CAPACITY);
		tankWater.setValidator(new TankValidator.Basic("water"));

		new ComponentPowerReceptor(machine);
		new LumbermillLogic(machine);
	}

	@Override
	public TileEntity createTileEntity() {
		return new TileEntityMachine(this);
	}

	@Override
	public void register() {
	}
}
