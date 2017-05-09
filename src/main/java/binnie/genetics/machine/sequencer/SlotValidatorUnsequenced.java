package binnie.genetics.machine.sequencer;

import binnie.core.machines.inventory.SlotValidator;
import binnie.genetics.Genetics;
import binnie.genetics.genetics.SequencerItem;
import binnie.genetics.machine.ModuleMachine;
import net.minecraft.item.ItemStack;

public class SlotValidatorUnsequenced extends SlotValidator {
	public SlotValidatorUnsequenced() {
		super(ModuleMachine.IconSequencer);
	}

	@Override
	public boolean isValid(ItemStack itemStack) {
		if (itemStack.getItem() == Genetics.itemSequencer) {
			SequencerItem seq = new SequencerItem(itemStack);
			return seq.sequenced < 100;
		}
		return false;
	}

	@Override
	public String getTooltip() {
		return "Unsequenced DNA";
	}
}
