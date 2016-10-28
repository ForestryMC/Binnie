package binnie.genetics.machine.polymeriser;

import binnie.core.machines.inventory.SlotValidator;
import binnie.genetics.Genetics;
import binnie.genetics.api.IItemChargable;
import binnie.genetics.machine.ModuleMachine;
import net.minecraft.item.ItemStack;

public class SlotValidatorUnfilledSerum extends SlotValidator {
	public SlotValidatorUnfilledSerum() {
		super(ModuleMachine.iconSerum);
	}

	@Override
	public boolean isValid(final ItemStack itemStack) {
		return itemStack.getItem() instanceof IItemChargable;
	}

	@Override
	public String getTooltip() {
		return Genetics.proxy.localise("machine.machine.polymeriser.tooltips.slots.unfilled");
	}
}
