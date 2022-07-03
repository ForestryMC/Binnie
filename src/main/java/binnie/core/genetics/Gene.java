package binnie.core.genetics;

import binnie.Binnie;
import binnie.genetics.api.IGene;
import forestry.api.core.INBTTagable;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.nbt.NBTTagCompound;

public class Gene implements INBTTagable, IGene {
    private IAllele allele;
    private IChromosomeType chromosome;
    private ISpeciesRoot root;

    @Override
    public ISpeciesRoot getSpeciesRoot() {
        return root;
    }

    @Override
    public String toString() {
        return getAlleleName();
    }

    public Gene(IAllele allele, IChromosomeType chromosome, ISpeciesRoot root) {
        this.allele = allele;
        this.chromosome = chromosome;
        this.root = root;
    }

    public Gene(NBTTagCompound nbt) {
        readFromNBT(nbt);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        allele = AlleleManager.alleleRegistry.getAllele(nbt.getString("allele"));
        root = AlleleManager.alleleRegistry.getSpeciesRoot(nbt.getString("root"));
        int chromoID = nbt.getByte("chromo");
        if (root != null && chromoID >= 0 && chromoID < root.getKaryotype().length) {
            chromosome = root.getKaryotype()[chromoID];
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setString("allele", allele.getUID());
        nbt.setString("root", root.getUID());
        nbt.setByte("chromo", (byte) chromosome.ordinal());
    }

    public boolean isCorrupted() {
        return allele == null || chromosome == null || root == null;
    }

    public static Gene create(NBTTagCompound nbt) {
        Gene gene = new Gene(nbt);
        return gene.isCorrupted() ? null : gene;
    }

    public static Gene create(IAllele allele, IChromosomeType chromosome, ISpeciesRoot root) {
        Gene gene = new Gene(allele, chromosome, root);
        return gene.isCorrupted() ? null : gene;
    }

    @Override
    public NBTTagCompound getNBTTagCompound() {
        NBTTagCompound nbt = new NBTTagCompound();
        writeToNBT(nbt);
        return nbt;
    }

    @Override
    public String getName() {
        return Binnie.Genetics.getSystem(root).getAlleleName(chromosome, allele);
    }

    public BreedingSystem getSystem() {
        return Binnie.Genetics.getSystem(root);
    }

    @Override
    public IChromosomeType getChromosome() {
        return chromosome;
    }

    @Override
    public IAllele getAllele() {
        return allele;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Gene)) {
            return false;
        }

        Gene g = (Gene) obj;
        return allele == g.allele && chromosome.ordinal() == g.chromosome.ordinal() && root == g.root;
    }

    public String getAlleleName() {
        return getSystem().getAlleleName(chromosome, allele);
    }

    public String getShortChromosomeName() {
        return getSystem().getChromosomeShortName(chromosome);
    }
}
