package binnie.genetics.machine.splicer;

import binnie.genetics.api.IGene;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.genetics.IAllele;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class Splicer {
	public static final int SLOT_SERUM_VIAL = 0;
	public static final int[] SLOT_SERUM_RESERVE = new int[]{1, 2};
	public static final int[] SLOT_SERUM_EXPENDED = new int[]{3, 4};
	public static final int[] SLOT_RESERVE = new int[]{5, 6, 7, 8, 9};
	public static final int SLOT_TARGET = 9;
	public static final int[] SLOT_FINISHED = new int[]{10, 11, 12, 13};

	public static void setGene(final IGene gene, final ItemStack target, final int chromoN) {
		int chromosomeID;
		final int chromosome = chromosomeID = gene.getChromosome().ordinal();
		if (chromosomeID >= EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal() && gene.getSpeciesRoot() instanceof IBeeRoot) {
			--chromosomeID;
		}
		final Class<? extends IAllele> cls = gene.getChromosome().getAlleleClass();
		if (!cls.isInstance(gene.getAllele())) {
			return;
		}
		final NBTTagCompound beeNBT = target.getTagCompound();
		final NBTTagCompound genomeNBT = beeNBT.getCompoundTag("Genome");
		final NBTTagList chromosomes = genomeNBT.getTagList("Chromosomes", 10);
		final NBTTagCompound chromosomeNBT = chromosomes.getCompoundTagAt(chromosomeID);
		chromosomeNBT.setString("UID" + chromoN, gene.getAllele().getUID());
		target.setTagCompound(beeNBT);
	}
}
