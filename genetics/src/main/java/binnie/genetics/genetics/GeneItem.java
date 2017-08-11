package binnie.genetics.genetics;

import javax.annotation.Nullable;
import java.util.List;

import binnie.core.api.genetics.IBreedingSystem;
import binnie.genetics.api.IGene;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;

import net.minecraftforge.common.util.Constants;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.INbtWritable;
import forestry.api.genetics.ISpeciesRoot;

import binnie.core.Binnie;
import binnie.core.genetics.Gene;


public class GeneItem implements INbtWritable, IGeneItem {
	private IGene gene;

	public GeneItem(IGene gene) {
		this.gene = gene;
	}

	@Nullable
	public static GeneItem create(ItemStack stack) {
		NBTTagCompound tagCompound = stack.getTagCompound();
		if (tagCompound != null && tagCompound.hasKey("gene", Constants.NBT.TAG_COMPOUND)) {
			NBTTagCompound geneNbt = tagCompound.getCompoundTag("gene");
			Gene gene = Gene.create(geneNbt);
			return new GeneItem(gene);
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
	public int getColor(int renderPass) {
		if (renderPass == 2) {
			return getBreedingSystem().getColour();
		}
		return 0xffffff;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getInfo(List<String> list) {
		String chromosomeName = getBreedingSystem().getChromosomeName(gene.getChromosome());
		list.add(TextFormatting.GOLD + chromosomeName + TextFormatting.GRAY + ": " + gene.getName());
	}

	public IBreedingSystem getBreedingSystem() {
		return Binnie.GENETICS.getSystem(gene.getSpeciesRoot());
	}

	public IGene getGene() {
		return gene;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		NBTTagCompound geneNBT = gene.getNBTTagCompound();
		nbt.setTag("gene", geneNBT);
		return nbt;
	}

	@Override
	public ISpeciesRoot getSpeciesRoot() {
		return gene.getSpeciesRoot();
	}

	@Override
	public void addGene(IGene gene) {
		this.gene = gene;
	}
}
