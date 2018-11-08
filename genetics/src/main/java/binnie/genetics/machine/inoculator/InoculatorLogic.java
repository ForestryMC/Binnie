package binnie.genetics.machine.inoculator;

import javax.annotation.Nullable;

import binnie.genetics.config.ConfigurationMain;
import binnie.genetics.machine.splicer.Splicer;
import net.minecraft.item.ItemStack;

import forestry.api.genetics.IAllele;
import forestry.api.genetics.IGenome;
import forestry.api.genetics.IIndividual;

import binnie.core.machines.Machine;
import binnie.core.machines.MachineUtil;
import binnie.core.machines.errors.ErrorState;
import binnie.core.machines.power.ComponentProcess;
import binnie.core.machines.power.IProcess;
import binnie.core.util.I18N;
import binnie.core.api.genetics.IGene;
import binnie.genetics.api.IItemSerum;
import binnie.genetics.genetics.Engineering;
import binnie.genetics.machine.GeneticsErrorCode;

public class InoculatorLogic extends ComponentProcess implements IProcess {
	public static final int PROCESS_BASE_LENGTH = ConfigurationMain.inoculatorTime;
	public static final int PROCESS_BASE_ENERGY = ConfigurationMain.inoculatorEnergy;
	public static final int BACTERIA_PER_PROCESS = 15;
	private float bacteriaDrain;

	public InoculatorLogic(final Machine machine) {
		super(machine);
		this.bacteriaDrain = 0.0f;
	}

	public static int getProcessLength(ItemStack itemStack) {
		return PROCESS_BASE_LENGTH * getNumberOfGenes(itemStack);
	}

	public static int getProcessBaseEnergy(ItemStack itemStack) {
		return PROCESS_BASE_ENERGY * getNumberOfGenes(itemStack);
	}

	private static int getNumberOfGenes(ItemStack serum) {
		if (serum.isEmpty()) {
			return 1;
		}
		return Engineering.getGenes(serum).length;
	}

	public static ItemStack applySerum(ItemStack target, ItemStack serum) {
		ItemStack applied = target.copy();
		final IGene[] genes = ((IItemSerum) serum.getItem()).getGenes(serum);
		for (final IGene gene : genes) {
			Splicer.setGene(gene, applied, true, true);
		}
		return applied;
	}

	@Override
	public int getProcessLength() {
		ItemStack stack = this.getUtil().getStack(0);
		return getProcessLength(stack);
	}

	@Override
	public int getProcessEnergy() {
		ItemStack stack = this.getUtil().getStack(0);
		return getProcessBaseEnergy(stack);
	}

	@Override
	public String getTooltip() {
		ItemStack stack = this.getUtil().getStack(0);
		int n = getNumberOfGenes(stack);
		if (n > 1) {
			return String.format(I18N.localise("genetics.machine.inoculator.tooltips.logic.genes"), Integer.valueOf(n).toString());
		} else {
			return I18N.localise("genetics.machine.inoculator.tooltips.logic.gene");
		}
	}

	@Override
	public ErrorState canWork() {
		if (this.getUtil().isSlotEmpty(Inoculator.SLOT_TARGET)) {
			return new ErrorState(GeneticsErrorCode.NO_INDIVIDUAL, Inoculator.SLOT_TARGET);
		}
		if (this.getUtil().isSlotEmpty(Inoculator.SLOT_SERUM_VIAL)) {
			return new ErrorState(GeneticsErrorCode.NO_SERUM, Inoculator.SLOT_SERUM_VIAL);
		}
		final ErrorState state = this.isValidSerum();
		if (state != null) {
			return state;
		}
		ItemStack serum = this.getUtil().getStack(Inoculator.SLOT_SERUM_VIAL);
		if (!serum.isEmpty() && Engineering.getCharges(serum) == 0) {
			return new ErrorState(GeneticsErrorCode.EMPTY_SERUM, Inoculator.SLOT_SERUM_VIAL);
		}
		if (getUtil().isTankEmpty(Inoculator.TANK_VEKTOR)) {
			return new ErrorState(GeneticsErrorCode.INOCULATOR_INSUFFICIENT_VECTOR, Inoculator.TANK_VEKTOR);
		}
		return super.canWork();
	}

	@Nullable
	public ErrorState isValidSerum() {
		final ItemStack serum = this.getUtil().getStack(Inoculator.SLOT_SERUM_VIAL);
		final ItemStack target = this.getUtil().getStack(Inoculator.SLOT_TARGET);
		final IGene[] genes = Engineering.getGenes(serum);
		if (genes.length == 0) {
			return new ErrorState(GeneticsErrorCode.INVALID_SERUM_NO);
		}
		if (!genes[0].getSpeciesRoot().isMember(target)) {
			return new ErrorState(GeneticsErrorCode.INVALID_SERUM_MISMATCH);
		}
		final IIndividual individual = genes[0].getSpeciesRoot().getMember(target);
		if (individual != null) {
			final IGenome genome = individual.getGenome();
			for (final IGene gene : genes) {
				final IAllele a = genome.getActiveAllele(gene.getChromosome());
				final IAllele b = genome.getInactiveAllele(gene.getChromosome());
				if (!a.getUID().equals(gene.getAllele().getUID()) || !b.getUID().equals(gene.getAllele().getUID())) {
					return null;
				}
			}
		}
		return new ErrorState(GeneticsErrorCode.DEFUNCT_SERUM);
	}

	@Override
	protected void onFinishTask() {
		super.onFinishTask();
		MachineUtil util = this.getUtil();

		final ItemStack serum = util.getStack(Inoculator.SLOT_SERUM_VIAL);
		final ItemStack target = util.getStack(Inoculator.SLOT_TARGET);

		ItemStack applied = applySerum(target, serum);
		util.setStack(Inoculator.SLOT_TARGET, applied);
		util.damageItem(serum, Inoculator.SLOT_SERUM_VIAL, 1);
	}

	@Override
	protected void onTickTask() {
		this.bacteriaDrain += BACTERIA_PER_PROCESS / (float) this.getProcessLength();
		if (this.bacteriaDrain >= 1.0f) {
			this.getUtil().drainTank(Inoculator.TANK_VEKTOR, 1);
			--this.bacteriaDrain;
		}
	}
}