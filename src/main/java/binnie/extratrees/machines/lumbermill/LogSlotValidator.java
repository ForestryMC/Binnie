package binnie.extratrees.machines.lumbermill;

import binnie.core.machines.inventory.SlotValidator;
import binnie.core.util.I18N;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class LogSlotValidator extends SlotValidator {
    public LogSlotValidator() {
        super(SlotValidator.IconBlock);
    }

    @Override
    public boolean isValid(ItemStack itemStack) {
        String name = OreDictionary.getOreName(OreDictionary.getOreID(itemStack));
        return name.contains("logWood") && Lumbermill.getPlankProduct(itemStack) != null;
    }

    @Override
    public String getTooltip() {
        return I18N.localise("extratrees.machine.lumbermill.logs");
    }
}
