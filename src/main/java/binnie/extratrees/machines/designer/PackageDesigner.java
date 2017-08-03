package binnie.extratrees.machines.designer;

import binnie.core.gui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.extratrees.core.ExtraTreesGUID;
import binnie.extratrees.machines.ExtraTreeMachine;
import binnie.extratrees.machines.designer.window.ComponentDesignerRecipe;
import binnie.extratrees.machines.designer.window.SlotValidatorBeeswax;
import binnie.extratrees.machines.designer.window.SlotValidatorPlanks;

public abstract class PackageDesigner extends ExtraTreeMachine.PackageExtraTreeMachine implements IMachineInformation {
	DesignerType type;

	public PackageDesigner(final DesignerType type) {
		super(type.name, type.texture, false);
		this.type = type;
	}

	@Override
	public void createMachine(final Machine machine) {
		new ExtraTreeMachine.ComponentExtraTreeGUI(machine, ExtraTreesGUID.WOODWORKER);
		final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
		inventory.addSlot(Designer.BEESWAX_SLOT, "polish").setValidator(new SlotValidatorBeeswax(this.type));
		inventory.addSlot(Designer.DESIGN_SLOT_1, "wood").setValidator(new SlotValidatorPlanks(this.type));
		inventory.addSlot(Designer.DESIGN_SLOT_2, "wood").setValidator(new SlotValidatorPlanks(this.type));
		new ComponentDesignerRecipe(machine, this.type);
	}
}
