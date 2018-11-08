package binnie.genetics.machine.analyser;

import net.minecraft.item.ItemStack;

import binnie.core.genetics.ManagerGenetics;
import binnie.core.machines.Machine;
import binnie.core.machines.errors.ErrorState;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.IProcess;
import binnie.genetics.config.ConfigurationMain;
import binnie.genetics.machine.GeneticsErrorCode;

public class AnalyserLogic extends ComponentProcessSetCost implements IProcess {
	private static final float DYE_PER_TICK = 0.002f;

	public AnalyserLogic(final Machine machine) {
		super(machine, ConfigurationMain.analyserEnergy, ConfigurationMain.analyserTime);
	}

	@Override
	public ErrorState canWork() {
		if (this.getUtil().isSlotEmpty(Analyser.SLOT_TARGET)) {
			return new ErrorState(GeneticsErrorCode.ANALYSER_NO_ITEM, Analyser.SLOT_TARGET);
		}
		final boolean analysed = ManagerGenetics.isAnalysed(this.getUtil().getStack(Analyser.SLOT_TARGET));
		if (analysed) {
			return new ErrorState(GeneticsErrorCode.ANALYSER_ALREADY_ANALYSED, Analyser.SLOT_TARGET);
		}
		return super.canWork();
	}

	@Override
	public ErrorState canProgress() {
		if (getMachine().getOwner() == null) {
			return new ErrorState(GeneticsErrorCode.NO_OWNER, Analyser.SLOT_TARGET);
		}
		if (this.getUtil().getSlotCharge(Analyser.SLOT_DYE) == 0.0f) {
			return new ErrorState(GeneticsErrorCode.ANALYSER_INSUFFICIENT_DYE, new int[]{Analyser.SLOT_DYE});
		}
		return super.canProgress();
	}

	@Override
	protected void onFinishTask() {
		super.onFinishTask();
		ItemStack itemStack = this.getUtil().getStack(Analyser.SLOT_TARGET);
		itemStack = ManagerGenetics.analyse(itemStack, getMachine().getWorld(), getMachine().getOwner());
		this.getInventory().setInventorySlotContents(Analyser.SLOT_TARGET, itemStack);
	}

	@Override
	protected void onTickTask() {
		this.getUtil().useCharge(Analyser.SLOT_DYE, 0.002f);
	}
}
