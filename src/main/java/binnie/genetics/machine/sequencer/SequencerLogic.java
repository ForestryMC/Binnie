package binnie.genetics.machine.sequencer;

import binnie.core.machines.Machine;
import binnie.core.machines.power.ComponentProcess;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.genetics.Genetics;
import binnie.genetics.genetics.GeneTracker;
import binnie.genetics.genetics.SequencerItem;
import binnie.genetics.item.GeneticsItems;
import com.google.common.base.Preconditions;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class SequencerLogic extends ComponentProcess implements IProcess {
	public SequencerLogic(final Machine machine) {
		super(machine);
	}

	public static float getSequenceStrength(ItemStack stack) {
		if (stack.isEmpty()) {
			return 1.0f;
		}
		final float mult = 1.0f - stack.getItemDamage() % 6 / 5.0f;
		return 1.0f - mult * mult * 0.75f;
	}

	@Override
	public int getProcessLength() {
		final ItemStack stack = this.getUtil().getStack(Sequencer.SLOT_TARGET);
		return (int) (19200.0f * getSequenceStrength(stack));
	}

	@Override
	public int getProcessEnergy() {
		return this.getProcessLength() * 20;
	}

	@Override
	public ErrorState canWork() {
		if (this.getUtil().isSlotEmpty(Sequencer.SLOT_TARGET)) {
			return new ErrorState.NoItem(Genetics.proxy.localise("machine.machine.sequencer.errors.no.dna"), 5);
		}
		return super.canWork();
	}

	@Override
	public ErrorState canProgress() {
		if (this.getMachine().getOwner() == null) {
			return new ErrorState(Genetics.proxy.localise("machine.errors.no.owner.desc"), Genetics.proxy.localise("machine.errors.no.owner.info"));
		}
		if (this.getUtil().getSlotCharge(Sequencer.SLOT_DYE) == 0.0f) {
			return new ErrorState.NoItem(Genetics.proxy.localise("machine.machine.sequencer.errors.insufficient.dye"), Sequencer.SLOT_DYE);
		}
		ItemStack stackDone = this.getUtil().getStack(Sequencer.SLOT_DONE);
		if (!stackDone.isEmpty() && stackDone.getCount() >= 64) {
			return new ErrorState.NoSpace(Genetics.proxy.localise("machine.machine.sequencer.errors.no.space"), new int[]{Sequencer.SLOT_DONE});
		}
		return super.canProgress();
	}

	@Override
	protected void onStartTask() {
		super.onStartTask();
		final ItemStack item = this.getUtil().getStack(Sequencer.SLOT_TARGET);
		if (!item.isEmpty()) {
			final SequencerItem seqItem = SequencerItem.create(item);
			if (seqItem != null) {
				final int seq = seqItem.sequenced;
				if (seq != 0) {
					this.setProgress(seq);
				}
			}
		}
	}

	@Override
	protected void onFinishTask() {
		super.onFinishTask();
		this.updateSequence();
		ItemStack stack = this.getUtil().getStack(Sequencer.SLOT_TARGET);
		Preconditions.checkState(!stack.isEmpty());
		final SequencerItem seqItem = SequencerItem.create(stack);
		Preconditions.checkState(seqItem != null);
		GeneTracker.getTracker(this.getMachine().getWorld(), this.getMachine().getOwner()).registerGene(seqItem.getGene());
		this.getUtil().decreaseStack(Sequencer.SLOT_TARGET, 1);
		if (this.getUtil().getStack(Sequencer.SLOT_DONE).isEmpty()) {
			this.getUtil().setStack(Sequencer.SLOT_DONE, GeneticsItems.EmptySequencer.get(1));
		} else {
			this.getUtil().decreaseStack(Sequencer.SLOT_DONE, -1);
		}
	}

	@Override
	protected void onTickTask() {
		this.updateSequence();
		this.getUtil().useCharge(Sequencer.SLOT_DYE, 0.4f * this.getProgressPerTick() / 100.0f);
	}

	private void updateSequence() {
		final int prog = (int) this.getProgress();
		final ItemStack item = this.getUtil().getStack(Sequencer.SLOT_TARGET);
		Preconditions.checkState(!item.isEmpty());
		final SequencerItem seqItem = SequencerItem.create(item);
		Preconditions.checkState(seqItem != null);
		final int seq = seqItem.sequenced;
		if (prog != seq) {
			seqItem.sequenced = prog;
			seqItem.writeToItem(item);
		}
	}
}
