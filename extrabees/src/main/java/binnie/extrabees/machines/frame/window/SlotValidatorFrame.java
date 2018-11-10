package binnie.extrabees.machines.frame.window;

import net.minecraft.item.ItemStack;

import forestry.api.apiculture.IHiveFrame;

import binnie.core.machines.ManagerMachine;
import binnie.core.machines.inventory.SlotValidator;

public class SlotValidatorFrame extends SlotValidator {
	public SlotValidatorFrame() {
		super(ManagerMachine.getSpriteFrame());
	}

	@Override
	public boolean isValid(ItemStack itemStack) {
		return !itemStack.isEmpty() && itemStack.getItem() instanceof IHiveFrame;
	}

	@Override
	public String getTooltip() {
		return "Hive Frames";
	}
}
