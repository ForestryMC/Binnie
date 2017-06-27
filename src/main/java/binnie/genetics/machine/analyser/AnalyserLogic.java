package binnie.genetics.machine.analyser;

import net.minecraft.item.ItemStack;

import binnie.core.machines.Machine;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.core.util.I18N;

public class AnalyserLogic extends ComponentProcessSetCost implements IProcess {
	private static final float DYE_PER_TICK = 0.002f;

	public AnalyserLogic(final Machine machine) {
		super(machine, 9000, 300);
	}

	@Override
	public ErrorState canWork() {
		if (this.getUtil().isSlotEmpty(Analyser.SLOT_TARGET)) {
			return new ErrorState.NoItem(I18N.localise("genetics.machine.labMachine.analyser.errors.no.item.desc"), Analyser.SLOT_TARGET);
		}
		final boolean analysed = Analyser.isAnalysed(this.getUtil().getStack(Analyser.SLOT_TARGET));
		if (analysed) {
			return new ErrorState.InvalidItem(I18N.localise("genetics.machine.labMachine.analyser.errors.already.analysed.desc"), I18N.localise("genetics.machine.labMachine.analyser.errors.already.analysed.info"), Analyser.SLOT_TARGET);
		}
		return super.canWork();
	}

	@Override
	public ErrorState canProgress() {
		if(getMachine().getOwner() == null){
			return new ErrorState.InvalidItem(I18N.localise("genetics.genetics.machine.errors.no.owner.desc"), I18N.localise("genetics.genetics.machine.errors.no.owner.info"), Analyser.SLOT_TARGET);
		}
		if (this.getUtil().getSlotCharge(Analyser.SLOT_DYE) == 0.0f) {
			return new ErrorState.Item(I18N.localise("genetics.machine.labMachine.analyser.errors.insufficient.dye.desc"), I18N.localise("genetics.machine.labMachine.analyser.errors.insufficient.dye.info"), new int[]{13});
		}
		return super.canProgress();
	}

	@Override
	protected void onFinishTask() {
		super.onFinishTask();
		ItemStack itemStack = this.getUtil().getStack(Analyser.SLOT_TARGET);
		itemStack = Analyser.analyse(itemStack, getMachine().getWorld(), getMachine().getOwner());
		this.getInventory().setInventorySlotContents(Analyser.SLOT_TARGET, itemStack);
	}

	@Override
	protected void onTickTask() {
		this.getUtil().useCharge(Analyser.SLOT_DYE, 0.002f);
	}
}
