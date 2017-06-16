package binnie.genetics.machine.genepool;

import net.minecraft.item.ItemStack;

import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.ISpeciesRoot;

import binnie.Binnie;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.IChargedSlots;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.genetics.Genetics;
import binnie.genetics.item.GeneticLiquid;

public class GenepoolLogic extends ComponentProcessSetCost implements IProcess {
	public static final float ENZYME_PER_PROCESS = 0.25f;
	private float ethanolDrain;

	public GenepoolLogic(final Machine machine) {
		super(machine, 8000, 400);
		this.ethanolDrain = 0.0f;
	}

	public static int getDNAAmount(final ItemStack stack) {
		final ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(stack);

		if (root == Binnie.GENETICS.getBeeRoot()) {
			if (Binnie.GENETICS.getBeeRoot().isDrone(stack)) {
				return 10;
			}
			if (Binnie.GENETICS.getBeeRoot().isMated(stack)) {
				return 50;
			}
			return 30;
		}

		return 10;
	}

	@Override
	public ErrorState canWork() {
		if (this.getUtil().isSlotEmpty(Genepool.SLOT_BEE)) {
			return new ErrorState.NoItem(Genetics.proxy.localise("machine.errors.no.individual.desc"), Genepool.SLOT_BEE);
		}
		return super.canWork();
	}

	@Override
	public ErrorState canProgress() {
		ItemStack individual = this.getUtil().getStack(Genepool.SLOT_BEE);
		if (!this.getUtil().spaceInTank(Genepool.TANK_DNA, getDNAAmount(individual))) {
			return new ErrorState.NoSpace(Genetics.proxy.localise("machine.errors.tanks.no.room.desc"), new int[]{0});
		}
		if (!this.getUtil().liquidInTank(Genepool.TANK_ETHANOL, 1)) {
			return new ErrorState.InsufficientLiquid(Genetics.proxy.localise("machine.labMachine.genepool.errors.not.enough.ethanol.desc"), Genepool.TANK_ETHANOL);
		}
		if (this.getUtil().getSlotCharge(Genepool.SLOT_ENZYME) == 0.0f) {
			return new ErrorState.NoItem(Genetics.proxy.localise("machine.labMachine.genepool.errors.insufficient.enzyme.desc"), Genepool.SLOT_ENZYME);
		}
		return super.canProgress();
	}

	@Override
	protected void onFinishTask() {
		super.onFinishTask();
		ItemStack individual = this.getUtil().getStack(Genepool.SLOT_BEE);
		final int amount = getDNAAmount(individual);
		this.getUtil().fillTank(Genepool.TANK_DNA, GeneticLiquid.RawDNA.get(amount));
		this.getUtil().deleteStack(Genepool.SLOT_BEE);
	}

	@Override
	protected void onTickTask() {
		ItemStack individual = this.getUtil().getStack(Genepool.SLOT_BEE);
		this.ethanolDrain += getDNAAmount(individual) * 1.2f * this.getProgressPerTick() / 100.0f;
		if (this.ethanolDrain >= 1.0f) {
			this.getUtil().drainTank(Genepool.TANK_ETHANOL, 1);
			--this.ethanolDrain;
		}
		IChargedSlots chargedSlots = this.getMachine().getInterface(IChargedSlots.class);
		chargedSlots.alterCharge(Genepool.SLOT_ENZYME, -ENZYME_PER_PROCESS * this.getProgressPerTick() / 100.0f);
	}
}
