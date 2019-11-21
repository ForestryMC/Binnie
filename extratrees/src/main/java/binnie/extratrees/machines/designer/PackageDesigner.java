package binnie.extratrees.machines.designer;

import binnie.core.gui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.design.api.IDesignerType;
import binnie.design.gui.ComponentDesignerRecipe;
import binnie.design.gui.DesignerSlots;
import binnie.design.gui.SlotValidatorDesignAdhesive;
import binnie.design.gui.SlotValidatorDesignMaterial;
import binnie.extratrees.gui.ExtraTreesGUID;
import binnie.extratrees.machines.ExtraTreeMachine;
import net.minecraft.tileentity.TileEntity;

public final class PackageDesigner extends MachinePackage implements IMachineInformation {
	private final IDesignerType type;

	public PackageDesigner(final IDesignerType type) {
		super(type.getName());
		this.type = type;
	}

	@Override
	public TileEntity createTileEntity() {
		return new TileEntityMachine(this);
	}

	@Override
	public void createMachine(final Machine machine) {
		new ExtraTreeMachine.ComponentExtraTreeGUI(machine, ExtraTreesGUID.WOODWORKER);
		final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
		inventory.addSlot(DesignerSlots.ADHESIVE_SLOT, getSlotRL("polish")).setValidator(new SlotValidatorDesignAdhesive(this.type));
		inventory.addSlot(DesignerSlots.DESIGN_SLOT_1, getSlotRL("wood")).setValidator(new SlotValidatorDesignMaterial(this.type));
		inventory.addSlot(DesignerSlots.DESIGN_SLOT_2, getSlotRL("wood")).setValidator(new SlotValidatorDesignMaterial(this.type));
		new ComponentDesignerRecipe(machine, this.type);
	}

}
