package binnie.extratrees.machines.designer.window;

import binnie.extratrees.machines.designer.IDesignerType;
import net.minecraft.item.ItemStack;

import binnie.core.machines.inventory.SlotValidator;
import binnie.extratrees.modules.ModuleMachine;

public class SlotValidatorBeeswax extends SlotValidator {
	private final IDesignerType type;

	public SlotValidatorBeeswax(final IDesignerType type) {
		super(ModuleMachine.spritePolish);
		this.type = type;
	}

	@Override
	public boolean isValid(final ItemStack itemStack) {
		return this.type.getSystem().getAdhesive().isItemEqual(itemStack);
	}

	@Override
	public String getTooltip() {
		return this.type.getSystem().getAdhesive().getDisplayName();
	}
}
