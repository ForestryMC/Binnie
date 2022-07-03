package binnie.genetics.machine.splicer;

import binnie.core.machines.Machine;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.core.util.I18N;
import binnie.genetics.api.IGene;
import binnie.genetics.api.IItemSerum;
import binnie.genetics.genetics.Engineering;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IGenome;
import forestry.api.genetics.IIndividual;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class SplicerComponentLogic extends ComponentProcessSetCost implements IProcess {
    public int nOfGenes;

    public SplicerComponentLogic(Machine machine) {
        super(machine, Splicer.RF_COST, Splicer.TIME_PERIOD);
        nOfGenes = 0;
    }

    @Override
    public int getProcessLength() {
        float n = getNumberOfGenes();
        if (n > 1.0f) {
            n = 1.0f + (n - 1.0f) * 0.5f;
        }
        // Fix for / by 0
        int temp = (int) (super.getProcessLength() * n);
        return temp != 0 ? temp : 1;
    }

    @Override
    public int getProcessEnergy() {
        float n = getNumberOfGenes();
        if (n > 1.0f) {
            n = 1.0f + (n - 1.0f) * 0.5f;
        }
        return (int) (super.getProcessEnergy() * n);
    }

    @Override
    public void onInventoryUpdate() {
        super.onInventoryUpdate();
        nOfGenes = getGenesToUse();
    }

    protected int getGenesToUse() {
        ItemStack serum = getUtil().getStack(Splicer.SLOT_SERUM_VIAL);
        ItemStack target = getUtil().getStack(Splicer.SLOT_TARGET);
        if (serum == null || target == null) {
            return 1;
        }

        IIndividual ind = AlleleManager.alleleRegistry.getIndividual(target);
        IGene[] genes = ((IItemSerum) serum.getItem()).getGenes(serum);
        if (ind.getGenome().getSpeciesRoot() != ((IItemSerum) serum.getItem()).getSpeciesRoot(serum)) {
            return 1;
        }

        int i = 0;
        for (IGene gene : genes) {
            if (ind.getGenome().getActiveAllele(gene.getChromosome()) != gene.getAllele()
                    || ind.getGenome().getInactiveAllele(gene.getChromosome()) != gene.getAllele()) {
                i++;
            }
        }
        return (i < 1) ? 1 : i;
    }

    private int getFullNumberOfGenes() {
        ItemStack serum = getUtil().getStack(Splicer.SLOT_SERUM_VIAL);
        if (serum == null) {
            return 1;
        }
        return Engineering.getGenes(serum).length;
    }

    private int getNumberOfGenes() {
        return nOfGenes;
    }

    @Override
    public String getTooltip() {
        int n = getNumberOfGenes();
        int f = getFullNumberOfGenes();
        if (f > 1) {
            if (n > 1) {
                return I18N.localise("genetics.machine.splicer.splicingIn.0", n, f);
            }
            return I18N.localise("genetics.machine.splicer.splicingIn.1", n, f);
        }

        if (n > 1) {
            return I18N.localise("genetics.machine.splicer.splicingIn.2", n);
        }
        return I18N.localise("genetics.machine.splicer.splicingIn.3", n);
    }

    @Override
    public ErrorState canWork() {
        if (getUtil().isSlotEmpty(Splicer.SLOT_TARGET)) {
            return new ErrorState.NoItem(
                    I18N.localise("genetics.machine.splicer.error.noIndividual"), Splicer.SLOT_TARGET);
        }
        if (getUtil().isSlotEmpty(Splicer.SLOT_SERUM_VIAL)) {
            return new ErrorState.NoItem(
                    I18N.localise("genetics.machine.splicer.error.noSerum"), Splicer.SLOT_SERUM_VIAL);
        }

        ErrorState state = isValidSerum();
        if (state != null) {
            return state;
        }

        if (getUtil().getStack(0) != null && Engineering.getCharges(getUtil().getStack(Splicer.SLOT_SERUM_VIAL)) == 0) {
            return new ErrorState(
                    I18N.localise("genetics.machine.splicer.error.emptySerum.title"),
                    I18N.localise("genetics.machine.splicer.error.emptySerum"));
        }
        return super.canWork();
    }

    public ErrorState isValidSerum() {
        ItemStack serum = getUtil().getStack(Splicer.SLOT_SERUM_VIAL);
        ItemStack target = getUtil().getStack(Splicer.SLOT_TARGET);
        IGene[] genes = Engineering.getGenes(serum);
        if (genes.length == 0) {
            return new ErrorState(
                    I18N.localise("genetics.machine.splicer.error.invalidSerum.title"),
                    I18N.localise("genetics.machine.splicer.error.invalidSerum.0"));
        }
        if (!genes[0].getSpeciesRoot().isMember(target)) {
            return new ErrorState(
                    I18N.localise("genetics.machine.splicer.error.invalidSerum.title"),
                    I18N.localise("genetics.machine.splicer.error.invalidSerum.1"));
        }

        IIndividual individual = genes[0].getSpeciesRoot().getMember(target);
        boolean hasAll = true;
        for (IGene gene : genes) {
            if (hasAll) {
                IGenome genome = individual.getGenome();
                IChromosomeType chromosome = gene.getChromosome();
                String geneAlleleUID = gene.getAllele().getUID();
                IAllele a = genome.getActiveAllele(chromosome);
                IAllele b = genome.getInactiveAllele(chromosome);
                hasAll = a.getUID().equals(geneAlleleUID) && b.getUID().equals(geneAlleleUID);
            }
        }

        if (hasAll) {
            return new ErrorState(
                    I18N.localise("genetics.machine.splicer.error.defunctSerum.title"),
                    I18N.localise("genetics.machine.splicer.error.defunctSerum"));
        }
        return null;
    }

    @Override
    public ErrorState canProgress() {
        return super.canProgress();
    }

    @Override
    protected void onFinishTask() {
        super.onFinishTask();
        ItemStack serum = getUtil().getStack(Splicer.SLOT_SERUM_VIAL);
        ItemStack target = getUtil().getStack(Splicer.SLOT_TARGET);
        IIndividual ind = AlleleManager.alleleRegistry.getIndividual(target);
        if (!ind.isAnalyzed()) {
            ind.analyze();
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            ind.writeToNBT(nbttagcompound);
            target.setTagCompound(nbttagcompound);
        }

        IGene[] genes = ((IItemSerum) serum.getItem()).getGenes(serum);
        for (IGene gene : genes) {
            Splicer.setGene(gene, target, 0);
            Splicer.setGene(gene, target, 1);
        }
        getUtil().damageItem(Splicer.SLOT_SERUM_VIAL, 1);
    }

    @Override
    protected void onTickTask() {
        // ignored
    }
}
