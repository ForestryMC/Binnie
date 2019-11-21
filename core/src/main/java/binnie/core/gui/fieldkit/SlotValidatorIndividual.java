package binnie.core.gui.fieldkit;

import binnie.core.Binnie;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.inventory.ValidatorSprite;
import binnie.core.util.I18N;
import forestry.api.genetics.AlleleManager;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class SlotValidatorIndividual extends SlotValidator {
	public SlotValidatorIndividual(@Nullable ValidatorSprite icon) {
		super(icon);
	}

	@Override
	public String getTooltip() {
		return I18N.localise("binniecore.gui.fieldkit.individual");
	}

	@Override
	public boolean isValid(ItemStack value) {
		return AlleleManager.alleleRegistry.isIndividual(value) || Binnie.GENETICS.getConversion(value) != null;
	}
}
