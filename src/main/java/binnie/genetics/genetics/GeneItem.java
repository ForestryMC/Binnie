package binnie.genetics.genetics;

import binnie.Binnie;
import binnie.core.genetics.BreedingSystem;
import binnie.core.genetics.Gene;
import binnie.genetics.api.IGene;
import forestry.api.core.INBTTagable;
import forestry.api.genetics.ISpeciesRoot;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

public class GeneItem implements INBTTagable, IGeneItem {
    protected IGene gene;

    public GeneItem(ItemStack stack) {
        if (stack == null) {
            return;
        }
        readFromNBT(stack.getTagCompound());
    }

    public GeneItem(IGene gene) {
        this.gene = gene;
    }

    public boolean isCorrupted() {
        return gene == null;
    }

    @Override
    public void writeToItem(ItemStack stack) {
        if (gene == null || stack == null) {
            return;
        }

        NBTTagCompound nbt = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
        writeToNBT(nbt);
        stack.setTagCompound(nbt);
    }

    @Override
    public int getColour(int color) {
        if (color == 2 && getBreedingSystem() != null) {
            return getBreedingSystem().getColor();
        }
        return 0xffffff;
    }

    @Override
    public void getInfo(List tooltip) {
        String chromosomeName = getBreedingSystem().getChromosomeName(gene.getChromosome());
        tooltip.add(EnumChatFormatting.GOLD + chromosomeName + EnumChatFormatting.GRAY + ": " + gene.getName());
    }

    public BreedingSystem getBreedingSystem() {
        if (gene == null) {
            return null;
        }
        return Binnie.Genetics.getSystem(gene.getSpeciesRoot().getUID());
    }

    public IGene getGene() {
        return gene;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        if (nbt != null) {
            gene = Gene.create(nbt.getCompoundTag("gene"));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        if (gene == null) {
            return;
        }

        NBTTagCompound geneNBT = gene.getNBTTagCompound();
        nbt.setTag("gene", geneNBT);
    }

    @Override
    public ISpeciesRoot getSpeciesRoot() {
        if (gene != null) {
            return gene.getSpeciesRoot();
        }
        return null;
    }

    @Override
    public void addGene(IGene gene) {
        this.gene = gene;
    }
}
