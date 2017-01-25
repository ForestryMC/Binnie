package binnie.extratrees.machines.brewery;

import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.MachineSide;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.core.ExtraTreesGUID;
import binnie.extratrees.machines.ExtraTreeMachine;
import net.minecraft.tileentity.TileEntity;

public class BreweryMachine extends ExtraTreeMachine.PackageExtraTreeMachine implements IMachineInformation {
	public static final int TANK_INPUT = 0;
	public static final int TANK_OUTPUT = 1;

	public static final int[] SLOT_RECIPE_GRAINS = new int[]{0, 1, 2};
	public static final int SLOT_RECIPE_INPUT = 3;
	public static final int SLOT_YEAST = 4;
	public static final int[] SLOTS_INVENTORY = new int[]{5, 6, 7, 8, 9, 10, 11, 12, 13};

	public BreweryMachine() {
		super("brewery", ExtraTreeTexture.breweryTexture, true);
	}

	@Override
	public void createMachine(final Machine machine) {
		new ExtraTreeMachine.ComponentExtraTreeGUI(machine, ExtraTreesGUID.Brewery);
		final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);

		inventory.addSlotArray(SLOT_RECIPE_GRAINS, "grain");
		for (final InventorySlot slot : inventory.getSlots(SLOT_RECIPE_GRAINS)) {
			slot.setValidator(new SlotValidatorBreweryGrain());
			slot.setType(InventorySlot.Type.Recipe);
		}

		inventory.addSlotArray(SLOTS_INVENTORY, "inventory");
		for (final InventorySlot slot : inventory.getSlots(SLOTS_INVENTORY)) {
			slot.forbidExtraction();
		}
		inventory.addSlot(SLOT_YEAST, "yeast");
		inventory.getSlot(SLOT_YEAST).setValidator(new SlotValidatorBreweryYeast());
		inventory.getSlot(SLOT_YEAST).setType(InventorySlot.Type.Recipe);

		inventory.addSlot(SLOT_RECIPE_INPUT, "ingredient");
		inventory.getSlot(SLOT_RECIPE_INPUT).setValidator(new SlotValidatorBreweryIngredient());
		inventory.getSlot(SLOT_RECIPE_INPUT).setType(InventorySlot.Type.Recipe);

		final ComponentTankContainer tanks = new ComponentTankContainer(machine);
		tanks.addTank(TANK_INPUT, "input", 5000);
		tanks.getTankSlot(TANK_INPUT).setValidator(new TankValidatorFermentInput());
		tanks.getTankSlot(TANK_INPUT).setOutputSides(MachineSide.TopAndBottom);
		tanks.addTank(TANK_OUTPUT, "output", 5000);
		tanks.getTankSlot(TANK_OUTPUT).setValidator(new TankValidatorFermentOutput());
		tanks.getTankSlot(TANK_OUTPUT).forbidInsertion();
		tanks.getTankSlot(TANK_OUTPUT).setOutputSides(MachineSide.Sides);

		new ComponentPowerReceptor(machine);
		new BreweryLogic(machine);
	}

	@Override
	public TileEntity createTileEntity() {
		return new TileEntityMachine(this);
	}

	@Override
	public void register() {
	}
}
