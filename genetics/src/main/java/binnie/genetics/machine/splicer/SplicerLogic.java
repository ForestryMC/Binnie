package binnie.genetics.machine.splicer;

import com.google.common.base.Preconditions;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;

import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IIndividual;

import binnie.core.api.genetics.IGene;
import binnie.core.machines.Machine;
import binnie.core.machines.errors.ErrorState;
import binnie.core.machines.power.ComponentProcess;
import binnie.core.machines.power.IProcess;
import binnie.genetics.api.IItemSerum;
import binnie.genetics.config.ConfigurationMain;
import binnie.genetics.genetics.Engineering;
import binnie.genetics.machine.GeneticsErrorCode;

public class SplicerLogic extends ComponentProcess implements IProcess {
	public static final int PROCESS_ENERGY = ConfigurationMain.splicerEnergy;
	public static final int PROCESS_LENGTH = ConfigurationMain.splicerTime;
	private int nOfGenes;

	public SplicerLogic(final Machine machine) {
		super(machine);
		this.nOfGenes = 0;
	}

	public static int getProcessLength(int numberOfGenes) {
		float n = numberOfGenes;
		if (n > 1.0f) {
			n = 1.0f + (n - 1.0f) * 0.5f;
		}
		// Fix for / by 0
		int temp = (int) (PROCESS_LENGTH * n);
		return temp != 0 ? temp : 1;
	}

	public static int getProcessEnergy(int numberOfGenes) {
		float n = numberOfGenes;
		if (n > 1.0f) {
			n = 1.0f + (n - 1.0f) * 0.5f;
		}
		return (int) (PROCESS_ENERGY * n);
	}

	public static int getGenesToUse(ItemStack serum, ItemStack target) {
		if (serum.isEmpty() || target.isEmpty()) {
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

	@Override
	public int getProcessLength() {
		int n = this.getNumberOfGenes();
		return getProcessLength(n);
	}

	@Override
	public int getProcessEnergy() {
		int n = this.getNumberOfGenes();
		return getProcessEnergy(n);
	}

	@Override
	public void onInventoryUpdate() {
		super.onInventoryUpdate();
		final ItemStack serum = this.getUtil().getStack(0);
		final ItemStack target = this.getUtil().getStack(9);
		this.nOfGenes = getGenesToUse(serum, target);
	}

	private int getFullNumberOfGenes() {
		final ItemStack serum = this.getUtil().getStack(0);
		if (serum.isEmpty()) {
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

	@Override
	public ErrorState canWork() {
		if (this.getUtil().isSlotEmpty(Splicer.SLOT_TARGET)) {
			return new ErrorState(GeneticsErrorCode.NO_INDIVIDUAL, Splicer.SLOT_TARGET);
		}

		ItemStack serum = this.getUtil().getStack(Splicer.SLOT_SERUM_VIAL);
		if (serum.isEmpty()) {
			return new ErrorState(GeneticsErrorCode.NO_SERUM, Splicer.SLOT_SERUM_VIAL);
		}

		final ErrorState state = this.isValidSerum();
		if (state != null) {
			return state;
		}

		if (Engineering.getCharges(serum) == 0) {
			return new ErrorState(GeneticsErrorCode.EMPTY_SERUM);
		}
		return super.canWork();
	}

	@Nullable
	public ErrorState isValidSerum() {
		final ItemStack serum = this.getUtil().getStack(Splicer.SLOT_SERUM_VIAL);
		if (serum.isEmpty()) {
			return null;
		}
		final ItemStack target = this.getUtil().getStack(Splicer.SLOT_TARGET);
		final IGene[] genes = Engineering.getGenes(serum);
		if (genes.length == 0) {
			return new ErrorState(GeneticsErrorCode.INVALID_SERUM_NO);
		}
		if (!genes[0].getSpeciesRoot().isMember(target)) {
			return new ErrorState(GeneticsErrorCode.INVALID_SERUM_MISMATCH);
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
			return new ErrorState(GeneticsErrorCode.DEFUNCT_SERUM);
		}
		return null;
	}

	@Override
	protected void onFinishTask() {
		super.onFinishTask();
		final ItemStack serum = this.getUtil().getStack(Splicer.SLOT_SERUM_VIAL);
		Preconditions.checkState(!serum.isEmpty());
		final ItemStack target = this.getUtil().getStack(Splicer.SLOT_TARGET);
		Preconditions.checkState(!target.isEmpty());
		final IGene[] genes = ((IItemSerum) serum.getItem()).getGenes(serum);
		for (final IGene gene : genes) {
			Splicer.setGene(gene, target, true, true);
		}
		this.getUtil().damageItem(serum, Splicer.SLOT_SERUM_VIAL, 1);
	}
}
