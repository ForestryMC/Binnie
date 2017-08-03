package binnie.extratrees.machines.designer.window;

import net.minecraft.item.ItemStack;

import binnie.core.machines.inventory.SlotValidator;
import binnie.extratrees.api.IDesignMaterial;
import binnie.extratrees.machines.designer.DesignerType;

public class SlotValidatorPlanks extends SlotValidator {
	DesignerType type;

	public SlotValidatorPlanks(final DesignerType type) {
		super(SlotValidator.spriteBlock);
		this.type = type;
	}

	@Override
	public boolean isValid(final ItemStack itemStack) {
		final IDesignMaterial mat = this.type.getSystem().getMaterial(itemStack);
		return !itemStack.isEmpty() && mat != null;
	}

	@Override
	public String getTooltip() {
		return this.type.getMaterialTooltip();
	}
}
