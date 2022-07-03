package binnie.genetics.machine.analyser;

import binnie.core.machines.inventory.SlotValidator;
import binnie.core.util.I18N;
import binnie.genetics.item.GeneticsItems;
import binnie.genetics.machine.ModuleMachine;
import net.minecraft.item.ItemStack;

public class DyeSlotValidator extends SlotValidator {
    public DyeSlotValidator() {
        super(ModuleMachine.IconDye);
    }

    @Override
    public boolean isValid(ItemStack itemStack) {
        return itemStack.isItemEqual(GeneticsItems.DNADye.get(1));
    }

    @Override
    public String getTooltip() {
        return I18N.localise("genetics.machine.analyser.dnaDye");
    }
}
