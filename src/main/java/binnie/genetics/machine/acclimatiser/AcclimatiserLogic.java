package binnie.genetics.machine.acclimatiser;

import java.util.ArrayList;
import java.util.List;

import binnie.core.machines.IMachine;
import binnie.core.machines.power.ComponentProcessIndefinate;
import binnie.core.machines.power.ErrorState;
import binnie.genetics.Genetics;
import net.minecraft.item.ItemStack;

public class AcclimatiserLogic extends ComponentProcessIndefinate {
	public AcclimatiserLogic(final IMachine machine) {
		super(machine, 2.0f);
	}

	@Override
	public ErrorState canWork() {
		if (this.getUtil().getStack(Acclimatiser.SLOT_TARGET) == null) {
			return new ErrorState.NoItem(Genetics.proxy.localise("machine.labMachine.acclimatiser.errors.no.individual.desc"), Acclimatiser.SLOT_TARGET);
		}
		if (this.getUtil().getNonNullStacks(Acclimatiser.SLOT_ACCLIMATISER).isEmpty()) {
			return new ErrorState.NoItem(Genetics.proxy.localise("machine.labMachine.acclimatiser.errors.no.item.desc"), Acclimatiser.SLOT_ACCLIMATISER);
		}
		return super.canWork();
	}

	@Override
	public ErrorState canProgress() {
		if (!Acclimatiser.canAcclimatise(this.getUtil().getStack(Acclimatiser.SLOT_TARGET), this.getUtil().getNonNullStacks(Acclimatiser.SLOT_ACCLIMATISER))) {
			return new ErrorState.InvalidItem(Genetics.proxy.localise("machine.labMachine.acclimatiser.errors.can.not.acclimatise.desc"), Acclimatiser.SLOT_TARGET);
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
		for (final ItemStack s : this.getUtil().getNonNullStacks(Acclimatiser.SLOT_ACCLIMATISER)) {
			if (Acclimatiser.canAcclimatise(this.getUtil().getStack(Acclimatiser.SLOT_TARGET), s)) {
				acclms.add(s);
			}
		}
		final ItemStack acc = acclms.get(this.getUtil().getRandom().nextInt(acclms.size()));
		final ItemStack acclimed = Acclimatiser.acclimatise(this.getUtil().getStack(Acclimatiser.SLOT_TARGET), acc);
		if (acclimed != null) {
			this.getUtil().setStack(Acclimatiser.SLOT_TARGET, acclimed);
			boolean removed = false;
			for (final int i : Acclimatiser.SLOT_ACCLIMATISER) {
				if (!removed && this.getUtil().getStack(i) != null && this.getUtil().getStack(i).isItemEqual(acc)) {
					this.getUtil().decreaseStack(i, 1);
					removed = true;
				}
			}
		}
	}
}
