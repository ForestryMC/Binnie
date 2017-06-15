package binnie.genetics.integration.jei.genepool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import forestry.api.genetics.ISpeciesType;

public class GenepoolRecipeMaker {
	public static List<GenepoolRecipeWrapper> create() {
		List<GenepoolRecipeWrapper> recipes = new ArrayList<>();

		Collection<ISpeciesRoot> roots = AlleleManager.alleleRegistry.getSpeciesRoot().values();
		for (ISpeciesRoot root : roots) {
			ISpeciesType[] speciesTypes = root.getIconType().getClass().getEnumConstants();
			IAllele[] defaultTemplate = root.getDefaultTemplate();
			IIndividual individual = root.templateAsIndividual(defaultTemplate);
			for (ISpeciesType speciesType : speciesTypes) {
				ItemStack memberStack = root.getMemberStack(individual, speciesType);
				memberStack.setItemDamage(OreDictionary.WILDCARD_VALUE);

				GenepoolRecipeWrapper recipeWrapper = new GenepoolRecipeWrapper(memberStack);
				recipes.add(recipeWrapper);
			}
		}

		return recipes;
	}
}
