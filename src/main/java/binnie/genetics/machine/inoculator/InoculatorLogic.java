package binnie.genetics.machine.inoculator;

import binnie.core.machines.Machine;
import binnie.core.machines.MachineUtil;
import binnie.core.machines.power.ComponentProcess;
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
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;

public class InoculatorLogic extends ComponentProcess implements IProcess {
	public static final int PROCESS_BASE_LENGTH = 12000;
	public static final int PROCESS_BASE_ENERGY = 600000;
	public static final int BACTERIA_PER_PROCESS = 15;

	public static int getProcessLength(ItemStack itemStack) {
		return PROCESS_BASE_LENGTH * getNumberOfGenes(itemStack);
	}

	public static int getProcessBaseEnergy(ItemStack itemStack) {
		return PROCESS_BASE_ENERGY * getNumberOfGenes(itemStack);
	}

	private static int getNumberOfGenes(@Nullable ItemStack serum) {
		if (serum == null) {
			return 1;
		}
		return Engineering.getGenes(serum).length;
	}

	private float bacteriaDrain;

	public InoculatorLogic(final Machine machine) {
		super(machine);
		this.bacteriaDrain = 0.0f;
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
			return String.format(Genetics.proxy.localise("genetics.machine.machine.inoculator.tooltips.logic.genes"), Integer.valueOf(n).toString());
		} else {
			return Genetics.proxy.localise("genetics.machine.machine.inoculator.tooltips.logic.gene");
		}
	}

	@Override
	public ErrorState canWork() {
		if (this.getUtil().isSlotEmpty(Inoculator.SLOT_TARGET)) {
			return new ErrorState.NoItem(Genetics.proxy.localise("machine.machine.inoculator.errors.no.individual.desc"), Inoculator.SLOT_TARGET);
		}
		if (this.getUtil().isSlotEmpty(Inoculator.SLOT_SERUM_VIAL)) {
			return new ErrorState.NoItem(Genetics.proxy.localise("machine.errors.no.serum.desc"), Inoculator.SLOT_SERUM_VIAL);
		}
		final ErrorState state = this.isValidSerum();
		if (state != null) {
			return state;
		}
		if (this.getUtil().getStack(Inoculator.SLOT_SERUM_VIAL) != null && Engineering.getCharges(this.getUtil().getStack(Inoculator.SLOT_SERUM_VIAL)) == 0) {
			return new ErrorState(Genetics.proxy.localise("machine.errors.empty.serum.desc"), Genetics.proxy.localise("machine.errors.empty.serum.info"));
		}
		return super.canWork();
	}

	public ErrorState isValidSerum() {
		final ItemStack serum = this.getUtil().getStack(Inoculator.SLOT_SERUM_VIAL);
		final ItemStack target = this.getUtil().getStack(Inoculator.SLOT_TARGET);
		final IGene[] genes = Engineering.getGenes(serum);
		if (genes.length == 0) {
			return new ErrorState(Genetics.proxy.localise("machine.errors.invalid.serum.desc"), Genetics.proxy.localise("machine.errors.invalid.serum.no.genes.info"));
		}
		if (!genes[0].getSpeciesRoot().isMember(target)) {
			return new ErrorState(Genetics.proxy.localise("machine.errors.invalid.serum.desc"), Genetics.proxy.localise("machine.errors.invalid.serum.mismatch.info"));
		}
		final IIndividual individual = genes[0].getSpeciesRoot().getMember(target);
		for (final IGene gene : genes) {
			final IAllele a = individual.getGenome().getActiveAllele(gene.getChromosome());
			final IAllele b = individual.getGenome().getInactiveAllele(gene.getChromosome());
			if (!a.getUID().equals(gene.getAllele().getUID()) || !b.getUID().equals(gene.getAllele().getUID())) {
				return null;
			}
		}
		return new ErrorState(Genetics.proxy.localise("genetics.machine.errors.defunct.serum.desc"), Genetics.proxy.localise("genetics.machine.errors.defunct.serum.info"));
	}

	@Override
	public ErrorState canProgress() {
		return super.canProgress();
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

	public static ItemStack applySerum(ItemStack target, ItemStack serum) {
		ItemStack applied = target.copy();
		final IIndividual ind = AlleleManager.alleleRegistry.getIndividual(applied);
		if (!ind.isAnalyzed()) {
			ind.analyze();
			final NBTTagCompound nbttagcompound = new NBTTagCompound();
			ind.writeToNBT(nbttagcompound);
			applied.setTagCompound(nbttagcompound);
		}
		final IGene[] genes = ((IItemSerum) serum.getItem()).getGenes(serum);
		for (final IGene gene : genes) {
			Inoculator.setGene(gene, applied, 0);
			Inoculator.setGene(gene, applied, 1);
		}
		if (applied.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
			applied.setItemDamage(0);
		}
		return applied;
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