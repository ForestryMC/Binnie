package binnie.genetics.machine.analyser;

import net.minecraft.item.ItemStack;

import binnie.core.genetics.ManagerGenetics;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.util.I18N;

public class SlotValidatorUnanalysed extends SlotValidator {
	public SlotValidatorUnanalysed() {
		super(null);
	}

	@Override
	public boolean isValid(final ItemStack itemStack) {
		return ManagerGenetics.isAnalysable(itemStack) && !ManagerGenetics.isAnalysed(itemStack);
	}

	@Override
	public String getTooltip() {
		return I18N.localise("genetics.machine.lab_machine.analyser.tooltips.slots.unanalysed");
	}
}
