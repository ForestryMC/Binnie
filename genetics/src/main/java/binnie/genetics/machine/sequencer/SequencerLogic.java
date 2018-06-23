package binnie.genetics.machine.sequencer;

import com.google.common.base.Preconditions;

import net.minecraft.item.ItemStack;

import binnie.core.machines.Machine;
import binnie.core.machines.errors.ErrorState;
import binnie.core.machines.power.ComponentProcess;
import binnie.core.machines.power.IProcess;
import binnie.genetics.config.ConfigurationMain;
import binnie.genetics.genetics.GeneTracker;
import binnie.genetics.genetics.SequencerItem;
import binnie.genetics.item.GeneticsItems;
import binnie.genetics.machine.GeneticsErrorCode;

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
		return (int) (ConfigurationMain.sequencerTimeMultiplier * getSequenceStrength(stack));
	}

	@Override
	public int getProcessEnergy() {
		return this.getProcessLength() * ConfigurationMain.sequencerEnergyMultiplier;
	}

	@Override
	public ErrorState canWork() {
		if (this.getUtil().isSlotEmpty(Sequencer.SLOT_TARGET)) {
			return new ErrorState(GeneticsErrorCode.SEQUENCER_NO_DNA, 5);
		}
		return super.canWork();
	}

	@Override
	public ErrorState canProgress() {
		if (this.getMachine().getOwner() == null) {
			return new ErrorState(GeneticsErrorCode.NO_OWNER);
		}
		if (this.getUtil().getSlotCharge(Sequencer.SLOT_DYE) == 0.0f) {
			return new ErrorState(GeneticsErrorCode.SEQUENCER_INSUFFICIENT_DYE, Sequencer.SLOT_DYE);
		}
		ItemStack stackDone = this.getUtil().getStack(Sequencer.SLOT_DONE);
		if (!stackDone.isEmpty() && stackDone.getCount() >= 64) {
			return new ErrorState(GeneticsErrorCode.SEQUENCER_NO_SPACE, new int[]{Sequencer.SLOT_DONE});
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
				final int seq = seqItem.getSequenced();
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
		final int seq = seqItem.getSequenced();
		if (prog != seq) {
			seqItem.setSequenced(prog);
			seqItem.writeToItem(item);
		}
	}
}
