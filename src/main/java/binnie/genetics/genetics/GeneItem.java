package binnie.genetics.genetics;

import binnie.Binnie;
import binnie.core.genetics.BreedingSystem;
import binnie.core.genetics.Gene;
import binnie.genetics.api.IGene;
import binnie.genetics.item.GeneticsItems;
import com.google.common.base.Preconditions;
import forestry.api.core.INbtWritable;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class GeneItem implements INbtWritable, IGeneItem {
	private IGene gene;

	@Nullable
	public static GeneItem create(final ItemStack stack) {
		NBTTagCompound tagCompound = stack.getTagCompound();
		if (tagCompound != null && tagCompound.hasKey("gene", Constants.NBT.TAG_COMPOUND)) {
			NBTTagCompound geneNbt = tagCompound.getCompoundTag("gene");
			Gene gene = Gene.create(geneNbt);
			return new GeneItem(gene);
		}
		return null;
	}

	public GeneItem(final IGene gene) {
		this.gene = gene;
	}

	@Override
	public void writeToItem(final ItemStack stack) {
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null) {
			nbt = new NBTTagCompound();
		}
		this.writeToNBT(nbt);
		stack.setTagCompound(nbt);
	}

	@Override
	public int getColour(final int renderPass) {
		if (renderPass == 2) {
			return this.getBreedingSystem().getColour();
		}
		return 16777215;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getInfo(final List<String> list) {
		final String chromosomeName = this.getBreedingSystem().getChromosomeName(this.gene.getChromosome());
		list.add(TextFormatting.GOLD + chromosomeName + TextFormatting.GRAY + ": " + this.gene.getName());
	}

	public BreedingSystem getBreedingSystem() {
		return Binnie.GENETICS.getSystem(this.gene.getSpeciesRoot());
	}

	public IGene getGene() {
		return this.gene;
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound nbt) {
		final NBTTagCompound geneNBT = this.gene.getNBTTagCompound();
		nbt.setTag("gene", geneNBT);
		return nbt;
	}

	@Override
	public ISpeciesRoot getSpeciesRoot() {
		return this.gene.getSpeciesRoot();
	}

	@Override
	public void addGene(final IGene gene) {
		this.gene = gene;
	}
}
