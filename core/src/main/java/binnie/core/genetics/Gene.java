package binnie.core.genetics;

import binnie.core.api.genetics.IBreedingSystem;
import binnie.core.api.genetics.IGene;
import com.google.common.base.Preconditions;

import net.minecraft.nbt.NBTTagCompound;

import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.ISpeciesRoot;

import binnie.core.Binnie;

public class Gene implements IGene {
	private IAllele allele;
	private IChromosomeType chromosome;
	private ISpeciesRoot root;

	public Gene(final IAllele allele, final IChromosomeType chromosome, final ISpeciesRoot root) {
		this.allele = allele;
		this.chromosome = chromosome;
		this.root = root;
	}

	public Gene(final NBTTagCompound nbt) {
		this.allele = AlleleManager.alleleRegistry.getAllele(nbt.getString("allele"));
		String rootKey = nbt.getString("root");
		ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(rootKey);
		Preconditions.checkArgument(root != null, "Could not find root: %s", rootKey);
		this.root = root;
		final int chromoID = nbt.getByte("chromo");
		Preconditions.checkArgument(chromoID >= 0 && chromoID < this.root.getKaryotype().length, "Invalid chromosomeId: %s", chromoID);
		this.chromosome = this.root.getKaryotype()[chromoID];
	}

	public static Gene create(final NBTTagCompound nbt) {
		return new Gene(nbt);
	}

	public static Gene create(final IAllele allele, final IChromosomeType chromosome, final ISpeciesRoot root) {
		return new Gene(allele, chromosome, root);
	}

	@Override
	public ISpeciesRoot getSpeciesRoot() {
		return this.root;
	}

	@Override
	public String toString() {
		return this.getAlleleName();
	}

	@Override
	public void readFromNBT(final NBTTagCompound nbt) {
		this.allele = AlleleManager.alleleRegistry.getAllele(nbt.getString("allele"));
		String rootKey = nbt.getString("root");
		ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(rootKey);
		Preconditions.checkArgument(root != null, "Could not find root: %s", rootKey);
		this.root = root;
		final int chromoID = nbt.getByte("chromo");
		Preconditions.checkArgument(chromoID >= 0 && chromoID < this.root.getKaryotype().length, "Invalid chromosomeId: %s", chromoID);
		this.chromosome = this.root.getKaryotype()[chromoID];
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound nbt) {
		nbt.setString("allele", this.allele.getUID());
		nbt.setString("root", this.root.getUID());
		nbt.setByte("chromo", (byte) this.chromosome.ordinal());
		return nbt;
	}

	@Override
	public NBTTagCompound getNBTTagCompound() {
		final NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return nbt;
	}

	@Override
	public String getName() {
		return Binnie.GENETICS.getSystem(this.root).getAlleleName(this.chromosome, this.allele);
	}

	public IBreedingSystem getSystem() {
		return Binnie.GENETICS.getSystem(this.root);
	}

	@Override
	public IChromosomeType getChromosome() {
		return this.chromosome;
	}

	@Override
	public IAllele getAllele() {
		return this.allele;
	}

	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof Gene)) {
			return false;
		}
		final Gene g = (Gene) obj;
		return this.allele == g.allele && this.chromosome.ordinal() == g.chromosome.ordinal() && this.root == g.root;
	}

	public String getAlleleName() {
		return this.getSystem().getAlleleName(this.chromosome, this.allele);
	}

	public String getShortChromosomeName() {
		return this.getSystem().getChromosomeShortName(this.chromosome);
	}
}
