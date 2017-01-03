package binnie.genetics.integration.jei.inoculator;

import binnie.Binnie;
import binnie.core.genetics.BreedingSystem;
import binnie.core.genetics.Gene;
import binnie.genetics.item.ItemSerum;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import forestry.api.genetics.ISpeciesType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class InoculatorRecipeMaker {
	public static List<InoculatorRecipeWrapper> create() {
		List<InoculatorRecipeWrapper> recipes = new ArrayList<>();

		for (BreedingSystem system : Binnie.Genetics.getActiveSystems()) {
			ISpeciesRoot root = system.getSpeciesRoot();
			IChromosomeType speciesChromosomeType = root.getSpeciesChromosomeType();
			IAllele[] defaultTemplate = root.getDefaultTemplate();
			IIndividual individual = root.templateAsIndividual(defaultTemplate);
			for (ISpeciesType speciesType : system.getActiveTypes()) {
				if (system.isDNAManipulable(speciesType)) {
					ItemStack memberStack = root.getMemberStack(individual, speciesType);
					memberStack.setItemDamage(OreDictionary.WILDCARD_VALUE);

					IAllele species = defaultTemplate[speciesChromosomeType.ordinal()];
					ItemStack serum = ItemSerum.create(new Gene(species, speciesChromosomeType, root));
					serum.setItemDamage(0); // set fully charged

					InoculatorRecipeWrapper recipeWrapper = new InoculatorRecipeWrapper(serum, memberStack);
					recipes.add(recipeWrapper);
				}
			}
		}

		return recipes;
	}
}
