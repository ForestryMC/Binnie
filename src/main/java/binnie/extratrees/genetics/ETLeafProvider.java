package binnie.extratrees.genetics;

import binnie.extratrees.block.ModuleBlocks;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.arboriculture.IAlleleTreeSpecies;
import forestry.api.arboriculture.ILeafProvider;
import forestry.api.genetics.IAllele;
import forestry.arboriculture.PluginArboriculture;
import forestry.arboriculture.genetics.LeafProvider;
import forestry.arboriculture.genetics.TreeDefinition;
import net.minecraft.item.ItemStack;

public class ETLeafProvider implements ILeafProvider {

	private IAlleleTreeSpecies treeSpecies;
	
	public ETLeafProvider() {
	}
	
	@Override
	public void init(IAlleleTreeSpecies treeSpecies) {
		this.treeSpecies = treeSpecies;
	}

	@Override
	public ItemStack getDecorativeLeaves() {
		IAllele allele = treeSpecies;
		if(allele == null){
			allele = TreeDefinition.Oak.getTemplate()[EnumTreeChromosome.SPECIES.ordinal()];
		}
		return ModuleBlocks.getDecorativeLeaves(allele.getUID());
	}
	
}
