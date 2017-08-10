package binnie.genetics.machine.inoculator;

import net.minecraft.item.ItemStack;

import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.ISpeciesRoot;

import binnie.core.Binnie;
import binnie.core.machines.inventory.SlotValidator;

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
		return "Inoculable Individual";
	}
}
