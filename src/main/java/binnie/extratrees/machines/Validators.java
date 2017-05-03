package binnie.extratrees.machines;

import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.inventory.ValidatorIcon;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.api.IDesignMaterial;
import net.minecraft.item.ItemStack;

public class Validators {
	public static class SlotValidatorBeeswax extends SlotValidator {
		protected DesignerType type;

		public SlotValidatorBeeswax(DesignerType type) {
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

	public static class SlotValidatorPlanks extends SlotValidator {
		protected DesignerType type;

		public SlotValidatorPlanks(DesignerType type) {
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
}
