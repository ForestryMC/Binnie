package binnie.extrabees.apiary.machine.hatchery;

import binnie.Binnie;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.util.I18N;
import binnie.extrabees.ExtraBees;
import forestry.api.apiculture.EnumBeeType;
import net.minecraft.item.ItemStack;

public class LarvaeSlotValidator extends SlotValidator {
	public LarvaeSlotValidator() {
		super(SlotValidator.IconBee);
	}

	@Override
	public boolean isValid(ItemStack itemStack) {
		return Binnie.Genetics.getBeeRoot().isMember(itemStack) && Binnie.Genetics.getBeeRoot().getType(itemStack) == EnumBeeType.LARVAE;
	}

	@Override
	public String getTooltip() {
		return I18N.localise(ExtraBees.instance, "machine.alveay.hatchery.tooltip");
	}
}
