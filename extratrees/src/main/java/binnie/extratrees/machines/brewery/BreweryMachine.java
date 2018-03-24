package binnie.extratrees.machines.brewery;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import binnie.core.Constants;
import binnie.core.gui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.TankSlot;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.extratrees.gui.ExtraTreesGUID;
import binnie.extratrees.machines.ExtraTreeMachine;
import binnie.extratrees.machines.brewery.window.SlotValidatorBreweryGrain;
import binnie.extratrees.machines.brewery.window.SlotValidatorBreweryIngredient;
import binnie.extratrees.machines.brewery.window.SlotValidatorBreweryYeast;
import binnie.extratrees.machines.brewery.window.TankValidatorFermentInput;
import binnie.extratrees.machines.brewery.window.TankValidatorFermentOutput;

public class BreweryMachine extends ExtraTreeMachine.PackageExtraTreeMachine implements IMachineInformation {
	public static final int TANK_INPUT = 0;
	public static final int TANK_OUTPUT = 1;

	public static final int[] SLOT_RECIPE_GRAINS = new int[]{0, 1, 2};
	public static final int SLOT_RECIPE_INPUT = 3;
	public static final int SLOT_YEAST = 4;
	public static final int[] SLOTS_INVENTORY = new int[]{5, 6, 7, 8, 9, 10, 11, 12, 13};

	public BreweryMachine() {
		super("brewery");
	}

	@Override
	public void createMachine(final Machine machine) {
		new ExtraTreeMachine.ComponentExtraTreeGUI(machine, ExtraTreesGUID.BREWERY);
		final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);

		for (final InventorySlot slot : inventory.addSlotArray(SLOT_RECIPE_GRAINS, new ResourceLocation(Constants.CORE_MOD_ID, "gui.slot.grain"))) {
			slot.setValidator(new SlotValidatorBreweryGrain());
			slot.setType(InventorySlot.Type.Recipe);
		}

		for (final InventorySlot slot : inventory.addSlotArray(SLOTS_INVENTORY, new ResourceLocation(Constants.CORE_MOD_ID, "gui.slot.inventory"))) {
			slot.forbidExtraction();
		}

		final InventorySlot yeast = inventory.addSlot(SLOT_YEAST, "yeast");
		yeast.setValidator(new SlotValidatorBreweryYeast());
		yeast.setType(InventorySlot.Type.Recipe);

		final InventorySlot ingredient = inventory.addSlot(SLOT_RECIPE_INPUT, "ingredient");
		ingredient.setValidator(new SlotValidatorBreweryIngredient());
		ingredient.setType(InventorySlot.Type.Recipe);

		final ComponentTankContainer tanks = new ComponentTankContainer(machine);
		TankSlot input = tanks.addTank(TANK_INPUT, "input", 5000);
		input.setValidator(new TankValidatorFermentInput());
		input.forbidExtraction();

		final TankSlot output = tanks.addTank(TANK_OUTPUT, "output", 5000);
		output.setValidator(new TankValidatorFermentOutput());
		output.setReadOnly();

		new ComponentPowerReceptor(machine);
		new BreweryLogic(machine);
	}

	@Override
	public TileEntity createTileEntity() {
		return new TileEntityMachine(this);
	}

}
