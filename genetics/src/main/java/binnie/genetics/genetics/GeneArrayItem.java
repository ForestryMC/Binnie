package binnie.genetics.genetics;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextFormatting;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.INbtReadable;
import forestry.api.core.INbtWritable;
import forestry.api.genetics.ISpeciesRoot;

import binnie.core.Binnie;
import binnie.core.api.genetics.IBreedingSystem;
import binnie.core.api.genetics.IGene;
import binnie.core.genetics.Gene;
import binnie.core.util.I18N;

public class GeneArrayItem implements INbtReadable, INbtWritable, IGeneItem {
	private static final String GENES_NBT = "genes";

	private final List<IGene> genes;

	public GeneArrayItem(ItemStack stack) {
		genes = new ArrayList<>();
		readFromNBT(stack.getTagCompound());
	}

	public GeneArrayItem(IGene gene) {
		genes = new ArrayList<>();
		addGene(gene);
	}

	public GeneArrayItem() {
		genes = new ArrayList<>();
	}

	@Override
	public int getColor(int renderPass) {
		if (renderPass == 2) {
			IBreedingSystem breedingSystem = getBreedingSystem();
			if (breedingSystem != null) {
				return breedingSystem.getColour();
			}
		}
		return 0xffffff;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getInfo(List<String> list) {
		List<String> totalList = new ArrayList<>();
		IBreedingSystem breedingSystem = getBreedingSystem();
		if (breedingSystem != null) {
			for (IGene gene : genes) {
				String chromosomeName = breedingSystem.getChromosomeName(gene.getChromosome());
				totalList.add(TextFormatting.GOLD + chromosomeName + TextFormatting.GRAY + ": " + gene.getName());
			}
		}

		if (totalList.size() < 4 || GuiScreen.isShiftKeyDown()) {
			list.addAll(totalList);
		} else {
			list.add(totalList.get(0));
			list.add(totalList.get(1));
			list.add((totalList.size() - 2) + " " + I18N.localise("genetics.item.gene.more.genes"));
		}
	}

	@Nullable
	public IBreedingSystem getBreedingSystem() {
		if (genes.isEmpty()) {
			return null;
		}
		IBreedingSystem system = Binnie.GENETICS.getSystem(genes.get(0).getSpeciesRoot().getUID());
		return (system == null) ? Binnie.GENETICS.getActiveSystems().iterator().next() : system;
	}

	public List<IGene> getGenes() {
		return genes;
	}

	@Override
	public void readFromNBT(@Nullable NBTTagCompound nbt) {
		genes.clear();
		if (nbt == null) {
			return;
		}

		NBTTagList list = nbt.getTagList(GENES_NBT, 10);
		for (int i = 0; i < list.tagCount(); ++i) {
			Gene gene = Gene.create(list.getCompoundTagAt(i));
			genes.add(gene);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		if (genes.isEmpty()) {
			return nbt;
		}

		NBTTagList list = new NBTTagList();
		for (IGene gene : genes) {
			list.appendTag(gene.getNBTTagCompound());
		}
		nbt.setTag(GENES_NBT, list);
		return nbt;
	}

	@Override
	@Nullable
	public ISpeciesRoot getSpeciesRoot() {
		if (genes.isEmpty()) {
			return null;
		}
		return genes.get(0).getSpeciesRoot();
	}

	@Nullable
	public IGene getGene(int chromosome) {
		for (IGene gene : genes) {
			if (gene.getChromosome().ordinal() == chromosome) {
				return gene;
			}
		}
		return null;
	}

	@Override
	public void writeToItem(ItemStack stack) {
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null) {
			nbt = new NBTTagCompound();
		}
		writeToNBT(nbt);
		stack.setTagCompound(nbt);
	}

	@Override
	public void addGene(IGene gene) {
		if (getGene(gene.getChromosome().ordinal()) != null) {
			genes.remove(getGene(gene.getChromosome().ordinal()));
		}
		genes.add(gene);
	}
}
