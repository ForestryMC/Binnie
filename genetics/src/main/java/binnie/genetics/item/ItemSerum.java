package binnie.genetics.item;

import com.google.common.base.Preconditions;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.ISpeciesRoot;

import binnie.core.Binnie;
import binnie.core.api.genetics.IBreedingSystem;
import binnie.core.api.genetics.IGene;
import binnie.core.genetics.Gene;
import binnie.core.util.I18N;
import binnie.genetics.Genetics;
import binnie.genetics.api.IItemSerum;
import binnie.genetics.genetics.GeneItem;
import binnie.genetics.genetics.IGeneItem;

public class ItemSerum extends ItemGene implements IItemSerum {
	public ItemSerum() {
		super("serum");
		this.setMaxDamage(16);
	}

	public static ItemStack create(final IGene gene) {
		final ItemStack item = new ItemStack(Genetics.items().itemSerum);
		item.setItemDamage(item.getMaxDamage());
		final GeneItem seq = new GeneItem(gene);
		seq.writeToItem(item);
		return item;
	}

	@Override
	public int getCharges(ItemStack stack) {
		return stack.getMaxDamage() - stack.getItemDamage();
	}

	@Override
	public int getMaxCharges(ItemStack stack) {
		return stack.getMaxDamage();
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			for (ISpeciesRoot root : AlleleManager.alleleRegistry.getSpeciesRoot().values()) {
				Map<IChromosomeType, List<IAllele>> chromosomeMap = Binnie.GENETICS.getChromosomeMap(root);
				if (chromosomeMap != null) {
					for (Map.Entry<IChromosomeType, List<IAllele>> entry : chromosomeMap.entrySet()) {
						IChromosomeType chromosome = entry.getKey();
						for (final IAllele allele : entry.getValue()) {
							Gene gene = Gene.create(allele, chromosome, root);
							IGeneItem geneItem = new GeneItem(gene);
							ItemStack stack = new ItemStack(this);
							geneItem.writeToItem(stack);
							items.add(stack);
						}
					}
				}
			}
		}
	}

	@Override
	public IGene[] getGenes(ItemStack stack) {
		GeneItem geneItem = this.getGeneItem(stack);
		if(geneItem == null){
			return new IGene[0];
		}
		return new IGene[]{geneItem.getGene()};
	}

	@Override
	public ISpeciesRoot getSpeciesRoot(ItemStack stack) {
		GeneItem geneItem = this.getGeneItem(stack);
		if(geneItem == null){
			return null;
		}
		return geneItem.getSpeciesRoot();
	}

	@Override
	public IGene getGene(ItemStack stack, int chromosome) {
		GeneItem geneItem = this.getGeneItem(stack);
		if(geneItem == null){
			return null;
		}
		return geneItem.getGene();
	}

	@Override
	@Nullable
	public GeneItem getGeneItem(ItemStack itemStack) {
		return GeneItem.create(itemStack);
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemstack) {
		GeneItem gene = this.getGeneItem(itemstack);
		if (gene != null && gene.getSpeciesRoot() != null) {
			IBreedingSystem system = Binnie.GENETICS.getSystem(gene.getSpeciesRoot());
			return system.getDescriptor() + ' ' + I18N.localise("genetics.item.gene.serum");
		} else {
			return I18N.localise("genetics.item.gene.serum.corrupted");
		}
	}

	@Override
	public ItemStack addGene(ItemStack stack, IGene gene) {
		final IGeneItem geneI = this.getGeneItem(stack);
		Preconditions.checkNotNull(geneI, "Cannot add gene to itemStack that is not a valid serum.");
		geneI.addGene(gene);
		geneI.writeToItem(stack);
		return stack;
	}
}
