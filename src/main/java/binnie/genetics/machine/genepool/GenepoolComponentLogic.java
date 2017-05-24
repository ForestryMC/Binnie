package binnie.genetics.machine.genepool;

import binnie.Binnie;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.IChargedSlots;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.genetics.item.GeneticLiquid;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.item.ItemStack;

public class GenepoolComponentLogic extends ComponentProcessSetCost implements IProcess {
	float enzymePerProcess;
	float ethanolPerProcess;
	private float ethanolDrain;

	public GenepoolComponentLogic(Machine machine) {
		super(machine, 8000, 400);
		enzymePerProcess = 0.25f;
		ethanolPerProcess = 50.0f;
		ethanolDrain = 0.0f;
	}

	@Override
	public ErrorState canWork() {
		if (getUtil().isSlotEmpty(0)) {
			return new ErrorState.NoItem("No Individual", Genepool.SLOT_BEE);
		}
		return super.canWork();
	}

	@Override
	public ErrorState canProgress() {
		if (!getUtil().spaceInTank(Genepool.TANK_DNA, getDNAAmount(getUtil().getStack(Genepool.TANK_DNA)))) {
			return new ErrorState.NoSpace("Not enough room in Tank", new int[]{Genepool.TANK_DNA});
		}
		if (!getUtil().liquidInTank(1, 1)) {
			return new ErrorState.InsufficientLiquid("Not enough Ethanol", Genepool.TANK_ETHANOL);
		}
		if (getUtil().getSlotCharge(Genepool.SLOT_ENZYME) == 0.0f) {
			return new ErrorState.NoItem("Insufficient Enzyme", Genepool.SLOT_ENZYME);
		}
		return super.canProgress();
	}

	@Override
	protected void onFinishTask() {
		super.onFinishTask();
		int amount = getDNAAmount(getUtil().getStack(Genepool.TANK_DNA));
		getUtil().fillTank(Genepool.TANK_DNA, GeneticLiquid.RawDNA.get(amount));
		getUtil().deleteStack(Genepool.TANK_DNA);
	}

	private int getDNAAmount(ItemStack stack) {
		ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(stack);
		if (root == null
			|| root != Binnie.Genetics.getBeeRoot()
			|| Binnie.Genetics.getBeeRoot().isDrone(stack)) {
			return 10;
		}
		if (Binnie.Genetics.getBeeRoot().isMated(stack)) {
			return 50;
		}
		return 30;
	}

	@Override
	protected void onTickTask() {
		ethanolDrain += getDNAAmount(getUtil().getStack(Genepool.TANK_DNA)) * 1.2f * getProgressPerTick() / 100.0f;
		if (ethanolDrain >= 1.0f) {
			getUtil().drainTank(Genepool.TANK_ETHANOL, 1);
			ethanolDrain--;
		}
		getMachine().getInterface(IChargedSlots.class).alterCharge(Genepool.SLOT_ENZYME, -enzymePerProcess * getProgressPerTick() / 100.0f);
	}
}
