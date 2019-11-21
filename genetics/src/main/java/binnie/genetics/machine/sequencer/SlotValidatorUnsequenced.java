package binnie.genetics.machine.sequencer;

import binnie.core.machines.inventory.SlotValidator;
import binnie.core.util.I18N;
import binnie.genetics.genetics.SequencerItem;
import binnie.genetics.modules.ModuleCore;
import binnie.genetics.modules.ModuleMachine;
import net.minecraft.item.ItemStack;

public class SlotValidatorUnsequenced extends SlotValidator {
	public SlotValidatorUnsequenced() {
		super(ModuleMachine.getSpriteSequencer());
	}

	@Override
	public boolean isValid(final ItemStack itemStack) {
		if (itemStack.getItem() == ModuleCore.itemSequencer) {
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
