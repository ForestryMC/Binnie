package binnie.genetics.machine.genepool;

import binnie.Binnie;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.IChargedSlots;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.genetics.Genetics;
import binnie.genetics.item.GeneticLiquid;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.item.ItemStack;

public class GenepoolLogic extends ComponentProcessSetCost implements IProcess {
	float enzymePerProcess;
	float ethanolPerProcess;
	private float ethanolDrain;

	public GenepoolLogic(final Machine machine) {
		super(machine, 8000, 400);
		this.enzymePerProcess = 0.25f;
		this.ethanolPerProcess = 50.0f;
		this.ethanolDrain = 0.0f;
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
		if (!this.getUtil().spaceInTank(Genepool.TANK_DNA, this.getDNAAmount(this.getUtil().getStack(0)))) {
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
		final int amount = this.getDNAAmount(this.getUtil().getStack(Genepool.SLOT_BEE));
		this.getUtil().fillTank(Genepool.TANK_DNA, GeneticLiquid.RawDNA.get(amount));
		this.getUtil().deleteStack(Genepool.SLOT_BEE);
	}

	private int getDNAAmount(final ItemStack stack) {
		final ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(stack);
		if (root == null) {
			return 10;
		}
		if (root != Binnie.Genetics.getBeeRoot()) {
			return 10;
		}
		if (Binnie.Genetics.getBeeRoot().isDrone(stack)) {
			return 10;
		}
		if (Binnie.Genetics.getBeeRoot().isMated(stack)) {
			return 50;
		}
		return 30;
	}

	@Override
	protected void onTickTask() {
		this.ethanolDrain += this.getDNAAmount(this.getUtil().getStack(Genepool.SLOT_BEE)) * 1.2f * this.getProgressPerTick() / 100.0f;
		if (this.ethanolDrain >= 1.0f) {
			this.getUtil().drainTank(Genepool.TANK_ETHANOL, 1);
			--this.ethanolDrain;
		}
		this.getMachine().getInterface(IChargedSlots.class).alterCharge(Genepool.SLOT_ENZYME, -this.enzymePerProcess * this.getProgressPerTick() / 100.0f);
	}
}
