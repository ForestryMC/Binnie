package binnie.extratrees.machines.designer;

import binnie.core.machines.inventory.SlotValidator;
import binnie.extratrees.api.IDesignMaterial;
import net.minecraft.item.ItemStack;

public class PlanksSlotValidator extends SlotValidator {
    protected DesignerType type;

    public PlanksSlotValidator(DesignerType type) {
        super(SlotValidator.IconBlock);
        this.type = type;
    }

    @Override
    public boolean isValid(ItemStack itemStack) {
        IDesignMaterial mat = type.getSystem().getMaterial(itemStack);
        return itemStack != null && mat != null;
    }

    @Override
    public String getTooltip() {
        return type.getMaterialTooltip();
    }
}
