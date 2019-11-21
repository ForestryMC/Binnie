package binnie.genetics.machine.splicer;

import binnie.core.api.genetics.IGene;
import com.google.common.base.Preconditions;
import forestry.api.apiculture.IBee;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosome;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class Splicer {
	public static final int SLOT_SERUM_VIAL = 0;
	public static final int[] SLOT_SERUM_RESERVE = new int[]{1, 2};
	public static final int[] SLOT_SERUM_EXPENDED = new int[]{3, 4};
	public static final int[] SLOT_RESERVE = new int[]{5, 6, 7, 8, 9};
	public static final int SLOT_TARGET = 9;
	public static final int[] SLOT_FINISHED = new int[]{10, 11, 12, 13};

	public static void setGene(IGene gene, ItemStack target, boolean setPrimary, boolean setSecondary) {
		int chromosomeID = gene.getChromosome().ordinal();
		Class<? extends IAllele> cls = gene.getChromosome().getAlleleClass();
		if (!cls.isInstance(gene.getAllele())) {
			return;
		}
		NBTTagCompound targetTag = target.getTagCompound();
		NBTTagCompound mate = null;
		if (targetTag != null && targetTag.hasKey("Mate")) {
			mate = targetTag.getCompoundTag("Mate").copy();
		}
		ISpeciesRoot speciesRoot = AlleleManager.alleleRegistry.getSpeciesRoot(target);
		Preconditions.checkNotNull(speciesRoot);
		IIndividual original = speciesRoot.getMember(target);
		Preconditions.checkNotNull(original);
		IChromosome[] chromosomes = original.getGenome().getChromosomes();
		IAllele[] primaryAlleles = new IAllele[chromosomes.length];
		IAllele[] secondaryAlleles = new IAllele[chromosomes.length];
		for (int i = 0; i < chromosomes.length; i++) {
			IChromosome chromosome = chromosomes[i];
			if (i == chromosomeID && setPrimary) {
				primaryAlleles[i] = gene.getAllele();
			} else {
				primaryAlleles[i] = chromosome.getPrimaryAllele();
			}
			if (i == chromosomeID && setSecondary) {
				secondaryAlleles[i] = gene.getAllele();
			} else {
				secondaryAlleles[i] = chromosome.getSecondaryAllele();
			}
		}
		IIndividual individual = speciesRoot.templateAsIndividual(primaryAlleles, secondaryAlleles);
		if (original.isAnalyzed()) {
			individual.analyze();
		}
		if (original instanceof IBee) {
			IBee individualBee = (IBee) individual;
			IBee originalBee = (IBee) original;
			individualBee.setIsNatural(originalBee.isNatural());
		}
		NBTTagCompound nbt = new NBTTagCompound();
		individual.writeToNBT(nbt);
		if (mate != null) {
			nbt.setTag("Mate", mate);
		}
		target.setTagCompound(nbt);
	}
}
