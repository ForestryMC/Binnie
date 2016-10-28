package binnie.genetics.machine.polymeriser;

import binnie.core.machines.Machine;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.genetics.Genetics;
import binnie.genetics.genetics.Engineering;
import net.minecraft.item.ItemStack;

public class PolymeriserLogic extends ComponentProcessSetCost implements IProcess {
	private static float chargePerProcess = 0.4f;
	private float dnaDrain;
	private float bacteriaDrain;

	public PolymeriserLogic(final Machine machine) {
		super(machine, 96000, 2400);
		this.dnaDrain = 0.0f;
		this.bacteriaDrain = 0.0f;
	}

	private float getCatalyst() {
		return (this.getUtil().getSlotCharge(Polymeriser.SLOT_GOLD) > 0.0f) ? 0.2f : 1.0f;
	}

	@Override
	public int getProcessLength() {
		return (int) (super.getProcessLength() * this.getNumberOfGenes() * this.getCatalyst());
	}

	@Override
	public int getProcessEnergy() {
		return (int) (super.getProcessEnergy() * this.getNumberOfGenes() * this.getCatalyst());
	}

	private float getDNAPerProcess() {
		return this.getNumberOfGenes() * 50;
	}

	@Override
	public void onTickTask() {
		super.onTickTask();
		this.getUtil().useCharge(Polymeriser.SLOT_GOLD, PolymeriserLogic.chargePerProcess * this.getProgressPerTick() / 100.0f);
		this.dnaDrain += this.getDNAPerProcess() * this.getProgressPerTick() / 100.0f;
		this.bacteriaDrain += 0.2f * this.getDNAPerProcess() * this.getProgressPerTick() / 100.0f;
		if (this.dnaDrain >= 1.0f) {
			this.getUtil().drainTank(Polymeriser.TANK_DNA, 1);
			--this.dnaDrain;
		}
		if (this.bacteriaDrain >= 1.0f) {
			this.getUtil().drainTank(Polymeriser.TANK_BACTERIA, 1);
			--this.bacteriaDrain;
		}
	}

	private int getNumberOfGenes() {
		final ItemStack serum = this.getUtil().getStack(Polymeriser.SLOT_SERUM);
		if (serum == null) {
			return 1;
		}
		return Engineering.getGenes(serum).length;
	}

	@Override
	public String getTooltip() {
		int n = this.getNumberOfGenes();
		if(n > 1){
			return String.format(Genetics.proxy.localise("genetics.machine.machine.polymeriser.tooltips.logic.genes"), Integer.valueOf(n).toString());
		}else{
			return Genetics.proxy.localise("genetics.machine.machine.polymeriser.tooltips.logic.gene");
		}
	}

	@Override
	public ErrorState canWork() {
		if (this.getUtil().isSlotEmpty(Polymeriser.SLOT_SERUM)) {
			return new ErrorState.NoItem(Genetics.proxy.localise("machine.machine.polymeriser.errors.item.no"), Polymeriser.SLOT_SERUM);
		}
		if (!this.getUtil().getStack(Polymeriser.SLOT_SERUM).isItemDamaged()) {
			return new ErrorState.InvalidItem(Genetics.proxy.localise("machine.machine.polymeriser.errors.item.filled"), Polymeriser.SLOT_SERUM);
		}
		return super.canWork();
	}

	@Override
	public ErrorState canProgress() {
		if (this.getUtil().getFluid(Polymeriser.TANK_BACTERIA) == null) {
			return new ErrorState.InsufficientLiquid(Genetics.proxy.localise("machine.machine.polymeriser.errors.insufficient.bacteria"), Polymeriser.TANK_BACTERIA);
		}
		if (this.getUtil().getFluid(Polymeriser.TANK_DNA) == null) {
			return new ErrorState.InsufficientLiquid(Genetics.proxy.localise("machine.machine.polymeriser.errors.insufficient.dna"), Polymeriser.TANK_DNA);
		}
		return super.canProgress();
	}

	@Override
	protected void onFinishTask() {
		super.onFinishTask();
		this.getUtil().damageItem(Polymeriser.SLOT_SERUM, -1);
	}
}