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
    public static final int[] SLOT_SERUM_RESERVE = new int[] {1, 2};
    public static final int[] SLOT_SERUM_EXPENDED = new int[] {3, 4};
    public static final int[] SLOT_RESERVE = new int[] {5, 6, 7, 8, 9};
    public static final int SLOT_TARGET = 9;
    public static final int[] SLOT_FINISHED = new int[] {10, 11, 12, 13};
    public static final int RF_COST = 12000000;
    public static final int TIME_PERIOD = 1200;
    public static final int POWER_CAPACITY = 20000;

    public static void setGene(IGene gene, ItemStack target, int chromoN) {
        int chromosomeID = gene.getChromosome().ordinal();
        if (chromosomeID >= EnumBeeChromosome.HUMIDITY.ordinal() && gene.getSpeciesRoot() instanceof IBeeRoot) {
            chromosomeID--;
        }

        Class<? extends IAllele> cls = gene.getChromosome().getAlleleClass();
        if (!cls.isInstance(gene.getAllele())) {
            return;
        }

        NBTTagCompound beeNBT = target.getTagCompound();
        NBTTagCompound genomeNBT = beeNBT.getCompoundTag("Genome");
        NBTTagList chromosomes = genomeNBT.getTagList("Chromosomes", 10);
        NBTTagCompound chromosomeNBT = chromosomes.getCompoundTagAt(chromosomeID);
        chromosomeNBT.setString("UID" + chromoN, gene.getAllele().getUID());
        target.setTagCompound(beeNBT);
    }
}
