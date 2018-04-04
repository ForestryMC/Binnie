package binnie.genetics.machine.acclimatiser;

import java.util.ArrayList;
import java.util.List;

import binnie.core.machines.MachineUtil;
import net.minecraft.item.ItemStack;

import binnie.core.machines.IMachine;
import binnie.core.machines.errors.ErrorState;
import binnie.core.machines.power.ComponentProcessIndefinate;
import binnie.genetics.machine.GeneticsErrorCode;

public class AcclimatiserLogic extends ComponentProcessIndefinate {
	public AcclimatiserLogic(final IMachine machine) {
		super(machine, 2.0f);
	}

	@Override
	public ErrorState canWork() {
		if (this.getUtil().getStack(Acclimatiser.SLOT_TARGET).isEmpty()) {
			return new ErrorState(GeneticsErrorCode.NO_INDIVIDUAL, Acclimatiser.SLOT_TARGET);
		}
		if (this.getUtil().getNonEmptyStacks(Acclimatiser.SLOT_ACCLIMATISER).isEmpty()) {
			return new ErrorState(GeneticsErrorCode.ACCLIMATISER_NO_ITEM, Acclimatiser.SLOT_ACCLIMATISER);
		}
		return super.canWork();
	}

	@Override
	public ErrorState canProgress() {
		if (!Acclimatiser.canAcclimatise(this.getUtil().getStack(Acclimatiser.SLOT_TARGET), this.getUtil().getNonEmptyStacks(Acclimatiser.SLOT_ACCLIMATISER))) {
			return new ErrorState(GeneticsErrorCode.ACCLIMATISER_CAN_NOT_WORK, Acclimatiser.SLOT_TARGET);
		}
		return super.canProgress();
	}

	@Override
	protected boolean inProgress() {
		return this.canWork() == null;
	}

	@Override
	protected void onTickTask() {
		super.onTickTask();
		if (this.getUtil().getRandom().nextInt(100) == 0) {
			this.attemptAcclimatisation();
		}
	}

	protected void attemptAcclimatisation() {
		final List<ItemStack> acclms = new ArrayList<>();
		final MachineUtil machineUtil = getUtil();
		ItemStack target = machineUtil.getStack(Acclimatiser.SLOT_TARGET);
		if (target.isEmpty()) {
			return;
		}

		for (final ItemStack s : machineUtil.getNonEmptyStacks(Acclimatiser.SLOT_ACCLIMATISER)) {
			if (Acclimatiser.canAcclimatise(target, s)) {
				acclms.add(s);
			}
		}
		final ItemStack acc = acclms.get(machineUtil.getRandom().nextInt(acclms.size()));
		final ItemStack acclimed = Acclimatiser.acclimatise(target, acc);
		machineUtil.setStack(Acclimatiser.SLOT_TARGET, acclimed);
		for (final int i : Acclimatiser.SLOT_ACCLIMATISER) {
			ItemStack stack = machineUtil.getStack(i);
			if (!stack.isEmpty() && stack.isItemEqual(acc)) {
				machineUtil.decreaseStack(i, 1);
				break;
			}
		}
	}
}
