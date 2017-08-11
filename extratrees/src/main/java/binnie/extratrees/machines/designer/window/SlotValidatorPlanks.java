package binnie.extratrees.machines.designer.window;

import binnie.extratrees.machines.designer.IDesignerType;
import net.minecraft.item.ItemStack;

import binnie.core.machines.inventory.SlotValidator;
import binnie.extratrees.api.IDesignMaterial;

public class SlotValidatorPlanks extends SlotValidator {
	private final IDesignerType type;

	public SlotValidatorPlanks(final IDesignerType type) {
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
