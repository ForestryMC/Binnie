package binnie.genetics.machine.analyser;

import binnie.core.machines.Machine;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import net.minecraft.item.ItemStack;

public class AnalyserComponentLogic extends ComponentProcessSetCost implements IProcess {
	private static final float DYE_PER_TICK = 0.002f;

	public AnalyserComponentLogic(Machine machine) {
		super(machine, 9000, 300);
	}

	@Override
	public ErrorState canWork() {
		if (getUtil().isSlotEmpty(Analyser.SLOT_TARGET)) {
			return new ErrorState.NoItem("No item to analyse", Analyser.SLOT_TARGET);
		}

		boolean analysed = Analyser.isAnalysed(getUtil().getStack(Analyser.SLOT_TARGET));
		if (analysed) {
			return new ErrorState.InvalidItem("Already Analysed", "Item has already been analysed", Analyser.SLOT_TARGET);
		}
		return super.canWork();
	}

	@Override
	public ErrorState canProgress() {
		if (getUtil().getSlotCharge(Analyser.SLOT_DYE) == 0.0f) {
			return new ErrorState.Item("Insufficient Dye", "Not enough DNA dye to analyse", new int[]{Analyser.SLOT_DYE});
		}
		return super.canProgress();
	}

	@Override
	protected void onFinishTask() {
		super.onFinishTask();
		ItemStack itemStack = getUtil().getStack(Analyser.SLOT_TARGET);
		itemStack = Analyser.analyse(itemStack);
		getInventory().setInventorySlotContents(Analyser.SLOT_TARGET, itemStack);
	}

	@Override
	protected void onTickTask() {
		getUtil().useCharge(Analyser.SLOT_DYE, DYE_PER_TICK);
	}
}
