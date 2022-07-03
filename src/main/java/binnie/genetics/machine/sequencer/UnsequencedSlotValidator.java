package binnie.genetics.machine.sequencer;

import binnie.core.machines.inventory.SlotValidator;
import binnie.core.util.I18N;
import binnie.genetics.Genetics;
import binnie.genetics.genetics.SequencerItem;
import binnie.genetics.machine.ModuleMachine;
import net.minecraft.item.ItemStack;

public class UnsequencedSlotValidator extends SlotValidator {
    public UnsequencedSlotValidator() {
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
        return I18N.localise("genetics.machine.sequencer.unsequencedDNA");
    }
}
