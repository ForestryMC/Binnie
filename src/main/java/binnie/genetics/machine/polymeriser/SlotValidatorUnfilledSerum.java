package binnie.genetics.machine.polymeriser;

import net.minecraft.item.ItemStack;

import binnie.core.machines.inventory.SlotValidator;
import binnie.genetics.Genetics;
import binnie.genetics.api.IItemChargeable;
import binnie.genetics.machine.ModuleMachine;

public class SlotValidatorUnfilledSerum extends SlotValidator {
	public SlotValidatorUnfilledSerum() {
		super(ModuleMachine.spriteSerum);
	}

	@Override
	public boolean isValid(final ItemStack itemStack) {
		return itemStack.getItem() instanceof IItemChargeable;
	}

	@Override
	public String getTooltip() {
		return Genetics.proxy.localise("machine.machine.polymeriser.tooltips.slots.unfilled");
	}
}
