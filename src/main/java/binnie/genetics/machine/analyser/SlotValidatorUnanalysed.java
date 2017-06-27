package binnie.genetics.machine.analyser;

import net.minecraft.item.ItemStack;

import binnie.core.machines.inventory.SlotValidator;
import binnie.core.util.I18N;

public class SlotValidatorUnanalysed extends SlotValidator {
	public SlotValidatorUnanalysed() {
		super(null);
	}

	@Override
	public boolean isValid(final ItemStack itemStack) {
		return Analyser.isAnalysable(itemStack) && !Analyser.isAnalysed(itemStack);
	}

	@Override
	public String getTooltip() {
		return I18N.localise("genetics.machine.labMachine.analyser.tooltips.slots.unanalysed");
	}
}
