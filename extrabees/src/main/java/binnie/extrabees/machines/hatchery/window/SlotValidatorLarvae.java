package binnie.extrabees.machines.hatchery.window;

import binnie.core.machines.ManagerMachine;
import binnie.core.machines.inventory.SlotValidator;
import binnie.extrabees.utils.Utils;
import forestry.api.apiculture.EnumBeeType;
import net.minecraft.item.ItemStack;

public class SlotValidatorLarvae extends SlotValidator {
	public SlotValidatorLarvae() {
		super(ManagerMachine.getSpriteBee());
	}

	@Override
	public boolean isValid(final ItemStack itemStack) {
		return Utils.getBeeRoot().isMember(itemStack) && Utils.getBeeRoot().getType(itemStack) == EnumBeeType.LARVAE;
	}

	@Override
	public String getTooltip() {
		return "Larvae";
	}
}
