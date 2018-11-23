package binnie.design.gui;

import net.minecraft.item.ItemStack;

import binnie.core.machines.ManagerMachine;
import binnie.core.machines.inventory.SlotValidator;
import binnie.design.api.IDesignMaterial;
import binnie.design.api.IDesignerType;

public class SlotValidatorDesignMaterial extends SlotValidator {
	private final IDesignerType type;

	public SlotValidatorDesignMaterial(IDesignerType type) {
		super(ManagerMachine.getSpriteBlock());
		this.type = type;
	}

	@Override
	public boolean isValid(ItemStack itemStack) {
		IDesignMaterial mat = this.type.getSystem().getMaterial(itemStack);
		return !itemStack.isEmpty() && mat != null;
	}

	@Override
	public String getTooltip() {
		return this.type.getMaterialTooltip();
	}
}
