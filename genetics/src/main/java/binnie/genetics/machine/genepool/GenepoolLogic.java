package binnie.genetics.machine.genepool;

import net.minecraft.item.ItemStack;

import forestry.api.apiculture.BeeManager;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.ISpeciesRoot;

import binnie.core.machines.Machine;
import binnie.core.machines.MachineUtil;
import binnie.core.machines.errors.CoreErrorCode;
import binnie.core.machines.errors.ErrorState;
import binnie.core.machines.inventory.IChargedSlots;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.IProcess;
import binnie.genetics.config.ConfigurationMain;
import binnie.genetics.item.GeneticLiquid;
import binnie.genetics.machine.GeneticsErrorCode;

public class GenepoolLogic extends ComponentProcessSetCost implements IProcess {
	public static final float ENZYME_PER_PROCESS = 0.25f;
	private float ethanolDrain;

	public GenepoolLogic(final Machine machine) {
		super(machine, ConfigurationMain.genepoolEnergy, ConfigurationMain.genepoolTime);
		this.ethanolDrain = 0.0f;
	}

	public static int getDNAAmount(final ItemStack stack) {
		final ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(stack);

		if (root == BeeManager.beeRoot) {
			if (BeeManager.beeRoot.isDrone(stack)) {
				return 10;
			}
			if (BeeManager.beeRoot.isMated(stack)) {
				return 50;
			}
			return 30;
		}

		return 10;
	}

	@Override
	public ErrorState canWork() {
		if (this.getUtil().isSlotEmpty(Genepool.SLOT_BEE)) {
			return new ErrorState(GeneticsErrorCode.NO_INDIVIDUAL, Genepool.SLOT_BEE);
		}
		return super.canWork();
	}

	@Override
	public ErrorState canProgress() {
		MachineUtil util = getUtil();
		ItemStack individual = util.getStack(Genepool.SLOT_BEE);
		if (!util.spaceInTank(Genepool.TANK_DNA, getDNAAmount(individual))) {
			return new ErrorState(CoreErrorCode.NO_SPACE_TANK, new int[]{Genepool.SLOT_BEE});
		}
		if (!util.liquidInTank(Genepool.TANK_ETHANOL, 1)) {
			return new ErrorState(GeneticsErrorCode.GENEPOOL_INSUFFICIENT_ETHANOL, Genepool.TANK_ETHANOL);
		}
		if (util.getSlotCharge(Genepool.SLOT_ENZYME) == 0.0f) {
			return new ErrorState(GeneticsErrorCode.GENEPOOL_INSUFFICIENT_ENZYME, Genepool.SLOT_ENZYME);
		}
		return super.canProgress();
	}

	@Override
	protected void onFinishTask() {
		super.onFinishTask();
		MachineUtil util = getUtil();
		ItemStack individual = util.getStack(Genepool.SLOT_BEE);
		int amount = getDNAAmount(individual);
		util.fillTank(Genepool.TANK_DNA, GeneticLiquid.RawDNA.get(amount));
		util.deleteStack(Genepool.SLOT_BEE);
	}

	@Override
	protected void onTickTask() {
		MachineUtil util = getUtil();
		ItemStack individual = util.getStack(Genepool.SLOT_BEE);
		this.ethanolDrain += getDNAAmount(individual) * 1.2f * this.getProgressPerTick() / 100.0f;
		if (this.ethanolDrain >= 1.0f) {
			util.drainTank(Genepool.TANK_ETHANOL, 1);
			--this.ethanolDrain;
		}
		IChargedSlots chargedSlots = this.getMachine().getInterface(IChargedSlots.class);
		chargedSlots.alterCharge(Genepool.SLOT_ENZYME, -ENZYME_PER_PROCESS * this.getProgressPerTick() / 100.0f);
	}
}
