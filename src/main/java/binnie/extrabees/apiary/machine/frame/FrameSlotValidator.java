package binnie.extrabees.apiary.machine.frame;

import binnie.core.machines.inventory.SlotValidator;
import binnie.core.util.I18N;
import forestry.api.apiculture.IHiveFrame;
import net.minecraft.item.ItemStack;

public class FrameSlotValidator extends SlotValidator {
    public FrameSlotValidator() {
        super(SlotValidator.IconFrame);
    }

    @Override
    public boolean isValid(ItemStack itemStack) {
        return itemStack != null && itemStack.getItem() instanceof IHiveFrame;
    }

    @Override
    public String getTooltip() {
        return I18N.localise("extrabees.machine.alveay.frame.tooltip");
    }
}
