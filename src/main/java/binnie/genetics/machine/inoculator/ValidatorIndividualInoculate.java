package binnie.genetics.machine.inoculator;

import binnie.Binnie;
import binnie.core.machines.inventory.SlotValidator;
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
		return root != null && Binnie.Genetics.getSystem(root).isDNAManipulable(object);
	}

	@Override
	public String getTooltip() {
		return "Inoculable Individual";
	}
}
