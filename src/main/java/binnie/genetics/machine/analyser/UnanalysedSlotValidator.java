package binnie.genetics.machine.analyser;

import binnie.core.machines.inventory.SlotValidator;
import binnie.core.util.I18N;
import binnie.genetics.Genetics;
import net.minecraft.item.ItemStack;

public class UnanalysedSlotValidator extends SlotValidator {
	public UnanalysedSlotValidator() {
		super(null);
	}

	@Override
	public boolean isValid(ItemStack stack) {
		return Analyser.isAnalysable(stack)
			&& !Analyser.isAnalysed(stack);
	}

	@Override
	public String getTooltip() {
		return I18N.localise(Genetics.instance, "machine.analyser.unanalysedItem");
	}
}
