package binnie.genetics.machine.sequencer;

import net.minecraft.item.ItemStack;

import binnie.core.machines.inventory.SlotValidator;
import binnie.core.util.I18N;
import binnie.genetics.Genetics;
import binnie.genetics.genetics.SequencerItem;
import binnie.genetics.machine.ModuleMachine;

public class SlotValidatorUnsequenced extends SlotValidator {
	public SlotValidatorUnsequenced() {
		super(ModuleMachine.getSpriteSequencer());
	}

	@Override
	public boolean isValid(final ItemStack itemStack) {
		if (itemStack.getItem() == Genetics.items().itemSequencer) {
			final SequencerItem seq = SequencerItem.create(itemStack);
			return seq != null && seq.getSequenced() < 100;
		}
		return false;
	}

	@Override
	public String getTooltip() {
		return I18N.localise("genetics.machine.sequencer.tooltips.slots.unsequenced.dna");
	}
}
