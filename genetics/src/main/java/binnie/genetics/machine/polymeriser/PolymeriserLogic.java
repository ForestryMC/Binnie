package binnie.genetics.machine.polymeriser;

import com.google.common.base.Preconditions;

import net.minecraft.item.ItemStack;

import binnie.core.machines.Machine;
import binnie.core.machines.errors.ErrorState;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.IProcess;
import binnie.core.util.I18N;
import binnie.genetics.config.ConfigurationMain;
import binnie.genetics.genetics.Engineering;
import binnie.genetics.machine.GeneticsErrorCode;

public class PolymeriserLogic extends ComponentProcessSetCost implements IProcess {
	private static final float chargePerProcess = 0.4f;
	private float dnaDrain;
	private float bacteriaDrain;

	public PolymeriserLogic(final Machine machine) {
		super(machine, ConfigurationMain.polymeriserEnergy, ConfigurationMain.polymeriserTime);
		this.dnaDrain = 0.0f;
		this.bacteriaDrain = 0.0f;
	}

	public static float getDNAPerProcess(ItemStack serum) {
		return getNumberOfGenes(serum) * 50;
	}

	public static float getBacteriaPerProcess(ItemStack serum) {
		return 0.2f * getDNAPerProcess(serum);
	}

	private static int getNumberOfGenes(ItemStack serum) {
		if (serum.isEmpty()) {
			return 1;
		}
		return Engineering.getGenes(serum).length;
	}

	private float getCatalyst() {
		return (this.getUtil().getSlotCharge(Polymeriser.SLOT_GOLD) > 0.0f) ? 0.2f : 1.0f;
	}

	@Override
	public int getProcessLength() {
		final ItemStack serum = this.getUtil().getStack(Polymeriser.SLOT_SERUM);
		return (int) (super.getProcessLength() * getNumberOfGenes(serum) * this.getCatalyst());
	}

	@Override
	public int getProcessEnergy() {
		final ItemStack serum = this.getUtil().getStack(Polymeriser.SLOT_SERUM);
		return (int) (super.getProcessEnergy() * getNumberOfGenes(serum) * this.getCatalyst());
	}

	@Override
	public void onTickTask() {
		super.onTickTask();
		final ItemStack serum = this.getUtil().getStack(Polymeriser.SLOT_SERUM);

		this.getUtil().useCharge(Polymeriser.SLOT_GOLD, PolymeriserLogic.chargePerProcess * this.getProgressPerTick() / 100.0f);
		this.dnaDrain += getDNAPerProcess(serum) * this.getProgressPerTick() / 100.0f;
		this.bacteriaDrain += getBacteriaPerProcess(serum) * this.getProgressPerTick() / 100.0f;
		if (this.dnaDrain >= 1.0f) {
			this.getUtil().drainTank(Polymeriser.TANK_DNA, 1);
			--this.dnaDrain;
		}
		if (this.bacteriaDrain >= 1.0f) {
			this.getUtil().drainTank(Polymeriser.TANK_BACTERIA, 1);
			--this.bacteriaDrain;
		}
	}

	@Override
	public String getTooltip() {
		final ItemStack serum = this.getUtil().getStack(Polymeriser.SLOT_SERUM);
		int n = getNumberOfGenes(serum);
		if (n > 1) {
			return String.format(I18N.localise("genetics.machine.polymeriser.tooltips.logic.genes"), Integer.valueOf(n).toString());
		} else {
			return I18N.localise("genetics.machine.polymeriser.tooltips.logic.gene");
		}
	}

	@Override
	public ErrorState canWork() {
		ItemStack serumStack = this.getUtil().getStack(Polymeriser.SLOT_SERUM);
		if (serumStack.isEmpty()) {
			return new ErrorState(GeneticsErrorCode.POLYMERISER_NO_ITEM, Polymeriser.SLOT_SERUM);
		}
		if (!serumStack.isItemDamaged()) {
			return new ErrorState(GeneticsErrorCode.POLYMERISER_ITEM_FILLED, Polymeriser.SLOT_SERUM);
		}
		return super.canWork();
	}

	@Override
	public ErrorState canProgress() {
		if (this.getUtil().getFluid(Polymeriser.TANK_BACTERIA) == null) {
			return new ErrorState(GeneticsErrorCode.POLYMERISER_INSUFFICIENT_BACTERIA, Polymeriser.TANK_BACTERIA);
		}
		if (this.getUtil().getFluid(Polymeriser.TANK_DNA) == null) {
			return new ErrorState(GeneticsErrorCode.POLYMERISER_INSUFFICIENT_DNA, Polymeriser.TANK_DNA);
		}
		return super.canProgress();
	}

	@Override
	protected void onFinishTask() {
		super.onFinishTask();
		ItemStack serumStack = this.getUtil().getStack(Polymeriser.SLOT_SERUM);
		Preconditions.checkState(!serumStack.isEmpty());
		this.getUtil().damageItem(serumStack, Polymeriser.SLOT_SERUM, -1);
	}
}