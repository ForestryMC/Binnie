package binnie.genetics.machine.isolator;

import java.util.Random;

import binnie.core.genetics.Gene;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.IChargedSlots;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.genetics.Genetics;
import binnie.genetics.item.ItemSequence;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.item.ItemStack;

public class IsolatorLogic extends ComponentProcessSetCost implements IProcess {
	float enzymePerProcess;
	float ethanolPerProcess;

	public IsolatorLogic(final Machine machine) {
		super(machine, 192000, 4800);
		this.enzymePerProcess = 0.5f;
		this.ethanolPerProcess = 10.0f;
	}

	@Override
	public ErrorState canWork() {
		if (this.getUtil().isSlotEmpty(Isolator.SLOT_TARGET)) {
			return new ErrorState.NoItem(Genetics.proxy.localise("machine.machine.isolator.errors.no.individual.desc"), Isolator.SLOT_TARGET);
		}
		if (!this.getUtil().isSlotEmpty(Isolator.SLOT_RESULUT)) {
			return new ErrorState.NoSpace(Genetics.proxy.localise("machine.machine.isolator.errors.no.room.desc"), Isolator.SLOT_FINISHED);
		}
		if (this.getUtil().isSlotEmpty(Isolator.SLOT_SEQUENCER_VIAL)) {
			return new ErrorState.NoItem(Genetics.proxy.localise("machine.machine.isolator.errors.no.empty.sequencer.desc"), Isolator.SLOT_SEQUENCER_VIAL);
		}
		return super.canWork();
	}

	@Override
	public ErrorState canProgress() {
		if (!this.getUtil().liquidInTank(Isolator.TANK_ETHANOL, (int) this.ethanolPerProcess)) {
			return new ErrorState.InsufficientLiquid(Genetics.proxy.localise("machine.machine.isolator.errors.insufficient.ethanol.desc"), Isolator.TANK_ETHANOL);
		}
		if (this.getUtil().getSlotCharge(Isolator.SLOT_ENZYME) == 0.0f) {
			return new ErrorState.NoItem(Genetics.proxy.localise("machine.errors.no.enzyme.desc"), Isolator.SLOT_ENZYME);
		}
		return super.canProgress();
	}

	@Override
	protected void onFinishTask() {
		super.onFinishTask();
		final Random rand = this.getMachine().getWorld().rand;
		final ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(this.getUtil().getStack(Isolator.SLOT_TARGET));
		if (root == null) {
			return;
		}
		final IIndividual individual = root.getMember(this.getUtil().getStack(Isolator.SLOT_TARGET));
		if (individual == null) {
			return;
		}
		IAllele allele = null;
		IChromosomeType chromosome = null;
		Gene gene = null;
		if (this.getMachine().getWorld().rand.nextFloat() < 2.0f) {
			while (gene == null) {
				try {
					chromosome = root.getKaryotype()[rand.nextInt(root.getKaryotype().length)];
					allele = (rand.nextBoolean() ? individual.getGenome().getActiveAllele(chromosome) : individual.getGenome().getInactiveAllele(chromosome));
					gene = Gene.create(allele, chromosome, root);
				} catch (Exception e) {
				}
			}
		}
		final ItemStack serum = ItemSequence.create(gene);
		this.getUtil().setStack(Isolator.SLOT_RESULUT, serum);
		this.getUtil().decreaseStack(Isolator.SLOT_SEQUENCER_VIAL, 1);
		if (rand.nextFloat() < 0.05f) {
			this.getUtil().decreaseStack(Isolator.SLOT_TARGET, 1);
		}
		this.getUtil().drainTank(Isolator.TANK_ETHANOL, (int) this.ethanolPerProcess);
	}

	@Override
	protected void onTickTask() {
		this.getMachine().getInterface(IChargedSlots.class).alterCharge(Isolator.SLOT_ENZYME, -this.enzymePerProcess * this.getProgressPerTick() / 100.0f);
	}
}
