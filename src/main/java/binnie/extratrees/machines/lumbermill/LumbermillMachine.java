package binnie.extratrees.machines.lumbermill;

import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.core.machines.inventory.TankValidator;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.core.ExtraTreesGUID;
import binnie.extratrees.machines.ExtraTreeMachine;
import net.minecraft.tileentity.TileEntity;

public class LumbermillMachine extends ExtraTreeMachine.PackageExtraTreeMachine implements IMachineInformation {
	public static int SLOT_LOG = 0;
	public static int SLOT_PLANKS = 1;
	public static int SLOT_BARK = 2;
	public static int SLOT_SAWDUST = 3;
	public static int TANK_WATER = 0;
	public static int TANK_WATER_CAPACITY = 10000;

	public LumbermillMachine() {
		super("lumbermill", ExtraTreeTexture.lumbermillTexture, true);
	}

	@Override
	public void createMachine(final Machine machine) {
		new ExtraTreeMachine.ComponentExtraTreeGUI(machine, ExtraTreesGUID.Lumbermill);
		final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
		inventory.addSlot(SLOT_LOG, "input");
		inventory.getSlot(SLOT_LOG).setValidator(new SlotValidatorLog());
		inventory.getSlot(SLOT_LOG).forbidExtraction();
		inventory.addSlot(SLOT_PLANKS, "output");
		inventory.addSlot(SLOT_BARK, "byproduct");
		inventory.addSlot(SLOT_SAWDUST, "byproduct");
		inventory.getSlot(SLOT_PLANKS).setReadOnly();
		inventory.getSlot(SLOT_BARK).setReadOnly();
		inventory.getSlot(SLOT_SAWDUST).setReadOnly();
		final ComponentTankContainer tanks = new ComponentTankContainer(machine);
		tanks.addTank(TANK_WATER, "input", TANK_WATER_CAPACITY);
		tanks.getTankSlot(TANK_WATER).setValidator(new TankValidator.Basic("water"));
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
