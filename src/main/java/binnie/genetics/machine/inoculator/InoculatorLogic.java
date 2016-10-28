package binnie.genetics.machine.inoculator;

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

public class InoculatorLogic extends ComponentProcessSetCost implements IProcess {
	private float bacteriaDrain;

	@Override
	public int getProcessLength() {
		return super.getProcessLength() * this.getNumberOfGenes();
	}

	@Override
	public int getProcessEnergy() {
		return super.getProcessEnergy() * this.getNumberOfGenes();
	}

	private int getNumberOfGenes() {
		final ItemStack serum = this.getUtil().getStack(0);
		if (serum == null) {
			return 1;
		}
		return Engineering.getGenes(serum).length;
	}

	@Override
	public String getTooltip() {
		final int n = this.getNumberOfGenes();
		return "Inoculating with " + n + " gene" + ((n > 1) ? "s" : "");
	}

	public InoculatorLogic(final Machine machine) {
		super(machine, 600000, 12000);
		this.bacteriaDrain = 0.0f;
	}

	@Override
	public ErrorState canWork() {
		if (this.getUtil().isSlotEmpty(Inoculator.SLOT_TARGET)) {
			return new ErrorState.NoItem(Genetics.proxy.localise("machine.machine.inoculator.no.individual"), Inoculator.SLOT_TARGET);
		}
		if (this.getUtil().isSlotEmpty(Inoculator.SLOT_SERUM_VIAL)) {
			return new ErrorState.NoItem(Genetics.proxy.localise("machine.machine.inoculator.no.serum"), Inoculator.SLOT_SERUM_VIAL);
		}
		final ErrorState state = this.isValidSerum();
		if (state != null) {
			return state;
		}
		if (this.getUtil().getStack(0) != null && Engineering.getCharges(this.getUtil().getStack(Inoculator.SLOT_SERUM_VIAL)) == 0) {
			return new ErrorState(Genetics.proxy.localise("machine.machine.inoculator.empty.serum"), Genetics.proxy.localise("machine.machine.inoculator.empty.serum.info"));
		}
		return super.canWork();
	}

	public ErrorState isValidSerum() {
		final ItemStack serum = this.getUtil().getStack(Inoculator.SLOT_SERUM_VIAL);
		final ItemStack target = this.getUtil().getStack(Inoculator.SLOT_TARGET);
		final IGene[] genes = Engineering.getGenes(serum);
		if (genes.length == 0) {
			return new ErrorState(Genetics.proxy.localise("machine.machine.inoculator.invalid.serum"), Genetics.proxy.localise("machine.machine.inoculator.invalid.serum.no.genes"));
		}
		if (!genes[0].getSpeciesRoot().isMember(target)) {
			return new ErrorState(Genetics.proxy.localise("machine.machine.inoculator.invalid.serum"), Genetics.proxy.localise("machine.machine.inoculator.invalid.serum.mismatch"));
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
		final ItemStack serum = this.getUtil().getStack(Inoculator.SLOT_SERUM_VIAL);
		final ItemStack target = this.getUtil().getStack(Inoculator.SLOT_TARGET);
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
			Inoculator.setGene(gene, target, 0);
			Inoculator.setGene(gene, target, 1);
		}
		this.getUtil().damageItem(Inoculator.SLOT_SERUM_VIAL, 1);
	}

	@Override
	protected void onTickTask() {
		this.bacteriaDrain += 15.0f * this.getProgressPerTick() / 100.0f;
		if (this.bacteriaDrain >= 1.0f) {
			this.getUtil().drainTank(Inoculator.TANK_VEKTOR, 1);
			--this.bacteriaDrain;
		}
	}
}