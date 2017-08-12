package binnie.extratrees.genetics;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;

import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.arboriculture.IAlleleTreeSpecies;
import forestry.api.arboriculture.ILeafProvider;
import forestry.api.genetics.IAllele;
import forestry.arboriculture.genetics.TreeDefinition;

import binnie.extratrees.modules.ModuleWood;

public class ETLeafProvider implements ILeafProvider {
	@Nullable
	private IAlleleTreeSpecies treeSpecies;

	@Override
	public void init(IAlleleTreeSpecies treeSpecies) {
		this.treeSpecies = treeSpecies;
	}

	@Override
	public ItemStack getDecorativeLeaves() {
		IAllele species;
		if (treeSpecies == null) {
			species = TreeDefinition.Oak.getTemplate()[EnumTreeChromosome.SPECIES.ordinal()];
		} else {
			species = treeSpecies;
		}
		return ModuleWood.getDecorativeLeaves(species.getUID());
	}
}
