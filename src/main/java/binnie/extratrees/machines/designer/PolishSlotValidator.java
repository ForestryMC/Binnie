package binnie.extratrees.machines.designer;

import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.inventory.ValidatorIcon;
import binnie.extratrees.ExtraTrees;
import net.minecraft.item.ItemStack;

public class PolishSlotValidator extends SlotValidator {
    protected DesignerType type;

    public PolishSlotValidator(DesignerType type) {
        super(new ValidatorIcon(ExtraTrees.instance, "validator/polish.0", "validator/polish.1"));
        this.type = type;
    }

    @Override
    public boolean isValid(ItemStack itemStack) {
        return type.getSystem().getAdhesive().isItemEqual(itemStack);
    }

    @Override
    public String getTooltip() {
        return type.getSystem().getAdhesive().getDisplayName();
    }
}
