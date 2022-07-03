package binnie.genetics.machine.splicer;

import binnie.core.machines.inventory.SlotValidator;
import binnie.core.util.I18N;
import binnie.genetics.api.IItemSerum;
import binnie.genetics.machine.ModuleMachine;
import net.minecraft.item.ItemStack;

class SerumSlotValidator extends SlotValidator {
    public SerumSlotValidator() {
        super(ModuleMachine.IconSerum);
    }

    @Override
    public boolean isValid(ItemStack itemStack) {
        return itemStack.getItem() instanceof IItemSerum;
    }

    @Override
    public String getTooltip() {
        return I18N.localise("genetics.machine.splicer.serums");
    }
}
