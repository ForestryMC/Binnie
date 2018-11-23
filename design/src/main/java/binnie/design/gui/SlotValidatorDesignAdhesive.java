package binnie.design.gui;

import net.minecraft.item.ItemStack;

import binnie.core.machines.inventory.SlotValidator;
import binnie.design.Design;
import binnie.design.api.IDesignerType;

public class SlotValidatorDesignAdhesive extends SlotValidator {
	private final IDesignerType type;

	public SlotValidatorDesignAdhesive(IDesignerType type) {
		super(Design.getSpritePolish());
		this.type = type;
	}

	@Override
	public boolean isValid(ItemStack itemStack) {
		return this.type.getSystem().getAdhesive().isItemEqual(itemStack);
	}

	@Override
	public String getTooltip() {
		return this.type.getSystem().getAdhesive().getDisplayName();
	}
}
