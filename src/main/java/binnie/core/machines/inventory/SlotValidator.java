package binnie.core.machines.inventory;

import binnie.core.util.I18N;
import forestry.api.genetics.AlleleManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public abstract class SlotValidator extends Validator<ItemStack> {
    public static ValidatorIcon IconBee;
    public static ValidatorIcon IconFrame;
    public static ValidatorIcon IconCircuit;
    public static ValidatorIcon IconBlock;
    private ValidatorIcon icon;

    public SlotValidator(ValidatorIcon icon) {
        this.icon = icon;
    }

    public IIcon getIcon(boolean input) {
        if (icon == null) {
            return null;
        }
        return icon.getIcon(input).getIcon();
    }

    public static class Item extends SlotValidator {
        private ItemStack target;

        public Item(ItemStack target, ValidatorIcon icon) {
            super(icon);
            this.target = target;
        }

        @Override
        public boolean isValid(ItemStack itemStack) {
            return itemStack.isItemEqual(target);
        }

        @Override
        public String getTooltip() {
            return target.getDisplayName();
        }
    }

    public static class Individual extends SlotValidator {
        public Individual() {
            super(null);
        }

        @Override
        public boolean isValid(ItemStack itemStack) {
            return AlleleManager.alleleRegistry.getIndividual(itemStack) != null;
        }

        @Override
        public String getTooltip() {
            return I18N.localise("binniecore.gui.slot.breedableIndividual");
        }
    }
}
