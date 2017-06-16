package binnie.genetics.integration.jei.sequencer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.item.ItemStack;

import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.ISpeciesRoot;

import binnie.core.genetics.Gene;
import binnie.genetics.item.ItemSequence;

public class SequencerRecipeMaker {
	public static List<SequencerRecipeWrapper> create() {
		List<SequencerRecipeWrapper> recipes = new ArrayList<>();

		Collection<ISpeciesRoot> roots = AlleleManager.alleleRegistry.getSpeciesRoot().values();
		for (ISpeciesRoot root : roots) {
			IChromosomeType speciesChromosomeType = root.getSpeciesChromosomeType();
			IAllele[] defaultTemplate = root.getDefaultTemplate();

			IAllele species = defaultTemplate[speciesChromosomeType.ordinal()];
			ItemStack filledSequence = ItemSequence.create(new Gene(species, speciesChromosomeType, root), false);
			recipes.add(new SequencerRecipeWrapper(filledSequence));

			filledSequence = filledSequence.copy();
			filledSequence.setItemDamage(0);
			recipes.add(new SequencerRecipeWrapper(filledSequence));
		}
		return recipes;
	}
}
