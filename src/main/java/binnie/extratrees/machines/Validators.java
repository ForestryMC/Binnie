// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.machines;

import binnie.extratrees.api.IDesignMaterial;
import net.minecraft.item.ItemStack;
import binnie.core.machines.inventory.ValidatorIcon;
import binnie.extratrees.ExtraTrees;
import binnie.core.machines.inventory.SlotValidator;

public class Validators
{
	public static class SlotValidatorBeeswax extends SlotValidator
	{
		DesignerType type;

		public SlotValidatorBeeswax(final DesignerType type) {
			super(new ValidatorIcon(ExtraTrees.instance, "validator/polish.0", "validator/polish.1"));
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

	public static class SlotValidatorPlanks extends SlotValidator
	{
		DesignerType type;

		public SlotValidatorPlanks(final DesignerType type) {
			super(SlotValidator.IconBlock);
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
