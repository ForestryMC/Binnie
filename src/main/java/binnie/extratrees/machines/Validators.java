package binnie.extratrees.machines;

import binnie.core.machines.inventory.SlotValidator;
import binnie.extratrees.api.IDesignMaterial;
import forestry.core.models.baker.ModelBaker;
import net.minecraft.item.ItemStack;

public class Validators {
	public static class SlotValidatorBeeswax extends SlotValidator {
		DesignerType type;

		public SlotValidatorBeeswax(final DesignerType type) {
			super(ModuleMachine.spritePolish);
			this.type = type;
		}

		@Override
		public boolean isValid(final ItemStack itemStack) {
			return this.type.getSystem().getAdhesive().isItemEqual(itemStack);
		}

		@Override
		public String getTooltip() {
			return this.type.getSystem().getAdhesive().getDisplayName();
		}
	}

	public static class SlotValidatorPlanks extends SlotValidator {
		DesignerType type;

		public SlotValidatorPlanks(final DesignerType type) {
			super(SlotValidator.spriteBlock);
			this.type = type;
		}

		@Override
		public boolean isValid(final ItemStack itemStack) {
			final IDesignMaterial mat = this.type.getSystem().getMaterial(itemStack);
			return itemStack != null && mat != null;
		}

		@Override
		public String getTooltip() {
			return this.type.getMaterialTooltip();
		}
	}
}
