package binnie.genetics.integration.jei.isolator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import forestry.api.genetics.ISpeciesType;

import binnie.core.genetics.Gene;
import binnie.genetics.item.ItemSequence;

public class IsolatorRecipeMaker {
	public static List<IsolatorRecipeWrapper> create() {
		List<IsolatorRecipeWrapper> recipes = new ArrayList<>();

		Collection<ISpeciesRoot> roots = AlleleManager.alleleRegistry.getSpeciesRoot().values();
		for (ISpeciesRoot root : roots) {
			ISpeciesType[] speciesTypes = root.getIconType().getClass().getEnumConstants();
			IChromosomeType speciesChromosomeType = root.getSpeciesChromosomeType();
			IAllele[] defaultTemplate = root.getDefaultTemplate();
			IIndividual individual = root.templateAsIndividual(defaultTemplate);
			for (ISpeciesType speciesType : speciesTypes) {
				ItemStack memberStack = root.getMemberStack(individual, speciesType);
				memberStack.setItemDamage(OreDictionary.WILDCARD_VALUE);

				IAllele species = defaultTemplate[speciesChromosomeType.ordinal()];
				ItemStack filledSequence = ItemSequence.create(new Gene(species, speciesChromosomeType, root), false);
				IsolatorRecipeWrapper recipeWrapper = new IsolatorRecipeWrapper(memberStack, filledSequence);
				recipes.add(recipeWrapper);
			}
		}

		return recipes;
	}
}
