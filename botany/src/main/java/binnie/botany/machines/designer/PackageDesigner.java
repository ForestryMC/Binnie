package binnie.botany.machines.designer;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import binnie.botany.gui.BotanyGUI;
import binnie.botany.machines.ComponentBotanyGUI;
import binnie.core.Constants;
import binnie.core.gui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.InventorySlot;
import binnie.design.api.IDesignerType;
import binnie.design.gui.ComponentDesignerRecipe;
import binnie.design.gui.DesignerSlots;
import binnie.design.gui.SlotValidatorDesignAdhesive;
import binnie.design.gui.SlotValidatorDesignMaterial;

public final class PackageDesigner extends MachinePackage implements IMachineInformation {
	private final IDesignerType type;

	public PackageDesigner(IDesignerType type) {
		super(type.getName());
		this.type = type;
	}

	@Override
	public TileEntity createTileEntity() {
		return new TileEntityMachine(this);
	}

	@Override
	public void createMachine(Machine machine) {
		new ComponentBotanyGUI(machine, BotanyGUI.TILEWORKER);
		ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
		InventorySlot mortarSlot = inventory.addSlot(DesignerSlots.ADHESIVE_SLOT, new ResourceLocation(Constants.BOTANY_MOD_ID, "gui.slot.mortar"));
		mortarSlot.setValidator(new SlotValidatorDesignAdhesive(this.type));
		InventorySlot ceramicSlot1 = inventory.addSlot(DesignerSlots.DESIGN_SLOT_1, new ResourceLocation(Constants.BOTANY_MOD_ID, "gui.slot.ceramic"));
		ceramicSlot1.setValidator(new SlotValidatorDesignMaterial(this.type));
		InventorySlot ceramicSlot2 = inventory.addSlot(DesignerSlots.DESIGN_SLOT_2, new ResourceLocation(Constants.BOTANY_MOD_ID, "gui.slot.ceramic"));
		ceramicSlot2.setValidator(new SlotValidatorDesignMaterial(this.type));
		new ComponentDesignerRecipe(machine, this.type);
	}

}
