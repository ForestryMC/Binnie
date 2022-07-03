package binnie.extrabees.apiary.machine.mutator;

import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.inventory.ValidatorIcon;
import binnie.core.util.I18N;
import binnie.extrabees.ExtraBees;
import net.minecraft.item.ItemStack;

public class MutatorSlotValidator extends SlotValidator {
    public MutatorSlotValidator() {
        super(new ValidatorIcon(ExtraBees.instance, "validator/mutator.0", "validator/mutator.1"));
    }

    @Override
    public boolean isValid(ItemStack itemStack) {
        return AlvearyMutator.isMutationItem(itemStack);
    }

    @Override
    public String getTooltip() {
        return I18N.localise("extrabees.machine.alveay.mutator.tooltip");
    }
}
