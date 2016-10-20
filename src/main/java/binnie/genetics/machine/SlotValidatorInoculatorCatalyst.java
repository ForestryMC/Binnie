package binnie.genetics.machine;

import binnie.core.machines.inventory.Validator;
import net.minecraft.item.ItemStack;

public class SlotValidatorInoculatorCatalyst extends Validator<ItemStack> {
    @Override
    public boolean isValid(final ItemStack itemStack) {
        return false;
    }

    @Override
    public String getTooltip() {
        return "Inoculation Catalyst";
    }
}
