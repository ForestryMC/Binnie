package binnie.genetics.integration.jei.inoculator;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import forestry.api.genetics.ISpeciesType;

import binnie.core.Binnie;
import binnie.core.genetics.BreedingSystem;
import binnie.core.genetics.Gene;
import binnie.genetics.genetics.Engineering;
import binnie.genetics.item.ItemSerum;
import binnie.genetics.item.ItemSerumArray;

public class InoculatorRecipeMaker {
	public static List<InoculatorRecipeWrapper> create() {
		List<InoculatorRecipeWrapper> recipes = new ArrayList<>();

		for (BreedingSystem system : Binnie.GENETICS.getActiveSystems()) {
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

					recipes.add(new InoculatorRecipeWrapper(serum, memberStack));
					recipes.add(new SplicerRecipeWrapper(serum, memberStack));

					ItemStack serumArray = ItemSerumArray.create(new Gene(species, speciesChromosomeType, root));
					serumArray.setItemDamage(0); // set fully charged
					for (IChromosomeType chromosomeType : root.getKaryotype()) {
						if (chromosomeType != speciesChromosomeType) {
							IAllele allele = defaultTemplate[chromosomeType.ordinal()];
							Engineering.addGene(serumArray, new Gene(allele, chromosomeType, root));
						}
					}

					recipes.add(new InoculatorRecipeWrapper(serumArray, memberStack));
					recipes.add(new SplicerRecipeWrapper(serumArray, memberStack));
				}
			}
		}

		return recipes;
	}
}
