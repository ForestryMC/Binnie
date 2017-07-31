package binnie.core.gui.fieldkit;

import javax.annotation.Nullable;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.inventory.ValidatorSprite;
import binnie.core.util.I18N;

public class SlotValidatorPaper extends SlotValidator {

	public SlotValidatorPaper(@Nullable ValidatorSprite icon) {
		super(icon);
	}

	@Override
	public boolean isValid(ItemStack value) {
		return value.getItem() == Items.PAPER;
	}

	@Override
	public String getTooltip() {
		return I18N.localise("binniecore.gui.fieldkit.paper");
	}

}
