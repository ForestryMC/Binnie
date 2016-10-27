package binnie.genetics.genetics;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.genetics.BreedingSystem;
import binnie.core.genetics.Gene;
import binnie.genetics.api.IGene;
import forestry.api.core.INbtReadable;
import forestry.api.core.INbtWritable;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class GeneArrayItem implements INbtReadable, INbtWritable, IGeneItem {
	List<IGene> genes;

	public GeneArrayItem(final ItemStack stack) {
		this.genes = new ArrayList<>();
		if (stack == null) {
			return;
		}
		this.readFromNBT(stack.getTagCompound());
	}

	public GeneArrayItem(final IGene gene) {
		this.genes = new ArrayList<>();
		this.addGene(gene);
	}

	public GeneArrayItem() {
		this.genes = new ArrayList<>();
	}

	@Override
	public int getColour(final int renderPass) {
		if (renderPass == 2) {
			return this.getBreedingSystem().getColour();
		}
		return 16777215;
	}

	@Override
	public void getInfo(final List list) {
		final List<Object> totalList = new ArrayList<>();
		for (IGene gene : this.genes) {
			final String chromosomeName = this.getBreedingSystem().getChromosomeName(gene.getChromosome());
			totalList.add(TextFormatting.GOLD + chromosomeName + TextFormatting.GRAY + ": " + gene.getName());
		}
		if (totalList.size() < 4 || BinnieCore.proxy.isShiftDown()) {
			list.addAll(totalList);
		} else {
			list.add(totalList.get(0));
			list.add(totalList.get(1));
			list.add(totalList.size() - 2 + " " + Binnie.Language.localise("genetic.item.gene.more.genes"));
		}
	}

	public BreedingSystem getBreedingSystem() {
		if (this.genes.size() == 0) {
			return null;
		}
		final BreedingSystem system = Binnie.Genetics.getSystem(this.genes.get(0).getSpeciesRoot().getUID());
		return (system == null) ? Binnie.Genetics.getActiveSystems().iterator().next() : system;
	}

	public List<IGene> getGenes() {
		return this.genes;
	}

	@Override
	public void readFromNBT(final NBTTagCompound nbt) {
		this.genes.clear();
		if (nbt != null) {
			final NBTTagList list = nbt.getTagList("genes", 10);
			for (int i = 0; i < list.tagCount(); ++i) {
				final Gene gene = Gene.create(list.getCompoundTagAt(i));
				this.genes.add(gene);
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound nbt) {
		if (this.genes.size() == 0) {
			return nbt;
		}
		final NBTTagList list = new NBTTagList();
		for (final IGene gene : this.genes) {
			final NBTTagCompound geneNBT = gene.getNBTTagCompound();
			list.appendTag(geneNBT);
		}
		nbt.setTag("genes", list);
		return nbt;
	}

	@Override
	public ISpeciesRoot getSpeciesRoot() {
		if (this.genes.size() == 0) {
			return null;
		}
		return this.genes.get(0).getSpeciesRoot();
	}

	public IGene getGene(final int chromosome) {
		for (final IGene gene : this.genes) {
			if (gene.getChromosome().ordinal() == chromosome) {
				return gene;
			}
		}
		return null;
	}

	@Override
	public void writeToItem(final ItemStack stack) {
		if (stack == null) {
			return;
		}
		final NBTTagCompound nbt = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
		this.writeToNBT(nbt);
		stack.setTagCompound(nbt);
	}

	@Override
	public void addGene(final IGene gene) {
		if (this.getGene(gene.getChromosome().ordinal()) != null) {
			this.genes.remove(this.getGene(gene.getChromosome().ordinal()));
		}
		this.genes.add(gene);
	}
}
