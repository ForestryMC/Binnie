package binnie.genetics.machine.splicer;

import binnie.core.machines.Machine;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.genetics.Genetics;
import binnie.genetics.api.IGene;
import binnie.genetics.api.IItemSerum;
import binnie.genetics.genetics.Engineering;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IIndividual;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class SplicerLogic extends ComponentProcessSetCost implements IProcess {
	int nOfGenes;

	@Override
	public int getProcessLength() {
		float n = this.getNumberOfGenes();
		if (n > 1.0f) {
			n = 1.0f + (n - 1.0f) * 0.5f;
		}
		// Fix for / by 0
		int temp = (int) (super.getProcessLength() * n);
		return temp != 0 ? temp : 1;
	}

	@Override
	public int getProcessEnergy() {
		float n = this.getNumberOfGenes();
		if (n > 1.0f) {
			n = 1.0f + (n - 1.0f) * 0.5f;
		}
		return (int) (super.getProcessEnergy() * n);
	}

	@Override
	public void onInventoryUpdate() {
		super.onInventoryUpdate();
		this.nOfGenes = this.getGenesToUse();
	}

	protected int getGenesToUse() {
		final ItemStack serum = this.getUtil().getStack(0);
		final ItemStack target = this.getUtil().getStack(9);
		if (serum == null || target == null) {
			return 1;
		}
		final IIndividual ind = AlleleManager.alleleRegistry.getIndividual(target);
		final IGene[] genes = ((IItemSerum) serum.getItem()).getGenes(serum);
		if (ind.getGenome().getSpeciesRoot() != ((IItemSerum) serum.getItem()).getSpeciesRoot(serum)) {
			return 1;
		}
		int i = 0;
		for (final IGene gene : genes) {
			if (ind.getGenome().getActiveAllele(gene.getChromosome()) != gene.getAllele() || ind.getGenome().getInactiveAllele(gene.getChromosome()) != gene.getAllele()) {
				++i;
			}
		}
		return (i < 1) ? 1 : i;
	}

	private int getFullNumberOfGenes() {
		final ItemStack serum = this.getUtil().getStack(0);
		if (serum == null) {
			return 1;
		}
		return Engineering.getGenes(serum).length;
	}

	private int getNumberOfGenes() {
		return this.nOfGenes;
	}

	@Override
	public String getTooltip() {
		final int n = this.getNumberOfGenes();
		final int f = this.getFullNumberOfGenes();
		return "Splicing in " + n + ((f > 1) ? ("/" + f) : "") + " gene" + ((n > 1) ? "s" : "");
	}

	public SplicerLogic(final Machine machine) {
		super(machine, 12000000, 1200);
		this.nOfGenes = 0;
	}

	@Override
	public ErrorState canWork() {
		if (this.getUtil().isSlotEmpty(Splicer.SLOT_TARGET)) {
			return new ErrorState.NoItem(Genetics.proxy.localise("machine.advMachine.splicer.errors.no.individual.desc"), Splicer.SLOT_TARGET);
		}
		if (this.getUtil().isSlotEmpty(Splicer.SLOT_SERUM_VIAL)) {
			return new ErrorState.NoItem(Genetics.proxy.localise("machine.errors.no.serum.desc"), Splicer.SLOT_SERUM_VIAL);
		}
		final ErrorState state = this.isValidSerum();
		if (state != null) {
			return state;
		}
		if (this.getUtil().getStack(Splicer.SLOT_SERUM_VIAL) != null && Engineering.getCharges(this.getUtil().getStack(Splicer.SLOT_SERUM_VIAL)) == 0) {
			return new ErrorState(Genetics.proxy.localise("machine.errors.empty.serum.desc"), Genetics.proxy.localise("machine.errors.empty.serum.info"));
		}
		return super.canWork();
	}

	public ErrorState isValidSerum() {
		final ItemStack serum = this.getUtil().getStack(Splicer.SLOT_SERUM_VIAL);
		final ItemStack target = this.getUtil().getStack(Splicer.SLOT_TARGET);
		final IGene[] genes = Engineering.getGenes(serum);
		if (genes.length == 0) {
			return new ErrorState(Genetics.proxy.localise("machine.errors.invalid.serum.desc"), Genetics.proxy.localise("machine.errors.invalid.serum.no.genes.info"));
		}
		if (!genes[0].getSpeciesRoot().isMember(target)) {
			return new ErrorState(Genetics.proxy.localise("machine.errors.invalid.serum.desc"), Genetics.proxy.localise("machine.errors.invalid.serum.mismatch.info"));
		}
		final IIndividual individual = genes[0].getSpeciesRoot().getMember(target);
		boolean hasAll = true;
		for (final IGene gene : genes) {
			if (hasAll) {
				final IAllele a = individual.getGenome().getActiveAllele(gene.getChromosome());
				final IAllele b = individual.getGenome().getInactiveAllele(gene.getChromosome());
				hasAll = (hasAll && a.getUID().equals(gene.getAllele().getUID()) && b.getUID().equals(gene.getAllele().getUID()));
			}
		}
		if (hasAll) {
			return new ErrorState(Genetics.proxy.localise("genetics.machine.errors.defunct.serum.desc"), Genetics.proxy.localise("machine.errors.defunct.serum.info"));
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
		final ItemStack serum = this.getUtil().getStack(Splicer.SLOT_SERUM_VIAL);
		final ItemStack target = this.getUtil().getStack(Splicer.SLOT_TARGET);
		final IIndividual ind = AlleleManager.alleleRegistry.getIndividual(target);
		if (!ind.isAnalyzed()) {
			ind.analyze();
			final NBTTagCompound nbttagcompound = new NBTTagCompound();
			ind.writeToNBT(nbttagcompound);
			target.setTagCompound(nbttagcompound);
		}
		final IGene[] arr$;
		final IGene[] genes = arr$ = ((IItemSerum) serum.getItem()).getGenes(serum);
		for (final IGene gene : arr$) {
			Splicer.setGene(gene, target, 0);
			Splicer.setGene(gene, target, 1);
		}
		this.getUtil().damageItem(Splicer.SLOT_SERUM_VIAL, 1);
	}

	@Override
	protected void onTickTask() {
	}
}
