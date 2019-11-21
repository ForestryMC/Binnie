package binnie.genetics.machine.splicer;

import binnie.core.Binnie;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.util.I18N;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.item.ItemStack;

public class ValidatorIndividualInoculate extends SlotValidator {
	public ValidatorIndividualInoculate() {
		super(null);
	}

	@Override
	public boolean isValid(final ItemStack object) {
		final ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(object);
		return root != null && Binnie.GENETICS.getSystem(root).isDNAManipulable(object);
	}

	@Override
	public String getTooltip() {
		return I18N.localise("genetics.machine.adv_machine.splicer.tooltips.slots.individual");
	}
}
