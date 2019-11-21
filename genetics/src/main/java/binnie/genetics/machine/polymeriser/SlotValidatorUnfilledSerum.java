package binnie.genetics.machine.polymeriser;

import binnie.core.machines.inventory.SlotValidator;
import binnie.core.util.I18N;
import binnie.genetics.api.IItemChargeable;
import binnie.genetics.modules.ModuleMachine;
import net.minecraft.item.ItemStack;

public class SlotValidatorUnfilledSerum extends SlotValidator {
	public SlotValidatorUnfilledSerum() {
		super(ModuleMachine.getSpriteSerum());
	}

	@Override
	public boolean isValid(final ItemStack itemStack) {
		return itemStack.getItem() instanceof IItemChargeable;
	}

	@Override
	public String getTooltip() {
		return I18N.localise("genetics.machine.polymeriser.tooltips.slots.unfilled");
	}
}
