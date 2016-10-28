package binnie.genetics.machine.splicer;

import binnie.core.machines.Machine;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
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
		if (this.getUtil().isSlotEmpty(9)) {
			return new ErrorState.NoItem("No Individual to Splice", 9);
		}
		if (this.getUtil().isSlotEmpty(0)) {
			return new ErrorState.NoItem("No Serum", 0);
		}
		final ErrorState state = this.isValidSerum();
		if (state != null) {
			return state;
		}
		if (this.getUtil().getStack(0) != null && Engineering.getCharges(this.getUtil().getStack(0)) == 0) {
			return new ErrorState("Empty Serum", "Serum is empty");
		}
		return super.canWork();
	}

	public ErrorState isValidSerum() {
		final ItemStack serum = this.getUtil().getStack(0);
		final ItemStack target = this.getUtil().getStack(9);
		final IGene[] genes = Engineering.getGenes(serum);
		if (genes.length == 0) {
			return new ErrorState("Invalid Serum", "Serum does not hold any genes");
		}
		if (!genes[0].getSpeciesRoot().isMember(target)) {
			return new ErrorState("Invalid Serum", "Mismatch of Serum Type and Target");
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
			return new ErrorState("Defunct Serum", "Individual already possesses this allele");
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
		final ItemStack serum = this.getUtil().getStack(0);
		final ItemStack target = this.getUtil().getStack(9);
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
		this.getUtil().damageItem(0, 1);
	}

	@Override
	protected void onTickTask() {
	}
}
