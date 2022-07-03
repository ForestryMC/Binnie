package binnie.genetics.genetics;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.genetics.BreedingSystem;
import binnie.core.genetics.Gene;
import binnie.core.util.I18N;
import binnie.genetics.api.IGene;
import forestry.api.core.INBTTagable;
import forestry.api.genetics.ISpeciesRoot;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;

public class GeneArrayItem implements INBTTagable, IGeneItem {
    protected List<IGene> genes;

    public GeneArrayItem(ItemStack stack) {
        genes = new ArrayList<>();
        if (stack == null) {
            return;
        }
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
    public int getColour(int color) {
        if (color == 2) {
            return getBreedingSystem().getColor();
        }
        return 0xffffff;
    }

    @Override
    public void getInfo(List tooltip) {
        List<Object> totalList = new ArrayList<>();
        for (IGene gene : genes) {
            String chromosomeName = getBreedingSystem().getChromosomeName(gene.getChromosome());
            totalList.add(EnumChatFormatting.GOLD + chromosomeName + EnumChatFormatting.GRAY + ": " + gene.getName());
        }

        if (totalList.size() < 4 || BinnieCore.proxy.isShiftDown()) {
            tooltip.addAll(totalList);
        } else {
            tooltip.add(totalList.get(0));
            tooltip.add(totalList.get(1));
            tooltip.add(I18N.localise("genetics.gene.more", totalList.size() - 2));
        }
    }

    public BreedingSystem getBreedingSystem() {
        if (genes.size() == 0) {
            return null;
        }
        BreedingSystem system =
                Binnie.Genetics.getSystem(genes.get(0).getSpeciesRoot().getUID());
        return (system == null) ? Binnie.Genetics.getActiveSystems().iterator().next() : system;
    }

    public List<IGene> getGenes() {
        return genes;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        genes.clear();
        if (nbt == null) {
            return;
        }

        NBTTagList list = nbt.getTagList("genes", 10);
        for (int i = 0; i < list.tagCount(); ++i) {
            Gene gene = Gene.create(list.getCompoundTagAt(i));
            genes.add(gene);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        if (genes.size() == 0) {
            return;
        }

        NBTTagList list = new NBTTagList();
        for (IGene gene : genes) {
            NBTTagCompound geneNBT = gene.getNBTTagCompound();
            list.appendTag(geneNBT);
        }
        nbt.setTag("genes", list);
    }

    @Override
    public ISpeciesRoot getSpeciesRoot() {
        if (genes.size() == 0) {
            return null;
        }
        return genes.get(0).getSpeciesRoot();
    }

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
        if (stack == null) {
            return;
        }

        NBTTagCompound nbt = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
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
